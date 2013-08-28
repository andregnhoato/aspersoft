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
import java.util.List;


/**
 *
 * @author andre
 */
public class PersistenceTest extends PersistenceController {
    
    public static ColetaDAO cdao;
    public static EnsaioDAO edao;
    
    public static void main(String[] args) throws Exception{
        PersistenceTest pt = new PersistenceTest();
        
        for(int i =0; i<10; i++){
            Ensaio e = new Ensaio();
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
            
        }
        
        System.err.println(edao.findAll().size());
        
        
        /*
        System.err.println(e.getGridAltura());
        System.err.println(e.getGridLargura());
        /*
        for(int i=0; i<e.getGridAltura(); i++){
            for(int j=0; j<e.getGridLargura(); j++){*/
        /*
        Ensaio ensaio = edao.getAll().get(0);
        System.out.println(e.getDescricao());
        System.out.println("ensaio"+ e.toString());
        
        Coleta coleta = new Coleta();
        coleta.setEnsaio(ensaio);
        coleta.setLinha(1);
        coleta.setColuna(1);
        coleta.setValor(0F);
        cdao.save(coleta);
       
        
        List<Coleta> coletas = cdao.getAll();
        if(coletas.size()>0)
            System.out.println("Maior que 0"+ coletas.size());
        else
            System.err.println("menor");
       
       */
    }

    public PersistenceTest() {
        this.cdao = new ColetaDAOImpl(getPersistenceContext());
        this.edao = new EnsaioDAOImpl(getPersistenceContext());
    }
    
}
