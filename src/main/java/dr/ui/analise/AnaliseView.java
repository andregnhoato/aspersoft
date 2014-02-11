package dr.ui.analise;

import dr.chart.AreaChartLineSmooth;
import dr.model.Ensaio;
import dr.report.AnaliseJavaBeanDataSource;
import dr.util.DateUtil;
import dr.util.IUniformidades;
import dr.util.UniformidadesImpl;
import dr.util.WindUtil;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Tela principal da aplicação. Apresenta uma lista com as informações
 * referentes a unifomidade são elas: coeficientes, sobreposição e Ensaio
 * resumido
 *
 * <p>A partir dessa tela é possível visualizar uniformidades do Ensaio.</p>
 *
 * @author
 * @Andre
 */
public final class AnaliseView extends Stage {

    private SobreposicaoTable table;
    private Ensaio ensaio;
    private ComboBox cbEspacamento;
    private Label metros;
    private SimpleStringProperty m;
    private Label descricao;
    private SimpleStringProperty desc;
    private Label pressao;
    private SimpleStringProperty pre;
    private Label data;
    private SimpleStringProperty dt;
    private Label inicio;
    private SimpleStringProperty ini;
    private Label velVento;
    private SimpleStringProperty vv;
    private Label dirVento;
    private SimpleStringProperty dv;
    private Label espacamento;
    private SimpleStringProperty esp;
    private Label duracao;
    private SimpleStringProperty dur;
    private Label quebraJato;
    private SimpleStringProperty qj;
    private Label bocal;
    private SimpleStringProperty boc;
    private ScrollPane sp;
    private Label cuc;
    private SimpleStringProperty Cuc;
    private Label cud;
    private SimpleStringProperty Cud;
    private Label cue;
    private SimpleStringProperty Cue;
    private Label cv;
    private SimpleStringProperty Cv;
    private Label mmq;
    private SimpleStringProperty Mmq;
    private Label dp;
    private SimpleStringProperty Dp;
    private Label aspersor;
    private Label laterais;
    IUniformidades uniformidade;
    private AreaChartLineSmooth grafico;
    private Button BtExportaSobreposicao;
    private Button BtExportaExcel;
    List<Float> perfil;
    List<Float> distancia;

    public AnaliseView() {

        this.uniformidade = new UniformidadesImpl(ensaio);
         

        initModality(Modality.APPLICATION_MODAL);

        this.setScene(inicializaAll());

    }

    private void inicializaComponentes(Ensaio e) {
        table = new SobreposicaoTable();
        cbEspacamento = new ComboBox(getEspacamentos());
        cbEspacamento.setId("comboEspacamento");
        descricao = new Label("--");
        pressao = new Label("--");
        data = new Label("--");
        velVento = new Label("--");
        dirVento = new Label("--");
        espacamento = new Label("--");
        inicio = new Label("--");
        metros = new Label("--");
        duracao = new Label("--");
        quebraJato = new Label("--");
        bocal = new Label("--");
        cuc = new Label("--");
        cud = new Label("--");
        cue = new Label("--");
        mmq = new Label("--");
        dp = new Label("--");
        cv = new Label("--");
        BtExportaSobreposicao = new Button("Exportar para excel");
        BtExportaSobreposicao.getStyleClass().add("buttonGreen");
        BtExportaSobreposicao.setId("btExportar");
        BtExportaExcel = new Button("Exportar Análise");
        BtExportaExcel.getStyleClass().add("buttonGreen");
        BtExportaExcel.setId("btExportar");
    }

    public Scene inicializaAll() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        setX(bounds.getMinX());
        setY(bounds.getMinY());
        setWidth(bounds.getWidth());
        setHeight(bounds.getHeight());

        GridPane geral = new GridPane();
        setTitle("Análise dos Dados");
        setResizable(true);

        inicializaComponentes(ensaio);
//        public void add(Node child, int columnIndex, int rowIndex, int colspan, int rowspan)

        /* primeiro painel dados do ensaio */
        TitledPane td = new TitledPane();
        td.setText("Dados do Ensaio");
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        grid.add(new Label("Ensaio:"), 0, 0);
        grid.add(descricao, 1, 0);
        grid.add(new Label("Pressão:"), 2, 0);
        grid.add(pressao, 3, 0);
        
        grid.add(new Label("Bocal:"), 0, 1);
        grid.add(bocal, 1, 1);
        grid.add(new Label("Quebra Jato:"), 2, 1);
        grid.add(quebraJato, 3, 1);
        
        grid.add(new Label("Data:"), 0, 2);
        grid.add(data, 1, 2);
        grid.add(new Label("Vel. Vento(m/s):"), 2, 2);
        grid.add(velVento, 3, 2);
        
        grid.add(new Label("Início:"), 0, 3);
        grid.add(inicio, 1, 3);
        grid.add(new Label("Direção vento:"), 2, 3);
        grid.add(dirVento, 3, 3);
        
