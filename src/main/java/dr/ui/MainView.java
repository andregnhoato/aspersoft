package dr.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação.
 *
 * @author
 * @Andre
 */
public class MainView {

    private Scene mainScene;
    private MenuItem menuAbout;
    private MenuItem menuEnsaio;
    private MenuItem menuEnsaioList;
    private MenuItem menuConfiguracao;

    public MainView(Stage stage) {
        //inicializaComponentes();
        Group panel = new Group();
        mainScene = new Scene(panel);
        mainScene.getStylesheets().add("style.css");

        MenuBar menuBar = getMenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        panel.getChildren().addAll(menuBar);

        Image image = new Image(getClass().getResourceAsStream("/logo.png"));
        stage.getIcons().add(image);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.setTitle("Aspersoft 1.1.2");
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
        Menu menuStart = new Menu("Início");
//        Menu menuConfig = new Menu("Preferências");

        menuEnsaio = new MenuItem("Ensaio");
        menuEnsaio.setId("ensaio");
        menuEnsaio.setAccelerator(KeyCombination.keyCombination("F2"));

        menuEnsaioList = new MenuItem("Listagem Ensaio");
        menuEnsaioList.setId("ensaioList");
        menuEnsaioList.setAccelerator(KeyCombination.keyCombination("F3"));
        
        menuConfiguracao = new MenuItem("Prefêrencias");
        menuConfiguracao.setId("config");
        menuConfiguracao.setAccelerator(KeyCombination.keyCombination("F4"));

        menuAbout = new MenuItem("Sobre");
        menuAbout.setId("exibirSobre");
        menuAbout.setAccelerator(KeyCombination.keyCombination("F1"));

        menuHelp.getItems().addAll(/*menuAbout ,*/ menuConfiguracao);
        menuStart.getItems().addAll(menuEnsaio, menuEnsaioList);
//        menuConfig.getItems().addAll(menuConfiguracao);
        menuBar.getMenus().addAll(menuStart ,menuHelp);
        return menuBar;
    }

    public MenuItem getMenuAbout() {
        return menuAbout;
    }

    public void setMenuAbout(MenuItem menuAbout) {
        this.menuAbout = menuAbout;
    }

    public MenuItem getMenuEnsaio() {
        return menuEnsaio;
    }

    public void setMenuEnsaio(MenuItem menuEnsaio) {
        this.menuEnsaio = menuEnsaio;
    }

    public MenuItem getMenuEnsaioList() {
        return menuEnsaioList;
    }

    public void setMenuEnsaioList(MenuItem menuEnsaioList) {
        this.menuEnsaioList = menuEnsaioList;
    }

    public MenuItem getMenuConfiguracao() {
        return menuConfiguracao;
    }

    public void setMenuConfiguracao(MenuItem menuConfiguracao) {
        this.menuConfiguracao = menuConfiguracao;
    }
}
