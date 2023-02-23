import java.util.Scanner;

public class tamrin1 {
    public static void main(String[] argv) {
        Scanner input = new Scanner(System.in);
        int numberOfMoves = input.nextInt();
        int[][] myGrid = new int[100][100];
        myGrid[49][49] = 1;
        gettingCordinates(myGrid, numberOfMoves, input);
    }

    //-------------------------------------------------------------------------------------------------------------------
    public static void gettingCordinates(int[][] my2DArray, int numberOfMoves, Scanner input) {
        // Scanner inTheMethodInput = new Scanner(System.in);
        int presentXCordinate = 49, presentYCordinate = 49;
        for (int i = 0; i < numberOfMoves; i++) {
            int xCordinate, yCordinate;
            xCordinate = input.nextInt();
            yCordinate = input.nextInt();
            int xPassed = 0, yPassed = 0, outOfRange = 0;
            if (presentXCordinate + xCordinate > 99
                    || presentXCordinate + xCordinate < 0
                    || presentYCordinate + yCordinate > 99
                    || presentYCordinate + yCordinate < 0) outOfRange = 1;
            if (outOfRange == 0) {
                if (xCordinate > 0) {
                    for (int j = 1; j <= xCordinate; j++) {
                        if (my2DArray[presentXCordinate + j][presentYCordinate] == 1) xPassed += 1;
                    }
                    presentXCordinate += xCordinate;
                }
                if (xCordinate <= 0) {
                    for (int j = xCordinate; j < 0; j++) {
                        if (my2DArray[presentXCordinate + j][presentYCordinate] == 1) xPassed += 1;
                    }
                    presentXCordinate += xCordinate;
                }
                if (yCordinate > 0) {
                    for (int j = 1; j <= yCordinate; ++j) {
                        if (my2DArray[presentXCordinate][presentYCordinate + j] == 1) yPassed += 1;
                    }
                    presentYCordinate += yCordinate;
                }
                if (yCordinate <= 0) {
                    for (int j = yCordinate; j < 0; ++j) {
                        if (my2DArray[presentXCordinate][presentYCordinate + j] == 1) yPassed += 1;
                    }
                    presentYCordinate += yCordinate;
                }
            }
            if (outOfRange == 0
                    && xPassed == 0
                    && yPassed == 0) {
                presentXCordinate -= xCordinate;
                presentYCordinate -= yCordinate;
                updatingArray(my2DArray, presentXCordinate, presentYCordinate, xCordinate, yCordinate);
                presentXCordinate += xCordinate;
                presentYCordinate += yCordinate;
            }
            if (outOfRange == 0
                    && (xPassed != 0 || yPassed != 0)) {
                presentXCordinate -= xCordinate;
                presentYCordinate -= yCordinate;
            }
        }
        presentXCordinate += 1;
        presentYCordinate += 1;
        System.out.print(presentXCordinate + " " + presentYCordinate);
    }

    //-------------------------------------------------------------------------------------------------------------------
    public static void updatingArray(int[][] my2DArray, int presentXCordinate, int presentYCordinate, int xCordinate, int yCordinate) {
        if (xCordinate > 0) {
            for (int i = 1; i <= xCordinate; ++i) {
                my2DArray[presentXCordinate + i][presentYCordinate] = 1;
            }
            presentXCordinate += xCordinate;
        }
        if (xCordinate <= 0) {
            for (int i = xCordinate; i < 0; ++i) {
                my2DArray[presentXCordinate + i][presentYCordinate] = 1;
            }
            presentXCordinate += xCordinate;
        }
        if (yCordinate > 0) {
            for (int i = 1; i <= yCordinate; ++i) {
                my2DArray[presentXCordinate][presentYCordinate + i] = 1;
            }
            presentYCordinate += yCordinate;
        }
        if (yCordinate <= 0) {
            for (int i = yCordinate; i < 0; ++i) {
                my2DArray[presentXCordinate][presentYCordinate + i] = 1;
            }
            presentYCordinate += yCordinate;
        }
    }
}