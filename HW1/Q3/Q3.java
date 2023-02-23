//I used ressetta code website to solve this problem. I know we have not read many of this code yet. But I searched
//and can explain the whole code.

import java.util.Collections;
import java.util.Arrays;
import java.util.Scanner;


public class first {
    private final int x;
    private final int y;
    private final int[][] maze;

    public first(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0);
    }

    public void display() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (j == 0 && i == 0) {
                    System.out.print("1e");
                } else {
                    System.out.print((maze[j][i] & 1) == 0 ? "11" : "10");
                }
            }
            System.out.println("1");
            for (int j = 0; j < x; j++) {
                if (j == 0) {
                    System.out.print((maze[j][i] & 8) == 0 ? "1*" : "1*");
                } else {
                    System.out.print((maze[j][i] & 8) == 0 ? "1*" : "0*");
                }
            }
            System.out.println("1");
        }
        for (int j = 0; j < x; j++) {
            if (j == x - 1) {
                System.out.print("1e");
            } else {
                System.out.print("11");
            }
        }
        System.out.println("1");
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

    ;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int soton = input.nextInt();
        int satr = input.nextInt();
        int tekrar = input.nextInt();
        for (int i = 0; i < tekrar; ++i) {
            first maze = new first(satr, soton);
            maze.display();
            System.out.println(" ");
        }
    }

}