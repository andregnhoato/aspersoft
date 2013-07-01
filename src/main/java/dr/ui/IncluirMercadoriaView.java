package dr.ui;

import dr.model.Mercadoria;
import java.text.ParseException;
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
 * Tela para incluir/editar o registro da <code>Mercadoria</code>.
 * 
 * <p>
 *  Essa tela trabalha em modo inclusão ou edicão de <code>Mercadoria</code>.
 *  Em edicão é possível acionar a funcionalidade para remover a <code>Mercadoria</code>.
 * </p>
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaView extends Stage {

    private TextField tfId;
    private TextField tfNome;
    private TextField tfQuantidade;
    private TextField tfDescricao;
    private TextField tfPreco;
    private TextField tfVersion;
    private Button bSalvar;
    private Button bCancelar;
    private Button bExcluir;

    public IncluirMercadoriaView() {
        setTitle("Incluir Mercadoria");
        setWidth(300);
        setHeight(230);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        
        inicializaComponentes();
    }

    private void inicializaComponentes() {
        VBox box = new VBox();
        box.getChildren().addAll(buildInputs(), buildBotoes());
        
        Scene scene = new Scene(new Group(box));
        scene.getStylesheets().add("style.css");
        this.setScene(scene);
        this.resetForm();
    }

    private HBox buildBotoes() {
        bSalvar = new Button("Salvar");
        bSalvar.setId("salvarMercadoria");
        bSalvar.setDefaultButton(true);

        bCancelar = new Button("Cancelar");
        bCancelar.setId("cancelarSalvarMercadoria");
        bCancelar.setCancelButton(true);
        
        bExcluir = new Button("Excluir");
        bExcluir.setId("excluirMercadoria");
        bExcluir.getStyleClass().add("buttonDanger");

        HBox box = new HBox();
        box.getChildren().addAll(bSalvar, bCancelar, bExcluir);
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

        tfNome = new TextField();
        tfNome.setPromptText("*Campo obrigatório");
        tfNome.setMinWidth(180);
        tfNome.setMaxWidth(180);

        tfDescricao = new TextField();
        tfDescricao.setPromptText("");
        tfDescricao.setMinWidth(180);
        tfDescricao.setMaxWidth(180);

        tfQuantidade = new TextField();
        tfQuantidade.setPromptText("*Campo obrigatório");
        tfQuantidade.setMinWidth(90);
        tfQuantidade.setMaxWidth(90);

        tfPreco = new TextField();
        tfPreco.setPromptText("*Campo obrigatório");
        tfPreco.setMinWidth(90);
        tfPreco.setMaxWidth(90);

        tfVersion = new TextField();
        tfVersion.setVisible(false);
        
        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Id: "), tfId)
                .addRow(new Label("Nome: "), tfNome)
                .addRow(new Label("Descrição: "), tfDescricao)
                .addRow(new Label("Quantidade: "), tfQuantidade)
                .addRow(new Label("Preço: "), tfPreco);
        
        return grid.build();
    }

    public final void resetForm() {
        tfId.setText("");
        tfNome.setText("");
        tfDescricao.setText("");
        tfPreco.setText("");
        tfQuantidade.setText("");
        tfVersion.setText("");
        bExcluir.setVisible(false);
    }

    private void populaTextFields(Mercadoria m) {
        tfId.setText(m.getId().toString());
        tfNome.setText(m.getNome());
        tfDescricao.setText(m.getDescricao() == null ? "" : m.getDescricao());
        tfQuantidade.setText(m.getQuantidade().toString());
        tfPreco.setText(m.getPrecoFormatado());
        tfVersion.setText(m.getVersion() == null ? "0" : m.getVersion().toString());
    }


    private Mercadoria loadMercadoriaFromPanel() {
        String nome = null;
        if (!tfNome.getText().trim().isEmpty()) {
            nome = tfNome.getText().trim();
        }
        
        String descricao = null;
        if (!tfDescricao.getText().trim().isEmpty()) {
            descricao = tfDescricao.getText().trim();
        }

        Integer quantidade = null;
        try {
            if (!tfQuantidade.getText().trim().isEmpty()) {
                quantidade = Integer.valueOf(tfQuantidade.getText());
            }
        } catch (NumberFormatException nex) {
            throw new RuntimeException("Erro durante a conversão do campo quantidade (Integer).\nConteudo inválido!");
        }

        Double preco = null;
        try {
            if (!tfPreco.getText().trim().isEmpty()) {
                preco = Mercadoria.formatStringToPreco(tfPreco.getText());
            }
        } catch (ParseException nex) {
            throw new RuntimeException("Erro durante a conversão do campo preço (Double).\nConteudo inválido!");
        }

        Integer id = null;
        try {
            id = Integer.parseInt(tfId.getText());
        } catch (Exception nex) {}

        Integer version = null;
        try {
            version = Integer.parseInt(tfVersion.getText());
        } catch (Exception nex) {}

        return new Mercadoria(id, nome, descricao, quantidade, preco, version);
    }

    public void setMercadoria(Mercadoria m) {
        resetForm();
        if (m != null) {
            populaTextFields(m);
            bExcluir.setVisible(true);
        }
    }
    
    public Integer getMercadoriaId() {
        try {
            return Integer.parseInt(tfId.getText());
        } catch (Exception nex) {
            return null;
        }
    }

    public Mercadoria getMercadoria() {
        return loadMercadoriaFromPanel();
    }

    public Button getSalvarButton() {
        return bSalvar;
    }

    public Button getCancelarButton() {
        return bCancelar;
    }
    
    public Button getExcluirButton() {
        return bExcluir;
    }
}
