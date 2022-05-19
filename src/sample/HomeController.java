package sample;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public StackPane container;
    public VBox pane;
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    private Database data;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void SetData(Database database, int n){
        data = database;
                    //switch to set boolean variables to unlock section
        switch (n) {
            case 0:
                data.bool2 = true;
                break;
            case 1:
                data.bool3 = true;
                data.bool4 = true;
                break;
            case 2: //animation unlocked after 2nd lesson
                break;
            case 3: //tutorial done
                break;
        }
        //unlock of section and visual changes

        if (!data.bool1) {   button1.setStyle("-fx-background-image: url('/sample/b1L.jpg')");
            button1.setDisable(false);} else {
            button1.setStyle("-fx-background-image: url('/sample/b1.jpg')");
            button1.setDisable(false);
        }
        if (!data.bool2) {   button2.setStyle("-fx-background-image: url('/sample/b2L.jpg')");
            button2.setDisable(true); } else {
            button2.setStyle("-fx-background-image: url('/sample/b2.jpg')");
            button2.setDisable(false);
        }
        if (!data.bool3) {   button3.setStyle("-fx-background-image: url('/sample/b3L.jpg')");
            button3.setDisable(true); } else {
            button3.setStyle("-fx-background-image: url('/sample/b3.jpg')");
            button3.setDisable(false);
        }
        if (!data.bool4) {   button4.setStyle("-fx-background-image: url('/sample/b4L.jpg')");
            button4.setDisable(true); } else {
            button4.setStyle("-fx-background-image: url(sample/b4.jpg)");
            button4.setDisable(false);
        }
    }
    //button navigation calls

    public void button1() throws IOException { goToLesson(0) ; }

    public void button2() throws IOException { goToLesson(1) ; }

    public void button3() throws IOException { goToLesson(2) ; }

    public void button4() throws IOException { goToAnimation();}


    // pages navigation functions

    public void goToLesson(int lesson) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("text.fxml"));
    Parent lessonParent = loader.load();
    TextController textController = loader.getController();
    textController.SetData(data, lesson);

    //animation and transition

    Scene current = button1.getScene();

    lessonParent.translateYProperty().set(current.getHeight());
    container.getChildren().add(lessonParent);

    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(lessonParent.translateYProperty(),0, Interpolator.EASE_IN);
    KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
    timeline.getKeyFrames().add(kf);
    timeline.setOnFinished(actionEvent -> {
        container.getChildren().remove(pane);
    });
    timeline.play();
    }

    public void goToAnimation() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("animation.fxml"));
        Parent animationParent = loader.load();
        AnimationController animationController = loader.getController();
        animationController.SetData(data);

        //animation and transition

        Scene current = button1.getScene();

        animationParent.translateYProperty().set(current.getHeight());
        container.getChildren().add(animationParent);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(animationParent.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(actionEvent -> {
            container.getChildren().remove(pane);
        });
        timeline.play();
    }
}
