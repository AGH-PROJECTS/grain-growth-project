package controllers;

import gui.DrawPanel;
import tools.BoardCell;
import tools.Cell;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class DrawController {
    private DrawPanel drawPanel;
    private Graphics2D g2d;
    private MenuController menuController;
    private BoardCell boardCell;
    private int drawPanelWidth;
    private int drawPanelHeight;
    private int cellSize = 0;
    private int widthSize = 0;
    private int heightSize = 0;
    private int radian = 0;
    private HashMap<Color, Integer> colors = new HashMap();
    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
            Color color = changeColor();
            g2d.setPaint(color);
            int xClick = e.getX();
            int yClick = e.getY();
            if(xClick/cellSize < widthSize && yClick/cellSize < heightSize) {
                Cell cell = boardCell.get(yClick/cellSize,xClick/cellSize);
                boardCell.changeCellState(yClick/cellSize,xClick/cellSize,1,color);
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

    public void generate(int height, int width) {
        this.cellSize = drawPanelWidth / width;
        this.widthSize = width;
        this.heightSize = height;
        boardCell = new BoardCell(width, height, cellSize);
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        g2d.clearRect(0,0, drawPanelWidth, drawPanelHeight);

        switch (menuController.getNucleationType().get()) {
            case 0:
                drawHomogeneous(menuController.getAmountInHorizontal(),menuController.getAmountInVertical());
                break;
            case 1:
                drawWithRadius(menuController.getAmountGerms(), menuController.getRadian());
                break;
            case 2:
                drawRandom(menuController.getAmountGerms());
                break;
            case 3:
                drawCustom();
                break;
        }
    }

    public void drawHomogeneous(int heightAmount, int widthAmount) {
        int distanceX = widthSize / heightAmount;
        int distanceY = heightSize / widthAmount;
        int y = distanceY/2;
        int x;
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        for(int i = 0; i < widthAmount; i++) {
            x = distanceX/2;
            for (int j = 0; j < heightAmount; j++) {
                Color color = changeColor();
                g2d.setPaint(color);
                Cell cell = boardCell.get(y,x);
                boardCell.changeCellState(y,x,1,color);
                g2d.fillRect(cell.getxCord() - cellSize, cell.getyCord() - cellSize, cellSize, cellSize);
                x += distanceX;
            }
            y += distanceY;
        }
    }

    private void drawCustom() {
        drawPanel.addMouseListener(mouseListener);
    }

    public void drawWithRadius(int amount, int radius) {
        int maxWidth = widthSize - 1;
        int minWidth = 1;
        int rangeWidth = maxWidth - minWidth + 1;
        int iterator = 0;

        int maxHeight = heightSize - 1;
        int minHeight = 1;
        int rangeHeight = maxHeight - minHeight + 1;
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        for (int zarodek = 0; zarodek < amount; zarodek++) {
            int x,y,YNext,XNext;
            int tryGetCords = 100;
            while (tryGetCords > 0) {
                x = (int) (Math.random() * rangeWidth) + minWidth;
                y = (int) (Math.random() * rangeHeight) + minHeight;
                Cell cell = boardCell.get(y,x);
                int Y = cell.getCenterY();
                int X = cell.getCenterX();

                if(cell.getCurrentState() == 0) {
                    for (int i = y - radius; i <= y + radius; i++)
                    {
                        if (i < 0)
                        {
                            YNext = i % maxHeight + maxHeight;
                        }
                        else
                        {
                            YNext = i % maxHeight;
                        }

                        for (int j = x - radius; j <= x + radius; j++)
                        {
                            if (j < 0)
                            {
                                XNext = j % maxWidth + maxWidth;
                            }
                            else
                            {
                                XNext = j % maxWidth;
                            }
                            if (menuController.getBinaryConditionType().get())
                            {
                                if (Math.pow(boardCell.get(YNext, XNext).getCenterX() - X, 2) + Math.pow(boardCell.get(YNext, XNext).getCenterY() - Y, 2) <= Math.pow(radius * cellSize, 2) && boardCell.get(YNext, XNext).getCurrentState() != 0)
                                {
                                    iterator++;
                                }
                            }
                            else
                            {
                                if (Math.pow(j * cellSize + (boardCell.get(YNext, XNext).getCenterX() % cellSize) - X, 2) + Math.pow(i * cellSize + (boardCell.get(YNext, XNext).getCenterY() % cellSize) - Y, 2) <= Math.pow(radius * cellSize, 2) && boardCell.get(YNext, XNext).getCurrentState() != 0)
                                {
                                    iterator++;
                                }
                            }

                        }
                    }

                    if (iterator == 0)
                    {
                        Color color = changeColor();
                        boardCell.changeCellState(y, x, 1, color);
                        g2d.setPaint(color);
                        g2d.fillRect(boardCell.get(y,x).getxCord(), boardCell.get(y,x).getyCord(), cellSize, cellSize);
                        iterator = 0;
                        break;
                    }
                    iterator = 0;
                    tryGetCords--;
                }
            }
        }
    }

    public void drawRandom(int amount) {
        int maxWidth = widthSize - 2;
        int minWidth = 1;
        int rangeWidth = maxWidth - minWidth + 1;

        int maxHeight = heightSize - 2;
        int minHeight = 1;
        int rangeHeight = maxHeight - minHeight + 1;
        int counter = 0;
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        for(int i = 0 ; i < amount; i++) {
            int x;
            int y;
            int tryGetCords = 100;
            while (tryGetCords > 0) {
                x = (int) (Math.random() * rangeWidth) + minWidth;
                y = (int) (Math.random() * rangeHeight) + minHeight;
                if (!boardCell.get(y, x).getColor().equals(Color.WHITE)) {
                    tryGetCords--;
                } else {
                    counter++;
                    System.out.println("Wylosowano poprawnie dla: " + counter);
                    Color color = changeColor();
                    g2d.setPaint(color);
                    Cell cell = boardCell.get(y, x);
                    boardCell.changeCellState(y, x, 1, color);
                    g2d.fillRect(cell.getxCord(), cell.getyCord(), cellSize, cellSize);
                    break;
                }
            }
        }
    }

    public void startSimulation(boolean toExit) {
        drawPanel.removeMouseListener(mouseListener);
        boardCell.setPeriodical(menuController.getBinaryConditionType().get());
        boardCell.setNeighbourhood(menuController.getNeighbourhoodType().get());
        boardCell.setRadius(menuController.getRadius());
        boardCell.startSimulation(drawPanel, toExit);
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    private Color changeColor() {
        int max = 255;
        int min = 0;
        int range = max - min + 1;
        int red = (int) (Math.random() * range) + min;
        int green = (int) (Math.random() * range) + min;
        int blue = (int) (Math.random() * range) + min;
        Color color = new Color(red, green, blue);
        while(colors.get(color) != null) {
            red = (int) (Math.random() * range) + min;
            green = (int) (Math.random() * range) + min;
            blue = (int) (Math.random() * range) + min;
            color = new Color(red, green, blue);
        }
        colors.put(color,1);


        return color;
    }

    public void startMonteCarlo(int iteration, double kt) {

        for(int i = 0; i < iteration; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int k = 1; k < heightSize + 1; k++) {
                for (int h = 1; h < widthSize + 1; h++) {
                    cellList.add(boardCell.get(k,h));
                }
            }
            Collections.shuffle(cellList);
            boardCell.setEnergy(cellList,kt);

            synchronized (this) {
                try {
                    wait(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void drawEnergy() {
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        for (int k = 1; k < heightSize + 1; k++) {
            for (int h = 1; h < widthSize + 1; h++) {
                Cell cell = boardCell.get(k,h);
                if(!cell.getColor().equals(Color.WHITE)) {
                    g2d.setPaint(cell.getEnergyColor());
                    g2d.fillRect(cell.getxCord() - cellSize, cell.getyCord() - cellSize, cellSize, cellSize);
                }
            }
        }

    }

    public void drawPrevious() {
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        g2d.clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());

        for (int k = 1; k < heightSize + 1; k++) {
            for (int h = 1; h < widthSize + 1; h++) {
                Cell cell = boardCell.get(k,h);
                g2d.setPaint(cell.getColor());
                g2d.fillRect(cell.getxCord() - cellSize, cell.getyCord() - cellSize, cellSize, cellSize);
            }
        }
    }
}
