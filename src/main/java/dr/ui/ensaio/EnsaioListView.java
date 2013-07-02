package dr.ui.ensaio;

import dr.model.Ensaio;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as mercadorias cadastradas. 
 * 
 * <p>A partir dessa tela é possível criar/editar ou pesquisar mercadoria.</p>
 * 
 * @author YaW Tecnologia
 */
public class EnsaioListView extends Stage{

    private Scene subScene;
    private EnsaioTable table;
    private Button bNewEnsaio;
    private Button bRefreshList;
    private Button bFindEnsaio;
    
    public EnsaioListView() {
        setTitle("Ensaios");
        setWidth(600);
        setHeight(400);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        
        inicializaComponentes();
        Group panel = new Group();
        HBox boxButtons = getButtonsBox();
        panel.getChildren().addAll(boxButtons, table);
        
        subScene = new Scene(panel);
        subScene.getStylesheets().add("style.css");
        
        
        
        //stage.setTitle("Lista de Mercadorias");
        //stage.setWidth(700);
        //stage.setHeight(510);
        //stage.setScene(mainScene);
        //stage.setResizable(false);
        //stage.centerOnScreen();
        //stage.show();
    }
    
    private void inicializaComponentes() {
        table = new EnsaioTable();
        
        bNewEnsaio = new Button("Novo");
        bNewEnsaio.getStyleClass().add("buttonGreen");
        bNewEnsaio.setId("addEnsaio");
        bFindEnsaio = new Button("Buscar");
        bFindEnsaio.getStyleClass().add("buttonLarge");
        bFindEnsaio.setId("findEnsaio");
        bRefreshList = new Button("Atualizar");
        bRefreshList.getStyleClass().add("buttonWhite");
        bRefreshList.setId("refreshList");
    }
    
    /*public void addTransition() {
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
    }*/
    
    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewEnsaio, bFindEnsaio, bRefreshList);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }
    
    public Button getNewButton() {
        return bNewEnsaio;
    }

    public Button getRefreshButton() {
        return bRefreshList;
    }

    public Button getFindButton() {
        return bFindEnsaio;
    }

    public EnsaioTable getTable() {
        return table;
    }

    public void refreshTable(List<Ensaio> ensaios) {
        table.reload(ensaios);        
    }

    private void disableButtonBar(boolean disable) {
        bNewEnsaio.setDisable(disable);
        bFindEnsaio.setDisable(disable);
        bRefreshList.setDisable(disable);
    }
}
