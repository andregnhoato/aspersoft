package dr.event.experiment;

import dr.event.AbstractEvent;
import dr.model.Experiment;



/**
 * Evento deve ser gerado durante a exclusão de uma <code>Mercadoria</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi removida.</p>
 * 
 * @author YaW Tecnologia
 */
public class RemoveExperimentEvent extends AbstractEvent<Experiment> {
    
    public RemoveExperimentEvent(Experiment m) {
        super(m);
    }
    
}
