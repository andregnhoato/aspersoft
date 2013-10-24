/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.util;

import dr.model.Coleta;
import dr.model.Ensaio;
import dr.ui.coleta.uniformidade.UniformidadeTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author andregnhoato
 */
public class UniformidadesImpl implements IUniformidades {

    private Ensaio ensaio;
    private ObservableList<ObservableList> sobreposicoes;
    private float gridAltura;
    private float gridLargura;
    boolean perfeito = false;
    private float sobreposicaoX;
    private float sobreposicaoY;
    private float posAspersorX;
    private float posAspersorY;
    private static Float mediaSobreposicao;

    @Override
    public ObservableList calculaSobreposicoes(int espacamentoX, int espacamentoY, List<Coleta> coletas, Ensaio e) {
        sobreposicoes = FXCollections.observableArrayList();
        this.ensaio = e;
        perfeito = false;
        try {

            //transformando a metragem em unidade
            gridAltura = ensaio.getGridAltura() / ensaio.getEspacamentoPluviometro();
            gridLargura = ensaio.getGridLargura() / ensaio.getEspacamentoPluviometro();

            if (coletas.size() < (gridAltura * gridLargura)) {
                throw new Exception("Invalid length array");
            }
            //1 identificando a localização do aspersor y = espacamento entre aspersores e x laterais
            //CORRIGIR EM UMA SITUAÇÃO ONDE 24X30 DEFINI-SE QUE 24 É Y (ALTURA OU ESPAÇAMENTO ENTRE ASPERSORES) E 30 É X (LARGURA LATERAIS)
            posAspersorX = gridLargura / 2;
            posAspersorY = gridAltura / 2;

            //2 identificando se a sobreposição compreende 1/4 do grid
            sobreposicaoX = espacamentoX / ensaio.getEspacamentoPluviometro();
            sobreposicaoY = espacamentoY / ensaio.getEspacamentoPluviometro();

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
                LinkedList<Float> sp = new LinkedList<>();


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
                    Float soma;
                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
                    soma = (float) (Math.round(soma * 100.0) / 100.0);
                    sp.add(soma);
                }

                int contador = 0;
                for (int linha = 0; linha < sobreposicaoX; linha++) {
                    ObservableList<Float> row = FXCollections.observableArrayList();
                    for (int coluna = 0; coluna < sobreposicaoY; coluna++) {
                        row.add(sp.get(contador));
                        contador++;
                    }
                    sobreposicoes.add(row);

                }

            } else {//FINALIZAR NÃO ESTÁ COMPLETO
                //4 sobreposição imperfeita separar os arrays adicionando zero nos espaços a serem completados
                List<Coleta> coletasAjustadas = ajustaTamanhoColetas(coletas, (sobreposicaoY - posAspersorY), (sobreposicaoX - posAspersorX), gridAltura, gridLargura);
                LinkedList<Float> quad1 = new LinkedList<>();
                LinkedList<Float> quad2 = new LinkedList<>();
                LinkedList<Float> quad3 = new LinkedList<>();
                LinkedList<Float> quad4 = new LinkedList<>();
                LinkedList<Float> sp = new LinkedList<>();


                for (Coleta c : coletasAjustadas) {
                    if (c.getLinha() < posAspersorX && c.getColuna() < posAspersorY) {
                        quad1.add(c.getValor());
                    } else {
                        if (c.getLinha() < posAspersorX && c.getColuna() >= posAspersorY) {
                            quad2.add(c.getValor());
                        } else {
                            if (c.getLinha() >= posAspersorX && c.getColuna() < posAspersorY) {
                                quad3.add(c.getValor());
                            } else {
                                quad4.add(c.getValor());
                            }
                        }
                    }
                }
                System.out.println("tamanho quad1: "+quad1.size());
                System.out.println("tamanho quad2: "+quad2.size());
                System.out.println("tamanho quad3: "+quad3.size());
                System.out.println("tamanho quad4: "+quad4.size());
                System.out.println("quad1: "+quad1.toString());
                System.out.println("quad2: "+quad2.toString());
                System.out.println("quad3: "+quad3.toString());
                System.out.println("quad4: "+quad4.toString());
                
                for (int j = 0; j < (sobreposicaoX * sobreposicaoY); j++) {
                    Float soma;
                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
                    soma = (float) (Math.round(soma * 100.0) / 100.0);
                    sp.add(soma);
                }
                System.out.println("Valores sobrepostos: "+ sp.toString());

                int contador = 0;
                for (int linha = 0; linha < sobreposicaoX; linha++) {
                    ObservableList<Float> row = FXCollections.observableArrayList();
                    for (int coluna = 0; coluna < sobreposicaoY; coluna++) {
                        row.add(sp.get(contador));
                        contador++;
                    }
                    sobreposicoes.add(row);

                }

//                switch (espacamentoY) {
//                    case 12:
//                        switch (espacamentoX) {
//                            case 12:
//                                sobreposicao12x12(sobreposicaoX, sobreposicaoY);
//                                break;
//                            case 18:
//                                sobreposicao12x18();
//                                break;
//                            case 24:
//                                sobreposicao12x24();
//                                break;
//
//                        }
//                    case 18:
//                        switch (espacamentoX) {
//                            case 18:
//                                sobreposicao18x18();
//                                break;
//                            case 24:
//                                sobreposicao18x24();
//                                break;
//                            case 30:
//                                sobreposicao18x30();
//                                break;
//                        }
//                    case 24:
//                        switch (espacamentoX) {
//                            case 24:
//                                sobreposicao24x24();
//                                break;
//                            case 30:
//                                sobreposicao24x30();
//                                break;
//                        }
//                    case 30:
//                        sobreposicao30x30();
//                        break;
//                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UniformidadeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sobreposicoes;


    }

