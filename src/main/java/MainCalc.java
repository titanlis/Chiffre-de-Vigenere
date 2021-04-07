import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @class MainCalc
 * @brief JavaFX application main window class.
 * @details JavaFX application main window class.
 * This class is where you enter the application and draw the main window.
 */
public class MainCalc extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Chiffre de Vigenere");
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);

        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        primaryStage.show();
    }
}
