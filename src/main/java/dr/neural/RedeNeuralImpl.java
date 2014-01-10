/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.neural;

import dr.model.Coleta;
import dr.model.Ensaio;
import dr.ui.Dialog;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andregnhoato
 */
public class RedeNeuralImpl implements IRedeNeural {

    @Override
    public List<Coleta> rede(Ensaio e) {
        try {
            createTxt(e);
            ProcessBuilder pb;
            System.out.println(System.getProperty("os.name"));
            if (System.getProperty("os.name").startsWith("Windows")) {
                pb = new ProcessBuilder("neural.exe");
                Dialog.showInfo("Detected Windows", "no problem yet");
            } else {
                pb = new ProcessBuilder("./neural.out");
            }
            Process p = pb.start();
//            InputStream is = p.getInputStream();
            
            for (int i = 0; i < 1000; i++) {
                System.err.println("aguardadndo processo externo");
            }
            

            Ensaio ensaioSimulado = cloneEnsaio(e);
            
            List<String> lines = readSmallTextFile("neural.txt");
            String[] in = lines.get(0).split(";");
            
            
            Coleta coleta;
            List<Coleta> coletas = new LinkedList<>();
            
            //criando as coletas e alimentando com os valores lidos no txt
            for (int i = 0; i < 256; i++) {
                coleta = new Coleta();
                coleta.setValor(Float.parseFloat(in[i].replaceAll(";", "")));
                coleta.setEnsaio(ensaioSimulado);
                coletas.add(coleta);
            }

            int contador = 0;
            for (int linha = 0; linha < 16; linha++) {
                for (int coluna = 0; coluna < 16; coluna++) {
                    coletas.get(contador).setLinha(linha);
                    coletas.get(contador).setColuna(coluna);
                    contador++;
                }
            }

            return coletas;

        } catch (IOException ex) {
            Logger.getLogger(RedeNeuralImpl.class.getName()).log(Level.SEVERE, null, ex);
            Dialog.showError(ex.getCause().toString(), ex.getMessage());
        } catch (IndexOutOfBoundsException ie) {
            Logger.getLogger(RedeNeuralImpl.class.getName()).log(Level.SEVERE, null, "Erro ao ler arquivo!! Não foi possível ler o arquivo de coletas!\"");
//            Dialog.showError("Erro ao ler arquivo", "Não foi possível ler o arquivo de coletas!");
        }

        return null;
    }

    public List<String> readSmallTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public void createTxt(Ensaio e) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("neural.txt", StandardCharsets.UTF_8.toString());
            StringBuilder sb = new StringBuilder();
            sb.append(normalizaBocal(e));
            sb.append(" ");
            sb.append(normalizaPressao(e));
            sb.append(" ");
            sb.append(normalizaVelocidade(e));
            sb.append(" ");
            sb.append(normalizaDirecao(e));
            writer.println(sb.toString());
            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(RedeNeuralImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Float normalizaBocal(Ensaio e) {
        try {
            float bocal = Float.parseFloat(e.getBocal().getDescricao().replaceAll("\\D+", ""));
            float quebraJato = Float.parseFloat(e.getQuebraJato().getDescricao().replaceAll("\\D+", ""));

            return (float) (((bocal + quebraJato) - 5.0) / 2.2);

        } catch (Exception ex) {
            Dialog.showError("Erro ao normalizar Bocais", "Necessário ajustar a descrição do bocal e quebra jato, manter somente número ex:2.4.");
        }

        return null;

    }

    public Float normalizaPressao(Ensaio e) {
        try {
            float pressao = Float.parseFloat(e.getPressao().replaceAll("kgf/cm2", ""));

            return (float) ((pressao - 2.0) / 1.5);

        } catch (Exception ex) {
            Dialog.showError("Erro ao normalizar Pressao", "Necessário ajustar a pressão, manter somente número ex:2.4.");
        }
        return null;
    }

    public Float normalizaVelocidade(Ensaio e) {
        return (float) (e.getVelocidadeVento() / 1.65);
    }

    public Float normalizaDirecao(Ensaio e) {
        return (float) ((e.getDirecaoVentoGraus() - 22.5) / 315);
    }

    public Float normalizaColeta(Coleta c) {
        return (float) (c.getValor() / 15.5);
    }

    private Ensaio cloneEnsaio(Ensaio e) {
        e.setId(null);
        e.setVersion(null);
        e.setDescricao(e.getDescricao()+" simulado");
        return e;
        
    }
}