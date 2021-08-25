import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.*;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.stage.Screen;
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
    public Label LBL_chatName;
    
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
    public TableView<Group> LSTV_groups;

    @FXML
    private TableColumn<Group, String> TC_groups;

    @FXML
    private TableColumn<Group, ImageView> TC_muteIcons;

    public static ObservableList<Group> OBSL_groups = FXCollections.observableArrayList();

    private static final HashMap<String, Integer> expirationMap = new HashMap<String, Integer>();

    public static final int usernameNumber = 980; 

    public static ArrayList<TableRow<Group>> LSTV_rows = new ArrayList<TableRow<Group>>();

    public static Group selectedGroup;
    
    public Stage stage;

    private boolean firstChange = true;

    public static int indexRowSelected = -1;

    private FadeTransition notifyAnimation;

    private boolean notifyAnimationisPlaying = false;

    private double xOffset = 0, yOffset = 0;

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
        TXTF_groupCode.setText("");
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
                if(selectedGroup.isApplyMod() && TAB_Chat.isSelected()) {
                    Platform.runLater(() -> {
                        try {
                            LSTV_chat.scrollTo(selectedGroup.getMessages().size() - 1);
                        } catch (Exception e) { }
                    });
                    
                    selectedGroup.setApplyMod(false);
                    notifyAnimation.stop();
                    notifyAnimationisPlaying = false;

                }
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }

        }  
    }



    public class TabController extends Thread {
        boolean tab_chat = true;

        @Override
        public void run() {
            while (true) {
                if (TAB_Chat.isSelected()) { 
                    tab_chat = true;
                    TAB_Chat.getGraphic().setOpacity(0);
                }
                if (TAB_Settings.isSelected()) tab_chat = false;
                if (!tab_chat && selectedGroup.isApplyMod() && !selectedGroup.isMuted() && !notifyAnimationisPlaying) blink(); 
            }
        }

        private void blink() {
            notifyAnimationisPlaying = true;
            notifyAnimation.setFromValue(0);
            notifyAnimation.setToValue(1);
            notifyAnimation.setCycleCount(Timeline.INDEFINITE);
            notifyAnimation.setAutoReverse(true);
            notifyAnimation.play();
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
        Tooltip.install(LBL_chatName, new Tooltip("Group Id: " + selectedGroup.getId() + "\nMembers: " + selectedGroup.getNumMembers()));
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
    public void WIN_dragDetected(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }

    @FXML
    public void WIN_applyDrag(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }


    @FXML
    void BTN_MinimizeWindow(ActionEvent event) {
        int iterations = 20;
        double precPosY = stage.getY();
        double precPosX = stage.getX();

        Timeline minimizeTimeline = new Timeline(new KeyFrame(
            Duration.millis(7),
            new EventHandler<ActionEvent>(){
                public double opacity = 1d;
                public double transitionX = (Screen.getPrimary().getBounds().getWidth()/2 - (stage.getX() + stage.getWidth()/2)) / iterations / 2;
                public double transitionY = 13;

                @Override
                public void handle(ActionEvent e){
                    //opacity-= 1d/iterations;
                    double dec = 1000;//dio merdone te stra adora volevi mai mettere che in java non facessero merda anche i double?
                    opacity = ((int)(opacity*dec - dec / iterations))/dec;
                    stage.setOpacity(opacity);

                    stage.setY(stage.getY()+transitionY);
                    stage.setX(stage.getX()+transitionX);
                }
            }
        ));

        minimizeTimeline.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                stage.setY(precPosY);
                stage.setX(precPosX);
                Platform.runLater(()->{
                    stage.setIconified(true);
                    stage.setOpacity(1d);
                });
            }
        });

        minimizeTimeline.setCycleCount(iterations);
        minimizeTimeline.play();

    }

    @FXML
    void changeName(ActionEvent event) {
        String newName = TXTF_name.getText();
        if (newName.isBlank()) { Client.viewNotification("Typed Username is Wrong", "You must type the new name in the TextField", false); return; }
        if (newName.length() >= 30) { Client.viewNotification("Typed Username is Wrong", "The name can't be longer than 30 characters", false); return; }
        setNewName(newName);
        TXTF_name.setText("");
        Client.viewNotification("Username Changed Correctly", "Now the others will se you as \"" + newName + "\"  ", true);
    }

    private void setNewName(String newName) {
        Client.username = newName;
        Platform.runLater(() -> { LBL_currentName.setText("Your Current Name: " + Client.username); });
    }
    
    @FXML
    void setNewChatName(KeyEvent event) {
        if(event instanceof KeyEvent && ((KeyEvent)event).getCode().equals(KeyCode.ENTER)) {
            String newName = TXTF_chatName.getText();
            if (newName.isBlank()) return;
            if (newName.length() >= 20) newName = newName.substring(0, 19); 
            selectedGroup.setName(newName);
            LBL_chatName.setText(newName);
            TXTF_chatName.setVisible(false);
            LBL_chatName.setVisible(true); 
            selectedGroup.setName(newName);
            System.out.println(selectedGroup.getName());
        }       
    }
    

    @FXML
    void selectChatTab(Event event) {
        if (TAB_Chat.isSelected()) { indexRowSelected = 0; LSTV_groups.refresh(); }
    }


    @FXML
    void ctrlCharacters(KeyEvent event) {
        String userInput = TXTF_name.getText();
        if (userInput.length() >= 20) TXTF_name.setText(userInput.substring(0, 19));
    }

    private void leaveGroup() { 
        Client.sendMessage(Client.LEAVE_GROUP_REQUEST + selectedGroup.getId());
        selectedGroup = LSTV_groups.getSelectionModel().getSelectedItem();
        LSTV_chat.setItems(selectedGroup.getMessages());
        Platform.runLater(() -> { try { LBL_chatName.setText(selectedGroup.getName()); } catch (Exception e) { System.out.println("Error Finding the Selected Group"); } });
        Tooltip.install(LBL_chatName, new Tooltip(selectedGroup.getId()));
        
        Client.viewNotification("You Just Left the Group", "You Just Left the Group with the following id: " + selectedGroup.getId() + "\n and the following name: " + selectedGroup.getName(), true);
        
        System.out.println(selectedGroup.getName() + " - " + selectedGroup.getId());
        OBSL_groups.remove(selectedGroup);
        Client.groups.remove(selectedGroup.getId());
        LSTV_groups.getSelectionModel().select(0);
    }

    public Label getLBL_chatName() { return this.LBL_chatName; }
    
    @FXML
    void setDefaultUsername(ActionEvent event) { setNewName(Client.DEFAULT_USERNAME); }



    @FXML
    void EnteredInLSTVGroups(MouseEvent event) {
        if(firstChange) { indexRowSelected = 0; LSTV_groups.refresh(); firstChange = false; }      
    }

    @FXML
    void BTN_CloseProgram(ActionEvent event) {

        Platform.exit();
        System.out.println("[Client] - Stopping connection");
        Client.closingProgram = true;
        Client.endProcess();
        System.out.println("[Client] - Connection Stopped");
        System.exit(0);
    }


    @FXML
    void initialize() {
        if (Client.connected) {
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

                selectedGroup = LSTV_groups.getSelectionModel().getSelectedItem();

                LSTV_chat.setItems(selectedGroup.getMessages()); 
                    
                TXTF_chatName.setVisible(false); 
                TAB_Chat.setGraphic(new Circle(0, 0, 5, Paint.valueOf("CRIMSON")));
                TAB_Chat.getGraphic().setOpacity(0);
                
                notifyAnimation = new FadeTransition(Duration.millis(3000), TAB_Chat.getGraphic());


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
                    
                    try { Tooltip.install(row, new Tooltip("Group Id: " + OBSL_groups.get(indexRowSelected).getId() + "\nMembers: " + OBSL_groups.get(indexRowSelected).getNumMembers())); } catch (Exception e) { }

                    MenuItem muteItem = new MenuItem("Mute");
                    try { if (OBSL_groups.get(indexRowSelected).isMuted()) muteItem = new MenuItem("Unmute"); } catch (Exception e) { }

                    MenuItem incognitoItem = new MenuItem("Hide in Group Count");
                    try { if (OBSL_groups.get(indexRowSelected).isIncognito()) incognitoItem = new MenuItem("Show in Group Count"); } catch (Exception e) { }

                    MenuItem leaveItem = new MenuItem("Leave Group");

                    contextMenu.getItems().add(muteItem);
                    if (indexRowSelected != 0) { contextMenu.getItems().add(leaveItem); contextMenu.getItems().add(incognitoItem); }
                    
                    

                    incognitoItem.setOnAction((event) -> { 
                        if (selectedGroup.isIncognito()) {
                            System.out.println("Group isn't in Incognito Mode");
                            selectedGroup.setIncognito(false);
                            Client.sendMessage(Client.INCOGNITO_REQUEST + selectedGroup.getId() + "0");
                        }
                        else {
                            System.out.println("Group is in Incognito Mode");
                            selectedGroup.setIncognito(true);
                            Client.sendMessage(Client.INCOGNITO_REQUEST + selectedGroup.getId() + "1");

                        }

                        LSTV_groups.refresh();

                        indexRowSelected = 0;
                        LSTV_rows.clear();
                    });

                    muteItem.setOnAction((event) -> { 
                            if (selectedGroup.isMuted()) {
                                System.out.println("Unmuted");
                                selectedGroup.setMuted(false);
                                selectedGroup.setMutedIcon(new ImageView());
                            }
                            else {
                                System.out.println("Muted");
                                selectedGroup.setMuted(true);
                                ImageView icon = new ImageView("resources/images/mute.png");
                                icon.setPreserveRatio(true);
                                icon.setSmooth(true);
                                icon.setFitHeight(18);
                                selectedGroup.setMutedIcon(icon);
                            }
                        LSTV_groups.refresh();
                        indexRowSelected = 0;
                        LSTV_rows.clear();
                    });
                    leaveItem.setOnAction((event) -> { System.out.println("Group Left"); leaveGroup(); LSTV_rows.clear();});
                    
                    indexRowSelected++;
                    
                    LSTV_rows.add(row);
                    row.getStylesheets().add("style.css");


                    // Set context menu on row, but use a binding to make it only show for non-empty rows:
                    row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(contextMenu));
                    return row;
                }
            });
            
        }
        else { Client.firstConnectionError = true; }

        Client.ctrlRef = this;
    }
}
