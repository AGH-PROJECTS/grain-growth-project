package gui;

import tools.BoardCell;
import tools.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrawPanel extends JPanel {
    public DrawPanel() {}

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
                //ellipse2DS.add(r);
               // Color color = changeColor();
                //g2d.setPaint(color);
                g2d.fill(board.get(indexX).get(indexY));
                //boardCell.changeCellState(indexX,indexY,1,color);
            }
        }

    }

}
