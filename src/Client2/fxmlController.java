import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
    private TextField TXTF_name;

    @FXML
    void BTN_onSend(ActionEvent event) {
        Client.reply(TXTF_message.getText());
            
        
    }

    public static ObservableList<String> OBSL_messages = FXCollections.observableArrayList();
    public static void addMessage(String message)
    {
        OBSL_messages.add(message);
    }
    @FXML
    void initialize() {
        LSTV_chat.setItems(OBSL_messages);
        assert LSTV_chat != null : "fx:id=\"LSTV_chat\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_message != null : "fx:id=\"TXTF_message\" was not injected: check your FXML file 'fxml.fxml'.";
        assert BTN_send != null : "fx:id=\"BTN_send\" was not injected: check your FXML file 'fxml.fxml'.";
        assert TXTF_name != null : "fx:id=\"TXTF_name\" was not injected: check your FXML file 'fxml.fxml'.";

    }
}
