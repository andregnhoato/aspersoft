package dr.ui.coleta;

import dr.model.Ensaio;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as coletas. 
 * 
 * <p>A partir dessa tela é possível atualizar/editar as coletas.</p>
 * 
 * @author @Andre
 */
public class ColetaListView extends Stage{

    private Scene subScene;
    private ColetaTable table;
    private Ensaio ensaio = new Ensaio();
    
    public ColetaListView() {
        setTitle("Coletas");
        //setWidth(800);
        //setHeight(500);
        
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        
        inicializaComponentes();
        
        Group panel = new Group();
        panel.getChildren().addAll(table);
        
        Scene scene = new Scene(panel);
//        scene.getStylesheets().add("style.css");
        this.setScene(scene);
        
    }
    
    private void inicializaComponentes() {
        table = new ColetaTable();
    }
    
    public void reRenderTable(){
        table.reRenderTable(ensaio);
    }

    public ColetaTable getTable() {
        return table;
    }

    public void refreshTable(List<Ensaio> ensaios) {
        //table.reload(ensaios);        
    }
    
    public void setEnsaio(Ensaio e){
        this.ensaio = e;
    }

}
