package dr.util;

import dr.model.Coleta;
import dr.model.Ensaio;
import java.util.List;
import javafx.collections.ObservableList;

/**
 * Interface de uniformidades
 * @author andregnhoato
 */
public interface IUniformidades {
    /**
     * Método que realiza a sopreposição dos valores da Coleta
     * @param espacamentoX Largura Laterais
     * @param espacamentoY Espaçamento entre aspersores
     * @param coletas Coletas do Ensaio
     * @param e Ensaio
     * @return  Lista com os valores sobrepostos preparada para setar na tabela
     */
    public ObservableList calculaSobreposicoes(int espacamentoX, int espacamentoY, List<Coleta> coletas, Ensaio e);
    
    /**
     * Metodo reponsável por calcular o CUC
     * @param sobreposicoes valores das sobrepostos 
     * @return valor do CUC
     */
    public Float calculoCuc(List<Float> sobreposicoes);
    
    /**
     * Metodo reponsável por calcular o CUD
     * @param sobreposicoes valores das sobrepostos 
     * @return valor do CUD
     */
    public Float calculoCud(List<Float> sobreposicoes);
    
    /**
     * Metodo reponsável por calcular o CUD
     * @param sobreposicoes valores das sobrepostos 
     * @return valor do CUD
     */
    public Float calculoCue(List<Float> sobreposicoes);
    
    /**
     * Método responsavel por calcular e retornar a listagem com os perfis de distribuição do ensaio
     * @param coletas
     * @return perfils
     */
    public List<Float> calculaPerfilDistribuicao();
    
    public Float getDesvioPadrao();
    public Float getCoeficienteVariacao();
    public Float getMediaMenorQuartil();

    
}
