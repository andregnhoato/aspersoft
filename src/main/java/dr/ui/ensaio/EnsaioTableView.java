package dr.ui.ensaio;

import dr.model.Bocal;
import dr.model.Ensaio;
import dr.model.QuebraJato;
import dr.util.WindUtil;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <code>TableView</code> adaptada para apresentar objetos
 * <code>Ensaio</code>.
 *
 * @author
 * @Andre
 */
public class EnsaioTableView extends TableView<EnsaioTableView.EnsaioItem> {

    private ObservableList<EnsaioItem> ensaios;

    public EnsaioTableView() {
        TableColumn<EnsaioItem, String> idCol = new TableColumn<>("Id");
//        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("id"));

        TableColumn<EnsaioItem, String> descricaoCol = new TableColumn<>("Descrição");
//        descricaoCol.setMinWidth(100);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("descricao"));

        TableColumn<EnsaioItem, String> pressaoCol = new TableColumn<>("Pressão");
//        pressaoCol.setMinWidth(80);
        pressaoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("pressao"));

        TableColumn<EnsaioItem, String> bocalCol = new TableColumn<>("Bocal");
//        bocalCol.setMinWidth(80);
        bocalCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("bocal"));

        TableColumn<EnsaioItem, String> quebraJatoCol = new TableColumn<>("QuebraJato");
//        quebraJatoCol.setMinWidth(90);
        quebraJatoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("quebraJato"));

        TableColumn<EnsaioItem, String> duracaoCol = new TableColumn<>("Duração");
//        duracaoCol.setMinWidth(80);
        duracaoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("duracao"));

        TableColumn<EnsaioItem, String> espacamentoCol = new TableColumn<>("Esp. pluviometro");
//        espacamentoCol.setMinWidth(110);
        espacamentoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("espacamentoPluviometro"));

        TableColumn<EnsaioItem, String> alturaCol = new TableColumn<>("Altura");
//        alturaCol.setMinWidth(80);
        alturaCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("gridAltura"));

        TableColumn<EnsaioItem, String> larguraCol = new TableColumn<>("Largura");
//        larguraCol.setMinWidth(80);
        larguraCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("gridLargura"));

        TableColumn<EnsaioItem, String> dataCol = new TableColumn<>("Data");
//        dataCol.setMinWidth(100);
        dataCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("data"));

        TableColumn<EnsaioItem, String> velocidadeVentoCol = new TableColumn<>("Vel. Vento");
//        velocidadeVentoCol.setMinWidth(80);
        velocidadeVentoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("velocidadeVento"));

        TableColumn<EnsaioItem, String> direcaoVentoCol = new TableColumn<>("Dir. Vento");
//        direcaoVentoCol.setMinWidth(80);
        direcaoVentoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("direcaoVento"));

        TableColumn<EnsaioItem, String> direcaoVentoGrausCol = new TableColumn<>("Sentido do Vento");
//        direcaoVentoGrausCol.setMinWidth(150);
        direcaoVentoGrausCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("direcaoVentoGraus"));

        TableColumn<EnsaioItem, String> vazaoCol = new TableColumn<>("Vazão");
