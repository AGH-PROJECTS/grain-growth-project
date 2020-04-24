package controllers;

import gui.DrawPanel;
import tools.BoardCell;
import tools.Cell;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class DrawController {
    private DrawPanel drawPanel;
    private Graphics2D g2d;
    private MenuController menuController;
    private BoardCell boardCell;
    private int drawPanelWidth;
    private int drawPanelHeight;
    private int cellSize = 0;
    private int rowsSize = 0;
    private int colsSize = 0;
    private int radian = 0;
    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
            Color color = changeColor();
            g2d.setPaint(color);
            int xClick = e.getX();
            int yClick = e.getY();
            if(xClick/cellSize < rowsSize && yClick/cellSize < colsSize) {
                Cell cell = boardCell.get(xClick/cellSize,yClick/cellSize);
                boardCell.changeCellState(xClick/cellSize,yClick/cellSize,1,color);
                g2d.fillRect(cell.getxCord(),cell.getyCord(),cellSize,cellSize);
            }

        }
    };

    public DrawController(int width, int height) {
        this.drawPanel = new DrawPanel();
        this.g2d = (Graphics2D) drawPanel.getGraphics();
        this.drawPanelWidth = width;
        this.drawPanelHeight = height;
        setDrawPanel();
    }

    private void setDrawPanel() {
        drawPanel.setBackground(new Color(230,230,230));
        drawPanel.setPreferredSize(new Dimension(drawPanelWidth, drawPanelHeight));
        drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
    }

    public void generate(int rows, int cols) {
        this.cellSize = drawPanelWidth / rows;
        this.rowsSize = rows;
        this.colsSize = cols;
        boardCell = new BoardCell(rows, cols, cellSize);
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        g2d.clearRect(0,0, drawPanelWidth, drawPanelHeight);

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                g2d.setPaint(Color.WHITE);
                Cell cell = boardCell.get(i,j);
                g2d.fillRect(cell.getxCord(), cell.getyCord(), cellSize, cellSize);
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(cell.getxCord(), cell.getyCord(), cellSize, cellSize);
            }
        }

        switch (menuController.getNucleationType().get()) {
            case 0:
                drawHomogeneous(menuController.getAmountInHorizontal(),menuController.getAmountInVertical());
                break;
            case 1:
                //drawPanel.drawWithRadian(menuController.getAmountGerms(),menuController.getRadius(), board, boardCell);
                break;
            case 2:
                drawRandom(menuController.getAmountGerms());
                break;
            case 3:
                drawCustom();
                break;
        }
    }

    public void drawHomogeneous(int rowsAmount, int colsAmount) {
        int distanceX = rowsSize / rowsAmount;
        int distanceY = colsSize / colsAmount;
        int x = distanceX/2;
        int y;
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        for(int i = 0; i < rowsAmount; i++) {
            y = distanceY/2;
            for (int j = 0; j < colsAmount; j++) {
                Color color = changeColor();
                g2d.setPaint(color);
                Cell cell = boardCell.get(x,y);
                boardCell.changeCellState(x,y,1,color);
                g2d.fillRect(cell.getxCord(), cell.getyCord(), cellSize, cellSize);
                y += distanceY;
                if(y > colsSize ) {
                    break;
                }
            }
            x += distanceX;
            if(x > rowsSize) {
                break;
            }
        }
    }

    private void drawCustom() {
        drawPanel.addMouseListener(mouseListener);
    }

    public void drawWithRadius(int amount, int radius) {
       // drawPanel.drawWithRadian(amount, radius, board, boardCell);
    }

    public void drawRandom(int amount) {
        int maxWidth = rowsSize - 1;
        int minWidth = 0;
        int rangeWidth = maxWidth - minWidth + 1;

        int maxHeight = colsSize - 1;
        int minHeight = 0;
        int rangeHeight = maxHeight - minHeight + 1;
        int counter = 0;
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        for(int i = 0 ; i < amount; i++) {
            int x;
            int y;
            int tryGetCords = 100;
            while (tryGetCords > 0) {
                x = (int) (Math.random() * rangeHeight) + minHeight;
                y = (int) (Math.random() * rangeWidth) + minWidth;
                if (!boardCell.get(x, y).getColor().equals(Color.WHITE)) {
                    tryGetCords--;
                } else {
                    counter++;
                    System.out.println("Wylosowano poprawnie dla: " + counter);
                    Color color = changeColor();
                    g2d.setPaint(color);
                    Cell cell = boardCell.get(x, y);
                    boardCell.changeCellState(x, y, 1, color);
                    g2d.fillRect(cell.getxCord(), cell.getyCord(), cellSize, cellSize);
                    break;
                }

            }
        }
    }

    public void startSimulation() {
        drawPanel.removeMouseListener(mouseListener);
        boardCell.setPeriodical(menuController.getBinaryConditionType().get());
        boardCell.setNeighbourhood(menuController.getNeighbourhoodType().get());
        boardCell.startSimulation(drawPanel);
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

    private Color changeColor() {
        int max = 255;
        int min = 0;
        int range = max - min + 1;

        int red = (int) (Math.random() * range) + min;
        int green = (int) (Math.random() * range) + min;
        int blue = (int) (Math.random() * range) + min;
        Color color = new Color(red, green, blue);
        return color;
    }
}
