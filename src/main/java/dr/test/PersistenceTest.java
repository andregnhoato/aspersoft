/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.test;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOJPA;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOJPA;
import dr.model.*;
import java.sql.Date;
import java.util.List;


/**
 *
 * @author andre
 */
public class PersistenceTest extends PersistenceController {
    
    public static ColetaDAO cdao;
    //public static EnsaioDAO edao;
    
    public static void main(String[] args){
        PersistenceTest pt = new PersistenceTest();
       /* Ensaio e = new Ensaio();
        e.setBocal("2.4");
        e.setData(new Date(2013, 7, 19));
        e.setDescricao("e1");
        e.setDirecaoVento("N");
        e.setDuracao("22");
        e.setEspacamentoPluviometro(1.4F);
        e.setGridAltura(10);
        e.setGridLargura(10);
        e.setInicio("11");
        e.setPressao("per");
        e.setQuebraJato("qq");
        e.setVelocidadeVento(0.4F);
        
        edao.save(e);
        
        
        
        System.err.println(e.getGridAltura());
        System.err.println(e.getGridLargura());
        for(int i=0; i<e.getGridAltura(); i++){
            for(int j=0; j<e.getGridLargura(); j++){*/
                Coleta coleta = new Coleta();
        //        coleta.setEnsaio(e);
                coleta.setLinha(1);
                coleta.setColuna(1);
                coleta.setValor(0F);
                cdao.save(coleta);
            /*}
        }
        System.out.println(e.getDescricao())*/
        List<Coleta> coletas = pt.cdao.getAll();
        if(coletas.size()>0)
            System.out.println("Maior que 0"+ coletas.size());
        else
            System.err.println("menor");
       
       
    }

    public PersistenceTest() {
        this.cdao = new ColetaDAOJPA(getPersistenceContext());
        //this.edao = new EnsaioDAOJPA(getPersistenceContext());
    }
    
}
