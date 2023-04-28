package org.f24.dto.component;

public class PaymentDetails {

    private String paymentDate;

    private String company;

    private String cabCode;

    private String checkNumber;

    private boolean isBank;

    private String abiCode;

    private String ibanCode;

    public PaymentDetails() {}

    /**
     * Constructs payment details section of F24 form
     *
     * @param paymentDate date of payment (data)
     * @param company       company (azienda)
     * @param cabCode       bank CAB code (CAB/Sportello)
     * @param checkNumber   check number of payment (pagamento effettuato con assegno n.ro)
     * @param isBank        verify type of payment check (bancario/postale – circolare/vaglia postale)
     * @param abiCode       ABI code (Cod. ABI)
     * @param ibanCode      IBAN code (autorizzo addebito su conto corrente codice IBAN )
     */
    public PaymentDetails(String paymentDate, String company, String cabCode, String checkNumber, boolean isBank, String abiCode, String ibanCode) {
        this.paymentDate = paymentDate;
        this.company = company;
        this.cabCode = cabCode;
        this.checkNumber = checkNumber;
        this.isBank = isBank;
        this.abiCode = abiCode;
        this.ibanCode = ibanCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCabCode() {
        return cabCode;
    }

    public void setCabCode(String cabCode) {
        this.cabCode = cabCode;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean bank) {
        isBank = bank;
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
