package dr.ui.coleta;

import dr.model.Coleta;
import dr.ui.ensaio.*;
import dr.model.Ensaio;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

/**
 * Reune os componentes para formar uma tabela de <code>Ensaio</code>.
 * 
 * @see ui.EnsaioTableView
 * 
 * @author @Andre
 */
public class ColetaTable extends VBox {
    
    private ColetaTableView table;
    private ObservableList coletas;
    
    public ColetaTable(){
        table = new ColetaTableView();
        
        this.getChildren().addAll(table);
        this.setPadding(new Insets(10, 10, 10, 10));//css
    }
    
    /*public void reload(List<Ensaio> ensaios) {
        table.reload(ensaios);
    }*/

    //public void setMouseEvent(EventHandler<MouseEvent> event) {
    //    table.setOnMouseClicked(event);
    //}
    
    public void reRenderTable(Ensaio ensaio){
        List<TableColumn> columns = new ArrayList<>();
        this.getChildren().remove(table);
        table = new ColetaTableView();
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        coletas = FXCollections.observableArrayList();
        Coleta coleta;
        if(ensaio !=null && ensaio.getGridLargura()!=null){
            char alphabet = 'A';
            for(int i = 0; i < ensaio.getGridLargura(); i++){

                TableColumn col = new TableColumn(""+alphabet);
                col.setMinWidth(15);
                col.setCellValueFactory(
                    new PropertyValueFactory<ColetaTableView.ColetaItem, String>("valor"));
               
                /*verificar melhor implementação do setcell factory*/
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                
                
                col.setOnEditCommit(
                    new EventHandler<CellEditEvent<ColetaTableView.ColetaItem, String>>() {

                        @Override
                        public void handle(CellEditEvent<ColetaTableView.ColetaItem, String> t) {
                            ((ColetaTableView.ColetaItem) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).toColeta().setValor(Float.parseFloat(t.getNewValue()));
                            
                        }
                    }
                );
                
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.getColumns().add(col);

                //for (int j = 0; j < ensaio.getGridAltura(); j++) {;;
                    coleta = new Coleta();
                    coleta.setLinha(i);
                    coleta.setColuna(i);
                    coleta.setValor(0F);
                    coleta.setTeste(i+"");
                    coletas.add(coleta);
                //}
                alphabet++;
            }
            table.setItems(coletas);
        }
        this.getChildren().addAll(table);
    }
    
    
    
}
