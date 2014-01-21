package dr.event;

import dr.model.Bocal;

/**
 * Evento deve ser gerado durante a inclusão de uma <code>Bocal</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi incluida.</p>
 * 
 * @author @andre
 */
public class IncluirBocalEvent extends AbstractEvent<Bocal> {
	
    public IncluirBocalEvent(Bocal e) {
        super(e);
    }
    
}
