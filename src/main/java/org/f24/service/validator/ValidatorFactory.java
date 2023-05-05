package org.f24.service.validator;

import org.f24.dto.form.*;
import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;
import org.f24.service.validator.impl.*;

public class ValidatorFactory {

    public static Validator createValidator(F24Form form) throws ResourceException {
        if (form instanceof F24Excise f24Excise)
            return new ExciseValidator("schemas/form/f24excise.json", f24Excise);
        else if (form instanceof F24Elid f24elid)
            return new ElidValidator("schemas/form/f24elid.json", f24elid);
        else if (form instanceof F24Standard f24standard)
            return new StandardValidator("schemas/form/f24standard.json", f24standard);
        else if (form instanceof F24Simplified f24simplified)
            return new SimplifiedValidator("schemas/form/f24simplified.json", f24simplified);
        else if (form != null) return new FormValidator("schemas/form/f24form.json",form);
        else
            throw new ResourceException(ErrorEnum.VALIDATOR_OBSOLETE.getMessage());
    }

}
