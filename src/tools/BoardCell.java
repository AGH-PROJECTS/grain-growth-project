package tools;

import gui.DrawPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BoardCell { //tablica komorek wraz z warunkami brzegowymi
    private List<List<Rectangle2D>> boardRects; // dodaje komorki zgodnie stane faktycznym
    private Cell[][] mainCells;
    private Cell[][] cells;
    private int rows = 0;
    private int cols = 0;
    private int x = 0;
    private int y = 0;
    private int cellSize = 0;
    private int neighbourhood = 0;
    private int radian = 0;

    public BoardCell(List<List<Rectangle2D>> boardRects, int size) {
        this.boardRects = boardRects;
        this.cellSize = size;
        this.rows = boardRects.size() + 2;
        this.cols = boardRects.get(0).size() + 2;
        this.mainCells = new Cell[rows][cols];
        this.cells = new Cell[rows - 2][cols - 2];
        generateCells();
    }

    private void generateCells() { //wygenerowanie całej tablicy
        for(int i = 1; i < rows - 1; i++) {
            mainCells[i] = new Cell[cols];
            for (int j = 1; j < cols - 1; j++) {
                mainCells[i][j] = new Cell(j,i,cellSize, Color.WHITE);
            }
        }

        for(int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                cells[i - 1][j - 1] = new Cell(j -1,i - 1,cellSize, Color.WHITE);
                cells[i - 1][j - 1].changeState(mainCells[i][j].getCurrentState(),mainCells[i][j].getColor());
            }
        }
    }

    public void changeCellState(int i, int j, int state, Color color) { //odwzorowanie tablicy na dana komorki
        mainCells[i + 1][j + 1].changeState(state,color);
        cells[i][j].changeState(state,color);
    }

    public Cell get(int x, int y) {
        return this.cells[x][y];
    }

    public void setNeighbourhood(int neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setBinaryConditions(int isPeriodical) {
        /*for(int i = 0; i < rows; i++) {
            mainCells[i + 1] = new Cell[rows + 2];
            for (int j = 0; j < cols; j++) {
                mainCells[i+1][j+1] = new Cell(size, i, j);
                mainCells[i+1][j+1].changeState(cells[i][j].getCurrentState(),cells[i][j].getColor());
            }
        }*/

        if(isPeriodical == 0) {

            for (int i = 0 ; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(mainCells[i][j] != null) {
                        System.out.print(mainCells[i][j].getCurrentState() + "\t");
                    } else {
                        System.out.print("-1\t");
                    }

                }
                System.out.println("\n");
            }

            System.out.println("____________________________\n");
            for (int i = 1; i < rows - 1; i++) {
                mainCells[0][i] = new Cell(i,0, cellSize,Color.WHITE);
                mainCells[0][i].changeState(mainCells[(rows - 2)][(i)].getCurrentState(), mainCells[(rows - 2)][(i)].getColor());
                mainCells[rows - 1][i] = new Cell(i, rows - 1, cellSize,Color.WHITE);
                mainCells[rows - 1][i].changeState(mainCells[0][i].getCurrentState(),mainCells[0][i].getColor());
            }

            for (int i = 0 ; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(mainCells[i][j] != null) {
                        System.out.print(mainCells[i][j].getCurrentState() + "\t");
                    } else {
                        System.out.print("-1\t");
                    }

                }
                System.out.println("\n");
            }

            System.out.println("____________________________\n");
            for (int i = 1; i < cols - 1; i++) {
                mainCells[i][0] = new Cell(0, i, cellSize,Color.WHITE);
                mainCells[i][0].changeState(mainCells[i][cols - 2].getCurrentState(),mainCells[i][cols - 2].getColor());
                mainCells[i][cols - 1] = new Cell(cols - 1,i, cellSize,Color.WHITE);
                mainCells[i][cols - 1].changeState(mainCells[i][0].getCurrentState(),mainCells[i][0].getColor());
            }

            for (int i = 0 ; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(mainCells[i][j] != null) {
                        System.out.print(mainCells[i][j].getCurrentState() + "\t");
                    } else {
                        System.out.print("-1\t");
                    }

                }
                System.out.println("\n");
            }
            System.out.println("____________________________\n");
            mainCells[0][0] = new Cell(0, 0, cellSize,Color.WHITE);
            mainCells[0][0].changeState(mainCells[rows - 2][cols - 2].getCurrentState(),mainCells[rows - 2][cols - 2].getColor());

            mainCells[rows - 1][cols - 1] = new Cell(cols - 1,rows - 1, cellSize, Color.WHITE);
            mainCells[rows - 1][cols - 1].changeState(mainCells[0][0].getCurrentState(),mainCells[0][0].getColor());

            mainCells[0][cols - 1] = new Cell(cols - 1,0, cellSize, Color.WHITE);
            mainCells[0][cols - 1].changeState(mainCells[rows - 2][0].getCurrentState(),mainCells[rows - 2][0].getColor());

            mainCells[rows - 1][0] = new Cell(0, rows - 1, cellSize, Color.WHITE);
            mainCells[rows - 1][0].changeState(mainCells[0][cols - 2].getCurrentState(),mainCells[0][cols - 2].getColor());

            for (int i = 0 ; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(mainCells[i][j] != null) {
                        System.out.print(mainCells[i][j].getCurrentState() + "\t");
                    } else {
                        System.out.print("-1\t");
                    }

                }
                System.out.println("\n");
            }
            System.out.println("____________________________\n");

        }
        else {
            for (int i = 0; i < cols; i++) {
                mainCells[0][i] = new Cell(0,i, cellSize, Color.WHITE);
                mainCells[rows - 1][i] = new Cell(rows - 1,i, cellSize, Color.WHITE);
            }

            for (int i = 0; i < rows; i++) {
                mainCells[i][0] = new Cell(i,0, cellSize, Color.WHITE);
                mainCells[i][cols - 1] = new Cell(i, cols - 1, cellSize, Color.WHITE);
            }

        }

        /*for (int i = 0 ; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(mainCells[i][j].getCurrentState() + "\t");
            }
            System.out.println("\n");
        }*/
    }

    public List<Cell> getAliveCells() {
        List<Cell> aliveCells = new LinkedList<>();
        Arrays.stream(cells).forEach(cells1 -> {
            Arrays.stream(cells1).forEach(cell -> {
                if(cell.getCurrentState() == 1) {
                    aliveCells.add(cell);
                }
            });
        });
        return aliveCells;
    }

    private Cell[] getMooreNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[8];

        int i = cell.getIndexX() + 1;
        int j = cell.getIndexY() + 1;

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
        int i = cell.getIndexX() + 1;
        int j = cell.getIndexY() + 1;
        neighboursTab[0] = mainCells[i - 1][j];
        neighboursTab[1] = mainCells[i + 1][j];
        neighboursTab[2] = mainCells[i][j + 1];
        neighboursTab[3] = mainCells[i][j - 1];
        return neighboursTab;
    }

    private Cell[] getPentagonalNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[5];
        int i = cell.getIndexX() + 1;
        int j = cell.getIndexY() + 1;

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
        int i = cell.getIndexX() + 1;
        int j = cell.getIndexY() + 1;

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
        AtomicBoolean isColored = new AtomicBoolean(false);
        while (!isColored.get())
        {
            for (int i = 0; i < rows - 2; i++) {
                for(int j = 0; j < cols - 2; j++) {
                    isColored.set(true);
                    if(cells[i][j].getColor().equals(Color.WHITE)) {
                        isColored.set(false);
                        break;
                    }
                }
            }

            for(int i = 0; i < rows - 2; i++) {
                for(int j = 0; j < cols - 2; j++) {
                    Cell cellCheck = cells[i][j];
                    if(cellCheck.getColor().equals(Color.WHITE)) {
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
                        cellCheck.changeState(1, color);
                        drawPanel.fillRect(cells[i][j], boardRects);
                    }
                }
            }

            for(int i = 0; i < rows - 2; i++) {
                for(int j = 0; j < cols - 2; j++) {
                    mainCells[j+1][i+1].changeState(cells[i][j].getCurrentState(), cells[i][j].getColor());
                }
            }

            for (int i = 0 ; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(mainCells[i][j].getCurrentState() + "\t");
                }
                System.out.println("\n");
            }
            System.out.println("\n\n");

            synchronized (this) {
                try {
                    wait(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
        this.radian = radian;
    }
}
