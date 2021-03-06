package com.fangxuele.tool.push.ui.form.msg;

import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.dao.TMsgMpTemplateMapper;
import com.fangxuele.tool.push.dao.TTemplateDataMapper;
import com.fangxuele.tool.push.domain.TMsgMpTemplate;
import com.fangxuele.tool.push.domain.TTemplateData;
import com.fangxuele.tool.push.logic.MessageTypeEnum;
import com.fangxuele.tool.push.logic.msgsender.WxMpTemplateMsgSender;
import com.fangxuele.tool.push.ui.component.TableInCellButtonColumn;
import com.fangxuele.tool.push.ui.form.MainWindow;
import com.fangxuele.tool.push.ui.form.MessageEditForm;
import com.fangxuele.tool.push.util.MybatisUtil;
import com.fangxuele.tool.push.util.SqliteUtil;
import com.fangxuele.tool.push.util.UIUtil;
import com.google.common.collect.Maps;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * MpTemplateMsgForm
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">Zhou Bo</a>
 * @since 2019/6/3.
 */
@Getter
@Slf4j
public class MpTemplateMsgForm implements IMsgForm {
    private JPanel templateMsgPanel;
    private JLabel templateIdLabel;
    private JTextField msgTemplateIdTextField;
    private JLabel templateUrlLabel;
    private JTextField msgTemplateUrlTextField;
    private JPanel templateMsgDataPanel;
    private JLabel templateMiniProgramAppidLabel;
    private JTextField msgTemplateMiniAppidTextField;
    private JLabel templateMiniProgramPagePathLabel;
    private JTextField msgTemplateMiniPagePathTextField;
    private JLabel templateMsgNameLabel;
    private JTextField templateDataNameTextField;
    private JLabel templateMsgValueLabel;
    private JTextField templateDataValueTextField;
    private JLabel templateMsgColorLabel;
    private JTextField templateDataColorTextField;
    private JButton templateMsgDataAddButton;
    private JTable templateMsgDataTable;
    private JComboBox templateListComboBox;
    private JButton refreshTemplateListButton;
    private JButton autoFillButton;
    private JTextArea templateContentTextArea;

    private static MpTemplateMsgForm mpTemplateMsgForm;

    private static TMsgMpTemplateMapper msgMpTemplateMapper = MybatisUtil.getSqlSession().getMapper(TMsgMpTemplateMapper.class);

    private static TTemplateDataMapper templateDataMapper = MybatisUtil.getSqlSession().getMapper(TTemplateDataMapper.class);

    /**
     * ??????????????????
     */
    public static List<WxMpTemplate> templateList;

    /**
     * ????????????map???key:templateId
     */
    public static Map<String, WxMpTemplate> templateMap;

    /**
     * ????????????????????????????????????????????????ID
     */
    public static String selectedMsgTemplateId;

    /**
     * ??????????????????????????????ComboBox
     */
    public static boolean needListenTemplateListComboBox = false;

    private static final Pattern BRACE_PATTERN = Pattern.compile("\\{([^{}]+)\\}");

