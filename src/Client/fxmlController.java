import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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
    private Tab TAB_Chat;
    
    @FXML
    private Tab TAB_Settings;

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
    private TextField TXTF_chatName;

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
    private TableView<Group> LSTV_groups;

    @FXML
    private TableColumn<Group, String> TC_groups;

    public static ObservableList<Group> OBSL_groups = FXCollections.observableArrayList();

    private static final HashMap<String, Integer> expirationMap = new HashMap<String, Integer>();

    public static final int usernameNumber = 980; 

    public static  boolean applyMod = false;

    @FXML
    void BTN_createGroup(ActionEvent event) { 
        String request = Client.CREATE_GROUP_REQUEST;
        request += expirationMap.get(CMB_groupExpiration.getSelectionModel().getSelectedItem());
        Client.sendMessage(request);
    }

    public static void removeGroup(String id) { OBSL_groups.remove(Client.groups.get(id)); }

    @FXML
    void BTN_joinGroup(ActionEvent event) {
        String id;
        if (TXTF_groupCode.getText().length() != 5) return;
        id = TXTF_groupCode.getText().toUpperCase();
        Client.sendMessage(Client.GROUP_REQUEST + id);
    }

    @FXML
    void BTN_sendMessage(Event event) {
        if (!(event instanceof KeyEvent && ((KeyEvent)event).getCode().equals(KeyCode.ENTER))) return;
        Client.sendMessage(TXTF_message.getText(), LSTV_groups.getSelectionModel().getSelectedItem().getId());  
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
        Client.groups.get(message.group).addMessage(message);
        fxmlController.applyMod = true;
        
    }

    public class ChatModifier extends Thread {
        @Override
        public void run() {
            while(true) {

                if(fxmlController.applyMod) {
                    Platform.runLater(() -> {
                        try {
                            LSTV_chat.scrollTo(LSTV_groups.getSelectionModel().getSelectedItem().getMessages().size() - 1);
                        } catch (Exception e) { }
                    });
                    
                    fxmlController.applyMod = false;
                }
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }

        }  
    }
    
    private void initHashMap() {
        expirationMap.put("1 hour", 1); //DA CAMBIARE CON LE KEYWORDS
        expirationMap.put("3 hours", 3);
        expirationMap.put("6 hours", 6);
        expirationMap.put("12 hours", 12);
        expirationMap.put("24 hours", 24);
        expirationMap.put("7 days", 168);
        expirationMap.put("Permanent", Client.GROUP_TIMEOUT);
    }

    @FXML
    void LSTV_changeGroup(MouseEvent event) {
        Group selectedGroup = LSTV_groups.getSelectionModel().getSelectedItem();
        LSTV_chat.setItems(selectedGroup.getMessages());
        Platform.runLater(() -> { try { LBL_chatName.setText(selectedGroup.getName()); } catch (Exception e) { System.out.println("Error Finding the Selected Group"); } });
        Tooltip.install(LBL_chatName, new Tooltip(selectedGroup.getId()));
    }

    public void changeGroup(String id) {
        Group selectedGroup = getGroupById(id);
        Platform.runLater(() -> { LSTV_chat.setItems(selectedGroup.getMessages()); });
        Platform.runLater(() -> { try { LBL_chatName.setText(selectedGroup.getName()); } catch (Exception e) { System.out.println("Error Finding the Selected Group"); } });
        Tooltip.install(LBL_chatName, new Tooltip(selectedGroup.getId()));
    }

    private Group getGroupById(String id) { for (Group g : OBSL_groups) if (g.getId().equals(id)) return g; return new Group();}

    @FXML
    public void LBL_chatNameClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if(mouseEvent.getClickCount() == 2) {
                TXTF_chatName.setVisible(true);
                LBL_chatName.setVisible(false);
            }
        }
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
    void setNewChatName(KeyEvent event) {
        if(event instanceof KeyEvent && ((KeyEvent)event).getCode().equals(KeyCode.ENTER)) {
            Group selectedGroup = getGroupById(LSTV_groups.getSelectionModel().getSelectedItem().getId());
            String newName = TXTF_chatName.getText();
            if (newName.isBlank()) return;
            selectedGroup.setName(newName);
            LBL_chatName.setText(newName);
            TXTF_chatName.setVisible(false);
            LBL_chatName.setVisible(true); 
            LSTV_groups.getSelectionModel().getSelectedItem().setName(newName);
            System.out.println( LSTV_groups.getSelectionModel().getSelectedItem().getName());
        }       
    }
    
    @FXML
    void ctrlCharacters(KeyEvent event) {
        String userInput = TXTF_name.getText();
        if (userInput.length() >= 24) TXTF_name.setText(userInput.substring(0, 22));
    }

    public Label getLBL_chatName() { return this.LBL_chatName; }
    
    @FXML
    void setDefaultUsername(ActionEvent event) { setNewName(Client.DEFAULT_USERNAME); }

    @FXML
    void initialize() {
        initHashMap();
        LSTV_groups.setItems(OBSL_groups);
        TC_groups.setCellValueFactory(new PropertyValueFactory<Group, String>("name"));
        //LSTV_chat.setMouseTransparent(true);
        ChatModifier cm = new ChatModifier();
        cm.start();
        setDefaultUsername(new ActionEvent());
        CMB_groupExpiration.getItems().addAll(Client.groupExpirations);
        CMB_groupExpiration.getSelectionModel().select(2);
        Client.sendMessage(Client.JOIN_REQUEST + Client.GLOBAL_CHAT.getId());
        Client.addNewGroup(Client.GLOBAL_CHAT);
        Platform.runLater(() -> { LSTV_groups.getSelectionModel().selectFirst(); });
        LSTV_chat.setItems(Client.groups.get(Client.GLOBAL_CHAT.getId()).getMessages());
        TXTF_chatName.setVisible(false);
        TAB_Chat.setGraphic(new Circle(0, 0, 5, Paint.valueOf("CRIMSON")));
        TAB_Chat.getGraphic().setVisible(false);
        Client.ctrlRef = this;
    }
}
