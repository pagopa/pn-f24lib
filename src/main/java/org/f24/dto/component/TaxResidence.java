package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "Tax Residence (Domicilio Fiscale) object")
public class TaxResidence {

    @NotBlank
    @Pattern(regexp = "^[A-Z]{1,40}$", message = "Invalid municipality. (comune)")
    private String municipality;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid province. (provincia)")
    private String province;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9\\s.]{1,35}$", message = "Invalid address. (frazione, via e numero civico) ")
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
