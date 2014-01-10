package dr.ui.ensaio;

import dr.model.Ensaio;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    private Label processando;
    private Button bNewEnsaio;
    private Button bRefreshList;
    private Button bFindEnsaio;
    private Button bColeta;
    private Button bUniformidade;
    private Button bSimulado;
    private ProgressBar pi;

    public EnsaioListView() {
        setTitle("Ensaios");

        setResizable(true);

        initModality(Modality.APPLICATION_MODAL);

        inicializaComponentes();

//        GridPane panel = new GridPane();
        Group panel = new Group();
        HBox boxButtons = getButtonsBox();
       
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
        bSimulado = new Button("Simulação");
        bSimulado.getStyleClass().add("buttonLarge");
        bSimulado.setId("simulado");
        processando = new Label("Processando simulação");
        processando.setVisible(false);
        pi = new ProgressBar();
        pi.setVisible(false);


    }

    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewEnsaio, bFindEnsaio, bRefreshList, bColeta, bUniformidade, bSimulado, processando, pi);
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

    public Button getSimuladoButton() {
        return bSimulado;
    }

    public EnsaioTable getTable() {
        return table;
    }

    public void refreshTable(List<Ensaio> ensaios) {
        table.reload(ensaios);
    }
    
    public void showProgress(boolean b){
        processando.setVisible(b);
        pi.setVisible(b);
    }
    
    public void disableButtonBar(boolean disable) {
        bNewEnsaio.setDisable(disable);
        bFindEnsaio.setDisable(disable);
        bRefreshList.setDisable(disable);
        bColeta.setDisable(disable);
        bUniformidade.setDisable(disable);
        bSimulado.setDisable(disable);

    }
}
