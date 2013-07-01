package dr.validation;

/**
 * valida o estado (dados) de uma entidade persistente.
 *
 * @author andre
 *
 * @param <Entity> indica o tipo da Entidade.
 */
public interface Validator<Entity> {

    /**
     * Método que aplica o mecanismo de validação a entidade.
     *
     * @param e entidade a ser validada.
     * @return String <i>vazia</i>, caso não exista problemas de validação. Ou
     * retorna uma string com as mensagens de validação.
     */
    public String validate(Entity e);

}
