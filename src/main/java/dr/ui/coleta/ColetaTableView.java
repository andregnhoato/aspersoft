package dr.ui.coleta;

import dr.ui.ensaio.*;
import dr.model.Coleta;
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
 * <code>TableView</code> adaptada para apresentar objetos <code>Coleta</code>.
 * @author @Andre
 */
public class ColetaTableView extends TableView<ColetaTableView.ColetaItem> {

    private ObservableList<ColetaItem> ensaios;
    private Coleta coleta;
    private Ensaio ensaio;

    public ColetaTableView(Ensaio e) {
        
        for (int i = 0; i < e.getGridLargura(); i++) {
            TableColumn<ColetaItem, String> idCol = new TableColumn<>(i+"");
            idCol.setMinWidth(10);
            idCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("id"));
        }
       
        TableColumn<ColetaItem, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("id"));
        
        TableColumn<ColetaItem, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setMinWidth(100);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("descricao"));

        TableColumn<ColetaItem, String> pressaoCol = new TableColumn<>("Pressão");
        pressaoCol.setMinWidth(80);
        pressaoCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("pressao"));

        TableColumn<ColetaItem, String> bocalCol = new TableColumn<>("Bocal");
        bocalCol.setMinWidth(80);
        bocalCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("bocal"));
        
        TableColumn<ColetaItem, String> quebraJatoCol = new TableColumn<>("QuebraJato");
        quebraJatoCol.setMinWidth(90);
        quebraJatoCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("quebraJato"));
        
        TableColumn<ColetaItem, String> duracaoCol = new TableColumn<>("Duração");
        duracaoCol.setMinWidth(80);
        duracaoCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("duracao"));
        
        TableColumn<ColetaItem, String> alturaCol = new TableColumn<>("Altura");
        alturaCol.setMinWidth(80);
        alturaCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("gridAltura"));

        TableColumn<ColetaItem, String> larguraCol = new TableColumn<>("Largura");
        larguraCol.setMinWidth(80);
        larguraCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("gridLargura"));
        
        TableColumn<ColetaItem, String> dataCol = new TableColumn<>("Data");
        dataCol.setMinWidth(100);
        dataCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("data"));
        
        TableColumn<ColetaItem, String> velocidadeVentoCol = new TableColumn<>("Vel. Vento");
        velocidadeVentoCol.setMinWidth(80);
        velocidadeVentoCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("velocidadeVento"));
        
        TableColumn<ColetaItem, String> direcaoVentoCol = new TableColumn<>("Dir. Vento");
        direcaoVentoCol.setMinWidth(80);
        direcaoVentoCol.setCellValueFactory(new PropertyValueFactory<ColetaItem, String>("direcaoVento"));
        
/*
        ensaios = FXCollections.observableArrayList();
        setItems(ensaios);
        
        getColumns().addAll(idCol, descricaoCol, dataCol, pressaoCol, bocalCol, quebraJatoCol, duracaoCol, velocidadeVentoCol, direcaoVentoCol/*, alturaCol, larguraCol);
*/
    }

    public void reload(final List<Coleta> ensaios) {
        this.ensaios.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (Coleta e: ensaios) {
                    ColetaItem item = new ColetaItem(e);
             //       ColetaTableView.this.ensaios.add(item);
                }
            }
            
        });
    }

    public Coleta getSelectedItem() {
       /*ColetaItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toColeta();
        }*/
        return null;
    }

    /**
     * Item da tabela, faz o binding da <code>Mercadoria</code> com <code>TableView</code>.
     */
    public static class ColetaItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty valor;

        private ColetaItem(Coleta c) {
            this.id = new SimpleStringProperty(c.getId() + "");
            this.valor = new SimpleStringProperty(c.getValor()+"");
        }

        public String getId() {
            return id.get();
        }
        
        public String getValor() {
            return valor.get();
        }
        
        public Coleta toColeta(){
            Coleta e = new Coleta();
            e.setId(Integer.parseInt(this.id.get()));
           /* e.setDescricao(this.descricao.get());
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
           */ 
            return e;
        }
    }
}
