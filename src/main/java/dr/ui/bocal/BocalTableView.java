package dr.ui.bocal;

import dr.model.Bocal;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <code>TableView</code> adaptada para apresentar objetos <code>Bocal</code>.
 * @author @Andre
 */
public class BocalTableView extends TableView<BocalTableView.BocalItem> {

    private ObservableList<BocalItem> bocais;

    public BocalTableView() {
        TableColumn<BocalItem, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<BocalItem, String>("id"));
        
        TableColumn<BocalItem, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setMinWidth(200);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<BocalItem, String>("descricao"));

        bocais = FXCollections.observableArrayList();
        setItems(bocais);
        
        getColumns().addAll(idCol, descricaoCol);

    }

    public void reload(final List<Bocal> ensaios) {
        this.bocais.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (Bocal e: ensaios) {
                    BocalItem item = new BocalItem(e);
                    BocalTableView.this.bocais.add(item);
                }
            }
            
        });
    }

    public Bocal getSelectedItem() {
        BocalItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toBocal();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding da <code>Mercadoria</code> com <code>TableView</code>.
     */
    public static class BocalItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty descricao;
        
        private BocalItem(Bocal b) {
            this.id = new SimpleStringProperty(b.getId() + "");
            this.descricao = new SimpleStringProperty(b.getDescricao());
            
        }

        public String getId() {
            return id.get();
        }
        
        public String getDescricao() {
            return descricao.get();
        }
        
        public Bocal toBocal(){
            Bocal b = new Bocal();
            b.setId(Integer.parseInt(this.id.get()));
            b.setDescricao(this.descricao.get());
            
            return b;
        }

    }
}
