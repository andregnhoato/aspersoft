/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.neural;

import com.sun.jna.Native;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andregnhoato
 */
public class RedeNeuralImpl {

    public static void carregaLib() {
        System.setProperty("jna.debug_load", "true");
        System.setProperty("jna.library.path", System.getProperty("user.dir"));

        IRedeNeural INSTANCE = (IRedeNeural) Native.loadLibrary("libRede", IRedeNeural.class);
        List<Float> parametros = new ArrayList<>();
        parametros.add(0.818182F);
        parametros.add(0.000000F);
        parametros.add(0.606061F);
        parametros.add(0.733333F);
        
        List saida = INSTANCE.rede(parametros);
        System.out.print(saida.toString());

    }
    public static void main(String[] args) { 
       RedeNeuralImpl.carregaLib();
//        readXLSXFile();

    }
}
