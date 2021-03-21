import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.Random;


public class fxmlController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> LSTV_chat;

    @FXML
    private TextField TXTF_message;

    @FXML
    private Button BTN_send;

    @FXML
    private Label LBL_chatName;

    @FXML
    private Label LBL_currentName;

    @FXML
    private TextField TXTF_name;

    @FXML
    private CheckBox CHB_paranoidMode;

    @FXML
    private TextField TXTF_groupCode;

    @FXML
    private Label LBL_groupCode;

    @FXML
    private Button BTN_changeName;

    @FXML
    private Slider SL_groupExpiration;

    @FXML
    private Button BTN_setDefaultUsername;

    @FXML
    private Button BTN_createGroup;

    @FXML
    private Button BTN_joinGroup;

    public static ObservableList<String> OBSL_messages = FXCollections.observableArrayList();

    public static final int usernameNumber = 980; 

    @FXML
    void BTN_createGroup(ActionEvent event) {

    }

    @FXML
    void BTN_joinGroup(ActionEvent event) {

    }

    @FXML
    void BTN_sendMessage(Event event) {
        if (!(event instanceof KeyEvent && ((KeyEvent)event).getCode().equals(KeyCode.ENTER))) return;
        Client.sendMessage(TXTF_message.getText());  
        TXTF_message.setText("");
        if (Client.paranoidMode) setNewName(genRandomUsername()); 
    }

    @FXML
    void CHB_changeParanoidMode(ActionEvent event) {
        Client.paranoidMode = !Client.paranoidMode;
        if (Client.paranoidMode) setNewName(genRandomUsername());
        else setDefaultUsername(new ActionEvent());
    }

    public String genRandomUsername() {
        Random random = new Random();
        String name = Client.DEFAULT_USERNAME;
        int rand = random.nextInt(usernameNumber - 1) - 1;
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("nomi.hrms"));
            for (int i = 0; i < rand; i++) fileReader.readLine();
            name = fileReader.readLine();
            fileReader.close();
        } catch(IOException e) { System.out.println("Error, can't read the File"); }
        return name;
    }

    public static void addMessage(Message message) {
        //  AGGIUNGERE IL CONTROLLO DEL GRUPPO
        OBSL_messages.add(message.username + message.content);
    }
    
    @FXML
    void changeName(ActionEvent event) {
        System.out.println(genRandomUsername());
        String newName = TXTF_name.getText();
        if (newName.isBlank()) newName = Client.DEFAULT_USERNAME;
        setNewName(newName);
        TXTF_name.setText("");
    }

    private void setNewName(String newName) {
        Client.username = newName;
        LBL_currentName.setText("Your Current Name: " + Client.username);
    }

    
    @FXML
    void ctrlCharacters(KeyEvent event) {
        String userInput = TXTF_name.getText();
        if (userInput.length() >= 24) TXTF_name.setText(userInput.substring(0, 22));
    }

    
    @FXML
    void setDefaultUsername(ActionEvent event) {
        setNewName(Client.DEFAULT_USERNAME);
    }

    @FXML
    void initialize() {
        LSTV_chat.setItems(OBSL_messages);
        LSTV_chat.setMouseTransparent(true);
        assert LSTV_chat != null : "fx:id=\"LSTV_chat\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_message != null : "fx:id=\"TXTF_message\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_send != null : "fx:id=\"BTN_send\" was not injected: check your FXML file 'fxml.fxml'.";
        assert LBL_chatName != null : "fx:id=\"LBL_chatName\" was not injected: check your FXML file 'fxml.fxml'.";
        assert LBL_currentName != null : "fx:id=\"LBL_currentName\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_name != null : "fx:id=\"TXTF_name\" was not injected: check your FXML file 'fxml.fxml'.";
        assert CHB_paranoidMode != null : "fx:id=\"CHB_paranoidMode\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_changeName != null : "fx:id=\"BTN_changeName\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_setDefaultUsername != null : "fx:id=\"BTN_setDefaultUsername\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_groupCode != null : "fx:id=\"TXTF_groupCode\" was not injected: check your FXML file 'fxml.fxml'.";
        assert LBL_groupCode != null : "fx:id=\"LBL_groupCode\" was not injected: check your FXML file 'fxml.fxml'.";
        assert SL_groupExpiration != null : "fx:id=\"SL_groupExpiration\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_createGroup != null : "fx:id=\"BTN_createGroup\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_joinGroup != null : "fx:id=\"BTN_joinGroup\" was not injected: check your FXML file 'fxml.fxml'.";

    }
}
