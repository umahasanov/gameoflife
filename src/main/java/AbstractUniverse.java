abstract class AbstractUniverse {
    static boolean[][] currentBoard;
    static boolean[][] nextBoard;
    final int numberOfThreads = 4;
    int gridSize;

    AbstractUniverse(boolean[][] init) {
        gridSize = init.length;
        currentBoard = new boolean[gridSize][gridSize];
        nextBoard = new boolean[gridSize][gridSize];
        currentBoard = init;
    }

    private int getNeighboursSum(int i, int j) {
        // south
        int south = i + 1;
        south = south > gridSize - 1 ? 0 : south;

        // north
        int north = i - 1;
        north = north < 0 ? gridSize - 1 : north;

        // east
        int east = j + 1;
        east = east > gridSize - 1 ? 0 : east;

        // west
        int west = j - 1;
        west = west < 0 ? gridSize - 1 : west;


        int[][] neighboursIndexes = new int[][]{
                new int[]{i, east},
                new int[]{i, west},
                new int[]{north, j},
                new int[]{south, j},
                new int[]{north, east},
                new int[]{north, west},
                new int[]{south, east},
                new int[]{south, west}
        };

        int sum = 0;
        for (int[] indexes : neighboursIndexes) {
            if (currentBoard[indexes[0]][indexes[1]]) {
                sum += 1;
            }
            if (sum > 3) {
                return -1;
            }
        }
        return sum;
    }

    void calculateNextState(int i, int j) {
        int neighboursSum = getNeighboursSum(i, j);
        boolean currentValue = currentBoard[i][j];
        nextBoard[i][j] = currentValue ? (neighboursSum > 1) : (neighboursSum == 3);
    }

    public boolean[][] getCurrentBoard() {
        return currentBoard;
    }

}
