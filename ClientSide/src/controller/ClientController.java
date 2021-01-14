package controller;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.Socket;


public class ClientController extends Thread {
    public TextArea txtArea;
    public AnchorPane root;
    public TextField txtMsg;
    public ImageView imageView;

    private FileChooser fileChooser;
    private File filePath;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    ObjectOutputStream oos = null;

    public void imgCloseOnAction(MouseEvent mouseEvent) {
//        try {
//            reader.close();
//            writer.close();
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.exit(0);
    }

    public void imgMinOnAction(MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    public void txtMsgOnAction(ActionEvent actionEvent) {
        String msg = txtMsg.getText().trim();
        writer.println(ClientLoginController.userName + ": " + msg);
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
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        String path = filePath.getPath();
        System.out.println(path);
        Image image = new Image(path);
        imageView = new ImageView();
        Panel panel = new Panel();
        imageView.setImage(image);
        root.getChildren().add(imageView);

    }

    //    public void saveImage() {
//        if (saveControl) {
//            try {
//                BufferedImage bufferedImage = ImageIO.read(filePath);
//                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//                txtArea.appendText(String.valueOf(image));
////                showProPic.setFill(new ImagePattern(image));
//                saveControl = false;
//                txtMsg.setText("");
//            } catch (IOException e) {
//                System.err.println(e.getMessage());
//            }
//        }
//    }
    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fullmsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullmsg.append(tokens[i]);
                }
                System.out.println(fullmsg);
                if (cmd.equalsIgnoreCase(ClientLoginController.userName + ":")) {
                    continue;
                } else if (fullmsg.toString().equalsIgnoreCase("bye")) {
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
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            writer = new PrintWriter(socket.getOutputStream(), true);
//            oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.flush();
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  public String readInput() {
        StringBuffer buffer = new StringBuffer();
        try {
            int ch;
            while ((ch = reader.read()) > -1) {
                buffer.append((char) ch);
            }
//            reader.close();
            System.out.println(buffer.toString());
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }*/
}
