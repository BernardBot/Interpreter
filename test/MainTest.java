import junit.framework.TestCase;
import org.junit.Test;

import java.util.Random;

/**
 * Created by Bernard on 9-6-2017.
 */
public class MainTest extends TestCase {
    //TODO: DRY it

    private String clean = "!%^*()-+/1234567890.";

    @Test
    public void testSanitizeClean() throws Exception {
        assertEquals(clean, Main.sanitize(clean));
    }

    @Test
    public void testSanitizeDirty() throws Exception {
        int iterations = 100;
        Random rnd = new Random();
        //TODO: use more/all dirty symbols
        char dirtySymbol = 'D';
        String dirty;
        for (int i = 0; i < iterations; i++) {
            dirty = clean;
            for (int j = 0; j < rnd.nextInt(clean.length()) + 1; j++) {
                int index = rnd.nextInt(clean.length());
                dirty = dirty.substring(0, index) +
                        dirtySymbol +
                        dirty.substring(index);
            }
            try{
                Main.sanitize(dirty);
                fail("Missing Exception\n" +
                        "Expected an Exception with message for dirty symbols to be thrown");
            } catch (Exception e) {
                assertEquals(e.getMessage(),
                        "ERROR!\n" +
                                "unaccepted symbols in string\n" +
                                "[String s]\t" + dirty + "\n" +
                                "[String s*]\t" + dirty.replaceAll("[!%^*()\\-+/1234567890.\\s]", ""));
            }
        }
    }

    @Test
    public void testSanitizeBrackets() throws Exception {
        char[] dirtyBrackets = new char[]{'(', ')'};
        int iterations = 100;
        //TODO: get random uneven number of brackets
        int brackets = 5;
        Random rnd = new Random();
        String dirty;
        for (int i = 0; i < iterations; i++) {
            dirty = clean;
            for (int j = 0; j < brackets; j++) {
                int index = rnd.nextInt(clean.length());
                dirty = dirty.substring(0, index) +
                        dirtyBrackets[rnd.nextInt(dirtyBrackets.length)] +
                        dirty.substring(index);
            }
            try{
                Main.sanitize(dirty);
                fail("Missing Exception\n" +
                        "Expected an Exception with message for unbalanced brackets");
            } catch (Exception e) {
                assertEquals(e.getMessage(),
                        "ERROR!\n" +
                                "brackets aren't balanced\n" +
                                "[String s]\t" + dirty + "\n" +
                                "[String s*]\t" + dirty.replaceAll("[^\\(\\)]", ""));
            }
        }
    }

    @Test
    public void testSanitizeWhitespace() throws Exception {
        char dirtyWhiteSpace = ' ';
        int iterations = 100;
        Random rnd = new Random();
        String dirty;
        for (int i = 0; i < iterations; i++) {
            dirty = clean;
            for (int j = 0; j < rnd.nextInt(clean.length()) + 1; j++) {
                int index = rnd.nextInt(clean.length());
                dirty = dirty.substring(0, index) +
                        dirtyWhiteSpace +
                        dirty.substring(index);
            }

            assertEquals(clean, Main.sanitize(dirty));
        }
    }

    @Test
    public void testParseSmoke() throws Exception {
        String s = "1+1";
        Expr expected = new Plus(new Num(1), new Num(1));

        //TODO: implement equals?
        assertEquals(expected, Main.parse(s));
    }
}