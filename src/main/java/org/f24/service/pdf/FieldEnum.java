package org.f24.service.pdf;

public enum FieldEnum {

    DELEGATION("attorney"),
    AGENCY("agency"),
    AGENCY_PROVINCE("agencyProvince"),
    PROVINCE("province"),
    TAX_CODE("taxCode"),
    CORPORATE_NAME("corporateName"),
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
    ACT_CODE("deedCode"),
    LOCATION_CODE("locationCode"),
    CONTRIBUTION_REASON("contributionReason"),
    INPS_CODE("inpsCode"),
    START_DATE("startDate"),
    END_DATE("endDate"),
    REGION_CODE("regionCode"),
    OPERATION_ID("operationId"),
    MUNICIPALITY_CODE("municipalityCode"),
    ACTIVE_REPENTANCE("ravv"),
    VARIED_BUILDINGS("building"),
    ADVANCE_PAYMENT("acc"),
    BALANCE("balance"),
    NUMBER_OF_BUILDINGS("numberOfBuildings"),
    INSTITUTION("institution"),
    MONTH("month"),
    DATE_OF_PAYMENT("dateOfPayment"),
    COMPANY("company"),
    CAB_CODE("cabCode"),
    CHECK_NUMBER("checkNumber"),
    ABI_CODE("abiCode"),
    BANK("isBank"),
    CIRCULAR("isCircular"),
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

    DEDUCTION("deduction"),
    DEBIT_AMOUNT("debitAmount"),
    CREDIT_AMOUNT("creditAmount"),
    TOTAL_AMOUNT("totalAmount"),

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
