package dr.ui.combinacao;

import dr.ui.ensaio.*;
import dr.model.Combinacao;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as combinacoes cadastradas.
 *
 * <p>A partir dessa tela é possível criar/editar ou pesquisar ensaio.</p>
 *
 * @author
 * @Andre
 */
public class CombinacaoListView extends Stage {

    private Scene subScene;
    private CombinacaoTable table;
    private Label countCombinacao;
    private Button bNewCombinacao;
    private Button bRefreshList;
    private Button bFindCombinacao;
    private Button bImprimir;
//    private Button bUniformidade;

    public CombinacaoListView() {
        setTitle("Tabela Desempenho Aspersor Pingo");
        
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
        table = new CombinacaoTable();

        bNewCombinacao = new Button("Novo");
        bNewCombinacao.getStyleClass().add("buttonLarge");
        bNewCombinacao.setId("addCombinacao");
        bFindCombinacao = new Button("Buscar");
        bFindCombinacao.getStyleClass().add("buttonLarge");
        bFindCombinacao.setId("findCombinacao");
        bRefreshList = new Button("Atualizar");
        bRefreshList.getStyleClass().add("buttonLarge");
        bRefreshList.setId("refreshList");
        bImprimir = new Button("Imprimir");
        bImprimir.getStyleClass().add("buttonLarge");
        bImprimir.setId("addColeta");
        

    }

    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewCombinacao, bFindCombinacao, bRefreshList, bImprimir);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }

    private HBox getLabelBox() {
        
        countCombinacao = new Label();
        countCombinacao.setText(table.getTotalCombinacao()+"");
        HBox box = new HBox();
        box.getChildren().add(new Label("Total :"));
        box.getChildren().add(countCombinacao);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }

    public Button getNewButton() {
        return bNewCombinacao;
    }

    public Button getRefreshButton() {
        return bRefreshList;
    }

    public Button getFindButton() {
        return bFindCombinacao;
    }

    public Button getImprimirButton() {
        return bImprimir;
    }

    public CombinacaoTable getTable() {
        return table;
    }

    public void refreshTable(List<Combinacao> combinacoes) {
        table.reload(combinacoes);
    }

    private void disableButtonBar(boolean disable) {
        bNewCombinacao.setDisable(disable);
        bFindCombinacao.setDisable(disable);
        bRefreshList.setDisable(disable);
        bImprimir.setDisable(disable);
    }
}
