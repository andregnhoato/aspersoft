package dr.event;

import dr.model.QuebraJato;
import java.util.List;

/**
 * Evento deve ser gerado durante a pesquisa de ensaios.
 * 
 * <p>
 *  Recebe um <code>List</code> com a(s) <code>QuebraJato<code>(s) encontrada(s).
 * </p>
 * 
 * @author @andre
 */
public class BuscarQuebraJatoEvent extends AbstractEvent<List<QuebraJato>> {
    
    public BuscarQuebraJatoEvent(List<QuebraJato> m) {
        super(m);
    }
    
}
