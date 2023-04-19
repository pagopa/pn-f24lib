package org.f24.dto.form;

import org.f24.dto.component.*;

public class F24Simplified extends F24Form {

    private PaymentMotiveSection paymentMotiveSection;

    public F24Simplified() {
        super();
    }

    /**
     * Constructs F24 Simplified dto.
     *
     * @param header               Header component
     * @param contributor          Contributor component
     * @param paymentDetails       PaymentDetails component
     * @param paymentMotiveSection PaymentMotiveSection component
     */
    public F24Simplified(Header header, Contributor contributor, PaymentDetails paymentDetails, PaymentMotiveSection paymentMotiveSection) {
        super(header, contributor, paymentDetails);
        this.paymentMotiveSection = paymentMotiveSection;
    }

    public PaymentMotiveSection getPaymentMotiveSection() {
        return paymentMotiveSection;
    }

    public void setPaymentMotiveSection(PaymentMotiveSection paymentMotiveSection) {
        this.paymentMotiveSection = paymentMotiveSection;
    }

}
