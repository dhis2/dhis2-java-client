package org.hisp.dhis.response.datavalueset;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ImportCount
{
    @JsonProperty
    private int imported;

    @JsonProperty
    private int updated;

    @JsonProperty
    private int ignored;

    @JsonProperty
    private int deleted;

    public ImportCount()
    {
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "[" )
            .append( "imported: " ).append( imported ).append( ", " )
            .append( "updated: " ).append( updated ).append( ", " )
            .append( "ignored: " ).append( ignored ).append( ", " )
            .append( "deleted: " ).append( deleted ).append( "]" ).toString();
    }
}
