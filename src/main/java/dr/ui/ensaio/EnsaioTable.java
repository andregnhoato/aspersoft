package dr.ui.ensaio;

import dr.model.Ensaio;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 * Reune os componentes para formar uma tabela de
 * <code>Ensaio</code>.
 *
 * @see ui.EnsaioTableView
 *
 * @author
 * @Andre
 */
public class EnsaioTable extends BorderPane {

    private EnsaioTableView table;

    public EnsaioTable() {
        table = new EnsaioTableView();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
       
//        table.setMaxSize(bounds.getWidth() / 2, bounds.getHeight() / 2);
//        table.setMinSize(bounds.getWidth() / 2.2, bounds.getHeight() / 2.5);
        
        
//        this.getChildren().addAll(table);
        this.setCenter(table);
        this.setPadding(new Insets(30, 10, 10, 10));//css
    }

    public void reload(List<Ensaio> ensaios) {
        table.reload(ensaios);
    }

    public void setMouseEvent(EventHandler<MouseEvent> event) {
        table.setOnMouseClicked(event);
    }

    public Ensaio getEnsaioSelected() {
        return table.getSelectedItem();
    }

    public Integer getTotalEnsaio() {
        return table.getItems().size();
    }
}
