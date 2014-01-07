/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.neural;

import dr.model.Coleta;
import dr.model.Ensaio;
import dr.ui.Dialog;
import java.util.List;

/**
 *
 * @author andregnhoato
 */
public class RedeNeuralImpl implements IRedeNeural{

    @Override
    public List<Float> rede(Ensaio e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Float normalizaBocal(Ensaio e){
        try{
            float bocal = Float.parseFloat(e.getBocal().getDescricao());
            float quebraJato = Float.parseFloat(e.getQuebraJato().getDescricao());
            
            return (float)(((bocal+quebraJato) - 5.0) / 2.2);
            
        }catch(Exception ex){
            Dialog.showError("Erro ao normalizar Bocais", "Necessário ajustar a descrição do bocal e quebra jato, manter somente número ex:2.4.");
        }
        
        return 0F;
        
    }
    
    public Float normalizaPressao(Ensaio e){
        try{
            float pressao = Float.parseFloat(e.getPressao());
            
            return (float)((pressao - 2.0) / 1.5);
            
        }catch(Exception ex){
            Dialog.showError("Erro ao normalizar Pressao", "Necessário ajustar a pressão, manter somente número ex:2.4.");
        }
        return 0F;
    }
    
    public Float normalizaVelocidade(Ensaio e){
        return (float) (e.getVelocidadeVento()/1.65);
    }
    
    public Float normalizaDirecao(Ensaio e){
        return (float)((e.getDirecaoVentoGraus() - 22.5) / 315);
    }
    
    public Float normalizaColeta(Coleta c){
        return (float)(c.getValor() / 15.5);
    }

    
}
