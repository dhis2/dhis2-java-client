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
package org.hisp.dhis.util.writer;

import static org.hisp.dhis.util.TextUtils.emptyIfNull;

import java.util.List;
import java.util.stream.Collectors;
import org.hisp.dhis.model.NameableObject;
import org.hisp.dhis.util.TextUtils;

public class NameableObjectHtmlWriter {
  private static final String HTML_TABLE =
      """
      <!DOCTYPE html>
      <html>
      <head>
        <title>Nameable objects table</title>
        <style>
          table {
            width: auto;
            border-collapse: collapse;
            margin: 20px 15px;
          }
          th, td {
            padding: 8px 12px;
            border: 1px solid #ddd;
          }
          th {
            text-align: left;
            font-weight: bold;
            background-color: #f2f2f2;
          }
          tr:nth-child(even) {
            background-color: #fafafa;
          }
          tr:hover {
            background-color: #f1f1f1;
          }
        </style>
      </head>
      <body>
      <table>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Code</th>
          <th>Short name</th>
          <th>Description</th>
        </tr>
        {}
      </table>
      </body>
      </html>""";

  private static final String TABLE_ROW =
      "<tr><td>{}</td><td>{}</td><td>{}</td><td>{}</td><td>{}</td></tr>";

  /**
   * Converts the given list of {@link NameableObject} to an HTML table string.
   *
   * @param <T> the type.
   * @param objects the list of {@link NameableObject}.
   * @return an HTML table string.
   */
  public static <T extends NameableObject> String toHtmlTable(List<T> objects) {
    String rows =
        objects.stream().map(NameableObjectHtmlWriter::toRow).collect(Collectors.joining());

    return TextUtils.format(HTML_TABLE, rows);
  }

  /**
   * Converts the given {@link NameableObject} to an HTML table row string.
   *
   * @param object the {@link NameableObject}.
   * @return an HTML table row string.
   */
  private static String toRow(NameableObject object) {
    return TextUtils.format(
        TABLE_ROW,
        emptyIfNull(object.getId()),
        emptyIfNull(object.getName()),
        emptyIfNull(object.getCode()),
        emptyIfNull(object.getShortName()),
        emptyIfNull(object.getDescription()));
  }
}
