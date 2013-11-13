package dr.validation;

import dr.model.Bocal;
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
public class BocalValidator implements Validator<Bocal> {

    private static ValidatorFactory factory;

    static {
        factory = buildDefaultValidatorFactory();
    }

    @Override
    public String validate(Bocal b) {
        StringBuilder sb = new StringBuilder();
        if (b != null) {
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<Bocal>> constraintViolations = validator.validate(b);

            if (!constraintViolations.isEmpty()) {
                sb.append("Validação da entidade Bocal\n");
                for (ConstraintViolation<Bocal> constraint : constraintViolations) {
                    sb.append(String.format("%n%s: %s", constraint.getPropertyPath(), constraint.getMessage()));
                }
            }
        }
        return sb.toString();
    }
}
