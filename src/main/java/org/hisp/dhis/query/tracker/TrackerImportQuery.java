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
package org.hisp.dhis.query.tracker;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hisp.dhis.model.IdScheme;
import org.hisp.dhis.model.ImportStrategy;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackerImportQuery {

  /** Default value is ReportMode.ERRORS */
  private ReportMode reportMode;

  /**
   * Can either be VALIDATE which will report errors in the payload without making changes to the
   * database or COMMIT (default) which will validate the payload and make changes to the database.
   */
  private ImportMode importMode;

  /**
   * IdScheme used for all metadata references unless overridden by a metadata specific parameter.
   * Default value is IdScheme.UID.
   */
  private IdScheme idScheme;

  /** IdScheme used for data element references. Default value is the same as idScheme. */
  private IdScheme dataElementIdScheme;

  /** IdScheme used for organisation unit references. Default value is the same as idScheme. */
  private IdScheme orgUnitIdScheme;

  /** IdScheme used for program references. Default value is the same as idScheme. */
  private IdScheme programIdScheme;

  /** IdScheme used for program stage references. Default value is the same as idScheme. */
  private IdScheme programStageIdScheme;

  /** IdScheme used for category option combo references. Default value is the same as idScheme. */
  private IdScheme categoryOptionComboIdScheme;

  /** IdScheme used for category option references. Default value is the same as idScheme. */
  private IdScheme categoryOptionIdScheme;

  /**
   * Indicates the effect the import should have. Can either be CREATE, UPDATE, CREATE_AND_UPDATE
   * and DELETE, which respectively only allows importing new data, importing changes to existing
   * data, importing any new or updates to existing data, and finally deleting data. Default value
   * is ImportStrategy.CREATE.
   */
  private ImportStrategy importStrategy;

  /**
   * Indicates how the import responds to validation errors. If ALL, all data imported must be valid
   * for any data to be committed. For OBJECT, only the data committed needs to be valid, while
   * other data can be invalid. Default value is AtomicMode.ALL.
   */
  private AtomicMode atomicMode;

  /**
   * Indicates the frequency of flushing. This is related to how often data is pushed into the
   * database during the import. Primarily used for debugging reasons, and should not be changed in
   * a production setting. Default value is FlushMode.AUTO.
   */
  private FlushMode flushMode;

  /**
   * Indicates the completeness of the validation step. It can be skipped, set to fail fast (Return
   * on the first error), or full (default), which will return any errors found. Default value is
   * ValidationMode.FULL.
   */
  private ValidationMode validationMode;

  /**
   * If true, it will skip validating the pattern of generated attributes. Default value is false.
   */
  private Boolean skipPatternValidation;

  /** If true, it will skip running any side effects for the import. Default value is false. */
  private Boolean skipSideEffects;

  /** If true, it will skip running any program rules for the import. Default value is false. */
  private Boolean skipRuleEngine;

  public static TrackerImportQuery instance() {
    return new TrackerImportQuery();
  }
}
