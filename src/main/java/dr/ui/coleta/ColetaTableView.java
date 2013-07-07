package dr.ui.ensaio;

import dr.model.Ensaio;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <code>TableView</code> adaptada para apresentar objetos <code>Ensaio</code>.
 * @author @Andre
 */
public class EnsaioTableView extends TableView<EnsaioTableView.EnsaioItem> {

    private ObservableList<EnsaioItem> ensaios;

    public EnsaioTableView() {
        TableColumn<EnsaioItem, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("id"));
        
        TableColumn<EnsaioItem, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setMinWidth(100);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("descricao"));

        TableColumn<EnsaioItem, String> pressaoCol = new TableColumn<>("Pressão");
        pressaoCol.setMinWidth(80);
        pressaoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("pressao"));

        TableColumn<EnsaioItem, String> bocalCol = new TableColumn<>("Bocal");
        bocalCol.setMinWidth(80);
        bocalCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("bocal"));
        
        TableColumn<EnsaioItem, String> quebraJatoCol = new TableColumn<>("QuebraJato");
        quebraJatoCol.setMinWidth(90);
        quebraJatoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("quebraJato"));
        
        TableColumn<EnsaioItem, String> duracaoCol = new TableColumn<>("Duração");
        duracaoCol.setMinWidth(80);
        duracaoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("duracao"));
        
        TableColumn<EnsaioItem, String> alturaCol = new TableColumn<>("Altura");
        alturaCol.setMinWidth(80);
        alturaCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("gridAltura"));

        TableColumn<EnsaioItem, String> larguraCol = new TableColumn<>("Largura");
        larguraCol.setMinWidth(80);
        larguraCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("gridLargura"));
        
        TableColumn<EnsaioItem, String> dataCol = new TableColumn<>("Data");
        dataCol.setMinWidth(100);
        dataCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("data"));
        
        TableColumn<EnsaioItem, String> velocidadeVentoCol = new TableColumn<>("Vel. Vento");
        velocidadeVentoCol.setMinWidth(80);
        velocidadeVentoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("velocidadeVento"));
        
        TableColumn<EnsaioItem, String> direcaoVentoCol = new TableColumn<>("Dir. Vento");
        direcaoVentoCol.setMinWidth(80);
        direcaoVentoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("direcaoVento"));
        

        ensaios = FXCollections.observableArrayList();
        setItems(ensaios);
        
        getColumns().addAll(idCol, descricaoCol, dataCol, pressaoCol, bocalCol, quebraJatoCol, duracaoCol, velocidadeVentoCol, direcaoVentoCol/*, alturaCol, larguraCol*/);

    }

    public void reload(final List<Ensaio> ensaios) {
        this.ensaios.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (Ensaio e: ensaios) {
                    EnsaioItem item = new EnsaioItem(e);
                    EnsaioTableView.this.ensaios.add(item);
                }
            }
            
        });
    }

    public Ensaio getSelectedItem() {
        EnsaioItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toEnsaio();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding da <code>Mercadoria</code> com <code>TableView</code>.
     */
    public static class EnsaioItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty descricao;
        private final SimpleStringProperty pressao;
        private final SimpleStringProperty bocal;
        private final SimpleStringProperty quebraJato;
        private final SimpleStringProperty inicio;
        private final SimpleStringProperty duracao;
        private final SimpleStringProperty velocidadeVento;
        private final SimpleStringProperty direcaoVento;
        private final SimpleStringProperty gridAltura;
        private final SimpleStringProperty gridLargura;
        private final SimpleStringProperty version;
        private final SimpleStringProperty data;

        private EnsaioItem(Ensaio e) {
            this.id = new SimpleStringProperty(e.getId() + "");
            this.descricao = new SimpleStringProperty(e.getDescricao());
            this.pressao = new SimpleStringProperty(e.getPressao());
            this.bocal = new SimpleStringProperty(e.getBocal());
            this.quebraJato = new SimpleStringProperty(e.getQuebraJato());
            this.inicio = new SimpleStringProperty(e.getInicio());
            this.duracao = new SimpleStringProperty(e.getDuracao());
            this.velocidadeVento = new SimpleStringProperty(e.getVelocidadeVento()+"");
            this.direcaoVento = new SimpleStringProperty(e.getDirecaoVento());
            this.gridAltura = new SimpleStringProperty(e.getGridAltura()+ "");
            this.gridLargura = new SimpleStringProperty(e.getGridLargura()+ "");
            this.data = new SimpleStringProperty(e.getData()+"");
            this.version = new SimpleStringProperty(e.getVersion() + "");
        }

        public String getId() {
            return id.get();
        }
        
        public String getDescricao() {
            return descricao.get();
        }
        
        public String getPressao() {
            return pressao.get();
        }

        public String getBocal() {
            return bocal.get();
        }
        
        public String getQuebraJato() {
            return quebraJato.get();
        }
        
        public String getInicio() {
            return inicio.get();
        }
        
        public String getDuracao() {
            return duracao.get();
        }

        public String getGridAltura() {
            return gridAltura.get();
        }
        
        public String getGridLargura() {
            return gridLargura.get();
        }

        public String getData() {
            return data.get();
        }
        
        public String getVelocidadeVento() {
            return velocidadeVento.get();
        }
        
        public String getDirecaoVento() {
            return direcaoVento.get();
        }
        
        
        public Ensaio toEnsaio(){
            Ensaio e = new Ensaio();
            e.setId(Integer.parseInt(this.id.get()));
            e.setDescricao(this.descricao.get());
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
    }
}
