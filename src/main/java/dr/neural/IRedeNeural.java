package dr.neural;

import dr.model.Coleta;
import dr.model.Ensaio;
import java.util.List;

/**
 *
 * @author andregnhoato
 */
public interface IRedeNeural {
    
    List<Coleta> rede(Ensaio e);
}
