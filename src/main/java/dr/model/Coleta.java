package dr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;


/**
 * @author @Andre
 * os valores x e y representam o posicionameno da coleta no grid
 */
@Entity
@Table(name = "coleta")
public class Coleta implements Bean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull 
    private Float valor;
    
    @Transient
    private String teste;
    
    @NotNull
    private Integer linha;
    
    @NotNull
    private Integer coluna;
    
    @JoinColumn(name = "id_ensaio", referencedColumnName = "id")
    @ManyToOne
    private Ensaio ensaio;
    
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Mercadoria.
     */
    @Version
    private Integer version;
    
    public Coleta() {
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
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