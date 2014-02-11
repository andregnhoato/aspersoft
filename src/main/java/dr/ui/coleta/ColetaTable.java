package dr.ui.coleta;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.dao.ConfiguracaoDAO;
import dr.dao.ConfiguracaoDAOImpl;
import dr.model.Coleta;
import dr.model.Configuracao;
import dr.ui.ensaio.*;
import dr.model.Ensaio;
import dr.ui.Dialog;
import dr.ui.table.cell.NumericEditableTableCell;
import dr.util.UniformidadesImpl;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    final ConfiguracaoDAO daoConfig;
    Label coleta;

    public ColetaTable() {
        table = new ColetaTableView();
        dao = new ColetaDAOImpl(pe.getPersistenceContext());
        daoConfig = new ConfiguracaoDAOImpl(pe.getPersistenceContext());
        this.getChildren().addAll(table);
        this.setPadding(new Insets(10, 10, 10, 10));//css


    }
   

    public void reRenderTable(Ensaio ensaio) {
        try {
            ArrayList<Coleta> clts;
            clts = (ArrayList<Coleta>) dao.findColetasByEnsaio(ensaio);
            if (clts.size() <= 0) {
                insertEmptyColetas(clts, ensaio);
            }
        } catch (Exception ex) {
            Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.e = ensaio;
        this.getChildren().remove(table);
        this.getChildren().remove(coleta);
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

            for (int i = 0; i < (ensaio.getGridLargura() / ensaio.getEspacamentoPluviometro()); i++) {

                final int j = i;

                TableColumn col = new TableColumn(alphabet + "");
                col.setCellFactory(numericFactory);


                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, Float>, ObservableValue<Float>>() {
                    @Override
                    public ObservableValue call(CellDataFeatures<ObservableList, Float> param) {

                        return new SimpleFloatProperty((Float) param.getValue().get(j));

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
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            Dialog.showError(e.getLocalizedMessage(), e.getMessage());
                        }
                    }
                });

                table.getColumns().add(col);
                alphabet++;
            }
            table.autosize();
            table.setItems(getColetasfromDatabase());

        }
        
        coleta = new Label("Visualização das coletas referente a duração de 60 minutos");
        coleta.setTextFill(Color.web("#0000FF"));
        if(e.getColetaHora()){
            this.setPadding(new Insets(10, 10, 10, 10));
            this.getChildren().addAll(coleta,table);
        }else
            this.getChildren().addAll(table);
    }

    /*método responsável por inserir todas as coletas vazias, é executado uma unica vez para cada ensaio*/
    public void insertEmptyColetas(ArrayList<Coleta> clts, final Ensaio ensaio) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dao != null) {
                        try {
                            for (int linha = 0; linha < (ensaio.getGridAltura() / ensaio.getEspacamentoPluviometro()); linha++) {
                                for (int coluna = 0; coluna < (ensaio.getGridLargura() / ensaio.getEspacamentoPluviometro()); coluna++) {
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
                            System.err.println(e.getMessage());
                        }
                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
    }

    private ObservableList getColetasfromDatabase() {
        coletas = FXCollections.observableArrayList();
        float divisao = 0;
        if(e.getColetaHora())
            if(e.getDuracao()> 60)
                divisao = e.getDuracao() / 60;
            else 
                divisao = 60 / e.getDuracao();

        try {
            List<Coleta> clts = dao.findColetasByEnsaio(e);
            Configuracao c = daoConfig.findAll().get(0);
            if (clts.size() > 0) {
                int contador = 0;
                for (int linha = 0; linha < (e.getGridAltura() / e.getEspacamentoPluviometro()); linha++) {
                    ObservableList<Float> row = FXCollections.observableArrayList();
                    for (int coluna = 0; coluna < (e.getGridLargura() / e.getEspacamentoPluviometro()); coluna++) {
                        if(e.getColetaHora()){
                            if(clts.get(contador).getValor()> 0){
                                Float a = (clts.get(contador).getValor() / divisao);
                                row.add(UniformidadesImpl.round(a, c.getCasasDecimaisColeta()));
                            }
                            
                        }else
                            row.add(UniformidadesImpl.round(clts.get(contador).getValor(), c.getCasasDecimaisColeta()));
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
