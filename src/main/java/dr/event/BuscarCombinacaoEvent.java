package dr.event;

import dr.model.Combinacao;
import java.util.List;

/**
 * Evento deve ser gerado durante a pesquisa de ensaios.
 * 
 * <p>
 *  Recebe um <code>List</code> com a(s) <code>Combinacao<code>(s) encontrada(s).
 * </p>
 * 
 * @author @andre
 */
public class BuscarCombinacaoEvent extends AbstractEvent<List<Combinacao>> {
    
    public BuscarCombinacaoEvent(List<Combinacao> m) {
        super(m);
    }
    
}
