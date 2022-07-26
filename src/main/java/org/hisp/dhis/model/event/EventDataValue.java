package org.hisp.dhis.model.event;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hisp.dhis.util.DateTimeUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class EventDataValue
{
    public static final String VALUE_TRUE = "true";

    public static final String VALUE_FALSE = "false";

    @JsonProperty
    private String dataElement;

    @JsonProperty
    private String value;

    @JsonProperty
    private Boolean providedElsewhere;

    @JsonProperty
    private Date createdAt;

    @JsonProperty
    private Date updatedAt;

    @JsonProperty
    private String storedBy;

    public EventDataValue( String dataElement, String value )
    {
        this.dataElement = dataElement;
        this.value = value;
    }

    public boolean isBoolean()
    {
        return VALUE_TRUE.equalsIgnoreCase( value ) || VALUE_FALSE.equalsIgnoreCase( value );
    }

    public Boolean getBooleanValue()
    {
        return isBoolean() ? Boolean.valueOf( value ) : null;
    }

    public boolean isDouble()
    {
        return NumberUtils.isCreatable( value );
    }

    public Double getDoubleValue()
    {
        return isDouble() ? Double.valueOf( value ) : null;
    }

    public boolean isInteger()
    {
        return StringUtils.isNumeric( value );
    }

    public Integer getIntegerValue()
    {
        return isInteger() ? Integer.valueOf( value ) : null;
    }

    public boolean isLocalDateTime()
    {
        return DateTimeUtils.isValidLocalDateTime( value );
    }

    public LocalDateTime getLocalDateTimeValue()
    {
        return DateTimeUtils.isValidLocalDateTime( value ) ? DateTimeUtils.getLocalDateTime( value ) : null;
    }
}
