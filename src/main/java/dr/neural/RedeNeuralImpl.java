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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andregnhoato
 */
public class RedeNeuralImpl implements IRedeNeural {

    public List<Float> rede(Ensaio e) {
        createTxt(e);
        ProcessBuilder pb = new ProcessBuilder("./a.out");
        try {
            Process p = pb.start();

            List<String> lines = readSmallTextFile("saida.txt");
            System.out.println(lines.get(1).toString());
            
        } catch (IOException ex) {
            Logger.getLogger(RedeNeuralImpl.class.getName()).log(Level.SEVERE, null, ex);
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
            writer = new PrintWriter("saida.txt", StandardCharsets.UTF_8.toString());
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
            float bocal = Float.parseFloat(e.getBocal().getDescricao());
            float quebraJato = Float.parseFloat(e.getQuebraJato().getDescricao());

            return (float) (((bocal + quebraJato) - 5.0) / 2.2);

        } catch (Exception ex) {
            Dialog.showError("Erro ao normalizar Bocais", "Necessário ajustar a descrição do bocal e quebra jato, manter somente número ex:2.4.");
        }

        return null;

    }

    public Float normalizaPressao(Ensaio e) {
        try {
            float pressao = Float.parseFloat(e.getPressao());

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
}