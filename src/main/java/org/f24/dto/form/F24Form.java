package org.f24.dto.form;

import org.f24.dto.component.TaxPayer;
import org.f24.dto.component.Header;
import org.f24.dto.component.PaymentDetails;

public class F24Form {

    private Header header;

    private TaxPayer taxPayer;

    private PaymentDetails paymentDetails;

    public F24Form() {
    }

    /**
     * Constructs basic components of F24 forms
     *
     * @param header         Header component
     * @param taxPayer       Contributor component
     * @param paymentDetails PaymentDetails component
     */
    public F24Form(Header header, TaxPayer taxPayer, PaymentDetails paymentDetails) {
        this.header = header;
        this.taxPayer = taxPayer;
        this.paymentDetails = paymentDetails;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public TaxPayer getTaxPayer() {
        return taxPayer;
    }

    public void setTaxPayer(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

}
