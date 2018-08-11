import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;

@Points("81")
public class PasswordRandomizerTest {
    String merkit = "abcdefghijklmnopqrstuvwxyz";

    @Test
    public void correctCharacters() {
        PasswordRandomizer arpoja = new PasswordRandomizer(13);
        for (int i = 0; i < 10; i++) {
            String sala = arpoja.createPassword();
            for (char c : sala.toCharArray()) {
                assertTrue("Your password \"" + sala + "\" has character '" +
                        c + "' that does not belong to a-z!", merkit.indexOf(c) != -1);
            }
        }
    }

    @Test
    public void correctLength() {
        PasswordRandomizer arpoja = new PasswordRandomizer(13);
        for (int i = 0; i < 10; i++) {
            String sala = arpoja.createPassword();
            assertEquals("Your password \"" + sala + "\" has wrong length!",
                    13, sala.length());
        }
    }

    @Test
    public void randomEnough() {
        HashSet<String> hs = new HashSet<String>();
        HashSet<Character> cs = new HashSet<Character>();
        PasswordRandomizer arpoja = new PasswordRandomizer(13);
        for (int i = 0; i < 1000; i++) {
            String sala = arpoja.createPassword();
            hs.add(sala);
            for (char c : sala.toCharArray()) {
                cs.add(c);
            }
        }

        int k = hs.size();
        assertTrue("Among 1000 password that you generater " + k +
                " were different! Not random enough!", k > 900);
        int ck = cs.size();
        assertTrue("1000 password that you generater contained " + ck +
                " different characters. Not random enough!", ck > 20);
    }

    @Test
    public void correctLength2() throws Exception {
        for (int i = 0; i < 100; i++) {
            PasswordRandomizer arpoja = new PasswordRandomizer(i);
            String salasana = arpoja.createPassword();
            assertTrue("The length of random words " + salasana.length() +
                    " does not correspond to the length specified as constructor parameter " + i,
                    (i == salasana.length()));
        }
    }
}
