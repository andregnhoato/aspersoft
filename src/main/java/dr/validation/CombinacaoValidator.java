package dr.validation;

import dr.model.Combinacao;
import static javax.validation.Validation.buildDefaultValidatorFactory;


import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;

/**
 * Implementa componente para validar os dados da entidade <code>Mercadoria</code>.
 *
 * <p>A validação ocorre através do Bean Validations, mecanismo de validação
 * padrão do Java baseado em anotações.</p>
 *
 * @author @Andre
 */
public class CombinacaoValidator implements Validator<Combinacao> {

    private static ValidatorFactory factory;

    static {
        factory = buildDefaultValidatorFactory();
    }

    @Override
    public String validate(Combinacao e) {
        StringBuilder sb = new StringBuilder();
        if (e != null) {
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<Combinacao>> constraintViolations = validator.validate(e);

            if (!constraintViolations.isEmpty()) {
                sb.append("Validação da entidade Combinacao\n");
                for (ConstraintViolation<Combinacao> constraint : constraintViolations) {
                    sb.append(String.format("%n%s: %s", constraint.getPropertyPath(), constraint.getMessage()));
                }
            }
        }
        return sb.toString();
    }
}
