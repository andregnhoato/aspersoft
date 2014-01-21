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
        engine.load("file://"+System.getProperty("user.dir")+"/help/inicio.html");

        StackPane sp = new StackPane();
        sp.getChildren().add(browser);
        
        setHeight(600);
        setWidth(800);
        
        Scene root = new Scene(sp);
        setScene(root);
    }
}
