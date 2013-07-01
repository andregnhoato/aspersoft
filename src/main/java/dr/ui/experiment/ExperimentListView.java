package dr.ui.experiment;

import dr.model.Experiment;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
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
public class ExperimentListView extends Stage{

    private Scene subScene;
    private ExperimentTable table;
    private Button bNewExperiment;
    private Button bRefreshList;
    private Button bFindExperiment;
    
    public ExperimentListView() {
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
        table = new ExperimentTable();
        
        bNewExperiment = new Button("Novo");
        bNewExperiment.getStyleClass().add("buttonGreen");
        bNewExperiment.setId("addExperiment");
        bFindExperiment = new Button("Buscar");
        bFindExperiment.getStyleClass().add("buttonLarge");
        bFindExperiment.setId("findExperiment");
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
        box.getChildren().addAll(bNewExperiment, bFindExperiment, bRefreshList);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }
    
    public Button getNewButton() {
        return bNewExperiment;
    }

    public Button getRefreshButton() {
        return bRefreshList;
    }

    public Button getFindButton() {
        return bFindExperiment;
    }

    public ExperimentTable getTable() {
        return table;
    }

    public void refreshTable(List<Experiment> experiments) {
        table.reload(experiments);        
    }

    private void disableButtonBar(boolean disable) {
        bNewExperiment.setDisable(disable);
        bFindExperiment.setDisable(disable);
        bRefreshList.setDisable(disable);
    }
}
