import java.io.IOException;

import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;

public class MessageError {
    private static Pane root;
    private Pane window;
    private Hyperlink link;

    public MessageError(Pane window){ 
        this.window = window; 
        link = (Hyperlink)window.getChildren().get(0); 
        link.setOnMouseClicked((mouseEvent) -> {
            Runtime rt = Runtime.getRuntime();
            String url = "http://youtube.com";
            try { rt.exec("rundll32 url.dll,FileProtocolHandler " + url); } catch (IOException e) { }
        });
    }

    public void setVisible(boolean visible){root.setVisible(visible); window.setVisible(visible); }
    

    public static Pane getRoot() { return root; }

    public Pane getWindow() { return window; }

    public static void setRoot(Pane ROOT){ root = ROOT; root.setVisible(false); }
    
}
