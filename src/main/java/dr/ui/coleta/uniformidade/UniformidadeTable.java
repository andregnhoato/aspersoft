package dr.ui.coleta.uniformidade;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.model.Coleta;
import dr.ui.ensaio.*;
import dr.model.Ensaio;
import dr.util.IUniformidades;
import dr.util.UniformidadesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

/**
 * Reune os componentes para formar uma tabela de
 * <code>Ensaio</code>.
 *
 * @see ui.EnsaioTableView
 *
 * @author
 * @Andre
 */
public class UniformidadeTable extends VBox {

    private UniformidadeTableView table;
    GridPane grid;
    private ObservableList<ObservableList> sobreposicoes;
    private Ensaio ensaio = null;
    PersistenceController pe = new PersistenceController();
    final ColetaDAO dao;
    private Label cuc;
    private SimpleStringProperty Cuc;
    Float CUC;
    private Label cud;
    private SimpleStringProperty Cud;
    Float CUD;
    private Label cue;
    private SimpleStringProperty Cue;
    Float CUE;
    float gridAltura;
    float gridLargura;
    IUniformidades uniformidade = new UniformidadesImpl();

    public UniformidadeTable() {
        table = new UniformidadeTableView();
        dao = new ColetaDAOImpl(pe.getPersistenceContext());
        grid = new GridPane();
        grid.setVgap(6);
        grid.setHgap(2);
        grid.setPadding(new Insets(5, 5, 5, 5));
        CUC = 0F;
        cuc = new Label("--");
//        cuc.setTextFill(Color.web("#0076a3"));
        cud = new Label("--");
//        cud.setTextFill(Color.web("#0076a3"));
        cue = new Label("--");
//        cue.setTextFill(Color.web("#0076a3"));
        grid.add(new Label("CUC: "), 0, 0);
        grid.add(cuc, 1, 0);
        grid.add(new Label("CUD: "), 2, 0);
        grid.add(cud, 3, 0);
        grid.add(new Label("CUE: "), 4, 0);
        grid.add(cue, 5, 0);

        grid.add(table, 0, 1, 6, 1);
        this.getChildren().addAll(grid);
        this.setPadding(new Insets(10, 10, 10, 10));//css


    }

    public void renderTable() {
        table = new UniformidadeTableView();

        int contador = 0;
        for (int linha = 0; linha < (ensaio.getGridAltura() / ensaio.getEspacamentoPluviometro()) / 2; linha++) {
            ObservableList<Float> row = FXCollections.observableArrayList();
            for (int coluna = 0; coluna < (ensaio.getGridAltura() / ensaio.getEspacamentoPluviometro()) / 2; coluna++) {
                row.add(0F);
                contador++;
            }
            sobreposicoes.add(row);

        }

        grid = new GridPane();
        grid.setVgap(1);
        grid.setHgap(2);
        grid.setPadding(new Insets(5, 5, 5, 5));

        table.setItems(sobreposicoes);
        grid.add(new Label("CUC: "), 0, 0);
        grid.add(cuc, 1, 0);
        grid.add(table, 0, 1, 6, 1);
        grid.add(new Label("CUD: "), 2, 0);
        grid.add(new Label("--"), 3, 0);
        grid.add(new Label("CUE: "), 4, 0);
        grid.add(new Label("-- "), 5, 0);
        this.getChildren().addAll(grid);

    }

    public void reRenderTable(Ensaio ensaio, String espacamento) {
        if (espacamento != null) {

            ArrayList<Coleta> clts = null;
            try {
                clts = (ArrayList<Coleta>) dao.findColetasByEnsaio(ensaio);
            } catch (Exception ex) {
                Logger.getLogger(UniformidadeTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            int espacamentoX = Integer.parseInt(espacamento.substring(3, 5));
            int espacamentoY = Integer.parseInt(espacamento.substring(0, 2));


            this.ensaio = ensaio;
            this.getChildren().remove(grid);
            table = new UniformidadeTableView();
            table.getSelectionModel().setCellSelectionEnabled(true);

            //chamar método que calcula sobreposição
            int contador = 0;
            if (ensaio != null && ensaio.getGridLargura() != null) {
                char alphabet = 'A';

                for (int i = 0; i < (espacamentoY / ensaio.getEspacamentoPluviometro()); i++) {
                    TableColumn col = new TableColumn(alphabet + "");
                    col.setSortable(false);
                    col.setPrefWidth(45);
                    final int j = i;
                    contador++;

                    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, Float>, ObservableValue<Float>>() {
                        @Override
                        public ObservableValue call(CellDataFeatures<ObservableList, Float> param) {

                            return new SimpleFloatProperty((Float) param.getValue().get(j));

                        }
                    });


                    table.getColumns().add(col);
                    alphabet++;
                }
                table.setMaxSize(contador * 45.5, contador * 28);
                table.setItems(calculaSobreposicao(espacamentoX, espacamentoY, clts));

            }
            calculaCoeficientes(espacamentoX,espacamentoY);
            grid = new GridPane();
            grid.setVgap(1);
            grid.setHgap(2);
//            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(new Label("CUC: "), 0, 0);
            grid.add(cuc, 1, 0);
            grid.add(new Label("CUD:"), 2, 0);
            grid.add(cud, 3, 0);
            grid.add(new Label("CUE:"), 4, 0);
            grid.add(cue, 5, 0);

            grid.add(table, 0, 1, 6, 1);
            this.getChildren().addAll(grid);

        }
    }

    public Ensaio getEnsaio() {
        return this.ensaio;

    }
    /**
     * Método responável por calcular os coeficientes CUC, CUD e CUE;
     * @param espacamentoX
     * @param espacamentoY 
     */
    private void calculaCoeficientes(int espacamentoX, int espacamentoY) {
        
        List<Float> listaSobreposicoes = listaSobreposicao(sobreposicoes, espacamentoX / ensaio.getEspacamentoPluviometro(), espacamentoY / ensaio.getEspacamentoPluviometro());
        CUC = uniformidade.calculoCuc(listaSobreposicoes);
        CUD = uniformidade.calculoCud(listaSobreposicoes);
        CUE = uniformidade.calculoCue(listaSobreposicoes);


        Cuc = new SimpleStringProperty(CUC + "");
        cuc.textProperty().bind(Cuc);
        cuc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        Cud = new SimpleStringProperty(CUD + "");
        cud.textProperty().bind(Cud);
        cud.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Cue = new SimpleStringProperty(CUE + "");
        cue.textProperty().bind(Cue);
        cue.setFont(Font.font("Verdana", FontWeight.BOLD, 14));


    }

    /**
     *
     * @param espacamentoX altura ou espaçamento entre aspersores
     * @param espacamentoY largura das laterais
     * @param coletas listagem de todas as coletas do ensaio
     * @return
     */
    private ObservableList calculaSobreposicao(int espacamentoX, int espacamentoY, List<Coleta> coletas) {
        this.sobreposicoes = uniformidade.calculaSobreposicoes(espacamentoX, espacamentoY, coletas, ensaio);
        return sobreposicoes;
    }
  
    /**
     *
     * @param sp lista de sobreposiçoes
     * @param sx sobreposição eixo x laterais
     * @param sy sobreposição eixo y aspersores
     */
    private List<Float> listaSobreposicao(ObservableList<ObservableList> sp, float sx, float sy) {
        List<Float> order = new ArrayList();
        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                order.add((float) sp.get(i).get(j));
            }
        }
        return order;
    }

}
