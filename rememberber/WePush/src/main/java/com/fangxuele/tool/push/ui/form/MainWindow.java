package com.fangxuele.tool.push.ui.form;

import com.fangxuele.tool.push.App;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

/**
 * <pre>
 * 主界面
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2017/6/7.
 */
@Getter
public class MainWindow {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel aboutPanel;
    private JSplitPane messagePanel;
    private JPanel memberPanel;
    private JPanel pushPanel;
    private JPanel settingPanel;
    private JPanel schedulePanel;
    private JPanel pushHisPanel;
    private JPanel messageEditPanel;
    private JPanel messageManagePanel;
    private JPanel messageTypePanel;
    private JPanel boostPanel;
    private JPanel infinityPanel;

    private static MainWindow mainWindow;

    private MainWindow() {
    }

    public static MainWindow getInstance() {
        if (mainWindow == null) {
            mainWindow = new MainWindow();
        }
        return mainWindow;
    }

    private static final GridConstraints GRID_CONSTRAINTS = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false);

    public void init() {
        mainWindow = getInstance();
        mainWindow.getMainPanel().updateUI();
        mainWindow.getAboutPanel().add(AboutForm.getInstance().getAboutPanel(), GRID_CONSTRAINTS);
//        mainWindow.getUserCasePanel().add(UserCaseForm.getInstance().getUserCasePanel(), GRID_CONSTRAINTS);
        mainWindow.getSchedulePanel().add(ScheduleForm.getInstance().getSchedulePanel(), GRID_CONSTRAINTS);
        mainWindow.getPushHisPanel().add(PushHisForm.getInstance().getPushHisPanel(), GRID_CONSTRAINTS);
        mainWindow.getSettingPanel().add(SettingForm.getInstance().getSettingPanel(), GRID_CONSTRAINTS);
        mainWindow.getMessageEditPanel().add(MessageEditForm.getInstance().getMessageEditPanel(), GRID_CONSTRAINTS);
        mainWindow.getMessageManagePanel().add(MessageManageForm.getInstance().getMessageManagePanel(), GRID_CONSTRAINTS);
        mainWindow.getMemberPanel().add(MemberForm.getInstance().getMemberPanel(), GRID_CONSTRAINTS);
        mainWindow.getPushPanel().add(PushForm.getInstance().getPushPanel(), GRID_CONSTRAINTS);
        mainWindow.getMessageTypePanel().add(MessageTypeForm.getInstance().getMessageTypePanel(), GRID_CONSTRAINTS);
        mainWindow.getBoostPanel().add(BoostForm.getInstance().getBoostPanel(), GRID_CONSTRAINTS);
        mainWindow.getInfinityPanel().add(InfinityForm.getInstance().getInfinityPanel(), GRID_CONSTRAINTS);
        mainWindow.getMessagePanel().setDividerLocation((int) (App.mainFrame.getWidth() / 5.6));
        mainWindow.getMainPanel().updateUI();
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane = new JTabbedPane();
        tabbedPane.setDoubleBuffered(true);
        Font tabbedPaneFont = this.$$$getFont$$$(null, -1, -1, tabbedPane.getFont());
        if (tabbedPaneFont != null) tabbedPane.setFont(tabbedPaneFont);
        tabbedPane.setTabLayoutPolicy(1);
        mainPanel.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        aboutPanel = new JPanel();
        aboutPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        aboutPanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("关于", aboutPanel);
        messageTypePanel = new JPanel();
        messageTypePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        messageTypePanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("①选择消息类型", messageTypePanel);
        messagePanel = new JSplitPane();
        messagePanel.setContinuousLayout(true);
        messagePanel.setDividerLocation(250);
        messagePanel.setDividerSize(4);
        messagePanel.setDoubleBuffered(true);
        messagePanel.setLastDividerLocation(250);
        messagePanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("②编辑消息", messagePanel);
        messageEditPanel = new JPanel();
        messageEditPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        messageEditPanel.setMaximumSize(new Dimension(-1, -1));
        messageEditPanel.setMinimumSize(new Dimension(-1, -1));
        messageEditPanel.setPreferredSize(new Dimension(-1, -1));
        messagePanel.setRightComponent(messageEditPanel);
        messageManagePanel = new JPanel();
        messageManagePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        messageManagePanel.setMaximumSize(new Dimension(-1, -1));
        messageManagePanel.setMinimumSize(new Dimension(-1, -1));
        messageManagePanel.setPreferredSize(new Dimension(280, -1));
        messagePanel.setLeftComponent(messageManagePanel);
        memberPanel = new JPanel();
        memberPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        memberPanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("③准备目标用户", memberPanel);
        pushPanel = new JPanel();
        pushPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        pushPanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("④开始推送", pushPanel);
        infinityPanel = new JPanel();
        infinityPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("变速模式", infinityPanel);
        boostPanel = new JPanel();
        boostPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("性能模式", boostPanel);
        pushHisPanel = new JPanel();
        pushHisPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        pushHisPanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("推送历史", pushHisPanel);
        schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        schedulePanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("计划任务", schedulePanel);
        settingPanel = new JPanel();
        settingPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        Font settingPanelFont = this.$$$getFont$$$("Microsoft YaHei UI", -1, -1, settingPanel.getFont());
        if (settingPanelFont != null) settingPanel.setFont(settingPanelFont);
        settingPanel.setMinimumSize(new Dimension(-1, -1));
        tabbedPane.addTab("设置", settingPanel);
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

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}