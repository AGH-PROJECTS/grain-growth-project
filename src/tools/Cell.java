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
    private int currentEnergy = 0;
    private int nextEnergy = 0;
    private Color color;
    private Color nextColor;
    private Color energyColor;

    public Cell(int idY, int idX, int size, Color color) {
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

    public void setCurrentEnergy(int currentEnergy, boolean isRadius) {
        this.currentEnergy = currentEnergy;
        setEnergyColor(currentEnergy,isRadius);
    }

    private void setEnergyColor(int energy, boolean isRadius) {
        if(isRadius) {
                if(energy < 256) {
                    energyColor = new Color(0,energy,255);
                }
                else if(energy >= 256 && energy < 512) {
                    energyColor = new Color(0,255,(255 - energy % 256));
                }
                else if(energy >= 512 && energy < 768) {
                    energyColor = new Color(energy % 256,255,0);
                }
                else if(energy >= 768 && energy<1024) {
                    energyColor = new Color(255, (255 - energy % 256), 0);
                }
                else {
                    energyColor = new Color(255 - energy % 256, 0,0);
                }

        } else {
            switch (energy) {
                case 0:
                    energyColor = new Color(30,200,235);
                    break;
                case 1:
                    energyColor = new Color(225,120,235);
                    break;
                case 2:
                    energyColor = new Color(114,36,209);
                    break;
                case 3:
                    energyColor = new Color(255,250,12);
                    break;
                case 4:
                    energyColor = new Color(0,0,0);
                    break;
                case 5:
                    energyColor = new Color(255,76,46);
                    break;
                case 6:
                    energyColor = new Color(255,76,227);
                    break;
                case 7:
                    energyColor = new Color(1,107,227);
                    break;
                case 8:
                    energyColor = new Color(1,207,179);
                    break;
            }
        }
    }

    public Color getEnergyColor() {
        return energyColor;
    }

    public Color getNextColor() {
        return nextColor;
    }
}
