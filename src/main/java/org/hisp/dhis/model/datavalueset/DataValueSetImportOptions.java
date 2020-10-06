package org.hisp.dhis.model.datavalueset;

import org.hisp.dhis.model.IdScheme;

import lombok.Getter;

@Getter
public class DataValueSetImportOptions
{
    private IdScheme dataElementIdScheme;

    private IdScheme orgUnitIdScheme;

    private IdScheme categoryOptionComboIdScheme;

    private IdScheme idScheme;

    private Boolean skipAudit;

    private DataValueSetImportOptions()
    {
    }

    public static DataValueSetImportOptions instance()
    {
        return new DataValueSetImportOptions();
    }

    public DataValueSetImportOptions withDataElementIdScheme( IdScheme dataElementIdScheme )
    {
        this.dataElementIdScheme = dataElementIdScheme;
        return this;
    }

    public DataValueSetImportOptions withOrgUnitIdScheme( IdScheme orgUnitIdScheme )
    {
        this.orgUnitIdScheme = orgUnitIdScheme;
        return this;
    }

    public DataValueSetImportOptions withCategoryOptionComboIdScheme( IdScheme categoryOptionComboIdScheme )
    {
        this.categoryOptionComboIdScheme = categoryOptionComboIdScheme;
        return this;
    }

    public DataValueSetImportOptions withIdScheme( IdScheme idScheme )
    {
        this.idScheme = idScheme;
        return this;
    }

    public DataValueSetImportOptions withSkipAudit()
    {
        this.skipAudit = true;
        return this;
    }
}
