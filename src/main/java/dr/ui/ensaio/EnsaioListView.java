package dr.ui.ensaio;

import dr.model.Ensaio;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as ensaios cadastradas.
 *
 * <p>A partir dessa tela é possível criar/editar ou pesquisar ensaio.</p>
 *
 * @author
 * @Andre
 */
public class EnsaioListView extends Stage {

    private Scene subScene;
    private EnsaioTable table;
    private Label countEnsaio;
    private Button bNewEnsaio;
    private Button bRefreshList;
    private Button bFindEnsaio;
    private Button bColeta;
    private Button bUniformidade;
    private Button bSimulado;

    public EnsaioListView() {
        setTitle("Ensaios");
        
        setResizable(true);

        initModality(Modality.APPLICATION_MODAL);

        inicializaComponentes();

//        GridPane panel = new GridPane();
        Group panel = new Group();
        HBox boxButtons = getButtonsBox();
        HBox boxLabel = getLabelBox();
//        panel.add(boxLabel, 0,0);
//        panel.add(table, 0, 1);
//        panel.add(boxButtons, 0,2);
        panel.getChildren().addAll(boxButtons, table);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add("style.css");
        this.setScene(scene);

    }

    private void inicializaComponentes() {
        table = new EnsaioTable();

        bNewEnsaio = new Button("Novo");
        bNewEnsaio.getStyleClass().add("buttonLarge");
        bNewEnsaio.setId("addEnsaio");
        bFindEnsaio = new Button("Buscar");
        bFindEnsaio.getStyleClass().add("buttonLarge");
        bFindEnsaio.setId("findEnsaio");
        bRefreshList = new Button("Atualizar");
        bRefreshList.getStyleClass().add("buttonLarge");
        bRefreshList.setId("refreshList");
        bColeta = new Button("Coleta");
        bColeta.getStyleClass().add("buttonLarge");
        bColeta.setId("addColeta");
        bUniformidade = new Button("Análise");
        bUniformidade.getStyleClass().add("buttonLarge");
        bUniformidade.setId("showUniformidade");
        bSimulado = new Button("Simulado");
        bSimulado.getStyleClass().add("buttonLarge");
        bSimulado.setId("simulado");


    }

    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewEnsaio, bFindEnsaio, bRefreshList, bColeta, bUniformidade, bSimulado);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }

    private HBox getLabelBox() {
        
        countEnsaio = new Label();
        countEnsaio.setText(table.getTotalEnsaio()+"");
        HBox box = new HBox();
        box.getChildren().add(new Label("Total :"));
        box.getChildren().add(countEnsaio);
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

    public Button getColetaButton() {
        return bColeta;
    }

    public Button getUniformidadeButton() {
        return bUniformidade;
    }
    
    public Button getSimuladoButton(){
        return bSimulado;
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
        bColeta.setDisable(disable);
        bUniformidade.setDisable(disable);
        
    }
}
