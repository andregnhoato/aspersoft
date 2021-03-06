package dr.ui.bocal;

import dr.model.Bocal;
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
public class BocalListView extends Stage {

    private BocalTable table;    
    private Button bNewBocal;
    private Button bRefreshList;
    private Button bFindBocal;
    private Button bEditBocal;
    
    public BocalListView() {
        setTitle("Bocais");
        
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
        table = new BocalTable();

        bNewBocal = new Button("Novo");
        bNewBocal.getStyleClass().add("buttonLarge");
        bNewBocal.setId("addBocal");
        bFindBocal = new Button("Buscar");
        bFindBocal.getStyleClass().add("buttonLarge");
        bFindBocal.setId("findBocal");
        bRefreshList = new Button("Atualizar");
        bRefreshList.getStyleClass().add("buttonLarge");
        bRefreshList.setId("refreshList");
        
        bEditBocal = new Button("Editar");
        bEditBocal.getStyleClass().add("buttonLarge");
        bEditBocal.setId("editList");
        
        


    }

    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewBocal, bEditBocal, bFindBocal, bRefreshList);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }

    public Button getNewButton() {
        return bNewBocal;
    }

    public Button getRefreshButton() {
        return bRefreshList;
    }

    public Button getFindButton() {
        return bFindBocal;
    }

    public Button getEditBocal() {
        return bEditBocal;
    }
    
    public BocalTable getTable() {
        return table;
    }

    public void refreshTable(List<Bocal> bocais) {
        table.reload(bocais);
    }

    private void disableButtonBar(boolean disable) {
        bNewBocal.setDisable(disable);
        bFindBocal.setDisable(disable);
        bRefreshList.setDisable(disable);
        bEditBocal.setDisable(disable);
        
    }
}
