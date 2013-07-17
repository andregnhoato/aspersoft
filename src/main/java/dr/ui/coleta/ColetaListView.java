package dr.ui.coleta;

import dr.model.Ensaio;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as ensaios cadastradas. 
 * 
 * <p>A partir dessa tela é possível criar/editar ou pesquisar ensaio.</p>
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
        scene.getStylesheets().add("style.css");
        this.setScene(scene);
        
    }
    
    private void inicializaComponentes() {
        table = new ColetaTable();
    }
    
    public void reRenderTable(){
        table.reRenderTable(ensaio);
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
