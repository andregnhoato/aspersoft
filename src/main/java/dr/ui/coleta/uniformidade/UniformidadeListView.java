package dr.ui.coleta.uniformidade;

import dr.model.Ensaio;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
    private ComboBox cbEspacamento;
    // assuming you have defined a StringProperty called "valueProperty"
//    /Label myLabel = new Label("Start");
//    myLabel.textProperty().bind(valueProperty);

    
    public UniformidadeListView() {
        this.ensaio = new Ensaio();
        setTitle("Uniformidade");
        //setWidth(800);
        //setHeight(500);
        
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        
        inicializaComponentes();
        
//        public void add(Node child, int columnIndex, int rowIndex, int colspan, int rowspan)
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(8);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Selecione o Espaçamento para visualizar a sobreposição: "), 0, 0,2,1);
        grid.add(cbEspacamento, 2, 0);
        grid.add(new Label("Metros "),3, 0);
        grid.add(new Label("Ensaio:"), 0, 1);       
        grid.add(new Label("linha 1 repetição 3"), 1, 1);
        grid.add(new Label("Pressão:"), 2, 1);
        grid.add(new Label("2.5 kgf:"), 3, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(new Label("21/01/2013"), 1, 2);
        grid.add(new Label("Vel. Vento:"), 2, 2);
        grid.add(new Label("0.9 m/s"), 3, 2);
        grid.add(new Label("Início:"), 0, 3);
        grid.add(new Label("10:00 horas"), 1, 3);
        grid.add(new Label("Direção vento:"), 2, 3);
        grid.add(new Label("WNE"), 3, 3);
        
                
        
        grid.add(table, 0, 4, 5, 1);
        
        
        Group panel = new Group();
        panel.getChildren().add(grid);
        
        Scene scene = new Scene(panel);
//        scene.getStylesheets().add("style.css");
        this.setScene(scene);
        
    }
    
    private void inicializaComponentes() {
        table = new UniformidadeTable();
        cbEspacamento = new ComboBox(getEspacamentos());
        cbEspacamento.setId("comboEspacamento");
    }
    
    public void reRenderTable(){
        table.reRenderTable(ensaio, (cbEspacamento.getValue() == null ? null :cbEspacamento.getValue().toString()));
    }
    
    public void renderTable(){
        table.renderTable();
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
    
    public ComboBox getComboEspacamento() {
        return cbEspacamento;
    }
    
    private ObservableList getEspacamentos() {
        ObservableList<String> espacamentos =
                FXCollections.observableArrayList(
                "12x12",
                "12x18",
                "12x24",
                "18x18",
                "18x24",
                "18x30",
                "24x24",
                "24x30",
                "30x30");

        return espacamentos;
    }
    

}
