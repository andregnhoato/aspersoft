package dr.event;

import dr.model.QuebraJato;



/**
 * Evento deve ser gerado durante a exclusão de uma <code>QuebraJato</code>.
 * 
 * <p>Recebe a referência da <code>QuebraJato</code> que foi removida.</p>
 * 
 * @author @Andre
 */
public class RemoveQuebraJatoEvent extends AbstractEvent<QuebraJato> {
    
    public RemoveQuebraJatoEvent(QuebraJato e) {
        super(e);
    }
    
}
