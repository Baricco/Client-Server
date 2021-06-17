import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.*;

import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    @FXML
    private TableColumn<Group, ImageView> TC_muteIcons;

    public static ObservableList<Group> OBSL_groups = FXCollections.observableArrayList();

    private static final HashMap<String, Integer> expirationMap = new HashMap<String, Integer>();

    public static final int usernameNumber = 980; 

    public static Group selectedGroup;
    
    public Stage stage;

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
        if (TXTF_groupCode.getText().length() != 5) {
            Client.viewNotification("Error Joining the Group", "The typed Id is either too short or too long,\nit must be 5 digits long", false);
            return;
        }
        id = TXTF_groupCode.getText().toUpperCase();
        Client.sendMessage(Client.GROUP_REQUEST + id);
    }

    @FXML
    void BTN_sendMessage(Event event) {
        if ((event instanceof KeyEvent && !((KeyEvent)event).getCode().equals(KeyCode.ENTER)) || ((event instanceof MouseEvent && !((MouseEvent)event).getButton().equals(MouseButton.PRIMARY)))) return;
        Client.sendMessage(TXTF_message.getText(), selectedGroup.getId());  
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
        selectedGroup.setApplyMod(true);
        
    }

    public HashMap<String, Integer> getExpirationMap() { return expirationMap; }


    public class ChatModifier extends Thread {
        @Override
        public void run() {
            while(true) {
                if(selectedGroup.isApplyMod()) {
                    Platform.runLater(() -> {
                        try {
                            LSTV_chat.scrollTo(selectedGroup.getMessages().size() - 1);
                        } catch (Exception e) { }
                    });
                    
                    selectedGroup.setApplyMod(false);
                }
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }

        }  
    }

    //DA SISTEMARE

    public class TabController extends Thread {
        boolean tab_chat = true;

        @Override
        public void run() {
            while (true) {
                if (TAB_Chat.isSelected()) { tab_chat = true; TAB_Chat.getGraphic().setOpacity(0); }
                if (TAB_Settings.isSelected()) tab_chat = false;
                if (!tab_chat && selectedGroup.isApplyMod() && !selectedGroup.isMuted()) blink();
            }
        }

        private void blink() {
            FadeTransition ft = new FadeTransition(Duration.millis(3000), TAB_Chat.getGraphic());
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        }
    }

    private void initHashMap() {
        expirationMap.put("1 hour", 1);
        expirationMap.put("3 hours", 3);
        expirationMap.put("6 hours", 6);
        expirationMap.put("12 hours", 12);
        expirationMap.put("24 hours", 24);
        expirationMap.put("7 days", 168);
        expirationMap.put("Permanent", Client.GROUP_TIMEOUT);
    }

    @FXML
    void LSTV_changeGroup(MouseEvent event) {
        selectedGroup = LSTV_groups.getSelectionModel().getSelectedItem();
        LSTV_chat.setItems(selectedGroup.getMessages());
        Platform.runLater(() -> { try { LBL_chatName.setText(selectedGroup.getName()); } catch (Exception e) { System.out.println("Error Finding the Selected Group"); } });
        Tooltip.install(LBL_chatName, new Tooltip(selectedGroup.getId()));
    }

    public void changeGroup(String id) {
        selectedGroup = getGroupById(id);
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
                TXTF_chatName.requestFocus();
            }
        }
    }

    @FXML
    void selectChatTab(Event event) {
        try {
        if (TAB_Chat.isSelected()) { 
            TAB_Chat.getGraphic().setOpacity(0); 
            selectedGroup.setApplyMod(false); }
        if (TAB_Settings.isSelected() && selectedGroup.isApplyMod() && !selectedGroup.isMuted()) blink();
        } catch(Exception e) { }
    }

    private void blink() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), TAB_Chat.getGraphic());
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
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
            String newName = TXTF_chatName.getText();
            if (newName.isBlank()) return;
            if (newName.length() >= 24) newName = newName.substring(0, 22); 
            selectedGroup.setName(newName);
            LBL_chatName.setText(newName);
            TXTF_chatName.setVisible(false);
            LBL_chatName.setVisible(true); 
            selectedGroup.setName(newName);
            System.out.println(selectedGroup.getName());
        }       
    }
    
    @FXML
    void ctrlCharacters(KeyEvent event) {
        String userInput = TXTF_name.getText();
        if (userInput.length() >= 24) TXTF_name.setText(userInput.substring(0, 22));
    }

    private void leaveGroup() {
        if (selectedGroup.getId().equals(Client.GLOBAL_CHAT.getId())) {
        
        Client.viewNotification("Error Leaving this Group", "You can't leave the Global Chat,\nbut you can mute it if you want", false);

        return;
        }
        Client.sendMessage(Client.LEAVE_GROUP_REQUEST + selectedGroup.getId());
        OBSL_groups.remove(selectedGroup);
        Client.groups.remove(selectedGroup.getId());
        LSTV_groups.getSelectionModel().select(OBSL_groups.size() - 1);
        LSTV_chat.setItems(selectedGroup.getMessages());
        Platform.runLater(() -> { try { LBL_chatName.setText(selectedGroup.getName()); } catch (Exception e) { System.out.println("Error Finding the Selected Group"); } });
        Tooltip.install(LBL_chatName, new Tooltip(selectedGroup.getId()));
        
        Client.viewNotification("You Just Left the Group", "You Just Left the Group with the following id: " + selectedGroup.getId() + "\n and the following name: " + selectedGroup.getName(), true);
    }

    public Label getLBL_chatName() { return this.LBL_chatName; }
    
    @FXML
    void setDefaultUsername(ActionEvent event) { setNewName(Client.DEFAULT_USERNAME); }

    @FXML
    void initialize() {
        initHashMap();
        LSTV_groups.setItems(OBSL_groups);
        TC_groups.setCellValueFactory(new PropertyValueFactory<Group, String>("name"));
        TC_muteIcons.setCellValueFactory(new PropertyValueFactory<Group, ImageView>("mutedIcon"));
        setDefaultUsername(new ActionEvent());
        CMB_groupExpiration.getItems().addAll(Client.groupExpirations);
        CMB_groupExpiration.getSelectionModel().select(2);
        Client.sendMessage(Client.JOIN_REQUEST + Client.GLOBAL_CHAT.getId());




        Client.groups.put(Client.GLOBAL_CHAT.getId(), Client.GLOBAL_CHAT);
        
        Platform.runLater(() -> {
            fxmlController.OBSL_groups.add(Client.GLOBAL_CHAT);
            LSTV_groups.getSelectionModel().select(0);
            LSTV_chat.setItems(Client.groups.get(Client.GLOBAL_CHAT.getId()).getMessages()); 
            selectedGroup = LSTV_groups.getSelectionModel().getSelectedItem();
                    
            TXTF_chatName.setVisible(false); 
            TAB_Chat.setGraphic(new Circle(0, 0, 5, Paint.valueOf("CRIMSON")));
            TAB_Chat.getGraphic().setOpacity(0);


            new ChatModifier().start();
            new TabController().start();
        });      



        TXTF_chatName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) { TXTF_chatName.setVisible(false); LBL_chatName.setVisible(true); }
            }
        });



        LSTV_groups.setRowFactory(new Callback<TableView<Group>, TableRow<Group>>() {
            @Override
            public TableRow<Group> call(TableView<Group> tableView) {
                final TableRow<Group> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();                

                MenuItem muteItem = new MenuItem("Mute");
                MenuItem leaveItem = new MenuItem("Leave Group");
                
                contextMenu.getItems().addAll(muteItem, leaveItem);
                
                muteItem.setOnAction((event) -> { 
                    if (selectedGroup.isMuted()) {
                        System.out.println("Unmuted");
                        selectedGroup.setMuted(false);
                        selectedGroup.setMutedIcon(new ImageView());
                        muteItem.setText("Mute");
                        LSTV_groups.refresh();
                    }
                    else {
                        System.out.println("Muted");
                        selectedGroup.setMuted(true);
                        ImageView icon = new ImageView("resources/images/mute.png");
                        icon.setPreserveRatio(true);
                        icon.setSmooth(true);
                        icon.setFitHeight(18);
                        selectedGroup.setMutedIcon(icon);
                        muteItem.setText("Unmute");
                        LSTV_groups.refresh();
                    }
                });
                leaveItem.setOnAction((event) -> { System.out.println("Group Left"); leaveGroup(); });

               // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(contextMenu));
                return row ;
            }
        });


        Client.ctrlRef = this;
    }
}
