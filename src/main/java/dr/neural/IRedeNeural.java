/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.neural;

import dr.model.Ensaio;
import java.util.List;

/**
 *
 * @author andregnhoato
 */
public interface IRedeNeural {
    
    List<Float> rede(Ensaio e);
    
}
