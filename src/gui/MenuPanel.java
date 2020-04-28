package gui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private Dimension mainDimension = new Dimension(100,30);

    private String [] nucleationKinds = { "Jednorodne", "Z promieniem", "Losowe", "Samodzielne"};
    private JComboBox comboBoxNucleationKinds = new JComboBox(nucleationKinds);

    private String [] neighbourhood = {"Moore", "Von Neumann", "Hex", "Pent", "Z promieniem"};
    private JComboBox comboBoxNeighbourhood = new JComboBox(neighbourhood);

    private String [] binaryConditionKinds = { "Periodyczne", "Absorbujące"};
    private JComboBox comboBoxBinaryConditionsKinds = new JComboBox(binaryConditionKinds);

    private JLabel labelWidth = new JLabel("Podaj szerokosc:");
    private JTextField textFieldWidth = new JTextField("100");

    private JLabel labelHeight = new JLabel("Podaj wysokosc:");
    private JTextField textFieldHeight = new JTextField("100");

    private JLabel labelAmountWidth = new JLabel("Ilość kolumn:");
    private JTextField textFieldAmountWidth = new JTextField("10");

    private JLabel labelAmountHeight = new JLabel("Ilość wierszy");
    private JTextField textFieldAmountHeight = new JTextField("10");

    private JLabel labelAmountGerms = new JLabel("Podaj ilość zarodków:");
    private JTextField textFieldAmountGerms = new JTextField("10");

    private JLabel labelRadius = new JLabel("Promień zarodków:");
    private JTextField textFieldRadius = new JTextField("100");

    private JLabel labelRadiusNeighbourhood = new JLabel("Promień do sasiedztwa:");
    private JTextField textFieldRadiusNeighbourhood = new JTextField("100");

    private JButton buttonGenerateBoard = new JButton("Generuj");

    private JButton buttonStartSimulation = new JButton("Start");

    private JButton buttonStartMonteCarlo = new JButton("MC");

    private JLabel labelIterationAmount = new JLabel("Liczba iteracji");
    private JTextField textFieldIterationAmount = new JTextField("100");

    private JLabel labelKt = new JLabel("Stała Kt:");
    private JTextField textFieldKt = new JTextField("3");

    private JButton buttonEnergy = new JButton("Energia");

    private JButton buttonCAMethod = new JButton("CA");

    public MenuPanel() {
        init();
    }

    private void init() {
        comboBoxBinaryConditionsKinds.setPreferredSize(mainDimension);
        comboBoxNucleationKinds.setPreferredSize(mainDimension);
        comboBoxNeighbourhood.setPreferredSize(mainDimension);
        textFieldHeight.setPreferredSize(mainDimension);
        textFieldWidth.setPreferredSize(mainDimension);
        buttonGenerateBoard.setPreferredSize(mainDimension);
        buttonStartSimulation.setPreferredSize(mainDimension);
        textFieldAmountHeight.setPreferredSize(mainDimension);
        textFieldAmountWidth.setPreferredSize(mainDimension);
        textFieldAmountGerms.setPreferredSize(mainDimension);
        textFieldRadius.setPreferredSize(mainDimension);
        textFieldRadiusNeighbourhood.setPreferredSize(mainDimension);
        buttonStartMonteCarlo.setPreferredSize(mainDimension);
        buttonEnergy.setPreferredSize(mainDimension);
        buttonCAMethod.setPreferredSize(mainDimension);
        textFieldIterationAmount.setPreferredSize(mainDimension);
        labelKt.setPreferredSize(mainDimension);
        textFieldKt.setPreferredSize(mainDimension);

        add(comboBoxNucleationKinds);
        add(comboBoxBinaryConditionsKinds);
        add(comboBoxNeighbourhood);
        add(labelWidth);
        add(textFieldWidth);
        add(labelHeight);
        add(textFieldHeight);
        add(labelAmountHeight);
        add(textFieldAmountHeight);
        add(labelAmountWidth);
        add(textFieldAmountWidth);
        add(labelAmountGerms);
        add(textFieldAmountGerms);
        add(labelRadius);
        add(textFieldRadius);
        add(labelRadiusNeighbourhood);
        add(textFieldRadiusNeighbourhood);
        add(buttonGenerateBoard);
        add(buttonStartSimulation);
        add(buttonStartMonteCarlo);
        add(labelIterationAmount);
        add(textFieldIterationAmount);
        add(labelKt);
        add(textFieldKt);
        add(buttonEnergy);
        add(buttonCAMethod);
    }

    public JComboBox getComboBoxNucleationKinds() {
        return comboBoxNucleationKinds;
    }

    public JComboBox getComboBoxNeighbourhood() {
        return comboBoxNeighbourhood;
    }

    public JComboBox getComboBoxBinaryConditionsKinds() {
        return comboBoxBinaryConditionsKinds;
    }

    public JButton getButtonGenerateBoard() {
        return buttonGenerateBoard;
    }

    public JButton getButtonStartSimulation() {
        return buttonStartSimulation;
    }

    public JTextField getTextFieldWidth() {
        return textFieldWidth;
    }

    public JTextField getTextFieldHeight() {
        return textFieldHeight;
    }

    public JTextField getTextFieldAmountWidth() {
        return textFieldAmountWidth;
    }

    public JTextField getTextFieldAmountHeight() {
        return textFieldAmountHeight;
    }

    public JTextField getTextFieldAmountGerms() {
        return textFieldAmountGerms;
    }

    public JTextField getTextFieldRadius() {
        return textFieldRadius;
    }

    public JTextField getTextFieldRadiusNeighbourhood() {
        return textFieldRadiusNeighbourhood;
    }

    public JButton getButtonStartMonteCarlo() {
        return buttonStartMonteCarlo;
    }

    public JButton getButtonEnergy() {
        return buttonEnergy;
    }

    public JButton getButtonCAMethod() {
        return buttonCAMethod;
    }

    public JTextField getTextFieldIterationAmount() {
        return textFieldIterationAmount;
    }

    public JTextField getTextFieldKt() {
        return textFieldKt;
    }
}
