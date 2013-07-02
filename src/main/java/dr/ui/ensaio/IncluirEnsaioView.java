package dr.ui.ensaio;

import dr.model.Ensaio;
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
 * add/edit Ensaio bean
 * 
 * @author @Andre
 */
public class IncluirEnsaioView extends Stage {

    private TextField tfId;
    private TextField tfPressao;
    private TextField tfBocal;
    private TextField tfQuebraJato;
    private TextField tfInicio;
    private TextField tfDuracao;
    private TextField tfVelocidadeVento;
    private TextField tfDirecaoVento;
    private TextField tfGridAltura;
    private TextField tfGridLargura;
    private TextField tfVersion;
    private Button bSave;
    private Button bCancel;
    private Button bRemove;

    public IncluirEnsaioView() {
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
        bSave.setId("saveEnsaio");
        bSave.setDefaultButton(true);

        bCancel = new Button("Cancelar");
        bCancel.setId("cancelEnsaio");
        bCancel.setCancelButton(true);
        
        bRemove = new Button("Excluir");
        bRemove.setId("removeEnsaio");
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

        tfPressao = new TextField();
        tfPressao.setPromptText("*Campo obrigatório");
        tfPressao.setMinWidth(180);
        tfPressao.setMaxWidth(180);

        tfBocal = new TextField();
        tfBocal.setPromptText("*Campo obrigatório");
        tfBocal.setMinWidth(180);
        tfBocal.setMaxWidth(180);

        tfQuebraJato = new TextField();
        tfQuebraJato.setPromptText("*Campo obrigatório");
        tfQuebraJato.setMinWidth(180);
        tfQuebraJato.setMaxWidth(180);

        tfInicio = new TextField();
        tfInicio.setPromptText("*Campo obrigatório");
        tfInicio.setMinWidth(180);
        tfInicio.setMaxWidth(180);
        
        tfDuracao = new TextField();
        tfDuracao.setPromptText("*Campo obrigatório");
        tfDuracao.setMinWidth(180);
        tfDuracao.setMaxWidth(180);
        
        tfVelocidadeVento = new TextField();
        tfVelocidadeVento.setMinWidth(180);
        tfVelocidadeVento.setMaxWidth(180);
        
        tfDirecaoVento = new TextField();
        tfDirecaoVento.setMinWidth(180);
        tfDirecaoVento.setMaxWidth(180);
        
        tfInicio = new TextField();
        tfInicio.setPromptText("*Campo obrigatório");
        tfInicio.setMinWidth(180);
        tfInicio.setMaxWidth(180);
        
        tfGridAltura = new TextField();
        tfGridAltura.setPromptText("*");
        tfGridAltura.setMinWidth(40);
        tfGridAltura.setMaxWidth(40);
        
        tfGridLargura = new TextField();
        tfGridLargura.setPromptText("*");
        tfGridLargura.setMinWidth(40);
        tfGridLargura.setMaxWidth(40);
        
        

        tfVersion = new TextField();
        tfVersion.setVisible(false);
        
        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Id: "), tfId)
                .addRow(new Label("Pressão: "), tfPressao)
                .addRow(new Label("Bocal: "), tfBocal)
                .addRow(new Label("Quebra Jato: "), tfQuebraJato)
                .addRow(new Label("Inicio: "), tfInicio)
                .addRow(new Label("Duração:"), tfDuracao)
                .addRow(new Label("Velocidade Vento:"), tfVelocidadeVento)
                .addRow(new Label("Direção Vento:"), tfDirecaoVento)
                .addRow(new Label("Dimensão altura x largura:"), tfGridAltura, new Label("x"),tfGridLargura);
        
        return grid.build();
    }

    public final void resetForm() {
        tfId.setText("");
        tfPressao.setText("");
        tfBocal.setText("");
        tfQuebraJato.setText("");
        tfInicio.setText("");
        tfDuracao.setText("");
        tfVelocidadeVento.setText("");
        tfDirecaoVento.setText("");
        tfGridAltura.setText("");
        tfGridLargura.setText("");
        tfVersion.setText("");
        bRemove.setVisible(false);
    }

    private void populaTextFields(Ensaio e) {
        tfId.setText(e.getId().toString());
        tfPressao.setText(e.getPressao());
        tfBocal.setText(e.getBocal());
        tfQuebraJato.setText(e.getQuebraJato());
        tfInicio.setText(e.getInicio());
        tfDuracao.setText(e.getDuracao());
        tfVelocidadeVento.setText(e.getVelocidadeVento()+"");
        tfDirecaoVento.setText(e.getDirecaoVento());
        tfGridAltura.setText(e.getGridAltura().toString());
        tfGridLargura.setText(e.getGridLargura().toString());
        tfVersion.setText(e.getVersion() == null ? "0" : e.getVersion().toString());
    }


    private Ensaio loadEnsaioFromPanel() {
        Ensaio e = new Ensaio();
        if (!tfPressao.getText().trim().isEmpty())
            e.setPressao(tfPressao.getText().trim());
        
        if (!tfBocal.getText().trim().isEmpty())
            e.setBocal(tfBocal.getText().trim());
        
        if (!tfQuebraJato.getText().trim().isEmpty())
            e.setQuebraJato(tfQuebraJato.getText().trim());
        
        if (!tfInicio.getText().trim().isEmpty())
            e.setInicio(tfInicio.getText().trim());
        
        if (!tfDuracao.getText().trim().isEmpty())
            e.setDuracao(tfDuracao.getText().trim());
        
        //TODO ajustar o parse de float
        if (!tfVelocidadeVento.getText().trim().isEmpty())
            e.setVelocidadeVento(Float.parseFloat(tfVelocidadeVento.getText().trim()));
        
        if (!tfDirecaoVento.getText().trim().isEmpty())
            e.setDirecaoVento(tfDirecaoVento.getText().trim());
        
        try {
            if (!tfGridAltura.getText().trim().isEmpty())
                e.setGridAltura(Integer.valueOf(tfGridAltura.getText()));

            if (!tfGridLargura.getText().trim().isEmpty())
                e.setGridLargura(Integer.valueOf(tfGridLargura.getText()));
            
        } catch (NumberFormatException nex) {
            throw new RuntimeException("Erro durante a conversão do campo alturaxlargura (Integer).\nConteudo inválido!");
        }

        e.setId((tfId.getText()!=null && !tfId.getText().isEmpty() ? Integer.parseInt(tfId.getText()): null));
        e.setVersion(tfVersion.getText() !=null && !tfVersion.getText().isEmpty() ? Integer.parseInt(tfVersion.getText()) : null);
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        e.setData(new Date(utilDate.getTime()));

        return e;
    }

    public void setEnsaio(Ensaio e) {
        resetForm();
        if (e != null) {
            populaTextFields(e);
            bRemove.setVisible(true);
        }
    }
    
    public Integer getEnsaioId() {
        try {
            return Integer.parseInt(tfId.getText());
        } catch (Exception nex) {
            return null;
        }
    }

    public Ensaio getEnsaio() {
        return loadEnsaioFromPanel();
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
