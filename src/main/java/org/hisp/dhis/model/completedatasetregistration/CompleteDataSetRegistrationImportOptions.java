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
package org.hisp.dhis.model.completedatasetregistration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.ImportStrategy;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompleteDataSetRegistrationImportOptions {
  /** Identifier property used for data sets in the response. Overrides idScheme. */
  private IdScheme dataSetIdScheme;

  /** Identifier property used for organisation units in the response. Overrides idScheme. */
  private IdScheme orgUnitIdScheme;

  /** Identifier property used for attribute option combos in the response. Overrides idScheme. */
  private IdScheme attributeOptionComboIdScheme;

  /** Identifier property used for metadata objects in the response. */
  private IdScheme idScheme;

  /** Whether to save changes on the server or just return the import summary. */
  private Boolean preheatCache;

  /** Whether registration applies to sub units. */
  private Boolean dryRun;

  /** Save objects of all, new or update import status on the server. */
  private ImportStrategy importStrategy;

  /**
   * Skip checks for existing complete registrations. Improves performance. Only use for empty
   * databases or when the registrations to import do not exist already.
   */
  private Boolean skipExistingCheck;

  public static CompleteDataSetRegistrationImportOptions instance() {
    return new CompleteDataSetRegistrationImportOptions();
  }
}
