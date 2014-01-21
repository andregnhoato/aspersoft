package dr.event;

import dr.event.AbstractEvent;
import dr.model.Ensaio;

/**
 * Evento deve ser gerado durante a inclusão de uma <code>Ensaio</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi incluida.</p>
 * 
 * @author @andre
 */
public class IncluirEnsaioEvent extends AbstractEvent<Ensaio> {
	
    public IncluirEnsaioEvent(Ensaio e) {
        super(e);
    }
    
}
