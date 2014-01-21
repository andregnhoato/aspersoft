package dr.ui.combinacao;

import dr.model.Combinacao;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
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

    private CombinacaoTable table;
    private Button bNewCombinacao;
    private Button bRefreshList;
    private Button bFindCombinacao;
    private Button bImprimir;

    public CombinacaoListView() {
        setTitle("Tabela Desempenho Aspersor Pingo");
        
        setResizable(true);

        initModality(Modality.APPLICATION_MODAL);

        inicializaComponentes();

        GridPane panel = new GridPane();
        panel.add(table, 0, 0);
        panel.add(getButtonsBox() , 0, 1);
        Scene scene = new Scene(panel);
        scene.getStylesheets().add("style.css");
        this.setScene(scene);

    }

    private void inicializaComponentes() {
        table = new CombinacaoTable();

        bNewCombinacao = new Button("Novo");
        bNewCombinacao.getStyleClass().add("buttonLarge");
        bNewCombinacao.setId("addCombinacao");
        bNewCombinacao.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bFindCombinacao = new Button("Buscar");
        bFindCombinacao.getStyleClass().add("buttonLarge");
        bFindCombinacao.setId("findCombinacao");
        bFindCombinacao.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bRefreshList = new Button("Atualizar");
        bRefreshList.getStyleClass().add("buttonLarge");
        bRefreshList.setId("refreshList");
        bRefreshList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bImprimir = new Button("Imprimir");
        bImprimir.getStyleClass().add("buttonLarge");
        bImprimir.setId("addColeta");
        bImprimir.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        

    }

    private TilePane getButtonsBox() {
        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setPadding(new Insets(10, 5, 10, 0));
        tileButtons.setHgap(5.0);
        tileButtons.setVgap(5.0);                                   
        tileButtons.getChildren().addAll(bNewCombinacao, bFindCombinacao, bRefreshList, bImprimir);
        
        return tileButtons;
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

}
