package controllers;

import gui.DrawPanel;
import gui.MainPanel;
import gui.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class MainController {
    int mainWidth = 1200;
    int mainHeight = 1040;
    private MainPanel mainPanel;
    private DrawController drawController;
    private MenuController menuController;
    public MainController() {
        this.drawController = new DrawController(1000, mainHeight);
        this.menuController = new MenuController(183, mainHeight);
        drawController.setMenuController(menuController);
        menuController.setDrawController(drawController);
        this.mainPanel = new MainPanel();
        setMainPanel();
    }

    private void setMainPanel() {
        mainPanel.setSize(mainWidth,mainHeight);
        mainPanel.add(drawController.getDrawPanel());
        mainPanel.add(menuController.getMenuPanel());
        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        mainPanel.setResizable(false);
        mainPanel.setVisible(true);
    }
}
