package org.hisp.dhis;

import org.hisp.dhis.model.completedatasetregistration.CompleteDataSetRegistration;
import org.hisp.dhis.model.completedatasetregistration.CompleteDataSetRegistrationImportOptions;
import org.hisp.dhis.query.completedatasetregistration.CompleteDataSetRegistrationQuery;
import org.hisp.dhis.response.Dhis2ClientException;
import org.hisp.dhis.response.completedatasetregistration.CompleteDataSetRegistrationResponse;
import org.hisp.dhis.response.data.ImportCount;
import org.hisp.dhis.response.data.Status;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hisp.dhis.util.DateTimeUtils.getDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag(TestTags.INTEGRATION)
class CompleteDataSetRegistrationApiTest {

    @Test
    void testGetCompleteDataSetRegistrationsWithStartEndDates() {
        CompleteDataSetRegistrationQuery query = CompleteDataSetRegistrationQuery.instance()
                .addDataSet("lyLU2wR22tC")
                .addOrgUnit("DiszpKrYNg8")
                .setStartDate("2010-01-01")
                .setEndDate("2026-01-31");

        Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
        List<CompleteDataSetRegistration> completeDataSetRegistrations = dhis2.getCompleteDataSetRegistrations(query);

        assertNotNull(completeDataSetRegistrations);
        assertFalse(completeDataSetRegistrations.isEmpty());
    }

    @Test
    void testGetCompleteDataSetRegistrationsWithCreated() {
        CompleteDataSetRegistrationQuery query = CompleteDataSetRegistrationQuery.instance()
                .addDataSet("lyLU2wR22tC")
                .addOrgUnit("DiszpKrYNg8")
                .setCreated("2010-01-01");

        Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
        List<CompleteDataSetRegistration> completeDataSetRegistrations = dhis2.getCompleteDataSetRegistrations(query);

        assertNotNull(completeDataSetRegistrations);
        assertFalse(completeDataSetRegistrations.isEmpty());
    }

    @Test
    void testGetCompleteDataSetRegistrationsWithoutDataSetIdThrowsException() {
        CompleteDataSetRegistrationQuery query = CompleteDataSetRegistrationQuery.instance()
                .addOrgUnit("DiszpKrYNg8")
                .setCreated("2010-01-01");

        Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
        Dhis2ClientException error = assertThrows(Dhis2ClientException.class, () -> dhis2.getCompleteDataSetRegistrations(query));

        assertNotNull(error);
        assertEquals("At least one data set must be specified", error.getMessage());
    }

    @Test
    void testSaveCompleteDataSetRegistrationsWithCreated() {
        Dhis2 dhis2 = new Dhis2(TestFixture.LOCAL_CONFIG);

        CompleteDataSetRegistration cdsr = getCompleteDataSetRegistration();
        CompleteDataSetRegistrationImportOptions importOptions = CompleteDataSetRegistrationImportOptions.instance();
        CompleteDataSetRegistrationResponse result = dhis2.saveCompleteDataSetRegistrations(List.of(cdsr), importOptions);

        assertNotNull(result);
        assertEquals(Status.SUCCESS, result.getStatus());
        ImportCount importCount = result.getImportCount();
        assertNotNull(importCount);
    }

    private static CompleteDataSetRegistration getCompleteDataSetRegistration() {
        CompleteDataSetRegistration cdsr = new CompleteDataSetRegistration();
        cdsr.setPeriod("202407");
        cdsr.setOrgUnit("DiszpKrYNg8");
        cdsr.setDataSet("lyLU2wR22tC");
        cdsr.setCreated(getDate(2013, 12, 30));
        cdsr.setCompleted(true);
        cdsr.setCreatedBy("system");
        return cdsr;
    }
}
