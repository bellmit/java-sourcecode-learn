package com.fangxuele.tool.push.ui.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.dao.TWxMpUserMapper;
import com.fangxuele.tool.push.domain.TWxMpUser;
import com.fangxuele.tool.push.logic.MessageTypeEnum;
import com.fangxuele.tool.push.logic.PushData;
import com.fangxuele.tool.push.logic.msgsender.DingMsgSender;
import com.fangxuele.tool.push.logic.msgsender.WxCpMsgSender;
import com.fangxuele.tool.push.logic.msgsender.WxMpTemplateMsgSender;
import com.fangxuele.tool.push.ui.component.TableInCellImageLabelRenderer;
import com.fangxuele.tool.push.ui.dialog.ExportDialog;
import com.fangxuele.tool.push.ui.form.MainWindow;
import com.fangxuele.tool.push.ui.form.MemberForm;
import com.fangxuele.tool.push.ui.form.msg.DingMsgForm;
import com.fangxuele.tool.push.ui.form.msg.WxCpMsgForm;
import com.fangxuele.tool.push.util.*;
import com.google.common.collect.Maps;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpTag;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.tag.WxTagListUser;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.sql.Connection;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * ??????????????????tab??????????????????
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2017/6/19.
 */
public class MemberListener {
    private static final Log logger = LogFactory.get();

    public static Map<String, Long> userTagMap = new HashMap<>();

    /**
     * ????????????????????????????????????????????????
     */
    public static Set<String> tagUserSet;

    public static final String TXT_FILE_DATA_SEPERATOR_REGEX = "\\|";

    private static List<String> toSearchRowsList;

    /**
     * ?????????????????????->??????ID
     */
    private static Map<String, String> wxCpTagNameToIdMap = Maps.newHashMap();

    /**
     * ???????????????ID>????????????
     */
    private static Map<String, String> wxCpIdToTagNameMap = Maps.newHashMap();

    /**
     * ?????????????????????->??????ID
     */
    private static Map<String, Long> wxCpDeptNameToIdMap = Maps.newHashMap();

    /**
     * ???????????????ID>????????????
     */
    private static Map<Long, String> wxCpIdToDeptNameMap = Maps.newHashMap();

    private static TWxMpUserMapper tWxMpUserMapper = MybatisUtil.getSqlSession().getMapper(TWxMpUserMapper.class);

    public static void addListeners() {
        MemberForm memberForm = MemberForm.getInstance();
        JProgressBar progressBar = memberForm.getMemberTabImportProgressBar();
        JTextField filePathField = memberForm.getMemberFilePathField();
        JLabel memberCountLabel = memberForm.getMemberTabCountLabel();
        JPanel memberPanel = memberForm.getMemberPanel();
        JTable memberListTable = memberForm.getMemberListTable();

        // ???????????????????????????
        memberForm.getImportFromNumButton().addActionListener(e -> ThreadUtil.execute(() -> {
            importByNum(memberForm, progressBar, memberCountLabel, memberPanel);
        }));

        memberForm.getImportNumTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent evt) {

            }

