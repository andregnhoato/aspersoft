package dr.ui.ensaio;

import dr.controller.PersistenceController;
import dr.dao.BocalDAO;
import dr.dao.BocalDAOImpl;
import dr.dao.QuebraJatoDAO;
import dr.dao.QuebraJatoDAOImpl;
import dr.model.Bocal;
import dr.model.Ensaio;
import dr.model.QuebraJato;
import dr.ui.Dialog;
import dr.ui.GridFormBuilder;
import dr.util.WindUtil;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * add/edit Ensaio
 *
 * @author
 * @Andre
 */
public class IncluirEnsaioView extends Stage {

    private TextField tfId;
    private TextField tfDescricao;
    private TextField tfPressao;
    private TextField tfBocal;
    private TextField tfQuebraJato;
    private TextField tfEspacamentoPluviometro;
    private TextField tfInicio;
    private TextField tfDuracao;
    private TextField tfVelocidadeVento;
    private ComboBox cbDirecaoVento;
    private TextField tfDirecaoVentoGraus;
    private TextField tfGridAltura;
    private TextField tfGridLargura;
    private TextField tfVersion;
    private TextField tfEvaporacao;
    private TextField tfVazao;
    private CheckBox cbColetaPorHora;
    private DatePicker dpData;
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

    public IncluirEnsaioView() {
        qjdao = new QuebraJatoDAOImpl(pe.getPersistenceContext());
        bdao = new BocalDAOImpl(pe.getPersistenceContext());
        setTitle("Incluir Ensaio");
        setWidth(410);
        setHeight(540);
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

        tfDescricao = new TextField();
        tfDescricao.setPromptText("*Campo obrigatório");
        tfDescricao.setMinWidth(180);
        tfDescricao.setMaxWidth(180);

        tfPressao = new TextField();
        tfPressao.setPromptText("*kgf/cm3");
        tfPressao.setMinWidth(180);
        tfPressao.setMaxWidth(180);

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
                    try {
                        bocal = bdao.getBocalByDescricao(tfBocal.getText()).get(0);
                        tfBocal.setText(bocal.getDescricao());
                    } catch (IndexOutOfBoundsException e) {
                        bocal = null;
                        tfBocal.setText("");
                    }
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
                    try {
                        quebraJato = qjdao.getQuebraJatoByDescricao(tfQuebraJato.getText()).get(0);
                        tfQuebraJato.setText(quebraJato.getDescricao());
                    } catch (IndexOutOfBoundsException e) {
                        quebraJato = null;
                        tfQuebraJato.setText("");
                    }
                }
            }
        });

        tfEspacamentoPluviometro = new TextField() {
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

        tfEspacamentoPluviometro.setPromptText("valor em metros");
        tfEspacamentoPluviometro.setMinWidth(180);
        tfEspacamentoPluviometro.setMaxWidth(180);

        tfInicio = new TextField();
        tfInicio.setPromptText("*Campo obrigatório");
        tfInicio.setMinWidth(180);
        tfInicio.setMaxWidth(180);

        tfDuracao = new TextField() {
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
        
        tfDuracao.setPromptText("*Campo obrigatório, ex 60");
        tfDuracao.setMinWidth(180);
        tfDuracao.setMaxWidth(180);

        tfVelocidadeVento = new TextField() {
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
        tfVelocidadeVento.setMinWidth(180);
        tfVelocidadeVento.setPromptText("m/s");
        tfVelocidadeVento.setMaxWidth(180);

        tfDirecaoVentoGraus = new TextField() {
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
        tfDirecaoVentoGraus.setMinWidth(180);
        tfDirecaoVentoGraus.setPromptText("graus em relação N");
        tfDirecaoVentoGraus.setMaxWidth(180);
        tfDirecaoVentoGraus.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old, Boolean novo) {
                String entrada = "";
                if (novo) {
                    entrada = tfDirecaoVentoGraus.getText();
                } else if (!entrada.equals(tfDirecaoVentoGraus.getText())) {
                    float graus = Float.parseFloat(tfDirecaoVentoGraus.getText());
                    if (graus > 360) {
                        graus = graus % 360;
                        tfDirecaoVentoGraus.setText(graus + "");
                    }
                    cbDirecaoVento.setValue(WindUtil.getWindByDegress(graus));
                }
            }
        });



        cbDirecaoVento = new ComboBox(getDirecoes());
        cbDirecaoVento.setMinWidth(180);
        cbDirecaoVento.setMaxWidth(180);
        cbDirecaoVento.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean novo) {
                String entrada = "";
                if (novo) {
                    entrada = (cbDirecaoVento.getValue() != null ? cbDirecaoVento.getValue().toString() : "");
                } else if (!entrada.equals((cbDirecaoVento.getValue() != null ? cbDirecaoVento.getValue().toString() : ""))) {
                    tfDirecaoVentoGraus.setText(WindUtil.getWindByText(WindUtil.WindDirection.valueOf(cbDirecaoVento.getValue().toString())) + "");
                }
            }
        });

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

        tfEvaporacao = new TextField() {
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

        tfEvaporacao.setPromptText("valor em milimetros");
        tfEvaporacao.setMinWidth(180);
        tfEvaporacao.setMaxWidth(180);

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

        //Issue 08: adição do DatePicker no cadastro de ensaio
        dpData = new DatePicker(new Locale("pt", "BR"));
        dpData.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        dpData.getCalendarView().todayButtonTextProperty().set("Hoje");
        dpData.getCalendarView().setShowWeeks(true);
        dpData.getStylesheets().add("datePicker.css");


        bBocal = new Button("...");
        bBocal.setId("bocalZoom");
        bBocal.getStyleClass().add("buttonGreen");

        bQuebraJato = new Button("...");
        bQuebraJato.setId("quebraZoom");
        bQuebraJato.getStyleClass().add("buttonGreen");
        
        cbColetaPorHora = new CheckBox("Coletas por hora");



        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Id: "), tfId)
                .addRow(new Label("Descrição: "), tfDescricao)
                .addRow(new Label("Pressão: "), tfPressao)
                .addRow(new Label("Bocal: "), tfBocal, bBocal)
                .addRow(new Label("Quebra Jato: "), tfQuebraJato, bQuebraJato)
                .addRow(new Label("Inicio: "), tfInicio)
                .addRow(new Label("Data:"), dpData)
                .addRow(new Label("Duração em minutos:"), tfDuracao)
                .addRow(new Label("Velocidade Vento:"), tfVelocidadeVento)
                .addRow(new Label("Direção Vento:"), cbDirecaoVento)
                .addRow(new Label("Direção Vento em Graus:"), tfDirecaoVentoGraus)
                .addRow(new Label("Vazão:"), tfVazao)
                .addRow(new Label("Evaporação:"), tfEvaporacao)
                .addRow(new Label("Espaço entre Pluviometros:"), tfEspacamentoPluviometro)
                .addRow(new Label("Dimensão altura:"), tfGridAltura)
                .addRow(new Label("Dimensão largura:"), tfGridLargura)
                .addRow(new Label(), cbColetaPorHora);

        return grid.build();
    }

    public final void resetForm() {
        tfId.setText("");
        tfDescricao.setText("");
        tfPressao.setText("");
        tfBocal.setText("");
        tfQuebraJato.setText("");
        tfEspacamentoPluviometro.setText("");
        tfInicio.setText("");
        tfDuracao.setText("");
        tfVelocidadeVento.setText("");
        cbDirecaoVento.setValue(null);
        tfDirecaoVentoGraus.setText("");
        tfVazao.setText("");
        tfEvaporacao.setText("");
        tfGridAltura.setText("");
        tfGridLargura.setText("");
        tfVersion.setText("");
        tfGridAltura.setEditable(true);
        tfGridLargura.setEditable(true);
        tfEspacamentoPluviometro.setEditable(true);
        cbColetaPorHora.setSelected(false);
        bRemove.setVisible(false);
    }

    private void populaTextFields(Ensaio e) {
        tfId.setText(e.getId().toString());
        tfDescricao.setText(e.getDescricao());
        tfPressao.setText(e.getPressao());
        tfBocal.setText(e.getBocal().getDescricao());
        tfQuebraJato.setText(e.getQuebraJato().getDescricao());
        tfEspacamentoPluviometro.setText(e.getEspacamentoPluviometro() + "");
        tfEspacamentoPluviometro.setEditable(false);
        tfInicio.setText(e.getInicio());
        tfDuracao.setText(e.getDuracao()+"");
        tfVelocidadeVento.setText(e.getVelocidadeVento() + "");
        cbDirecaoVento.setValue(WindUtil.getWindByDegress(e.getDirecaoVentoGraus()));
        tfDirecaoVentoGraus.setText(e.getDirecaoVentoGraus() + "");
        tfVazao.setText(e.getVazao() + "");
        tfEvaporacao.setText(e.getEvaporacao() + "");
        tfGridAltura.setText(e.getGridAltura().toString());
        tfGridAltura.setEditable(false);
        tfGridLargura.setText(e.getGridLargura().toString());
        tfGridLargura.setEditable(false);
        tfVersion.setText(e.getVersion() == null ? "0" : e.getVersion().toString());
        dpData.setSelectedDate(e.getData());
        cbColetaPorHora.setSelected(e.getColetaHora());
         
        


    }

    private Ensaio loadEnsaioFromPanel() {
        Ensaio e = new Ensaio();

        if (!tfDescricao.getText().trim().isEmpty()) {
            e.setDescricao(tfDescricao.getText().trim());
        }

        if (!tfPressao.getText().trim().isEmpty()) {
            e.setPressao(tfPressao.getText().trim());
        }

        if (!tfBocal.getText().trim().isEmpty()) {
            e.setBocal(bocal);
        }

        if (!tfQuebraJato.getText().trim().isEmpty()) {
            e.setQuebraJato(quebraJato);
        }

        if (!tfInicio.getText().trim().isEmpty()) {
            e.setInicio(tfInicio.getText().trim());
        }

        if (!tfDuracao.getText().trim().isEmpty()) {
            e.setDuracao(Integer.parseInt(tfDuracao.getText()));
        }

        if (!tfVazao.getText().trim().isEmpty()) {
            e.setVazao(Float.parseFloat(tfVazao.getText().trim()));
        }

        if (!tfEvaporacao.getText().trim().isEmpty()) {
            e.setEvaporacao(Float.parseFloat(tfEvaporacao.getText().trim()));
        }


        if (!tfEspacamentoPluviometro.getText().trim().isEmpty()) {
            e.setEspacamentoPluviometro(Float.parseFloat(tfEspacamentoPluviometro.getText().trim()));
        }

        if (!tfVelocidadeVento.getText().trim().isEmpty()) {
            e.setVelocidadeVento(Float.parseFloat(tfVelocidadeVento.getText().trim()));
        }

        if (!tfDirecaoVentoGraus.getText().trim().isEmpty()) {
            e.setDirecaoVentoGraus(Float.parseFloat(tfDirecaoVentoGraus.getText().trim()));
        }

        try {
            if (!tfGridAltura.getText().trim().isEmpty()) {
                e.setGridAltura(Integer.valueOf(tfGridAltura.getText()));
            }

            if (!tfGridLargura.getText().trim().isEmpty()) {
                e.setGridLargura(Integer.valueOf(tfGridLargura.getText()));
            }

        } catch (NumberFormatException nex) {
            throw new RuntimeException("Erro durante a conversão do campo alturaxlargura (Integer).\nConteudo inválido!");
        }

        e.setId((tfId.getText() != null && !tfId.getText().isEmpty() ? Integer.parseInt(tfId.getText()) : null));
        e.setVersion(tfVersion.getText() != null && !tfVersion.getText().isEmpty() ? Integer.parseInt(tfVersion.getText()) : null);
        e.setData(dpData.getSelectedDate());
        e.setBocal(this.bocal);
        e.setQuebraJato(this.quebraJato);
        e.setColetaHora(cbColetaPorHora.isSelected());

        return e;
    }

    public void setEnsaio(Ensaio e) {
        resetForm();
        if (e != null) {
            populaTextFields(e);
            this.bocal = e.getBocal();
            this.quebraJato = e.getQuebraJato();
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

    private ObservableList getDirecoes() {
        ObservableList<String> direcoes = FXCollections.observableArrayList(
                "N",
                "NNE",
                "NE",
                "ENE",
                "E",
                "ESE",
                "SE",
                "SSE",
                "S",
                "SSW",
                "SW",
                "WSW",
                "W",
                "WNW",
                "NW",
                "NNW");

        return direcoes;
    }
}
