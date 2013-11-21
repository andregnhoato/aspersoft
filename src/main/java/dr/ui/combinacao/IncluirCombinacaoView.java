package dr.ui.combinacao;

import dr.controller.PersistenceController;
import dr.dao.BocalDAO;
import dr.dao.BocalDAOImpl;
import dr.dao.QuebraJatoDAO;
import dr.dao.QuebraJatoDAOImpl;
import dr.model.Bocal;
import dr.model.Combinacao;
import dr.model.QuebraJato;
import dr.ui.GridFormBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * add/edit Combinacao
 *
 * @author
 * @Andre
 */
public class IncluirCombinacaoView extends Stage {

    private TextField tfId;
    private TextField tfDescricao;
    private TextField tfPressao;
    private TextField tfBocal;
    private TextField tfQuebraJato;
    private TextField tfDiametroIrrigado;
    private TextField tfGridAltura;
    private TextField tfGridLargura;
    private TextField tfVersion;
    private TextField tfPeq;
    private TextField tfVazao;
    
    private Button bSave;
    private Button bCancel;
    private Button bRemove;
    private Button bBocal;
    private Button bQuebraJato;
    private Bocal bocal;
    private QuebraJato quebraJato;
    PersistenceController pe = new PersistenceController();
    final QuebraJatoDAO qjdao;
    final BocalDAO bdao;

