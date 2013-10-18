package dr.ui.coleta.uniformidade;

import dr.model.Ensaio;
import dr.util.DateUtil;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as informações
 * referentes a unifomidade são elas: coeficientes, sobreposição e Ensaio
 * resumido
 *
 * <p>A partir dessa tela é possível visualizar uniformidades do Ensaio.</p>
 *
 * @author
 * @Andre
 */
public class UniformidadeListView extends Stage {

    private Scene subScene;
    private UniformidadeTable table;
    private Ensaio ensaio;
    private ComboBox cbEspacamento;
    private Label metros;
    private SimpleStringProperty m;
    private Label descricao;
    private SimpleStringProperty desc;
    private Label pressao;
    private SimpleStringProperty pre;
    private Label data;
    private SimpleStringProperty dt;
    private Label inicio;
    private SimpleStringProperty ini;
    private Label velVento;
    private SimpleStringProperty vv;
    private Label dirVento;
    private SimpleStringProperty dv;
    private Label espacamento;
    private SimpleStringProperty esp;
   

    public UniformidadeListView() {


        setTitle("Uniformidade");
        setResizable(true);
        inicializaComponentes(ensaio);
//        public void add(Node child, int columnIndex, int rowIndex, int colspan, int rowspan)
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(6);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Selecione o Espaçamento para visualizar a sobreposição: "), 0, 0, 2, 1);
        grid.add(cbEspacamento, 2, 0,2,1);
        grid.add(new Label("Ensaio:"), 0, 1);
        grid.add(descricao, 1, 1);
        grid.add(new Label("Pressão:"), 2, 1);
        grid.add(pressao, 3, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(data, 1, 2);
        grid.add(new Label("Vel. Vento(m/s):"), 2, 2);
        grid.add(velVento, 3, 2);
        grid.add(new Label("Início:"), 0, 3);
        grid.add(inicio, 1, 3);
        grid.add(new Label("Direção vento:"), 2, 3);
        grid.add(dirVento, 3, 3);
        grid.add(new Label("Espaçamento (m): "), 0, 4);
        grid.add(espacamento, 1, 4);
        grid.add(new Label("Tamanho grid: "), 2, 4);
        grid.add(metros, 3, 4);
//        grid.add(new Label("CUC: "), 0,5);
        grid.add(table, 0, 5, 4, 1);


        Group panel = new Group();
        panel.getChildren().add(grid);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add("style.css");
        this.setScene(scene);

    }

    private void inicializaComponentes(Ensaio e) {
        table = new UniformidadeTable();
        cbEspacamento = new ComboBox(getEspacamentos());
        cbEspacamento.setId("comboEspacamento");
        descricao = new Label("--");
        pressao= new Label("--");
        data= new Label("--");
        velVento = new Label("--");
        dirVento = new Label("--");
        espacamento = new Label("--");
        inicio = new Label("--");
        metros = new Label("--");
        
      
    }

    public void reRenderTable() {

        table.reRenderTable(ensaio, (cbEspacamento.getValue() == null ? null : cbEspacamento.getValue().toString()));


    }

    public void renderTable() {
        table.renderTable();
    }

    public UniformidadeTable getTable() {
        return table;
    }

    public void refreshTable(List<Ensaio> ensaios) {
        //table.reload(ensaios);        
    }

    public void setEnsaio(Ensaio e) {
        this.ensaio = e;


        float mQuad = (e.getEspacamentoPluviometro() * e.getGridAltura()) * (e.getEspacamentoPluviometro() * e.getGridLargura());
        
        desc = new SimpleStringProperty(e.getDescricao());
        pre = new SimpleStringProperty(e.getPressao());
        dt = new SimpleStringProperty(DateUtil.formatDate(e.getData()));
        vv = new SimpleStringProperty(e.getVelocidadeVento() + "");
        dv = new SimpleStringProperty(e.getDirecaoVento());
        esp = new SimpleStringProperty(e.getEspacamentoPluviometro() + "");
        ini = new SimpleStringProperty(e.getInicio());
        m = new SimpleStringProperty(mQuad + " m2");
        


        descricao.textProperty().bind(desc);
        pressao.textProperty().bind(pre);
        data.textProperty().bind(dt);
        velVento.textProperty().bind(vv);
        dirVento.textProperty().bind(dv);
        espacamento.textProperty().bind(esp);
        inicio.textProperty().bind(ini);
        metros.textProperty().bind(m);
        

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
