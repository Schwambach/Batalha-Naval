package server;

import common.IGame;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            IGame jogo = new GameImpl();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("BatalhaNaval", jogo);
            System.out.println("Servidor pronto! Esperando jogador...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
