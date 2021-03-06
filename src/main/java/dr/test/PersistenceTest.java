/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.test;

import dr.controller.PersistenceController;
import dr.dao.BocalDAO;
import dr.dao.BocalDAOImpl;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.dao.CombinacaoDAO;
import dr.dao.CombinacaoDAOImpl;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOImpl;
import dr.dao.QuebraJatoDAO;
import dr.dao.QuebraJatoDAOImpl;
import dr.model.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class PersistenceTest extends PersistenceController {

    public static ColetaDAO cdao;
    public static EnsaioDAO edao;
    public static CombinacaoDAO combDao;
    public static QuebraJatoDAO qdao;
    public static BocalDAO bdao;

    public static void main(String[] args) throws Exception {

        PersistenceTest pt = new PersistenceTest();

//        pt.listarColetasByEnsaio(118);
//        pt.qtdeColetasByEnsaio(1);
//        pt.removeColetasByEnsaio(1);
//        pt.qtdeColetasByEnsaio(1);
        //pt.qtdeEnsaios();
        //pt.sobreposicao(4, 12, 12);

//        pt.qtdeColetas();
//        pt.atualizarEnsaio();


//        pt.removeColeta(30320);
//        pt.removeColeta(30312);
//        pt.removeColeta(30321);
//        pt.removeColeta(30322);
//        pt.removeColeta(30323);
//        pt.removeColeta(30324);
//        pt.removeColeta(30325);
//        pt.removeColeta(30326);
//        pt.removeColeta(30308);


//         pt.listarColetasByEnsaio(108);
        pt.listarColetasByEnsaio(118);
//        pt.insertNewFunc();




    }

    public void atualizarColeta(int idEnsaio, int linha, int coluna) {
        Ensaio e;

        try {
            e = edao.findById(idEnsaio);
            Coleta c = cdao.findColetaByPosicao(e, linha, coluna);
            c.setValor(8F);
            cdao.update(c);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizarEnsaio() {
        Ensaio e;
        try {
            List<Ensaio> ensaios = (List<Ensaio>) edao.findAll();
            for (Ensaio ensaio : ensaios) {
                ensaio.setGridAltura(24);
                ensaio.setGridLargura(24);
                edao.update(ensaio);
            }

        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //remove as coletas de um determinado ensaio param id do ensaio
    public void removeColetasByEnsaio(int idEnsaio) {
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

    public void insertNewFunc() {
        List<QuebraJato> quebras = new ArrayList<>();

        QuebraJato qj = new QuebraJato();
        qj.setDescricao("2.4");//2.6, 2.8 3.2
        quebras.add(qj);
        qj = new QuebraJato();
        qj.setDescricao("2.6");
        quebras.add(qj);
        qj = new QuebraJato();
        qj.setDescricao("2.8");
        quebras.add(qj);
        qj = new QuebraJato();
        qj.setDescricao("3.2");
        quebras.add(qj);
        for (int i = 0; i < quebras.size(); i++) {
            try {
                qdao.save(quebras.get(i));
            } catch (Exception ex) {
                Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Bocal> bocais = new ArrayList<>();
        Bocal b = new Bocal();
        b.setDescricao("2.6");
        bocais.add(b);
        b = new Bocal();
        b.setDescricao("2.8");
        bocais.add(b);
        b = new Bocal();
        b.setDescricao("3.0");
        bocais.add(b);
        b = new Bocal();
        b.setDescricao("3.2");
        bocais.add(b);
        b = new Bocal();
        b.setDescricao("3.4");
        bocais.add(b);
        b = new Bocal();
        b.setDescricao("3.6");
        bocais.add(b);
        b = new Bocal();
        b.setDescricao("3.8");
        bocais.add(b);
        b = new Bocal();
        b.setDescricao("4.0");
        bocais.add(b);
        for (int i = 0; i < bocais.size(); i++) {
            try {
                bdao.save(bocais.get(i));
            } catch (Exception ex) {
                Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        try {
            quebras.clear();
            quebras = qdao.findAll();
            for (int i = 0; i < quebras.size(); i++) {
                System.out.println("ID: "+quebras.get(i).getId());
                System.out.println("Descricao quebra jatoØ: "+quebras.get(i).getDescricao());
                
            }
            
            bocais.clear();
            bocais = bdao.findAll();
            for (int i = 0; i < bocais.size(); i++) {
                System.out.println("ID: "+bocais.get(i).getId());
                System.out.println("Descricao bocais: "+bocais.get(i).getDescricao());
                
            }
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Combinacao c = new Combinacao();
        c.setAltura(0F);
        c.setDiametroIrrigado(0F);
        c.setLargura(12F);
        c.setPressao(0F);
        c.setVazao(0F);
        c.setAltura(12F);
        c.setBocal(bocais.get(0));
        c.setQuebraJato(quebras.get(0));
        try {
            combDao.save(c);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    public void listarColetasByEnsaio(int idEnsaio) {
        try {
            Ensaio e = new Ensaio();
            e.setId(idEnsaio);
            List<Coleta> coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);
            System.out.println("tamanho array: " + coletas.size());

            int linha = 15;
            int coluna = 10;
            for (int i = 0; i <= 5; i++) {
                Coleta c = new Coleta();
                c.setColuna(coluna);
                c.setLinha(linha);
                c.setValor(0F);
                c.setEnsaio(coletas.get(0).getEnsaio());

                cdao.save(c);
                coluna++;
            }
            coletas = null;
            coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);
            System.out.println("tamanho array: " + coletas.size());
            for (Coleta cc : coletas) {
                Coleta coleta = cc;
                System.out.println("tamanho array: " + coletas.size());

                System.out.println("coleta id: " + coleta.getId());
                System.out.println("coleta coluna: " + coleta.getColuna());
                System.out.println("coleta linha: " + coleta.getLinha());
                System.out.println("coleta valor: " + coleta.getValor());
                System.out.println("id ensaio: " + coleta.getEnsaio().getId() + " descricao: " + (coleta.getEnsaio() == null ? "nulo" : coleta.getEnsaio().getDescricao()));
                System.out.println("=====================================");

            }
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void removeColeta(int idColeta) {
        Coleta c = new Coleta();
        try {
            c = cdao.findById(idColeta);
            cdao.remove(c);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void qtdeColetas() {
        try {

            List<Coleta> coletas = (List<Coleta>) cdao.findAll();


            System.out.println("Quantidade de coletas: " + coletas.size());


        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void qtdeColetasByEnsaio(int idEnsaio) {
        try {
            Ensaio e = new Ensaio();
            e.setId(idEnsaio);
            List<Coleta> coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);


            System.out.println("Quantidade de coletas: " + coletas.size());

            if (coletas.size() < 256) {
                throw new Exception("coletas zuadas no ensaios" + coletas.get(0).getEnsaio().getDescricao());
            }

        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void qtdeEnsaios() {
        try {
            List<Ensaio> ensaios = (List<Ensaio>) edao.findAll();
            System.out.println("Quantidade de ensaios: " + ensaios.size());

        } catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sobreposicao(int idEnsaio, int espacamentoX, int espacamentoY) {
        try {
            Ensaio e = edao.findById(idEnsaio);
            DecimalFormat df = new DecimalFormat("0.00");

            float sobreposicaoX = 0;
            float sobreposicaoY = 0;
            boolean perfeito = false;


            //coletas
            List<Coleta> coletas = (List<Coleta>) cdao.findColetasByEnsaio(e);
            if (coletas.size() < (e.getGridAltura() * e.getGridLargura())) {
                throw new Exception("Invalid length array");
            }


            //1 identificando a localização do aspersor ** verificar com Paulo
            int posAspersorX = e.getGridAltura() / 2;
            int posAspersorY = e.getGridLargura() / 2;

            //2 identificando se a sobreposição compreende 1/4 do grid
            sobreposicaoX = espacamentoX / e.getEspacamentoPluviometro();
            sobreposicaoY = espacamentoY / e.getEspacamentoPluviometro();

            if (posAspersorX == posAspersorY) {
                if (posAspersorX == sobreposicaoX && posAspersorY == sobreposicaoY) {
                    perfeito = true;
                }
            }

            //armazenar os valores sobrepostos em coletas
//            ArrayList<Coleta> sobreposicoes  = new ArrayList();
            //3 se sobreposição é perfeita separar os quadrantes para realizar a soma
            if (perfeito) {
                LinkedList<Float> quad1 = new LinkedList<>();
                LinkedList<Float> quad2 = new LinkedList<>();
                LinkedList<Float> quad3 = new LinkedList<>();
                LinkedList<Float> quad4 = new LinkedList<>();
                LinkedList<Float> sobreposicoes = new LinkedList<>();


                for (Coleta c : coletas) {
                    if (c.getLinha() < posAspersorX && c.getColuna() < posAspersorX) {
                        quad1.add(c.getValor());
                    } else {
                        if (c.getLinha() < posAspersorX && c.getColuna() >= posAspersorX) {
                            quad2.add(c.getValor());
                        } else {
                            if (c.getLinha() >= posAspersorX && c.getColuna() < posAspersorX) {
                                quad3.add(c.getValor());
                            } else {
                                quad4.add(c.getValor());
                            }
                        }
                    }
                }
                for (int j = 0; j < (sobreposicaoX * sobreposicaoY); j++) {
                    Float soma = 0F;
                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
                    sobreposicoes.add(soma);
                }

                System.out.println("Quad1: " + quad1.toString());
                System.out.println("Quad1 size: " + quad1.size());
                System.out.println("Quad2: " + quad2.toString());
                System.out.println("Quad2 size: " + quad2.size());
                System.out.println("Quad3: " + quad3.toString());
                System.out.println("Quad3 size: " + quad3.size());
                System.out.println("Quad4: " + quad4.toString());
                System.out.println("Quad4 size: " + quad4.size());
                System.out.println("VALORES SOBREPOSTOS: " + sobreposicoes.toString());




            } else {
                //4 sobreposição imperfeita separar os arrays adicionando zero nos espaços a serem completados
                LinkedList<Float> quad1 = new LinkedList<>();
                LinkedList<Float> quad2 = new LinkedList<>();
                LinkedList<Float> quad3 = new LinkedList<>();
                LinkedList<Float> quad4 = new LinkedList<>();
                LinkedList<Float> sobreposicoes = new LinkedList<>();
                Map<Integer, Integer> celulas = new HashMap<>();


                for (Coleta c : coletas) {
                    if (c.getLinha() < posAspersorX && c.getColuna() < posAspersorX) {
                        quad1.add(c.getValor());
                        celulas.put(c.getLinha(), c.getColuna());
                    } else {
                        if (c.getLinha() < posAspersorX && c.getColuna() >= posAspersorX) {
                            quad2.add(c.getValor());
                            celulas.put(c.getLinha(), c.getColuna());
                        } else {
                            if (c.getLinha() >= posAspersorX && c.getColuna() < posAspersorX) {
                                quad3.add(c.getValor());
                                celulas.put(c.getLinha(), c.getColuna());
                            } else {
                                if (c.getLinha() >= posAspersorX && c.getColuna() > posAspersorX) {
                                    quad4.add(c.getValor());
                                    celulas.put(c.getLinha(), c.getColuna());
                                }
                            }
                        }
                    }
                }
                for (int j = 0; j < (sobreposicaoX * sobreposicaoY); j++) {
                    Float soma = 0F;
                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
                    sobreposicoes.add(soma);
                }


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
        this.qdao = new QuebraJatoDAOImpl(getPersistenceContext());
        this.combDao = new CombinacaoDAOImpl(getPersistenceContext());
        this.bdao = new BocalDAOImpl(getPersistenceContext());
    }
}
