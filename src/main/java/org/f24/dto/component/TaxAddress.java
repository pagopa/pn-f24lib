package org.f24.dto.component;

public class TaxAddress {

    private String municipality;

    private String province;

    private String address;

    public TaxAddress() {}

    /**
     * Constructs tax residence section of contributor (domicilio fiscale)
     *
     * @param municipality municipality (comune)
     * @param province     province (prov.)
     * @param address      street and house number (via e numero civico)
     */
    public TaxAddress(String municipality, String province, String address) {
        this.municipality = municipality;
        this.province = province;
        this.address = address;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
