package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char[][] matrix = {
                {'_','_','_',},
                {'_','_','_',},
                {'_','_','_',}
        };
        //print matrix in the beginning
        printBoardState(matrix);

        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        int stepCounter = 2;
        do {
            //user input
            int[] userInArr = checkUserInput(matrix);
            fillMatrix(matrix,userInArr,stepCounter);
            //print matrix
            printBoardState(matrix);
            //checking game state
            String output = checkWinCondition(matrix);
            ++stepCounter;

            if (output.equals("X wins") || output.equals("O wins") || output.equals("Draw") ){
                System.out.println(output);
                end = true;
            }

        } while(!end);

        scanner.close();
    }

    private static void printBoardState(char[][] matrix) {
        System.out.println("---------");
        for (char[] chars : matrix) {
            System.out.print('|' + " ");
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.print('|');
            System.out.println();
        }
        System.out.println("---------");
    }

    private static void fillMatrix(char[][] matrix, int[] userInArr, int step) {
        int i = userInArr[0];
        int j = userInArr[1];
        char player = step % 2 == 0 ? 'X' : 'O';
        matrix[i-1][j-1] = player;
    }

    private static int[] checkUserInput(char[][] matrix) {
        boolean key = false;
        int first = 0;
        int second = 0;
        do {
            Scanner scanner = new Scanner(System.in);
            String userIn = scanner.nextLine();
            try {
                String[] userArr = userIn.split(" ");
                first = Integer.parseInt(userArr[0]);
                second = Integer.parseInt(userArr[1]);
                if ((first - 1 > 2 || first - 1 < 0) || (second - 1 > 2 || second - 1 < 0)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                if (matrix[first-1][second-1] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            key = true;
        } while (!key);
        return new int[]{first, second};
    }

    private static String checkWinCondition(char[][] matrix) {
        String output;
        boolean winX = false;
        boolean winO = false;
        int resDiagonal1 = matrix[0][0] + matrix[1][1] + matrix[2][2];
        int resDiagonal2 = matrix[0][2] + matrix[1][1] + matrix[2][0];
        for (int i = 0; i < 3; i++) {
            int resRow = matrix[i][0] + matrix[i][1] + matrix[i][2];
            int resColumn = matrix[0][i] + matrix[1][i] + matrix[2][i];
            if (resDiagonal1 == 264 || resDiagonal2 == 264 || resRow == 264 || resColumn == 264) {
                winX = true;
            }
            if (resDiagonal1 == 237 || resDiagonal2 == 237 || resRow == 237 || resColumn == 237) {
                winO = true;
            }
        }

        //counting input char`s
        int countX = inputCounter(matrix, 'X');
        int countO = inputCounter(matrix, 'O');
        int countEmpty = inputCounter(matrix, '_');

        //checking game state
        //Impossible - (X wins && O wins) or countX >> countO and vice-versa
        // X wins - 'X' + 'X' + 'X' = 264
        // O wins - 'O' + 'O' + 'O' = 237
        //Draw - countX + countO = 9 && neither X nor O wins
        //Game not finished - neither side wins but the grid still has empty cells

        if (Math.abs(countX - countO) > 1) {
            output = "Impossible";
        } else if (winX && winO) {
            output = "Impossible";
        } else if (winX) {
            output = "X wins";
        } else if (winO) {
            output = "O wins";
        } else if ((countX + countO == 9) && !winO && !winX) {
            output = "Draw";
        } else if (countEmpty > 0 && !winX && !winO) {
            output = "Game not finished";
        } else { output = "Wierd state of game";}

        return output;
    }

    private static int inputCounter(char[][] array, char c) {
        int counter = 0;
        for (int l = 0; l < 3; l++) {
            for (int m = 0; m < 3; m++) {
                if (c == array[l][m]) {
                    counter += 1;
                }
            }
        }
        return counter;
    }

}