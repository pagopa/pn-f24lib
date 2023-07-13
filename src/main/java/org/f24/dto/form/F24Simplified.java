package org.f24.dto.form;

import org.f24.dto.component.*;

public class F24Simplified extends F24Form {

    private PaymentReasonSection paymentReasonSection;

    public F24Simplified() {
        super();
    }

    /**
     * Constructs F24 Simplified dto.
     *
     * @param header               Header component
     * @param taxPayer             TaxPayer component
     * @param paymentDetails       PaymentDetails component
     * @param paymentReasonSection PaymentReasonSection component
     */
    public F24Simplified(Header header, TaxPayer taxPayer, PaymentDetails paymentDetails, PaymentReasonSection paymentReasonSection) {
        super(header, taxPayer, paymentDetails);
        this.paymentReasonSection = paymentReasonSection;
    }

    public PaymentReasonSection getPaymentReasonSection() {
        return paymentReasonSection;
    }

    public void setPaymentReasonSection(PaymentReasonSection paymentReasonSection) {
        this.paymentReasonSection = paymentReasonSection;
    }

}
