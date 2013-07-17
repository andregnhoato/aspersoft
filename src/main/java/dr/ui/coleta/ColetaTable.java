package dr.ui.coleta;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.table.NumberColumn;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableController;
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
import org.apache.poi.ss.formula.functions.T;

/**
 * Reune os componentes para formar uma tabela de <code>Ensaio</code>.
 * 
 * @see ui.EnsaioTableView
 * 
 * @author @Andre
 */
public class ColetaTable extends VBox {
    
    private TableView<Coleta> table;
    private ObservableList coletas;
    //private TableControl table;
    //private ColetaController coletaController = new ColetaController();
    
    public ColetaTable(){
        table = new TableView<>();
        
        //table = new TableControl(Coleta.class);
        //ColetaController cc = new ColetaController();
        //table.setController(coletaController);
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
        table = new TableView<>();
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
                    new PropertyValueFactory<Coleta, String>("teste"));
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setOnEditCommit(
                    new EventHandler<CellEditEvent<Coleta, String>>() {

                        @Override
                        public void handle(CellEditEvent<Coleta, String> t) {
                            ((Coleta) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setTeste(t.getNewValue());
                            
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
    
    /*public Ensaio getEnsaioSelected() {
        return table.getSelectedItem();
    }*/
    /*
    public class ColetaController extends TableController<Object>{
        
        
        this.getChildren().remove(table);
        table = new TableControl(Coleta.class);
        coletas = FXCollections.observableArrayList();
        Coleta coleta;
        if(ensaio !=null && ensaio.getGridLargura()!=null){
            char alphabet = 'A';
            for(int i = 0; i < ensaio.getGridLargura(); i++){
                NumberColumn<Coleta, Float> col = new NumberColumn<>(""+alphabet, Float.class);
                //TableColumn col = new TableColumn(""+alphabet);
                table.addColumn(col);
                alphabet++;
               
            }
        }
        this.getChildren().addAll(table);

        @Override
        public TableData loadData(int i, List<TableCriteria> list, List<String> list1, List<TableColumn.SortType> list2, int i1) {
            
            
            
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        /**
         *
         * @param list
         * @return
        
        @Override
        public List<T> insert(List<T> list) {
            
            return null;
        }
    }     */   
    
}
