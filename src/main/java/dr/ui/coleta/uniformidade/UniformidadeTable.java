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
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
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
    private Label aspersor;
    private Label laterais;

    public UniformidadeTable() {
        table = new UniformidadeTableView();
        dao = new ColetaDAOImpl(pe.getPersistenceContext());
        grid = new GridPane();
        grid.setVgap(0);
        grid.setHgap(2);
//        grid.setPadding(new Insets(5, 5, 5, 5));
        CUC = 0F;
        cuc = new Label("--");
        cud = new Label("--");
        cue = new Label("--");
        grid.add(new Label("CUC: "), 1, 0);
        grid.add(cuc, 2, 0);
        grid.add(new Label("CUD:"), 3, 0);
        grid.add(cud, 4, 0);
        grid.add(new Label("CUE:"), 5, 0);
        grid.add(cue, 6, 0);

        aspersor = new Label("Espaçamento entre aspersores");
        aspersor.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                aspersor.setScaleX(1.5);
                aspersor.setScaleY(1.5);
            }
        });

        aspersor.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                aspersor.setScaleX(1);
                aspersor.setScaleY(1);
            }
        });

        laterais = new Label("Laterais");
        laterais.setRotate(270);
        laterais.setTranslateY(50);
        laterais.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                laterais.setScaleX(1.5);
                laterais.setScaleY(1.5);
            }
        });

        laterais.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                laterais.setScaleX(1);
                laterais.setScaleY(1);
            }
        });

        laterais.setAlignment(Pos.BASELINE_LEFT);
        grid.add(laterais, 0, 0, 1, 2);
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.add(table, 1, 1, 6, 1);
        aspersor.setAlignment(Pos.TOP_CENTER);
        grid.add(aspersor, 2, 2, 5, 1);
        this.getChildren().addAll(grid);
//        this.setPadding(new Insets(10, 10, 10, 10));//css


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
//                    col.setPrefWidth(60);
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

                if ((espacamentoX / ensaio.getEspacamentoPluviometro()) == espacamentoY / ensaio.getEspacamentoPluviometro()) {
                    table.setMaxSize(contador * 45.5, contador * 28);
                } else {
                    table.setMaxSize(contador * 45.5, 35 * (espacamentoX / ensaio.getEspacamentoPluviometro()));
                }

                table.setItems(calculaSobreposicao(espacamentoX, espacamentoY, clts));

            }
            calculaCoeficientes(espacamentoX, espacamentoY);
            grid = new GridPane();
            grid.setVgap(5);
            grid.setHgap(5);
            grid.add(new Label("CUC: "), 1, 0);
            grid.add(cuc, 2, 0);
            grid.add(new Label("CUD:"), 3, 0);
            grid.add(cud, 4, 0);
            grid.add(new Label("CUE:"), 5, 0);
            grid.add(cue, 6, 0);
            
            aspersor = new Label("Espaçamento entre aspersores "+espacamentoY+" metros");
            aspersor.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    aspersor.setScaleX(1.5);
                    aspersor.setScaleY(1.5);
                }
            });

            aspersor.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    aspersor.setScaleX(1);
                    aspersor.setScaleY(1);
                }
            });

            laterais = new Label("Laterais "+espacamentoX+" metros");
            laterais.setRotate(270);
            laterais.setTranslateY(50);
            laterais.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    laterais.setScaleX(1.5);
                    laterais.setScaleY(1.5);
                }
            });

            laterais.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    laterais.setScaleX(1);
                    laterais.setScaleY(1);
                }
            });
            laterais.setAlignment(Pos.BASELINE_LEFT);
            grid.add(laterais, 0, 0, 1, 2);
            grid.setPadding(new Insets(0, 0, 0, 0));
            grid.add(table, 1, 1, 6, 1);
            aspersor.setAlignment(Pos.TOP_CENTER);
            grid.add(aspersor, 2, 2, 5, 1);

//            grid.add(table, 0, 1, 6, 1);
//            this.setPadding();
            this.getChildren().addAll(grid);


        }
    }

    public Ensaio getEnsaio() {
        return this.ensaio;

    }

    /**
     * Método responável por calcular os coeficientes CUC, CUD e CUE;
     *
     * @param espacamentoX
     * @param espacamentoY
     */
    private void calculaCoeficientes(int espacamentoX, int espacamentoY) {

        List<Float> listaSobreposicoes = listaSobreposicao(sobreposicoes, espacamentoX / ensaio.getEspacamentoPluviometro(), espacamentoY / ensaio.getEspacamentoPluviometro());
        CUC = uniformidade.calculoCuc(listaSobreposicoes);
        CUD = uniformidade.calculoCud(listaSobreposicoes);
        CUE = uniformidade.calculoCue(listaSobreposicoes);
        
        //        chamar aqui o calculo do perfil de distribuição
        uniformidade.calculaPerfilDistribuicao();

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
