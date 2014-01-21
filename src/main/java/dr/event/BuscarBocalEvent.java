package dr.event;

import dr.model.Bocal;
import java.util.List;

/**
 * Evento deve ser gerado durante a pesquisa de ensaios.
 * 
 * <p>
 *  Recebe um <code>List</code> com a(s) <code>Bocal<code>(s) encontrada(s).
 * </p>
 * 
 * @author @andre
 */
public class BuscarBocalEvent extends AbstractEvent<List<Bocal>> {
    
    public BuscarBocalEvent(List<Bocal> m) {
        super(m);
    }
    
}
