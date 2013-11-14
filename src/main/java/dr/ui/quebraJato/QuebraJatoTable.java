package dr.ui.quebraJato;

import dr.model.QuebraJato;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 * Reune os componentes para formar uma tabela de
 * <code>QuebraJato</code>.
 *
 * @see ui.QuebraJatoTableView
 *
 * @author
 * @Andre
 */
public class QuebraJatoTable extends BorderPane {

    private QuebraJatoTableView table;

    public QuebraJatoTable() {
        table = new QuebraJatoTableView();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
       
//        table.setMaxSize(200,350);
//        table.setMinSize(bounds.getWidth() / 2.2, bounds.getHeight() / 2.5);
        
        
//        this.getChildren().addAll(table);
        this.setCenter(table);
        this.setPadding(new Insets(10, 10, 10, 10));//css
    }

    public void reload(List<QuebraJato> bocais) {
        table.reload(bocais);
    }

    public void setMouseEvent(EventHandler<MouseEvent> event) {
        table.setOnMouseClicked(event);
    }

    public QuebraJato getQuebraJatoSelected() {
        return table.getSelectedItem();
    }

    public Integer getTotalQuebraJato() {
        return table.getItems().size();
    }
}