    /**
     * @param sobreposicoes lista com as sobreposições calculadas
     * @formula cuc possivel visualizar a formula em
     * https://dl.dropboxusercontent.com/u/10055997/cuc.gif
     */
    @Override
    public Float calculoCuc(List<Float> listaSobreposicoes) {

        Float somatoria = 0F;
        Float somatoriaAbsolutos = 0F;
        //somatória da sobreposiçao
        for (int i = 0; i < listaSobreposicoes.size(); i++) {
            somatoria += listaSobreposicoes.get(i);
        }

        mediaSobreposicao = somatoria / listaSobreposicoes.size();

        List<Float> valoresAbsolutos = new ArrayList();
        for (int i = 0; i < listaSobreposicoes.size(); i++) {
            valoresAbsolutos.add(Math.abs(listaSobreposicoes.get(i) - mediaSobreposicao));
        }
        for (int i = 0; i < valoresAbsolutos.size(); i++) {
            somatoriaAbsolutos += valoresAbsolutos.get(i);
        }

        return round(1 - (somatoriaAbsolutos / (valoresAbsolutos.size() * mediaSobreposicao)), 4);
    }

    /**
     * @param listaSobreposicoes lista com as sobreposições
     * @param mediaSobreposicoes media dos valores sobrepostos
     * @see Calculo cud corresponde a somatória dos 25% menores valores divido
     * pela media geral das sobreposições
     * @return CUD
     */
    @Override
    public Float calculoCud(List<Float> listaSobreposicoes) {
        //ordena a listagem
        Collections.sort(listaSobreposicoes);
        //armazenando o valor de 1/4 do array
        float quarto = listaSobreposicoes.size() / 4;
        float somatoria = 0;
        for (int i = 0; i < quarto; i++) {
            somatoria += listaSobreposicoes.get(i);
        }

        //calcula media de 1/4
        float quartil = somatoria / quarto;
        //calcula cud media de 1/4 dividido pela media geral

        return round((quartil / this.mediaSobreposicao), 4);

    }

    /**
     *
     * @param listaSobreposicoes lista com os valores sobrepostos
     * @param mediaSobreposicoes media dos valores sobrepostos
     * @return CUE
     */
    @Override
    public Float calculoCue(List<Float> listaSobreposicoes) {

        //1 calcular o desvio padrão = valor sopreposição - media geral elevado ao quadrado e realizar a somatória dos valores
        Float soma = 0F;
        for (Float valor : listaSobreposicoes) {
            double a = valor - mediaSobreposicao;
            soma = +(float) Math.pow(a, 2);
        }
        //2 calcular cue = 1 - raiz quadrada(soma / ((quantidade sobreposições -1 ) * media sobreposições ao quadrado
        return round(1 - (float) Math.sqrt(soma / ((listaSobreposicoes.size() - 1) * (float) Math.pow(mediaSobreposicao, 2))), 4);
    }

