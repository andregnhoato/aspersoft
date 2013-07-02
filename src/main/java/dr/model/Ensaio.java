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
@Table(name = "experiment")
public class Experiment implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull(message = "Pressão") @Size(min=1, max=200)
    private String pressure;
    
    @NotNull @Size(min=1, max=200)
    private String nozzle;
    
    @NotNull @Size(min=1, max=200)
    @Column(name = "jet_break")
    private String jetBreak;
    
    @NotNull @Size(min=1, max=200)
    private String start;
    
    @NotNull 
    private String duration;
    
    @NotNull
    @Column(name = "grid_height")
    private Integer gridHeight;
    
    @NotNull
    @Column(name = "grid_width")
    private Integer gridWidth;
    
    @NotNull
    private Date date;
    
       
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Mercadoria.
     */
    @Version
    private Integer version;
    
    
    
    public Experiment() {
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
    
    public void setVersion(Integer version) {
        this.version =  version;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getNozzle() {
        return nozzle;
    }

    public void setNozzle(String nozzle) {
        this.nozzle = nozzle;
    }

    public String getJetBreak() {
        return jetBreak;
    }

    public void setJetBreak(String jetBreak) {
        this.jetBreak = jetBreak;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(Integer gridHeight) {
        this.gridHeight = gridHeight;
    }

    public Integer getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(Integer gridWidth) {
        this.gridWidth = gridWidth;
    }
         
}