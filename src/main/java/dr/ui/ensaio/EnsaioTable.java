package dr.ui.experiment;

import dr.model.Experiment;
import dr.model.Mercadoria;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Reune os componentes para formar uma tabela de <code>Experiment</code>.
 * 
 * @see ui.ExperimentTableView
 * 
 * @author @Andre
 */
public class ExperimentTable extends VBox {
    
    private ExperimentTableView table;
    
    public ExperimentTable(){
        table = new ExperimentTableView();
        this.getChildren().addAll(table);
        this.setPadding(new Insets(30, 0, 0, 10));//css
    }
    
    public void reload(List<Experiment> experiments) {
        table.reload(experiments);
    }

    public void setMouseEvent(EventHandler<MouseEvent> event) {
        table.setOnMouseClicked(event);
    }
    
    public Experiment getExperimentSelected() {
        return table.getSelectedItem();
    }
}
