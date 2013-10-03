package dr.ui.coleta;

import dr.model.Coleta;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

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
    

//    public Coleta getSelectedItem() {
//       ColetaItem item = getSelectionModel().getSelectedItem();
//        if (item != null) {
//            return item.toColeta();
//        }
//        return null;
//    }

    /**
     * Item da tabela, faz o binding da <code>coleta</code> com <code>TableView</code>.
    */
    public static class ColetaItem {

        private final ListProperty<SimpleFloatProperty> valores;

        public ColetaItem() {
            this.valores = new SimpleListProperty<>();
        }
        
        public void addValor(Float valor){
            this.valores.add(new SimpleFloatProperty(valor));
        }

//        public Float getValor() {
//            return valor.get();
//        }
        
        public void setValor(int pos,Float valor){
            this.valores.get(pos).set(valor);
        }
        
        public Coleta toColeta(int pos){
            Coleta c = new Coleta();
            c.setValor(this.valores.get(pos).get());

            return c;
        }
    }
}

