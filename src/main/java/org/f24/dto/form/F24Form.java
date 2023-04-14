package org.f24.dto.form;

import org.f24.dto.component.Contributor;
import org.f24.dto.component.Header;
import org.f24.dto.component.PaymentDetails;

import javax.validation.Valid;
import java.awt.image.BufferedImage;

public class F24Form {

    private Header header;

    @Valid
    private Contributor contributor;

    @Valid
    private PaymentDetails paymentDetails;

    /**
     * Constructs basic components of F24 forms
     *
     * @param header         Header component
     * @param contributor    Contributor component
     * @param paymentDetails PaymentDetails component
     */
    public F24Form(Header header, Contributor contributor, PaymentDetails paymentDetails) {
        this.header = header;
        this.contributor = contributor;
        this.paymentDetails = paymentDetails;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

}
