package org.hisp.dhis.model.completedatasetregistration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.ImportStrategy;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompleteDataSetRegistrationImportOptions {
    /**
     * Identifier property used for data sets in the response. Overrides idScheme.
     */
    private IdScheme dataSetIdScheme;

    /**
     * Identifier property used for organisation units in the response. Overrides idScheme.
     */
    private IdScheme orgUnitIdScheme;

    /**
     * attributeOptionComboIdScheme
     */
    private IdScheme attributeOptionComboIdScheme;

    /**
     * Identifier property used for metadata objects in the response.
     */
    private IdScheme idScheme;

    /**
     * Whether to save changes on the server or just return the import summary.
     */
    private Boolean preheatCache;

    /**
     * Whether registration applies to sub units.
     */
    private Boolean dryRun;

    /**
     * Save objects of all, new or update import status on the server.
     */
    private ImportStrategy importStrategy;

    /**
     * Skip checks for existing complete registrations. Improves performance.
     * Only use for empty databases or when the registrations to import do not exist already.
     */
    private Boolean skipExistingCheck;

    public static CompleteDataSetRegistrationImportOptions instance() {
        return new CompleteDataSetRegistrationImportOptions();
    }
}
