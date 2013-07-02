package dr.event.experiment;

import dr.model.Experiment;
import dr.ui.GridFormBuilder;
import java.sql.Date;
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
 * add/edit Experiment bean
 * 
 * @author @Andre
 */
public class AddExperimentView extends Stage {

    private TextField tfId;
    private TextField tfPressure;
    private TextField tfNozzle;
    private TextField tfJetBreak;
    private TextField tfStart;
    private TextField tfDuration;
    private TextField tfGridHeigth;
    private TextField tfGridWidth;
    private TextField tfVersion;
    private Button bSave;
    private Button bCancel;
    private Button bRemove;

    public AddExperimentView() {
        setTitle("Incluir Ensaio");
        setWidth(400);
        setHeight(280);
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
        bSave.setId("saveExperiment");
        bSave.setDefaultButton(true);

        bCancel = new Button("Cancelar");
        bCancel.setId("cancelExperiment");
        bCancel.setCancelButton(true);
        
        bRemove = new Button("Excluir");
        bRemove.setId("removeExperiment");
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

        tfPressure = new TextField();
        tfPressure.setPromptText("*Campo obrigatório");
        tfPressure.setMinWidth(180);
        tfPressure.setMaxWidth(180);

        tfNozzle = new TextField();
        tfNozzle.setPromptText("*Campo obrigatório");
        tfNozzle.setMinWidth(180);
        tfNozzle.setMaxWidth(180);

        tfJetBreak = new TextField();
        tfJetBreak.setPromptText("*Campo obrigatório");
        tfJetBreak.setMinWidth(180);
        tfJetBreak.setMaxWidth(180);

        tfStart = new TextField();
        tfStart.setPromptText("*Campo obrigatório");
        tfStart.setMinWidth(180);
        tfStart.setMaxWidth(180);
        
        tfDuration = new TextField();
        tfDuration.setPromptText("*Campo obrigatório");
        tfDuration.setMinWidth(180);
        tfDuration.setMaxWidth(180);
        
        tfStart = new TextField();
        tfStart.setPromptText("*Campo obrigatório");
        tfStart.setMinWidth(180);
        tfStart.setMaxWidth(180);
        
        tfGridHeigth = new TextField();
        tfGridHeigth.setPromptText("*");
        tfGridHeigth.setMinWidth(40);
        tfGridHeigth.setMaxWidth(40);
        
        tfGridWidth = new TextField();
        tfGridWidth.setPromptText("*");
        tfGridWidth.setMinWidth(40);
        tfGridWidth.setMaxWidth(40);
        
        

        tfVersion = new TextField();
        tfVersion.setVisible(false);
        
        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Id: "), tfId)
                .addRow(new Label("Pressão: "), tfPressure)
                .addRow(new Label("Bocal: "), tfNozzle)
                .addRow(new Label("Quebra Jato: "), tfJetBreak)
                .addRow(new Label("Inicio: "), tfStart)
                .addRow(new Label("Duração:"), tfDuration)
                .addRow(new Label("Dimensão altura x largura:"), tfGridHeigth, new Label("x"),tfGridWidth);
        
        return grid.build();
    }

    public final void resetForm() {
        tfId.setText("");
        tfPressure.setText("");
        tfNozzle.setText("");
        tfJetBreak.setText("");
        tfStart.setText("");
        tfDuration.setText("");
        tfGridHeigth.setText("");
        tfGridWidth.setText("");
        tfVersion.setText("");
        bRemove.setVisible(false);
    }

    private void populaTextFields(Experiment e) {
        tfId.setText(e.getId().toString());
        tfPressure.setText(e.getPressure());
        tfNozzle.setText(e.getNozzle());
        tfJetBreak.setText(e.getJetBreak());
        tfStart.setText(e.getStart());
        tfDuration.setText(e.getDuration());
        tfGridHeigth.setText(e.getGridHeight().toString());
        tfGridWidth.setText(e.getGridWidth().toString());
        tfVersion.setText(e.getVersion() == null ? "0" : e.getVersion().toString());
    }


    private Experiment loadExperimentFromPanel() {
        Experiment e = new Experiment();
        if (!tfPressure.getText().trim().isEmpty())
            e.setPressure(tfPressure.getText().trim());
        
        if (!tfNozzle.getText().trim().isEmpty())
            e.setNozzle(tfNozzle.getText().trim());
        
        if (!tfJetBreak.getText().trim().isEmpty())
            e.setJetBreak(tfJetBreak.getText().trim());
        
        if (!tfStart.getText().trim().isEmpty())
            e.setStart(tfStart.getText().trim());
        
        if (!tfDuration.getText().trim().isEmpty())
            e.setDuration(tfDuration.getText().trim());
        
        try {
            if (!tfGridHeigth.getText().trim().isEmpty())
                e.setGridHeight(Integer.valueOf(tfGridHeigth.getText()));

            if (!tfGridWidth.getText().trim().isEmpty())
                e.setGridWidth(Integer.valueOf(tfGridWidth.getText()));
            
        } catch (NumberFormatException nex) {
            throw new RuntimeException("Erro durante a conversão do campo alturaxlargura (Integer).\nConteudo inválido!");
        }

        e.setId((tfId.getText()!=null && !tfId.getText().isEmpty() ? Integer.parseInt(tfId.getText()): null));
        e.setVersion(tfVersion.getText() !=null && !tfVersion.getText().isEmpty() ? Integer.parseInt(tfVersion.getText()) : null);
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        e.setDate(new Date(utilDate.getTime()));

        return e;
    }

    public void setExperiment(Experiment e) {
        resetForm();
        if (e != null) {
            populaTextFields(e);
            bRemove.setVisible(true);
        }
    }
    
    public Integer getExperimentId() {
        try {
            return Integer.parseInt(tfId.getText());
        } catch (Exception nex) {
            return null;
        }
    }

    public Experiment getExperiment() {
        return loadExperimentFromPanel();
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
