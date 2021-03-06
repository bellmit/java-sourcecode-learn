package com.fangxuele.tool.push.ui.listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.dao.TWxAccountMapper;
import com.fangxuele.tool.push.domain.TWxAccount;
import com.fangxuele.tool.push.logic.msgsender.AliYunMsgSender;
import com.fangxuele.tool.push.logic.msgsender.BdYunMsgSender;
import com.fangxuele.tool.push.logic.msgsender.HttpMsgSender;
import com.fangxuele.tool.push.logic.msgsender.HwYunMsgSender;
import com.fangxuele.tool.push.logic.msgsender.MailMsgSender;
import com.fangxuele.tool.push.logic.msgsender.QiNiuYunMsgSender;
import com.fangxuele.tool.push.logic.msgsender.TxYunMsgSender;
import com.fangxuele.tool.push.logic.msgsender.WxMaSubscribeMsgSender;
import com.fangxuele.tool.push.logic.msgsender.WxMpTemplateMsgSender;
import com.fangxuele.tool.push.logic.msgsender.YunPianMsgSender;
import com.fangxuele.tool.push.ui.Init;
import com.fangxuele.tool.push.ui.UiConsts;
import com.fangxuele.tool.push.ui.dialog.CommonTipsDialog;
import com.fangxuele.tool.push.ui.dialog.DingAppDialog;
import com.fangxuele.tool.push.ui.dialog.MailTestDialog;
import com.fangxuele.tool.push.ui.dialog.SwitchWxAccountDialog;
import com.fangxuele.tool.push.ui.dialog.SystemEnvResultDialog;
import com.fangxuele.tool.push.ui.dialog.WxCpAppDialog;
import com.fangxuele.tool.push.ui.form.MessageManageForm;
import com.fangxuele.tool.push.ui.form.SettingForm;
import com.fangxuele.tool.push.ui.form.msg.DingMsgForm;
import com.fangxuele.tool.push.ui.form.msg.WxCpMsgForm;
import com.fangxuele.tool.push.util.HikariUtil;
import com.fangxuele.tool.push.util.MybatisUtil;
import com.fangxuele.tool.push.util.SqliteUtil;
import com.fangxuele.tool.push.util.SystemUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * <pre>
 * ??????tab??????????????????
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2017/6/16.
 */
public class SettingListener {
    private static final Log logger = LogFactory.get();

    public static String wxAccountType;

    private static TWxAccountMapper wxAccountMapper = MybatisUtil.getSqlSession().getMapper(TWxAccountMapper.class);

