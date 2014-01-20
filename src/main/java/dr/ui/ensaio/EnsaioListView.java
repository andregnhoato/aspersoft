package dr.ui.ensaio;

import dr.model.Bocal;
import dr.model.Ensaio;
import dr.model.QuebraJato;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as ensaios cadastradas.
 *
 * <p>A partir dessa tela é possível criar/editar ou pesquisar ensaio.</p>
 *
 * @author
 * @Andre
 */
public class EnsaioListView extends Stage {

    private EnsaioTable table;
    private Label processando;
    private Button bNewEnsaio;
    private Button bRefreshList;
    private Button bFindEnsaio;
    private Button bColeta;
    private Button bUniformidade;
    private Button bSimulado;
    private TextField tfBocal;
    private Button bBocal;
    private TextField tfQuebraJato;
    private Button bQuebraJato;
    private TextField tfDescricao;
    private Button bPesquisar;
    private ProgressBar pi;
    private Bocal bocal;
    private QuebraJato quebraJato;

    public EnsaioListView() {
        setTitle("Ensaios");

        setResizable(true);

        initModality(Modality.APPLICATION_MODAL);

        inicializaComponentes();

        
        GridPane geral = new GridPane();
//        geral.autosize();
//        geral.setHgap(10);
//        geral.setVgap(10);
//        HBox boxButtons = getButtonsBox();
        GridPane grid = new GridPane();
        TitledPane td = new TitledPane();
        td.setText("Filtros");
        grid.add(getFilterBox(), 0, 0);
        td.setContent(grid);
        td.setCollapsible(false);
        td.setMinHeight(50);
//        td.setMaxWidth(900);
        geral.add(td,0,0);
//        geral.setTop(td);
        
        
//        grid = new GridPane();
//        grid.setVgap(5);
//        grid.setHgap(5);
//        grid.setPadding(new Insets(5, 5, 5, 5));
//        table.setMinHeight(300);
//        table.setMaxHeight(350);
        geral.add(table, 0,1);
        geral.add(getButtonsBox(),0,2);

//        td = new TitledPane();
//        td.setText("Ensaios");
//        td.setMaxHeight(500);
//        td.setMaxWidth(900);
//        td.setContent(grid);
//        td.setCollapsible(false);
        
//        HBox boxButtons = getButtonsBox();
       
//        panel.getChildren().addAll(boxButtons, table);
//        HBox boxFilter = getFilterBox();
       
//        geral.add(table, 0, 1);
//        geral.add(getButtonsBox(), 0,2);
//        geral.setCenter(grid);
//        geral.setBottom(getButtonsBox());
//        geral.setMaxHeight(700);
//        geral.setMinHeight(700);
//        geral.setMaxWidth(900);
//        geral.setMinWidth(900);
//        geral.setBottom(getButtonsBox());
        
        setHeight(700);
        setWidth(980);
        Scene scene = new Scene(geral);
        
        scene.getStylesheets().add("style.css");
        this.setScene(scene);



    }

    private void inicializaComponentes() {
        table = new EnsaioTable();

        bNewEnsaio = new Button("Novo");
        bNewEnsaio.getStyleClass().add("buttonLarge");
        bNewEnsaio.setId("addEnsaio");
        bFindEnsaio = new Button("Buscar");
        bFindEnsaio.getStyleClass().add("buttonLarge");
        bFindEnsaio.setId("findEnsaio");
        bRefreshList = new Button("Atualizar");
        bRefreshList.getStyleClass().add("buttonLarge");
        bRefreshList.setId("refreshList");
        bColeta = new Button("Coleta");
        bColeta.getStyleClass().add("buttonLarge");
        bColeta.setId("addColeta");
        bUniformidade = new Button("Análise");
        bUniformidade.getStyleClass().add("buttonLarge");
        bUniformidade.setId("showUniformidade");
        bSimulado = new Button("Simulação");
        bSimulado.getStyleClass().add("buttonLarge");
        bSimulado.setId("simulado");
        processando = new Label("Processando simulação");
        processando.setVisible(false);
        pi = new ProgressBar();
        pi.setVisible(false);
        
        bBocal = new Button("...");
        bBocal.getStyleClass().add("buttonGreen");
        bBocal.setId("zoomBocal");
        
        bPesquisar = new Button("Aplicar");
        bPesquisar.getStyleClass().add("buttonGreen");
        bPesquisar.setId("pesquisar");
        
        
        bQuebraJato = new Button("...");
        bQuebraJato.getStyleClass().add("buttonGreen");
        bQuebraJato.setId("zoomQuebra");
        
        tfBocal = new TextField();
//        tfBocal.setPromptText("*Campo obrigatório");
        tfBocal.setMinWidth(180);
        tfBocal.setMaxWidth(180);
        tfBocal.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old, Boolean novo) {
//                String entrada = "";
                if (tfBocal.getText().isEmpty()) {
                    bocal = null;
                } 
//                else if (!entrada.equals(tfBocal.getText())) {
//                    try {
////                        bocal = bdao.getBocalByDescricao(tfBocal.getText()).get(0);
////                        tfBocal.setText(bocal.getDescricao());
//                    } catch (IndexOutOfBoundsException e) {
////                        bocal = null;
//                        tfBocal.setText("");
//                    }
//                }
            }
        });
        
        tfQuebraJato = new TextField();
