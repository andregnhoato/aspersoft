package dr.ui.ensaio;

import dr.model.Ensaio;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * Reune os componentes para formar uma tabela de
 * <code>Ensaio</code>.
 *
 * @see ui.EnsaioTableView
 *
 * @author
 * @Andre
 */
public class EnsaioTable extends BorderPane{

    private EnsaioTableView table;

    public EnsaioTable() {
        table = new EnsaioTableView();
        this.setCenter(table);

        this.autosize();
        

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