        grid.add(new Label("Espaçamento (m): "), 0, 4);
        grid.add(espacamento, 1, 4);
        grid.add(new Label("Tamanho grid: "), 2, 4);
        grid.add(metros, 3, 4);
        
        grid.add(new Label("Duração:"), 0, 5);
        grid.add(duracao, 1, 5);
        
        td.setContent(grid);
        td.setPrefWidth(bounds.getWidth());

        geral.add(td, 0, 0);

        /* segundo painel perfil de distribuição*/
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        grafico = new AreaChartLineSmooth(xAxis, yAxis);

        grafico.setTitle("Perfil de distribuição");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("perfil");
        //populating the series with data

        grafico.getYAxis().setAutoRanging(true);
        grafico.getXAxis().setAutoRanging(true);
        grafico.getData().add(series);
        grafico.setPrefSize(700, 400);

        Label cursorCoords = createCursorGraphCoordsMonitorLabel(grafico);
        cursorCoords.setAlignment(Pos.CENTER);
        grid = new GridPane();
        td = new TitledPane();
        grid.add(this.grafico, 0, 0);
        grid.add(cursorCoords, 0, 1);
        td.setText("Perfil de distribuição");
        td.setContent(grid);
        geral.add(td, 0, 1);

        /* terceiro painel superposição*/
        td = new TitledPane();
        grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(50, 50, Double.MAX_VALUE));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.add(new Label("Selecione o Espaçamento para visualizar a sobreposição: "), 0, 0, 2, 1);
        grid.add(cbEspacamento, 3, 0);

        aspersor = new Label("Espaçamento entre aspersores");
        aspersor.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                aspersor.setScaleX(1.5);
                aspersor.setScaleY(1.5);
            }
        });

        aspersor.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                aspersor.setScaleX(1);
                aspersor.setScaleY(1);
            }
        });

        laterais = new Label("Laterais");
        laterais.setRotate(270);
        laterais.setTranslateY(50);
        laterais.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                laterais.setScaleX(1.5);
                laterais.setScaleY(1.5);
            }
        });

        laterais.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                laterais.setScaleX(1);
                laterais.setScaleY(1);
            }
        });
        laterais.setAlignment(Pos.BASELINE_LEFT);
        grid.add(laterais, 0, 1);
        grid.add(table, 1, 1, 3, 1);
        aspersor.setAlignment(Pos.TOP_CENTER);
        grid.add(aspersor, 1, 2, 3, 1);

        td.setText("Sobreposição");
        td.setContent(grid);
        td.autosize();
        geral.add(td, 0, 2);

        /* quarto coeficientes */
        grid = new GridPane();
        td = new TitledPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.add(new Label("Coeficiente de uniformidade de Christiansen (CUC):"), 0, 0);
        grid.add(cuc, 1, 0);
        grid.add(new Label("Coeficiente de uniformidade de distribuição (CUD):"), 0, 1);
        grid.add(cud, 1, 1);
        grid.add(new Label("Coeficiente estatístico de uniformidade: (CUE):"), 0, 2);
        grid.add(cue, 1, 2);
        td.setText("Uniformidades");
        td.setContent(grid);
        td.autosize();
        geral.add(td, 0, 3);

        /*quinto painel estatística*/
        grid = new GridPane();
        td = new TitledPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.add(new Label("Desvio Padrão:"), 0, 0);
        grid.add(dp, 1, 0);
        grid.add(new Label("Média 1º quartil:"), 0, 1);
        grid.add(mmq, 1, 1);
        grid.add(new Label("CV:"), 0, 2);
        grid.add(cv, 1, 2);
        td.setText("Dados Estatísticos");
        td.setContent(grid);
        td.autosize();
        geral.add(td, 0, 4);
        geral.add(BtExportaExcel,0,5);
 
        sp = new ScrollPane();
        sp.setContent(geral);

        Scene scene = new Scene(sp);
        scene.getStylesheets().add("style.css");

        return scene;

    }

    private Label createCursorGraphCoordsMonitorLabel(AreaChartLineSmooth lineChart) {
        final Axis<Number> xAxis = lineChart.getXAxis();
        final Axis<Number> yAxis = lineChart.getYAxis();

        final Label cursorCoords = new Label();

        final Node chartBackground = lineChart.lookup(".chart-plot-background");
        for (Node n : chartBackground.getParent().getChildrenUnmodifiable()) {
            if (n != chartBackground && n != xAxis && n != yAxis) {
                n.setMouseTransparent(true);
            }
        }

        chartBackground.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setVisible(true);
            }
        });

        chartBackground.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setText(
                        String.format(
                        "(distância %.2f, milímetros %.2f)",
                        xAxis.getValueForDisplay(mouseEvent.getX()),
                        yAxis.getValueForDisplay(mouseEvent.getY())));
            }
        });

        chartBackground.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setVisible(false);
            }
        });

        xAxis.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setVisible(true);
            }
        });

        xAxis.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setText(
                        String.format(
                        "distância = %.2f",
                        xAxis.getValueForDisplay(mouseEvent.getX())));
            }
        });

        xAxis.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setVisible(false);
            }
        });

        yAxis.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setVisible(true);
            }
        });

        yAxis.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setText(
                        String.format(
                        "milímetros = %.2f",
                        yAxis.getValueForDisplay(mouseEvent.getY())));
            }
        });

        yAxis.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cursorCoords.setVisible(false);
            }
        });

        return cursorCoords;
    }

    public void reRenderTable() {

        table.reRenderTable(ensaio, (cbEspacamento.getValue() == null ? null : cbEspacamento.getValue().toString()));
        uniformidade = new UniformidadesImpl(ensaio);
        Cuc = new SimpleStringProperty(table.getCUC() + "");
        cuc.textProperty().bind(Cuc);
        cuc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Cud = new SimpleStringProperty(table.getCUD() + "");
        cud.textProperty().bind(Cud);
        cud.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Cue = new SimpleStringProperty(table.getCUE() + "");
        cue.textProperty().bind(Cue);
        cue.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Dp = new SimpleStringProperty(table.getDP() + "");
        dp.textProperty().bind(Dp);
        dp.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Cv = new SimpleStringProperty(table.getCV() + "%");
        cv.textProperty().bind(Cv);
        cv.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Mmq = new SimpleStringProperty(table.getMMQ() + "");
        mmq.textProperty().bind(Mmq);
        mmq.setFont(Font.font("Verdana", FontWeight.BOLD, 14));


        this.grafico.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName(ensaio.getDescricao());
        perfil = uniformidade.calculaPerfilDistribuicao();
        distancia = uniformidade.calculaDistanciaPerfilDistribuicao();
        for (int i = 0; i < perfil.size(); i++) {
            series.getData().add(new XYChart.Data(distancia.get(i), perfil.get(i)));

        }
        this.grafico.getData().add(series);

    }

    public SobreposicaoTable getTable() {
        return table;
    }

    public void refreshTable(List<Ensaio> ensaios) {
        //table.reload(ensaios);        
    }

    public void setEnsaio(Ensaio e) {
        this.ensaio = e;


        float mQuad = (e.getGridAltura() * e.getGridLargura());

        desc = new SimpleStringProperty(e.getDescricao());
        pre = new SimpleStringProperty(e.getPressao());
        dt = new SimpleStringProperty(DateUtil.formatDate(e.getData()));
        vv = new SimpleStringProperty(e.getVelocidadeVento() + "");
        dv = new SimpleStringProperty(WindUtil.getWindByDegress(e.getDirecaoVentoGraus()));
        esp = new SimpleStringProperty(e.getEspacamentoPluviometro() + "");
        ini = new SimpleStringProperty(e.getInicio());
        m = new SimpleStringProperty(mQuad + " m2");
        qj = new SimpleStringProperty(e.getQuebraJato().getDescricao());
        boc = new SimpleStringProperty(e.getBocal().getDescricao());
        
        if(e.getColetaHora()){
            dur = new SimpleStringProperty("60 minutos");
            duracao.setTextFill(Color.web("#0000FF"));
        }else{
            dur = new SimpleStringProperty(e.getDuracao() + " minutos");
            duracao.setTextFill(Color.web("#000000"));
        }



        descricao.textProperty().bind(desc);
        pressao.textProperty().bind(pre);
        data.textProperty().bind(dt);
        velVento.textProperty().bind(vv);
        dirVento.textProperty().bind(dv);
        espacamento.textProperty().bind(esp);
        inicio.textProperty().bind(ini);
        metros.textProperty().bind(m);
        quebraJato.textProperty().bind(qj);
        bocal.textProperty().bind(boc);
        duracao.textProperty().bind(dur);
        
        
    }

    public ComboBox getComboEspacamento() {
        return cbEspacamento;
    }
    
    public Button getBtExportar(){
        return BtExportaSobreposicao;
    }
    
    public Button getBtExportarExcel(){
        return BtExportaExcel;
    }
    
    public ObservableList getSobreposicao(){
        
        return table.getSobreposicoes();
    }
    
    public AnaliseJavaBeanDataSource getReport(){
        AnaliseJavaBeanDataSource report = new AnaliseJavaBeanDataSource();
        report.setEnsaio(ensaio);
        report.setCuc(table.getCUC());
        report.setCud(table.getCUD());
        report.setCue(table.getCUE());
        report.setCv(table.getCV());
        report.setDp(table.getDP());
        report.setMedia(table.getMMQ());
        report.setDistancia(distancia);
        report.setPerfil(perfil);
        report.setSobreposicao(table.getSobreposicoes());
        report.setSobredimensao((cbEspacamento.getValue()!=null?cbEspacamento.getValue().toString():""));
        
        return report;
    }

    private ObservableList getEspacamentos() {
        ObservableList<String> espacamentos =
                FXCollections.observableArrayList(
                "12x12",
                "12x15",
                "12x18",
                "12x24",
                "15x15",
                "18x18",
                "18x24",
                "18x30",
                "24x24",
                "24x30",
                "30x30");

        return espacamentos;
    }
}
