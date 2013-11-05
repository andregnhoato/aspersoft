package dr.report;

import dr.model.Ensaio;
import java.util.List;

/**
 * Classe utilizada para alimentar o relatório
 * @author andregnhoato
 */
public class AnaliseJavaBeanDataSource {
    private Ensaio ensaio;
    private List<List<Float>> sobreposicao;
    private List<Float> perfil;
    private List<Float> distancia;
    private Float cuc;
    private Float cud;
    private Float cue;
    private Float dp;
    private Float media;
    private Float cv;

    public Ensaio getEnsaio() {
        return ensaio;
    }

    public void setEnsaio(Ensaio ensaio) {
        this.ensaio = ensaio;
    }

    public List<List<Float>> getSobreposicao() {
        return sobreposicao;
    }

    public void setSobreposicao(List<List<Float>> sobreposicao) {
        this.sobreposicao = sobreposicao;
    }

    public List<Float> getPerfil() {
        return perfil;
    }

    public void setPerfil(List<Float> perfil) {
        this.perfil = perfil;
    }

    public List<Float> getDistancia() {
        return distancia;
    }

    public void setDistancia(List<Float> distancia) {
        this.distancia = distancia;
    }

    public Float getCuc() {
        return cuc;
    }

    public void setCuc(Float cuc) {
        this.cuc = cuc;
    }

    public Float getCud() {
        return cud;
    }

    public void setCud(Float cud) {
        this.cud = cud;
    }

    public Float getCue() {
        return cue;
    }

    public void setCue(Float cue) {
        this.cue = cue;
    }

    public Float getDp() {
        return dp;
    }

    public void setDp(Float dp) {
        this.dp = dp;
    }

    public Float getMedia() {
        return media;
    }

    public void setMedia(Float media) {
        this.media = media;
    }

    public Float getCv() {
        return cv;
    }

    public void setCv(Float cv) {
        this.cv = cv;
    }
}
