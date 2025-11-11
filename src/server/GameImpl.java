package server;

import common.IGame;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameImpl extends UnicastRemoteObject implements IGame {
    private int[][] board1 = new int[5][5];
    private int[][] board2 = new int[5][5];
    private String player1, player2, turn;

    public GameImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean registerPlayer(String name) {
        if (player1 == null) {
            player1 = name;
            turn = player1;
            System.out.println("Jogador 1 conectado: " + name);
            return true;
        } else if (player2 == null) {
            player2 = name;
            System.out.println("Jogador 2 conectado: " + name);
            return true;
        }
        return false;
    }

    @Override
    public boolean placeShip(int x, int y, int size, boolean horizontal, String player) {
        int[][] board = player.equals(player1) ? board1 : board2;
        try {
            for (int i = 0; i < size; i++) {
                if (horizontal) board[x][y + i] = 1;
                else board[x + i][y] = 1;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String fire(int x, int y, String player) {
        int[][] targetBoard = player.equals(player1) ? board2 : board1;

        if (targetBoard[x][y] == 1) {
            targetBoard[x][y] = 3;
            if (isAllShipsSunk(targetBoard)) {
                return "venceu";
            }
            turn = player.equals(player1) ? player2 : player1;
            return "acertou";
        } else if (targetBoard[x][y] == 0) {
            targetBoard[x][y] = 2;
            turn = player.equals(player1) ? player2 : player1;
            return "errou";
        }
        return "invalido";
    }

    @Override
    public String getCurrentTurn() { return turn; }

    @Override
    public int[][] getBoard(String player) {
        return player.equals(player1) ? board1 : board2;
    }

    private boolean isAllShipsSunk(int[][] board) {
        for (int[] row : board)
            for (int cell : row)
                if (cell == 1) return false;
        return true;
    }
}
