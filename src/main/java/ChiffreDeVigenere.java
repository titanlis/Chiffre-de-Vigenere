/* @file ChiffreDeVigenere.java
 * The file contains a class for encrypting and decrypting text using the Vigenere method.
 */


/**
 * @class ChiffreDeVigenere
 * @brief Chiffre de Vigenere
 * @details A class for encrypting and decrypting text using the Vigenere method.
 */
public class ChiffreDeVigenere {
    private final static String LATIN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final static String CYRILLIC_ALPHABET = "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞßàáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿ";
    private final static String NUMERIC_ALPHABET = "0123456789";
    private final static String PUNCTUATION_MARKS = "!@#$%^&*_=+-/.?<>,:;{}[]\"\'\\|()¹~` ";
    private final static String DEFAULT_KEY = "keyVigenere";

    private String key;
    private String alphabet;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getAlphabet() {
        return alphabet;
    }
    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public ChiffreDeVigenere() {
        this.key = DEFAULT_KEY;
        this.alphabet = LATIN_ALPHABET + CYRILLIC_ALPHABET + NUMERIC_ALPHABET + PUNCTUATION_MARKS;
    }

    public ChiffreDeVigenere(String key) {
        this.key = key;
        this.alphabet = LATIN_ALPHABET + CYRILLIC_ALPHABET + NUMERIC_ALPHABET + PUNCTUATION_MARKS;
    }

    public ChiffreDeVigenere(String key, String alphabet) {
        this.key = key;
        this.alphabet = alphabet;
    }

    /**Encryption*/
    public String encrypting(String message) throws IllegalAccessException {
        if(!whetherTheCharactersOfTheKeyAreInTheCurrentAlphabet()){
            throw new IllegalAccessException("Some characters of the key are not contained in the current alphabet.");
        }
        if(!whetherTheCharactersOfTheMessageAreInTheCurrentAlphabet(message)){
            throw new IllegalAccessException("Some characters of the message are not contained in the current alphabet.");
        }
        StringBuilder s = new StringBuilder();
        int len = alphabet.length();
        String[] table = new String[len];
        table[0] = alphabet;
        //creating table
        for(int i=1; i<len; i++){
            table[i] = table[i-1].substring(1, len) + table[i-1].charAt(0);
        }

        //key extension by message size
        String key1 = key;
        for(int i=0; key1.length() < message.length(); i++){
           if(i==key.length()){
               i=0;
           }
            key1 = key1 + key.charAt(i);
        }

        //Message encoding by table
        for(int i=0; i<message.length(); i++){
            s.append(table[alphabet.indexOf(message.charAt(i))].charAt(alphabet.indexOf(key1.charAt(i))));
        }

        return s.toString();
    }

    public String decrypting(String message) throws IllegalAccessException {
        if(!whetherTheCharactersOfTheKeyAreInTheCurrentAlphabet()){
            throw new IllegalAccessException("Some characters of the key are not contained in the current alphabet.");
        }
        if(!whetherTheCharactersOfTheMessageAreInTheCurrentAlphabet(message)){
            throw new IllegalAccessException("Some characters of the message are not contained in the current alphabet.");
        }

        StringBuilder s = new StringBuilder();
        int len = alphabet.length();
        String[] table = new String[len];
        table[0] = alphabet;
        for(int i=1; i<len; i++){
            table[i] = table[i-1].substring(1, len) + table[i-1].charAt(0);
        }

        StringBuilder key1 = new StringBuilder(key);
        for(int i=0; key1.length() < message.length(); i++){
            if(i==key.length()){
                i=0;
            }
            key1.append(key.charAt(i));
        }

        for(int i=0; i<message.length(); i++){
            s.append(alphabet.charAt(table[alphabet.indexOf(key1.charAt(i))].indexOf(message.charAt(i))));
        }
        return s.toString();
    }

    //Check. Are all the letters of the key in the alphabet?
    private boolean whetherTheCharactersOfTheKeyAreInTheCurrentAlphabet(){
        for(int i=0; i< key.length(); i++){
            if(alphabet.indexOf(key.charAt(i))==-1){
                return false;
            }
        }
        return true;
    }

    //Check. Are all the letters of the message in the alphabet?
    private boolean whetherTheCharactersOfTheMessageAreInTheCurrentAlphabet(String message){
        for(int i=0; i< message.length(); i++){
            if(alphabet.indexOf(message.charAt(i))==-1){
                return false;
            }
        }
        return true;
    }
}
