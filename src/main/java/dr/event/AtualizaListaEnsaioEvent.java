package dr.event;

import dr.event.*;

/**
 * Evento deve ser gerado quando for necessário atualizar a tabela de Ensaios.
 * 
 * @author @Andre
 */
public class AtualizaListaEnsaioEvent extends AbstractEvent<Object> {
    
    public AtualizaListaEnsaioEvent() {
        super(null);
    }
    
}
