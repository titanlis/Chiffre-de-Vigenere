import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @class HelpClassController
 * @brief Controller of the help window.
 * @details The help window displays brief instructions on how to use the application.
 */
public class HelpClassController {
    @FXML
    Button closeButton;

    @FXML
    WebView webV;



    @FXML
    public void buttonExitClicked() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void initialize() {
        String webText =  //
                          "<p align = \"center\">A program for encrypting and decrypting text using the Vigenere method.<br>" //
                        + "It is possible to choose the alphabet and the key. <br>" //
                        + "The text can be single or multiline. If the text has several lines, " //
                        + "a line feed character is added to the alphabet when encrypting. "//
                        + "When decrypting text with multiple lines, you must select the checkbox \"line break\"."//
                        +  " This item will add a line break character to the alphabet, if there was no such "//
                        + "a character in it. If you deselect this option, the line break character will be "//
                        + "removed from the current alphabet.</p>"//
                        + "<p align=\"justify\">To see all the characters of the current alphabet, "//
                        + "press the \"Set alphabet\" button. To keep it unchanged, press \"Cancel\".</p>"//
                        + "<p align=\"justify\">With this program you can try to decode the text encoded by "//
                        + "the Vigenere cipher or its special case - the Caesar cipher. "//
                        + "To decrypt it, you must have a text of at least 500 characters long, you must know "//
                        + "the alphabet and the length of the key.</p>"//
                        + "<p align=\"justify\">Procedure for breaking the cipher:"
                        + "<ol>"
                        + "<li>Place the encrypted text in the upper text field.</li>"
                        + "<li> Press the \"Set alphabet\" button and enter the current alphabet.</li>"
                        + "<li> press the \"Crack key tools\" button and enter the length of the key. </li>"
                        + "In the pop-up dialog box you can check the current alphabet. Press \"OK\".</li>"
                        + "<li> In the lower text box the decryption variant will appear, and in the \"Key\" "
                        + "box the current key variant will appear.</li></ol>"
                        + "If the decryption is unsuccessful, you can change the order of characters in the alphabet,"
                        + " the alphabet itself and the length of the key until the result is satisfactory.</p>"
                        + "<p align=\"right\"><br>?Timofeev Andrei (2021)</p>"
        ;

        WebEngine webEngine = webV.getEngine();
        webEngine.loadContent(webText);
    }
}
