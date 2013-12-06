package dr.ui.perfil;

import dr.chart.AreaChartLineSmooth;
import dr.model.Ensaio;
import dr.report.AnaliseJavaBeanDataSource;
import dr.ui.ensaio.EnsaioTable;
import dr.util.IUniformidades;
import dr.util.UniformidadesImpl;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
public final class PerfilView extends Stage {

    private Ensaio ensaio;
    private List<Ensaio> ensaios;
    private EnsaioTable table;
    IUniformidades uniformidade;
    private AreaChart grafico;
    private Button BtExportaExcel;
    private Button BtLimpar;
    List<Float> perfil;
    List<Float> distancia;

    public PerfilView() {
        initModality(Modality.APPLICATION_MODAL);
        this.setScene(inicializaAll());

    }

    private void inicializaComponentes() {
        BtExportaExcel = new Button("Exportar Gráfico");
        BtExportaExcel.getStyleClass().add("buttonGreen");
        BtExportaExcel.setId("btExportar");

        BtLimpar = new Button("Limpar");
        BtLimpar.getStyleClass().add("buttonGreen");
        BtLimpar.setId("btLimpar");
    }

    public Scene inicializaAll() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

//        setX(bounds.getMinX());
        setY(bounds.getMinY());
        setWidth(820);
        setHeight(bounds.getHeight());

        GridPane geral = new GridPane();
        setTitle("Comparação de perfis");
        setResizable(true);
        

        inicializaComponentes();
        /* public void add(Node child, int columnIndex, int rowIndex, int colspan, int rowspan)
         /* primeiro painel da tabela de ensaios */
        TitledPane td = new TitledPane();
        td.setText("Ensaios");
        table = new EnsaioTable();
        table.setPrefSize(800, 300);
        GridPane grid = new GridPane();
        grid.add(table, 0, 0);
        td.setContent(grid);
        td.setPrefWidth(bounds.getWidth());

        geral.add(td, 0, 0);

        /* segundo painel gráficos */
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        grafico = new AreaChartLineSmooth(xAxis, yAxis);
        grafico.setTitle("Perfil de distribuição");
        //defining a series
        //populating the series with data
        grafico.getYAxis().setAutoRanging(true);
        grafico.getXAxis().setAutoRanging(true);
        grafico.setPrefSize(800, 500);
        
        Label cursorCoords = createCursorGraphCoordsMonitorLabel(grafico);
        cursorCoords.setAlignment(Pos.CENTER);
        grid = new GridPane();
        td = new TitledPane();
        grid.add(this.grafico, 0, 0);
        grid.add(cursorCoords, 0, 1);
        td.setText("Perfil de distribuição");
        td.setContent(grid);
        geral.add(td, 0, 1);

        grid = new GridPane();
        grid.add(BtExportaExcel, 0, 0);
        grid.add(BtLimpar, 1, 0);
        geral.add(grid, 0, 2);

        ScrollPane sp = new ScrollPane();
        sp.setContent(geral);
        
        initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(sp);
        scene.getStylesheets().add("style.css");

        return scene;

    }

    private Label createCursorGraphCoordsMonitorLabel(AreaChart lineChart) {
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

    public void limparGrafico() {
        this.grafico.getData().clear();
    }

    public void reRenderTable() {
        uniformidade = new UniformidadesImpl(ensaio);
        XYChart.Series series = new XYChart.Series();
        series.setName(ensaio.getDescricao());
        perfil = uniformidade.calculaPerfilDistribuicao();
        distancia = uniformidade.calculaDistanciaPerfilDistribuicao();
        for (int i = 0; i < perfil.size(); i++) {
            series.getData().add(new XYChart.Data(distancia.get(i), perfil.get(i)));
        }
        this.grafico.getData().addAll(series);
        this.grafico.autosize();
    }

    public void refreshTable(List<Ensaio> ensaios) {
        table.reload(ensaios);
    }

    public void setEnsaio(Ensaio e) {
        this.ensaio = e;
    }

    public EnsaioTable getTable() {
        return table;
    }

    public Button getBtExportarExcel() {
        return BtExportaExcel;
    }

    public Button getBtLimpar() {
        return BtLimpar;
    }

    public AnaliseJavaBeanDataSource getReport() {
        AnaliseJavaBeanDataSource report = new AnaliseJavaBeanDataSource();
        report.setEnsaio(ensaio);
        report.setDistancia(distancia);
        report.setPerfil(perfil);
        return report;
    }
}
