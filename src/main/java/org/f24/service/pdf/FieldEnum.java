package org.f24.service.pdf;

public enum FieldEnum {

    DELEGATION("delegation"),
    AGENCY("agency"),
    PROVINCE("province"),
    TAX_CODE("taxCode"),
    SURNAME("surname"),
    NAME("name"),
    DATE_OF_BIRTH("dateOfBirth"),
    SEX("sex"),
    MUNICIPALITY_OF_BIRTH("municipalityOfBirth"),
    MUNICIPALITY("municipality"),
    ADDRESS("address"),
    RECEIVER_TAX_CODE("receiverTaxCode"),
    ID_CODE("idCode"),
    TRIBUTE_CODE("tributeCode"),
    INSTALLMENT("installment"),
    REPORTING_YEAR("reportingYear"),
    OFFICE_CODE("officeCode"),
    ACT_CODE("actCode"),
    LOCATION_CODE("locationCode"),
    CONTRIBUTION_REASON("contributionReason"),
    INPS_CODE("inpsCode"),
    START_DATE("startDate"),
    END_DATE("endDate"),
    REGION_CODE("regionCode"),
    OPERATION_ID("operationId"),
    MUNICIPALITY_CODE("municipalityCode"),
    ACTIVE_REPENTANCE("activeRepentance"),
    VARIED_BUILDINGS("variedBuildings"),
    ADVANCE_PAYMENT("advancePayment"),
    BALANCE("balance"),
    NUMBER_OF_BUILDINGS("numberOfBuildings"),
    DEDUCTION("deduction"),
    INSTITUTION("institution"),
    MONTH("month"),
    DATE_OF_PAYMENT("dateOfPayment"),
    COMPANY("company"),
    CAB_CODE("cabCode"),
    CHECK_NUMBER("checkNumber"),
    ABI_CODE("abiCode"),
    BANK("bank"),
    IBAN_CODE("ibanCode"),
    COMPANY_CODE("companyCode"),
    BANK_ACCOUNT("bankAccount"),
    REFERENCE_NUMBER("referenceNumber"),
    REASON("reason"),
    INSTITUTION_CODE("institutionCode"),
    POSITION_CODE("positionCode"),
    TYPE("type"),
    ID_ELEMENTS("idElements"),
    BANK_ACCOUNT_NUMBER("bankAccountNumber"),
    BANK_ID("bankId"),
    SECTION("section"),
    CALENDAR_YEAR("calendarYear"),
    
    HEADER("header"),
    CONTRIBUTOR("contributor"),
    PERSONAL_DATA("personalData"),
    TAX_RESIDENCE("taxResidence"),
    TREASURY("treasury"),
    INPS("inps"),
    REGION("region"),
    IMU("imu"),
    INAIL("inail"),
    SOCIAL_SECURITY("socialSecurity"),
    PAYMENT_DETAILS("paymentDetails"),
    EXCISE("excise"),

    DEDUCTION_INT("deductionInt"),
    DEDUCTION_DEC("deductionDec"),
    DEBIT_AMOUNT("debitAmount"),
    DEBIT_AMOUNT_INT("debitAmountInt"),
    DEBIT_AMOUNT_DEC("debitAmountDec"),
    CREDIT_AMOUNT("creditAmount"),
    CREDIT_AMOUNT_INT("creditAmountInt"),
    CREDIT_AMOUNT_DEC("creditAmountDec"),

    TOTAL_DEBIT("totalDebit"),
    TOTAL_CREDIT("totalCredit"),

    SIGNATURE("signature");
    private String name;

    FieldEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
