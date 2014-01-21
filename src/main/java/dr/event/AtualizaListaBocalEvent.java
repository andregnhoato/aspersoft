package dr.event;

/**
 * Evento deve ser gerado quando for necessário atualizar a tabela de Bocais.
 * 
 * @author @Andre
 */
public class AtualizaListaBocalEvent extends AbstractEvent<Object> {
    
    public AtualizaListaBocalEvent() {
        super(null);
    }
    
}
