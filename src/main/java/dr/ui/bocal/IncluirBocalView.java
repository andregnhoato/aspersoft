package dr.ui.bocal;

import dr.model.Bocal;
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
 * add/edit Bocal
 *
 * @author
 * @Andre
 */
public class IncluirBocalView extends Stage {

    private TextField tfId;
    private TextField tfDescricao;
    private TextField tfVersion;
    
    private Button bSave;
    private Button bCancel;
    private Button bRemove;

    public IncluirBocalView() {
        setTitle("Incluir Bocal");
        setWidth(390);
        setHeight(500);
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
        bSave.setId("saveBocal");
        bSave.setDefaultButton(true);

        bCancel = new Button("Cancelar");
        bCancel.setId("cancelBocal");
        bCancel.setCancelButton(true);

        bRemove = new Button("Excluir");
        bRemove.setId("removeBocal");
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

    private void populaTextFields(Bocal e) {
        tfId.setText(e.getId().toString());
        tfDescricao.setText(e.getDescricao());
        
    }

    private Bocal loadBocalFromPanel() {
        Bocal b = new Bocal();
        if (!tfDescricao.getText().trim().isEmpty()) {
            b.setDescricao(tfDescricao.getText().trim());
        }

        b.setId((tfId.getText() != null && !tfId.getText().isEmpty() ? Integer.parseInt(tfId.getText()) : null));
        b.setVersion(tfVersion.getText() != null && !tfVersion.getText().isEmpty() ? Integer.parseInt(tfVersion.getText()) : null);
        
        return b;
    }

    public void setBocal(Bocal e) {
        resetForm();
        if (e != null) {
            populaTextFields(e);
            bRemove.setVisible(true);
        }
    }

    public Integer getBocalId() {
        try {
            return Integer.parseInt(tfId.getText());
        } catch (Exception nex) {
            return null;
        }
    }

    public Bocal getBocal() {
        return loadBocalFromPanel();
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
