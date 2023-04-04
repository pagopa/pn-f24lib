package org.f24.dto.form;

import org.f24.dto.component.Contributor;
import org.f24.dto.component.Header;
import org.f24.dto.component.PaymentDetails;
import org.f24.dto.component.TreasuryAndOtherSection;

import java.awt.image.BufferedImage;

public class F24Elid extends F24Form {

    private TreasuryAndOtherSection treasurySection;
    private String bankAccountNumber;
    private String abiCode;
    private String bankId;

    /**
     * Constructs F24 ELID dto.
     *
     * @param header            Header component
     * @param contributor       Contributor component
     * @param signature         signature (firma)
     * @param paymentDetails    PaymentDetails component
     * @param treasurySection   TreasuryAndOtherSection component
     * @param bankAccountNumber bank account debit authorization n (autorizzo addebito su conto corrente bancario n°)
     * @param abiCode           ABI code (cod. ABI)
     * @param bankId            bank ID (CAB)
     */
    public F24Elid(Header header, Contributor contributor, BufferedImage signature, PaymentDetails paymentDetails, TreasuryAndOtherSection treasurySection, String bankAccountNumber, String abiCode, String bankId) {
        super(header, contributor, signature, paymentDetails);
        this.treasurySection = treasurySection;
        this.bankAccountNumber = bankAccountNumber;
        this.abiCode = abiCode;
        this.bankId = bankId;
    }

}
