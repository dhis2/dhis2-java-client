package org.hisp.dhis.response;

import static org.hisp.dhis.util.CollectionUtils.mapToList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.hisp.dhis.response.object.ObjectResponse;
import org.hisp.dhis.response.objects.ObjectsResponse;

public class ObjectsResponseBuilder {
  private List<ObjectsResponse> objectsResponses;
  
  private List<ObjectResponse> objectResponses;
  
  public ObjectsResponseBuilder() {
    this.objectsResponses = new ArrayList<>();
    this.objectResponses = new ArrayList<>();
  }
  
  /**
   * Adds the given {@link ObjectsResponse}.
   *
   * @return this {@link ObjectsResponseBuilder}.
   */
  public ObjectsResponseBuilder add(ObjectsResponse response) {
    this.objectsResponses.add(response);
    return this;
  }

  /**
   * Adds the given {@link ObjectResponse}.
   *
   * @return this {@link ObjectsResponseBuilder}.
   */
  public ObjectsResponseBuilder add(ObjectResponse response) {
    this.objectResponses.add(response);
    return this;
  }
  
  /**
   * Builds an instance of {@link ObjectsResponse}.
   * 
   * @return an {@link ObjectsResponse}.
   */
  public ObjectsResponse build() {
    ObjectsResponse response = new ObjectsResponse();
    
    return response;
  }
  
  /**
   * Returns the optional {@link Status} with the highest value.
   * 
   * @return an optional {@link Status}.
   */
  Optional<Status> getHighestStatus() {
    List<Status> statuses = new ArrayList<Status>();
    statuses.addAll(mapToList(objectsResponses, ObjectsResponse::getStatus));
    statuses.addAll(mapToList(objectResponses, ObjectResponse::getStatus));
    return statuses.stream().sorted(Comparator.reverseOrder()).findFirst();
  }
  
  /**
   * Returns the optional HTTP status code with the highest value.
   * @return an optional HTTP status code.
   */
  Optional<Integer> getHighestHttpStatusCode() {
    List<Integer> codes = new ArrayList<Integer>();
    codes.addAll(mapToList(objectsResponses, ObjectsResponse::getHttpStatusCode));
    codes.addAll(mapToList(objectResponses, ObjectResponse::getHttpStatusCode));
    return codes.stream().sorted(Comparator.reverseOrder()).findFirst();
  }
}
