package org.f24.dto.form;

import org.f24.dto.component.TaxPayer;
import org.f24.dto.component.TreasurySection;
import org.f24.dto.component.Header;
import org.f24.dto.component.InpsSection;
import org.f24.dto.component.LocalTaxSection;
import org.f24.dto.component.PaymentDetails;
import org.f24.dto.component.RegionSection;

public class F24Form {

    private Header header;

    private TaxPayer taxPayer;

    private PaymentDetails paymentDetails;

    private TreasurySection treasurySection;

    private InpsSection inpsSection;

    private RegionSection regionSection;

    private LocalTaxSection localTaxSection;

    public F24Form() {
    }

    /**
     * Constructs expanded list of components for F24 forms
     *
     * @param header          Header component
     * @param taxPayer        TaxPayer component
     * @param paymentDetails  PaymentDetails component
     * @param treasurySection TreasurySection component
     * @param inpsSection     InpsSection component
     * @param regionSection   RegionSection component
     * @param localTaxSection LocalTaxSection component
     */
    public F24Form(Header header, TaxPayer taxPayer, PaymentDetails paymentDetails, TreasurySection treasurySection,
            InpsSection inpsSection, RegionSection regionSection, LocalTaxSection localTaxSection) {
        this.header = header;
        this.taxPayer = taxPayer;
        this.paymentDetails = paymentDetails;
        this.treasurySection = treasurySection;
        this.inpsSection = inpsSection;
        this.regionSection = regionSection;
        this.localTaxSection = localTaxSection;
    }

    /**
     * Constructs basic list of components for F24 forms
     *
     * @param header         Header component
     * @param taxPayer       TaxPayer component
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

    public TreasurySection getTreasurySection() {
        return treasurySection;
    }

    public void setTreasurySection(TreasurySection treasurySection) {
        this.treasurySection = treasurySection;
    }

    public InpsSection getInpsSection() {
        return inpsSection;
    }

    public void setInpsSection(InpsSection inpsSection) {
        this.inpsSection = inpsSection;
    }

    public RegionSection getRegionSection() {
        return regionSection;
    }

    public void setRegionSection(RegionSection regionSection) {
        this.regionSection = regionSection;
    }

    public LocalTaxSection getLocalTaxSection() {
        return localTaxSection;
    }

    public void setLocalTaxSection(LocalTaxSection localTaxSection) {
        this.localTaxSection = localTaxSection;
    }

}
