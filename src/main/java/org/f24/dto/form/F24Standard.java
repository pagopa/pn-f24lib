package org.f24.dto.form;

import org.f24.dto.component.*;

public class F24Standard extends F24Form {

    private SocialSecuritySection socialSecuritySection;
    private String ibanCode;

    public F24Standard() {
        super();
    }

    /**
     * Constructs F24 Standard dto.
     *
     * @param header                Header component
     * @param taxPayer              TaxPayer component
     * @param paymentDetails        PaymentDetails component
     * @param treasurySection       TreasurySection component
     * @param inpsSection           InpsSection component
     * @param regionSection         RegionSection component
     * @param localTaxSection       LocalTaxSection component
     * @param socialSecuritySection SocialSecuritySection component
     * @param ibanCode              IBAN code (autorizzo addebito su conto corrente codice IBAN)
     */
    public F24Standard(Header header, TaxPayer taxPayer, PaymentDetails paymentDetails, TreasurySection treasurySection, InpsSection inpsSection, RegionSection regionSection, LocalTaxSection localTaxSection, SocialSecuritySection socialSecuritySection, String ibanCode) {
        super(header, taxPayer, paymentDetails, treasurySection, inpsSection, regionSection, localTaxSection);
        this.socialSecuritySection = socialSecuritySection;
        this.ibanCode = ibanCode;
    }

    public SocialSecuritySection getSocialSecuritySection() {
        return socialSecuritySection;
    }

    public void setSocialSecuritySection(SocialSecuritySection socialSecuritySection) {
        this.socialSecuritySection = socialSecuritySection;
    }

    public String getIbanCode() {
        return ibanCode;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

}
