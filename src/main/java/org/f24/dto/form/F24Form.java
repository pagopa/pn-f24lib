package org.f24.dto.form;

import org.f24.dto.component.Contributor;
import org.f24.dto.component.Header;
import org.f24.dto.component.PaymentDetails;

import java.awt.image.BufferedImage;

public class F24Form {

    private Header header;
    private Contributor contributor;
    private BufferedImage signature;
    private PaymentDetails paymentDetails;

    /**
     * Constructs basic components of F24 forms
     *
     * @param header         Header component
     * @param contributor    Contributor component
     * @param signature      signature (firma)
     * @param paymentDetails PaymentDetails component
     */
    public F24Form(Header header, Contributor contributor, BufferedImage signature, PaymentDetails paymentDetails) {
        this.header = header;
        this.contributor = contributor;
        this.signature = signature;
        this.paymentDetails = paymentDetails;
    }

}
