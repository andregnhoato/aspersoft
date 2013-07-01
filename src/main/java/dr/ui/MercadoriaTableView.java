package dr.ui;

import dr.model.Mercadoria;
import java.text.ParseException;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <code>TableView</code> adaptada para apresentar objetos <code>Mercadoria</code>.
 *
 * @author YaW Tecnologia
 */
public class MercadoriaTableView extends TableView<MercadoriaTableView.MercadoriaItem> {

    private ObservableList<MercadoriaItem> mercadorias;

    public MercadoriaTableView() {
        TableColumn<MercadoriaItem, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(80);
        idCol.setCellValueFactory(new PropertyValueFactory<MercadoriaItem, String>("id"));

        TableColumn<MercadoriaItem, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setMinWidth(190);
        nomeCol.setCellValueFactory(new PropertyValueFactory<MercadoriaItem, String>("nome"));

        TableColumn<MercadoriaItem, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setMinWidth(200);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<MercadoriaItem, String>("descricao"));

        TableColumn<MercadoriaItem, String> quantidadeCol = new TableColumn<>("Quantidade");
        quantidadeCol.setMinWidth(105);
        quantidadeCol.setCellValueFactory(new PropertyValueFactory<MercadoriaItem, String>("quantidade"));

        TableColumn<MercadoriaItem, String> precoCol = new TableColumn<>("Preço");
        precoCol.setMinWidth(100);
        precoCol.setCellValueFactory(new PropertyValueFactory<MercadoriaItem, String>("preco"));

        mercadorias = FXCollections.observableArrayList();
        setItems(mercadorias);
        
        getColumns().addAll(idCol, nomeCol, descricaoCol, quantidadeCol, precoCol);

    }

    public void reload(final List<Mercadoria> mercadorias) {
        this.mercadorias.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (Mercadoria m: mercadorias) {
                    MercadoriaItem item = new MercadoriaItem(m);
                    MercadoriaTableView.this.mercadorias.add(item);
                }
            }
            
        });
    }

    public Mercadoria getSelectedItem() {
        MercadoriaItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toMercadoria();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding da <code>Mercadoria</code> com <code>TableView</code>.
     */
    public static class MercadoriaItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty nome;
        private final SimpleStringProperty descricao;
        private final SimpleStringProperty quantidade;
        private final SimpleStringProperty preco;
        private final SimpleStringProperty version;

        private MercadoriaItem(Mercadoria m) {
            this.id = new SimpleStringProperty(m.getId() + "");
            this.nome = new SimpleStringProperty(m.getNome());
            this.descricao = new SimpleStringProperty(m.getDescricao());
            this.quantidade = new SimpleStringProperty(m.getQuantidade() + "");
            this.preco = new SimpleStringProperty(Mercadoria.convertPrecoToString(m.getPreco()));
            this.version = new SimpleStringProperty(m.getVersion() + "");
        }

        public String getId() {
            return id.get();
        }
        
        public String getNome() {
            return nome.get();
        }

        public String getDescricao() {
            return descricao.get();
        }

        public String getQuantidade() {
            return quantidade.get();
        }

        public String getPreco() {
            return preco.get();
        }
        
        public Mercadoria toMercadoria(){
            double preco;
            try {
                preco = Mercadoria.formatStringToPreco(this.preco.get());
            } catch(ParseException pex) {
                throw new RuntimeException();
            }
            return new Mercadoria(
                    Integer.valueOf(this.id.get()),
                    this.nome.get(),
                    this.descricao.get(),
                    Integer.valueOf(this.quantidade.get()),
                    preco,
                    Integer.valueOf(this.version.get()));
        }
    }
}
