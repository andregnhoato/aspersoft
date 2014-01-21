package dr.event;

import dr.model.Combinacao;



/**
 * Evento deve ser gerado durante a exclusão de uma <code>Combinacao</code>.
 * 
 * <p>Recebe a referência da <code>Combinacao</code> que foi removida.</p>
 * 
 * @author @Andre
 */
public class RemoveCombinacaoEvent extends AbstractEvent<Combinacao> {
    
    public RemoveCombinacaoEvent(Combinacao e) {
        super(e);
    }
    
}
