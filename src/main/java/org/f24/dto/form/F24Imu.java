package org.f24.dto.form;

import org.f24.dto.component.*;

import java.awt.image.BufferedImage;

public class F24Imu extends F24Form {

    private TreasurySection treasurySection;
    private InpsSection inpsSection;
    private RegionSection regionSection;
    private ImuSection imuSection;
    private SocialSecuritySection securitySection;
    private String ibanCode;

    /**
     * Constructs F24 IMU dto.
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
    public F24Imu(Header header, Contributor contributor, BufferedImage signature, PaymentDetails paymentDetails, TreasurySection treasurySection, InpsSection inpsSection, RegionSection regionSection, ImuSection imuSection, SocialSecuritySection securitySection, String ibanCode) {
        super(header, contributor, signature, paymentDetails);
        this.treasurySection = treasurySection;
        this.inpsSection = inpsSection;
        this.regionSection = regionSection;
        this.imuSection = imuSection;
        this.securitySection = securitySection;
        this.ibanCode = ibanCode;
    }

}
