package org.f24.service.pdf.util;

public enum FieldEnum {

    DELEGATION("delegation"),
    AGENCY("agency"),
    AGENCY_PROVINCE("agencyProvince"),
    TAX_CODE("taxCode"),
    NAME("name"),
    CORPORATE_NAME("corporateName"),
    BIRTH_DATE("birthDate"),
    SEX("sex"),
    BIRTH_PLACE("birthPlace"),
    BIRTH_PROVINCE("birthProvince"),
    MUNICIPALITY("municipality"),
    TAX_PROVINCE("taxProvince"),
    ADDRESS("address"),
    SECTION("section"),
    RELATIVE_PERSON_TAX_CODE("relativePersonTaxCode"),
    ID_CODE("idCode"),
    DOCUMENT_CODE("documentCode"),
    OFFICE_CODE("officeCode"),
    IS_NOT_TAX_YEAR("calendarYear"),
    OPERATION_ID("operationId"),
    TAX_TYPE_CODE("taxTypeCode"),
    EXCISE_PROVINCE("exciseProvince"),
    RECONSIDERATION("reconsideration"),
    PROPERTIES_CHANGED("propertiesChanges"),
    ADVANCE_PAYMENT("advancePayment"),
    FULL_PAYMENT("fullPayment"),
    NUMBER_OF_PROPERTIES("numberOfProperties"),
    INSTALLMENT("installment"),
    MONTH("month"),
    YEAR("reportingYear"),
    CONTRIBUTION_REASON("contributionReason"),
    INPS_CODE("inpsCode"),
    START_DATE("startDate"),
    END_DATE("endDate"),
    REGION_CODE("regionCode"),
    MUNICIPALITY_CODE("municipalityCode"),
    POSITION_CODE("positionCode"),
    CONTROL_CODE("controlCode"),
    COMPANY_CODE("companyCode"),
    REASON("reason"),
    REFERENCE_NUMBER("referenceNumber"),
    DATE_OF_PAYMENT("dateOfPayment"),
    COMPANY("company"),
    CAB_CODE("cabCode"),
    CHECK_NUMBER("checkNumber"),
    ABI_CODE("abiCode"),
    BANK("isBank"),
    CIRCULAR("isCircular"),
    IBAN_CODE("ibanCode"),
    DEDUCTION("deduction"),
    DEBIT_AMOUNT("debitAmount"),
    CREDIT_AMOUNT("creditAmount"),
    TOTAL_AMOUNT("totalAmount"),
    TOTAL_DEBIT("totalDebitAmount"),
    TOTAL_CREDIT("totalCreditAmount"),
    SIGNATURE("signature"),
    TYPE("type"),
    ID_ELEMENT("idElement"),
    BALANCE_SIGN("balanceSign"),

    // Records numbers
    TAX_RECORDS_NUMBER(6),
    UNIV_RECORDS_NUMBER(4),
    INAIL_RECORDS_NUMBER(3),
    SOC_RECORDS_NUMBER(2),
    EXCISE_TAX_RECORDS_NUMBER(7),
    TREASURY_RECORDS_NUMBER(28),
    REASON_RECORDS_NUMBER(10);

    private String name;
    private int recordsNum;

    FieldEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    FieldEnum(int recordsNum) {
        this.recordsNum = recordsNum;
    }

    public int getRecordsNum() {
        return recordsNum;
    }

}
