package controllers;

import gui.DrawPanel;
import gui.MenuPanel;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuController {
    private MenuPanel menuPanel;
    private int menuPanelWidth;
    private int menuPanelHeight;
    private AtomicInteger nucleationType = new AtomicInteger();
    private AtomicInteger binaryConditionType = new AtomicInteger();
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
            System.out.println("Generate clicks");
            int widthSize = Integer.parseInt(menuPanel.getTextFieldWidth().getText());
            int heightSize = Integer.parseInt(menuPanel.getTextFieldHeight().getText());
            drawController.generate(widthSize, heightSize);
            /*switch (nucleationType.get()) {
                case 0:
                    int rowsAmount = Integer.parseInt(textFieldAmountHeight.getText());
                    int colsAmount = Integer.parseInt(textFieldAmountWidth.getText());
                    drawController.drawHomogeneous(rowsAmount, colsAmount);
                    break;
                case 1:
                    int amount = Integer.parseInt(textFieldAmountGerms.getText());
                    int radius = Integer.parseInt(textFieldRadius.getText());
                    drawController.drawWithRadius(amount, radius);
                    break;
                case 2:
                    int amount2 = Integer.parseInt(textFieldAmountGerms.getText());
                    drawController.drawRandom(amount2);
                    break;
            }
            drawController.setNeighbourhood(neighbourhoodType.get());
            drawController.setBinaryConditionsKinds(binaryConditionType.get());*/
        });
        menuPanel.getButtonStartSimulation().addActionListener(e->{
            System.out.println("Start click");
            drawController.startSimulation();
            //drawController.setCustomNucleationKind(false);
        });

        menuPanel.getComboBoxNucleationKinds().addActionListener(e-> {
            switch (menuPanel.getComboBoxNucleationKinds().getSelectedIndex()) {
                case 0:
                    nucleationType.set(0);
                    amountInHorizontal = Integer.parseInt(menuPanel.getTextFieldAmountWidth().getText());
                    amountInVertical = Integer.parseInt(menuPanel.getTextFieldAmountHeight().getText());
                    drawController.setCustomNucleationKind(false);
                    break;
                case 1:
                    nucleationType.set(1);
                    amountGerms = Integer.parseInt(menuPanel.getTextFieldAmountGerms().getText());
                    radius = Integer.parseInt(menuPanel.getTextFieldRadius().getText());
                    drawController.setCustomNucleationKind(false);
                    break;
                case 2:
                    nucleationType.set(2);
                    amountGerms = Integer.parseInt(menuPanel.getTextFieldAmountGerms().getText());
                    drawController.setCustomNucleationKind(false);
                    break;
                case 3:
                    nucleationType.set(3);
                    drawController.setCustomNucleationKind(true);
                    break;
            }
        });

        menuPanel.getComboBoxBinaryConditionsKinds().addActionListener(e-> {
            System.out.println("Binary cond clicked");
            if(menuPanel.getComboBoxBinaryConditionsKinds().getSelectedIndex() == 0) {
                binaryConditionType.set(0);
            }
            else {
                binaryConditionType.set(1);
            }
        });

        menuPanel.getComboBoxNeighbourhood().addActionListener(e-> {
            System.out.println("Neigh clicked");
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

    public AtomicInteger getBinaryConditionType() {
        return binaryConditionType;
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
}
