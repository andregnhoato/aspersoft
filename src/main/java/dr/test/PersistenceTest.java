/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.test;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOImpl;
import dr.model.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author andre
 */
public class PersistenceTest extends PersistenceController {
    
    public static ColetaDAO cdao;
    public static EnsaioDAO edao;
    
    public static void main(String[] args) throws Exception{
        
        PersistenceTest pt = new PersistenceTest();
//        pt.listarColetasByEnsaio(1);
//        pt.qtdeColetasByEnsaio(1);
//        pt.removeColetasByEnsaio(1);
//        pt.qtdeColetasByEnsaio(1);
        //pt.qtdeEnsaios();
        //pt.sobreposicao(4, 12, 12);
        for (int i = 0; i < 108; i++) {
            pt.qtdeColetasByEnsaio(i);
            
        }

       
        
       
    }
    
    public void atualizarColeta(int idEnsaio, int linha, int coluna){
        Ensaio e;
        
        try{
            e = edao.findById(idEnsaio);
            Coleta c = cdao.findColetaByPosicao(e, linha, coluna);
            c.setValor(8F);
            cdao.update(c);
        }catch(Exception ex){
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //remove as coletas de um determinado ensaio param id do ensaio
    public void removeColetasByEnsaio(int idEnsaio){
        Ensaio e = new Ensaio();
        e.setId(idEnsaio);
        List<Coleta> coletas;
        try {
            coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);
            for (Iterator<Coleta> it = coletas.iterator(); it.hasNext();) {
            Coleta coleta = it.next();
            System.out.println(cdao.remove(coleta));
            }
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listarColetasByEnsaio(int idEnsaio){
        try {
            Ensaio e = new Ensaio();
            e.setId(idEnsaio);
            List<Coleta> coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);
            for (Iterator<Coleta> it = coletas.iterator(); it.hasNext();) {
                Coleta coleta = it.next();
                System.out.println("coleta id: " + coleta.getId());
                System.out.println("coleta coluna: " + coleta.getColuna());
                System.out.println("coleta linha: " + coleta.getLinha());
                System.out.println("coleta valor" + coleta.getValor());
                System.out.println("id ensaio: " + coleta.getEnsaio().getId() + " descricao: " + (coleta.getEnsaio() == null ? "nulo" : coleta.getEnsaio().getDescricao()));
                System.out.println("=====================================");
            }
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void qtdeColetasByEnsaio(int idEnsaio ){
        try {
            Ensaio e = new Ensaio();
            e.setId(idEnsaio);
            List<Coleta> coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);

            
            System.out.println("Quantidade de coletas: " + coletas.size());
            
            if(coletas.size()<256)
                throw new Exception("coletas zuadas no ensaios"+coletas.get(0).getEnsaio().getDescricao());
            
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void qtdeEnsaios(){
        try {
            List<Ensaio> ensaios = (List<Ensaio>) edao.findAll();            
            System.out.println("Quantidade de ensaios: " + ensaios.size());
            
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void sobreposicao(int idEnsaio, int espacamentoX, int espacamentoY ){
        try {
            Ensaio e = edao.findById(idEnsaio);
            DecimalFormat df = new DecimalFormat("0.00");
            float sobreposicao = 0;
            boolean perfeito = false;
            if(espacamentoX == espacamentoY){
                sobreposicao = espacamentoX / e.getEspacamentoPluviometro();
            }
            //coletas
            List<Coleta> coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);
            if(coletas.size()<(e.getGridAltura()*e.getGridLargura())){
                throw new Exception("Invalid length array");
            }
            
            //1 identificando a localização do aspersor ** verificar com Paulo
            int posAspersor = e.getGridAltura() / 2;
            //2 identificando se a sobreposição compreende 1/4 do grid
            if(posAspersor == sobreposicao)
                perfeito = true;
            
            //armazenar os valores sobrepostos em coletas
//            ArrayList<Coleta> sobreposicoes  = new ArrayList();
            //3 se sobreposição é perfeita separar os quadrantes para realizar a soma
            if (perfeito){
                LinkedList<Float> quad1 = new LinkedList<>();
                LinkedList<Float> quad2 = new LinkedList<>();
                LinkedList<Float> quad3 = new LinkedList<>();
                LinkedList<Float> quad4 = new LinkedList<>();
                LinkedList<Float> sobreposicoes = new LinkedList<>();
                
                Iterator i = coletas.iterator();
                while(i.hasNext()){
                    Coleta c =(Coleta) i.next();
                    if(c.getLinha()<posAspersor && c.getColuna() < posAspersor)
                        quad1.add(c.getValor());
                    else{
                        if(c.getLinha() < posAspersor && c.getColuna() >= posAspersor)
                            quad2.add(c.getValor());
                        else{
                            if(c.getLinha() >= posAspersor && c.getColuna() < posAspersor)
                                quad3.add(c.getValor());
                            else{
                                quad4.add(c.getValor());
                            }
                        }
                    }
                }
                for (int j = 0; j < (sobreposicao*sobreposicao); j++) {
                    Float soma = 0F;
                    soma = quad1.get(j)+quad2.get(j)+quad3.get(j)+quad4.get(j);
                    sobreposicoes.add(soma);
                }
                
                System.out.println("Quad1: "+ quad1.toString());
                System.out.println("Quad1 size: "+ quad1.size());
                System.out.println("Quad2: "+ quad2.toString());
                System.out.println("Quad2 size: "+ quad2.size());
                System.out.println("Quad3: "+ quad3.toString());
                System.out.println("Quad3 size: "+ quad3.size());
                System.out.println("Quad4: "+ quad4.toString());
                System.out.println("Quad4 size: "+ quad4.size());
                System.out.println("VALORES SOBREPOSTOS: "+ sobreposicoes.toString());
                
                
                
                
            }else{
                //4 sobreposição imperfeita separar os arrays adicionando zero nos espaços a serem completados
                
            }
       
//            System.out.println("grid altura: " + e.getGridAltura() + " grid largura: "+ e.getGridLargura());
//            System.out.println("espaçamento: " + e.getEspacamentoPluviometro());
//            System.out.println("Quantidade de coletas: " + coletas.size());
            
            
            
        } catch (Exception e) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
     
    public PersistenceTest() {
        this.cdao = new ColetaDAOImpl(getPersistenceContext());
        this.edao = new EnsaioDAOImpl(getPersistenceContext());
    }
    
}
