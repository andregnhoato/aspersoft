package dr.app;

import dr.controller.ListaMercadoriaController;
import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Ponto de entrada da aplicacão.
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaApp extends Application {
    
    private ListaMercadoriaController controller;
    
    @Override
    public void start(Stage stage){
        Locale.setDefault(new Locale("pt","BR"));
        controller = new ListaMercadoriaController(stage);
    }

    @Override
    public void stop() throws Exception {
        controller.cleanUp();
    }
    
    public static void main(String[] args) {
        MercadoriaApp.launch(args);
    }
    
}
