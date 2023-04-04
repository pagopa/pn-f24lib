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

}
