package dr.ui.combinacao;

import dr.ui.*;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
 * Tela utilizada para realizar a pesquisa de <code>Combinacao</code> 
 * com filtro no campo <code>nome</code>. 
 * 
 * @author @Andre
 */
public class BuscarCombinacaoView extends Stage {

    private TextField tfNome;
    private DatePicker dpDataInicio;
    private DatePicker dpDataFim;
    private Button bBuscar;
    private Button bCancelar;

    public BuscarCombinacaoView() {
        setTitle("Buscar Combinações de bocais");
        setWidth(250);
        setHeight(150);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);

        inicializaComponentes();
    }

    private void inicializaComponentes() {
        GridPane inputs = buildInputs();
        HBox buttons = buildBotoes();
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(inputs, buttons);
        Scene scene = new Scene(new Group(vbox));
        scene.getStylesheets().add("style.css");
        this.setScene(scene);
    }

    private HBox buildBotoes() {
        bBuscar = new Button("Buscar");
        bBuscar.setId("buscarCombinacao");
        bBuscar.setDefaultButton(true);
        
        bCancelar = new Button("Cancelar");
        bCancelar.setId("cancelarBuscaCombinacao");
        bCancelar.setCancelButton(true);
        
        HBox box = new HBox();
        box.getChildren().addAll(bBuscar, bCancelar);
        box.getStyleClass().add("buttonBar");
        
        return box;
    }
    
    private GridPane buildInputs() {
        tfNome = new TextField();
        tfNome.setPromptText("filtro descrição");
        tfNome.setMinWidth(180);
        tfNome.setMaxWidth(180);
        
        
        dpDataInicio = new DatePicker(new Locale("pt", "BR"));
        dpDataInicio.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        dpDataInicio.getCalendarView().todayButtonTextProperty().set("Hoje");
        dpDataInicio.getCalendarView().setShowWeeks(true);
        dpDataInicio.getStylesheets().add("datePicker.css");
        dpDataInicio.setPromptText("filtro data início");
        
        dpDataFim = new DatePicker(new Locale("pt", "BR"));
        dpDataFim.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        dpDataFim.getCalendarView().todayButtonTextProperty().set("Hoje");
        dpDataFim.getCalendarView().setShowWeeks(true);
        dpDataFim.getStylesheets().add("datePicker.css");
        dpDataFim.setPromptText("filtro data final");
        
        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Combinações: "), tfNome);
//                .addRow(new Label("Data Início:"), dpDataInicio)
//                .addRow(new Label("Data Fim:"), dpDataFim);
        
        return grid.build();
    }
    
    public void resetForm() {
        tfNome.setText("");
        
    }

    public Button getBuscarButton() {
        return bBuscar;
    }
    
    public Button getCancelarButton() {
        return bCancelar;
    }

    public String getText() {
        return tfNome.getText();
    }
    
    public Date getDataInicio(){
        return dpDataInicio.getSelectedDate();
    }
    
    public Date getDataFim(){
        return dpDataFim.getSelectedDate();
    }
}
