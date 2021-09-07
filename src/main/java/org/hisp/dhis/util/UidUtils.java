package org.hisp.dhis.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class UidUtils
{
    private static final String letters = "abcdefghijklmnopqrstuvwxyz"
        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String ALLOWED_CHARS = "0123456789" + letters;

    private static final int NUMBER_OF_CODEPOINTS = ALLOWED_CHARS.length();

    private static final Pattern UID_PATTERN = Pattern.compile( "^[a-zA-Z]{1}[a-zA-Z0-9]{10}$" );

    private static final int CODESIZE = 11;

    /**
     * Generates a UID according to the following rules:
     *
     * <ul>
     * <li>Alphanumeric characters only.</li>
     * <li>Exactly 11 characters long.</li>
     * <li>First character is alphabetic.</li>
     * </ul>
     *
     * @return a UID.
     */
    public static String generateUid()
    {
        return generateCode( CODESIZE );
    }

    /**
     * Tests whether the given code is a valid UID.
     *
     * @param code the code to validate.
     * @return true if the code is valid.
     */
    public static boolean isValidUid( String code )
    {
        return code != null && UID_PATTERN.matcher( code ).matches();
    }

    /**
     * Generates a pseudo random string with alphanumeric characters.
     *
     * @param codeSize the number of characters in the code.
     * @return the code.
     */
    private static String generateCode( int codeSize )
    {
        ThreadLocalRandom r = ThreadLocalRandom.current();

        char[] randomChars = new char[codeSize];

        // First char should be a letter

        randomChars[0] = letters.charAt( r.nextInt( letters.length() ) );

        for ( int i = 1; i < codeSize; ++i )
        {
            randomChars[i] = ALLOWED_CHARS.charAt( r.nextInt( NUMBER_OF_CODEPOINTS ) );
        }

        return new String( randomChars );
    }
}
