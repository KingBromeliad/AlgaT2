package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Database data;

    @Override
    public void start(Stage primaryStage) throws Exception{
        data = new Database();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        HomeController homeController = loader.getController();
        homeController.SetData(data, 3);

        primaryStage.setTitle("AlgaT");
        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreen(false);

        primaryStage.show();
    }

    public static void main(String[] args) { launch(args);
    }
}
