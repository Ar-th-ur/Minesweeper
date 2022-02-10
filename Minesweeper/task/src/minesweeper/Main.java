package minesweeper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many mines do you want on the field?");
        int countOfMines = scanner.nextInt();

        MineSweeperGame game = new MineSweeperGame(countOfMines);
        game.start();
    }
}
