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
package org.hisp.dhis.response.job;

public enum JobCategory {
  DATA_STATISTICS,
  DATA_INTEGRITY,
  RESOURCE_TABLE,
  ANALYTICS_TABLE,
  DATA_SYNC,
  PROGRAM_DATA_SYNC,
  TRACKER_PROGRAMS_DATA_SYNC,
  TRACKER_IMPORT_JOB,
  EVENT_PROGRAMS_DATA_SYNC,
  FILE_RESOURCE_CLEANUP,
  IMAGE_PROCESSING,
  META_DATA_SYNC,
  SMS_SEND,
  SEND_SCHEDULED_MESSAGE,
  PROGRAM_NOTIFICATIONS,
  VALIDATION_RESULTS_NOTIFICATION,
  CREDENTIALS_EXPIRY_ALERT,
  MONITORING,
  PUSH_ANALYSIS,
  PREDICTOR,
  DATA_SET_NOTIFICATION,
  REMOVE_EXPIRED_RESERVED_VALUES,
  DATAVALUE_IMPORT,
  ANALYTICSTABLE_UPDATE,
  METADATA_IMPORT,
  GML_IMPORT,
  EVENT_IMPORT,
  ENROLLMENT_IMPORT,
  TEI_IMPORT,
  LEADER_ELECTION,
  LEADER_RENEWAL,
  COMPLETE_DATA_SET_REGISTRATION_IMPORT
}
