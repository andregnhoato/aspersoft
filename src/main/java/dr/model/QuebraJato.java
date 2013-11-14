package dr.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;


/**
 * @author @Andre
 * Rntidade Bocal representa o quebra jato utilizado nos ensaios e tabela desempenho aspersor pingo
 */

@Table(uniqueConstraints={@UniqueConstraint(columnNames={"descricao"})})
@Entity(name = "quebra_jato")
public class QuebraJato implements Bean, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull 
    @Column
    private String descricao;
   
    @Version
    private Integer version;
    
    public QuebraJato() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
}