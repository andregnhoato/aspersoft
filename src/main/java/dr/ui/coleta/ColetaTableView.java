package dr.ui.coleta;

import dr.ui.ensaio.*;
import dr.model.Coleta;
import dr.model.Ensaio;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
public class ColetaTableView extends TableView<ColetaTableView.ColetaItem> {

    private ObservableList coletas;
    private Coleta coleta;

    public ColetaTableView() {
        
    }
    
    public void reload(final List<Coleta> coletas) {
        this.coletas.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
             //   for (Coleta e: ensaios) {
             //       ColetaItem item = new ColetaItem(e);
             //       ColetaTableView.this.ensaios.add(item);
             //   }
            }
            
        });
    }
    

    public Coleta getSelectedItem() {
       ColetaItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toColeta();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding da <code>coleta</code> com <code>TableView</code>.
    */
    public static class ColetaItem {

        private final SimpleFloatProperty valor;

        public ColetaItem(Float valor) {
            this.valor = new SimpleFloatProperty(valor);
        }

        public Float getValor() {
            return valor.get();
        }
        
        public void setValor(Float valor){
            this.valor.set(valor);
        }
        
        public Coleta toColeta(){
            Coleta c = new Coleta();
            c.setValor(this.valor.get());

            return c;
        }
    }
}

