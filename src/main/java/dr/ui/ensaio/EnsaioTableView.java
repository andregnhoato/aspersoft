package dr.ui.experiment;

import dr.model.Experiment;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <code>TableView</code> adaptada para apresentar objetos <code>Experiment</code>.
 * @author @Andre
 */
public class ExperimentTableView extends TableView<ExperimentTableView.ExperimentItem> {

    private ObservableList<ExperimentItem> experiments;

    public ExperimentTableView() {
        TableColumn<ExperimentItem, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(80);
        idCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("id"));

        TableColumn<ExperimentItem, String> pressureCol = new TableColumn<>("Pressão");
        pressureCol.setMinWidth(80);
        pressureCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("pressure"));

        TableColumn<ExperimentItem, String> nozzleCol = new TableColumn<>("Bocal");
        nozzleCol.setMinWidth(80);
        nozzleCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("noozle"));
        
        TableColumn<ExperimentItem, String> jetBreakCol = new TableColumn<>("QuebraJato");
        jetBreakCol.setMinWidth(80);
        jetBreakCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("jetBreak"));
        
        TableColumn<ExperimentItem, String> durationCol = new TableColumn<>("Duração");
        durationCol.setMinWidth(80);
        durationCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("duration"));
        
        TableColumn<ExperimentItem, String> heigthCol = new TableColumn<>("Altura");
        heigthCol.setMinWidth(80);
        heigthCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("gridHeigth"));

        TableColumn<ExperimentItem, String> widthCol = new TableColumn<>("Largura");
        widthCol.setMinWidth(80);
        widthCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("gridWidth"));
        
        TableColumn<ExperimentItem, String> dateCol = new TableColumn<>("Data");
        dateCol.setMinWidth(80);
        dateCol.setCellValueFactory(new PropertyValueFactory<ExperimentItem, String>("date"));
        
                

        experiments = FXCollections.observableArrayList();
        setItems(experiments);
        
        getColumns().addAll(idCol, dateCol, pressureCol, nozzleCol, jetBreakCol, durationCol, heigthCol, widthCol);

    }

    public void reload(final List<Experiment> experiments) {
        this.experiments.clear();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (Experiment e: experiments) {
                    ExperimentItem item = new ExperimentItem(e);
                    ExperimentTableView.this.experiments.add(item);
                }
            }
            
        });
    }

    public Experiment getSelectedItem() {
        ExperimentItem item = getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.toExperiment();
        }
        return null;
    }

    /**
     * Item da tabela, faz o binding da <code>Mercadoria</code> com <code>TableView</code>.
     */
    public static class ExperimentItem {

        private final SimpleStringProperty id;
        private final SimpleStringProperty pressure;
        private final SimpleStringProperty nozzle;
        private final SimpleStringProperty jetBreak;
        private final SimpleStringProperty start;
        private final SimpleStringProperty duration;
        private final SimpleStringProperty gridHeight;
        private final SimpleStringProperty gridWidth;
        private final SimpleStringProperty version;
        private final SimpleStringProperty date;

        private ExperimentItem(Experiment e) {
            this.id = new SimpleStringProperty(e.getId() + "");
            this.pressure = new SimpleStringProperty(e.getPressure());
            this.nozzle = new SimpleStringProperty(e.getNozzle());
            this.jetBreak = new SimpleStringProperty(e.getJetBreak());
            this.start = new SimpleStringProperty(e.getStart());
            this.duration = new SimpleStringProperty(e.getDuration());
            this.gridHeight = new SimpleStringProperty(e.getGridHeight()+ "");
            this.gridWidth = new SimpleStringProperty(e.getGridWidth()+ "");
            this.date = new SimpleStringProperty(e.getDate()+"");
            this.version = new SimpleStringProperty(e.getVersion() + "");
        }

        public String getId() {
            return id.get();
        }
        
        public String getPressure() {
            return pressure.get();
        }

        public String getNozzle() {
            return nozzle.get();
        }
        
        public String getJetBreak() {
            return jetBreak.get();
        }
        
        public String getStart() {
            return start.get();
        }
        
        public String getDuration() {
            return duration.get();
        }

        public String getGridHeigth() {
            return gridHeight.get();
        }
        
        public String getGridWidth() {
            return gridWidth.get();
        }

        public String getDate() {
            return date.get();
        }
        
        public Experiment toExperiment(){
            Experiment e = new Experiment();
            e.setId(Integer.parseInt(this.id.get()));
            e.setPressure(this.pressure.get());
            e.setDuration(this.duration.get());
            e.setGridHeight(Integer.parseInt(this.gridHeight.get()));
            e.setGridWidth(Integer.parseInt(this.gridWidth.get()));
            e.setJetBreak(this.jetBreak.get());
            e.setNozzle(this.nozzle.get());
            e.setStart(this.start.get());
            e.setVersion(Integer.parseInt(this.version.get()));
            
            return e;
        }
    }
}
