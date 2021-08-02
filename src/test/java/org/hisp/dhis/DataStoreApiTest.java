package org.hisp.dhis;

import lombok.AllArgsConstructor;

import org.hisp.dhis.category.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.fasterxml.jackson.annotation.JsonProperty;

@Category( IntegrationTest.class )
public class DataStoreApiTest
{
    @Test
    public void testSaveGet()
    {
        Dhis2 dhis2 = new Dhis2( TestFixture.DEFAULT_CONFIG );

        Fruit banana = new Fruit( "Banana", "Yellow" );
        Fruit grape = new Fruit( "Grape", "Green" );

    }

    @AllArgsConstructor
    class Fruit
    {
        @JsonProperty
        private String name;

        @JsonProperty
        private String color;
    }
}
