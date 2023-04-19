package org.f24.dto.component;

public class Header {

    private String delegationTo;
    private String agency;

    private String province;

    public Header() {}

    /**
     * Constructs header section of F24 form
     *
     * @param delegationTo irrevocable delegation to (delega irrevocabile a)
     * @param agency       agency (agenza)
     * @param province     province (prov.)
     */
    public Header(String delegationTo, String agency, String province) {
        this.delegationTo = delegationTo;
        this.agency = agency;
        this.province = province;
    }

    public String getDelegationTo() {
        return delegationTo;
    }

    public void setDelegationTo(String delegationTo) {
        this.delegationTo = delegationTo;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
