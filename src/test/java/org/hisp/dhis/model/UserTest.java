/*
 * Copyright (c) 2004-2026, University of Oslo
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

import static org.hisp.dhis.support.Assertions.assertEmpty;
import static org.hisp.dhis.util.CollectionUtils.mutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hisp.dhis.model.user.User;
import org.hisp.dhis.support.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(TestTags.UNIT)
class UserTest {
  private final OrgUnit ouA = new OrgUnit("GvFqTavdpGE", "Agape CHP");
  private final OrgUnit ouB = new OrgUnit("uPshwz3B3Uu", "Bandasuma CHP");

  @Test
  void testGetDataViewOrganisationUnitsWithFallbackA() {
    User user = new User();
    user.setOrganisationUnits(mutableList(ouA, ouB));

    assertEquals(mutableList(ouA, ouB), user.getOrganisationUnits());
    assertEmpty(user.getDataViewOrganisationUnits());
    assertEquals(mutableList(ouA, ouB), user.getDataViewOrganisationUnitsWithFallback());
  }

  @Test
  void testGetDataViewOrganisationUnitsWithFallbackB() {
    User user = new User();
    user.setOrganisationUnits(mutableList(ouA, ouB));
    user.setDataViewOrganisationUnits(mutableList(ouA));

    assertEquals(mutableList(ouA, ouB), user.getOrganisationUnits());
    assertEquals(mutableList(ouA), user.getDataViewOrganisationUnits());
    assertEquals(mutableList(ouA), user.getDataViewOrganisationUnitsWithFallback());
  }
}
