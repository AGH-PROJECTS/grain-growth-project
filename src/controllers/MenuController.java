package controllers;

import gui.MenuPanel;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuController {
    private MenuPanel menuPanel;
    private int menuPanelWidth;
    private int menuPanelHeight;
    private AtomicInteger nucleationType = new AtomicInteger();
    private AtomicBoolean binaryConditionType = new AtomicBoolean();
    private AtomicInteger neighbourhoodType = new AtomicInteger();
    private int radian = 0;
    private int amountInVertical = 0;
    private int amountInHorizontal = 0;
    private int amountGerms = 0;
    private int radius = 0;
    private DrawController drawController;

    public MenuController(int width, int height) {
        this.menuPanel = new MenuPanel();
        this.menuPanelWidth = width;
        this.menuPanelHeight = height;
        setMenuPanel();
        setActionsMenuPanel();
    }

    private void setMenuPanel() {
        menuPanel.setPreferredSize(new Dimension(menuPanelWidth,menuPanelHeight));
        menuPanel.setBackground(Color.LIGHT_GRAY);
    }

    private void setActionsMenuPanel() {
        menuPanel.getButtonGenerateBoard().addActionListener(e-> {
            int rows = Integer.parseInt(menuPanel.getTextFieldWidth().getText());
            int cols = Integer.parseInt(menuPanel.getTextFieldHeight().getText());
            drawController.generate(rows, cols);
        });

        menuPanel.getButtonStartSimulation().addActionListener(e->{
            drawController.startSimulation();
        });

        menuPanel.getComboBoxNucleationKinds().addActionListener(e-> {
            switch (menuPanel.getComboBoxNucleationKinds().getSelectedIndex()) {
                case 0:
                    nucleationType.set(0);
                    amountInHorizontal = Integer.parseInt(menuPanel.getTextFieldAmountWidth().getText());
                    amountInVertical = Integer.parseInt(menuPanel.getTextFieldAmountHeight().getText());
                    break;
                case 1:
                    nucleationType.set(1);
                    amountGerms = Integer.parseInt(menuPanel.getTextFieldAmountGerms().getText());
                    radius = Integer.parseInt(menuPanel.getTextFieldRadius().getText());
                    break;
                case 2:
                    nucleationType.set(2);
                    amountGerms = Integer.parseInt(menuPanel.getTextFieldAmountGerms().getText());
                    break;
                case 3:
                    nucleationType.set(3);
                    break;
            }
        });

        menuPanel.getComboBoxBinaryConditionsKinds().addActionListener(e-> {
            if(menuPanel.getComboBoxBinaryConditionsKinds().getSelectedIndex() == 0) {
                binaryConditionType.set(true);
            }
            else {
                binaryConditionType.set(false);
            }
        });

        menuPanel.getComboBoxNeighbourhood().addActionListener(e-> {
            switch (menuPanel.getComboBoxNeighbourhood().getSelectedIndex()) {
                case 0:
                    neighbourhoodType.set(0);
                    break;
                case 1:
                    neighbourhoodType.set(1);
                    break;
                case 2:
                    neighbourhoodType.set(2);
                    break;
                case 3:
                    neighbourhoodType.set(3);
                    break;
                case 4:
                    neighbourhoodType.set(4);
                    radian = Integer.parseInt(menuPanel.getTextFieldRadiusNeighbourhood().getText());
                    break;
            }
        });
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public AtomicInteger getNucleationType() {
        return nucleationType;
    }

    public AtomicInteger getNeighbourhoodType() {
        return neighbourhoodType;
    }

    public void setDrawController(DrawController drawController) {
        this.drawController = drawController;
    }

    public int getAmountInVertical() {
        return amountInVertical;
    }

    public int getAmountInHorizontal() {
        return amountInHorizontal;
    }

    public int getAmountGerms() {
        return amountGerms;
    }

    public int getRadius() {
        return radius;
    }

    public int getRadian() {
        return radian;
    }

    public AtomicBoolean getBinaryConditionType() {
        return binaryConditionType;
    }
}
