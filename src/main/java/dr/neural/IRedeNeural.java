/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.neural;

import com.sun.jna.Library;
import java.util.List;

/**
 *
 * @author andregnhoato
 */
public interface IRedeNeural extends Library {
    
    List<Float> rede(List<Float> entrada);
    
}
