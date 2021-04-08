import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private Button BTN_changeName;

    @FXML
    private ComboBox<String> CMB_groupExpiration;

    @FXML
    private Button BTN_setDefaultUsername;

    @FXML
    private Button BTN_createGroup;

    @FXML
    private Button BTN_joinGroup;

    @FXML
    private ListView<String> LSTV_groups;

    public static ObservableList<String> OBSL_messages = FXCollections.observableArrayList();

    public static ObservableList<String> OBSL_groups = FXCollections.observableArrayList();

    //SE SI PASSA CON IL CURSORE SOPRA AL NOME BISOGNA POTER VEDERE IL CODICE DEL GRUPPO

    private static final HashMap<String, Integer> expirationMap = new HashMap<String, Integer>();

    public static final int usernameNumber = 980; 

    public static  boolean applyMod = false;

    @FXML
    void BTN_createGroup(ActionEvent event) { 
        String request = Client.CREATE_GROUP_REQUEST;
        request += expirationMap.get(CMB_groupExpiration.getSelectionModel().getSelectedItem());
        Client.sendMessage(request);
    }

    @FXML
    void BTN_joinGroup(ActionEvent event) {
        String id;
        if (TXTF_groupCode.getText().length() != 4) return;
        id = TXTF_groupCode.getText();
        Client.sendMessage(Client.GROUP_REQUEST + id);
    }

    @FXML
    void BTN_sendMessage(Event event) {
        if (!(event instanceof KeyEvent && ((KeyEvent)event).getCode().equals(KeyCode.ENTER))) return;
        Client.sendMessage(TXTF_message.getText(), LBL_chatName.getText());  
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

        Platform.runLater(() -> {
            try {
                OBSL_messages.add(message.username + ":  " +  message.content);
            } catch (Exception e) { }
        });
        
        fxmlController.applyMod = true;
    }

    public class ChatModifier extends Thread {
        @Override
        public void run() {
            while(true) {

                if(fxmlController.applyMod) {
                    Platform.runLater(() -> {
                        try {
                            LSTV_chat.scrollTo(OBSL_messages.size()-1);
                        } catch (Exception e) { }
                    });
                    
                    fxmlController.applyMod = false;
                }
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }

        }  
    }
    
    private void initHashMap() {
        expirationMap.put("1 hour", 1);
        expirationMap.put("3 hours", 3);
        expirationMap.put("6 hours", 6);
        expirationMap.put("12 hours", 12);
        expirationMap.put("24 hours", 24);
        expirationMap.put("7 days", 168);
        expirationMap.put("Permanent", Integer.MAX_VALUE);
    }

    @FXML
    void LSTV_changeGroup(MouseEvent event) {
        Platform.runLater(() -> {
            try {
                LBL_chatName.setText(LSTV_groups.getSelectionModel().getSelectedItem());
            } catch (Exception e) { }
        });
    }

    @FXML
    void changeName(ActionEvent event) {
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
        initHashMap();
        LSTV_chat.setItems(OBSL_messages);
        LSTV_groups.setItems(OBSL_groups);
        //LSTV_chat.setMouseTransparent(true);
        ChatModifier cm = new ChatModifier();
        cm.start();
        setDefaultUsername(new ActionEvent());
        CMB_groupExpiration.getItems().addAll(Client.groupExpirations);
        CMB_groupExpiration.getSelectionModel().select(2);
        Client.addNewGroup(Client.GLOBAL_CHAT);
    }
}
