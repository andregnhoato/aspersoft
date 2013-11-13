package dr.event;

import dr.model.Bocal;



/**
 * Evento deve ser gerado durante a exclusão de uma <code>Bocal</code>.
 * 
 * <p>Recebe a referência da <code>Bocal</code> que foi removida.</p>
 * 
 * @author @Andre
 */
public class RemoveBocalEvent extends AbstractEvent<Bocal> {
    
    public RemoveBocalEvent(Bocal e) {
        super(e);
    }
    
}
