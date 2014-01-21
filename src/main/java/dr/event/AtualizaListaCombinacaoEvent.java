package dr.event;

import dr.event.*;

/**
 * Evento deve ser gerado quando for necessário atualizar a tabela de Combinacaos.
 * 
 * @author @Andre
 */
public class AtualizaListaCombinacaoEvent extends AbstractEvent<Object> {
    
    public AtualizaListaCombinacaoEvent() {
        super(null);
    }
    
}