//        vazaoCol.setMinWidth(80);
        vazaoCol.setCellValueFactory(new PropertyValueFactory<EnsaioItem, String>("vazao"));


        ensaios = FXCollections.observableArrayList();
        setItems(ensaios);

        getColumns().addAll(idCol, descricaoCol, dataCol, pressaoCol, bocalCol, quebraJatoCol, duracaoCol, velocidadeVentoCol, direcaoVentoCol, direcaoVentoGrausCol, vazaoCol, espacamentoCol/*, alturaCol, larguraCol*/);

    }

    public void reload(final List<Ensaio> ensaios) {
        this.ensaios.clear();
        if (ensaios !=  null && ensaios.size() > 0) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (Ensaio e : ensaios) {
                        EnsaioItem item = new EnsaioItem(e);
                        EnsaioTableView.this.ensaios.add(item);
                    }
                }
            });
        }
    }

    public Ensaio getSelectedItem() {
        EnsaioItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toEnsaio();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding da
     * <code>Mercadoria</code> com
     * <code>TableView</code>.
     */
    public static class EnsaioItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty descricao;
        private final SimpleStringProperty pressao;
        private final SimpleStringProperty bocal;
        private final SimpleStringProperty quebraJato;
        private final SimpleStringProperty espacamentoPluviometro;
        private final SimpleStringProperty inicio;
        private final SimpleStringProperty duracao;
        private final SimpleStringProperty velocidadeVento;
        private final SimpleStringProperty direcaoVento;
        private final SimpleStringProperty direcaoVentoGraus;
        private final SimpleStringProperty gridAltura;
        private final SimpleStringProperty gridLargura;
        private final SimpleStringProperty version;
        private final SimpleStringProperty vazao;
        private final SimpleStringProperty evaporacao;
        private final SimpleStringProperty data;
        private final Bocal bocalObject;
        private final QuebraJato quebraJatoObject;

        private EnsaioItem(Ensaio e) {
            this.id = new SimpleStringProperty(e.getId() + "");
            this.descricao = new SimpleStringProperty(e.getDescricao());
            this.pressao = new SimpleStringProperty(e.getPressao());
            this.bocal = new SimpleStringProperty((e.getBocal() != null ? e.getBocal().getDescricao() : ""));
            this.quebraJato = new SimpleStringProperty((e.getQuebraJato() != null ? e.getQuebraJato().getDescricao() : ""));
            this.espacamentoPluviometro = new SimpleStringProperty(e.getEspacamentoPluviometro() + "");
            this.inicio = new SimpleStringProperty(e.getInicio());
            this.duracao = new SimpleStringProperty(e.getDuracao());
            this.velocidadeVento = new SimpleStringProperty(e.getVelocidadeVento() + "");
            this.direcaoVento = new SimpleStringProperty(WindUtil.getWindByDegress(e.getDirecaoVentoGraus()));
            this.direcaoVentoGraus = new SimpleStringProperty(e.getDirecaoVentoGraus() + "");
            this.gridAltura = new SimpleStringProperty(e.getGridAltura() + "");
            this.gridLargura = new SimpleStringProperty(e.getGridLargura() + "");
            this.data = new SimpleStringProperty(formatDate(e.getData()));
            this.version = new SimpleStringProperty(e.getVersion() + "");
            this.vazao = new SimpleStringProperty(e.getVazao() + "");
            this.evaporacao = new SimpleStringProperty(e.getEvaporacao() + "");
            this.bocalObject = e.getBocal();
            this.quebraJatoObject = e.getQuebraJato();
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

        public String getEspacamentoPluviometro() {
            return espacamentoPluviometro.get();
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

        public String getDirecaoVentoGraus() {
            return direcaoVentoGraus.get();
        }

        public String getVazao() {
            return vazao.get();
        }

        public String getEvaporacao() {
            return evaporacao.get();
        }

        public Bocal getBocalObject() {
            return bocalObject;
        }

        public QuebraJato getQuebraJatoObject() {
            return quebraJatoObject;
        }

        public Ensaio toEnsaio() {
            Ensaio e = new Ensaio();
            e.setId(Integer.parseInt(this.id.get()));
            e.setDescricao(this.descricao.get());
            e.setPressao(this.pressao.get());
            e.setDuracao(this.duracao.get());
            e.setGridAltura(Integer.parseInt(this.gridAltura.get()));
            e.setGridLargura(Integer.parseInt(this.gridLargura.get()));
            e.setQuebraJato(this.quebraJatoObject);
            e.setEspacamentoPluviometro(Float.parseFloat(this.espacamentoPluviometro.get()));
            e.setBocal(this.bocalObject);
            e.setInicio(this.inicio.get());
            e.setVersion(Integer.parseInt(this.version.get()));
            e.setDirecaoVentoGraus(Float.parseFloat(this.direcaoVentoGraus.get()));
            e.setVelocidadeVento(Float.parseFloat(this.velocidadeVento.get()));
            e.setVazao(Float.parseFloat(this.vazao.get()));
            e.setEvaporacao(Float.parseFloat(this.evaporacao.get()));
            e.setData(formatDate(this.data.get()));

            return e;
        }

        private String formatDate(Date data) {
            if (data != null) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                return df.format(data);
            } else {
                return null;
            }
        }

        private Date formatDate(String data) {
            if (data != null && data != "") {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    return df.parse(data);
                } catch (ParseException ex) {
                    Logger.getLogger(EnsaioTableView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;

        }
    }
}
