package org.hisp.dhis.model.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hisp.dhis.util.DateTimeUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@ToString( onlyExplicitlyIncluded = true )
public class EventDataValue
{
    public static final String VALUE_TRUE = "true";

    public static final String VALUE_FALSE = "false";

    @ToString.Include
    @JsonProperty
    private String dataElement;

    @ToString.Include
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

    /**
     * Indicates whether the value is not null or empty.
     *
     * @return true if the value is not null or empty.
     */
    @JsonIgnore
    public boolean hasValue()
    {
        return StringUtils.isNotEmpty( value );
    }

    /**
     * Indicates whether the value represents a boolean.
     *
     * @return true if the value represents a boolean.
     */
    @JsonIgnore
    public boolean isBoolean()
    {
        return VALUE_TRUE.equalsIgnoreCase( value ) || VALUE_FALSE.equalsIgnoreCase( value );
    }

    /**
     * Returns the value as a boolean, only if this value represents a boolean.
     * Returns false if not.
     *
     * @return a boolean value.
     */
    @JsonIgnore
    public boolean getBooleanValue()
    {
        return isBoolean() ? Boolean.valueOf( value ) : false;
    }

    /**
     * Indicates whether the value represents a double.
     *
     * @return true if the value represents a double.
     */
    @JsonIgnore
    public boolean isDouble()
    {
        return NumberUtils.isCreatable( value );
    }

    /**
     * Returns the value as a double, only if the value represents a double.
     * Returns 0.0 if not.
     *
     * @return a double value.
     */
    @JsonIgnore
    public double getDoubleValue()
    {
        return isDouble() ? Double.valueOf( value ) : 0.0;
    }

    /**
     * Indicates whether the value represents an integer.
     *
     * @return true if the value represents an integer.
     */
    @JsonIgnore
    public boolean isInteger()
    {
        return StringUtils.isNumeric( value );
    }

    /**
     * Returns the value as an integer, only if the value represents an integer.
     * Return 0 if not.
     *
     * @return an integer value.
     */
    @JsonIgnore
    public int getIntegerValue()
    {
        return isInteger() ? Integer.valueOf( value ) : 0;
    }

    /**
     * Indicates whether the value represents a {@link LocalDate}.
     *
     * @return true if the value represents a {@link LocalDate}.
     */
    @JsonIgnore
    public boolean isLocalDate()
    {
        return DateTimeUtils.isValidLocalDate( value );
    }

    /**
     * Returns the value as a {@link LocalDate}, only if the value represents a
     * {@link LocalDate} or a {@link LocalDateTime}. If the value represents the
     * latter, the {@link LocalDateTime} is converted to a {@link LocalDate}.
     * Returns null if not.
     *
     * @return a {@link LocalDate} or null.
     */
    @JsonIgnore
    public LocalDate getLocalDateValue()
    {
        if ( DateTimeUtils.isValidLocalDate( value ) )
        {
            return DateTimeUtils.getLocalDate( value );
        }

        return DateTimeUtils.isValidLocalDateTime( value ) ? DateTimeUtils.getLocalDateTimeAsDate( value ) : null;
    }

    /**
     * Indicates whether the value represents a {@link LocalDateTime}.
     *
     * @return true if the value represents a {@link LocalDateTime}.
     */
    @JsonIgnore
    public boolean isLocalDateTime()
    {
        return DateTimeUtils.isValidLocalDateTime( value );
    }

    /**
     * Returns the value as a {@link LocalDateTime}, only if the value
     * represents a {@link LocalDateTime}. Returns null if not.
     *
     * @return a {@link LocalDateTime} or null.
     */
    @JsonIgnore
    public LocalDateTime getLocalDateTimeValue()
    {
        return DateTimeUtils.isValidLocalDateTime( value ) ? DateTimeUtils.getLocalDateTime( value ) : null;
    }
}
