package dr.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * @author @Andre Modelo que representa as configurações do sistema
 */
@Table//(uniqueConstraints=@UniqueConstraint(columnNames={"linha, coluna"}))
@Entity(name = "configuracao")
public class Configuracao implements Bean, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Column
    private Integer casasDecimaisColeta;
    @NotNull
    @Column
    private Integer casasDecimaisSobreposicao;
    @NotNull
    @Column
    private Integer casasDecimaisUniformidade;
    @NotNull
    @Column
    private Integer casasDecimaisDadosEstatisticos;
    
    @Version
    private Integer version;

    public Configuracao() {
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
    
    

    public Integer getCasasDecimaisColeta() {
        return casasDecimaisColeta;
    }

    public void setCasasDecimaisColeta(Integer casasDecimaisColeta) {
        this.casasDecimaisColeta = casasDecimaisColeta;
    }

    public Integer getCasasDecimaisSobreposicao() {
        return casasDecimaisSobreposicao;
    }

    public void setCasasDecimaisSobreposicao(Integer casasDecimaisSobreposicao) {
        this.casasDecimaisSobreposicao = casasDecimaisSobreposicao;
    }

    public Integer getCasasDecimaisUniformidade() {
        return casasDecimaisUniformidade;
    }

    public void setCasasDecimaisUniformidade(Integer casasDecimaisUniformidade) {
        this.casasDecimaisUniformidade = casasDecimaisUniformidade;
    }

    public Integer getCasasDecimaisDadosEstatisticos() {
        return casasDecimaisDadosEstatisticos;
    }

    public void setCasasDecimaisDadosEstatisticos(Integer casasDecimaisDadosEstatisticos) {
        this.casasDecimaisDadosEstatisticos = casasDecimaisDadosEstatisticos;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}