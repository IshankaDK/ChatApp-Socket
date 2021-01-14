package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerController {

    public TextArea txtArea;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();


    public void btnStartOnAction(ActionEvent actionEvent) {
        new Thread(()->{
            ServerSocket serverSocket;
            Socket socket;
            try {
                txtArea.appendText("Server Started\n");
                serverSocket = new ServerSocket(6000);
                while (true) {
                    socket = serverSocket.accept();
                    txtArea.appendText("Client Connected :-  "+socket+"\n"); //.getInetAddress().toString()
                    ClientHandler clientThread = new ClientHandler(socket, clients);
                    clients.add(clientThread);
                    System.out.println("check....."+clients);
                    clientThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void btnStopOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}