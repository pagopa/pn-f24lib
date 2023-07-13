package org.f24.dto.component;

import org.f24.exception.ResourceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CompanyDataTest {

    @Test
    void givenCompanyData_whenCreateCompanyDataObject_thenCompanyDataObjectCreated() throws ResourceException {
        CompanyData companyData = new CompanyData("name", new TaxAddress("MILANO", "MI","VIA SAN MARCO 25"));
        assertEquals("name",companyData.getName());
        assertEquals("MILANO", companyData.getTaxAddress().getMunicipality());
    }

    @Test
    void givenEmptyObject_whenCreateCompanyDataObject_thenEmptyCompanyDataObjectCreated(){
        CompanyData companyData = new CompanyData();
        assertNull(companyData.getName());
    }
}