    public IncluirCombinacaoView() {
        qjdao = new QuebraJatoDAOImpl(pe.getPersistenceContext());
        bdao = new BocalDAOImpl(pe.getPersistenceContext());
        setTitle("Incluir Combinação de bocais");
        setWidth(410);
        setHeight(520);
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
        bSave.setId("saveCombinacao");
        bSave.setDefaultButton(true);

        bCancel = new Button("Cancelar");
        bCancel.setId("cancelCombinacao");
        bCancel.setCancelButton(true);

        bRemove = new Button("Excluir");
        bRemove.setId("removeCombinacao");
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

        

        tfBocal = new TextField();
        tfBocal.setPromptText("*Campo obrigatório");
        tfBocal.setMinWidth(180);
        tfBocal.setMaxWidth(180);
        tfBocal.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old, Boolean novo) {
                String entrada = "";
                if (novo) {
                    entrada = tfBocal.getText();
                } else if (!entrada.equals(tfBocal.getText())) {
                    bocal = bdao.getBocalByDescricao(tfBocal.getText()).get(0);
                    tfBocal.setText(bocal.getDescricao());
                }
            }
        });

        tfQuebraJato = new TextField();
        tfQuebraJato.setPromptText("*Campo obrigatório");
        tfQuebraJato.setMinWidth(180);
        tfQuebraJato.setMaxWidth(180);
        tfQuebraJato.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old, Boolean novo) {
                String entrada = "";
                if (novo) {
                    entrada = tfQuebraJato.getText();
                } else if (!entrada.equals(tfQuebraJato.getText())) {
                    quebraJato = qjdao.getQuebraJatoByDescricao(tfQuebraJato.getText()).get(0);
                    tfQuebraJato.setText(quebraJato.getDescricao());
                }
            }
        });
        
        tfPressao = new TextField();
        tfPressao.setPromptText("*mca");
        tfPressao.setMinWidth(180);
        tfPressao.setMaxWidth(180);
        
        tfVazao = new TextField() {
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

        tfVazao.setPromptText("valor em litros");
        tfVazao.setMinWidth(180);
        tfVazao.setMaxWidth(180);

        tfDiametroIrrigado = new TextField() {
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

        tfDiametroIrrigado.setPromptText("valor em metros");
        tfDiametroIrrigado.setMinWidth(180);
        tfDiametroIrrigado.setMaxWidth(180);

        tfPeq = new TextField() {
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

        tfPeq.setPromptText("*mm/h");
        tfPeq.setMinWidth(180);
        tfPeq.setMaxWidth(180);

        tfGridAltura = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                //permitir somente numeros no campo
                if (text.matches("\\d{0,2}")) {
                    super.replaceText(start, end, text);
                    verify();
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("\\d{0,2}")) {
                    super.replaceSelection(text);
                    verify();
                }
            }

            private void verify() {
                if (getText().length() > 2) {
                    setText(getText().substring(0, 2));
                }
            }
        };
        tfGridAltura.setPromptText("metros");
        tfGridAltura.setMinWidth(60);
        tfGridAltura.setMaxWidth(60);

        tfGridLargura = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                //permitir somente numeros no campo
                if (text.matches("\\d{0,2}")) {
                    super.replaceText(start, end, text);
                    verify();
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (text.matches("\\d{0,2}")) {
                    super.replaceSelection(text);
                    verify();
                }
            }

            private void verify() {
                if (getText().length() > 2) {
                    setText(getText().substring(0, 2));
                }
            }
        };
        tfGridLargura.setPromptText("metros");
        tfGridLargura.setMinWidth(60);
        tfGridLargura.setMaxWidth(60);


        tfVersion = new TextField();
        tfVersion.setVisible(false);

        bBocal = new Button("...");
        bBocal.setId("bocalZoom");
        bBocal.getStyleClass().add("buttonGreen");

        bQuebraJato = new Button("...");
        bQuebraJato.setId("quebraZoom");
        bQuebraJato.getStyleClass().add("buttonGreen");




        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Id: "), tfId)
                .addRow(new Label("Descrição: "), tfDescricao)
                .addRow(new Label("Bocal: "), tfBocal, bBocal)
                .addRow(new Label("Quebra Jato: "), tfQuebraJato, bQuebraJato)
                .addRow(new Label("Pressão: "), tfPressao)
                .addRow(new Label("Vazão:"), tfVazao)
                .addRow(new Label("Diâmetro Irrigado: "), tfDiametroIrrigado)
                .addRow(new Label("Dimensão altura:"), tfGridAltura)
                .addRow(new Label("Dimensão largura:"), tfGridLargura)
                .addRow(new Label("Peq:"), tfPeq);

        return grid.build();
    }

    public final void resetForm() {
        tfId.setText("");
        tfDescricao.setText("");
        tfPressao.setText("");
        tfBocal.setText("");
        tfQuebraJato.setText("");
        tfDiametroIrrigado.setText("");
        tfVazao.setText("");
        tfPeq.setText("");
        tfGridAltura.setText("");
        tfGridLargura.setText("");
        tfVersion.setText("");
//        tfGridAltura.setEditable(true);
//        tfGridLargura.setEditable(true);
        
        bRemove.setVisible(false);
    }

    private void populaTextFields(Combinacao c) {
        tfId.setText(c.getId().toString());
        tfDescricao.setText(c.getDescricao());
        tfPressao.setText(c.getPressao()+"");
        tfBocal.setText(c.getBocal().getDescricao());
        tfQuebraJato.setText(c.getQuebraJato().getDescricao());
        tfVazao.setText(c.getVazao() + "");
        tfDiametroIrrigado.setText(c.getDiametroIrrigado() + "");
        tfGridAltura.setText(c.getAltura().toString());
//        tfGridAltura.setEditable(false);
        tfGridLargura.setText(c.getLargura().toString());
//        tfGridLargura.setEditable(false);
        tfPeq.setText(c.getPeq()+"");
        tfVersion.setText(c.getVersion() == null ? "0" : c.getVersion().toString());
        
    }

    private Combinacao loadCombinacaoFromPanel() {
        Combinacao c = new Combinacao();

        if (!tfDescricao.getText().trim().isEmpty()) {
            c.setDescricao(tfDescricao.getText().trim());
        }

        if (!tfPressao.getText().trim().isEmpty()) {
            c.setPressao(Float.parseFloat(tfPressao.getText().trim()));
        }

        if (!tfBocal.getText().trim().isEmpty()) {
            c.setBocal(bocal);
        }

        if (!tfQuebraJato.getText().trim().isEmpty()) {
            c.setQuebraJato(quebraJato);
        }

        if (!tfVazao.getText().trim().isEmpty()) {
            c.setVazao(Float.parseFloat(tfVazao.getText().trim()));
        }

        if (!tfDiametroIrrigado.getText().trim().isEmpty()) {
            c.setDiametroIrrigado(Float.valueOf(tfDiametroIrrigado.getText().trim()));
        }

        try {
            if (!tfGridAltura.getText().trim().isEmpty()) {
                c.setAltura(Float.valueOf(tfGridAltura.getText()));
            }

            if (!tfGridLargura.getText().trim().isEmpty()) {
                c.setLargura(Float.valueOf(tfGridLargura.getText()));
            }

        } catch (NumberFormatException nex) {
            throw new RuntimeException("Erro durante a conversão do campo alturaxlargura (Integer).\nConteudo inválido!");
        }
        
        if (!tfPeq.getText().trim().isEmpty()) {
            c.setPeq(Float.valueOf(tfPeq.getText().trim()));
        }

        c.setId((tfId.getText() != null && !tfId.getText().isEmpty() ? Integer.parseInt(tfId.getText()) : null));
        c.setVersion(tfVersion.getText() != null && !tfVersion.getText().isEmpty() ? Integer.parseInt(tfVersion.getText()) : null);
        c.setBocal(this.bocal);
        c.setQuebraJato(this.quebraJato);

        return c;
    }

    public void setCombinacao(Combinacao c) {
        resetForm();
        if (c != null) {
            populaTextFields(c);
            this.bocal = c.getBocal();
            this.quebraJato = c.getQuebraJato();
            bRemove.setVisible(true);
        }
    }

    public Integer getCombinacaoId() {
        try {
            return Integer.parseInt(tfId.getText());
        } catch (Exception nex) {
            return null;
        }
    }

    public Combinacao getCombinacao() {
        return loadCombinacaoFromPanel();
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

    public Button getQuebraButton() {
        return bQuebraJato;
    }

    public Button getBocalButton() {
        return bBocal;
    }

    public Bocal getBocal() {
        return bocal;
    }

    public void setBocal(Bocal bocal) {
        if (bocal != null) {
            tfBocal.setText(bocal.getDescricao());
            this.bocal = bocal;
        }
    }

    public QuebraJato getQuebraJato() {

        return quebraJato;
    }

    public void setQuebraJato(QuebraJato quebraJato) {
        if (quebraJato != null) {
            tfQuebraJato.setText(quebraJato.getDescricao());
            this.quebraJato = quebraJato;
        }
    }

}
