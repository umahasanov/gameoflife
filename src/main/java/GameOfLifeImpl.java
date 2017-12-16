
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeImpl implements GameOfLife {


    @Override
    public List<String> play(String inputFile) {
        List<String> list = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String[] arr = reader.readLine().split(" ");
            int size = Integer.parseInt(arr[0]);
            int numberOfIterations = Integer.parseInt(arr[1]);
            boolean[][] array = new boolean[size][size];
            int a = 0;

            while (reader.ready()) {
                String s = reader.readLine();

                    for (int j = 0; j < size; j++) {
                        array[a][j] = Integer.parseInt(String.valueOf(s.charAt(j))) == 1;
                    }
                    a += 1;

            }
            boolean[][] currentBoard = Main.main(numberOfIterations, array).getCurrentBoard();
            StringBuilder line = new StringBuilder();
            for (boolean[] booleans : currentBoard) {
                for (boolean aBoolean : booleans) {
                    line.append(aBoolean ? 1 : 0);
                }
                list.add(line.toString());
                line.delete(0, booleans.length);
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
