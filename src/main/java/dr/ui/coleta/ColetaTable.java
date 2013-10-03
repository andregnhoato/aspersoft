package dr.ui.coleta;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.model.Coleta;
import dr.ui.ensaio.*;
import dr.model.Ensaio;
import dr.ui.coleta.ColetaTableView.ColetaItem;
import dr.ui.table.cell.NumericEditableTableCell;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ObservableList coletas;
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
            System.out.println("tamanho do array: " + clts.size());
            if (clts.size() <= 0) {
                insertEmptyColetas(clts, ensaio);
            }
            int i = 0;

            for (Coleta c : clts) {
                System.out.println(i + "***** valor da coleta: " + c.getValor());
                i++;
            }

        } catch (Exception ex) {
            Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.e = ensaio;
        List<TableColumn> columns = new ArrayList<>();
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

                TableColumn col = createColumns(numericFactory, "" + alphabet);

                table.getColumns().add(col);
                alphabet++;
            }
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.autosize();
//            coletas = FXCollections.observableArrayList(clts);
            table.setItems(getColetasfromDatabase());
            
//            table.setItems(coletas);

//            table.setItems(coletas);
            //table.setItems(getColetasfromDatabase());
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
                    ColetaItem ci = new ColetaItem();
                    for (int coluna = 0; coluna < e.getGridLargura(); coluna++) {
                        ci.addValor(clts.get(contador).getValor());
                        contador++;
                    }
                    coletas.addAll(ci);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return coletas;
    }

    public void setValue(int row, int col, Float val) {
        System.out.println("Linha: " + row + " Coluna: " + col + "Valor: " + val);
        try {
            final ColetaItem selectedRow = table.getItems().get(row);
            final TableColumn<ColetaItem, ?> selectedColumn = table.getColumns().get(col);
            // Lookup the propery name for this column
            final String propertyName = ((PropertyValueFactory) selectedColumn.getCellValueFactory()).getProperty();

            // Use reflection to get the property
            final Field f = ColetaItem.class.getField(propertyName);
            final Object o = f.get(selectedRow);

            // Modify the value based on the type of property
            if (o instanceof SimpleFloatProperty) {
                ((SimpleFloatProperty) o).setValue(val);
            } else if (o instanceof SimpleIntegerProperty) {
                System.err.print("Nothing to say.... did`nt work!!!! sad");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private TableColumn createColumns(Callback<TableColumn, TableCell> numericFactory, String columnName) {
        //Somente valores númericos.
        TableColumn column = new TableColumn(columnName);
        column.setSortable(false);
        column.setCellValueFactory(new PropertyValueFactory<ColetaTableView.ColetaItem, Float>("valor"));
        column.setCellFactory(numericFactory);

        //Evento disparado após editar a celula
        column.setOnEditCommit(new EventHandler<CellEditEvent<ColetaTableView.ColetaItem, Float>>() {
            @Override
            public void handle(CellEditEvent<ColetaTableView.ColetaItem, Float> t) {
                Float valor = t.getNewValue().floatValue();
                ((ColetaTableView.ColetaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValor(t.getTablePosition().getColumn(), valor);
                // = new Coleta();
                final Coleta c = ((ColetaTableView.ColetaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).toColeta(t.getTablePosition().getColumn());
                c.setEnsaio(e);
                c.setLinha(t.getTablePosition().getRow());
                c.setColuna(t.getTablePosition().getColumn());

                //final ColetaDAO dao = new ColetaDAOImpl(pe.getPersistenceContext());

                try {
                    if (dao != null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    dao.save(c);
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

        return column;
    }
}
