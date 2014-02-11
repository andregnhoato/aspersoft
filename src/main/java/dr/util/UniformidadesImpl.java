package dr.util;

import dr.controller.PersistenceController;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.dao.ConfiguracaoDAO;
import dr.dao.ConfiguracaoDAOImpl;
import dr.model.Coleta;
import dr.model.Configuracao;
import dr.model.Ensaio;
import dr.ui.analise.SobreposicaoTable;
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
 * Classe que implementa a interface IUniformidades responsável por realizar
 * todos os calculos referente a uniformidades dos Ensaios
 *
 * @author andregnhoato
 */
public class UniformidadesImpl extends PersistenceController implements IUniformidades {

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
    private boolean reSobrepoeX = false;
    private boolean reSobrepoeY = false;
    List<Coleta> coletas;
    private Float desvioPadrao;
    private Float mediaMenorQuartil;
    private Float coeficienteVariacao;
    private ColetaDAO cDao;
    private ConfiguracaoDAO configDao;
    private Configuracao config;

    public UniformidadesImpl(Ensaio e) {

        if (e != null) {
            this.cDao = new ColetaDAOImpl(this.getPersistenceContext());
            this.configDao = new ConfiguracaoDAOImpl(this.getPersistenceContext());
            this.ensaio = e;
            //transformando a metragem em unidade
            gridAltura = ensaio.getGridAltura() / ensaio.getEspacamentoPluviometro();
            gridLargura = ensaio.getGridLargura() / ensaio.getEspacamentoPluviometro();

            try {
                if(e.getColetaHora()){
                    LinkedList<Coleta> clts = new LinkedList<>();
                    for (Coleta clt :cDao.findColetasByEnsaio(e)) {
                        Coleta c = new Coleta();
                        c.setValor(clt.getValor()/(e.getDuracao()/60));
                        c.setColuna(clt.getColuna());
                        c.setEnsaio(clt.getEnsaio());
                        c.setLinha(clt.getLinha());
                        c.setId(clt.getId());
                        clts.add(c);
                    }
                    this.coletas = clts;
                }else
                    this.coletas = cDao.findColetasByEnsaio(e);
                
                this.config = configDao.findAll().get(0);
            } catch (Exception ex) {
                Logger.getLogger(UniformidadesImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    @Override
    public ObservableList calculaSobreposicoes(int espacamentoX, int espacamentoY, List<Coleta> coletas) {
        sobreposicoes = FXCollections.observableArrayList();
        perfeito = false;
        try {
            this.coletas = coletas;

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
                } else {
                    if (posAspersorX > sobreposicaoX) {
                        reSobrepoeX = true;
                    }
                    if (posAspersorY > sobreposicaoY) {
                        reSobrepoeY = true;
                    }
                }
            }


            //3 se sobreposição é perfeita separar os quadrantes para realizar a soma
            if (perfeito) {

                ajustaSobreposicaoParaTabela(sobreposicaoQuadrantes(coletas));

            } else {

                //4 sobreposição imperfeita separar os arrays adicionando zero nos espaços a serem completados
                List<Coleta> coletasAjustadas = ajustaTamanhoColetas(coletas, (sobreposicaoY - posAspersorY), (sobreposicaoX - posAspersorX), gridAltura, gridLargura);
                if (reSobrepoeX || reSobrepoeY) {
                    List<Float> sobre1 = sobreposicaoQuadrantes(coletasAjustadas);

                    List<Coleta> clts = new LinkedList<>();
                    int cont = 0;
                    for (int linha = 0; linha < (reSobrepoeX ? posAspersorX * 2 : posAspersorX); linha++) {
                        for (int coluna = 0; coluna < (reSobrepoeY ? (posAspersorY * 2) : posAspersorY); coluna++) {
                            Coleta c = new Coleta();
                            c.setColuna(coluna);
                            c.setLinha(linha);
                            c.setEnsaio(ensaio);
                            c.setValor(sobre1.get(cont));
                            clts.add(c);
                            cont++;
                        }
                    }

                    if (!reSobrepoeX && reSobrepoeY) {
                        LinkedList<Float> quad1 = new LinkedList<>();
                        LinkedList<Float> quad2 = new LinkedList<>();
                        LinkedList<Float> sp = new LinkedList<>();

                        for (Coleta c : coletas) {
                            if (c.getColuna() < (posAspersorY)) {
                                quad1.add(c.getValor());
                            } else {
                                quad2.add(c.getValor());
                            }
                        }
                        System.out.println("Quad1: " + quad1.toString());
                        System.out.println("Quad2: " + quad2.toString());

                        for (int j = 0; j < (posAspersorX * posAspersorY); j++) {
                            Float soma;
                            soma = quad1.get(j) + quad2.get(j);
                            soma = (float) (Math.round(soma * 100.0) / 100.0);
                            sp.add(soma);
                        }

                        ajustaSobreposicaoParaTabela(sp);
                    }else{
                        reSobrepoeX = !reSobrepoeX;
                        reSobrepoeY = !reSobrepoeY;
                        ajustaSobreposicaoParaTabela(sobreposicaoQuadrantes(clts));
                    }
                } else {
                    ajustaSobreposicaoParaTabela(sobreposicaoQuadrantes(coletasAjustadas));
                }


            }
        } catch (Exception ex) {
            Logger.getLogger(SobreposicaoTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sobreposicoes;
    }

    /**
     * Método que realiza a divisão dos quandrantes e realiza as sobreposições
     *
     * @param coletas listagem de coletas
     * @return valores sobrepostos
     */
    public LinkedList<Float> sobreposicaoQuadrantes(List<Coleta> coletas) {
        LinkedList<Float> quad1 = new LinkedList<>();
        LinkedList<Float> quad2 = new LinkedList<>();
        LinkedList<Float> quad3 = new LinkedList<>();
        LinkedList<Float> quad4 = new LinkedList<>();
        LinkedList<Float> sp = new LinkedList<>();

        for (Coleta c : coletas) {
            if (c.getLinha() < (reSobrepoeX ? (posAspersorX * 2) : (posAspersorX)) && c.getColuna() < (reSobrepoeY ? (posAspersorY * 2) : (posAspersorY))) {
                quad1.add(c.getValor());
            } else {
                if (c.getLinha() < (reSobrepoeX ? (posAspersorX * 2) : (posAspersorX)) && c.getColuna() >= (reSobrepoeY ? (posAspersorY * 2) : (posAspersorY))) {
                    quad2.add(c.getValor());
                } else {
                    if (c.getLinha() >= (reSobrepoeX ? (posAspersorX * 2) : (posAspersorX)) && c.getColuna() < (reSobrepoeY ? (posAspersorY * 2) : (posAspersorY))) {
                        quad3.add(c.getValor());
                    } else {
                        quad4.add(c.getValor());
                    }
                }
            }
        }
        System.out.println("Quad1: " + quad1.toString());
        System.out.println("Quad2: " + quad2.toString());
        System.out.println("Quad3: " + quad3.toString());
        System.out.println("Quad4: " + quad4.toString());


        for (int j = 0; j < ((reSobrepoeX ? (posAspersorX * 2) : (posAspersorX)) * (reSobrepoeY ? (posAspersorY * 2) : (posAspersorY))); j++) {
            Float soma;
            soma = quad1.get(j) + quad2.get(j) + quad3.get(j) + quad4.get(j);
            soma = (float) (Math.round(soma * 100.0) / 100.0);
            sp.add(soma);
        }


        return sp;
    }

    /**
     * Método responsável por orgarnizar o array de sobreposições para a tabela
     * que irá listar na tela de uniformidades já seta no array sobreposicoes da
     * classe
     *
     * @param sp sobreposiçoes calculadas
     */
    public void ajustaSobreposicaoParaTabela(LinkedList<Float> sp) {
        int contador = 0;
        for (int linha = 0; linha < posAspersorX; linha++) {
            ObservableList<Float> row = FXCollections.observableArrayList();
            for (int coluna = 0; coluna < posAspersorY; coluna++) {
                row.add(round(sp.get(contador), config.getCasasDecimaisSobreposicao()));
                contador++;
            }
            sobreposicoes.add(row);
        }
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

        float ccc = 1 - somatoriaAbsolutos / (valoresAbsolutos.size() * mediaSobreposicao);

        return round(ccc, config.getCasasDecimaisUniformidade());
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
        mediaMenorQuartil = 0F;
        //ordena a listagem
        Collections.sort(listaSobreposicoes);
        //armazenando o valor de 1/4 do array
        float quarto = listaSobreposicoes.size() / 4;
        float somatoria = 0;
        for (int i = 0; i < quarto; i++) {
            somatoria += listaSobreposicoes.get(i);
        }

        //calcula media de 1/4
        mediaMenorQuartil = somatoria / quarto;
        //calcula cud media de 1/4 dividido pela media geral

        return round((mediaMenorQuartil / UniformidadesImpl.mediaSobreposicao), config.getCasasDecimaisUniformidade());

    }

    /**
     *
     * @param listaSobreposicoes lista com os valores sobrepostos
     * @param mediaSobreposicoes media dos valores sobrepostos
     * @return CUE
     */
    @Override
    public Float calculoCue(List<Float> listaSobreposicoes) {
        coeficienteVariacao = 0F;
        desvioPadrao = 0F;
        float soma = 0F;
        //1 calcular o desvio padrão = valor sopreposição - media geral elevado ao quadrado e realizar a somatória dos valores
        for (Float valor : listaSobreposicoes) {

            double a = valor - UniformidadesImpl.mediaSobreposicao;
            float desvio = (float) Math.pow(a, 2);

            soma += desvio;

        }
        //desvio padrão é = raiz quadradada(somatória dos desvios / tamanho das lista de sobreposições -1)
        desvioPadrao = (float) Math.sqrt(soma / (listaSobreposicoes.size() - 1));
        //
        coeficienteVariacao = (soma / UniformidadesImpl.mediaSobreposicao) /**
                 * 100
                 */
                ;

        //2 calcular cue = 1 - raiz quadrada(soma / ((quantidade sobreposições -1 ) * media sobreposições ao quadrado
        return round(1 - (float) Math.sqrt((soma / ((listaSobreposicoes.size() - 1) * (float) Math.pow(UniformidadesImpl.mediaSobreposicao, 2)))), config.getCasasDecimaisUniformidade());
    }

    /**
     * metodo de arredondamento
     *
     * @param d valor a ser arredondado
     * @param decimalPlace casas decimais do arredondamento
     * @return valor arredondado
     */
    public static Float round(Float d, int decimalPlace) {
        try {
            if (d != null && d != 0F) {
                BigDecimal bd = new BigDecimal(Float.toString(d));
                bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
                return bd.floatValue();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0F;


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


        System.out.println("AjustaTamanhoColetas colunas: " + y + " linhas: " + x);
        List<Float> list = new LinkedList<>();
        List<Float> rowArray = new LinkedList<>();
        List<Float> columnArray = new LinkedList<>();

        if (y != 0) {
            //reposicionar aspersor
            posAspersorY += y;
            //verificação caso y e x sejão negativos significa que o espaçamento é menor que a largura do grid e precisa ser resobreposto
            if (posAspersorY < (gridAltura / 2)) {
                y = (gridAltura / 2) - (y * (-2));
            }

            //adicionar as colunas 
            for (int j = 0; j < y; j++) {
                columnArray.add(0F);
            }

        }
        if (x != 0) {

            //reposicionar aspersor
            posAspersorX += x;
            if (posAspersorX < (gridLargura / 2)) {
                x = (gridLargura / 2) - (x * (-2));
            }
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

        List<Coleta> clts = new LinkedList<>();
        int cont = 0;
        for (int linha = 0; linha < (reSobrepoeX ? (posAspersorX * 4) : (posAspersorX * 2)); linha++) {
            for (int coluna = 0; coluna < (reSobrepoeY ? (posAspersorY * 4) : (posAspersorY * 2)); coluna++) {
                Coleta c = new Coleta();
                c.setColuna(coluna);
                c.setLinha(linha);
                c.setEnsaio(ensaio);
                c.setValor(list.get(cont));
                clts.add(c);
                cont++;
            }
        }
        return clts;
    }

    @Override
    public List<Float> calculaDistanciaPerfilDistribuicao() {
        List<Float> distancias = new LinkedList<>();
        if (this.ensaio != null) {
            float hipotenusa = (float) Math.sqrt(((float) Math.pow((ensaio.getEspacamentoPluviometro()), 2) + (float) Math.pow((ensaio.getEspacamentoPluviometro()), 2)));
            float somaHipotenusa = 0;
            for (int i = 0; i < (gridLargura / 2); i++) {
                somaHipotenusa += hipotenusa;
                if (i == 0) {
                    distancias.add(hipotenusa / 2);
                    somaHipotenusa = hipotenusa / 2;
                } else {
                    distancias.add(somaHipotenusa);
                }
            }
        }
        return distancias;
    }

    @Override
    public List<Float> calculaPerfilDistribuicao() {
        List<Float> vperfil = new LinkedList<>();
        List<List<Float>> perfis = new LinkedList<>();
        if (coletas != null) {

            //calculo do perfil
            int x = 0;
            int xx = (int) gridAltura - 1;
            int y = 0;
            int yy = (int) gridLargura - 1;

            boolean acabou = false;
            while (!acabou) {

                for (Coleta c : coletas) {
                    if (c.getLinha() == x && c.getColuna() == y) {
                        vperfil.add(c.getValor());
                    }
                    if (c.getLinha() == x && c.getColuna() == yy) {
                        vperfil.add(c.getValor());
                    }
                    if (c.getLinha() == xx && c.getColuna() == y) {
                        vperfil.add(c.getValor());
                    }
                    if (c.getLinha() == xx && c.getColuna() == yy) {
                        vperfil.add(c.getValor());
                    }
                    if (vperfil.size() == 4) {
                        perfis.add(vperfil);
                        vperfil = new ArrayList<>();
                        break;
                    }
                }
                if (perfis.size() == (gridLargura / 2)) {
                    acabou = true;
                }
                x++;
                xx--;
                y++;
                yy--;
            }

            for (int i = perfis.size() - 1; i >= 0; i--) {
                float mediaPerfil = 0;
                for (int j = 0; j < perfis.get(i).size(); j++) {
                    mediaPerfil += (perfis.get(i).get(j) / 2);
                }
                vperfil.add(mediaPerfil / 4);
            }

        }

        return vperfil;

    }

    @Override
    public Float getDesvioPadrao() {
        return round((desvioPadrao != null ? desvioPadrao : 0), config.getCasasDecimaisDadosEstatisticos());
    }

    @Override
    public Float getMediaMenorQuartil() {
        return round((mediaMenorQuartil != null ? mediaMenorQuartil : 0), config.getCasasDecimaisDadosEstatisticos());
    }

    @Override
    public Integer getCoeficienteVariacao() {
        if(coeficienteVariacao != null){
            coeficienteVariacao = round(coeficienteVariacao, 0);
            return new Integer(coeficienteVariacao.intValue()) ;
        }else
            return 0;
    }

    @Override
    public List<Coleta> getColetas() {
        return this.coletas;
    }
}
