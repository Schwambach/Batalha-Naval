package client;

import common.IGame;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cliente {
    private static IGame jogo;
    private static String nome;

    public static void main(String[] args) {
        try {
            String host = JOptionPane.showInputDialog("IP do host (localhost se for o mesmo PC):");
            nome = JOptionPane.showInputDialog("Seu nome:");
            Registry reg = LocateRegistry.getRegistry(host, 1099);
            jogo = (IGame) reg.lookup("BatalhaNaval");

            if (jogo.registerPlayer(nome)) {
                JOptionPane.showMessageDialog(null, "Conectado! Esperando outro jogador...");
                abrirInterface();
            } else {
                JOptionPane.showMessageDialog(null, "Servidor cheio!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void abrirInterface() {
        JFrame frame = new JFrame("Batalha Naval - " + nome);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 5));

        JButton[][] botoes = new JButton[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = i, y = j;
                botoes[i][j] = new JButton();
                botoes[i][j].addActionListener(e -> {
                    try {
                        if (nome.equals(jogo.getCurrentTurn())) {
                            String resultado = jogo.fire(x, y, nome);
                            JOptionPane.showMessageDialog(frame, resultado);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Espere sua vez!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                frame.add(botoes[i][j]);
            }
        }
        frame.setVisible(true);
    }
}
