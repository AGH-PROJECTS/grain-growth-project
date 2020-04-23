package tools;

import java.awt.*;

public class Cell {
    private int size;
    private final int indexX;
    private final int indexY;
    private int xCord;
    private int yCord;
    private int centerX;
    private int centerY;
    private int currentState = 0;
    private int nextState = 0;
    private Color color;
    private Color nextColor;

    public Cell(int idX, int idY, int size, Color color) {
        this.indexX = idX;
        this.indexY = idY;
        this.xCord = idX * size;
        this.yCord = idY * size;
        this.centerX = xCord + (int)(Math.random() * size);
        this.centerY = yCord + (int)(Math.random() * size);
        this.color = color;
        this.nextColor = color;
    }

    public void changeState(int state, Color color) {
        this.currentState = state;
        this.color = color;
        this.nextColor = color;
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public Color getColor() {
        return color;
    }

    public void setNextState(int nextState) {
        if(nextColor.equals(Color.WHITE)) {
            this.nextState = currentState;
        } else {
            this.nextState = nextState;
        }

    }

    public void setNextColor(Color color) {
        this.nextColor = color;
    }

    public void setNewValues() {
        this.currentState = nextState;
        this.color = nextColor;
    }

}