//        tfQuebraJato.setPromptText("*Campo obrigatório");
        tfQuebraJato.setMinWidth(180);
        tfQuebraJato.setMaxWidth(180);
        tfQuebraJato.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old, Boolean novo) {
//                String entrada = "";
                if (tfQuebraJato.getText().isEmpty()) {
                    quebraJato = null;
                } 
//                else if (!entrada.equals(tfQuebraJato.getText())) {
//                    try {
////                        quebraJato = qjdao.getQuebraJatoByDescricao(tfQuebraJato.getText()).get(0);
////                        tfQuebraJato.setText(quebraJato.getDescricao());
//                    } catch (IndexOutOfBoundsException e) {
////                        quebraJato = null;
//                        tfQuebraJato.setText("");
//                    }
//                }
            }
        });
        
        tfDescricao = new TextField();
//        tfDescricao.setPromptText("*Campo obrigatório");
        tfDescricao.setMinWidth(180);
        tfDescricao.setMaxWidth(180);
        


    }

    private HBox getButtonsBox() {
        HBox box = new HBox();
        box.getChildren().addAll(bNewEnsaio/*, bFindEnsaio*/, bRefreshList, bColeta, bUniformidade, bSimulado, processando, pi);
        box.getStyleClass().add("buttonBarMain");
        return box;
    }
    
    private HBox getFilterBox(){
        HBox box = new HBox();
        box.getChildren().addAll(new Label("Descrição: "), tfDescricao, new Label("Bocal: "), tfBocal, bBocal, new Label("Quebra Jato: "), tfQuebraJato, bQuebraJato, bPesquisar);
        box.getStyleClass().add("buttonBar");
        return box;
         
   }

    

    public Button getNewButton() {
        return bNewEnsaio;
    }

    public Button getRefreshButton() {
        return bRefreshList;
    }

    public Button getFindButton() {
        return bFindEnsaio;
    }

    public Button getColetaButton() {
        return bColeta;
    }

    public Button getUniformidadeButton() {
        return bUniformidade;
    }

    public Button getSimuladoButton() {
        return bSimulado;
    }

    public EnsaioTable getTable() {
        return table;
    }
    
    public Button getBocalButton(){
        return bBocal;
    }
    
    public Button getQuebraJatoButton(){
        return bQuebraJato;
    }
    
    public Button getFilterButton(){
        return bPesquisar;
    }

    public void refreshTable(List<Ensaio> ensaios) {
        table.reload(ensaios);
    }
    
    public void showProgress(boolean b){
        processando.setVisible(b);
        pi.setVisible(b);
    }
    
    public ProgressBar getProgressBar(){
        return pi;
    }

    public void disableButtonBar(boolean disable) {
        bNewEnsaio.setDisable(disable);
        bFindEnsaio.setDisable(disable);
        bRefreshList.setDisable(disable);
        bColeta.setDisable(disable);
        bUniformidade.setDisable(disable);
        bSimulado.setDisable(disable);

    }

    public Bocal getBocal() {
        return bocal;
    }

    public void setBocal(Bocal bocal) {
        this.bocal = bocal;
        tfBocal.setText(bocal.getDescricao());
    }

    public QuebraJato getQuebraJato() {
        return quebraJato;
    }

    public void setQuebraJato(QuebraJato quebraJato) {
        tfQuebraJato.setText(quebraJato.getDescricao());
        this.quebraJato = quebraJato;
    }
    
}
