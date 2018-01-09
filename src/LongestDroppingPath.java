import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LongestDroppingPath {
    static int[][] map;
    private static class Result {
        int distance;
        int drop;

        public Result (int dist, int drop) {
            this.distance = dist;
            this.drop = drop;
        }
    }
    static Result[][] resultMatrix;

    private static int[][] readMap(String mapLocation) throws IOException{
        File mapFile = new File(mapLocation);
        Scanner sc = new Scanner(mapFile);
        int length = sc.nextInt();
        int height = sc.nextInt();
        int[][] returnMap = new int[height][length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                returnMap[i][j] = sc.nextInt();
            }
        }
        return returnMap;
    }

    private static Result getDistanceAndDropFromPoint(int row, int column) {
        if (resultMatrix[row][column] != null) {
            return resultMatrix[row][column];
        }
        if (row < 0 || row >= map.length || column < 0 || column >= map[0].length) {
            return new Result(0, 0);
        }
        List<Result> allDirectionResults = new ArrayList<>();
        if (column < map[0].length - 1 && map[row][column] > map[row][column+1]) {
            Result resultRight = getDistanceAndDropFromPoint(row, column+1);
            allDirectionResults.add(new Result(resultRight.distance + 1,
                    resultRight.drop + map[row][column] - map[row][column+1]));
        }
        if (row < map.length - 1 && map[row][column] > map[row+1][column]) {
            Result resultDown = getDistanceAndDropFromPoint(row+1, column);
            allDirectionResults.add(new Result(resultDown.distance + 1,
                    resultDown.drop + map[row][column] - map[row+1][column]));
        }
        if (row > 0 && map[row][column] > map[row-1][column]) {
            Result resultUp = getDistanceAndDropFromPoint(row-1, column);
            allDirectionResults.add(new Result(resultUp.distance + 1,
                    resultUp.drop + map[row][column] - map[row-1][column]));
        }
        if (column > 0 && map[row][column] > map[row][column-1]) {
            Result resultLeft = getDistanceAndDropFromPoint(row, column-1);
            allDirectionResults.add(new Result(resultLeft.distance + 1,
                    resultLeft.drop + map[row][column] - map[row][column-1]));
        }
        Result best = (allDirectionResults.size() != 0) ? maxResult(allDirectionResults.toArray(new Result[allDirectionResults.size()]))
                : new Result(1, 0);
        resultMatrix[row][column] = best;
        return resultMatrix[row][column];
    }

    private static Result maxResult(Result... resultArray) {
        if (0 == resultArray.length) {
            return new Result(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        Result bestResult = resultArray[0];
        for (Result r : resultArray) {
            if (r.distance > bestResult.distance) {
                bestResult = r;
            }
            else if (r.distance == bestResult.distance && r.drop > bestResult.drop) {
                bestResult = r;
            }
        }
        return bestResult;
    }

    public static void main(String[] args) {
        try {
            map = readMap("map.txt");
            System.out.println("Read in [" + map.length + "] by [" + map[0].length + "] numbers.");
            resultMatrix = new Result[map.length][map[0].length];
            Result bestResult = new Result(Integer.MIN_VALUE, Integer.MIN_VALUE);

            for (int i = 0; i < map.length; i++){
                for (int j = 0; j < map[0].length; j++) {
                    Result tempResult = getDistanceAndDropFromPoint(i, j);
                    bestResult = maxResult(bestResult, tempResult);
                }
            }
            System.out.println("Distance :[" + bestResult.distance + "], Drop: [" + bestResult.drop + "]");
            System.out.println("Done.");
        }
        catch (IOException e) {
            System.out.println("Exception reading map: " + e.getMessage());
        }
    }
}
