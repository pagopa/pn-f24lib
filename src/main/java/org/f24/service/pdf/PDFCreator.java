package org.f24.service.pdf;

public interface PDFCreator {

    String MODEL_FOLDER_NAME = "templates";

    default String getMoney(int input) {
        double doubleInput = input/100.0;
        int integerPart = (int) doubleInput;
        double decimalPart = doubleInput - integerPart;
        String decimalPartString = String.format("%.2f", decimalPart).split(",")[1];
        decimalPartString = decimalPartString.replace("", " ").trim();
        return integerPart + "  " +  decimalPartString;
    }

    /**
     * Method which creates PDF Document for certain dto with no parameters in
     *
     * @return PDFDocument object which represents generated PDF
     */
    byte[] createPDF();

}
