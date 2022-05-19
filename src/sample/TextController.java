package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TextController implements Initializable {

    public Label pageIndicator;
    public Label content;
    public Label title;
    public Button button;
    public VBox pane;
    public StackPane container;
    private int currentPage = 0;
    private int lessonNumber = 0;
    private Database data;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        currentPage = 1;
    }

    public void nextPage(ActionEvent event){
        currentPage++;
        if (currentPage > data.Lessons[lessonNumber].size() ) currentPage = 1;
        pageIndicator.setText(Integer.toString(currentPage));
        content.setText(data.Lessons[lessonNumber].get(currentPage - 1));
    }

    public void prevPage(ActionEvent event){
        currentPage--;
            if (currentPage <= 0) currentPage = data.Lessons[lessonNumber].size();
        content.setText(data.Lessons[lessonNumber].get(currentPage - 1));
        pageIndicator.setText(Integer.toString(currentPage));
    }

    public void SetData(Database database, int lesson) {
        lessonNumber = lesson;
        data = database;
        content.setText(data.Lessons[lessonNumber].get(0));
        title.setText(data.Titles[lessonNumber]);
    }

    // pages navigation functions

    public void goToHome(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent homeParent = loader.load();
        HomeController homeController = loader.getController();
        if (data.QuizDone[lessonNumber].get(4) == true || lessonNumber == 0)  homeController.SetData(data, lessonNumber); //call SetData with correct parameters.
        else homeController.SetData(data, lessonNumber - 1);
        container.getChildren().add(homeParent);
        container.getChildren().remove(pane);
    }

    public void goToQuiz(ActionEvent event) throws IOException {

        if (lessonNumber != 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz.fxml"));
            Parent quizParent = loader.load();
            QuizController quizController = loader.getController();
            quizController.SetData(data, lessonNumber);
            container.getChildren().add(quizParent);
            container.getChildren().remove(pane);
        }
    }
}
