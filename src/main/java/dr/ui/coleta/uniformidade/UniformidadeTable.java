package dr.ui.coleta.uniformidade;

import com.sun.prism.paint.Color;
import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.model.Coleta;
import dr.ui.ensaio.*;
import dr.model.Ensaio;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    ;
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
        for (int linha = 0; linha < (ensaio.getGridAltura()/ensaio.getEspacamentoPluviometro()) / 2; linha++) {
            ObservableList<Float> row = FXCollections.observableArrayList();
            for (int coluna = 0; coluna < (ensaio.getGridAltura()/ensaio.getEspacamentoPluviometro()) / 2; coluna++) {
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
            int espacamentoX = Integer.parseInt(espacamento.substring(0, 2));
            int espacamentoY = Integer.parseInt(espacamento.substring(3, 5));


            this.ensaio = ensaio;
            this.getChildren().remove(grid);
            table = new UniformidadeTableView();
            
//        table.setEditable(true);
            table.getSelectionModel().setCellSelectionEnabled(true);

            //chamar método que calcula sobreposição
//        this.calculaSobreposicao(12,12, clts);
            int contador = 0;
            if (ensaio != null && ensaio.getGridLargura() != null) {
                char alphabet = 'A';

                for (int i = 0; i < ((this.ensaio.getGridAltura() / ensaio.getEspacamentoPluviometro()) / 2); i++) {
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
            grid = new GridPane();
            grid.setVgap(1);
            grid.setHgap(2);
//            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(new Label("CUC: "), 0, 0);
            grid.add(cuc, 1, 0);
            grid.add(new Label("CUD:"), 2, 0);
            grid.add(cud, 3,0);
            grid.add(new Label("CUE:"), 4, 0);
            grid.add(cue, 5,0);
            
            grid.add(table, 0, 1, 6, 1);
            this.getChildren().addAll(grid);
            
        }
    }

    public Ensaio getEnsaio() {
        return this.ensaio;

    }

    /**
     *
     * @param espacamentoX altura ou espaçamento entre aspersores
     * @param espacamentoY largura das laterais
     * @param coletas listagem de todas as coletas do ensaio
     * @return
     */
    private ObservableList calculaSobreposicao(int espacamentoX, int espacamentoY, List<Coleta> coletas) {
        sobreposicoes = FXCollections.observableArrayList();
        try {
//            List<Coleta> clts = dao.findColetasByEnsaio(ensaio); chamada redundante
            DecimalFormat df = new DecimalFormat("0.00");
            float sobreposicaoX;
            float sobreposicaoY;
            boolean perfeito = false;

            //transformando a metragem em unidade
            float gridAltura = ensaio.getGridAltura() / ensaio.getEspacamentoPluviometro();
            float gridLargura = ensaio.getGridLargura()/ ensaio.getEspacamentoPluviometro();

            if (coletas.size() < (gridAltura * gridLargura)) {
                throw new Exception("Invalid length array");
            }
            //1 identificando a localização do aspersor ** verificar com Paulo
            //CORRIGIR EM UMA SITUAÇÃO ONDE 24X30 DEFINI-SE QUE 24 É Y (ALTURA OU ESPAÇAMENTO ENTRE ASPERSORES) E 30 É X (LARGURA LATERAIS)
            float posAspersorX = gridLargura / 2;
            float posAspersorY = gridAltura / 2;

            //2 identificando se a sobreposição compreende 1/4 do grid
            sobreposicaoX = espacamentoX / ensaio.getEspacamentoPluviometro();
            sobreposicaoY = espacamentoY / ensaio.getEspacamentoPluviometro();

            if (posAspersorX == posAspersorY) {
                if (posAspersorX == sobreposicaoX && posAspersorY == sobreposicaoY) {
                    perfeito = true;
                }
            }

            //armazenar os valores sobrepostos em coletas
//            ArrayList<Coleta> sobreposicoes  = new ArrayList();
            //3 se sobreposição é perfeita separar os quadrantes para realizar a soma
            if (perfeito) {
                LinkedList<Float> quad1 = new LinkedList<>();
                LinkedList<Float> quad2 = new LinkedList<>();
                LinkedList<Float> quad3 = new LinkedList<>();
                LinkedList<Float> quad4 = new LinkedList<>();
                LinkedList<Float> sp = new LinkedList<>();


                for (Coleta c : coletas) {
                    if (c.getLinha() < posAspersorX && c.getColuna() < posAspersorX) {
                        quad1.add(c.getValor());
                    } else {
                        if (c.getLinha() < posAspersorX && c.getColuna() >= posAspersorX) {
                            quad2.add(c.getValor());
                        } else {
                            if (c.getLinha() >= posAspersorX && c.getColuna() < posAspersorX) {
                                quad3.add(c.getValor());
                            } else {
                                quad4.add(c.getValor());
                            }
                        }
                    }
                }
                for (int j = 0; j < (sobreposicaoX * sobreposicaoY); j++) {
                    Float soma;
                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
                    soma = (float) (Math.round(soma * 100.0) / 100.0);
                    sp.add(soma);
                }

                System.out.println("Quad1: " + quad1.toString());
                System.out.println("Quad1 size: " + quad1.size());
                System.out.println("Quad2: " + quad2.toString());
                System.out.println("Quad2 size: " + quad2.size());
                System.out.println("Quad3: " + quad3.toString());
                System.out.println("Quad3 size: " + quad3.size());
                System.out.println("Quad4: " + quad4.toString());
                System.out.println("Quad4 size: " + quad4.size());
                System.out.println("VALORES SOBREPOSTOS: " + sp.toString());


                int contador = 0;
                for (int linha = 0; linha < sobreposicaoX; linha++) {
                    ObservableList<Float> row = FXCollections.observableArrayList();
                    for (int coluna = 0; coluna < sobreposicaoY; coluna++) {
                        row.add(sp.get(contador));
                        contador++;
                    }
                    sobreposicoes.add(row);

                }

                calculoCuc(sobreposicaoX, sobreposicaoY);




            } else {//FINALIZAR NÃO ESTÁ COMPLETO
                //4 sobreposição imperfeita separar os arrays adicionando zero nos espaços a serem completados
                LinkedList<Float> quad1 = new LinkedList<>();
                LinkedList<Float> quad2 = new LinkedList<>();
                LinkedList<Float> quad3 = new LinkedList<>();
                LinkedList<Float> quad4 = new LinkedList<>();
                LinkedList<Float> sp = new LinkedList<>();
                Map<Integer, Integer> celulas = new HashMap<>();


                for (Coleta c : coletas) {
                    if (c.getLinha() < posAspersorX && c.getColuna() < posAspersorX) {
                        quad1.add(c.getValor());
                        celulas.put(c.getLinha(), c.getColuna());
                    } else {
                        if (c.getLinha() < posAspersorX && c.getColuna() >= posAspersorX) {
                            quad2.add(c.getValor());
                            celulas.put(c.getLinha(), c.getColuna());
                        } else {
                            if (c.getLinha() >= posAspersorX && c.getColuna() < posAspersorX) {
                                quad3.add(c.getValor());
                                celulas.put(c.getLinha(), c.getColuna());
                            } else {
                                if (c.getLinha() >= posAspersorX && c.getColuna() > posAspersorX) {
                                    quad4.add(c.getValor());
                                    celulas.put(c.getLinha(), c.getColuna());
                                }
                            }
                        }
                    }
                }
                System.out.println(celulas.toString());
                for (int j = 0; j < (sobreposicaoX * sobreposicaoY); j++) {

                    Float soma;
                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
                    soma = (float) (Math.round(soma * 100.0) / 100.0);
                    sp.add(soma);
                }


            }

//            System.out.println("grid altura: " + e.getGridAltura() + " grid largura: "+ e.getGridLargura());
//            System.out.println("espaçamento: " + e.getEspacamentoPluviometro());
//            System.out.println("Quantidade de coletas: " + coletas.size());
        } catch (Exception ex) {
            Logger.getLogger(UniformidadeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sobreposicoes;
    }

    /**
     *
     * @param sp lista de sobreposiçoes
     * @param sx sobreposição eixo x laterais
     * @param sy sobreposição eixo y aspersores
     */
    public List<Float> ordenaSobreposicao(ObservableList<ObservableList> sp, float sx, float sy) {
        List<Float> order = new ArrayList();
        for (int i = 0; i < sy; i++) {

            for (int j = 0; j < sx; j++) {
                order.add((float) sp.get(i).get(j));
            }
        }
        return order;
    }

    /**
     * @param sobreposicaoOrdenada array com as sobreposições
     * @formula cuc possivel visualizar a formula em
     * https://dl.dropboxusercontent.com/u/10055997/cuc.gif
     */
    public void calculoCuc(float sobreposicaoX, float sobreposicaoY) {
        //chama a ordenação
        List<Float> arrayOrdenado = ordenaSobreposicao(sobreposicoes, sobreposicaoX, sobreposicaoY);
        Float media = 0F;
        Float somatoria = 0F;
        Float somatoriaAbsolutos = 0F;
        //somatória da sobreposiçao
        for (int i = 0; i < arrayOrdenado.size(); i++) {
            somatoria += arrayOrdenado.get(i);
        }
        System.out.println("somatória: " + somatoria);
        media = somatoria / arrayOrdenado.size();
        System.out.println("media: " + media);

        List<Float> valoresAbsolutos = new ArrayList();
        for (int i = 0; i < arrayOrdenado.size(); i++) {
            valoresAbsolutos.add(Math.abs(arrayOrdenado.get(i) - media));
        }
        for (int i = 0; i < valoresAbsolutos.size(); i++) {
            somatoriaAbsolutos += valoresAbsolutos.get(i);
        }

        System.out.println("valores absolutos: " + valoresAbsolutos.toString());
        CUC = 1 - (somatoriaAbsolutos / (valoresAbsolutos.size() * media));
        Cuc = new SimpleStringProperty(CUC + "");
        cuc.textProperty().bind(Cuc);
        Cud = new SimpleStringProperty("--");
        cud.textProperty().bind(Cud);
        Cue = new SimpleStringProperty("--");
        cue.textProperty().bind(Cue);
        System.out.println("CUC: " + CUC);


    }
}
