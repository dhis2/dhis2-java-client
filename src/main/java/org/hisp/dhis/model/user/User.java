/*
 * Copyright (c) 2004-2025, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.hisp.dhis.model.user;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.IdentifiableObject;
import org.hisp.dhis.model.OrgUnit;

@Getter
@Setter
@NoArgsConstructor
public class User extends IdentifiableObject {
  @JsonProperty private String username;

  @JsonProperty private String firstName;

  @JsonProperty private String surname;

  @JsonProperty private String email;

  @JsonProperty private String phoneNumber;

  @JsonProperty private Boolean externalAuth;

  @JsonProperty private Date lastLogin;

  @JsonProperty private Boolean disabled;

  @JsonProperty private List<OrgUnit> organisationUnits = new ArrayList<>();

  @JsonProperty private List<OrgUnit> dataViewOrganisationUnits = new ArrayList<>();

  @JsonProperty private List<OrgUnit> teiSearchOrganisationUnits = new ArrayList<>();

  /**
   * Indicates whether at least one organisation unit exists.
   *
   * @return true if at least one organisation unit exists.
   */
  @JsonIgnore
  public boolean hasOrganisationUnits() {
    return isNotEmpty(organisationUnits);
  }

  /**
   * Returns the first {@link OrgUnit}, or null if none exist.
   *
   * @return the first {@link OrgUnit}.
   */
  public OrgUnit getFirstOrganisationUnit() {
    return isNotEmpty(organisationUnits) ? organisationUnits.get(0) : null;
  }
}
