package dr.validation;

import dr.model.Experiment;
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
public class ExperimentValidator implements Validator<Experiment> {

    private static ValidatorFactory factory;

    static {
        factory = buildDefaultValidatorFactory();
    }

    @Override
    public String validate(Experiment e) {
        StringBuilder sb = new StringBuilder();
        if (e != null) {
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<Experiment>> constraintViolations = validator.validate(e);

            if (!constraintViolations.isEmpty()) {
                sb.append("Validação da entidade Experiment\n");
                for (ConstraintViolation<Experiment> constraint : constraintViolations) {
                    sb.append(String.format("%n%s: %s", constraint.getPropertyPath(), constraint.getMessage()));
                }
            }
        }
        return sb.toString();
    }
}
