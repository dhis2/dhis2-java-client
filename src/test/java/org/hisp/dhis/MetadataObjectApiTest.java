package org.hisp.dhis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.hisp.dhis.model.AggregationType;
import org.hisp.dhis.model.DataDomain;
import org.hisp.dhis.model.DataElement;
import org.hisp.dhis.model.MetadataEntity;
import org.hisp.dhis.model.ValueType;
import org.hisp.dhis.response.HttpStatus;
import org.hisp.dhis.response.Status;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.support.TestTags;
import org.hisp.dhis.util.UidUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.INTEGRATION)
class MetadataObjectApiTest {
  @Test
  void testSaveRemove() {
    Dhis2 dhis2 = new Dhis2(TestFixture.DEFAULT_CONFIG);
    
    String uidA = UidUtils.generateUid();
    String uidB = UidUtils.generateUid();
    MetadataEntity entity = MetadataEntity.DATA_ELEMENT;    
    DataElement object = getDataElement(uidA);
    
    // Save
    
    ObjectResponse createResponse = dhis2.saveMetadataObject(object);
    
    assertEquals(Status.OK, createResponse.getStatus());
    assertEquals(HttpStatus.CREATED, createResponse.getHttpStatus());
    
    // Update
    
    object.setName(uidB);
    
    ObjectResponse updateResponse = dhis2.updateMetadataObject(object);

    assertEquals(Status.OK, updateResponse.getStatus());
    assertEquals(HttpStatus.OK, updateResponse.getHttpStatus());
    
    // Remove
    
    ObjectResponse removeResponse = dhis2.removeMetadataObject(entity, uidA);

    assertEquals(Status.OK, removeResponse.getStatus());
    assertEquals(HttpStatus.OK, removeResponse.getHttpStatus());
  }
  
  private DataElement getDataElement(String uid) {
    DataElement object = new DataElement();
    object.setId(uid);
    object.setCode(uid);
    object.setName(uid);
    object.setShortName(uid);
    object.setValueType(ValueType.INTEGER);
    object.setAggregationType(AggregationType.SUM);
    object.setDomainType(DataDomain.AGGREGATE);
    return object;
  }
}
