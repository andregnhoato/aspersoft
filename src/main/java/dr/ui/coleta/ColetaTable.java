package dr.ui.coleta;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.model.Coleta;
import dr.ui.ensaio.*;
import dr.model.Ensaio;
import dr.ui.table.cell.NumericEditableTableCell;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
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
public class ColetaTable extends VBox {

    private ColetaTableView table;
    private ObservableList<ObservableList> coletas;
    private Ensaio e = null;
    PersistenceController pe = new PersistenceController();
    final ColetaDAO dao;

    public ColetaTable() {
        table = new ColetaTableView();
        dao = new ColetaDAOImpl(pe.getPersistenceContext());
        this.getChildren().addAll(table);
        this.setPadding(new Insets(10, 10, 10, 10));//css


    }

    public void reRenderTable(Ensaio ensaio) {
        ArrayList<Coleta> clts = null;
        try {
            clts = (ArrayList<Coleta>) dao.findColetasByEnsaio(ensaio);
            if (clts.size() <= 0) {
                insertEmptyColetas(clts, ensaio);
            }
        } catch (Exception ex) {
            Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.e = ensaio;
        this.getChildren().remove(table);
        table = new ColetaTableView();
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);

        if (ensaio != null && ensaio.getGridLargura() != null) {
            char alphabet = 'A';

            //Custon cell factory que irá criar celulas que aceitará somente números
            Callback<TableColumn, TableCell> numericFactory = new Callback<TableColumn, TableCell>() {
                @Override
                public TableCell call(TableColumn p) {
                    return new NumericEditableTableCell();
                }
            };

            for (int i = 0; i < ensaio.getGridLargura(); i++) {

                final int j = i;

                TableColumn col = new TableColumn(alphabet + "");
                col.setCellFactory(numericFactory);
                

                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, Float>, ObservableValue<Float>>() {
                    public ObservableValue call(CellDataFeatures<ObservableList, Float> param) {

                        return new SimpleFloatProperty((Float)param.getValue().get(j));

                    }
                });
                col.setSortable(false);
                col.setPrefWidth(40);
                //Evento disparado após editar a celula atualiza coleta na base
                col.setOnEditCommit(new EventHandler<CellEditEvent<ObservableValue, Float>>() {
                    @Override
                    public void handle(CellEditEvent<ObservableValue, Float> t) {
                        final Float valor = t.getNewValue();
                        final int linha = t.getTablePosition().getRow();
                        final int coluna = t.getTablePosition().getColumn();
                        try {
                            if (dao != null) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Coleta col = dao.findColetaByPosicao(e, linha, coluna);
                                            col.setValor(valor);                                           
                                            dao.update(col);
                                        } catch (Exception ex) {
                                            Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                            } else {
                                throw new Exception("dao está nulo");
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                });
               
                table.getColumns().add(col);
                alphabet++;
            }
//            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.autosize();
            table.setItems(getColetasfromDatabase());

        }
        this.getChildren().addAll(table);
    }

    /*método responsável por inserir todas as coletas vazias, é executado uma unica vez para cada ensaio*/
    public void insertEmptyColetas(ArrayList<Coleta> clts, final Ensaio ensaio) {
        try {
            if (dao != null) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int linha = 0; linha < ensaio.getGridAltura(); linha++) {
                                for (int coluna = 0; coluna < ensaio.getGridLargura(); coluna++) {
                                    Coleta c = new Coleta();
                                    c.setColuna(coluna);
                                    c.setLinha(linha);
                                    c.setEnsaio(ensaio);
                                    c.setValor(0F);
                                    try {
                                        dao.save(c);
                                    } catch (Exception ex) {
                                        Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ObservableList getColetasfromDatabase() {
        coletas = FXCollections.observableArrayList();
        try {
            List<Coleta> clts = dao.findColetasByEnsaio(e);
            if (clts.size() > 0) {
                int contador = 0;
                for (int linha = 0; linha < e.getGridAltura(); linha++) {
                    ObservableList<Float> row = FXCollections.observableArrayList();
                    for (int coluna = 0; coluna < e.getGridLargura(); coluna++) {
                        row.add(clts.get(contador).getValor());
                        contador++;
                    }
                    coletas.add(row);

                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coletas;
    }
}
