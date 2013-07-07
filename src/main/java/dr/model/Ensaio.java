package dr.model;

import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author @Andre
 */
@Entity
@Table(name = "ensaio")
public class Ensaio implements Bean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull @Size(min=1, max=200)
    private String descricao;
    
    @NotNull @Size(min=1, max=200)
    private String pressao;
    
    @NotNull @Size(min=1, max=200)
    private String bocal;
    
    @NotNull @Size(min=1, max=200)
    @Column(name = "quebra_jato")
    private String quebraJato;
    
    @NotNull @Size(min=1, max=200)
    private String inicio;
    
    @NotNull 
    private String duracao;
    
    @NotNull
    @Column(name = "grid_altura")
    private Integer gridAltura;
    
    @NotNull
    @Column(name = "grid_largura")
    private Integer gridLargura;
    
    @NotNull
    private Date data;
    
    @Column(name = "velocidade_vento")
    private float velocidadeVento;
    
    @Column(name = "direcao_vento")
    private String direcaoVento;
    
       
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Mercadoria.
     */
    @Version
    private Integer version;
    
    
    
    public Ensaio() {
    }

   @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version =  version;
    }

    public String getPressao() {
        return pressao;
    }

    public void setPressao(String pressao) {
        this.pressao = pressao;
    }

    public String getBocal() {
        return bocal;
    }

    public void setBocal(String bocal) {
        this.bocal = bocal;
    }

    public String getQuebraJato() {
        return quebraJato;
    }

    public void setQuebraJato(String quebraJato) {
        this.quebraJato = quebraJato;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Integer getGridAltura() {
        return gridAltura;
    }

    public void setGridAltura(Integer gridAltura) {
        this.gridAltura = gridAltura;
    }

    public Integer getGridLargura() {
        return gridLargura;
    }

    public void setGridLargura(Integer gridLargura) {
        this.gridLargura = gridLargura;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getVelocidadeVento() {
        return velocidadeVento;
    }

    public void setVelocidadeVento(float velocidadeVento) {
        this.velocidadeVento = velocidadeVento;
    }

    public String getDirecaoVento() {
        return direcaoVento;
    }

    public void setDirecaoVento(String direcaoVento) {
        this.direcaoVento = direcaoVento;
    }
}