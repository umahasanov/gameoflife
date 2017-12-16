import java.util.ArrayList;

public class Main {

    static Universe main(int numberOfIterations, boolean[][] init) throws InterruptedException {
        Universe universe = new Universe(init);
        for (int k = 0; k < numberOfIterations; ++k) {
            universe.update();
        }
        return universe;
    }


    static class Universe extends AbstractUniverse {
        private static Calculator[] calculators;
        private Thread[] calculatorThreads;


        Universe(boolean[][] init) {
            super(init);
            calculators = new Calculator[numberOfThreads];
            for (int i = 0; i < numberOfThreads; ++i) {
                calculators[i] = new Calculator(getCellsForThread(i));
            }
        }

        ArrayList<int[]> getCellsForThread(int k) {
            int rowsPerThread = gridSize / numberOfThreads;
            int maxThreadNumberHavingOneAdditionalRow = gridSize % rowsPerThread;
            int itsBetterToNameItVariableFoo = rowsPerThread * numberOfThreads;

            ArrayList<int[]> cells = new ArrayList<>();


            for (int i = 0; i < gridSize; ++i) {
                if (i >= rowsPerThread * k && i < rowsPerThread * (k + 1)) {
                    for (int j = 0; j < gridSize; ++j) {
                        cells.add(new int[]{i, j});
                    }
                }
            }
            if (k < maxThreadNumberHavingOneAdditionalRow) {
                for (int j = 0; j < gridSize; ++j) {
                    cells.add(new int[]{itsBetterToNameItVariableFoo + k, j});
                }
            }

            return cells;
        }


        void update() throws InterruptedException {
            calculatorThreads = new Thread[numberOfThreads];
            for (int i = 0; i < numberOfThreads; ++i) {
                calculatorThreads[i] = new Thread(calculators[i]);
            }
            for (int i = 0; i < numberOfThreads; ++i) {
                calculatorThreads[i].start();
            }
            for (int i = 0; i < numberOfThreads; ++i) {
                calculatorThreads[i].join();
            }

            currentBoard = nextBoard;
            nextBoard = new boolean[gridSize][gridSize];
        }

        private class Calculator implements Runnable {
            private ArrayList<int[]> indexes;

            Calculator(ArrayList<int[]> indexes) {
                this.indexes = indexes;
            }

            @Override
            public void run() {
                for (int[] index : indexes) {
                    calculateNextState(index[0], index[1]);
                }
            }
        }
    }

}
