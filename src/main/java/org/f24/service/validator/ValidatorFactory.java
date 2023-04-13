package org.f24.service.validator;

import org.f24.dto.form.*;
import org.f24.service.validator.impl.ElidValidator;
import org.f24.service.validator.impl.ExciseValidator;
import org.f24.service.validator.impl.StandardValidator;
import org.f24.service.validator.impl.SimplifiedValidator;

public class ValidatorFactory {

    public static Validator createValidator(F24Form form) {
        if (form instanceof F24Excise)
            return new ExciseValidator((F24Excise) form);
        else if (form instanceof F24Elid)
            return new ElidValidator((F24Elid) form);
        else if (form instanceof F24Standard)
            return new StandardValidator((F24Standard) form);
        else if (form instanceof F24Simplified)
            return new SimplifiedValidator((F24Simplified) form);
            //ToDo throw exception
        else return null;
    }

}
