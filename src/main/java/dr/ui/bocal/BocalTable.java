package dr.ui.bocal;

import dr.ui.bocal.*;
import dr.model.Bocal;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 * Reune os componentes para formar uma tabela de
 * <code>Bocal</code>.
 *
 * @see ui.BocalTableView
 *
 * @author
 * @Andre
 */
public class BocalTable extends BorderPane {

    private BocalTableView table;

    public BocalTable() {
        table = new BocalTableView();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
       
//        table.setMaxSize(bounds.getWidth() / 2, bounds.getHeight() / 2);
//        table.setMinSize(bounds.getWidth() / 2.2, bounds.getHeight() / 2.5);
        
        
//        this.getChildren().addAll(table);
        this.setCenter(table);
        this.setPadding(new Insets(30, 10, 10, 10));//css
    }

    public void reload(List<Bocal> bocais) {
        table.reload(bocais);
    }

    public void setMouseEvent(EventHandler<MouseEvent> event) {
        table.setOnMouseClicked(event);
    }

    public Bocal getBocalSelected() {
        return table.getSelectedItem();
    }

    public Integer getTotalBocal() {
        return table.getItems().size();
    }
}
