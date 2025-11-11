package common;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote {
    boolean registerPlayer(String name) throws RemoteException;
    boolean placeShip(int x, int y, int size, boolean horizontal, String player) throws RemoteException;
    String fire(int x, int y, String player) throws RemoteException; // "acertou", "errou", "venceu"
    String getCurrentTurn() throws RemoteException;
    int[][] getBoard(String player) throws RemoteException; // para atualizar a tela
}
