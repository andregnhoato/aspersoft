package dr.ui.coleta.uniformidade;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.model.Coleta;
import dr.ui.ensaio.*;
import dr.model.Ensaio;
import dr.test.PersistenceTest;
import static dr.test.PersistenceTest.cdao;
import static dr.test.PersistenceTest.edao;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

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
    private ObservableList<ObservableList> sobreposicoes;
    private Ensaio e = null;
    PersistenceController pe = new PersistenceController();
    final ColetaDAO dao;

    public UniformidadeTable() {
        table = new UniformidadeTableView();
        dao = new ColetaDAOImpl(pe.getPersistenceContext());
        this.getChildren().addAll(table);
        this.setPadding(new Insets(10, 10, 10, 10));//css


    }

    public void reRenderTable(Ensaio ensaio) {
        ArrayList<Coleta> clts = null;
        try {
            clts = (ArrayList<Coleta>) dao.findColetasByEnsaio(ensaio);
        } catch (Exception ex) {
            Logger.getLogger(UniformidadeTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.e = ensaio;
        this.getChildren().remove(table);
        table = new UniformidadeTableView();
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);

        //chamar método que calcula sobreposição
//        this.calculaSobreposicao(12,12, clts);

        if (ensaio != null && ensaio.getGridLargura() != null) {
            char alphabet = 'A';
            
            for (int i = 0; i < (e.getGridAltura()/2); i++) {
                TableColumn col = new TableColumn(alphabet + "");
                col.setSortable(false);
                col.setPrefWidth(40);
                table.getColumns().add(col);
                alphabet++;
            }
            table.autosize();
            table.setItems(calculaSobreposicao(12, 12, clts));

        }
        this.getChildren().addAll(table);
    }

    private ObservableList calculaSobreposicao(int espacamentoX, int espacamentoY, List<Coleta> coletas) {
        sobreposicoes = FXCollections.observableArrayList();
        try {
            List<Coleta> clts = dao.findColetasByEnsaio(e);
            DecimalFormat df = new DecimalFormat("0.00");
            float sobreposicaoX;
            float sobreposicaoY;
            boolean perfeito = false;


            //coletas
            //List<Coleta> coletas = clts;
            if (coletas.size() < (e.getGridAltura() * e.getGridLargura())) {
                throw new Exception("Invalid length array");
            }
            //1 identificando a localização do aspersor ** verificar com Paulo
            int posAspersorX = e.getGridAltura() / 2;
            int posAspersorY = e.getGridLargura() / 2;

            //2 identificando se a sobreposição compreende 1/4 do grid
            sobreposicaoX = espacamentoX / e.getEspacamentoPluviometro();
            sobreposicaoY = espacamentoY / e.getEspacamentoPluviometro();

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
                for (int j = 0; j < (sobreposicaoX * sobreposicaoY); j++) {
                    Float soma;
                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
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
}
