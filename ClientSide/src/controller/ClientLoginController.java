package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class ClientLoginController {
    private double xOffset = 0;
    private double yOffset = 0;
    public AnchorPane root;
    public JFXTextField txtUserName;
    public static String userName;

    public static ArrayList<String> users = new ArrayList<>();

    public void txtUserName(ActionEvent actionEvent) throws IOException {
        userName = txtUserName.getText().trim();
        if (!userName.equals(null)){
            boolean flag = false;
            if (users.isEmpty()){
                users.add(userName);
                flag=true;
            }
            for (String s:users) {
                if(!s.equalsIgnoreCase(userName)){
                    flag=true;
                    System.out.println(userName);
                    break;
                }
            }
            if (flag){
                Stage stage = (Stage) root.getScene().getWindow();
//                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/Client.fxml"))));
//                root.setOnMousePressed(event -> {
//                    xOffset = event.getSceneX();
//                    yOffset = event.getSceneY();
//                });
//                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        stage.setX(event.getScreenX() - xOffset);
//                        stage.setY(event.getScreenY() - yOffset);
//                    }
//                });
            }else {
                new Alert(Alert.AlertType.ERROR,"User Already Exist",ButtonType.OK).show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR,"Enter Username",ButtonType.OK).show();
        }

    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        txtUserName(actionEvent);
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void imgMinOnAction(MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

}
