package dr.ui.ensaio;

import dr.model.Ensaio;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Reune os componentes para formar uma tabela de <code>Ensaio</code>.
 * 
 * @see ui.EnsaioTableView
 * 
 * @author @Andre
 */
public class EnsaioTable extends VBox {
    
    private EnsaioTableView table;
    
    public EnsaioTable(){
        table = new EnsaioTableView();
        this.getChildren().addAll(table);
        this.setPadding(new Insets(30, 0, 0, 10));//css
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
}
