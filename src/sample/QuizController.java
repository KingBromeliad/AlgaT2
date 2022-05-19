package sample;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

    public ToggleGroup group1 = new ToggleGroup();
    public Label question;
    public Label answer;
        public Rectangle box0;
        public Rectangle box1;
        public Rectangle box2;
        public Rectangle box3;
        public Rectangle box4;
        public Label option1;
        public Label option2;
        public Label option3;
        public RadioButton toggle1;
        public RadioButton toggle2;
        public RadioButton toggle3;
    public StackPane container;
    public VBox pane;
    public HBox answerPane;
    public Button button;
    private int quizSession;
    private int currentQuiz;
    private Rectangle[] box = new Rectangle[5];
    private Label[] Option = new Label[3];
    private RadioButton[] Toggle = new RadioButton[3];
    private Database database;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        box[0] = box0;
        box[1] = box1;
        box[2] = box2;
        box[3] = box3;
        box[4] = box4;
        Option[0] = option1;
        Option[1] = option2;
        Option[2] = option3;
        Toggle[0] = toggle1;
        Toggle[1] = toggle2;
        Toggle[2] = toggle3;

    currentQuiz = 0;
    answerPane.setVisible(false);
    }


    public void SetData(Database data, int session) {
        database = data;
        quizSession = session;
        currentQuiz = 0;
        question.setText(database.Questions[quizSession - 1].get(currentQuiz));
        box[currentQuiz/2].setStrokeWidth(3);
        SetCorrect();
        for (int i = 0; i < 5; i++){
            if (database.QuizDone[quizSession].get(i) == true) box[i].setFill(Color.web("#67e09b"));
        }
    }
    // quiz check function

    public  void check (ActionEvent actionEvent){
        answerPane.setVisible(true);
        if (group1.getSelectedToggle() == Toggle[database.CorrectAnswer[quizSession - 1].get(currentQuiz/2)]) {
            answer.setText(database.Questions[quizSession - 1].get(currentQuiz + 1));
            box[currentQuiz/2].setFill(Color.web("#67e09b"));
            database.QuizDone[quizSession].set(currentQuiz/2, true);
        }
        else {
            answer.setText("Sbagliato!");
            box[currentQuiz / 2].setFill(Color.web("#e06767"));
        }

    }

    //correct answer in Database.CorrectAnswer[quizSession]. method for not repeating code

    public void SetCorrect(){
        Option[0].setText(database.Options[quizSession - 1].get(currentQuiz/2*3));
        Option[1].setText(database.Options[quizSession - 1].get(currentQuiz/2*3 + 1));
        Option[2].setText(database.Options[quizSession - 1].get(currentQuiz/2*3 + 2));

        //Option[0] in correct position
        Option[database.CorrectAnswer[quizSession - 1].get(currentQuiz/2)].setText(database.Options[quizSession - 1].get(currentQuiz/2 * 3));
        //Option[correct position] in 0
        Option[0].setText(database.Options[quizSession - 1].get(currentQuiz/2*3 + database.CorrectAnswer[quizSession - 1].get(currentQuiz/2)));
    }


    // quiz navigation functions

    public void nextQuiz (ActionEvent actionEvent){
        if (database.QuizDone[quizSession].get(currentQuiz/2) == true && currentQuiz < Database.QUIZ_NUMBER * 2) {

            toggle1.setSelected(false);
            toggle2.setSelected(false);
            toggle3.setSelected(false);

            box[currentQuiz / 2].setStrokeWidth(0);
            currentQuiz += 2;
            if (database.QuizDone[quizSession].get(currentQuiz/2) == true) answerPane.setVisible(true);
            else answerPane.setVisible(false);
            box[currentQuiz / 2].setStrokeWidth(3);
            SetCorrect();
            question.setText(database.Questions[quizSession - 1].get(currentQuiz));
            answer.setText(database.Questions[quizSession - 1].get(currentQuiz + 1));
        }

    }

    public void prevQuiz (ActionEvent actionEvent){
        if (currentQuiz > 0) {

            toggle1.setSelected(false);
            toggle2.setSelected(false);
            toggle3.setSelected(false);

            box[currentQuiz / 2].setStrokeWidth(0);
            currentQuiz -= 2;
            answerPane.setVisible(true);
            box[currentQuiz / 2].setStrokeWidth(3);
            SetCorrect();
            question.setText(database.Questions[quizSession - 1].get(currentQuiz));
            answer.setText(database.Questions[quizSession - 1].get(currentQuiz + 1));
        }
    }


    // pages navigation functions

    public void goToLesson(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("text.fxml"));
        Parent lessonParent = loader.load();
        TextController textController = loader.getController();
        textController.SetData(database, quizSession);
        Scene current = button.getScene();

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

    public void goToHome(ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent homeParent = loader.load();
            HomeController homeController = loader.getController();
        if (database.QuizDone[quizSession].get(4) == true)  homeController.SetData(database, quizSession);
        else homeController.SetData(database, quizSession - 1);
            container.getChildren().add(homeParent);
            container.getChildren().remove(pane);

    }
}


