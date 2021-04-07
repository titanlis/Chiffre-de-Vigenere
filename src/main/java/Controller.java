import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @class Controller
 * @brief The event handler of the main form "sample.fxml".
 * @details The class handles events that appear in the window specified by the form from the "sample.fxml" file.
 */
public class Controller {

    /**Instance of encryption/decryption class by Vigen?re method.*/
    private ChiffreDeVigenere cV;
    /**A string with a key derived from the text form in the main window.*/
    private String keyString;
    /**Current Alphabet.*/
    private String alphabet;
    /**Length of the key to break the cipher.*/
    private int lenCrackKey;

    @FXML
    Button exitButton;

    @FXML
    Button helpButton;

    @FXML
    Button crackButton;

    @FXML
    Button coddingButton;

    @FXML
    Button decodingButton;

    @FXML
    Button setAlphabetButton;

    @FXML
    TextArea startText;

    @FXML
    TextArea finishText;

    @FXML
    TextField keyField;

    @FXML
    CheckBox lineBreak;

    @FXML
    public void showHelpWindow(ActionEvent event) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(
                    HelpClassController.class.getResource("help.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Help Window");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

    @FXML
    public void buttonCoddingClicked() {
        String sText = startText.getText();
        keyString = keyField.getText();
        if(keyString.length()==0){
            keyString = alphabet.substring(0,1);
            keyField.setText(keyString);
        }
        cV.setAlphabet(alphabet);
        cV.setKey(keyString);
        String fText = "";
        try {
            String[] toks = sText.split("\n");
            if (toks.length > 1) {
                if (alphabet.indexOf('\n') == -1) {
                    alphabet += '\n';
                    cV.setAlphabet(alphabet);
                }
            }
            fText = cV.encrypting(sText);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        finishText.setText(fText);
    }

    @FXML
    public void buttonDecodingClicked() {
        String sText = startText.getText();
        keyString = keyField.getText();
        if(keyString.length()==0){
            keyString = alphabet.substring(0,1); //первая буква алфавита
            keyField.setText(keyString);
        }
        cV.setAlphabet(alphabet);
        cV.setKey(keyString);

        if (alphabet.indexOf('\n') == -1 && lineBreak.isSelected()) {
            System.out.println("Adding line break");
            alphabet += '\n';
            cV.setAlphabet(alphabet);
        }
        else if (alphabet.indexOf('\n') != -1 && !lineBreak.isSelected()){
            for(int i=0; i<alphabet.length(); i++){
                if(alphabet.charAt(i)=='\n'){
                    alphabet =alphabet.substring(0,i)+alphabet.substring(i+1);
                    cV.setAlphabet(alphabet);
                    break;
                }
            }
        }

        String fText = null;
        try {
            fText = cV.decrypting(sText);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        finishText.setText(fText);
    }

    @FXML
    public void setAlphabetFunc() {
        TextInputDialog inputAlphabetDialog = new TextInputDialog();
        inputAlphabetDialog.setTitle("Input Alphabet Dialog");
        inputAlphabetDialog.setHeaderText("Old alphabet : \"" + alphabet + "\"");
        inputAlphabetDialog.setContentText("Enter a new alphabet : ");
        Optional<String> al = inputAlphabetDialog.showAndWait();
        if (al.isPresent()) {
            alphabet = al.get();
            cV.setAlphabet(alphabet);
        }
    }

    @FXML
    public void buttonExitClicked() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**Are all the letters of the message in the alphabet?*/
    boolean isInAlphabet(String message, String alp){
        for(int i=0; i<message.length(); i++){
            if(alp.indexOf(message.charAt(i))==-1){
                return false;
            }
        }
        return true;
    }

    /**A method for breaking the Vigenere cipher.*/
    @FXML
    public void buttonCrackClicked() throws IllegalAccessException {
        TextInputDialog inputAlphabetDialog = new TextInputDialog();
        inputAlphabetDialog.setTitle("Key length");
        inputAlphabetDialog.setHeaderText("Alphabet : \"" + alphabet + "\"");
        inputAlphabetDialog.setContentText("Enter a key length : ");
        Optional<String> al = inputAlphabetDialog.showAndWait();
        if (al.isPresent()) {
            lenCrackKey = Integer.parseInt(al.get());
            String text = startText.getText();

            if(isInAlphabet(text, alphabet)){
                String key = getKey(text);
                keyField.setText(key);
                if(key.length()==0){
                    return;
                }
                finishText.setText(new ChiffreDeVigenere(key, alphabet).decrypting(text));
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The text contains a character that is not part of the alphabet.");
                alert.showAndWait();
            }
        }
    }


    /**Find the most frequently encountered symbol in the list*/
    private char maxChar(ArrayList<Character> a){
        char ch = ' ';
        char tecCh= ' ';
        int frag = 0, max_frag=0;
        for(int i=0;  i<a.size()-1; i++){
            tecCh=a.get(i);
            frag = 0;
            for(int j=i+1; j<a.size(); j++){
                if(tecCh == a.get(j)){
                    frag++;
                }
                if(frag>max_frag){
                    max_frag = frag;
                    ch = tecCh;
                }
            }
        }
        return ch;
    }

    /**Finding the key*/
    private String getKey(String text) throws IllegalAccessException {
        if(text.length()==0){
            return new String("");
        }
        StringBuilder key = new StringBuilder();
        int numStr = (text.length()%lenCrackKey > 0)? text.length()/lenCrackKey+1:text.length()/lenCrackKey;
        char[][] array = new char[numStr][lenCrackKey];
        for(int i=0; i<text.length();  i++){
            array[i/lenCrackKey][i%lenCrackKey] = text.charAt(i);
        }

        ArrayList<ArrayList<Character>> arr = new ArrayList<>(lenCrackKey);

        for(int i=0; i<lenCrackKey; i++){
            arr.add(new ArrayList<>());
        }

        for(int i=0; i < lenCrackKey; i++){
            for(int j=0; j<numStr-1; j++){
                arr.get(i).add(array[j][i]);
            }
        }

        String keyMax = "";
        if(alphabet.indexOf(' ')!=-1){
            keyMax+=' ';
        }
        else if(alphabet.indexOf('_')!=-1) {
            keyMax += '_';
        }
        else if(alphabet.indexOf('\t')!=-1) {
            keyMax += '\t';
        }
        else{
            return new String("");
        }

        ChiffreDeVigenere l1 = new ChiffreDeVigenere(keyMax, alphabet);
        for(int i=0; i<lenCrackKey; i++){
            key.append(l1.decrypting("" + maxChar(arr.get(i))));
        }
        return key.toString();
    }

    @FXML
    public void initialize() {
        cV = new ChiffreDeVigenere();
        keyString = cV.getKey();
        keyField.setText(keyString);
        alphabet = cV.getAlphabet();
        lineBreak.setAllowIndeterminate(false);
        lenCrackKey = 4;
    }
}
