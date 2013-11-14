package dr.ui.quebraJato;

import dr.model.QuebraJato;
import dr.ui.GridFormBuilder;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * add/edit QuebraJato
 *
 * @author
 * @Andre
 */
public class IncluirQuebraJatoView extends Stage {

    private TextField tfId;
    private TextField tfDescricao;
    private TextField tfVersion;
    
    private Button bSave;
    private Button bCancel;
    private Button bRemove;

    public IncluirQuebraJatoView() {
        setTitle("Incluir Quebra Jato");
        setWidth(300);
        setHeight(150);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);

        inicializaComponentes();
    }

    private void inicializaComponentes() {
        VBox box = new VBox();
        box.getChildren().addAll(buildInputs(), buildButtons());

        Scene scene = new Scene(new Group(box));
        scene.getStylesheets().add("style.css");

        this.setScene(scene);
        this.resetForm();
    }

    private HBox buildButtons() {

        bSave = new Button("Salvar");
        bSave.setId("saveQuebraJato");
        bSave.setDefaultButton(true);

        bCancel = new Button("Cancelar");
        bCancel.setId("cancelQuebraJato");
        bCancel.setCancelButton(true);

        bRemove = new Button("Excluir");
        bRemove.setId("removeQuebraJato");
        bRemove.getStyleClass().add("buttonDanger");

        HBox box = new HBox();
        box.getChildren().addAll(bSave, bCancel, bRemove);
        box.getStyleClass().add("buttonBar");

        return box;
    }

    private GridPane buildInputs() {

        tfId = new TextField();
        tfId.setPromptText("");
        tfId.setId("0");
        tfId.setEditable(false);
        tfId.setMinWidth(90);
        tfId.setMaxWidth(90);

        tfDescricao = new TextField();
        tfDescricao.setPromptText("*Campo obrigatório");
        tfDescricao.setMinWidth(180);
        tfDescricao.setMaxWidth(180);
        
        tfVersion = new TextField();
        tfVersion.setVisible(false);

        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Id: "), tfId)
                .addRow(new Label("Descrição: "), tfDescricao);

        return grid.build();
    }

    public final void resetForm() {
        tfId.setText("");
        tfDescricao.setText("");
        tfVersion.setText("");
        bRemove.setVisible(false);
    }

    private void populaTextFields(QuebraJato qj) {
        tfId.setText(qj.getId().toString());
        tfDescricao.setText(qj.getDescricao());
        
    }

    private QuebraJato loadQuebraJatoFromPanel() {
        QuebraJato qj = new QuebraJato();
        if (!tfDescricao.getText().trim().isEmpty()) {
            qj.setDescricao(tfDescricao.getText().trim());
        }

        qj.setId((tfId.getText() != null && !tfId.getText().isEmpty() ? Integer.parseInt(tfId.getText()) : null));
        qj.setVersion(tfVersion.getText() != null && !tfVersion.getText().isEmpty() ? Integer.parseInt(tfVersion.getText()) : null);
        
        return qj;
    }

    public void setQuebraJato(QuebraJato qj) {
        resetForm();
        if (qj != null) {
            populaTextFields(qj);
            bRemove.setVisible(true);
        }
    }

    public Integer getQuebraJatoId() {
        try {
            return Integer.parseInt(tfId.getText());
        } catch (Exception nex) {
            return null;
        }
    }

    public QuebraJato getQuebraJato() {
        return loadQuebraJatoFromPanel();
    }

    public Button getSaveButton() {
        return bSave;
    }

    public Button getCancelButton() {
        return bCancel;
    }

    public Button getRemoveButton() {
        return bRemove;
    }

}
