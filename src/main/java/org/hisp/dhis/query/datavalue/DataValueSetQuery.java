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
package org.hisp.dhis.query.datavalue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.IdScheme;

@Getter
@Setter
@Accessors(chain = true)
public class DataValueSetQuery {
  private final Set<String> dataSets = new HashSet<>();

  private final Set<String> dataElements = new HashSet<>();

  private final Set<String> dataElementGroups = new HashSet<>();

  private final Set<String> orgUnits = new HashSet<>();

  private final Set<String> orgUnitGroups = new HashSet<>();

  private final Set<String> periods = new HashSet<>();

  private String startDate;

  private String endDate;

  private final Set<String> attributeOptionCombos = new HashSet<>();

  private Boolean children;

  private Boolean includeDeleted;

  private String lastUpdated;

  private String lastUpdatedDuration;

  private Integer limit;

  private IdScheme dataElementIdScheme;

  private IdScheme orgUnitIdScheme;

  private IdScheme categoryOptionComboIdScheme;

  private IdScheme attributeOptionComboIdScheme;

  private IdScheme dataSetIdScheme;

  private IdScheme categoryIdScheme;

  private IdScheme categoryOptionIdScheme;

  private IdScheme idScheme;

  private IdScheme inputOrgUnitIdScheme;

  private IdScheme inputDataSetIdScheme;

  private IdScheme inputDataElementGroupIdScheme;

  private IdScheme inputDataElementIdScheme;

  private IdScheme inputIdScheme;

  public static DataValueSetQuery instance() {
    return new DataValueSetQuery();
  }

  public DataValueSetQuery addDataElements(Collection<String> dataElements) {
    this.dataElements.addAll(dataElements);
    return this;
  }

  public DataValueSetQuery addOrgUnits(Collection<String> orgUnits) {
    this.orgUnits.addAll(orgUnits);
    return this;
  }

  public DataValueSetQuery addPeriods(Collection<String> periods) {
    this.periods.addAll(periods);
    return this;
  }

  public DataValueSetQuery addDataSets(Collection<String> dataSets) {
    this.dataSets.addAll(dataSets);
    return this;
  }

  public DataValueSetQuery addDataElementGroups(Collection<String> dataElementGroups) {
    this.dataElementGroups.addAll(dataElementGroups);
    return this;
  }

  public DataValueSetQuery addOrgUnitGroups(Collection<String> orgUnitGroups) {
    this.orgUnitGroups.addAll(orgUnitGroups);
    return this;
  }

  public DataValueSetQuery addAttributeOptionCombos(Collection<String> attributeOptionCombos) {
    this.attributeOptionCombos.addAll(attributeOptionCombos);
    return this;
  }
}
