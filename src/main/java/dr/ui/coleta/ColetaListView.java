package dr.ui.coleta;

import dr.model.Ensaio;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
    private Button BtExportaExcel;
    private Label coleta; 
//    private Button btVisualizarHora;
    
    public ColetaListView() {
        setTitle("Coletas");
        
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        
        inicializaComponentes();
        
        GridPane panel = new GridPane();
        if(ensaio.getColetaHora()){
            panel.add(coleta, 0,0,3,1);
            panel.add(table, 0, 1,3,1);
            panel.add(BtExportaExcel, 0, 2);
        }else{
            panel.add(table, 0, 0,3,1);
            panel.add(BtExportaExcel, 0, 1);
        }
        Scene scene = new Scene(panel);
        scene.getStylesheets().add("style.css");
        this.setScene(scene);
        
    }
    
    private void inicializaComponentes() {
        table = new ColetaTable();
        BtExportaExcel = new Button("Exportar");
        BtExportaExcel.getStyleClass().add("buttonGreen");
        BtExportaExcel.setId("btExportar");

    }
    
    public void reRenderTable(){
        table.reRenderTable(ensaio);
    }

    public ColetaTable getTable() {
        return table;
    }
    
    public Ensaio getEnsaio(){
        return this.ensaio;
    }
    
    public Button getButtonExportar(){
        return this.BtExportaExcel;
    }
    

    public void refreshTable(List<Ensaio> ensaios) {
        //table.reload(ensaios);        
    }
    
    public void setEnsaio(Ensaio e){
        this.ensaio = e;
    }

}
