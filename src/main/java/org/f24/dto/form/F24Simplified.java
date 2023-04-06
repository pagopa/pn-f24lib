package org.f24.dto.form;

import org.f24.dto.component.Contributor;
import org.f24.dto.component.Header;
import org.f24.dto.component.PaymentDetails;
import org.f24.dto.component.TreasuryAndOtherSection;

import java.awt.image.BufferedImage;

public class F24Simplified extends F24Form {

    private TreasuryAndOtherSection treasurySection;

    /**
     * Constructs F24 Simplified dto.
     *
     * @param header          Header component
     * @param contributor     Contributor component
     * @param signature       signature (firma)
     * @param paymentDetails  PaymentDetails component
     * @param treasurySection TreasuryAndOtherSection component

     */
    public F24Simplified(Header header, Contributor contributor, BufferedImage signature, PaymentDetails paymentDetails, TreasuryAndOtherSection treasurySection) {
        super(header, contributor, signature, paymentDetails);
        this.treasurySection = treasurySection;
    }

    public TreasuryAndOtherSection getTreasurySection() {
        return treasurySection;
    }

    public void setTreasurySection(TreasuryAndOtherSection treasurySection) {
        this.treasurySection = treasurySection;
    }

}
