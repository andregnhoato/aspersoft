package dr.event.experiment;

import dr.event.AbstractEvent;
import dr.model.Experiment;

/**
 * Evento deve ser gerado durante a inclusão de uma <code>Experiment</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi incluida.</p>
 * 
 * @author YaW Tecnologia
 */
public class AddExperimentEvent extends AbstractEvent<Experiment> {
	
    public AddExperimentEvent(Experiment m) {
        super(m);
    }
    
}
