package dr.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * @author @Andre Combinação entre os bocais para gerar a tabela de desempenho
 * aspersor pingo
 */
@Table//(uniqueConstraints=@UniqueConstraint(columnNames={"linha, coluna"}))
@Entity(name = "combinacao")
public class Combinacao implements Bean, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bocal", referencedColumnName = "id")
    private Bocal bocal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_quebra_jato", referencedColumnName = "id")
    private QuebraJato quebraJato;
    @NotNull
    @Column
    private Float pressao;
    @NotNull
    @Column
    private Float vazao;
    @NotNull
    @Column
    private Float diametroIrrigado;
    @NotNull
    @Column
    private Float altura;
    @NotNull
    @Column
    private Float largura;
    @Column
    private Float peq;
    @Version
    private Integer version;

    public Combinacao() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public Float getPressao() {
        return pressao;
    }

    public void setPressao(Float pressao) {
        this.pressao = pressao;
    }

    public Float getVazao() {
        return vazao;
    }

    public void setVazao(Float vazao) {
        this.vazao = vazao;
    }

    public Float getDiametroIrrigado() {
        return diametroIrrigado;
    }

    public void setDiametroIrrigado(Float diametroIrrigado) {
        this.diametroIrrigado = diametroIrrigado;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Float getLargura() {
        return largura;
    }

    public void setLargura(Float largura) {
        this.largura = largura;
    }

    public Float getPeq() {
        return peq;
    }

    public void setPeq(Float peq) {
        this.peq = peq;
    }

    public Bocal getBocal() {
        return bocal;
    }

    public void setBocal(Bocal bocal) {
        this.bocal = bocal;
    }

    public QuebraJato getQuebraJato() {
        return quebraJato;
    }

    public void setQuebraJato(QuebraJato quebraJato) {
        this.quebraJato = quebraJato;
    }
    
}