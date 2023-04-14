package org.f24.service.validator;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;


public class TaxCodeCalculator {

    private TaxCodeCalculator() {
    }

    public static String calculateTaxCode(String lastname, String firstname, String sex, Date date, String municipality) {
        String[] args = new String[] { lastname, firstname, sex, municipality };
        for (String s : args) {
            if (isEmpty(s)) {
                throwEmptyArgException();
            }
        }
        if (date == null) {
            throwEmptyArgException();
        }

        sex = sex.toUpperCase().trim();
        if (!("M".equals(sex) || "F".equals(sex))) {
            throw new IllegalArgumentException("Argument 'sex' must be 'm' or 'f'");
        }

        firstname = firstname.toUpperCase().trim();
        if (!CHAR_ALLOWED.matcher(firstname).matches()) {
            throwIllegalArgException("firstname");
        } else {
            firstname = getSubstitutionChar(firstname);
        }

        lastname = lastname.toUpperCase().trim();
        if (!CHAR_ALLOWED.matcher(lastname).matches()) {
            throwIllegalArgException("lastname");
        } else {
            lastname = getSubstitutionChar(lastname);
        }

        municipality = municipality.toUpperCase().trim();
        if (!MUNICIPALITY_COD_ALLOWED.matcher(municipality).matches()) {
            throw new IllegalArgumentException("Argument 'municipality' is not valid");
        }

        StringBuilder taxCode = new StringBuilder();
        taxCode.append(calculateLastnameCod(lastname));
        taxCode.append(calculateFirstnameCod(firstname));
        taxCode.append(calculateDtCod(date, sex));
        taxCode.append(municipality);
        taxCode.append(calculateControlChar(taxCode));

        return taxCode.toString();

    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    private static void throwEmptyArgException() {
        throw new IllegalArgumentException(
                "non sono permessi parametri nulli o vuoti");
    }

    private static void throwIllegalArgException(String arg_name) {
        throw new IllegalArgumentException(
                "L'argomento '"
                        + arg_name
                        + "' non può contenere caratteri speciali.");
    }

    private static String getConsVow(String string, boolean conson) {
        StringBuilder text = new StringBuilder();
        int i = 0;
        char[] valChar = string.toCharArray();
        for (i = 0; i < valChar.length; i++) {
            if (isVowel(valChar[i]) ^ conson) {
                text.append(valChar[i]);
            }
        }
        return text.toString();
    }

    private static StringBuilder calculateLastnameCod(String stringa) {

        StringBuilder cod = new StringBuilder();

        cod.append(getConsVow(stringa, true)
                + getConsVow(stringa, false));

        if (cod.length() > 3) {
            cod = new StringBuilder(cod.substring(0, 3));
        }

        for (int i = cod.length(); i < 3; i++) {
            cod.append(SUBSTITUTION_CHAR);
        }

        return cod;

    }

    private static StringBuilder calculateFirstnameCod(String stringa) {

        StringBuilder cod = new StringBuilder(getConsVow(stringa, true));

        if (cod.length() >= 4) {
            cod = cod.delete(1, 2);
        }

        cod.append(getConsVow(stringa, false));

        if (cod.length() > 3) {
            cod = cod.replace(0, cod.length(), cod.substring(0, 3));
        }

        for (int i = cod.length(); i < 3; i++) {
            cod.append(SUBSTITUTION_CHAR);
        }

        return cod;

    }

    private static StringBuilder calculateDtCod(Date birthDate, String sex) {

        StringBuilder cod = new StringBuilder();

        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(birthDate);

        Integer day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        Integer month = cal.get(GregorianCalendar.MONTH);
        Integer year = cal.get(GregorianCalendar.YEAR);

        cod.append(year.toString(), 2, 4);
        cod.append(getMonthCode(month));

        if (sex.equals("M")) {
            cod.append(String.format("%02d", day));
        } else {
            day += 40;
            cod.append(day);
        }

        return cod;

    }

    private static Character calculateControlChar(StringBuilder taxCode) {

        Integer sum = 0;

        for (int i = 0; i < taxCode.length(); i++) {

            int k = Character.getNumericValue(taxCode.charAt(i));
            if (i % 2 == 0) {
                sum += EVEN_ODD_CHAR_CODES[1][k];
            } else {
                sum +=  EVEN_ODD_CHAR_CODES[0][k];
            }

        }

        return Character.toUpperCase(Character.forDigit(((sum % 26)+10), 35));

    }

    private static String getSubstitutionChar(String value){

        for (int i = 0; i < CHAR_SUBSTITUTION[1].length;i++){

            value = value.replaceAll(CHAR_SUBSTITUTION[ROW_REGEX][i],
                    CHAR_SUBSTITUTION[ROW_SUB][i]);

        }

        return value;

    }

    private static final String SUBSTITUTION_CHAR = "X";

    private static char[] MONTHS_CODES = {
            'A', 'B', 'C', 'D', 'E', 'H',
            'L', 'M', 'P', 'R', 'S', 'T' };

    private static char getMonthCode(int mese){
        return MONTHS_CODES[mese];
    }

    private static int[][] EVEN_ODD_CHAR_CODES={
            {0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25},
            {1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23}};


    private static boolean isVowel(char c) {
        return VOWEL_ALLOWED.matcher(String.valueOf(c)).matches();
    }

    private static final String[][]
            CHAR_SUBSTITUTION = {{"[À]","[È]","[É]","[Ì]","[Ò]","[Ù]","[\\s]","[']"},
            {"A","E","E","I","O","U","",""}};

    private static final int ROW_REGEX = 0;
    private static final int ROW_SUB = 1;

    private static final Pattern CHAR_ALLOWED = Pattern.compile("[A-ZÀÈÉÌÒÙ' ]+");

    private static final Pattern MUNICIPALITY_COD_ALLOWED = Pattern
            .compile("[A-Z][0-9]{3}");

    private static final Pattern VOWEL_ALLOWED = Pattern.compile("[AEIOU]");

}

