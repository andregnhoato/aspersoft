package dr.event;

import dr.event.*;
import dr.model.Coleta;

/**
 * Evento deve ser gerado quando perder o foco da célula e inserir/atualizar a coleta.
 * 
 * @author @Andre
 */
public class AtualizaColetaEvent extends AbstractEvent<Coleta> {
    
    public AtualizaColetaEvent(Coleta c) {
        super(c);
    }
    
}
