package controller;

import controller.ClientLoginController.*;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static controller.ClientLoginController.users;

public class ClientController extends Thread {
    public TextArea txtArea;
    public AnchorPane root;
    public TextField txtMsg;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void imgMinOnAction(MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    public void txtMsgOnAction(ActionEvent actionEvent) {
        String msg = txtMsg.getText().trim();
        writer.println(ClientLoginController.userName + ": "+msg);
        txtArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtArea.appendText("Me: " + msg + "\n\n");
        txtMsg.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }

    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        txtMsgOnAction(actionEvent);
    }

    public void imgImageOnAction(MouseEvent mouseEvent) {
    }
    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(ClientLoginController.userName + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                txtArea.appendText(msg + "\n\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void initialize() {
        try {
            socket = new Socket("localhost", 6000);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
