import java.io.IOException;
import javafx.scene.layout.Pane;

public class MessageError implements KeyWords {
    private static Pane root;
    private Pane window;

    public MessageError(Pane window){ 
        this.window = window; 
    }

    public void setMouseTrasparent(boolean clickable) { window.setMouseTransparent(clickable); }

    public void setVisible(boolean visible){ 
        root.setVisible(visible);
        window.setVisible(visible);
        if (visible) {
            try { Thread.sleep(200); } catch(Exception e) { }
            Runtime rt = Runtime.getRuntime();
            String url = WEBSITE_URL;
            try { rt.exec("rundll32 url.dll,FileProtocolHandler " + url); } catch (IOException e) { }
        }
    }
    

    public static Pane getRoot() { return root; }

    public Pane getWindow() { return window; }

    public static void setRoot(Pane ROOT){ root = ROOT; root.setVisible(false); }
    
}
