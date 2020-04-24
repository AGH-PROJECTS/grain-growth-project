package tools;

import gui.DrawPanel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BoardCell { //tablica komorek wraz z warunkami brzegowymi
    private Cell[][] mainCells;
    private int cellSize = 0;
    private int rows = 0;
    private int cols = 0;
    private int neighbourhood = 0;
    private boolean isPeriodical = false;

    public BoardCell(int rows, int cols, int cellSize) {
        this.rows = rows + 2;
        this.cols = cols + 2;
        this.mainCells = new Cell[this.rows][this.cols];
        this.cellSize = cellSize;
        generateCells();
    }

    private void generateCells() { //wygenerowanie całej tablicy
        for(int i = 0; i < rows; i++) {
            mainCells[i] = new Cell[cols];
            for (int j = 0; j < cols; j++) {
                mainCells[i][j] = new Cell(i,j,cellSize, Color.WHITE);
            }
        }
    }

    public void setBinaryConditions() {
        /*for(int i = 0; i < rows; i++ ) {
            for(int j = 0; j < cols; j++) {
                System.out.print(mainCells[i][j].getCurrentState() + "\t");
            }
            System.out.println("\n");
        }*/
        if(isPeriodical) {
            for (int i = 1; i < cols - 1; i++) {
                mainCells[0][i].changeState(mainCells[rows - 2][i].getCurrentState(),mainCells[rows - 2][i].getColor());
                mainCells[rows - 1][i].changeState(mainCells[1][i].getCurrentState(),mainCells[rows - 2][i].getColor());
            }

            for (int i = 1; i < rows - 1; i++) {
                mainCells[i][0].changeState(mainCells[i][cols - 2].getCurrentState(),mainCells[i][cols - 2].getColor());
                mainCells[i][cols - 1].changeState(mainCells[i][1].getCurrentState(),mainCells[i][1].getColor());
            }

            mainCells[0][0].changeState(mainCells[rows - 2][cols - 2].getCurrentState(),mainCells[rows - 2][cols - 2].getColor());

            mainCells[rows - 1][cols - 1].changeState(mainCells[1][1].getCurrentState(),mainCells[1][1].getColor());

            mainCells[0][cols - 1].changeState(mainCells[rows - 2][1].getCurrentState(),mainCells[rows - 2][1].getColor());

            mainCells[rows - 1][0].changeState(mainCells[1][cols - 2].getCurrentState(),mainCells[1][cols - 2].getColor());
        }

    }

    public void changeCellState(int i, int j, int state, Color color) { //odwzorowanie tablicy na dana komorki
        mainCells[i + 1][j + 1].changeState(state,color);
    }

    public Cell get(int x, int y) {
        return this.mainCells[x][y];
    }
    
    public void setNeighbourhood(int neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public List<Cell> getAliveCells() {
        List<Cell> aliveCells = new LinkedList<>();
        /*Arrays.stream(cells).forEach(cells1 -> {
            Arrays.stream(cells1).forEach(cell -> {
                if(cell.getCurrentState() == 1) {
                    aliveCells.add(cell);
                }
            });
        });*/
        return aliveCells;
    }

    private Cell[] getMooreNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[8];

        int i = cell.getIndexX();
        int j = cell.getIndexY();

        neighboursTab[0] = mainCells[i - 1][j - 1];
        neighboursTab[1] = mainCells[i - 1][j];
        neighboursTab[2] = mainCells[i - 1][j + 1];
        neighboursTab[3] = mainCells[i][j - 1];
        neighboursTab[4] = mainCells[i][j + 1];
        neighboursTab[5] = mainCells[i + 1][j - 1];
        neighboursTab[6] = mainCells[i + 1][j];
        neighboursTab[7] = mainCells[i + 1][j + 1];

        return neighboursTab;
    }

    private Cell[] getVonNeumannNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[4];
        int i = cell.getIndexX();
        int j = cell.getIndexY();
        neighboursTab[0] = mainCells[i - 1][j];
        neighboursTab[1] = mainCells[i + 1][j];
        neighboursTab[2] = mainCells[i][j + 1];
        neighboursTab[3] = mainCells[i][j - 1];
        return neighboursTab;
    }

    private Cell[] getPentagonalNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[5];
        int i = cell.getIndexX();
        int j = cell.getIndexY();

        int max = 4;
        int min = 1;
        int range = max - min + 1;
        int kind = (int) (Math.random() * range) + min;

        switch (kind) {
            case 1:
                neighboursTab[0] = mainCells[i - 1][j];
                neighboursTab[1] = mainCells[i + 1][j];
                neighboursTab[2] = mainCells[i][j - 1];
                neighboursTab[3] = mainCells[i - 1][j - 1];
                neighboursTab[4] = mainCells[i + 1][j - 1];
                break;
            case 2:
                neighboursTab[0] = mainCells[i - 1][j];
                neighboursTab[1] = mainCells[i + 1][j];
                neighboursTab[2] = mainCells[i - 1][j + 1];
                neighboursTab[3] = mainCells[i][j + 1];
                neighboursTab[4] = mainCells[i + 1][j + 1];
                break;
            case 3:
                neighboursTab[0] = mainCells[i][j - 1];
                neighboursTab[1] = mainCells[i][j + 1];
                neighboursTab[2] = mainCells[i + 1][j - 1];
                neighboursTab[3] = mainCells[i + 1][j];
                neighboursTab[4] = mainCells[i + 1][j + 1];
                break;
            case 4:
                neighboursTab[0] = mainCells[i][j - 1];
                neighboursTab[1] = mainCells[i][j + 1];
                neighboursTab[2] = mainCells[i - 1][j - 1];
                neighboursTab[3] = mainCells[i - 1][j];
                neighboursTab[4] = mainCells[i - 1][j + 1];
                break;
        }
        return neighboursTab;
    }

    private Cell[] getHexagonalNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[6];
        int i = cell.getIndexX();
        int j = cell.getIndexY();

        int max = 2;
        int min = 1;
        int range = max - min + 1;
        int kind = (int) (Math.random() * range) + min;

        switch (kind) {
            case 1:
                neighboursTab[0] = mainCells[i][j - 1];
                neighboursTab[1] = mainCells[i][j + 1];
                neighboursTab[2] = mainCells[i - 1][j];
                neighboursTab[3] = mainCells[i + 1][j];
                neighboursTab[4] = mainCells[i - 1][j + 1];
                neighboursTab[5] = mainCells[i + 1][j - 1];
                break;
            case 2:
                neighboursTab[0] = mainCells[i][j - 1];
                neighboursTab[1] = mainCells[i][j + 1];
                neighboursTab[2] = mainCells[i - 1][j];
                neighboursTab[3] = mainCells[i + 1][j];
                neighboursTab[4] = mainCells[i - 1][j - 1];
                neighboursTab[5] = mainCells[i + 1][j + 1];
                break;
        }

        return neighboursTab;
    }

    private Cell[] getRadianNeighbourhood(Cell cell) {
      /*  Ellipse2D r = new Ellipse2D.Double(cell.getCenterX() - radian/2, cell.getCenterY() - radian/2,radian,radian);
        List<Cell> whiteCells = new LinkedList<>();

        *//*aliveCells.forEach(c -> {
            *//**//*g2d.setPaint(Color.black);
            g2d.fillRect(cell.getCenterX() - 1,cell.getCenterY() - 1,3,3);*//**//*
            if(c.getColor().equals(Color.WHITE)) {
                if (r.contains(c.getCenterX(), c.getCenterY())) {
                    whiteCells.add(cell);
                }
            }*//*

        });
        Cell[] cells = new Cell[whiteCells.size()];
        whiteCells.toArray(cells);
        return cells;*/
        return null;
    }

    public void startSimulation(DrawPanel drawPanel) {
            boolean isNotAlive = true;
            while(isNotAlive) {
                for(int i = 1; i < rows - 1; i++) {
                    for(int j = 1; j < cols - 1; j++) {
                        Cell cellCheck = mainCells[i][j];
                        if(cellCheck.getCurrentState() == 0) {
                            Cell[] neighbours = new Cell[0];
                            switch (neighbourhood) {
                                case 0:
                                    neighbours = getMooreNeighbourhood(cellCheck);
                                    break;
                                case 1:
                                    neighbours = getVonNeumannNeighbourhood(cellCheck);
                                    break;
                                case 2:
                                    neighbours = getHexagonalNeighbourhood(cellCheck);
                                    break;
                                case 3:
                                    neighbours = getPentagonalNeighbourhood(cellCheck);
                                    break;
                                case 4:
                                    neighbours = getRadianNeighbourhood(cellCheck);
                                    break;
                            }
                            Color color = getTheMostFrequentlyColor(neighbours);
                            cellCheck.setNextColor(color);
                            cellCheck.setNextState(1);
                        }
                    }
                }

                Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
                g2d.clearRect(0,0,drawPanel.getWidth(),drawPanel.getHeight());

                for(int k = 1; k < rows - 1; k++) {
                    for(int h = 1; h < cols - 1; h++) {
                        Cell cell = mainCells[k][h];
                        cell.setNewValues();
                        g2d.setPaint(cell.getColor());
                        g2d.fillRect(cell.getxCord() - cellSize, cell.getyCord() - cellSize, cellSize, cellSize);
                    }
                }

                setBinaryConditions();

                synchronized (this) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            /*    //System.out.println("\n\n");
                for(int i = 1; i < rows - 1; i++ ) {
                    for(int j = 1; j < cols - 1; j++) {
                        //System.out.print(mainCells[i][j].getCurrentState() + "\t");
                        if(mainCells[i][j].getColor().equals(Color.WHITE) ) {
                            isNotAlive = true;
                            break;
                        } else {
                            isNotAlive = false;
                        }
                    }
                    //System.out.println("\n");
                }*/
            }
    }

    private Color getTheMostFrequentlyColor(Cell[] tabCell) {
        Color color = Color.WHITE;

        Map<Color,Integer> colorsMap = new HashMap<>();
        Arrays.stream(tabCell).forEach(cell -> {
            if(colorsMap.get(cell.getColor()) != null) {
                colorsMap.computeIfPresent(cell.getColor(),(c,l)-> l += 1);
            }
            else {
                colorsMap.put(cell.getColor(),1);
            }
        });

        colorsMap.remove(Color.WHITE);

        List<Map.Entry<Color,Integer>> list = new LinkedList<>(colorsMap.entrySet());

        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        if(list.size() > 0) {
            color = list.stream().findFirst().get().getKey();
        }

        return color;
    }

    public void setRadian(int radian) {
        //this.radian = radian;
    }

    public void setCell(int i, int j, int cellSize, Color white) {
        mainCells[i][j] = new Cell(i,j,cellSize, Color.WHITE);
    }

    public void setPeriodical(boolean periodical) {
        isPeriodical = periodical;
    }
}
