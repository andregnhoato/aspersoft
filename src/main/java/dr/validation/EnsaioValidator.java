package dr.validation;

import dr.model.Ensaio;
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
 * @author YaW Tecnologia
 */
public class EnsaioValidator implements Validator<Ensaio> {

    private static ValidatorFactory factory;

    static {
        factory = buildDefaultValidatorFactory();
    }

    @Override
    public String validate(Ensaio e) {
        StringBuilder sb = new StringBuilder();
        if (e != null) {
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<Ensaio>> constraintViolations = validator.validate(e);

            if (!constraintViolations.isEmpty()) {
                sb.append("Validação da entidade Ensaio\n");
                for (ConstraintViolation<Ensaio> constraint : constraintViolations) {
                    sb.append(String.format("%n%s: %s", constraint.getPropertyPath(), constraint.getMessage()));
                }
            }
        }
        return sb.toString();
    }
}