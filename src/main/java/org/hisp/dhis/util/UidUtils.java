package org.hisp.dhis.util;

import java.util.concurrent.ThreadLocalRandom;

public class UidUtils
{
    private static final String letters = "abcdefghijklmnopqrstuvwxyz"
        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALLOWED_CHARS = "0123456789" + letters;
    private static final int NUMBER_OF_CODEPOINTS = ALLOWED_CHARS.length();
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
