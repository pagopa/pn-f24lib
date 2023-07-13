package org.f24.dto.form;

import org.f24.dto.component.*;

public class F24Excise extends F24Form {

    private ExciseSection exciseSection;
    private String ibanCode;

    public F24Excise() {
        super();
    }

    /**
     * Constructs F24 Excise dto.
     *
     * @param header          Header component
     * @param taxPayer        TaxPayer component
     * @param paymentDetails  PaymentDetails component
     * @param treasurySection TreasurySection component
     * @param inpsSection     InpsSection component
     * @param regionSection   RegionSection component
     * @param localTaxSection LocalTaxSection component
     * @param exciseSection   ExciseSection component
     * @param ibanCode        IBAN code (autorizzo addebito su conto corrente codice IBAN)
     */
    public F24Excise(Header header, TaxPayer taxPayer, PaymentDetails paymentDetails, TreasurySection treasurySection, InpsSection inpsSection, RegionSection regionSection, LocalTaxSection localTaxSection, ExciseSection exciseSection, String ibanCode) {
        super(header, taxPayer, paymentDetails, treasurySection, inpsSection, regionSection, localTaxSection);
        this.exciseSection = exciseSection;
        this.ibanCode = ibanCode;
    }

    public ExciseSection getExciseSection() {
        return exciseSection;
    }

    public void setExciseSection(ExciseSection exciseSection) {
        this.exciseSection = exciseSection;
    }

    public String getIbanCode() {
        return ibanCode;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

}
