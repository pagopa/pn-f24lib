package org.f24.dto.component;

import java.util.Date;

public class PaymentDetails {

    private Date dateOfPayment;
    private String company;
    private String bankID;
    private String checkNumber;
    private String bank;
    private String circular;
    private String abiCode;
    private String ibanCode;

    /**
     * Constructs payment details section of F24 form
     *
     * @param dateOfPayment date of payment (data)
     * @param company       company (azienda)
     * @param bankID        bank ID (CAB/Sportello)
     * @param checkNumber   check number of payment (pagamento effettuato con assegno n.ro)
     * @param bank          bank (bancario/postale)
     * @param circular      circular (circolare/vaglia postale)
     * @param abiCode       ABI code (Cod. ABI)
     * @param ibanCode      IBAN code (autorizzo addebito su conto corrente codice IBAN )
     */
    public PaymentDetails(Date dateOfPayment, String company, String bankID, String checkNumber, String bank, String circular, String abiCode, String ibanCode) {
        this.dateOfPayment = dateOfPayment;
        this.company = company;
        this.bankID = bankID;
        this.checkNumber = checkNumber;
        this.bank = bank;
        this.circular = circular;
        this.abiCode = abiCode;
        this.ibanCode = ibanCode;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCircular() {
        return circular;
    }

    public void setCircular(String circular) {
        this.circular = circular;
    }

    public String getAbiCode() {
        return abiCode;
    }

    public void setAbiCode(String abiCode) {
        this.abiCode = abiCode;
    }

    public String getIbanCode() {
        return ibanCode;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

}
