package dr.ui.ajuda;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author andregnhoato
 */
public class AjudaView extends Stage {

    public AjudaView() {
        setTitle("Guia de Utilização Aspersoft");
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
//        String url = "https://dl.dropboxusercontent.com/u/10055997/help/inicio.html";
        String url = System.getProperty("user.dir")+"/help/inicio.html";
        engine.load(url);

        StackPane sp = new StackPane();
        sp.getChildren().add(browser);
        
        setHeight(600);
        setWidth(800);
        
        Scene root = new Scene(sp);
        setScene(root);
    }
}
