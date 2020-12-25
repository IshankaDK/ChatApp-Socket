import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppInitializer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("view/ClientLogin.fxml"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
