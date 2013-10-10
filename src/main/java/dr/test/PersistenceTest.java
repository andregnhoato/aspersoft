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
import java.sql.Date;
import java.util.Iterator;
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
        pt.atualizarColeta(2, 0, 0);

       
        
       
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
            
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
     
    public PersistenceTest() {
        this.cdao = new ColetaDAOImpl(getPersistenceContext());
        this.edao = new EnsaioDAOImpl(getPersistenceContext());
    }
    
}
