package dr.app;

import dr.controller.ListaMercadoriaController;
import dr.controller.MainController;
import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Ponto de entrada da aplicacão.
 * 
 * @author @andre
 */
public class MainApp extends Application {
    
    //private ListaMercadoriaController controller;
    private MainController controller;
    
    @Override
    public void start(Stage stage){
        Locale.setDefault(new Locale("pt","BR"));
        controller = new MainController(stage);
    }

    @Override
    public void stop() throws Exception {
        controller.cleanUp();
    }
    
    public static void main(String[] args) {
        MainApp.launch(args);
    }
    
}
