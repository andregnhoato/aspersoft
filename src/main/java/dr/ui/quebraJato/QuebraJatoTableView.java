package dr.ui.quebraJato;

import dr.ui.bocal.*;
import dr.model.QuebraJato;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <code>TableView</code> adaptada para apresentar objetos <code>QuebraJato</code>.
 * @author @Andre
 */
public class QuebraJatoTableView extends TableView<QuebraJatoTableView.QuebraJatoItem> {

    private ObservableList<QuebraJatoItem> quebras;

    public QuebraJatoTableView() {
        TableColumn<QuebraJatoItem, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<QuebraJatoItem, String>("id"));
        
        TableColumn<QuebraJatoItem, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setMinWidth(200);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<QuebraJatoItem, String>("descricao"));

        quebras = FXCollections.observableArrayList();
        setItems(quebras);
        
        getColumns().addAll(idCol, descricaoCol);

    }

    public void reload(final List<QuebraJato> ensaios) {
        this.quebras.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (QuebraJato e: ensaios) {
                    QuebraJatoItem item = new QuebraJatoItem(e);
                    QuebraJatoTableView.this.quebras.add(item);
                }
            }
            
        });
    }

    public QuebraJato getSelectedItem() {
        QuebraJatoItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toQuebraJato();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding do <code>Quebra Jato</code> com <code>TableView</code>.
     */
    public static class QuebraJatoItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty descricao;
        
        private QuebraJatoItem(QuebraJato b) {
            this.id = new SimpleStringProperty(b.getId() + "");
            this.descricao = new SimpleStringProperty(b.getDescricao());
            
        }

        public String getId() {
            return id.get();
        }
        
        public String getDescricao() {
            return descricao.get();
        }
        
        public QuebraJato toQuebraJato(){
            QuebraJato qj = new QuebraJato();
            qj.setId(Integer.parseInt(this.id.get()));
            qj.setDescricao(this.descricao.get());
            
            return qj;
        }

    }
}
