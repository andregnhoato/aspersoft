package dr.ui.quebraJato;

import dr.model.QuebraJato;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as bocals cadastradas.
 *
 * <p>A partir dessa tela é possível criar/editar ou pesquisar bocal.</p>
 *
 * @author
 * @Andre
 */
public class QuebraJatoListView extends Stage {

    private Scene subScene;
    private QuebraJatoTable table;    
    private Button bNewQuebraJato;
    private Button bRefreshList;
    private Button bFindQuebraJato;
    
    public QuebraJatoListView() {
        setTitle("Quebra Jato");
        
        setResizable(true);

        initModality(Modality.APPLICATION_MODAL);

        inicializaComponentes();


        Group panel = new Group();
        HBox boxButtons = getButtonsBox();
        
        panel.getChildren().addAll(boxButtons, table);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add("style.css");
        this.setScene(scene);

    }

    private void inicializaComponentes() {
        table = new QuebraJatoTable();

        bNewQuebraJato = new Button("Novo");
        bNewQuebraJato.getStyleClass().add("buttonLarge");
        bNewQuebraJato.setId("addQuebraJato");
        bFindQuebraJato = new Button("Buscar");
        bFindQuebraJato.getStyleClass().add("buttonLarge");
        bFindQuebraJato.setId("findQuebraJato");
        bRefreshList = new Button("Atualizar");
        bRefreshList.getStyleClass().add("buttonLarge");
        bRefreshList.setId("refreshList");
        


    }

    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewQuebraJato, bFindQuebraJato, bRefreshList);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }

    public Button getNewButton() {
        return bNewQuebraJato;
    }

    public Button getRefreshButton() {
        return bRefreshList;
    }

    public Button getFindButton() {
        return bFindQuebraJato;
    }

    

    public QuebraJatoTable getTable() {
        return table;
    }

    public void refreshTable(List<QuebraJato> bocais) {
        table.reload(bocais);
    }

    private void disableButtonBar(boolean disable) {
        bNewQuebraJato.setDisable(disable);
        bFindQuebraJato.setDisable(disable);
        bRefreshList.setDisable(disable);
        
    }
}
