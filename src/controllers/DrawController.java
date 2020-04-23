package controllers;

import gui.DrawPanel;
import tools.BoardCell;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class DrawController {
    private DrawPanel drawPanel;
    private BoardCell boardCell;
    private int drawPanelWidth;
    private int drawPanelHeight;
    private int size = 0;
    private MenuController menuController;
    private int radian = 0;
    private List<List<Rectangle2D>> board = new LinkedList<>();

    public DrawController(int width, int height) {
        this.drawPanel = new DrawPanel();
        this.drawPanelWidth = width;
        this.drawPanelHeight = height;
        setDrawPanel();
    }

    private void setDrawPanel() {
        drawPanel.setBackground(new Color(230,230,230));
        drawPanel.setPreferredSize(new Dimension(drawPanelWidth, drawPanelHeight));
        drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        drawPanel.setCellSize(size);
    }

    public void generate(int height, int width) {
        if(drawPanelWidth > drawPanelHeight) {
            this.size = (drawPanelWidth/width);
        } else {
            this.size = (drawPanelHeight/height);
        }
        board.clear();
        board = new LinkedList<>();
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        g2d.clearRect(0,0, drawPanel.getWidth(), drawPanel.getHeight());
        for(int i = 0; i < height; i++) {
            board.add(new LinkedList<>());
            for(int j = 0; j < width; j++) {
                Rectangle2D rectangle2D = new Rectangle2D.Double(j * size,i * size, size, size);
                board.get(i).add(rectangle2D);
                g2d.setPaint(Color.WHITE);
                g2d.fill(rectangle2D);
                g2d.setPaint(Color.BLACK);
                g2d.draw(rectangle2D);
            }
        }

        boardCell = new BoardCell(board,size);

        switch (menuController.getNucleationType().get()) {
            case 0:
                drawPanel.drawHomogeneous(menuController.getAmountInHorizontal(),menuController.getAmountInVertical(), board, boardCell);
                break;
            case 1:
                drawPanel.drawWithRadian(menuController.getAmountGerms(),menuController.getRadius(), board, boardCell);
                break;
            case 2:
                drawPanel.drawRandom(menuController.getAmountGerms(), board, boardCell);
                break;
            case 3:
                drawPanel.drawCustom(board, boardCell);
                break;
        }
    }

    public void setCustomNucleationKind(boolean isAllowed) {
        drawPanel.setCustomAllowed(isAllowed);
    }

    public void setNeighbourhood(int i) {
        //boardCell.setNeighbourhood(i);
    }

    public void setBinaryConditionsKinds(int binaryCondition) {
        boardCell.setBinaryConditions(binaryCondition);
    }

    public void drawHomogeneous(int rowsAmount, int colsAmount) {
        drawPanel.drawHomogeneous(rowsAmount, colsAmount, board, boardCell);
    }

    public void drawWithRadius(int amount, int radius) {
        drawPanel.drawWithRadian(amount, radius, board, boardCell);
    }

    public void startSimulation() {
        boardCell.setBinaryConditions(menuController.getBinaryConditionType().get());
        boardCell.setNeighbourhood(menuController.getNeighbourhoodType().get());
        getRadian();
        boardCell.setRadian(radian);
        boardCell.startSimulation(drawPanel);
        //drawPanel.deleteEllipses();
    }

    public void drawRandom(int amount) {
        drawPanel.drawRandom(amount, board, boardCell);
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void getRadian() {
        this.radian = menuController.getRadian();
    }
}
