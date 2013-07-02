package dr.event.experiment;

import dr.event.*;

/**
 * Evento deve ser gerado quando for necessário atualizar a tabela de Ensaios.
 * 
 * @author @Andre
 */
public class UpdateListExperimentEvent extends AbstractEvent<Object> {
    
    public UpdateListExperimentEvent() {
        super(null);
    }
    
}
