package dr.ui;

import dr.ui.experiment.ExperimentListView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. 
 * @author @Andre
 */
public class MainView {

    private Scene mainScene;
    private MenuItem menuAbout;
    private MenuItem menuExperiment;
    private MenuItem menuExperimentList;
    
    public MainView(Stage stage) {
        //inicializaComponentes();
        Group panel = new Group();
        mainScene = new Scene(panel);
        mainScene.getStylesheets().add("style.css");
        
        MenuBar menuBar = getMenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        //HBox boxButtons = experimentListView.getButtonsBox();
        
        panel.getChildren().addAll(menuBar);
        
        stage.setTitle("Definir Nome");
        stage.setWidth(700);
        stage.setHeight(510);
        stage.setScene(mainScene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
    
    private void inicializaComponentes() {
        //start ui componentes 
    }
    
    private MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuHelp = new Menu("Ajuda");
        Menu menuStart = new Menu("Inicio");
        
        menuExperiment = new MenuItem("Ensaio");
        menuExperiment.setId("experiment");
        menuExperiment.setAccelerator(KeyCombination.keyCombination("F2"));
        
        menuExperimentList = new MenuItem("Listagem Ensaio");
        menuExperimentList.setId("experimentList");
        menuExperimentList.setAccelerator(KeyCombination.keyCombination("F3"));
        
        menuAbout = new MenuItem("Sobre");
        menuAbout.setId("exibirSobre");
        menuAbout.setAccelerator(KeyCombination.keyCombination("F1"));
        
        menuHelp.getItems().addAll(menuAbout);
        menuStart.getItems().addAll(menuExperiment, menuExperimentList);
        menuBar.getMenus().addAll(menuStart, menuHelp);
        return menuBar;
    }

    public MenuItem getMenuAbout() {
        return menuAbout;
    }

    public void setMenuAbout(MenuItem menuAbout) {
        this.menuAbout = menuAbout;
    }

    public MenuItem getMenuExperiment() {
        return menuExperiment;
    }

    public void setMenuExperiment(MenuItem menuExperiment) {
        this.menuExperiment = menuExperiment;
    }

    public MenuItem getMenuExperimentList() {
        return menuExperimentList;
    }

    public void setMenuExperimentList(MenuItem menuExperimentList) {
        this.menuExperimentList = menuExperimentList;
    }
}
