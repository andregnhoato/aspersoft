package dr.controller;

import dr.event.AbstractEventListener;
import dr.event.ensaio.AtualizaColetaEvent;
import dr.model.Ensaio;
import dr.ui.coleta.ColetaListView;
import javafx.event.EventType;

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
        
        //registerAction(coletaView.getTable().get, null);
                
        registerEventListener(AtualizaColetaEvent.class, new AbstractEventListener<AtualizaColetaEvent>(){
            @Override
            public void handleEvent(AtualizaColetaEvent event){
                System.out.println("**** Evento de saída da celula");
            }
        });
        
        
        
        
        
        
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
