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
    private Slider SL_groupExpiration;

    @FXML
    private Button BTN_createGroup;

    @FXML
    private Button BTN_joinGroup;

    public static ObservableList<String> OBSL_messages = FXCollections.observableArrayList();

    @FXML
    void BTN_createGroup(ActionEvent event) {

    }

    @FXML
    void BTN_joinGroup(ActionEvent event) {

    }

    @FXML
    void BTN_sendMessage(Event event) {
        if (!(event instanceof KeyEvent && ((KeyEvent)event).getCode().equals(KeyCode.ENTER)) && !TXTF_message.getText().isEmpty()) return;
        Client.reply(TXTF_message.getText());   
    }

    @FXML
    void sendMessage(KeyEvent event) {

    }

    @FXML
    void CHB_changeParanoidMode(ActionEvent event) {

    }

    public static void addMessage(String message) {
        OBSL_messages.add(message);
    }

    @FXML
    void initialize() {
        LSTV_chat.setItems(OBSL_messages);
        assert LSTV_chat != null : "fx:id=\"LSTV_chat\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_message != null : "fx:id=\"TXTF_message\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_send != null : "fx:id=\"BTN_send\" was not injected: check your FXML file 'fxml.fxml'.";
        assert LBL_chatName != null : "fx:id=\"LBL_chatName\" was not injected: check your FXML file 'fxml.fxml'.";
        assert LBL_currentName != null : "fx:id=\"LBL_currentName\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_name != null : "fx:id=\"TXTF_name\" was not injected: check your FXML file 'fxml.fxml'.";
        assert CHB_paranoidMode != null : "fx:id=\"CHB_paranoidMode\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_groupCode != null : "fx:id=\"TXTF_groupCode\" was not injected: check your FXML file 'fxml.fxml'.";
        assert LBL_groupCode != null : "fx:id=\"LBL_groupCode\" was not injected: check your FXML file 'fxml.fxml'.";
        assert SL_groupExpiration != null : "fx:id=\"SL_groupExpiration\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_createGroup != null : "fx:id=\"BTN_createGroup\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_joinGroup != null : "fx:id=\"BTN_joinGroup\" was not injected: check your FXML file 'fxml.fxml'.";

    }
}
