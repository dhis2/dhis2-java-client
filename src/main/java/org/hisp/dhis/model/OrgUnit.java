/*
 * Copyright (c) 2004-2024, University of Oslo
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
package org.hisp.dhis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrgUnit extends NameableObject {
  @JsonProperty private String path;

  @JsonProperty private Integer level;

  @Setter @JsonProperty private OrgUnit parent;

  @Setter @JsonProperty private Date openingDate;

  @Setter @JsonProperty private Date closedDate;

  @Setter @JsonProperty private String comment;

  @Setter @JsonProperty private String url;

  @Setter @JsonProperty private String contactPerson;

  @Setter @JsonProperty private String address;

  @Setter @JsonProperty private String email;

  @Setter @JsonProperty private String phoneNumber;

  public OrgUnit(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public OrgUnit(String id, String name, String shortName) {
    this(id, name);
    this.shortName = shortName;
  }

  public OrgUnit(String id, String name, String shortName, OrgUnit parent) {
    this(id, name, shortName);
    this.parent = parent;
  }

  public OrgUnit(String id, String name, String shortName, OrgUnit parent, Date openingDate) {
    this(id, name, shortName, parent);
    this.openingDate = openingDate;
  }
}
