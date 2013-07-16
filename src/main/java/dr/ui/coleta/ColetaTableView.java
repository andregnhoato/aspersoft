package dr.ui.coleta;

import dr.ui.ensaio.*;
import dr.model.Coleta;
import dr.model.Ensaio;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * <code>TableView</code> adaptada para apresentar objetos <code>Coleta</code>.
 * @author @Andre
 */
public class ColetaTableView extends TableView {

    private ObservableList coletas;
    private Coleta coleta;

    public ColetaTableView() {
        
    }
    
    public void reRenderTable(Ensaio ensaio ){
        //IMPLEMENTAR A REDERIZAÇÃO DA TABELA DE ACORDO COM O TAMANHO PASSADO NO ENSAIO
        
        coletas= FXCollections.observableArrayList();
        if(ensaio !=null && ensaio.getGridAltura() !=null ){
            for (int i = 0; i < ensaio.getGridLargura(); i++) {
                final int j = i;
                TableColumn  col = new TableColumn(i+"");
                col.setMinWidth(10);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
               
               getColumns().addAll(col);
            }

            for (int i = 0; i < ensaio.getGridAltura(); i++) {
                    //Iterate Row
                    ObservableList<Coleta> row = FXCollections.observableArrayList();
                    for(int x=1 ; x<=ensaio.getGridAltura(); x++){
                        //Iterate Column
                        Coleta a = new Coleta();
                        a.setX(x);
                        a.setY(i);
                        a.setValor(Float.NaN);
                        row.add(a);
                    }
                    System.out.println("Row [1] added "+row );
                    coletas.add(row);
                }
                //FINALLY ADDED TO TableView
                setItems(coletas);

            }
    }
       
/*
        ensaios = FXCollections.observableArrayList();
        setItems(ensaios);
        
        getColumns().addAll(idCol, descricaoCol, dataCol, pressaoCol, bocalCol, quebraJatoCol, duracaoCol, velocidadeVentoCol, direcaoVentoCol/*, alturaCol, larguraCol);
*/
    }

    /*public void reload(final List<Coleta> ensaios) {
        this.ensaios.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (Coleta e: ensaios) {
                    ColetaItem item = new ColetaItem(e);
             //       ColetaTableView.this.ensaios.add(item);
                }
            }
            
        });
    }*/

    /*public Coleta getSelectedItem() {
       /*ColetaItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toColeta();
        }
        return null;
    }*/

    /**
     * Item da tabela, faz o binding da <code>Mercadoria</code> com <code>TableView</code>.
     
    public static class ColetaItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty valor;

        private ColetaItem(Coleta c) {
            this.id = new SimpleStringProperty(c.getId() + "");
            this.valor = new SimpleStringProperty(c.getValor()+"");
        }

        public String getId() {
            return id.get();
        }
        
        public String getValor() {
            return valor.get();
        }
        
        public Coleta toColeta(){
            Coleta e = new Coleta();
            e.setId(Integer.parseInt(this.id.get()));
           /* e.setDescricao(this.descricao.get());
            e.setPressao(this.pressao.get());
            e.setDuracao(this.duracao.get());
            e.setGridAltura(Integer.parseInt(this.gridAltura.get()));
            e.setGridLargura(Integer.parseInt(this.gridLargura.get()));
            e.setQuebraJato(this.quebraJato.get());
            e.setBocal(this.bocal.get());
            e.setInicio(this.inicio.get());
            e.setVersion(Integer.parseInt(this.version.get()));
            e.setDirecaoVento(this.direcaoVento.get());
            e.setVelocidadeVento(Float.parseFloat(this.getVelocidadeVento()));
           
            return e;
        }
    }*/

