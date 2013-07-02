package dr.event.ensaio;

import dr.event.AbstractEvent;
import dr.model.Ensaio;



/**
 * Evento deve ser gerado durante a exclusão de uma <code>Mercadoria</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi removida.</p>
 * 
 * @author @Andre
 */
public class RemoveEnsaioEvent extends AbstractEvent<Ensaio> {
    
    public RemoveEnsaioEvent(Ensaio e) {
        super(e);
    }
    
}