    /**
     * metodo de arredondamento
     *
     * @param d valor a ser arredondado
     * @param decimalPlace casas decimais do arredondamento
     * @return valor arredondado
     */
    public static Float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     *
     * @param coletas Array com as coletas
     * @param y quantidade de colunas a ser adicionadas deverá ser sempre par
     * altura
     * @param x quantidade de linhas a ser adicionadas deverá ser sempre par
     * largura
     * @return lista com as respectivas linnhas colunas adicionadas com valores
     * = 0
     */
    public List<Coleta> ajustaTamanhoColetas(List<Coleta> coletas, float y, float x, float gridAltura, float gridLargura) {
        List<Float> list = new LinkedList<>();

        List<Float> rowArray = new LinkedList<>();
        List<Float> columnArray = new LinkedList<>();


        if (y != 0) {
            //reposicionar aspersor
            posAspersorY += y;
            //adicionar as colunas 
            for (int j = 0; j < y; j++) {
                columnArray.add(0F);
            }

        }
        if (x != 0) {

            //reposicionar aspersor
            posAspersorX += x;
            if (y != 0) {
                rowArray.addAll(0, columnArray);
            }
            //adicionar linhas linha
            for (int i = 0; i < gridLargura; i++) {
                rowArray.add(0F);
            }
            if (y != 0) {
                rowArray.addAll(columnArray);
            }

            //adicionando as primeiras linhas x
            for (int i = 0; i < x; i++) {
                list.addAll(0, rowArray);
            }
        }
        if (y != 0) {
            list.addAll(columnArray);
        }


        for (int i = 0; i < (gridLargura * gridAltura); i++) {
            list.add(coletas.get(i).getValor());
            if (((i + 1) % gridLargura) == 0 && y != 0) {
                list.addAll(columnArray);
                list.addAll(columnArray);
            }

        }
        //ajusta o array removendo a ultima adição do array de coluna excedente
        if (y != 0) {
            for (int i = 0; i < y; i++) {
                list.remove(list.size() - 1);
            }

        }

        if (x != 0) {
            //adicionando as ultimas linhas x
            for (int i = 0; i < x; i++) {
                list.addAll(rowArray);
            }
        }
        System.out.println("array original" + coletas.size());
        System.out.println("Array alterado" + list.size());
        System.out.println("Tamanho que era pra ficar o array" + coletas.toString());
        System.out.println("Tamanho que era pra ficar o array" + list.toString());
        
        List<Coleta> clts = new LinkedList<>();
        int cont =0;
        for (int linha = 0; linha < (posAspersorX*2); linha++) {
            for (int coluna = 0; coluna < (posAspersorY * 2); coluna++) {
                Coleta c = new Coleta();
                c.setColuna(coluna);
                c.setLinha(linha);
                c.setEnsaio(ensaio);
                c.setValor(list.get(cont));
                clts.add(c);
                cont ++;
            }
        }

        

        return clts;
    }

    public void sobreposicao12x12(float sobreposicaoX, float sobreposicaoY) {
        //1 passo adicionar as linhas e colunas extras se necessário
//        ajustaTamanhoColetas(sobreposicaoX, sobreposicaoY, gridAltura, gridLargura);
//        LinkedList<Float> quad1 = new LinkedList<>();
//        LinkedList<Float> quad2 = new LinkedList<>();
//           LinkedList<Float> quad3 = new LinkedList<>();
//                LinkedList<Float> quad4 = new LinkedList<>();
//                LinkedList<Float> sp = new LinkedList<>();
//                Map<Integer, Integer> celulas = new HashMap<>();
//
//
//                for (Coleta c : coletas) {
//                    if (c.getLinha() < posAspersorX && c.getColuna() < posAspersorX) {
//                        quad1.add(c.getValor());
//                        celulas.put(c.getLinha(), c.getColuna());
//                    } else {
//                        if (c.getLinha() < posAspersorX && c.getColuna() >= posAspersorX) {
//                            quad2.add(c.getValor());
//                            celulas.put(c.getLinha(), c.getColuna());
//                        } else {
//                            if (c.getLinha() >= posAspersorX && c.getColuna() < posAspersorX) {
//                                quad3.add(c.getValor());
//                                celulas.put(c.getLinha(), c.getColuna());
//                            } else {
//                                if (c.getLinha() >= posAspersorX && c.getColuna() > posAspersorX) {
//                                    quad4.add(c.getValor());
//                                    celulas.put(c.getLinha(), c.getColuna());
//                                }
//                            }
//                        }
//                    }
//                }
//                System.out.println(celulas.toString());
//                for (int j = 0; j < (sobreposicaoX * sobreposicaoY); j++) {
//
//                    Float soma;
//                    soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
//                    soma = (float) (Math.round(soma * 100.0) / 100.0);
//                    sp.add(soma);
//                }
    }

    public void sobreposicao12x18() {
    }

    public void sobreposicao12x24() {
    }

    public void sobreposicao18x18() {
    }

    public void sobreposicao18x24() {
    }

    public void sobreposicao18x30() {
    }

    public void sobreposicao24x24() {
    }

    public void sobreposicao24x30() {
    }

    public void sobreposicao30x30() {
    }
    //            } else {//FINALIZAR NÃO ESTÁ COMPLETO
//                //4 sobreposição imperfeita separar os arrays adicionando zero nos espaços a serem completados
//
//                switch (espacamentoY) {
//                    case 12:
//                        switch (espacamentoX) {
//                            case 12:
//                                sobreposicao12x12(sobreposicaoX, sobreposicaoY);
//                                break;
//                            case 18:
//                                sobreposicao12x18();
//                                break;
//                            case 24:
//                                sobreposicao12x24();
//                                break;
//
//                        }
//                    case 18:
//                        switch (espacamentoX) {
//                            case 18:
//                                sobreposicao18x18();
//                                break;
//                            case 24:
//                                sobreposicao18x24();
//                                break;
//                            case 30:
//                                sobreposicao18x30();
//                                break;
//                        }
//                    case 24:
//                        switch (espacamentoX) {
//                            case 24:
//                                sobreposicao24x24();
//                                break;
//                            case 30:
//                                sobreposicao24x30();
//                                break;
//                        }
//                    case 30:
//                        sobreposicao30x30();
//                        break;
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(UniformidadeTable.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return sobreposicoes;
}
