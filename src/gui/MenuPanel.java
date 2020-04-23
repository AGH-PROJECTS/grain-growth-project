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

    private JLabel labelWidth = new JLabel("Podaj wysokość:");
    private JTextField textFieldWidth = new JTextField("100");

    private JLabel labelHeight = new JLabel("Podaj szerokość:");
    private JTextField textFieldHeight = new JTextField("100");

    private JLabel labelAmountWidth = new JLabel("Podaj ilość w kolumnie:");
    private JTextField textFieldAmountWidth = new JTextField("10");

    private JLabel labelAmountHeight = new JLabel("Podaj ilość w wierszu:");
    private JTextField textFieldAmountHeight = new JTextField("10");

    private JLabel labelAmountGerms = new JLabel("Podaj ilość zarodków:");
    private JTextField textFieldAmountGerms = new JTextField("10");

    private JLabel labelRadius = new JLabel("Podaj promień do generowania zarodków:");
    private JTextField textFieldRadius = new JTextField("100");

    private JLabel labelRadiusNeighbourhood = new JLabel("Podaj promień do sasiedztwa:");
    private JTextField textFieldRadiusNeighbourhood = new JTextField("100");

    private JButton buttonGenerateBoard = new JButton("Generuj");

    private JButton buttonStartSimulation = new JButton("Start");

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
}
