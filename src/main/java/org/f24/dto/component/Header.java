package org.f24.dto.component;

public class Header {

    private String attorneyTo;
    private String agency;
    private String province;

    /**
     * Constructs header section of F24 form
     *
     * @param attorneyTo irrevocable attorney to (delega irrevocabile a)
     * @param agency     agency (agenza)
     * @param province   province (prov.)
     */
    public Header(String attorneyTo, String agency, String province) {
        this.attorneyTo = attorneyTo;
        this.agency = agency;
        this.province = province;
    }

    public String getAttorneyTo() {
        return attorneyTo;
    }

    public void setAttorneyTo(String attorneyTo) {
        this.attorneyTo = attorneyTo;
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
