package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnimationController implements Initializable {

    public StackPane container;
    public VBox pane;
    public ImageView scene;
    public ImageView hashAlgoritm;
    public Button next;
    public Label key;
    public Label hashValue;
    public Label scan;
    private int i = 0;
    private Database data;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void SetData(Database database){
        data = database;
        hashAlgoritm.setImage(data.hash);
        this.reset();
    }

    //both for initialization and reset
    public void reset() {
        //set images
        i = 0;
        scene.setImage(data.frames[0]);
        key.setText(data.Keys[0]);
        hashValue.setText(data.HashValues[0]);
        scan.setText(data.Scans[0]);
    }

    public void setNext(ActionEvent actionEvent) {
        i++;
        if (i > data.frames.length - 1) i = 0;
            scene.setImage(data.frames[i]);
            key.setText(data.Keys[i]);
            hashValue.setText(data.HashValues[i]);
            scan.setText(data.Scans[i]);

    }

    public void goToHome(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent homeParent = loader.load();
        HomeController homeController = loader.getController();
        homeController.SetData(data, 3);
        container.getChildren().add(homeParent);
        container.getChildren().remove(pane);
    }
}
