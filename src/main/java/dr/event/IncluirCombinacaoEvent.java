package dr.event;

import dr.model.Combinacao;

/**
 * Evento deve ser gerado durante a inclusão de uma <code>Combinacao</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi incluida.</p>
 * 
 * @author @andre
 */
public class IncluirCombinacaoEvent extends AbstractEvent<Combinacao> {
	
    public IncluirCombinacaoEvent(Combinacao e) {
        super(e);
    }
    
}
