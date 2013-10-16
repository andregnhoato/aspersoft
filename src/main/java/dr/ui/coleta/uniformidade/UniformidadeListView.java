package dr.ui.coleta.uniformidade;

import dr.model.Ensaio;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as informações referentes a unifomidade são elas:
 *  coeficientes, sobreposição e Ensaio resumido
 * 
 * <p>A partir dessa tela é possível visualizar uniformidades do Ensaio.</p>
 * 
 * @author @Andre
 */
public class UniformidadeListView extends Stage{

    private Scene subScene;
    private UniformidadeTable table;
    private Ensaio ensaio;
    
    public UniformidadeListView() {
        this.ensaio = new Ensaio();
        setTitle("Uniformidade");
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
        table = new UniformidadeTable();
    }
    
    public void reRenderTable(){
        table.reRenderTable(ensaio);
    }

    public UniformidadeTable getTable() {
        return table;
    }

    public void refreshTable(List<Ensaio> ensaios) {
        //table.reload(ensaios);        
    }
    
    public void setEnsaio(Ensaio e){
        this.ensaio = e;
    }

}
