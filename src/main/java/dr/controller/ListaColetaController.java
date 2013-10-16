package dr.controller;

import dr.model.Ensaio;
import dr.ui.coleta.ColetaListView;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Coleta</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class ListaColetaController extends PersistenceController {
    
    private ColetaListView coletaView;

    public ListaColetaController(AbstractController parent) {
        this.coletaView = new ColetaListView();       
    }

    void setEnsaio(Ensaio e) {
        this.coletaView.setEnsaio(e);
    }

    void reRenderTable() {
        this.coletaView.reRenderTable();
    }

    void show() {
        this.coletaView.show();
    }
}
