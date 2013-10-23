/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.util;

import dr.model.Coleta;
import dr.model.Ensaio;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author andregnhoato
 */
public interface IUniformidades {
    
    public ObservableList calculaSobreposicoes(int espacamentoX, int espacamentoY, List<Coleta> coletas, Ensaio e);
//    public List<Float> sobreposicaoPerfeita();
    public Float calculoCuc(List<Float> sobreposicoes);
    public Float calculoCud(List<Float> sobreposicoes);
    public Float calculoCue(List<Float> sobreposicoes);
//    
//    public void sobreposicao12x12(float sobreposicaoX, float sobreposicaoY);
    
    
}
