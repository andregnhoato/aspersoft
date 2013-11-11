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
 * @author @Andre
 * os valores x e y representam o posicionameno da coleta no grid
 */

@Table//(uniqueConstraints=@UniqueConstraint(columnNames={"linha, coluna"}))
@Entity(name = "coleta")
public class Coleta implements Bean, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull 
    @Column
    private Float valor;
    
    @NotNull
    @Column
    private Integer linha;
    
    @NotNull
    @Column
    private Integer coluna;
     
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ensaio", referencedColumnName = "id")
    private Ensaio ensaio;
    
    @Version
    private Integer version;
    
    public Coleta() {
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

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Ensaio getEnsaio() {
        return ensaio;
    }

    public void setEnsaio(Ensaio ensaio) {
        this.ensaio = ensaio;
    }

    public Integer getLinha() {
        return linha;
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public Integer getColuna() {
        return coluna;
    }

    public void setColuna(Integer coluna) {
        this.coluna = coluna;
    }
}