package dr.controller;

import dr.model.Ensaio;
import dr.ui.coleta.uniformidade.UniformidadeListView;

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
        
        
    }

    void setEnsaio(Ensaio e) {
        this.uniformidadeView.setEnsaio(e);
    }

    void reRenderTable() {
        this.uniformidadeView.reRenderTable();
    }

    void show() {
        this.uniformidadeView.show();
    }
}
