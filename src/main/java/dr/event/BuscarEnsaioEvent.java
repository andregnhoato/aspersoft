package dr.event;

import dr.event.*;
import dr.model.Ensaio;
import java.util.List;

/**
 * Evento deve ser gerado durante a pesquisa de ensaios.
 * 
 * <p>
 *  Recebe um <code>List</code> com a(s) <code>Ensaio<code>(s) encontrada(s).
 * </p>
 * 
 * @author @andre
 */
public class BuscarEnsaioEvent extends AbstractEvent<List<Ensaio>> {
    
    public BuscarEnsaioEvent(List<Ensaio> m) {
        super(m);
    }
    
}
