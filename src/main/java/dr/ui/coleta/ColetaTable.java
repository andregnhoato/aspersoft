package dr.ui.coleta;

import dr.model.Coleta;
import dr.ui.ensaio.*;
import dr.model.Ensaio;
import dr.ui.table.cell.NumericEditableTableCell;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
            
            //Custon cell factory que irá criar celulas que aceitará somente números
            Callback<TableColumn, TableCell> numericFactory = new Callback<TableColumn, TableCell>() {
                @Override
                public TableCell call(TableColumn p) {
                    return new NumericEditableTableCell();
                }
            };
        
            for(int i = 0; i < ensaio.getGridLargura(); i++){

                TableColumn col = createColumns(numericFactory, ""+alphabet);
                
                
                table.getColumns().add(col);

                /*VERIFICAR REPRESENTAÇÃO DOS OBJETOS NO GRID */
                    ColetaTableView.ColetaItem c = new ColetaTableView.ColetaItem(0F);
                    c.setValor(0F);
                    coletas.add(c);
                alphabet++;
            }
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.autosize();
            table.getColumns().addListener(new ListChangeListener() {
        public boolean suspended;
 
        @Override
        public void onChanged(Change change) {
            change.next();
            if (change.wasReplaced() && !suspended) {
                this.suspended = true;
                //table.getColumns().setAll(coletas);
                this.suspended = false;
            }
        }
    });
            table.setItems(coletas);
        }
        this.getChildren().addAll(table);
    }
    
    private TableColumn createColumns(Callback<TableColumn, TableCell> numericFactory, String columnName) {
        //Somente valores númericos.
        TableColumn column = new TableColumn(columnName);
        //column.setMinWidth(15);
        //column.setMaxWidth(20);
        column.setSortable(false);
        //column.setResizable(false);
        column.setCellValueFactory(new PropertyValueFactory<ColetaTableView.ColetaItem, Float>("valor"));
        column.setCellFactory(numericFactory);
        
        //Evento disparado após editar a celular
        column.setOnEditCommit(new EventHandler<CellEditEvent<ColetaTableView.ColetaItem, Float>>() {
            @Override
            public void handle(CellEditEvent<ColetaTableView.ColetaItem, Float> t) {
                Float valor = t.getNewValue().floatValue();
                ((ColetaTableView.ColetaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValor(valor);
            }
        });
         
        return column;
    }
    
}