            @Override
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    importByNum(memberForm, progressBar, memberCountLabel, memberPanel);
                }
            }
        });

        // ???????????????????????????
        memberForm.getImportFromFileButton().addActionListener(e -> ThreadUtil.execute(MemberListener::importFromFile));

        // ???sql?????? ????????????
        memberForm.getImportFromSqlButton().addActionListener(e -> ThreadUtil.execute(MemberListener::importFromSql));

        // ?????????-????????????????????????
        memberForm.getMemberImportAllButton().addActionListener(e -> ThreadUtil.execute(MemberListener::importWxAll));

        // ?????????-?????????????????????????????????
        memberForm.getMemberImportTagFreshButton().addActionListener(e -> {
            WxMpService wxMpService = WxMpTemplateMsgSender.getWxMpService();
            if (wxMpService.getWxMpConfigStorage() == null) {
                return;
            }

            try {
                List<WxUserTag> wxUserTagList = wxMpService.getUserTagService().tagGet();

                memberForm.getMemberImportTagComboBox().removeAllItems();
                userTagMap = new HashMap<>();

                for (WxUserTag wxUserTag : wxUserTagList) {
                    String item = wxUserTag.getName() + "/" + wxUserTag.getCount() + "??????";
                    memberForm.getMemberImportTagComboBox().addItem(item);
                    userTagMap.put(item, wxUserTag.getId());
                }

            } catch (WxErrorException e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
                e1.printStackTrace();
            }
        });

        // ?????????-?????????????????????????????????????????????(?????????)
        memberForm.getMemberImportTagButton().addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                if (memberForm.getMemberImportTagComboBox().getSelectedItem() != null
                        && StringUtils.isNotEmpty(memberForm.getMemberImportTagComboBox().getSelectedItem().toString())) {

                    long selectedTagId = userTagMap.get(memberForm.getMemberImportTagComboBox().getSelectedItem());
                    getMpUserListByTag(selectedTagId, false);
                    renderMemberListTable();
                    JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(memberPanel, "????????????????????????????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (WxErrorException e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            } finally {
                progressBar.setIndeterminate(false);
                progressBar.setValue(progressBar.getMaximum());
                progressBar.setVisible(false);
            }
        }));

        // ?????????-?????????????????????????????????????????????(?????????)
        memberForm.getMemberImportTagRetainButton().addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                if (memberForm.getMemberImportTagComboBox().getSelectedItem() != null
                        && StringUtils.isNotEmpty(memberForm.getMemberImportTagComboBox().getSelectedItem().toString())) {

                    long selectedTagId = userTagMap.get(memberForm.getMemberImportTagComboBox().getSelectedItem());
                    getMpUserListByTag(selectedTagId, true);
                    renderMemberListTable();
                    JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(memberPanel, "????????????????????????????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (WxErrorException e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
                e1.printStackTrace();
            } finally {
                progressBar.setIndeterminate(false);
                progressBar.setValue(progressBar.getMaximum());
                progressBar.setVisible(false);
            }
        }));

        // ?????????-?????????????????????????????????????????????
        memberForm.getMemberImportTagExceptButton().addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                if (memberForm.getMemberImportTagComboBox().getSelectedItem() != null
                        && StringUtils.isNotEmpty(memberForm.getMemberImportTagComboBox().getSelectedItem().toString())) {

                    long selectedTagId = userTagMap.get(memberForm.getMemberImportTagComboBox().getSelectedItem());
                    removeMpUserListByTag(selectedTagId);
                    renderMemberListTable();
                    JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(memberPanel, "????????????????????????????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (WxErrorException e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
                e1.printStackTrace();
            } finally {
                progressBar.setIndeterminate(false);
                progressBar.setValue(progressBar.getMaximum());
                progressBar.setVisible(false);
            }
        }));

        // ?????????-??????????????????????????????
        memberForm.getClearDbCacheButton().addActionListener(e -> {
            int count = tWxMpUserMapper.deleteAll();
            JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n????????????" + count + "???????????????", "??????",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // ?????????-???????????????-??????
        memberForm.getWxCpTagsRefreshButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                if (WxCpMsgForm.getInstance().getAppNameComboBox().getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(MainWindow.getInstance().getMessagePanel(), "?????????????????????tab??????????????????", "??????",
                            JOptionPane.ERROR_MESSAGE);
                    MainWindow.getInstance().getTabbedPane().setSelectedIndex(2);
                    return;
                }
                memberForm.getWxCpTagsComboBox().removeAllItems();

                try {
                    // ??????????????????
                    List<WxCpTag> wxCpTagList = WxCpMsgSender.getWxCpService().getTagService().listAll();
                    for (WxCpTag wxCpTag : wxCpTagList) {
                        memberForm.getWxCpTagsComboBox().addItem(wxCpTag.getName());
                        wxCpTagNameToIdMap.put(wxCpTag.getName(), wxCpTag.getId());
                        wxCpIdToTagNameMap.put(wxCpTag.getId(), wxCpTag.getName());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                            JOptionPane.ERROR_MESSAGE);
                    logger.error(ex.toString());
                }
            });
        });

        // ?????????-???????????????-??????
        memberForm.getWxCpTagsImportButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                if (memberForm.getWxCpTagsComboBox().getSelectedItem() == null) {
                    return;
                }
                try {
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                    int importedCount = 0;
                    PushData.allUser = Collections.synchronizedList(new ArrayList<>());

                    // ????????????id
                    String tagId = wxCpTagNameToIdMap.get(memberForm.getWxCpTagsComboBox().getSelectedItem());
                    // ????????????
                    List<WxCpUser> wxCpUsers = WxCpMsgSender.getWxCpService().getTagService().listUsersByTagId(tagId);
                    for (WxCpUser wxCpUser : wxCpUsers) {
                        Long[] depIds = wxCpUser.getDepartIds();
                        List<String> deptNameList = Lists.newArrayList();
                        if (depIds != null) {
                            for (Long depId : depIds) {
                                deptNameList.add(wxCpIdToDeptNameMap.get(depId));
                            }
                        }
                        String[] dataArray = new String[]{wxCpUser.getUserId(), wxCpUser.getName(), String.join("/", deptNameList)};
                        PushData.allUser.add(dataArray);
                        importedCount++;
                        memberCountLabel.setText(String.valueOf(importedCount));
                    }
                    renderMemberListTable();
                    if (!PushData.fixRateScheduling) {
                        JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                            JOptionPane.ERROR_MESSAGE);
                    logger.error(ex.toString());
                } finally {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisible(false);
                }

            });
        });

        // ?????????-???????????????-??????
        memberForm.getWxCpDeptsRefreshButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                if (WxCpMsgForm.getInstance().getAppNameComboBox().getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(MainWindow.getInstance().getMessagePanel(), "?????????????????????tab??????????????????", "??????",
                            JOptionPane.ERROR_MESSAGE);
                    MainWindow.getInstance().getTabbedPane().setSelectedIndex(2);
                    return;
                }
                memberForm.getWxCpDeptsComboBox().removeAllItems();

                try {
                    // ??????????????????
                    List<WxCpDepart> wxCpDepartList = WxCpMsgSender.getWxCpService().getDepartmentService().list(null);
                    for (WxCpDepart wxCpDepart : wxCpDepartList) {
                        memberForm.getWxCpDeptsComboBox().addItem(wxCpDepart.getName());
                        wxCpDeptNameToIdMap.put(wxCpDepart.getName(), wxCpDepart.getId());
                        wxCpIdToDeptNameMap.put(wxCpDepart.getId(), wxCpDepart.getName());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                            JOptionPane.ERROR_MESSAGE);
                    logger.error(ex.toString());
                }
            });
        });

        // ?????????-???????????????-??????
        memberForm.getWxCpDeptsImportButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                if (memberForm.getWxCpDeptsComboBox().getSelectedItem() == null) {
                    return;
                }
                try {
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                    int importedCount = 0;
                    PushData.allUser = Collections.synchronizedList(new ArrayList<>());

                    // ????????????id
                    Long deptId = wxCpDeptNameToIdMap.get(memberForm.getWxCpDeptsComboBox().getSelectedItem());
                    // ????????????
                    List<WxCpUser> wxCpUsers = WxCpMsgSender.getWxCpService().getUserService().listByDepartment(deptId, true, 0);
                    for (WxCpUser wxCpUser : wxCpUsers) {
                        String statusStr = "";
                        if (wxCpUser.getStatus() == 1) {
                            statusStr = "?????????";
                        } else if (wxCpUser.getStatus() == 2) {
                            statusStr = "?????????";
                        } else if (wxCpUser.getStatus() == 4) {
                            statusStr = "?????????";
                        }
                        Long[] depIds = wxCpUser.getDepartIds();
                        List<String> deptNameList = Lists.newArrayList();
                        if (depIds != null) {
                            for (Long depId : depIds) {
                                deptNameList.add(wxCpIdToDeptNameMap.get(depId));
                            }
                        }
                        String[] dataArray = new String[]{wxCpUser.getUserId(), wxCpUser.getName(), wxCpUser.getGender().getGenderName(), wxCpUser.getEmail(), String.join("/", deptNameList), wxCpUser.getPosition(), statusStr};
                        PushData.allUser.add(dataArray);
                        importedCount++;
                        memberCountLabel.setText(String.valueOf(importedCount));
                    }
                    renderMemberListTable();
                    if (!PushData.fixRateScheduling) {
                        JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                            JOptionPane.ERROR_MESSAGE);
                    logger.error(ex.toString());
                } finally {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisible(false);
                }

            });
        });

        // ?????????-????????????
        memberForm.getWxCpImportAllButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                importWxCpAll();
            });
        });

        // ??????-???????????????-??????
        memberForm.getDingDeptsRefreshButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                if (DingMsgForm.getInstance().getAppNameComboBox().getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(MainWindow.getInstance().getMessagePanel(), "?????????????????????tab??????????????????", "??????",
                            JOptionPane.ERROR_MESSAGE);
                    MainWindow.getInstance().getTabbedPane().setSelectedIndex(2);
                    return;
                }
                memberForm.getDingDeptsComboBox().removeAllItems();

                try {
                    // ??????????????????
                    DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
                    OapiDepartmentListRequest request = new OapiDepartmentListRequest();
                    request.setHttpMethod("GET");
                    OapiDepartmentListResponse response = client.execute(request, DingMsgSender.getAccessTokenTimedCache().get("accessToken"));
                    if (response.getErrcode() != 0) {
                        JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + response.getErrmsg(), "??????",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    List<OapiDepartmentListResponse.Department> departmentList = response.getDepartment();
                    for (OapiDepartmentListResponse.Department department : departmentList) {
                        memberForm.getDingDeptsComboBox().addItem(department.getName());
                        wxCpDeptNameToIdMap.put(department.getName(), department.getId());
                        wxCpIdToDeptNameMap.put(department.getId(), department.getName());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                            JOptionPane.ERROR_MESSAGE);
                    logger.error(ex.toString());
                }
            });
        });

        // ??????-???????????????-??????
        memberForm.getDingDeptsImportButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                if (memberForm.getDingDeptsComboBox().getSelectedItem() == null) {
                    return;
                }
                try {
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                    int importedCount = 0;
                    PushData.allUser = Collections.synchronizedList(new ArrayList<>());

                    // ????????????id
                    Long deptId = wxCpDeptNameToIdMap.get(memberForm.getDingDeptsComboBox().getSelectedItem());
                    // ????????????
                    DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
                    OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
                    request.setDepartmentId(deptId);
                    request.setOffset(0L);
                    request.setSize(100L);
                    request.setHttpMethod("GET");

                    long offset = 0;
                    OapiUserSimplelistResponse response = new OapiUserSimplelistResponse();
                    while (response.getErrcode() == null || response.getUserlist().size() > 0) {
                        response = client.execute(request, DingMsgSender.getAccessTokenTimedCache().get("accessToken"));
                        if (response.getErrcode() != 0) {
                            if (response.getErrcode() == 60011) {
                                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + response.getErrmsg() + "\n\n???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", "??????",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + response.getErrmsg(), "??????", JOptionPane.ERROR_MESSAGE);
                            }

                            logger.error(response.getErrmsg());
                            return;
                        }
                        List<OapiUserSimplelistResponse.Userlist> userlist = response.getUserlist();
                        for (OapiUserSimplelistResponse.Userlist dingUser : userlist) {
                            String[] dataArray = new String[]{dingUser.getUserid(), dingUser.getName()};
                            PushData.allUser.add(dataArray);
                            importedCount++;
                            memberCountLabel.setText(String.valueOf(importedCount));
                        }
                        offset += 100;
                        request.setOffset(offset);
                    }
                    renderMemberListTable();
                    if (!PushData.fixRateScheduling) {
                        JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                            JOptionPane.ERROR_MESSAGE);
                    logger.error(ex.toString());
                } finally {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisible(false);
                }

            });
        });

        // ??????-????????????
        memberForm.getDingImportAllButton().addActionListener(e -> {
            ThreadUtil.execute(() -> {
                importDingAll();
            });
        });

        // ??????????????????
        memberForm.getClearImportButton().addActionListener(e -> {
            int isClear = JOptionPane.showConfirmDialog(memberPanel, "???????????????", "??????",
                    JOptionPane.YES_NO_OPTION);
            if (isClear == JOptionPane.YES_OPTION) {
                MemberForm.clearMember();
            }
        });

        // ????????????
        memberForm.getMemberImportExploreButton().addActionListener(e -> {
            File beforeFile = new File(filePathField.getText());
            JFileChooser fileChooser;

            if (beforeFile.exists()) {
                fileChooser = new JFileChooser(beforeFile);
            } else {
                fileChooser = new JFileChooser();
            }

            FileFilter filter = new FileNameExtensionFilter("*.txt,*.csv,*.xlsx,*.xls", "txt", "csv", "TXT", "CSV", "xlsx", "xls");
            fileChooser.setFileFilter(filter);

            int approve = fileChooser.showOpenDialog(memberPanel);
            if (approve == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }

        });

        // ??????-??????????????????
        memberForm.getSelectAllButton().addActionListener(e -> ThreadUtil.execute(() -> memberListTable.selectAll()));

        // ??????-??????????????????
        memberForm.getDeleteButton().addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                int[] selectedRows = memberListTable.getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(memberPanel, "????????????????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int isDelete = JOptionPane.showConfirmDialog(memberPanel, "???????????????", "??????",
                            JOptionPane.YES_NO_OPTION);
                    if (isDelete == JOptionPane.YES_OPTION) {
                        DefaultTableModel tableModel = (DefaultTableModel) memberListTable.getModel();
                        for (int i = selectedRows.length; i > 0; i--) {
                            tableModel.removeRow(memberListTable.getSelectedRow());
                        }
                        memberListTable.updateUI();
                    }
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        }));

        // ??????-??????????????????
        memberForm.getImportSelectedButton().addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                int[] selectedRows = memberListTable.getSelectedRows();
                if (selectedRows.length <= 0) {
                    JOptionPane.showMessageDialog(memberPanel, "????????????????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    PushData.allUser = Collections.synchronizedList(new ArrayList<>());
                    progressBar.setIndeterminate(true);
                    progressBar.setVisible(true);
                    for (int selectedRow : selectedRows) {
                        String toImportData = (String) memberListTable.getValueAt(selectedRow, 0);
                        PushData.allUser.add(toImportData.split(TXT_FILE_DATA_SEPERATOR_REGEX));
                        memberCountLabel.setText(String.valueOf(PushData.allUser.size()));
                        progressBar.setMaximum(100);
                        progressBar.setValue(100);
                        progressBar.setIndeterminate(false);
                    }
                    JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            } finally {
                progressBar.setMaximum(100);
                progressBar.setValue(100);
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
            }
        }));

        // ??????-??????????????????
        memberForm.getExportButton().addActionListener(e -> ThreadUtil.execute(() -> {
            int[] selectedRows = memberListTable.getSelectedRows();
            int columnCount = memberListTable.getColumnCount();
            BigExcelWriter writer = null;
            try {
                if (selectedRows.length > 0) {
                    ExportDialog.showDialog();
                    if (ExportDialog.confirm) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int approve = fileChooser.showOpenDialog(memberPanel);
                        String exportPath;
                        if (approve == JFileChooser.APPROVE_OPTION) {
                            exportPath = fileChooser.getSelectedFile().getAbsolutePath();
                        } else {
                            return;
                        }

                        List<String> rowData;
                        List<List<String>> rows = Lists.newArrayList();
                        for (int selectedRow : selectedRows) {
                            rowData = Lists.newArrayList();
                            for (int i = 0; i < columnCount; i++) {
                                String data = (String) memberListTable.getValueAt(selectedRow, i);
                                rowData.add(data);
                            }
                            rows.add(rowData);
                        }
                        String nowTime = DateUtil.now().replace(":", "_").replace(" ", "_");
                        String fileName = "MemberExport_" + MessageTypeEnum.getName(App.config.getMsgType()) + "_" + nowTime;
                        String fileFullName = exportPath + File.separator + fileName;
                        if (ExportDialog.fileType == ExportDialog.EXCEL) {
                            fileFullName += ".xlsx";
                            //?????????????????????writer
                            writer = ExcelUtil.getBigWriter(fileFullName);
                            //?????????????????????????????????????????????????????????
                            writer.merge(rows.get(0).size() - 1, "????????????????????????");
                            //??????????????????????????????????????????
                            writer.write(rows);
                            writer.flush();
                        } else if (ExportDialog.fileType == ExportDialog.CSV) {
                            fileFullName += ".csv";
                            CSVWriter csvWriter = new CSVWriter(new FileWriter(FileUtil.touch(fileFullName)));
                            for (List<String> row : rows) {
                                String[] array = row.toArray(new String[row.size()]);
                                csvWriter.writeNext(array);
                            }
                            csvWriter.flush();
                            csvWriter.close();
                        } else if (ExportDialog.fileType == ExportDialog.TXT) {
                            fileFullName += ".txt";
                            FileWriter fileWriter = new FileWriter(fileFullName);
                            int size = rows.size();
                            for (int i = 0; i < size; i++) {
                                List<String> row = rows.get(i);
                                fileWriter.append(String.join("|", row));
                                if (i < size - 1) {
                                    fileWriter.append(StrUtil.CRLF);
                                }
                            }
                            fileWriter.flush();
                            fileWriter.close();
                        }
                        JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????",
                                JOptionPane.INFORMATION_MESSAGE);
                        try {
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(FileUtil.file(fileFullName));
                        } catch (Exception e2) {
                            logger.error(e2);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(memberPanel, "????????????????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        }));

        // ??????-??????????????????
        memberForm.getSearchButton().addActionListener(e -> searchEvent());

        // ??????-?????????????????????
        memberForm.getSearchTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    searchEvent();
                } catch (Exception e1) {
                    logger.error(e1);
                } finally {
                    super.keyPressed(e);
                }
            }
        });

    }

    /**
     * ???????????????
     *
     * @param memberForm
     * @param progressBar
     * @param memberCountLabel
     * @param memberPanel
     */
    private static void importByNum(MemberForm memberForm, JProgressBar progressBar, JLabel memberCountLabel, JPanel memberPanel) {
        if (StringUtils.isBlank(memberForm.getImportNumTextField().getText())) {
            JOptionPane.showMessageDialog(memberPanel, "??????????????????", "??????",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int currentImported = 0;

        try {
            int importNum = Integer.parseInt(memberForm.getImportNumTextField().getText());
            progressBar.setVisible(true);
            progressBar.setMaximum(importNum);

            PushData.allUser = Collections.synchronizedList(new ArrayList<>());

            for (int i = 0; i < importNum; i++) {
                String[] array = new String[1];
                array[0] = String.valueOf(i);
                PushData.allUser.add(array);
                currentImported++;
                memberCountLabel.setText(String.valueOf(currentImported));
            }

            renderMemberListTable();

            if (!PushData.fixRateScheduling) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(e1);
            e1.printStackTrace();
        } finally {
            progressBar.setMaximum(100);
            progressBar.setValue(100);
            progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
        }
    }

    private static void searchEvent() {
        JTable memberListTable = MemberForm.getInstance().getMemberListTable();
        ThreadUtil.execute(() -> {
            int rowCount = memberListTable.getRowCount();
            int columnCount = memberListTable.getColumnCount();
            try {
                if (rowCount > 0 || toSearchRowsList != null) {
                    if (toSearchRowsList == null) {
                        toSearchRowsList = Lists.newArrayList();
                        List<String> rowData;
                        for (int i = 0; i < rowCount; i++) {
                            rowData = Lists.newArrayList();
                            for (int j = 0; j < columnCount; j++) {
                                String data = (String) memberListTable.getValueAt(i, j);
                                rowData.add(data);
                            }
                            toSearchRowsList.add(String.join("==", rowData));
                        }
                    }

                    String keyWord = MemberForm.getInstance().getSearchTextField().getText();
                    List<String> searchResultList = toSearchRowsList.parallelStream().filter(rowStr -> rowStr.contains(keyWord)).collect(Collectors.toList());

                    DefaultTableModel tableModel = (DefaultTableModel) memberListTable.getModel();
                    tableModel.setRowCount(0);
                    for (String rowString : searchResultList) {
                        tableModel.addRow(rowString.split("=="));
                    }
                }
            } catch (Exception e1) {
                logger.error(e1);
            }
        });
    }

    /**
     * ??????????????????????????????
     */
    public static void getMpUserList() throws WxErrorException {
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        JLabel memberCountLabel = MemberForm.getInstance().getMemberTabCountLabel();

        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        WxMpService wxMpService = WxMpTemplateMsgSender.getWxMpService();
        if (wxMpService.getWxMpConfigStorage() == null) {
            return;
        }

        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);

        ConsoleUtil.consoleWithLog("???????????????????????????????????????" + wxMpUserList.getTotal());
        ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxMpUserList.getCount());

        progressBar.setIndeterminate(false);
        progressBar.setMaximum((int) wxMpUserList.getTotal());
        int importedCount = 0;
        PushData.allUser = Collections.synchronizedList(new ArrayList<>());

        if (wxMpUserList.getCount() == 0) {
            memberCountLabel.setText(String.valueOf(importedCount));
            progressBar.setValue(importedCount);
            return;
        }

        List<String> openIds = wxMpUserList.getOpenids();

        for (String openId : openIds) {
            PushData.allUser.add(new String[]{openId});
            importedCount++;
            memberCountLabel.setText(String.valueOf(importedCount));
            progressBar.setValue(importedCount);
        }

        while (StringUtils.isNotEmpty(wxMpUserList.getNextOpenid())) {
            wxMpUserList = wxMpService.getUserService().userList(wxMpUserList.getNextOpenid());

            ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxMpUserList.getCount());

            if (wxMpUserList.getCount() == 0) {
                break;
            }
            openIds = wxMpUserList.getOpenids();
            for (String openId : openIds) {
                PushData.allUser.add(new String[]{openId});
                importedCount++;
                memberCountLabel.setText(String.valueOf(importedCount));
                progressBar.setValue(importedCount);
            }
        }

        progressBar.setValue((int) wxMpUserList.getTotal());
    }

    /**
     * ???????????????????????????????????????
     *
     * @param tagId
     * @throws WxErrorException
     */
    public static void getMpUserListByTag(Long tagId) throws WxErrorException {
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        JLabel memberCountLabel = MemberForm.getInstance().getMemberTabCountLabel();

        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        WxMpService wxMpService = WxMpTemplateMsgSender.getWxMpService();
        if (wxMpService.getWxMpConfigStorage() == null) {
            return;
        }

        WxTagListUser wxTagListUser = wxMpService.getUserTagService().tagListUser(tagId, "");

        ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxTagListUser.getCount());

        if (wxTagListUser.getCount() == 0) {
            return;
        }

        List<String> openIds = wxTagListUser.getData().getOpenidList();

        tagUserSet = Collections.synchronizedSet(new HashSet<>());
        tagUserSet.addAll(openIds);

        while (StringUtils.isNotEmpty(wxTagListUser.getNextOpenid())) {
            wxTagListUser = wxMpService.getUserTagService().tagListUser(tagId, wxTagListUser.getNextOpenid());

            ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxTagListUser.getCount());

            if (wxTagListUser.getCount() == 0) {
                break;
            }
            openIds = wxTagListUser.getData().getOpenidList();

            tagUserSet.addAll(openIds);
        }

        PushData.allUser = Collections.synchronizedList(new ArrayList<>());
        for (String openId : tagUserSet) {
            PushData.allUser.add(new String[]{openId});
        }

        memberCountLabel.setText(String.valueOf(PushData.allUser.size()));
        progressBar.setIndeterminate(false);
        progressBar.setValue(progressBar.getMaximum());

    }

    /**
     * ???????????????????????????????????????
     *
     * @param tagId
     * @param retain ???????????????
     * @throws WxErrorException
     */
    public static void getMpUserListByTag(Long tagId, boolean retain) throws WxErrorException {
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        JLabel memberCountLabel = MemberForm.getInstance().getMemberTabCountLabel();

        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        WxMpService wxMpService = WxMpTemplateMsgSender.getWxMpService();
        if (wxMpService.getWxMpConfigStorage() == null) {
            return;
        }

        WxTagListUser wxTagListUser = wxMpService.getUserTagService().tagListUser(tagId, "");

        ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxTagListUser.getCount());

        if (wxTagListUser.getCount() == 0) {
            return;
        }

        List<String> openIds = wxTagListUser.getData().getOpenidList();

        if (tagUserSet == null) {
            tagUserSet = Collections.synchronizedSet(new HashSet<>());
            tagUserSet.addAll(openIds);
        }

        if (retain) {
            // ?????????
            tagUserSet.retainAll(openIds);
        } else {
            // ???????????????
            openIds.removeAll(tagUserSet);
            tagUserSet.addAll(openIds);
        }

        while (StringUtils.isNotEmpty(wxTagListUser.getNextOpenid())) {
            wxTagListUser = wxMpService.getUserTagService().tagListUser(tagId, wxTagListUser.getNextOpenid());

            ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxTagListUser.getCount());

            if (wxTagListUser.getCount() == 0) {
                break;
            }
            openIds = wxTagListUser.getData().getOpenidList();

            if (retain) {
                // ?????????
                tagUserSet.retainAll(openIds);
            } else {
                // ???????????????
                openIds.removeAll(tagUserSet);
                tagUserSet.addAll(openIds);
            }
        }

        PushData.allUser = Collections.synchronizedList(new ArrayList<>());
        for (String openId : tagUserSet) {
            PushData.allUser.add(new String[]{openId});
        }

        memberCountLabel.setText(String.valueOf(PushData.allUser.size()));
        progressBar.setIndeterminate(false);
        progressBar.setValue(progressBar.getMaximum());

    }

    /**
     * ??????????????????
     *
     * @param tagId
     * @throws WxErrorException
     */
    private static void removeMpUserListByTag(long tagId) throws WxErrorException {
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        JLabel memberCountLabel = MemberForm.getInstance().getMemberTabCountLabel();

        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        WxMpService wxMpService = WxMpTemplateMsgSender.getWxMpService();
        if (wxMpService.getWxMpConfigStorage() == null) {
            return;
        }

        WxTagListUser wxTagListUser = wxMpService.getUserTagService().tagListUser(tagId, "");

        ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxTagListUser.getCount());

        if (wxTagListUser.getCount() == 0) {
            return;
        }

        List<String> openIds = wxTagListUser.getData().getOpenidList();

        tagUserSet = PushData.allUser.stream().map(e -> e[0]).collect(Collectors.toSet());
        openIds.forEach(tagUserSet::remove);

        while (StringUtils.isNotEmpty(wxTagListUser.getNextOpenid())) {
            wxTagListUser = wxMpService.getUserTagService().tagListUser(tagId, wxTagListUser.getNextOpenid());

            ConsoleUtil.consoleWithLog("?????????OPENID?????????" + wxTagListUser.getCount());

            if (wxTagListUser.getCount() == 0) {
                break;
            }
            openIds = wxTagListUser.getData().getOpenidList();

            openIds.forEach(tagUserSet::remove);
        }

        PushData.allUser = Collections.synchronizedList(new ArrayList<>());
        for (String openId : tagUserSet) {
            PushData.allUser.add(new String[]{openId});
        }

        memberCountLabel.setText(String.valueOf(PushData.allUser.size()));
        progressBar.setIndeterminate(false);
        progressBar.setValue(progressBar.getMaximum());

    }

    /**
     * ??????????????????????????????
     */
    public static void renderMemberListTable() {
        JTable memberListTable = MemberForm.getInstance().getMemberListTable();
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        MemberForm memberForm = MemberForm.getInstance();

        toSearchRowsList = null;
        DefaultTableModel tableModel = (DefaultTableModel) memberListTable.getModel();
        tableModel.setRowCount(0);
        progressBar.setVisible(true);
        progressBar.setMaximum(PushData.allUser.size());

        int msgType = App.config.getMsgType();

        // ????????????
        List<String> headerNameList = Lists.newArrayList();
        headerNameList.add("Data");
        if (MessageTypeEnum.isWxMaOrMpType(msgType)) {
            if (memberForm.getImportOptionAvatarCheckBox().isSelected()) {
                headerNameList.add("??????");
            }
            if (memberForm.getImportOptionBasicInfoCheckBox().isSelected()) {
                headerNameList.add("??????");
                headerNameList.add("??????");
                headerNameList.add("??????");
                headerNameList.add("????????????");
            }
            headerNameList.add("openId");
        } else {
            headerNameList.add("??????");
        }

        String[] headerNames = new String[headerNameList.size()];
        headerNameList.toArray(headerNames);
        DefaultTableModel model = new DefaultTableModel(null, headerNames);
        memberListTable.setModel(model);
        if (MessageTypeEnum.isWxMaOrMpType(msgType) && memberForm.getImportOptionAvatarCheckBox().isSelected()) {
            memberListTable.getColumn("??????").setCellRenderer(new TableInCellImageLabelRenderer());
        }

        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) memberListTable.getTableHeader()
                .getDefaultRenderer();
        // ??????????????????
        hr.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);

        // ?????????0???Data?????????
        JTableUtil.hideColumn(memberListTable, 0);

        // ????????????
        if (MessageTypeEnum.isWxMaOrMpType(msgType) && memberForm.getImportOptionAvatarCheckBox().isSelected()) {
            memberListTable.setRowHeight(66);
        } else {
            memberListTable.setRowHeight(36);
        }

        List<Object> rowDataList;
        WxMpService wxMpService = null;
        boolean needToGetInfoFromWeiXin = false;
        if (MessageTypeEnum.isWxMaOrMpType(msgType) && (memberForm.getImportOptionBasicInfoCheckBox().isSelected() ||
                memberForm.getImportOptionAvatarCheckBox().isSelected())) {
            needToGetInfoFromWeiXin = true;
        }
        if (needToGetInfoFromWeiXin) {
            wxMpService = WxMpTemplateMsgSender.getWxMpService();
        }
        for (int i = 0; i < PushData.allUser.size(); i++) {
            String[] importedData = PushData.allUser.get(i);
            try {
                String openId = importedData[0];
                rowDataList = new ArrayList<>();
                rowDataList.add(String.join("|", importedData));
                if (needToGetInfoFromWeiXin) {
                    WxMpUser wxMpUser = null;
                    TWxMpUser tWxMpUser = tWxMpUserMapper.selectByPrimaryKey(openId);
                    if (tWxMpUser != null) {
                        wxMpUser = new WxMpUser();
                        BeanUtil.copyProperties(tWxMpUser, wxMpUser);
                    } else {
                        if (wxMpService != null) {
                            try {
                                wxMpUser = wxMpService.getUserService().userInfo(openId);
                                if (wxMpUser != null) {
                                    tWxMpUser = new TWxMpUser();
                                    BeanUtil.copyProperties(wxMpUser, tWxMpUser);
                                    tWxMpUserMapper.insertSelective(tWxMpUser);
                                }
                            } catch (Exception e) {
                                logger.error(e);
                            }
                        }
                    }

                    if (wxMpUser != null) {
                        if (memberForm.getImportOptionAvatarCheckBox().isSelected()) {
                            rowDataList.add(wxMpUser.getHeadImgUrl());
                        }
                        if (memberForm.getImportOptionBasicInfoCheckBox().isSelected()) {
                            rowDataList.add(wxMpUser.getNickname());
                            rowDataList.add(wxMpUser.getSexDesc());
                            rowDataList.add(wxMpUser.getCountry() + "-" + wxMpUser.getProvince() + "-" + wxMpUser.getCity());
                            rowDataList.add(DateFormatUtils.format(wxMpUser.getSubscribeTime() * 1000, "yyyy-MM-dd HH:mm:ss"));
                        }
                    } else {
                        if (memberForm.getImportOptionAvatarCheckBox().isSelected()) {
                            rowDataList.add("");
                        }
                        if (memberForm.getImportOptionBasicInfoCheckBox().isSelected()) {
                            rowDataList.add("");
                            rowDataList.add("");
                            rowDataList.add("");
                            rowDataList.add("");
                        }
                    }
                    rowDataList.add(openId);
                } else {
                    rowDataList.add(String.join("|", importedData));
                }

                model.addRow(rowDataList.toArray());
            } catch (Exception e) {
                logger.error(e);
            }
            progressBar.setValue(i + 1);
        }
    }

    /**
     * ??????????????????
     */
    public static void importFromFile() {
        MemberForm.getInstance().getImportFromFileButton().setEnabled(false);
        JTextField filePathField = MemberForm.getInstance().getMemberFilePathField();
        JPanel memberPanel = MemberForm.getInstance().getMemberPanel();
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        JLabel memberCountLabel = MemberForm.getInstance().getMemberTabCountLabel();

        if (StringUtils.isBlank(filePathField.getText())) {
            JOptionPane.showMessageDialog(memberPanel, "??????????????????????????????????????????????????????????????????", "??????",
                    JOptionPane.INFORMATION_MESSAGE);
            MemberForm.getInstance().getImportFromFileButton().setEnabled(true);
            return;
        }
        File file = new File(filePathField.getText());
        if (!file.exists()) {
            JOptionPane.showMessageDialog(memberPanel, filePathField.getText() + "\n?????????????????????", "???????????????",
                    JOptionPane.ERROR_MESSAGE);
            MemberForm.getInstance().getImportFromFileButton().setEnabled(true);
            return;
        }
        CSVReader reader = null;
        FileReader fileReader;

        int currentImported = 0;

        try {
            progressBar.setVisible(true);
            progressBar.setIndeterminate(true);
            String fileNameLowerCase = file.getName().toLowerCase();

            if (fileNameLowerCase.endsWith(".csv")) {
                // ??????????????????????????????
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                reader = new CSVReader(new InputStreamReader(in, FileCharSetUtil.getCharSet(file)));
                String[] nextLine;
                PushData.allUser = Collections.synchronizedList(new ArrayList<>());

                while ((nextLine = reader.readNext()) != null) {
                    PushData.allUser.add(nextLine);
                    currentImported++;
                    memberCountLabel.setText(String.valueOf(currentImported));
                }
            } else if (fileNameLowerCase.endsWith(".xlsx") || fileNameLowerCase.endsWith(".xls")) {
                ExcelReader excelReader = ExcelUtil.getReader(file);
                List<List<Object>> readAll = excelReader.read(1, Integer.MAX_VALUE);
                PushData.allUser = Collections.synchronizedList(new ArrayList<>());

                for (List<Object> objects : readAll) {
                    if (objects != null && objects.size() > 0) {
                        String[] nextLine = new String[objects.size()];
                        for (int i = 0; i < objects.size(); i++) {
                            nextLine[i] = objects.get(i).toString();
                        }
                        PushData.allUser.add(nextLine);
                        currentImported++;
                        memberCountLabel.setText(String.valueOf(currentImported));
                    }
                }
            } else if (fileNameLowerCase.endsWith(".txt")) {
                fileReader = new FileReader(file, FileCharSetUtil.getCharSetName(file));
                PushData.allUser = Collections.synchronizedList(new ArrayList<>());
                BufferedReader br = fileReader.getReader();
                String line;
                while ((line = br.readLine()) != null) {
                    PushData.allUser.add(line.split(TXT_FILE_DATA_SEPERATOR_REGEX));
                    currentImported++;
                    memberCountLabel.setText(String.valueOf(currentImported));
                }
            } else {
                JOptionPane.showMessageDialog(memberPanel, "??????????????????????????????", "?????????????????????",
                        JOptionPane.ERROR_MESSAGE);
                MemberForm.getInstance().getImportFromFileButton().setEnabled(true);
                return;
            }
            renderMemberListTable();

            if (!PushData.fixRateScheduling) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
            }

            App.config.setMemberFilePath(filePathField.getText());
            App.config.save();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(e1);
            e1.printStackTrace();
        } finally {
            progressBar.setMaximum(100);
            progressBar.setValue(100);
            progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
            MemberForm.getInstance().getImportFromFileButton().setEnabled(true);
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error(e1);
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * ??????SQL??????
     */
    public static void importFromSql() {
        MemberForm memberForm = MemberForm.getInstance();
        memberForm.getImportFromSqlButton().setEnabled(false);
        JPanel memberPanel = memberForm.getMemberPanel();
        JProgressBar progressBar = memberForm.getMemberTabImportProgressBar();
        JLabel memberCountLabel = memberForm.getMemberTabCountLabel();

        if (StringUtils.isBlank(App.config.getMysqlUrl()) || StringUtils.isBlank(App.config.getMysqlUser())) {
            JOptionPane.showMessageDialog(memberPanel, "?????????????????????????????????MySQL??????????????????", "??????",
                    JOptionPane.INFORMATION_MESSAGE);
            memberForm.getImportFromSqlButton().setEnabled(true);
            return;
        }
        String querySql = memberForm.getImportFromSqlTextArea().getText();
        if (StringUtils.isBlank(querySql)) {
            JOptionPane.showMessageDialog(memberPanel, "??????????????????????????????SQL???", "??????",
                    JOptionPane.INFORMATION_MESSAGE);
            memberForm.getImportFromSqlButton().setEnabled(true);
            return;
        }

        if (StringUtils.isNotEmpty(querySql)) {
            Connection conn = null;
            try {
                memberForm.getImportFromSqlButton().setEnabled(false);
                memberForm.getImportFromSqlButton().updateUI();
                progressBar.setVisible(true);
                progressBar.setIndeterminate(true);

                // ?????????
                PushData.allUser = Collections.synchronizedList(new ArrayList<>());
                int currentImported = 0;

                conn = HikariUtil.getConnection();
                List<Entity> entityList = SqlExecutor.query(conn, querySql, new EntityListHandler());
                for (Entity entity : entityList) {
                    Set<String> fieldNames = entity.getFieldNames();
                    String[] msgData = new String[fieldNames.size()];
                    int i = 0;
                    for (String fieldName : fieldNames) {
                        msgData[i] = entity.getStr(fieldName);
                        i++;
                    }
                    PushData.allUser.add(msgData);
                    currentImported++;
                    memberCountLabel.setText(String.valueOf(currentImported));
                }

                renderMemberListTable();
                if (!PushData.fixRateScheduling) {
                    JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
                }

                App.config.setMemberSql(querySql);
                App.config.save();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            } finally {
                DbUtil.close(conn);
                memberForm.getImportFromSqlButton().setEnabled(true);
                memberForm.getImportFromSqlButton().updateUI();
                progressBar.setMaximum(100);
                progressBar.setValue(100);
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                memberForm.getImportFromSqlButton().setEnabled(true);
            }
        }
    }

    /**
     * ??????????????????
     */
    public static void importWxAll() {
        JPanel memberPanel = MemberForm.getInstance().getMemberPanel();
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        MemberForm.getInstance().getMemberImportAllButton().setEnabled(false);

        try {
            getMpUserList();
            renderMemberListTable();
            if (!PushData.fixRateScheduling) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (WxErrorException e1) {
            JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(e1);
            e1.printStackTrace();
        } finally {
            progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
            MemberForm.getInstance().getMemberImportAllButton().setEnabled(true);
        }
    }

    /**
     * ???????????????????????????
     */
    public static void importWxCpAll() {
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        JLabel memberCountLabel = MemberForm.getInstance().getMemberTabCountLabel();
        JPanel memberPanel = MemberForm.getInstance().getMemberPanel();

        try {
            if (WxCpMsgForm.getInstance().getAppNameComboBox().getSelectedItem() == null) {
                JOptionPane.showMessageDialog(MainWindow.getInstance().getMessagePanel(), "?????????????????????tab??????????????????", "??????",
                        JOptionPane.ERROR_MESSAGE);
                MainWindow.getInstance().getTabbedPane().setSelectedIndex(2);
                return;
            }

            progressBar.setVisible(true);
            progressBar.setIndeterminate(true);
            int importedCount = 0;
            PushData.allUser = Collections.synchronizedList(new ArrayList<>());

            // ??????????????????id
            List<WxCpDepart> wxCpDepartList = WxCpMsgSender.getWxCpService().getDepartmentService().list(null);
            long minDeptId = Long.MAX_VALUE;
            for (WxCpDepart wxCpDepart : wxCpDepartList) {
                if (wxCpDepart.getId() < minDeptId) {
                    minDeptId = wxCpDepart.getId();
                }
            }
            // ????????????
            List<WxCpUser> wxCpUsers = WxCpMsgSender.getWxCpService().getUserService().listByDepartment(minDeptId, true, 0);
            for (WxCpUser wxCpUser : wxCpUsers) {
                String statusStr = "";
                if (wxCpUser.getStatus() == 1) {
                    statusStr = "?????????";
                } else if (wxCpUser.getStatus() == 2) {
                    statusStr = "?????????";
                } else if (wxCpUser.getStatus() == 4) {
                    statusStr = "?????????";
                }
                Long[] depIds = wxCpUser.getDepartIds();
                List<String> deptNameList = Lists.newArrayList();
                if (depIds != null) {
                    for (Long depId : depIds) {
                        deptNameList.add(wxCpIdToDeptNameMap.get(depId));
                    }
                }
                String[] dataArray = new String[]{wxCpUser.getUserId(), wxCpUser.getName(), wxCpUser.getGender().getGenderName(), wxCpUser.getEmail(), String.join("/", deptNameList), wxCpUser.getPosition(), statusStr};
                PushData.allUser.add(dataArray);
                importedCount++;
                memberCountLabel.setText(String.valueOf(importedCount));
            }
            renderMemberListTable();
            if (!PushData.fixRateScheduling) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(ex.toString());
        } finally {
            progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
        }
    }

    /**
     * ???????????????????????????
     */
    public static void importDingAll() {
        JProgressBar progressBar = MemberForm.getInstance().getMemberTabImportProgressBar();
        JLabel memberCountLabel = MemberForm.getInstance().getMemberTabCountLabel();
        JPanel memberPanel = MemberForm.getInstance().getMemberPanel();

        try {
            if (DingMsgForm.getInstance().getAppNameComboBox().getSelectedItem() == null) {
                JOptionPane.showMessageDialog(MainWindow.getInstance().getMessagePanel(), "?????????????????????tab??????????????????", "??????",
                        JOptionPane.ERROR_MESSAGE);
                MainWindow.getInstance().getTabbedPane().setSelectedIndex(2);
                return;
            }

            progressBar.setVisible(true);
            progressBar.setIndeterminate(true);
            int importedCount = 0;
            PushData.allUser = Collections.synchronizedList(new ArrayList<>());

            // ????????????id???1
            // ????????????
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
            OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
            request.setDepartmentId(1L);
            request.setOffset(0L);
            request.setSize(100L);
            request.setHttpMethod("GET");

            long offset = 0;
            OapiUserSimplelistResponse response = new OapiUserSimplelistResponse();
            while (response.getErrcode() == null || response.getUserlist().size() > 0) {
                response = client.execute(request, DingMsgSender.getAccessTokenTimedCache().get("accessToken"));
                if (response.getErrcode() != 0) {
                    if (response.getErrcode() == 60011) {
                        JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + response.getErrmsg() + "\n\n???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", "??????",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + response.getErrmsg(), "??????", JOptionPane.ERROR_MESSAGE);
                    }

                    logger.error(response.getErrmsg());
                    return;
                }
                List<OapiUserSimplelistResponse.Userlist> userlist = response.getUserlist();
                for (OapiUserSimplelistResponse.Userlist dingUser : userlist) {
                    String[] dataArray = new String[]{dingUser.getUserid(), dingUser.getName()};
                    PushData.allUser.add(dataArray);
                    importedCount++;
                    memberCountLabel.setText(String.valueOf(importedCount));
                }
                offset += 100;
                request.setOffset(offset);
            }

            renderMemberListTable();
            if (!PushData.fixRateScheduling) {
                JOptionPane.showMessageDialog(memberPanel, "???????????????", "??????", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(memberPanel, "???????????????\n\n" + ex, "??????",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(ex.toString());
        } finally {
            progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
        }
    }
}
