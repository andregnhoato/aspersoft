package dr.ui.analise;

import dr.model.Coleta;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * <code>TableView</code> adaptada para apresentar objetos
 * <code>Sobreposição</code>.
 *
 * @author
 * @Andre
 */
public class SobreposicaoTableView extends TableView {

    private ObservableList coletas;

    public SobreposicaoTableView() {
    }

    public void reload(final List<Coleta> coletas) {
        this.coletas.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    
}
