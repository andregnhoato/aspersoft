package dr.ui.combinacao;

import dr.model.Combinacao;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 * Reune os componentes para formar uma tabela de
 * <code>Combinacao</code>.
 *
 * @see ui.CombinacaoTableView
 *
 * @author
 * @Andre
 */
public class CombinacaoTable extends BorderPane {

    private CombinacaoTableView table;

    public CombinacaoTable() {
        table = new CombinacaoTableView();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
       
        this.setCenter(table);
    }

    public void reload(List<Combinacao> combinacoes) {
        table.reload(combinacoes);
    }

    public void setMouseEvent(EventHandler<MouseEvent> event) {
        table.setOnMouseClicked(event);
    }

    public Combinacao getCombinacaoSelected() {
        return table.getSelectedItem();
    }

    public Integer getTotalCombinacao() {
        return table.getItems().size();
    }
}
