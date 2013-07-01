package dr.ui;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Constrói um <code>GridPane</code> com capacidade de organizar os elementos de forma tabular.
 *
 * <p>Cada linha é organizada com um <code>Label</code> na coluna1 e um <code>TextField</code> na coluna2.
 * 
 * @author YaW Tecnologia
 */
public class GridFormBuilder {
    
    private GridPane pane;
    
    private int row = 0;
    
    public GridFormBuilder() {
        this.pane = new GridPane();
        this.pane.getStyleClass().add("gridForm");
    }
    
    public GridFormBuilder addRow(Label label, TextField tf) {
        this.pane.add(label, 0, row);
        this.pane.add(tf, 1, row++);
        GridPane.setHalignment(label, HPos.RIGHT);
        return this;
    }
    
    public GridFormBuilder addRow(Label label, TextField tf, Label label2, TextField tf2) {
        this.pane.add(label, 0, row);
        this.pane.add(tf, 1, row);
        this.pane.add(label2, 2, row);
        this.pane.add(tf2, 3, row++);
        GridPane.setHalignment(label, HPos.RIGHT);
        return this;
    }

    public GridPane build() {
        return this.pane;
    }
}
