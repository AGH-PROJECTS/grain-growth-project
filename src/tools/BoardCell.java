package tools;

import gui.DrawPanel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BoardCell { //tablica komorek wraz z warunkami brzegowymi
    private Cell[][] mainCells;
    private int cellSize = 0;
    private int height = 0;
    private int width = 0;
    private int neighbourhood = 0;
    private boolean isPeriodical = false;
    private boolean toExit = false;
    private boolean isRadius = false;
    private DrawPanel drawPanel;
    private int radius = 1;
    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!toExit) {
                for (int y = 1; y < height - 1; y++) {
                    for (int x = 1; x < width - 1; x++) {
                        Cell cellCheck = mainCells[y][x];
                        if (cellCheck.getCurrentState() == 0) {
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
                g2d.clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());

                for (int k = 1; k < height - 1; k++) {
                    for (int h = 1; h < width - 1; h++) {
                        Cell cell = get(k, h);
                        cell.setNewValues();

                        if(!cell.getColor().equals(Color.WHITE)) {
                            g2d.setPaint(cell.getColor());
                            g2d.fillRect(cell.getxCord() - cellSize, cell.getyCord() - cellSize, cellSize, cellSize);
                        }

                    }
                }

                setBinaryConditions();

                synchronized (this) {
                    try {
                        wait(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    public BoardCell(int width, int height, int cellSize) {
        this.height = height + 2;
        this.width = width + 2;
        this.mainCells = new Cell[this.height][this.width];
        this.cellSize = cellSize;
        generateCells();
    }

    private void generateCells() { //wygenerowanie całej tablicy
        for(int y = 0; y < height; y++) {
            mainCells[y] = new Cell[width];
            for (int x = 0; x < width; x++) {
                mainCells[y][x] = new Cell(y,x,cellSize, Color.WHITE);
            }
        }
    }

    public void setBinaryConditions() {
        if(isPeriodical) {
            for (int i = 1; i < width - 1; i++) {
                mainCells[0][i].changeState(mainCells[height - 2][i].getCurrentState(),mainCells[height - 2][i].getColor());
                mainCells[height - 1][i].changeState(mainCells[1][i].getCurrentState(),mainCells[height - 2][i].getColor());
            }

            for (int i = 1; i < height - 1; i++) {
                mainCells[i][0].changeState(mainCells[i][width - 2].getCurrentState(),mainCells[i][width - 2].getColor());
                mainCells[i][width - 1].changeState(mainCells[i][1].getCurrentState(),mainCells[i][1].getColor());
            }

            mainCells[0][0].changeState(mainCells[height - 2][width - 2].getCurrentState(),mainCells[height - 2][width - 2].getColor());

            mainCells[height - 1][width - 1].changeState(mainCells[1][1].getCurrentState(),mainCells[1][1].getColor());

            mainCells[0][width - 1].changeState(mainCells[height - 2][1].getCurrentState(),mainCells[height - 2][1].getColor());

            mainCells[height - 1][0].changeState(mainCells[1][width - 2].getCurrentState(),mainCells[1][width - 2].getColor());
        }

    }

    public void changeCellState(int y, int x, int state, Color color) { //odwzorowanie tablicy na dana komorki
        mainCells[y + 1][x + 1].changeState(state,color);
    }

    public Cell get(int y, int x) {
        return this.mainCells[y][x];
    }
    
    public void setNeighbourhood(int neighbourhood) {
        this.neighbourhood = neighbourhood;
        if(neighbourhood == 4) {
            isRadius = true;
        }else {
            isRadius = false;
        }
    }

    private Cell[] getMooreNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[8];

        int i = cell.getIndexY();
        int j = cell.getIndexX();

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
        int i = cell.getIndexY();
        int j = cell.getIndexX();
        neighboursTab[0] = mainCells[i - 1][j];
        neighboursTab[1] = mainCells[i + 1][j];
        neighboursTab[2] = mainCells[i][j + 1];
        neighboursTab[3] = mainCells[i][j - 1];
        return neighboursTab;
    }

    private Cell[] getPentagonalNeighbourhood(Cell cell) {
        Cell[]  neighboursTab = new Cell[5];
        int i = cell.getIndexY();
        int j = cell.getIndexX();

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
        int i = cell.getIndexY();
        int j = cell.getIndexX();

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
        List<Cell> neighboursList = new ArrayList<>();
        int maxWidth = width - 2;
        int maxHeight = height - 2;
        int x = cell.getIndexX();
        int y = cell.getIndexY();
        int Y = cell.getCenterY();
        int X = cell.getCenterX();
        int YNext, XNext;
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
                if (!isPeriodical)
                {
                    if (Math.pow(get(YNext, XNext).getCenterX() - X, 2) + Math.pow(get(YNext, XNext).getCenterY() - Y, 2) <= Math.pow(radius * cellSize, 2) && get(YNext, XNext).getCurrentState() != 0)
                    {
                       neighboursList.add(get(YNext, XNext));
                    }
                }
                else
                {
                    if (Math.pow(j * cellSize + (get(YNext, XNext).getCenterX() % cellSize) - X, 2) + Math.pow(i * cellSize + (get(YNext, XNext).getCenterY() % cellSize) - Y, 2) <= Math.pow(radius * cellSize, 2) && get(YNext, XNext).getCurrentState() != 0)
                    {
                        neighboursList.add(get(YNext, XNext));
                    }
                }

            }
        }
        Cell[] cells = new Cell[neighboursList.size()];
        return neighboursList.toArray(cells);
    }

    public void startSimulation(DrawPanel drawPanel, boolean toExit) {
        this.drawPanel = drawPanel;
        if(toExit) {
            t.start();
        } else {
            this.toExit = true;
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

        if(list.size() > 0) {
            list.sort(Map.Entry.comparingByValue());
            Collections.reverse(list);
            int amount = list.stream().findFirst().get().getValue();
            list = list.stream().filter(w -> w.getValue() == amount).collect(Collectors.toList());
            Collections.shuffle(list);
            color = list.stream().findFirst().get().getKey();
        }
        Collections.shuffle(list);

        return color;
    }

    public void setPeriodical(boolean periodical) {
        isPeriodical = periodical;
    }

    public void setEnergy(List<Cell> cellList, double kt) {
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        cellList.forEach(cell-> {
            List<Cell> neighbours = new ArrayList<>();
            switch (neighbourhood) {
                case 0:
                    neighbours = Arrays.asList(getMooreNeighbourhood(cell));
                    break;
                case 1:
                    neighbours = Arrays.asList(getVonNeumannNeighbourhood(cell));
                    break;
                case 2:
                    neighbours = Arrays.asList(getHexagonalNeighbourhood(cell));
                    break;
                case 3:
                    neighbours = Arrays.asList(getPentagonalNeighbourhood(cell));
                    break;
                case 4:
                    neighbours = Arrays.asList(getRadianNeighbourhood(cell));
                    break;
            }
            getEnergy(neighbours,cell,kt);
            if(!cell.getColor().equals(Color.WHITE)) {
                if(!cell.getColor().equals(cell.getNextColor())) {
                    cell.setNewValues();
                    g2d.setPaint(cell.getColor());
                    g2d.fillRect(cell.getxCord() - cellSize, cell.getyCord() - cellSize, cellSize, cellSize);
                }

            }
        });

        setBinaryConditions();
    }

    private void getEnergy(List<Cell> neighbours, Cell cellCheck, double kt) {
        List<Cell> cells = neighbours.stream()
                .filter(cell -> !cell.getColor().equals(Color.WHITE)).collect(Collectors.toList());

        long energy = cells
                .stream()
                .filter(cell -> !cell.getColor().equals(cellCheck.getColor()))
                .count();

        int randomNeighbour = (int) (Math.random() * (double) (neighbours.size() - 1));

        Cell cellNeighbour = neighbours.get(randomNeighbour);

        long hipoteticEnergy = cells
                .stream()
                .filter(cell -> !cell.getColor().equals(cellNeighbour.getColor()))
                .count();

        int deltaEnergy = (int) (hipoteticEnergy - energy);

        if(getProbability(deltaEnergy,kt) > Math.random()) {
            cellCheck.setNextColor(cellNeighbour.getColor());
            cellCheck.setNextState(cellNeighbour.getCurrentState());
            cellCheck.setCurrentEnergy((int)energy, isRadius);
        }

    }

    private double getProbability(int deltaEnergy, double kt) {
        if(deltaEnergy <= 0 ){
            return 1;
        } else {
            return Math.exp(-deltaEnergy/kt);
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
