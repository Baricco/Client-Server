import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//IL COGLIONE CHE HA SCRITTO STA CAGATA VA UCCISO
public class Notification extends Popup {
   
    String title, message;

    private void init(String message) {
        this.setAutoFix(true);
        this.setAutoHide(true);
        this.setHideOnEscape(true);
        Label label = new Label(message);
        label.setOnMouseReleased(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent e) { hide(); } });
        label.getStylesheets().add("style.css");
        label.getStyleClass().add("popup");
        this.getContent().add(label);
    }
    
    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
        init(message);
    }

    public void show(Stage stage) {
        //QUESTA FUNZIONE NON VA
        this.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                setX(stage.getX() + stage.getWidth() / 2 - getWidth() / 2);
                setY(stage.getY() + stage.getHeight() / 2 - getHeight() / 2);
            }
        });        
    }
}
