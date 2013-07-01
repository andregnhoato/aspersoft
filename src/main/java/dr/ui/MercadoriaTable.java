package dr.ui;

import dr.model.Mercadoria;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Reune os componentes para formar uma tabela de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.jfx.ui.MercadoriaTableView
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaTable extends VBox {
    
    private MercadoriaTableView table;
    
    public MercadoriaTable(){
        table = new MercadoriaTableView();
        this.getChildren().addAll(table);
        this.setPadding(new Insets(30, 0, 0, 10));//css
    }
    
    public void reload(List<Mercadoria> mercadorias) {
        table.reload(mercadorias);
    }

    public void setMouseEvent(EventHandler<MouseEvent> event) {
        table.setOnMouseClicked(event);
    }
    
    public Mercadoria getMercadoriaSelected() {
        return table.getSelectedItem();
    }
}
