package gui;

import tools.BoardCell;
import tools.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrawPanel extends JPanel {
    /*private boolean isCustomAllowed = false;
    private int cellSize = 0;
    private List<List<Rectangle2D>> rectangles = new LinkedList<>();
    private List<List<Rectangle2D>> tempBoard = new LinkedList<>();
    private BoardCell boardCell;*/
    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if(isCustomAllowed) {
                super.mousePressed(e);
                for(List<Rectangle2D> rects : tempBoard) {
                    for(Rectangle2D rect : rects) {
                        if(rect.contains(e.getPoint())) {
                            Graphics2D g2d = (Graphics2D) getGraphics();
                            Color color = changeColor();
                            g2d.setPaint(color);
                            g2d.fill(rect);
                            int x = tempBoard.indexOf(rects);
                            int y = rects.indexOf(rect);
                            boardCell.changeCellState(x,y,1,color);
                        }
                    }
                }
            }
        }
    };

    public DrawPanel() {

    }



    int size = 0;
    List<Ellipse2D> ellipse2DS = new ArrayList<>();

    public void drawCellPanels(int rows, int cols, int size) {
        this.size = size;
        removeMouseListener(mouseListener);
        //rectangles.clear();
        //boardCell = new BoardCell(rows,cols);
        Graphics2D g2d = (Graphics2D) this.getGraphics();
        g2d.clearRect(0,0,getWidth(),getHeight());
        for(int i = 0; i < rows; i++) {
            //rectangles.add(new LinkedList<>());
            for(int j = 0; j < cols; j++) {
                Rectangle2D rectangle2D = new Rectangle2D.Double(j * size,i * size,size,size);
                //rectangles.get(i).add(rectangle2D);
                //boardCell.set(size,i,j);
                g2d = (Graphics2D) this.getGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fill(rectangle2D);
                g2d.setPaint(Color.BLACK);
                g2d.draw(rectangle2D);
            }
        }
        addMouseListener(mouseListener);
    }

    public Color changeColor() {
        int max = 255;
        int min = 0;
        int range = max - min + 1;

        int red = (int) (Math.random() * range) + min;
        int green = (int) (Math.random() * range) + min;
        int blue = (int) (Math.random() * range) + min;
        Color color = new Color(red, green, blue);
        return color;
    }

/*    public BoardCell getBoardCell() {
        return boardCell;
    }*/

    public void setCustomAllowed(boolean customAllowed) {
        isCustomAllowed = customAllowed;
    }

    public void drawHomogeneous(int amountHorizontal, int amountVertical, List<List<Rectangle2D>> board, BoardCell boardCell) {
        //pobierac dane
        int distanceX = board.get(0).size() / amountHorizontal;
        int distanceY = board.size() / amountVertical;
        int x = distanceX/2;
        int y;
        for(int i = 0; i < amountHorizontal; i++) {
            y = distanceY/2;
            for (int j = 0; j < amountVertical; j++) {
                Graphics2D g2d = (Graphics2D) getGraphics();
                Color color = changeColor();
                g2d.setPaint(color);
                if(board.size() > x && board.get(x).size() > y) {
                    g2d.fill(board.get(x).get(y));
                    boardCell.changeCellState(x,y,1,color);
                } else {
                    break;
                }
                y += distanceY;
            }
            x += distanceX;
        }
    }

    public void drawWithRadian(int amount, int radius, List<List<Rectangle2D>> board, BoardCell boardCell) {
        int maxWidth = board.get(0).size() - 1;
        int minWidth = 0;
        int rangeWidth = maxWidth - minWidth + 1;

        int maxHeight = board.size() - 1;
        int minHeight = 0;
        int rangeHeight = maxHeight - minHeight + 1;

        Graphics2D g2d = (Graphics2D) getGraphics();

        for(int i = 0 ; i < amount; i++) {
            int indexX = (int) (Math.random() * rangeHeight) + minHeight;
            int indexY = (int) (Math.random() * rangeWidth) + minWidth;

            List<Cell> aliveCells = boardCell.getAliveCells();
            Ellipse2D r = new Ellipse2D.Double(boardCell.get(indexX,indexY).getCenterX() - radius/2, boardCell.get(indexX,indexY).getCenterY() - radius/2,radius,radius);
            AtomicBoolean isCanDraw = new AtomicBoolean(true);
            aliveCells.forEach(cell -> {
                g2d.setPaint(Color.black);
                g2d.fillRect(cell.getCenterX() - 1,cell.getCenterY() - 1,3,3);
                if (r.contains(cell.getCenterX(), cell.getCenterY())) {
                    isCanDraw.set(false);
                }
            });

            if(isCanDraw.get()) {
                g2d.setPaint(Color.BLACK);
                g2d.draw(r);
                ellipse2DS.add(r);
                Color color = changeColor();
                g2d.setPaint(color);
                g2d.fill(board.get(indexX).get(indexY));
                boardCell.changeCellState(indexX,indexY,1,color);
            }
        }

    }

    public void drawRandom(int amount, List<List<Rectangle2D>> board, BoardCell boardCell) {
        int maxWidth = board.get(0).size() -1;
        int minWidth = 0;
        int rangeWidth = maxWidth - minWidth + 1;

        int maxHeight = board.size() - 1;
        int minHeight = 0;
        int rangeHeight = maxHeight - minHeight + 1;

        for(int i = 0 ; i < amount; i++) {
            int x = (int) (Math.random() * rangeHeight) + minHeight;
            int y = (int) (Math.random() * rangeWidth) + minWidth;
            Graphics2D g2d = (Graphics2D) getGraphics();
            Color color = changeColor();
            g2d.setPaint(color);
            g2d.fill(board.get(x).get(y));
            boardCell.changeCellState(x,y,1,color);
        }
    }

    public void fillRect(Cell cell, List<List<Rectangle2D>> boardRects) {
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.setPaint(cell.getColor());
        g2d.fill(boardRects.get(cell.getIndexX()).get(cell.getIndexY()));
    }


    public void drawCustom(List<List<Rectangle2D>> board, BoardCell boardCell) {
        this.tempBoard = board;
        this.boardCell = boardCell;
        addMouseListener(mouseListener);
    }

    public void setCellSize(int size) {
        this.cellSize = size;
    }
}
