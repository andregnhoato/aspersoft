package dr.event;

import dr.model.Mercadoria;
import java.util.List;

/**
 * Evento deve ser gerado durante a pesquisa de mercadorias.
 * 
 * <p>
 *  Recebe um <code>List</code> com a(s) <code>Mercadoria<code>(s) encontrada(s).
 * </p>
 * 
 * @author YaW Tecnologia
 */
public class BuscarMercadoriaEvent extends AbstractEvent<List<Mercadoria>> {
    
    public BuscarMercadoriaEvent(List<Mercadoria> m) {
        super(m);
    }
    
}
