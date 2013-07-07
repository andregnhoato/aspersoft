package dr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity
@Table(name = "coleta")
public class Coleta implements Bean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull 
    private Float value;
    
    @NotNull
    private Integer x;
    
    @NotNull
    private Integer y;
    
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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Ensaio getEnsaio() {
        return ensaio;
    }

    public void setEnsaio(Ensaio ensaio) {
        this.ensaio = ensaio;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}