package dr.validation;

import dr.model.QuebraJato;
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
public class QuebraJatoValidator implements Validator<QuebraJato> {

    private static ValidatorFactory factory;

    static {
        factory = buildDefaultValidatorFactory();
    }

    @Override
    public String validate(QuebraJato b) {
        StringBuilder sb = new StringBuilder();
        if (b != null) {
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<QuebraJato>> constraintViolations = validator.validate(b);

            if (!constraintViolations.isEmpty()) {
                sb.append("Validação da entidade QuebraJato\n");
                for (ConstraintViolation<QuebraJato> constraint : constraintViolations) {
                    sb.append(String.format("%n%s: %s", constraint.getPropertyPath(), constraint.getMessage()));
                }
            }
        }
        return sb.toString();
    }
}
