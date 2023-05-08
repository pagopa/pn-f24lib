package org.f24.dto.form;

import org.f24.dto.component.TaxPayer;
import org.f24.dto.component.Header;
import org.f24.dto.component.PaymentDetails;
import org.f24.dto.component.TreasuryAndOtherSection;

public class F24Elid extends F24Form {

    private TreasuryAndOtherSection treasurySection;

    private String bankAccountNumber;
    private String abiCode;
    private String bankId;

    /**
     * Constructs F24 ELID dto.
     *
     * @param header            Header component
     * @param taxPayer          Contributor component
     * @param paymentDetails    PaymentDetails component
     * @param treasurySection   TreasuryAndOtherSection component
     * @param bankAccountNumber bank account debit authorization n (autorizzo addebito su conto corrente bancario n°)
     * @param abiCode           ABI code (cod. ABI)
     * @param bankId            bank ID (CAB)
     */
    public F24Elid(Header header, TaxPayer taxPayer, PaymentDetails paymentDetails, TreasuryAndOtherSection treasurySection, String bankAccountNumber, String abiCode, String bankId) {
        super(header, taxPayer, paymentDetails);
        this.treasurySection = treasurySection;
        this.bankAccountNumber = bankAccountNumber;
        this.abiCode = abiCode;
        this.bankId = bankId;
    }

    public TreasuryAndOtherSection getTreasurySection() {
        return treasurySection;
    }

    public void setTreasurySection(TreasuryAndOtherSection treasurySection) {
        this.treasurySection = treasurySection;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getAbiCode() {
        return abiCode;
    }

    public void setAbiCode(String abiCode) {
        this.abiCode = abiCode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

}