    public MpTemplateMsgForm() {
        // ????????????-?????? ????????????
        templateMsgDataAddButton.addActionListener(e -> {
            String[] data = new String[3];
            data[0] = getInstance().getTemplateDataNameTextField().getText();
            data[1] = getInstance().getTemplateDataValueTextField().getText();
            data[2] = getInstance().getTemplateDataColorTextField().getText();

            if (getInstance().getTemplateMsgDataTable().getModel().getRowCount() == 0) {
                initTemplateDataTable();
            }

            DefaultTableModel tableModel = (DefaultTableModel) getInstance().getTemplateMsgDataTable()
                    .getModel();
            int rowCount = tableModel.getRowCount();

            Set<String> keySet = new HashSet<>();
            String keyData;
            for (int i = 0; i < rowCount; i++) {
                keyData = (String) tableModel.getValueAt(i, 0);
                keySet.add(keyData);
            }

            if (StringUtils.isEmpty(data[0]) || StringUtils.isEmpty(data[1])) {
                JOptionPane.showMessageDialog(MessageEditForm.getInstance().getMsgEditorPanel(), "Name???value???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (keySet.contains(data[0])) {
                JOptionPane.showMessageDialog(MessageEditForm.getInstance().getMsgEditorPanel(), "Name???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (StringUtils.isEmpty(data[2])) {
                    data[2] = "#000000";
                } else if (!data[2].startsWith("#")) {
                    data[2] = "#" + data[2];
                }
                tableModel.addRow(data);
            }
        });
        templateListComboBox.addItemListener(e -> {
            if (needListenTemplateListComboBox && e.getStateChange() == ItemEvent.SELECTED) {
                clearAllFieldExceptTemplateListAndContent();
                fillWxTemplateContentToField();
            }
        });
        autoFillButton.addActionListener(e -> autoFillTemplateDataTable());
        refreshTemplateListButton.addActionListener(e -> {
            initTemplateList();
            initTemplateDataTable();
        });
    }

    @Override
    public void init(String msgName) {
        MpTemplateMsgForm mpTemplateMsgForm = getInstance();
        if (UIUtil.isDarkLaf()) {
            Color bgColor = new Color(43, 43, 43);
            mpTemplateMsgForm.getTemplateContentTextArea().setBackground(bgColor);
            Color foreColor = new Color(187, 187, 187);
            mpTemplateMsgForm.getTemplateContentTextArea().setForeground(foreColor);
        }

        clearAllField();

        Integer msgId = 0;
        List<TMsgMpTemplate> tMsgMpTemplateList = msgMpTemplateMapper.selectByMsgTypeAndMsgName(MessageTypeEnum.MP_TEMPLATE_CODE, msgName);
        if (tMsgMpTemplateList.size() > 0) {
            TMsgMpTemplate tMsgMpTemplate = tMsgMpTemplateList.get(0);
            msgId = tMsgMpTemplate.getId();
            selectedMsgTemplateId = tMsgMpTemplate.getTemplateId();
            initTemplateList();

            mpTemplateMsgForm.getMsgTemplateIdTextField().setText(tMsgMpTemplate.getTemplateId());
            mpTemplateMsgForm.getMsgTemplateUrlTextField().setText(tMsgMpTemplate.getUrl());
            mpTemplateMsgForm.getMsgTemplateMiniAppidTextField().setText(tMsgMpTemplate.getMaAppid());
            mpTemplateMsgForm.getMsgTemplateMiniPagePathTextField().setText(tMsgMpTemplate.getMaPagePath());

            MessageEditForm messageEditForm = MessageEditForm.getInstance();
            messageEditForm.getMsgNameField().setText(tMsgMpTemplate.getMsgName());
            messageEditForm.getPreviewUserField().setText(tMsgMpTemplate.getPreviewUser());
        } else {
            initTemplateList();
        }

        initTemplateDataTable();
        fillTemplateDataTable(msgId);
    }

    @Override
    public void save(String msgName) {
        int msgId = 0;
        boolean existSameMsg = false;

        List<TMsgMpTemplate> tMsgMpTemplateList = msgMpTemplateMapper.selectByMsgTypeAndMsgName(MessageTypeEnum.MP_TEMPLATE_CODE, msgName);
        if (tMsgMpTemplateList.size() > 0) {
            existSameMsg = true;
            msgId = tMsgMpTemplateList.get(0).getId();
        }

        int isCover = JOptionPane.NO_OPTION;
        if (existSameMsg) {
            // ???????????????????????????
            isCover = JOptionPane.showConfirmDialog(MainWindow.getInstance().getMessagePanel(), "????????????????????????????????????\n???????????????", "??????",
                    JOptionPane.YES_NO_OPTION);
        }

        if (!existSameMsg || isCover == JOptionPane.YES_OPTION) {
            String templateId = getInstance().getMsgTemplateIdTextField().getText();
            String templateUrl = getInstance().getMsgTemplateUrlTextField().getText();
            String templateMiniAppid = getInstance().getMsgTemplateMiniAppidTextField().getText();
            String templateMiniPagePath = getInstance().getMsgTemplateMiniPagePathTextField().getText();

            String now = SqliteUtil.nowDateForSqlite();

            TMsgMpTemplate tMsgMpTemplate = new TMsgMpTemplate();
            tMsgMpTemplate.setMsgType(MessageTypeEnum.MP_TEMPLATE_CODE);
            tMsgMpTemplate.setMsgName(msgName);
            tMsgMpTemplate.setTemplateId(templateId);
            tMsgMpTemplate.setUrl(templateUrl);
            tMsgMpTemplate.setMaAppid(templateMiniAppid);
            tMsgMpTemplate.setMaPagePath(templateMiniPagePath);
            tMsgMpTemplate.setCreateTime(now);
            tMsgMpTemplate.setModifiedTime(now);

            MessageEditForm messageEditForm = MessageEditForm.getInstance();
            tMsgMpTemplate.setPreviewUser(messageEditForm.getPreviewUserField().getText());
            tMsgMpTemplate.setWxAccountId(App.config.getWxAccountId());

            if (existSameMsg) {
                msgMpTemplateMapper.updateByMsgTypeAndMsgName(tMsgMpTemplate);
            } else {
                msgMpTemplateMapper.insertSelective(tMsgMpTemplate);
                msgId = tMsgMpTemplate.getId();
            }

            // ??????????????????

            // ?????????????????????????????????????????????????????????
            if (existSameMsg) {
                templateDataMapper.deleteByMsgTypeAndMsgId(MessageTypeEnum.MP_TEMPLATE_CODE, msgId);
            }

            // ??????table?????????????????????
            if (getInstance().getTemplateMsgDataTable().getModel().getRowCount() == 0) {
                initTemplateDataTable();
            }

            // ????????????
            DefaultTableModel tableModel = (DefaultTableModel) getInstance().getTemplateMsgDataTable()
                    .getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String name = (String) tableModel.getValueAt(i, 0);
                String value = (String) tableModel.getValueAt(i, 1);
                String color = ((String) tableModel.getValueAt(i, 2)).trim();

                TTemplateData tTemplateData = new TTemplateData();
                tTemplateData.setMsgType(MessageTypeEnum.MP_TEMPLATE_CODE);
                tTemplateData.setMsgId(msgId);
                tTemplateData.setName(name);
                tTemplateData.setValue(value);
                tTemplateData.setColor(color);
                tTemplateData.setCreateTime(now);
                tTemplateData.setModifiedTime(now);

                templateDataMapper.insert(tTemplateData);
            }

            JOptionPane.showMessageDialog(MainWindow.getInstance().getMessagePanel(), "???????????????", "??????",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static MpTemplateMsgForm getInstance() {
        if (mpTemplateMsgForm == null) {
            mpTemplateMsgForm = new MpTemplateMsgForm();
        }
        return mpTemplateMsgForm;
    }

    /**
     * ?????????????????????Table(??????????????????)
     *
     * @param msgId
     */
    public static void fillTemplateDataTable(Integer msgId) {
        // ????????????Data???
        List<TTemplateData> templateDataList = templateDataMapper.selectByMsgTypeAndMsgId(MessageTypeEnum.MP_TEMPLATE_CODE, msgId);
        String[] headerNames = {"Name", "Value", "Color", "??????"};
        Object[][] cellData = new String[templateDataList.size()][headerNames.length];
        for (int i = 0; i < templateDataList.size(); i++) {
            TTemplateData tTemplateData = templateDataList.get(i);
            cellData[i][0] = tTemplateData.getName();
            cellData[i][1] = tTemplateData.getValue();
            cellData[i][2] = tTemplateData.getColor();
        }
        DefaultTableModel model = new DefaultTableModel(cellData, headerNames);
        getInstance().getTemplateMsgDataTable().setModel(model);
        TableColumnModel tableColumnModel = getInstance().getTemplateMsgDataTable().getColumnModel();
        tableColumnModel.getColumn(headerNames.length - 1).
                setCellRenderer(new TableInCellButtonColumn(getInstance().getTemplateMsgDataTable(), headerNames.length - 1));
        tableColumnModel.getColumn(headerNames.length - 1).
                setCellEditor(new TableInCellButtonColumn(getInstance().getTemplateMsgDataTable(), headerNames.length - 1));

        // ????????????
        tableColumnModel.getColumn(3).setPreferredWidth(getInstance().getTemplateMsgDataAddButton().getWidth());
        tableColumnModel.getColumn(3).setMaxWidth(getInstance().getTemplateMsgDataAddButton().getWidth());
    }

    /**
     * ?????????????????????ComboBox
     */
    public static void initTemplateList() {
        needListenTemplateListComboBox = false;
        try {
            templateMap = Maps.newHashMap();
            templateList = WxMpTemplateMsgSender.getWxMpService().getTemplateMsgService().getAllPrivateTemplate();
            getInstance().getTemplateListComboBox().removeAllItems();
            int selectedIndex = 0;
            for (int i = 0; i < templateList.size(); i++) {
                WxMpTemplate wxMpTemplate = templateList.get(i);
                getInstance().getTemplateListComboBox().addItem(wxMpTemplate.getTitle());
                templateMap.put(wxMpTemplate.getTemplateId(), wxMpTemplate);
                if (wxMpTemplate.getTemplateId().equals(selectedMsgTemplateId)) {
                    selectedIndex = i;
                }
            }

            if (getInstance().getTemplateListComboBox().getItemCount() > 0) {
                getInstance().getTemplateListComboBox().setSelectedIndex(selectedIndex);
                fillWxTemplateContentToField();
            }

        } catch (Exception e) {
            log.error(e.toString());
        }
        needListenTemplateListComboBox = true;
    }

    /**
     * ??????????????????????????????????????????list
     *
     * @param templateContent ????????????
     * @return params ??????????????????
     */
    public static List<String> getTemplateParams(String templateContent) {
        List<String> params = Lists.newArrayList();
        Matcher matcher = BRACE_PATTERN.matcher(templateContent);
        while (matcher.find()) {
            params.add(matcher.group(1).replace(".DATA", ""));
        }
        return params;
    }

    /**
     * ????????????id??????????????????????????????WxTemplate???????????????
     */
    public static void fillWxTemplateContentToField() {
        WxMpTemplate wxMpTemplate = templateList.get(getInstance().getTemplateListComboBox().getSelectedIndex());
        if (wxMpTemplate != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("-----------??????ID-----------\n").append(wxMpTemplate.getTemplateId()).append("\n");
            stringBuilder.append("\n----------????????????----------\n").append(wxMpTemplate.getPrimaryIndustry()).append("-").append(wxMpTemplate.getDeputyIndustry()).append("\n");
            stringBuilder.append("\n----------????????????----------\n").append(wxMpTemplate.getContent()).append("\n");
            stringBuilder.append("\n----------????????????----------\n").append(wxMpTemplate.getExample());
            getInstance().getTemplateContentTextArea().setText(stringBuilder.toString());
            getInstance().getMsgTemplateIdTextField().setText(wxMpTemplate.getTemplateId());
        }
    }

    /**
     * ????????????????????????Table
     */
    private static void autoFillTemplateDataTable() {
        if (templateList != null) {
            WxMpTemplate wxMpTemplate = templateList.get(getInstance().getTemplateListComboBox().getSelectedIndex());
            if (wxMpTemplate != null) {
                initTemplateDataTable();
                DefaultTableModel tableModel = (DefaultTableModel) getInstance().getTemplateMsgDataTable()
                        .getModel();
                List<String> params = getTemplateParams(wxMpTemplate.getContent());
                for (int i = 0; i < params.size(); i++) {
                    String param = params.get(i);
                    String[] data = new String[3];
                    data[0] = param;
                    data[1] = "?????????" + (i + 1);
                    data[2] = "#000000";
                    tableModel.addRow(data);
                }
            }
        }
    }

    /**
     * ???????????????????????????table
     */
    public static void initTemplateDataTable() {
        JTable msgDataTable = getInstance().getTemplateMsgDataTable();
        String[] headerNames = {"Name", "Value", "Color", "??????"};
        DefaultTableModel model = new DefaultTableModel(null, headerNames);
        msgDataTable.setModel(model);
        msgDataTable.updateUI();
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) msgDataTable.getTableHeader().getDefaultRenderer();
        // ??????????????????
        hr.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);

        TableColumnModel tableColumnModel = msgDataTable.getColumnModel();
        tableColumnModel.getColumn(headerNames.length - 1).
                setCellRenderer(new TableInCellButtonColumn(msgDataTable, headerNames.length - 1));
        tableColumnModel.getColumn(headerNames.length - 1).
                setCellEditor(new TableInCellButtonColumn(msgDataTable, headerNames.length - 1));

        // ????????????
        tableColumnModel.getColumn(3).setPreferredWidth(getInstance().getTemplateMsgDataAddButton().getWidth());
        tableColumnModel.getColumn(3).setMaxWidth(getInstance().getTemplateMsgDataAddButton().getWidth());
    }

    /**
     * ????????????????????????
     */
    public static void clearAllField() {
        clearAllFieldExceptTemplateListAndContent();
        getInstance().getTemplateListComboBox().removeAllItems();
        getInstance().getTemplateContentTextArea().setText("");
    }

    /**
     * ????????????????????????
     */
    public static void clearAllFieldExceptTemplateListAndContent() {
        getInstance().getMsgTemplateIdTextField().setText("");
        getInstance().getMsgTemplateUrlTextField().setText("");
        getInstance().getMsgTemplateMiniAppidTextField().setText("");
        getInstance().getMsgTemplateMiniPagePathTextField().setText("");
        getInstance().getTemplateDataNameTextField().setText("");
        getInstance().getTemplateDataValueTextField().setText("");
        getInstance().getTemplateDataColorTextField().setText("");
        selectedMsgTemplateId = null;
        initTemplateDataTable();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        templateMsgPanel = new JPanel();
        templateMsgPanel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 15, 0, 0), -1, -1));
        panel1.add(templateMsgPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        templateMsgPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "?????????-??????????????????", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, templateMsgPanel.getFont()), null));
        templateMsgDataPanel = new JPanel();
        templateMsgDataPanel.setLayout(new GridLayoutManager(3, 4, new Insets(10, 0, 0, 0), -1, -1));
        templateMsgPanel.add(templateMsgDataPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        templateMsgDataPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "????????????????????????\"${ENTER}\"??????????????????", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, templateMsgDataPanel.getFont()), null));
        templateDataNameTextField = new JTextField();
        templateDataNameTextField.setToolTipText("?????????????????????????????????????????????first??????keyword1??????remark?????????");
        templateMsgDataPanel.add(templateDataNameTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateDataValueTextField = new JTextField();
        templateMsgDataPanel.add(templateDataValueTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateDataColorTextField = new JTextField();
        templateDataColorTextField.setToolTipText("????????????FF0000");
        templateMsgDataPanel.add(templateDataColorTextField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateMsgDataAddButton = new JButton();
        templateMsgDataAddButton.setIcon(new ImageIcon(getClass().getResource("/icon/add.png")));
        templateMsgDataAddButton.setText("");
        templateMsgDataPanel.add(templateMsgDataAddButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateMsgDataTable = new JTable();
        templateMsgDataTable.setAutoCreateColumnsFromModel(true);
        templateMsgDataTable.setAutoCreateRowSorter(true);
        templateMsgDataTable.setGridColor(new Color(-12236470));
        templateMsgDataTable.setRowHeight(36);
        templateMsgDataPanel.add(templateMsgDataTable, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        templateMsgNameLabel = new JLabel();
        templateMsgNameLabel.setText("name");
        templateMsgNameLabel.setToolTipText("?????????????????????????????????????????????first??????keyword1??????remark?????????");
        templateMsgDataPanel.add(templateMsgNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateMsgValueLabel = new JLabel();
        templateMsgValueLabel.setText("value");
        templateMsgDataPanel.add(templateMsgValueLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateMsgColorLabel = new JLabel();
        templateMsgColorLabel.setText("color");
        templateMsgColorLabel.setToolTipText("????????????FF0000");
        templateMsgDataPanel.add(templateMsgColorLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoFillButton = new JButton();
        autoFillButton.setText("????????????");
        templateMsgDataPanel.add(autoFillButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 5, 0), -1, -1));
        templateMsgPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        templateIdLabel = new JLabel();
        templateIdLabel.setText("??????ID *");
        panel2.add(templateIdLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        msgTemplateIdTextField = new JTextField();
        panel2.add(msgTemplateIdTextField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        templateUrlLabel = new JLabel();
        templateUrlLabel.setText("??????URL");
        panel2.add(templateUrlLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        msgTemplateUrlTextField = new JTextField();
        panel2.add(msgTemplateUrlTextField, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        templateMiniProgramAppidLabel = new JLabel();
        templateMiniProgramAppidLabel.setText("?????????appid");
        templateMiniProgramAppidLabel.setToolTipText("?????????");
        panel2.add(templateMiniProgramAppidLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        msgTemplateMiniAppidTextField = new JTextField();
        msgTemplateMiniAppidTextField.setText("");
        msgTemplateMiniAppidTextField.setToolTipText("?????????");
        panel2.add(msgTemplateMiniAppidTextField, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        templateMiniProgramPagePathLabel = new JLabel();
        templateMiniProgramPagePathLabel.setText("?????????????????????");
        templateMiniProgramPagePathLabel.setToolTipText("?????????");
        panel2.add(templateMiniProgramPagePathLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        msgTemplateMiniPagePathTextField = new JTextField();
        msgTemplateMiniPagePathTextField.setText("");
        msgTemplateMiniPagePathTextField.setToolTipText("?????????");
        panel2.add(msgTemplateMiniPagePathTextField, new GridConstraints(5, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("????????????");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateListComboBox = new JComboBox();
        panel2.add(templateListComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshTemplateListButton = new JButton();
        refreshTemplateListButton.setIcon(new ImageIcon(getClass().getResource("/icon/refresh.png")));
        refreshTemplateListButton.setText("??????");
        panel2.add(refreshTemplateListButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateContentTextArea = new JTextArea();
        templateContentTextArea.setEditable(false);
        panel2.add(templateContentTextArea, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        templateMsgNameLabel.setLabelFor(templateDataNameTextField);
        templateMsgValueLabel.setLabelFor(templateDataValueTextField);
        templateMsgColorLabel.setLabelFor(templateDataColorTextField);
        templateIdLabel.setLabelFor(msgTemplateIdTextField);
        templateUrlLabel.setLabelFor(msgTemplateUrlTextField);
        templateMiniProgramAppidLabel.setLabelFor(msgTemplateMiniAppidTextField);
        templateMiniProgramPagePathLabel.setLabelFor(msgTemplateMiniPagePathTextField);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

}