    public static void addListeners() {
        SettingForm settingForm = SettingForm.getInstance();
        JPanel settingPanel = settingForm.getSettingPanel();

        // ??????-??????-???????????????????????????
        settingForm.getAutoCheckUpdateCheckBox().addActionListener(e -> {
            App.config.setAutoCheckUpdate(settingForm.getAutoCheckUpdateCheckBox().isSelected());
            App.config.save();
        });
        // ??????-??????-????????????????????????
        settingForm.getUseTrayCheckBox().addActionListener(e -> {
            App.config.setUseTray(settingForm.getUseTrayCheckBox().isSelected());
            App.config.save();
            if (App.tray == null && App.config.isUseTray()) {
                Init.initTray();
            } else if (App.tray != null && !App.config.isUseTray()) {
                App.tray.remove(App.trayIcon);
                App.trayIcon = null;
                App.tray = null;
            }
        });
        // ??????-??????-???????????????????????????????????????
        settingForm.getCloseToTrayCheckBox().addActionListener(e -> {
            App.config.setCloseToTray(settingForm.getCloseToTrayCheckBox().isSelected());
            App.config.save();
        });
        // ??????-??????-?????????????????????
        settingForm.getDefaultMaxWindowCheckBox().addActionListener(e -> {
            App.config.setDefaultMaxWindow(settingForm.getDefaultMaxWindowCheckBox().isSelected());
            App.config.save();
        });

        // ??????-??????-???????????????
        settingForm.getMaxThreadsSaveButton().addActionListener(e -> {
            try {
                App.config.setMaxThreads(Integer.valueOf(settingForm.getMaxThreadsTextField().getText()));
                App.config.save();
                PushListener.refreshPushInfo();

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-?????????-??????
        settingForm.getSettingMpInfoSaveButton().addActionListener(e -> {
            try {
                String accountName;
                if (settingForm.getMpAccountSwitchComboBox().getSelectedItem() == null || StringUtils.isEmpty(settingForm.getMpAccountSwitchComboBox().getSelectedItem().toString())) {
                    accountName = "????????????";
                } else {
                    accountName = settingForm.getMpAccountSwitchComboBox().getSelectedItem().toString();
                }
                App.config.setWechatMpName(accountName);
                App.config.setWechatAppId(settingForm.getWechatAppIdTextField().getText());
                App.config.setWechatAppSecret(new String(settingForm.getWechatAppSecretPasswordField().getPassword()));
                App.config.setWechatToken(new String(settingForm.getWechatTokenPasswordField().getPassword()));
                App.config.setWechatAesKey(new String(settingForm.getWechatAesKeyPasswordField().getPassword()));

                App.config.setMpUseProxy(settingForm.getMpUseProxyCheckBox().isSelected());
                App.config.setMpProxyHost(settingForm.getMpProxyHostTextField().getText());
                App.config.setMpProxyPort(settingForm.getMpProxyPortTextField().getText());
                App.config.setMpProxyUserName(settingForm.getMpProxyUserNameTextField().getText());
                App.config.setMpProxyPassword(settingForm.getMpProxyPasswordTextField().getText());

                App.config.setMpUseOutSideAt(settingForm.getUseOutSideAccessTokenCheckBox().isSelected());
                App.config.setMpManualAt(settingForm.getManualAtRadioButton().isSelected());
                App.config.setMpApiAt(settingForm.getApiAtRadioButton().isSelected());
                App.config.setMpAt(settingForm.getAccessTokenTextField().getText());
                App.config.setMpAtExpiresIn(settingForm.getAtExpiresInTextField().getText());
                App.config.setMpAtApiUrl(settingForm.getAtApiUrlTextField().getText());

                App.config.save();

                boolean update = false;
                List<TWxAccount> tWxAccountList = wxAccountMapper.selectByAccountTypeAndAccountName(UiConsts.WX_ACCOUNT_TYPE_MP, accountName);
                if (tWxAccountList.size() > 0) {
                    update = true;
                }

                TWxAccount tWxAccount = new TWxAccount();
                String now = SqliteUtil.nowDateForSqlite();
                tWxAccount.setAccountType(UiConsts.WX_ACCOUNT_TYPE_MP);
                tWxAccount.setAccountName(accountName);
                tWxAccount.setAppId(App.config.getWechatAppId());
                tWxAccount.setAppSecret(App.config.getWechatAppSecret());
                tWxAccount.setToken(App.config.getWechatToken());
                tWxAccount.setAesKey(App.config.getWechatAesKey());
                tWxAccount.setModifiedTime(now);
                if (update) {
                    tWxAccount.setId(tWxAccountList.get(0).getId());
                    wxAccountMapper.updateByPrimaryKeySelective(tWxAccount);
                } else {
                    tWxAccount.setCreateTime(now);
                    wxAccountMapper.insert(tWxAccount);
                }

                SettingForm.initSwitchMultiAccount();
                MessageManageForm.initSwitchMultiAccount();
                WxMpTemplateMsgSender.wxMpConfigStorage = null;
                WxMpTemplateMsgSender.wxMpService = null;
                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-?????????-???????????????
        settingForm.getMpAccountManageButton().addActionListener(e -> {
            SwitchWxAccountDialog dialog = new SwitchWxAccountDialog();
            wxAccountType = UiConsts.WX_ACCOUNT_TYPE_MP;
            dialog.renderTable();
            dialog.pack();
            dialog.setVisible(true);
        });

        // ???????????????????????????
        settingForm.getMpAccountSwitchComboBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String accountName = e.getItem().toString();
                List<TWxAccount> wxAccountList = wxAccountMapper.selectByAccountTypeAndAccountName(UiConsts.WX_ACCOUNT_TYPE_MP, accountName);
                if (wxAccountList.size() > 0) {
                    TWxAccount tWxAccount = wxAccountList.get(0);
                    settingForm.getMpAccountSwitchComboBox().setSelectedItem(tWxAccount.getAccountName());
                    settingForm.getWechatAppIdTextField().setText(tWxAccount.getAppId());
                    settingForm.getWechatAppSecretPasswordField().setText(tWxAccount.getAppSecret());
                    settingForm.getWechatTokenPasswordField().setText(tWxAccount.getToken());
                    settingForm.getWechatAesKeyPasswordField().setText(tWxAccount.getAesKey());

                    App.config.setWechatMpName(tWxAccount.getAccountName());
                    App.config.setWechatAppId(settingForm.getWechatAppIdTextField().getText());
                    App.config.setWechatAppSecret(new String(settingForm.getWechatAppSecretPasswordField().getPassword()));
                    App.config.setWechatToken(new String(settingForm.getWechatTokenPasswordField().getPassword()));
                    App.config.setWechatAesKey(new String(settingForm.getWechatAesKeyPasswordField().getPassword()));

                    App.config.setMpUseProxy(settingForm.getMpUseProxyCheckBox().isSelected());
                    App.config.setMpProxyHost(settingForm.getMpProxyHostTextField().getText());
                    App.config.setMpProxyPort(settingForm.getMpProxyPortTextField().getText());
                    App.config.setMpProxyUserName(settingForm.getMpProxyUserNameTextField().getText());
                    App.config.setMpProxyPassword(settingForm.getMpProxyPasswordTextField().getText());

                    App.config.setMpUseOutSideAt(settingForm.getUseOutSideAccessTokenCheckBox().isSelected());
                    App.config.setMpManualAt(settingForm.getManualAtRadioButton().isSelected());
                    App.config.setMpApiAt(settingForm.getApiAtRadioButton().isSelected());
                    App.config.setMpAt(settingForm.getAccessTokenTextField().getText());
                    App.config.setMpAtExpiresIn(settingForm.getAtExpiresInTextField().getText());
                    App.config.setMpAtApiUrl(settingForm.getAtApiUrlTextField().getText());

                    App.config.setWxAccountId(tWxAccount.getId());
                    App.config.save();
                    MessageManageForm.getInstance().getAccountSwitchComboBox().setSelectedItem(tWxAccount.getAccountName());
                    WxMpTemplateMsgSender.wxMpConfigStorage = null;
                    WxMpTemplateMsgSender.wxMpService = null;
                }
            }
        });

        // ??????-?????????-??????
        settingForm.getSettingMaInfoSaveButton().addActionListener(e -> {
            try {
                String accountName;
                if (settingForm.getMaAccountSwitchComboBox().getSelectedItem() == null || StringUtils.isEmpty(settingForm.getMaAccountSwitchComboBox().getSelectedItem().toString())) {
                    accountName = "????????????";
                } else {
                    accountName = settingForm.getMaAccountSwitchComboBox().getSelectedItem().toString();
                }
                App.config.setMiniAppName(accountName);
                App.config.setMiniAppAppId(settingForm.getMiniAppAppIdTextField().getText());
                App.config.setMiniAppAppSecret(new String(settingForm.getMiniAppAppSecretPasswordField().getPassword()));
                App.config.setMiniAppToken(new String(settingForm.getMiniAppTokenPasswordField().getPassword()));
                App.config.setMiniAppAesKey(new String(settingForm.getMiniAppAesKeyPasswordField().getPassword()));

                App.config.setMaUseProxy(settingForm.getMaUseProxyCheckBox().isSelected());
                App.config.setMaProxyHost(settingForm.getMaProxyHostTextField().getText());
                App.config.setMaProxyPort(settingForm.getMaProxyPortTextField().getText());
                App.config.setMaProxyUserName(settingForm.getMaProxyUserNameTextField().getText());
                App.config.setMaProxyPassword(settingForm.getMaProxyPasswordTextField().getText());
                App.config.save();

                boolean update = false;
                List<TWxAccount> tWxAccountList = wxAccountMapper.selectByAccountTypeAndAccountName(UiConsts.WX_ACCOUNT_TYPE_MA, accountName);
                if (tWxAccountList.size() > 0) {
                    update = true;
                }

                TWxAccount tWxAccount = new TWxAccount();
                String now = SqliteUtil.nowDateForSqlite();
                tWxAccount.setAccountType(UiConsts.WX_ACCOUNT_TYPE_MA);
                tWxAccount.setAccountName(accountName);
                tWxAccount.setAppId(App.config.getMiniAppAppId());
                tWxAccount.setAppSecret(App.config.getMiniAppAppSecret());
                tWxAccount.setToken(App.config.getMiniAppToken());
                tWxAccount.setAesKey(App.config.getMiniAppAesKey());
                tWxAccount.setModifiedTime(now);
                if (update) {
                    tWxAccount.setId(tWxAccountList.get(0).getId());
                    wxAccountMapper.updateByPrimaryKeySelective(tWxAccount);
                } else {
                    tWxAccount.setCreateTime(now);
                    wxAccountMapper.insert(tWxAccount);
                }

                SettingForm.initSwitchMultiAccount();
                WxMaSubscribeMsgSender.wxMaConfigStorage = null;
                WxMaSubscribeMsgSender.wxMaService = null;
                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-?????????-???????????????
        settingForm.getMaAccountManageButton().addActionListener(e -> {
            SwitchWxAccountDialog dialog = new SwitchWxAccountDialog();
            wxAccountType = UiConsts.WX_ACCOUNT_TYPE_MA;
            dialog.renderTable();
            dialog.pack();
            dialog.setVisible(true);
        });

        // ???????????????????????????
        settingForm.getMaAccountSwitchComboBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String accountName = e.getItem().toString();
                List<TWxAccount> wxAccountList = wxAccountMapper.selectByAccountTypeAndAccountName(UiConsts.WX_ACCOUNT_TYPE_MA, accountName);
                if (wxAccountList.size() > 0) {
                    TWxAccount tWxAccount = wxAccountList.get(0);
                    settingForm.getMaAccountSwitchComboBox().setSelectedItem(tWxAccount.getAccountName());
                    settingForm.getMiniAppAppIdTextField().setText(tWxAccount.getAppId());
                    settingForm.getMiniAppAppSecretPasswordField().setText(tWxAccount.getAppSecret());
                    settingForm.getMiniAppTokenPasswordField().setText(tWxAccount.getToken());
                    settingForm.getMiniAppAesKeyPasswordField().setText(tWxAccount.getAesKey());

                    App.config.setMiniAppName(accountName);
                    App.config.setMiniAppAppId(settingForm.getMiniAppAppIdTextField().getText());
                    App.config.setMiniAppAppSecret(new String(settingForm.getMiniAppAppSecretPasswordField().getPassword()));
                    App.config.setMiniAppToken(new String(settingForm.getMiniAppTokenPasswordField().getPassword()));
                    App.config.setMiniAppAesKey(new String(settingForm.getMiniAppAesKeyPasswordField().getPassword()));

                    App.config.setMaUseProxy(settingForm.getMaUseProxyCheckBox().isSelected());
                    App.config.setMaProxyHost(settingForm.getMaProxyHostTextField().getText());
                    App.config.setMaProxyPort(settingForm.getMaProxyPortTextField().getText());
                    App.config.setMaProxyUserName(settingForm.getMaProxyUserNameTextField().getText());
                    App.config.setMaProxyPassword(settingForm.getMaProxyPasswordTextField().getText());

                    App.config.setWxAccountId(tWxAccount.getId());
                    App.config.save();
                    MessageManageForm.getInstance().getAccountSwitchComboBox().setSelectedItem(tWxAccount.getAccountName());
                    WxMaSubscribeMsgSender.wxMaConfigStorage = null;
                    WxMaSubscribeMsgSender.wxMaService = null;
                }
            }
        });

        // ?????????-??????
        settingForm.getWxCpSaveButton().addActionListener(e -> {
            try {
                App.config.setWxCpCorpId(settingForm.getWxCpCorpIdTextField().getText());
                App.config.save();

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-?????????-????????????
        settingForm.getWxCpAppManageButton().addActionListener(e -> {
            WxCpAppDialog dialog = new WxCpAppDialog();
            dialog.renderTable();
            dialog.pack();
            dialog.setVisible(true);
            WxCpMsgForm.initAppNameList();
        });

        // ??????-??????-????????????
        settingForm.getDingAppManageButton().addActionListener(e -> {
            DingAppDialog dialog = new DingAppDialog();
            dialog.renderTable();
            dialog.pack();
            dialog.setVisible(true);
            DingMsgForm.initAppNameList();
        });

        // ??????-???????????????-??????
        settingForm.getSettingAliyunSaveButton().addActionListener(e -> {
            try {
                App.config.setAliyunAccessKeyId(settingForm.getAliyunAccessKeyIdTextField().getText());
                App.config.setAliyunAccessKeySecret(new String(settingForm.getAliyunAccessKeySecretTextField().getPassword()));
                App.config.setAliyunSign(settingForm.getAliyunSignTextField().getText());
                App.config.save();
                AliYunMsgSender.iAcsClient = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-???????????????-??????
        settingForm.getSettingTxyunSaveButton().addActionListener(e -> {
            try {
                App.config.setTxyunAppId(settingForm.getTxyunAppIdTextField().getText());
                App.config.setTxyunAppKey(new String(settingForm.getTxyunAppKeyTextField().getPassword()));
                App.config.setTxyunSign(settingForm.getTxyunSignTextField().getText());
                App.config.save();

                TxYunMsgSender.smsSingleSender = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-???????????????-??????
        settingForm.getHwSaveButton().addActionListener(e -> {
            try {
                App.config.setHwAppKey(settingForm.getHwAppKeyTextField().getText());
                App.config.setHwAppSecretPassword(new String(settingForm.getHwAppSecretPasswordField().getPassword()));
                App.config.setHwAccessUrl(settingForm.getHwAccessUrlTextField().getText());
                App.config.setHwSenderCode(settingForm.getHwSenderCodeTextField().getText());
                App.config.setHwSignature(settingForm.getHwSignatureTextField().getText());
                App.config.save();

                HwYunMsgSender.closeableHttpClient = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-???????????????-??????
        settingForm.getBdSaveButton().addActionListener(e -> {
            try {
                App.config.setBdAccessKeyId(settingForm.getBdAccessKeyIdTextField().getText());
                App.config.setBdSecretAccessKey(new String(settingForm.getBdSecretAccessKeyPasswordField().getPassword()));
                App.config.setBdEndPoint(settingForm.getBdEndPointTextField().getText());
                App.config.setBdInvokeId(settingForm.getBdInvokeIdTextField().getText());
                App.config.save();

                BdYunMsgSender.smsClient = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-???????????????-??????
        settingForm.getUpSaveButton().addActionListener(e -> {
            try {
                App.config.setUpAuthorizationToken(settingForm.getUpAuthorizationTokenTextField().getText());
                App.config.save();

                HttpMsgSender.okHttpClient = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-???????????????-??????
        settingForm.getQiniuSaveButton().addActionListener(e -> {
            try {
                App.config.setQiniuAccessKey(settingForm.getQiniuAccessKeyTextField().getText());
                App.config.setQiniuSecretKey(settingForm.getQiniuSecretKeyTextField().getText());
                App.config.save();

                QiNiuYunMsgSender.smsManager = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-???????????????-??????
        settingForm.getSettingYunpianSaveButton().addActionListener(e -> {
            try {
                App.config.setYunpianApiKey(new String(settingForm.getYunpianApiKeyTextField().getPassword()));
                App.config.save();
                YunPianMsgSender.yunpianClient = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        settingForm.getHttpSaveButton().addActionListener(e -> {
            try {
                App.config.setHttpUseProxy(settingForm.getHttpUseProxyCheckBox().isSelected());
                App.config.setHttpProxyHost(settingForm.getHttpProxyHostTextField().getText());
                App.config.setHttpProxyPort(settingForm.getHttpProxyPortTextField().getText());
                App.config.setHttpProxyUserName(settingForm.getHttpProxyUserTextField().getText());
                App.config.setHttpProxyPassword(settingForm.getHttpProxyPasswordTextField().getText());
                App.config.save();

                HttpMsgSender.proxy = null;
                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // E-Mail??????
        settingForm.getTestMailButton().addActionListener(e -> {
            App.config.setMailHost(settingForm.getMailHostTextField().getText());
            App.config.setMailPort(settingForm.getMailPortTextField().getText());
            App.config.setMailFrom(settingForm.getMailFromTextField().getText());
            App.config.setMailUser(settingForm.getMailUserTextField().getText());
            App.config.setMailPassword(new String(settingForm.getMailPasswordField().getPassword()));
            App.config.setMailUseStartTLS(settingForm.getMailStartTLSCheckBox().isSelected());
            App.config.setMailUseSSL(settingForm.getMailSSLCheckBox().isSelected());
            MailMsgSender.mailAccount = null;

            MailTestDialog mailTestDialog = new MailTestDialog();
            mailTestDialog.pack();
            mailTestDialog.setVisible(true);
        });

        // E-Mail??????
        settingForm.getSaveMailButton().addActionListener(e -> {
            try {
                App.config.setMailHost(settingForm.getMailHostTextField().getText());
                App.config.setMailPort(settingForm.getMailPortTextField().getText());
                App.config.setMailFrom(settingForm.getMailFromTextField().getText());
                App.config.setMailUser(settingForm.getMailUserTextField().getText());
                App.config.setMailPassword(new String(settingForm.getMailPasswordField().getPassword()));
                App.config.setMailUseStartTLS(settingForm.getMailStartTLSCheckBox().isSelected());
                App.config.setMailUseSSL(settingForm.getMailSSLCheckBox().isSelected());
                App.config.save();

                MailMsgSender.mailAccount = null;

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // mysql?????????-????????????
        settingForm.getSettingTestDbLinkButton().addActionListener(e -> {
            HikariDataSource hikariDataSource = null;
            try {
                String dbUrl = settingForm.getMysqlUrlTextField().getText();
                String dbUser = settingForm.getMysqlUserTextField().getText();
                String dbPassword = new String(settingForm.getMysqlPasswordField().getPassword());
                if (StringUtils.isBlank(dbUrl)) {
                    settingForm.getMysqlUrlTextField().grabFocus();
                    return;
                }
                if (StringUtils.isBlank(dbUser)) {
                    settingForm.getMysqlUserTextField().grabFocus();
                    return;
                }
                if (StringUtils.isBlank(dbPassword)) {
                    settingForm.getMysqlPasswordField().grabFocus();
                    return;
                }
                hikariDataSource = new HikariDataSource();
                hikariDataSource.setJdbcUrl("jdbc:mysql://" + dbUrl);
                hikariDataSource.setUsername(dbUser);
                hikariDataSource.setPassword(dbPassword);
                if (hikariDataSource.getConnection() == null) {
                    JOptionPane.showMessageDialog(settingPanel, "????????????", "??????",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            } finally {
                if (hikariDataSource != null) {
                    try {
                        hikariDataSource.close();
                    } catch (Exception e2) {
                        logger.error(e2);
                    }
                }
            }
        });

        // mysql?????????-??????
        settingForm.getSettingDbInfoSaveButton().addActionListener(e -> {
            try {
                App.config.setMysqlUrl(settingForm.getMysqlUrlTextField().getText());
                App.config.setMysqlUser(settingForm.getMysqlUserTextField().getText());
                App.config.setMysqlPassword(new String(settingForm.getMysqlPasswordField().getPassword()));
                App.config.save();

                if (HikariUtil.getHikariDataSource() != null) {
                    HikariUtil.getHikariDataSource().close();
                }

                JOptionPane.showMessageDialog(settingPanel, "???????????????", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-??????
        settingForm.getSettingAppearanceSaveButton().addActionListener(e -> {
            try {
                if (!App.config.getTheme().equals(settingForm.getSettingThemeComboBox().getSelectedItem().toString())) {
                    App.config.setTheme(Objects.requireNonNull(settingForm.getSettingThemeComboBox().getSelectedItem()).toString());
                    Init.initTheme();
                    for (Window window : Window.getWindows()) {
                        SwingUtilities.updateComponentTreeUI(window);
                    }
                }
                Init.initGlobalFont();

                App.config.setFont(Objects.requireNonNull(settingForm.getSettingFontNameComboBox().getSelectedItem()).toString());
                App.config.setFontSize(Integer.parseInt(Objects.requireNonNull(settingForm.getSettingFontSizeComboBox().getSelectedItem()).toString()));
                App.config.save();

                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n??????????????????????????????????????????\n\n", "??????",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(settingPanel, "???????????????\n\n" + e1.getMessage(), "??????",
                        JOptionPane.ERROR_MESSAGE);
                logger.error(e1);
            }
        });

        // ??????-????????????
        settingForm.getShowLogButton().addActionListener(e -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(SystemUtil.LOG_DIR));
            } catch (Exception e2) {
                logger.error("????????????????????????", e2);
            }
        });

        // ??????-??????????????????
        settingForm.getSystemEnvButton().addActionListener(e -> {
            try {
                SystemEnvResultDialog dialog = new SystemEnvResultDialog();

                dialog.appendTextArea("------------System.getenv---------------");
                Map<String, String> map = System.getenv();
                for (Map.Entry<String, String> envEntry : map.entrySet()) {
                    dialog.appendTextArea(envEntry.getKey() + "=" + envEntry.getValue());
                }

                dialog.appendTextArea("------------System.getProperties---------------");
                Properties properties = System.getProperties();
                for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
                    dialog.appendTextArea(objectObjectEntry.getKey() + "=" + objectObjectEntry.getValue());
                }

                dialog.pack();
                dialog.setVisible(true);
            } catch (Exception e2) {
                logger.error("??????????????????????????????", e2);
            }
        });

        settingForm.getMpUseProxyCheckBox().addChangeListener(e -> SettingForm.toggleMpProxyPanel());
        settingForm.getMaUseProxyCheckBox().addChangeListener(e -> SettingForm.toggleMaProxyPanel());
        settingForm.getHttpUseProxyCheckBox().addChangeListener(e -> SettingForm.toggleHttpProxyPanel());
        settingForm.getUseOutSideAccessTokenCheckBox().addChangeListener(e -> SettingForm.toggleMpOutSideAccessTokenPanel());
        settingForm.getManualAtRadioButton().addChangeListener(e -> {
            boolean isSelected = settingForm.getManualAtRadioButton().isSelected();
            if (isSelected) {
                settingForm.getApiAtRadioButton().setSelected(false);
            }
        });
        settingForm.getApiAtRadioButton().addChangeListener(e -> {
            boolean isSelected = settingForm.getApiAtRadioButton().isSelected();
            if (isSelected) {
                settingForm.getManualAtRadioButton().setSelected(false);
            }
        });

        settingForm.getOutSideAtTipsLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                CommonTipsDialog dialog = new CommonTipsDialog();

                StringBuilder tipsBuilder = new StringBuilder();
                tipsBuilder.append("<h1>?????????????????????????????????AccessToken???</h1>");
                tipsBuilder.append("<p>?????????????????????????????????AccessToken??????????????????AppID???AppSecret?????????????????????AccessToken???</p>");
                tipsBuilder.append("<p>?????????????????????????????????????????????????????????????????????????????????????????????AppID????????????????????????</p>");
                tipsBuilder.append("<p>????????????????????????????????????????????????????????????????????????????????????????????????</p>");
                tipsBuilder.append("<h2>??????????????????WePush??????????????????????????????????????????</h2>");
                tipsBuilder.append("<h2>????????????????????????</h2>");

                dialog.setHtmlText(tipsBuilder.toString());
                dialog.pack();
                dialog.setVisible(true);

                super.mousePressed(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setIcon(new ImageIcon(UiConsts.HELP_FOCUSED_ICON));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                label.setIcon(new ImageIcon(UiConsts.HELP_ICON));
                super.mouseExited(e);
            }
        });
        settingForm.getManualAtTipsLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                CommonTipsDialog dialog = new CommonTipsDialog();

                StringBuilder tipsBuilder = new StringBuilder();
                tipsBuilder.append("<h1>???????????????</h1>");
                tipsBuilder.append("<h2>????????????AccessToken???????????????</h2>");
                tipsBuilder.append("<h2>??????????????????????????????WePush?????????????????????????????????????????????</h2>");
                tipsBuilder.append("<p>?????????????????????????????????????????????????????????</p>");

                dialog.setHtmlText(tipsBuilder.toString());
                dialog.pack();
                dialog.setVisible(true);

                super.mousePressed(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setIcon(new ImageIcon(UiConsts.HELP_FOCUSED_ICON));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                label.setIcon(new ImageIcon(UiConsts.HELP_ICON));
                super.mouseExited(e);
            }
        });
        settingForm.getApiAtTipsLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                CommonTipsDialog dialog = new CommonTipsDialog();

                StringBuilder tipsBuilder = new StringBuilder();
                tipsBuilder.append("<h1>???????????????</h1>");
                tipsBuilder.append("<h2>?????????????????????????????????????????????????????????????????????????????????</h2>");
                tipsBuilder.append("<p>???????????????????????????????????????????????????</p>");
                tipsBuilder.append("<p>????????????GET????????????????????????</p>");
                tipsBuilder.append("<p>{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}</p>");
                tipsBuilder.append("<p>??????????????????????????????????????????????????????????????????????????????</p>");
                tipsBuilder.append("<p>????????????????????????????????????????????????</p>");
                tipsBuilder.append("<p>?????????http://mydomain.com/wechat/getAccessToken?secret=jad76^j2#SY</p>");

                dialog.setHtmlText(tipsBuilder.toString());
                dialog.pack();
                dialog.setVisible(true);

                super.mousePressed(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setIcon(new ImageIcon(UiConsts.HELP_FOCUSED_ICON));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                label.setIcon(new ImageIcon(UiConsts.HELP_ICON));
                super.mouseExited(e);
            }
        });
    }

}
