package dr.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe de modelo que representa uma mercadoria.
 * 
 * <p>A <code>mercadoria</code> é um objeto persistido no banco de dados, por isso a classificamos como <strong>Entidade</strong>.</p>
 * 
 * <p>Por se tratar de uma Entidade persistente, a <code>Mercadoria</code> utiliza as anotações <code>JPA</code> para definir o mapeamento <code>ORM</code>.
 * 
 * <p>As funcionalidades desse sistema demonstração são concentradas no cadastro (CRUD) de mercadorias.</p>
 * 
 * <p>
 *  Outra característica dessa classe, é o uso de anotações do Bean Validations para validar o estado (dados) da <code>Mercadoria</code>.
 *  Bean Validations (JSR 303) é uma especificação Java para habilitar a validação de dados via o uso de anotações. O principal provider
 *  dessa API é o <code>Hibernate Validator</code>.
 * </p>
 *
 * @author YaW Tecnologia
 */

public class Mercadoria implements AbstractEntity {

    /**
     * Chave primária da entidade <code>Mercadoria</code>. O valor gerado pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull @Size(min=5, max=200)
    private String nome;
    
    private String descricao;
    
    @NotNull @Min(value=1)
    private Integer quantidade;
    
    @NotNull @Min(value=1)
    private Double preco;
    
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Mercadoria.
     */
    @Version
    private Integer version;
    
    private static final NumberFormat numberFmt = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

    public Mercadoria() {
    }

    public Mercadoria(Integer id, String nome, String descricao, Integer quantidade, Double preco, Integer version) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.version = version;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getPrecoFormatado() {
        return convertPrecoToString(this.preco);
    }

    public Integer getVersion() {
        return version;
    }

    public static String convertPrecoToString(double preco) {
        return numberFmt.format(preco);
    }

    public static double formatStringToPreco(String strPreco) throws ParseException {
        return numberFmt.parse(strPreco).doubleValue();
    }

    @Override
    public String toString() {
        return "[ " + nome + " - " + descricao + " - " + quantidade + " - " + preco + " ]";
    }
}