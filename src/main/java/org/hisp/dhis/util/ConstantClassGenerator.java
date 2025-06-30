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
package org.hisp.dhis.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.hisp.dhis.model.IdentifiableObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Generator of string values and files for Java classes. Converts DHIS2 objects into Java constant
 * classes.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantClassGenerator {
  /**
   * Converts the given class type name and objects to a string representation of a Java class. The
   * Java class will contain constants for each object, where the object <code>code</code> is the
   * variable name, and the object <code>ID</code> is the value.
   *
   * @param typeName the class type name.
   * @param packageName the package name for the class.
   * @param objects the list of {@link IdentifiableObject}.
   * @return the string representation of the Java class.
   */
  public static String toConstantClass(
      String typeName, String packageName, List<? extends IdentifiableObject> objects) {
    String className = toClassName(typeName);
    StringWriter writer = new StringWriter();

    writer.write(
        String.format(
            """
            package %s;

            /** %s constants. */
            public class %s {
            """,
            packageName, typeName, className));

    objects.forEach(
        object ->
            writer.write(
                String.format(
                    """
                    \s\spublic static final String %s = "%s";
                    """,
                    toJavaVariable(object.getCode()), object.getId())));

    writer.write(
        """
        }
        """);

    return writer.toString();
  }

  /**
   * Converts the given class type name and objects to a string representation of a Java enum. The
   * Java enum will contain constants for each object, where the object <code>code</code> is the
   * enum name, and the object <code>ID</code> is the enum value.
   *
   * @param typeName the class type name.
   * @param packageName the package name for the class.
   * @param objects the list of {@link IdentifiableObject}.
   * @return the string representation of the Java enum.
   */
  public static String toValueEnum(
      String typeName, String packageName, List<? extends IdentifiableObject> objects) {
    final String className = toClassName(typeName);
    final StringBuilder writer = new StringBuilder();

    writer.append(
        String.format(
            """
            package %s;

            /** %s enumeration. */
            public enum %s {
            """,
            packageName, typeName, className));

    objects.forEach(
        object ->
            writer.append(
                String.format(
                    """
                    \s\s%s("%s"),
                    """,
                    toJavaVariable(object.getCode()), object.getName())));

    TextUtils.replaceLast(writer, ",", ";");

    writer.append(
        String.format(
            """

            \s\sprivate final String value;

            \s\s%s(String value) {
            \s\s\s\sthis.value = value;
            \s\s}

            \s\spublic String getValue() {
            \s\s\s\sreturn value;
            \s\s}
            }
            """,
            className));

    return writer.toString();
  }

  /**
   * Converts the given class type name and objects to a string representation of a Java enum. The
   * Java enum will contain constants for each object, where the object <code>code</code> is the
   * enum name, and the object <code>ID</code> is the enum value.
   *
   * @param typeName the class type name.
   * @param packageName the package name for the class.
   * @param objects the list of {@link IdentifiableObject}.
   * @return the string representation of the Java enum.
   */
  public static String toNameValueEnum(
      String typeName, String packageName, List<? extends IdentifiableObject> objects) {
    final String className = toClassName(typeName);
    final StringBuilder writer = new StringBuilder();

    writer.append(
        String.format(
            """
            package %s;

            /** %s enumeration. */
            public enum %s {
            """,
            packageName, typeName, className));

    objects.forEach(
        object ->
            writer.append(
                String.format(
                    """
                    \s\s%s("%s", "%s"),
                    """,
                    toJavaVariable(object.getCode()), object.getCode(), object.getName())));

    TextUtils.replaceLast(writer, ",", ";");

    writer.append(
        String.format(
            """

            \s\sprivate final String name;
            
            \s\sprivate final String value;

            \s\s%s(String name, String value) {
            \s\s\s\sthis.name = name;
            \s\s\s\sthis.value = value;
            \s\s}
            
            \s\spublic String getName() {
            \s\s\s\sreturn name;
            \s\s}

            \s\spublic String getValue() {
            \s\s\s\sreturn value;
            \s\s}
            }
            """,
            className));

    return writer.toString();
  }
  
  /**
   * Writes a class of the given type. The class file is written to the following location.
   *
   * <pre><code>
   * {user.home}/{targetDir}/classes
   * </code></pre>
   *
   * The filename is on the following format.
   *
   * <pre><code>
   * Dhis2{type}.java
   * </code></pre>
   *
   * @param type the class type, without suffix.
   * @param value the class value.
   * @param targetDir the target directory.
   */
  public static void writeClass(String type, String value, String targetDir) {
    String className = toClassName(type);
    String fileName = String.format("%s.java", className);

    String homeDir = System.getProperty("user.home");
    String classDir = String.format("%s/%s/classes", homeDir, targetDir);
    String filePath = String.format("%s/%s", classDir, fileName);

    try {
      Files.deleteIfExists(Paths.get(filePath));
      Files.createDirectories(Paths.get(classDir));
      FileUtils.writeStringToFile(new File(filePath), value, StandardCharsets.UTF_8);
      log.info("Wrote class file: {}", filePath);
    } catch (IOException ex) {
      log.error("Failed to write to file: " + filePath, ex);
    }
  }

  /**
   * Converts the given type to a class name.
   *
   * @param type the type to convert.
   * @return the class name.
   */
  static String toClassName(String type) {
    return String.format("%s%s", "Dhis2", type);
  }

  /**
   * Converts a given string into a valid Java variable name.
   *
   * @param input The input string to convert.
   * @return A string that is a valid Java variable name.
   */
  static String toJavaVariable(String input) {
    Objects.requireNonNull(input);

    // Replace hyphens and spaces with underscores
    String result = input.replaceAll("[\\s-]", "_");

    // Remove invalid characters
    result = result.replaceAll("[^a-zA-Z0-9_]", "");

    // If after cleaning, the string is empty, provide a default name
    if (result.isEmpty()) {
      return "_EMPTY";
    }

    // Ensure the variable name does not start with a digit
    if (Character.isDigit(result.charAt(0))) {
      result = "_" + result;
    }

    return result;
  }
}
