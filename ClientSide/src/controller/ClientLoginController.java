package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ClientLoginController {
    public AnchorPane root;
    public JFXTextField txtUserName;
    public static String userName;

    public static ArrayList<String> users = new ArrayList<>();

    public void txtUserName(ActionEvent actionEvent) throws IOException {
        userName = txtUserName.getText().trim();
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
            stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/Client.fxml"))));
        }else {
            new Alert(Alert.AlertType.ERROR,"User Already Exist",ButtonType.OK).show();
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
