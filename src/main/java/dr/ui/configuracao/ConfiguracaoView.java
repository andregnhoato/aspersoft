package dr.ui.configuracao;

import dr.model.Configuracao;
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
 * add/edit Configuracao
 *
 * @author
 * @Andre
 */
public class ConfiguracaoView extends Stage {

    private TextField tfId;
    private TextField tfCDColeta;
    private TextField tfCDSobreposicao;
    private TextField tfCDUniformidade;
    private TextField tfCDEstatistica;
    private TextField tfVersion;
    private Button bSave;
    private Button bCancel;
//    private Button bRemove;

    public ConfiguracaoView() {
        setTitle("Itens de Configuração");
        setWidth(350);
        setHeight(230);
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
        bSave.setId("saveConfiguracao");
        bSave.setDefaultButton(true);

        bCancel = new Button("Cancelar");
        bCancel.setId("cancelConfiguracao");
        bCancel.setCancelButton(true);

        HBox box = new HBox();
        box.getChildren().addAll(bSave, bCancel);
        box.getStyleClass().add("buttonBar");

        return box;
    }

    private GridPane buildInputs() {

        tfId = new TextField();
        tfId.setPromptText("");
        tfId.setId("0");
        tfId.setEditable(false);
        tfId.setMinWidth(50);
        tfId.setMaxWidth(50);

        tfCDColeta = new TextField(){
            @Override
            public void replaceText(int start, int end, String text) {
                //permitir somente numeros no campo
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceSelection(text);
                }
            }
        };
        
//        tfCDColeta.setPromptText("*Campo obrigatório");
        tfCDColeta.setMinWidth(50);
        tfCDColeta.setMaxWidth(50);

        tfCDEstatistica = new TextField(){
            @Override
            public void replaceText(int start, int end, String text) {
                //permitir somente numeros no campo
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceSelection(text);
                }
            }
        };
//        tfCDEstatistica.setPromptText("*kgf/cm3");
        tfCDEstatistica.setMinWidth(50);
        tfCDEstatistica.setMaxWidth(50);

        tfCDSobreposicao = new TextField(){
            @Override
            public void replaceText(int start, int end, String text) {
                //permitir somente numeros no campo
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceSelection(text);
                }
            }
        };
//        tfCDSobreposicao.setPromptText("*Campo obrigatório");
        tfCDSobreposicao.setMinWidth(50);
        tfCDSobreposicao.setMaxWidth(50);

        tfCDUniformidade = new TextField(){
            @Override
            public void replaceText(int start, int end, String text) {
                //permitir somente numeros no campo
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("^\\d{0,3}(\\.\\d{0,2})?$")) {
                    super.replaceSelection(text);
                }
            }
        };
//        tfCDUniformidade.setPromptText("*Campo obrigatório");
        tfCDUniformidade.setMinWidth(50);
        tfCDUniformidade.setMaxWidth(50);
        
        tfVersion = new TextField();
        tfVersion.setVisible(false);

        

        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Id: "), tfId)
                .addRow(new Label("Casas decimais tela de Coleta: "), tfCDColeta)
                .addRow(new Label("Casas decimais grid de Sobreposição: "), tfCDSobreposicao)
                .addRow(new Label("Casas decimais dados estatísticos: "), tfCDEstatistica)
                .addRow(new Label("Casas decimais dados de uniformidades:  "), tfCDUniformidade);

        return grid.build();
    }

    public final void resetForm() {
        tfId.setText("");
        tfCDColeta.setText("");
        tfCDEstatistica.setText("");
        tfCDSobreposicao.setText("");
        tfCDUniformidade.setText("");
        tfVersion.setText("");
        
        
    }

    private void populaTextFields(Configuracao c) {
        tfId.setText(c.getId().toString());
        tfCDColeta.setText(c.getCasasDecimaisColeta()+"");
        tfCDEstatistica.setText(c.getCasasDecimaisDadosEstatisticos()+"");
        tfCDSobreposicao.setText(c.getCasasDecimaisSobreposicao()+"");
        tfCDUniformidade.setText(c.getCasasDecimaisUniformidade()+"");
        tfVersion.setText(c.getVersion() == null ? "0" : c.getVersion().toString());
        
    }

    private Configuracao loadConfiguracaoFromPanel() {
        Configuracao c = new Configuracao();
        
        if(!tfId.getText().trim().isEmpty()){
            c.setId(Integer.parseInt(tfId.getText()));
        }
        
        if (!tfCDColeta.getText().trim().isEmpty()) {
            c.setCasasDecimaisColeta(Integer.parseInt(tfCDColeta.getText().trim()));
        }

        if (!tfCDEstatistica.getText().trim().isEmpty()) {
            c.setCasasDecimaisDadosEstatisticos(Integer.parseInt(tfCDEstatistica.getText().trim()));
        }

        if (!tfCDSobreposicao.getText().trim().isEmpty()) {
            c.setCasasDecimaisSobreposicao(Integer.parseInt(tfCDSobreposicao.getText().trim()));
        }

        if (!tfCDUniformidade.getText().trim().isEmpty()) {
            c.setCasasDecimaisUniformidade(Integer.parseInt(tfCDUniformidade.getText().trim()));
        }
        c.setVersion(tfVersion.getText() != null && !tfVersion.getText().isEmpty() ? Integer.parseInt(tfVersion.getText()) : null);

        return c;
    }

    public void setConfiguracao(Configuracao e) {
        resetForm();
        if (e != null) {
            populaTextFields(e);
         
        }
    }

    public Integer getConfiguracaoId() {
        try {
            return Integer.parseInt(tfId.getText());
        } catch (Exception nex) {
            return null;
        }
    }

    public Configuracao getConfiguracao() {
        return loadConfiguracaoFromPanel();
    }

    public Button getSaveButton() {
        return bSave;
    }

    public Button getCancelButton() {
        return bCancel;
    }
}
