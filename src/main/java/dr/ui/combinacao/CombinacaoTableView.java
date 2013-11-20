package dr.ui.combinacao;

import dr.model.Bocal;
import dr.model.Combinacao;
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
 * <code>TableView</code> adaptada para apresentar objetos <code>Combinacao</code>.
 * @author @Andre
 */
public class CombinacaoTableView extends TableView<CombinacaoTableView.CombinacaoItem> {

    private ObservableList<CombinacaoItem> combinacoes;

    public CombinacaoTableView() {
        TableColumn<CombinacaoItem, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("id"));
        
        TableColumn<CombinacaoItem, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setMinWidth(100);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("descricao"));

        TableColumn<CombinacaoItem, String> pressaoCol = new TableColumn<>("Pressão");
        pressaoCol.setMinWidth(80);
        pressaoCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("pressao"));

        TableColumn<CombinacaoItem, String> combinacaoCol = new TableColumn<>("Combinação de bocais");
        combinacaoCol.setMinWidth(130);
        combinacaoCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("combinacao"));
        
        TableColumn<CombinacaoItem, String> diametroIrrigado = new TableColumn<>("Diâmetro Irrigado");
        diametroIrrigado.setMinWidth(130);
        diametroIrrigado.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("diametroIrrigado"));
        
        TableColumn<CombinacaoItem, String> peqCol = new TableColumn<>("Peq");
        peqCol.setMinWidth(80);
        peqCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("peq"));
        
        TableColumn<CombinacaoItem, String> alturaCol = new TableColumn<>("Altura");
        alturaCol.setMinWidth(80);
        alturaCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("gridAltura"));

        TableColumn<CombinacaoItem, String> larguraCol = new TableColumn<>("Largura");
        larguraCol.setMinWidth(80);
        larguraCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("gridLargura"));
        
        TableColumn<CombinacaoItem, String> vazaoCol = new TableColumn<>("Vazão");
        vazaoCol.setMinWidth(80);
        vazaoCol.setCellValueFactory(new PropertyValueFactory<CombinacaoItem, String>("vazao"));
        

        combinacoes = FXCollections.observableArrayList();
        setItems(combinacoes);
        
        getColumns().addAll(idCol, descricaoCol, combinacaoCol, pressaoCol, vazaoCol, diametroIrrigado , alturaCol, larguraCol, peqCol);

    }

    public void reload(final List<Combinacao> combinacoes) {
        this.combinacoes.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (Combinacao e: combinacoes) {
                    CombinacaoItem item = new CombinacaoItem(e);
                    CombinacaoTableView.this.combinacoes.add(item);
                }
            }
            
        });
    }

    public Combinacao getSelectedItem() {
        CombinacaoItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toCombinacao();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding da <code>Mercadoria</code> com <code>TableView</code>.
     */
    public static class CombinacaoItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty descricao;
        private final SimpleStringProperty pressao;
        private final SimpleStringProperty combinacao;
        private final SimpleStringProperty diametroIrrigado;
        private final SimpleStringProperty peq;
        private final SimpleStringProperty gridAltura;
        private final SimpleStringProperty gridLargura;
        private final SimpleStringProperty version;
        private final SimpleStringProperty vazao;
        private final Bocal bocalObject;
        private final QuebraJato quebraJatoObject;

        private CombinacaoItem(Combinacao c) {
            this.id = new SimpleStringProperty(c.getId() + "");
            this.descricao = new SimpleStringProperty(c.getDescricao());
            this.pressao = new SimpleStringProperty(c.getPressao()+"");
            this.combinacao = new SimpleStringProperty((c.getBocal()!= null ?c.getBocal().getDescricao():"") + " X " + (c.getQuebraJato() != null ? c.getQuebraJato().getDescricao() :  ""));
            this.diametroIrrigado = new SimpleStringProperty(c.getDiametroIrrigado()+"");
            this.peq = new SimpleStringProperty(c.getPeq()+"");
            this.gridAltura = new SimpleStringProperty(c.getAltura()+ "");
            this.gridLargura = new SimpleStringProperty(c.getLargura()+ "");
            this.version = new SimpleStringProperty(c.getVersion() + "");
            this.vazao = new SimpleStringProperty(c.getVazao() + "");
            this.bocalObject = c.getBocal();
            this.quebraJatoObject= c.getQuebraJato();
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

        public String getCombinacao() {
            return combinacao.get();
        }
        
        public String getDiametroIrrigado() {
            return diametroIrrigado.get();
        }
        
        public String getPeq() {
            return peq.get();
        }
        
        public String getGridAltura() {
            return gridAltura.get();
        }
        
        public String getGridLargura() {
            return gridLargura.get();
        }

        public String getVazao() {
            return vazao.get();
        }
       
        public Bocal getBocalObject() {
            return bocalObject;
        }

        public QuebraJato getQuebraJatoObject() {
            return quebraJatoObject;
        }
        
        public Combinacao toCombinacao(){
            Combinacao c = new Combinacao();
            c.setId(Integer.parseInt(this.id.get()));
            c.setDescricao(this.descricao.get());
            c.setPressao(Float.valueOf(this.pressao.get()));
            c.setPeq(Float.valueOf(this.peq.get()));
            c.setAltura(Float.valueOf(this.gridAltura.get()));
            c.setLargura(Float.valueOf(this.gridLargura.get()));
            c.setQuebraJato(this.quebraJatoObject);
            c.setBocal(this.bocalObject);
            c.setVersion(Integer.parseInt(this.version.get()));
            c.setVazao(Float.parseFloat(this.vazao.get()));
            c.setDiametroIrrigado(Float.parseFloat(this.diametroIrrigado.get()));
            
            return c;
        }
    }
}
