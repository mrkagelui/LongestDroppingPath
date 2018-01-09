import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LongestDroppingPath {
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

    private static class Result {
        int distance;
        int drop;

        public Result (int dist, int drop) {
            this.distance = dist;
            this.drop = drop;
        }
    }

    private static Result getDistanceAndDropFromPoint(int row, int column, int[][] map) {
        return null;
    }

    public static void main(String[] args) {
        try {
            int[][] LocationMap = readMap("map.txt");
            System.out.println("Read in [" + LocationMap.length + "] by [" + LocationMap[0].length + "] numbers.");
            Result resultCombo = new Result(Integer.MIN_VALUE, Integer.MIN_VALUE);
            for (int i = 0; i < LocationMap.length; i++){
                for (int j = 0; j < LocationMap[0].length; j++) {
                    Result tempResult = getDistanceAndDropFromPoint(i, j, LocationMap);
                    if (tempResult.distance > resultCombo.distance) {
                        resultCombo = tempResult;
                    }
                    else if (tempResult.distance == resultCombo.distance && tempResult.drop > resultCombo.drop) {
                        resultCombo = tempResult;
                    }
                }
            }
            System.out.println("Done.");
        }
        catch (IOException e) {
            System.out.println("Exception reading map: " + e.getMessage());
        }
    }
}
