package org.f24.dto.component;

public class TaxResidence {

    private String municipality;
    private String province;
    private String address;

    /**
     * Constructs tax residence section of contributor (domicilio fiscale)
     *
     * @param municipality municipality (comune)
     * @param province     province (prov.)
     * @param address      street and house number (via e numero civico)
     */
    public TaxResidence(String municipality, String province, String address) {
        this.municipality = municipality;
        this.province = province;
        this.address = address;
    }

}
