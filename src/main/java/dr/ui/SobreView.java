package dr.ui;

import static dr.util.ApplicationProperties.getBuild;
import static dr.util.ApplicationProperties.getDesenvolvidoPor;
import static dr.util.ApplicationProperties.getSite;
import static dr.util.ApplicationProperties.getTitulo;
import static dr.util.ApplicationProperties.getVersao;
import static dr.util.ApplicationProperties.getURLLogo;
import javafx.application.Platform;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Tela <i>Sobre</i>. Apresenta detalhes da aplicação.
 * 
 * @author YaW Tecnologia
 */
public class SobreView extends Stage {

    public SobreView() {
        setTitle("Sobre");
        setWidth(670);
        setHeight(200);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        centerOnScreen();
        
        inicializaComponentes();
    }
    
    private void inicializaComponentes() {
        GridPane inputs = buildInputs();
        VBox image = getImage();
        
        HBox vbox = new HBox();
        vbox.getChildren().addAll(image, inputs);
        Scene scene = new Scene(new Group(vbox));
        scene.getStylesheets().add("style.css");
        this.setScene(scene);
        
        //fecha a tela com ESC
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.ESCAPE, KeyCombination.SHORTCUT_ANY), 
                new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                hide();
                            }
                        });
                    }
                }
            );
    }
    
    private GridPane buildInputs() {
        GridFormBuilder grid = new GridFormBuilder();
        grid.addRow(new Label("Aplicacão: "), buildField(getTitulo(), 200));
        grid.addRow(new Label("Versão: "), buildField(getVersao(), 100));
        grid.addRow(new Label("Desenvolvido por: "), buildField(getDesenvolvidoPor(), 150));
        grid.addRow(new Label("Build: "), buildField(getBuild(), 350));
        grid.addRow(new Label("Site: "), buildField(getSite(), 200));
        
        return grid.build();
    }
    
    private TextField buildField(String text, double maxWidth){
        TextField tf = new TextField(text);
        tf.setEditable(false);
        tf.setMinWidth(maxWidth);
        tf.setMaxWidth(maxWidth);
        return tf;
    }
    
    private VBox getImage() {
        Image image = new Image(getURLLogo());
        VBox box = new VBox();
        box.getChildren().add(new ImageView(image));
        box.getStyleClass().add("logoBox");
        return box;
    }
}
