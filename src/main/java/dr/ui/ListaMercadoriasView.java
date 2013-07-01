package dr.ui;

import dr.model.Mercadoria;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Tela principal da aplicação. Apresenta uma lista com as mercadorias cadastradas. 
 * 
 * <p>A partir dessa tela é possível criar/editar ou pesquisar mercadoria.</p>
 * 
 * @author YaW Tecnologia
 */
public class ListaMercadoriasView {

    private Scene mainScene;
    private MercadoriaTable tabela;
    private Button bNewMercadoria;
    private Button bRefreshLista;
    private Button bFindMercadoria;
    private MenuItem menuSobre;
    
    public ListaMercadoriasView(Stage stage) {
        inicializaComponentes();
        Group panel = new Group();
        mainScene = new Scene(panel);
        mainScene.getStylesheets().add("style.css");
        
        MenuBar menuBar = getMenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        HBox boxButtons = getButtonsBox();
        
        panel.getChildren().addAll(boxButtons, tabela, menuBar);
        
        stage.setTitle("Lista de Mercadorias");
        stage.setWidth(700);
        stage.setHeight(510);
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    private void inicializaComponentes() {
        tabela = new MercadoriaTable();
        
        bNewMercadoria = new Button("Nova");
        bNewMercadoria.getStyleClass().add("buttonGreen");
        bNewMercadoria.setId("incluirMercadoria");
        bFindMercadoria = new Button("Buscar");
        bFindMercadoria.getStyleClass().add("buttonLarge");
        bFindMercadoria.setId("buscarMercadorias");
        bRefreshLista = new Button("Atualizar");
        bRefreshLista.getStyleClass().add("buttonWhite");
        bRefreshLista.setId("atualizarMercadorias");
    }
    
    public void addTransition() {
        disableButtonBar(true);
        FadeTransition ft = new FadeTransition(Duration.millis(2000), tabela);
        ft.setFromValue(0.2);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.play();
        
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                disableButtonBar(false);
            }
        });
    }
    
    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewMercadoria, bFindMercadoria, bRefreshLista);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }
    
    private MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuAjuda = new Menu("Ajuda");
        menuSobre = new MenuItem("Sobre");
        menuSobre.setId("exibirSobre");
        menuSobre.setAccelerator(KeyCombination.keyCombination("F1"));
        
        menuAjuda.getItems().addAll(menuSobre);
        menuBar.getMenus().addAll(menuAjuda);
        return menuBar;
    }

    public Button getNewButton() {
        return bNewMercadoria;
    }

    public Button getRefreshButton() {
        return bRefreshLista;
    }

    public Button getFindButton() {
        return bFindMercadoria;
    }

    public MercadoriaTable getTabela() {
        return tabela;
    }

    public MenuItem getMenuSobre() {
        return menuSobre;
    }
    
    public void refreshTable(List<Mercadoria> mercadorias) {
        tabela.reload(mercadorias);        
    }

    private void disableButtonBar(boolean disable) {
        bNewMercadoria.setDisable(disable);
        bFindMercadoria.setDisable(disable);
        bRefreshLista.setDisable(disable);
    }
}
