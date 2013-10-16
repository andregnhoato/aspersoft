package dr.ui.coleta.uniformidade;

import dr.model.Coleta;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * <code>TableView</code> adaptada para apresentar objetos
 * <code>Sobreposição</code>.
 *
 * @author
 * @Andre
 */
public class UniformidadeTableView extends TableView<UniformidadeTableView.ColetaItem> {

    private ObservableList coletas;
    private Coleta coleta;

    public UniformidadeTableView() {
    }

    public void reload(final List<Coleta> coletas) {
        this.coletas.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    /**
     * Item da tabela, faz o binding da
     * <code>coleta</code> com
     * <code>TableView</code>. this object represent a table cell or should
     * represent
     */
    public static class ColetaItem {

        private final SimpleFloatProperty valor;

        public ColetaItem() {
            this.valor = new SimpleFloatProperty();
        }

        public Float getValor() {
            return valor.get();
        }

        public void setValor(Float valor) {
            this.valor.set(valor);
        }

        public Coleta toColeta() {
            Coleta c = new Coleta();
            c.setValor(this.valor.get());

            return c;
        }
    }
}
