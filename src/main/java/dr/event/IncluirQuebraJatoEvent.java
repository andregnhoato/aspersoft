package dr.event;

import dr.model.QuebraJato;

/**
 * Evento deve ser gerado durante a inclusão de uma <code>QuebraJato</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi incluida.</p>
 * 
 * @author @andre
 */
public class IncluirQuebraJatoEvent extends AbstractEvent<QuebraJato> {
	
    public IncluirQuebraJatoEvent(QuebraJato e) {
        super(e);
    }
    
}
