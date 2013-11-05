package dr.ui.analise;

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
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
public class SobreposicaoTable extends VBox {

    private SobreposicaoTableView table;
    GridPane grid;
    private ObservableList<ObservableList> sobreposicoes;
    private Ensaio ensaio = null;
    PersistenceController pe = new PersistenceController();
    final ColetaDAO dao;   
    private Float CUC;    
    private Float CUD;  
    private Float CUE;
    private Float CV;  
    private Float MMQ;   
    private Float DP;
    float gridAltura;
    float gridLargura;
    IUniformidades uniformidade;
    

    public SobreposicaoTable() {
        table = new SobreposicaoTableView();
        dao = new ColetaDAOImpl(pe.getPersistenceContext());
        CUC = 0F;
        this.getChildren().addAll(table);
    }

    public void reRenderTable(Ensaio ensaio, String espacamento) {
        if (espacamento != null) {
            uniformidade = new UniformidadesImpl(ensaio);
            ArrayList<Coleta> clts = null;
            try {
                clts = (ArrayList<Coleta>) dao.findColetasByEnsaio(ensaio);
            } catch (Exception ex) {
                Logger.getLogger(SobreposicaoTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            int espacamentoX = Integer.parseInt(espacamento.substring(3, 5));
            int espacamentoY = Integer.parseInt(espacamento.substring(0, 2));


            this.ensaio = ensaio;
            this.getChildren().remove(table);
            table = new SobreposicaoTableView();
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

                if ((espacamentoX / ensaio.getEspacamentoPluviometro()) == espacamentoY / ensaio.getEspacamentoPluviometro()) {
                    table.setMaxSize(contador * 45.5, contador * 28);
                } else {
                    table.setMaxSize(contador * 45.5, 35 * (espacamentoX / ensaio.getEspacamentoPluviometro()));
                }

                table.setItems(calculaSobreposicao(espacamentoX, espacamentoY, clts));

            }
            calculaCoeficientes(espacamentoX, espacamentoY);

            this.getChildren().addAll(table);


        }
    }

    public Ensaio getEnsaio() {
        return this.ensaio;

    }
    
    public ObservableList getSobreposicoes(){
        
        return this.sobreposicoes;
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

        DP = uniformidade.getDesvioPadrao();
        MMQ = uniformidade.getMediaMenorQuartil();
        CV = uniformidade.getCoeficienteVariacao();
}

    /**
     *
     * @param espacamentoX altura ou espaçamento entre aspersores
     * @param espacamentoY largura das laterais
     * @param coletas listagem de todas as coletas do ensaio
     * @return
     */
    private ObservableList calculaSobreposicao(int espacamentoX, int espacamentoY, List<Coleta> coletas) {
        this.sobreposicoes = uniformidade.calculaSobreposicoes(espacamentoX, espacamentoY, coletas);
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

    public Float getCUC() {
        return (CUC!=null?CUC:0);
    }

    public Float getCUD() {
        return (CUD!=null?CUD:0);
    }

    public Float getCUE() {
        return (CUE!=null?CUE:0);
    }

    public Float getMMQ() {
        return (MMQ!=null?MMQ:0);
    }

    public Float getDP() {
        return (DP!=null?DP:0);
    }

    public Float getCV() {
        return (CV!=null?CV:0);
    }
    
    
}
