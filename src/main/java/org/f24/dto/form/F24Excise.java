package org.f24.dto.form;

import org.f24.dto.component.*;

public class F24Excise extends F24Form {

    private TreasurySection treasurySection;

    private InpsSection inpsSection;

    private RegionSection regionSection;
    private ImuSection imuSection;

    private ExciseSection exciseSection;
    private String ibanCode;

    /**
     * Constructs F24 Excise dto.
     *
     * @param header          Header component
     * @param contributor     Contributor component
     * @param paymentDetails  PaymentDetails component
     * @param treasurySection TreasurySection component
     * @param inpsSection     InpsSection component
     * @param regionSection   RegionSection component
     * @param imuSection      ImuSection component
     * @param exciseSection   ExciseSection component
     * @param ibanCode        IBAN code (autorizzo addebito su conto corrente codice IBAN)
     */
    public F24Excise(Header header, Contributor contributor, PaymentDetails paymentDetails, TreasurySection treasurySection, InpsSection inpsSection, RegionSection regionSection, ImuSection imuSection, ExciseSection exciseSection, String ibanCode) {
        super(header, contributor,  paymentDetails);
        this.treasurySection = treasurySection;
        this.inpsSection = inpsSection;
        this.regionSection = regionSection;
        this.imuSection = imuSection;
        this.exciseSection = exciseSection;
        this.ibanCode = ibanCode;
    }

    public TreasurySection getTreasurySection() {
        return treasurySection;
    }

    public void setTreasurySection(TreasurySection treasurySection) {
        this.treasurySection = treasurySection;
    }

    public InpsSection getInpsSection() {
        return inpsSection;
    }

    public void setInpsSection(InpsSection inpsSection) {
        this.inpsSection = inpsSection;
    }

    public RegionSection getRegionSection() {
        return regionSection;
    }

    public void setRegionSection(RegionSection regionSection) {
        this.regionSection = regionSection;
    }

    public ImuSection getImuSection() {
        return imuSection;
    }

    public void setImuSection(ImuSection imuSection) {
        this.imuSection = imuSection;
    }

    public ExciseSection getExciseSection() {
        return exciseSection;
    }

    public void setExciseSection(ExciseSection exciseSection) {
        this.exciseSection = exciseSection;
    }

    public String getIbanCode() {
        return ibanCode;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

}
