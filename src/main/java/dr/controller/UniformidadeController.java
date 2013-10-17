package dr.controller;

import dr.action.AbstractAction;
import dr.model.Ensaio;
import dr.ui.coleta.uniformidade.UniformidadeListView;
import javafx.application.Platform;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela  <code>Uniformidades </code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class UniformidadeController extends PersistenceController {
    
    private UniformidadeListView uniformidadeView;

    public UniformidadeController(AbstractController parent) {
        this.uniformidadeView = new UniformidadeListView();
        
        registerAction(this.uniformidadeView.getComboEspacamento(), new AbstractAction() {
            @Override
            protected void action() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        uniformidadeView.reRenderTable();
                    }
                });
            }
        });
        
        
        
        
    }

    void setEnsaio(Ensaio e) {
        this.uniformidadeView.setEnsaio(e);
    }

    void reRenderTable() {
        this.uniformidadeView.reRenderTable();
    }
    
    void renderTable(){
        this.uniformidadeView.renderTable();
    }

    void show() {
        this.uniformidadeView.show();
    }
}
