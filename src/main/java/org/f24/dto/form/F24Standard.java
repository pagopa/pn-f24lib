package org.f24.dto.form;

import org.f24.dto.component.*;

import javax.validation.Valid;
import java.awt.image.BufferedImage;

public class F24Standard extends F24Form {

    @Valid
    private TreasurySection treasurySection;

    @Valid
    private InpsSection inpsSection;

    private RegionSection regionSection;
    private ImuSection imuSection;
    private SocialSecuritySection securitySection;
    private String ibanCode;

    /**
     * Constructs F24 Standard dto.
     *
     * @param header          Header component
     * @param contributor     Contributor component
     * @param signature       signature (firma)
     * @param paymentDetails  PaymentDetails component
     * @param treasurySection TreasurySection component
     * @param inpsSection     InpsSection component
     * @param regionSection   RegionSection component
     * @param imuSection      ImuSection component
     * @param securitySection SocialSecuritySection component
     * @param ibanCode        IBAN code (autorizzo addebito su conto corrente codice IBAN)
     */
    public F24Standard(Header header, Contributor contributor, BufferedImage signature, PaymentDetails paymentDetails, TreasurySection treasurySection, InpsSection inpsSection, RegionSection regionSection, ImuSection imuSection, SocialSecuritySection securitySection, String ibanCode) {
        super(header, contributor, signature, paymentDetails);
        this.treasurySection = treasurySection;
        this.inpsSection = inpsSection;
        this.regionSection = regionSection;
        this.imuSection = imuSection;
        this.securitySection = securitySection;
        this.ibanCode = ibanCode;
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

    public ImuSection getImuSection() {
        return imuSection;
    }

    public void setImuSection(ImuSection imuSection) {
        this.imuSection = imuSection;
    }

    public SocialSecuritySection getSecuritySection() {
        return securitySection;
    }

    public void setSecuritySection(SocialSecuritySection securitySection) {
        this.securitySection = securitySection;
    }

    public String getIbanCode() {
        return ibanCode;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

}
