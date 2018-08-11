import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

public class PasswordTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Before
    public void init() {
        ReflectionUtils.newInstanceOfClass(Password.class);
    }

    @Test
    @Points("22.1")
    public void asksAndAnswersWhenWrong() {
        io.setSysIn("boot\n");
        try {
            Password.main(new String[0]);
        } catch (NoSuchElementException e) {
        }


        String out = io.getSysOut();
        assertTrue("You did not ask anything", out.trim().length() > 0);
        assertTrue("You should prompt the user with question that contains \":\", now you printed " + out,
                out.contains(":"));

        assertTrue("You should promt the user with question \"Type the password:\", now you printed " + out,
                out.contains("assword"));

        assertTrue("With wrong password, program's output should contain text \"Wrong!\", you printed " + out,
                out.contains("rong"));

    }

    @Test
    @Points("22.1")
    public void asksAndAnswersWhenRight() {
        io.setSysIn("carrot\n");
        try {
            Password.main(new String[0]);
        } catch (NoSuchElementException e) {

        }

        String out = io.getSysOut();
        assertTrue("You did not ask anything", out.trim().length() > 0);
        assertTrue("You should prompt the user with question that contains \":\", now you printed " + out,
                out.contains(":"));

        assertTrue("You should promt the user with question \"Type the password:\", now you printed " + out,
                out.contains("assword"));

        assertTrue("With correct password, program's output should contain text \"Right!\".  Make sure that you have not changed the password \"carrot\". You printed " + out,
                out.contains("ight"));
    }

    @Test
    @Points("22.2")
    public void ohjelmaKysyyUudelleenKunVaarin() {
        io.setSysIn("boot\nfish\ndijkstra\n");

        boolean poikkeusNahty = false;
        try {
            Password.main(new String[0]);
        } catch (NoSuchElementException e) {
            poikkeusNahty = true;
        }

        if (!poikkeusNahty) {
            fail("Program should keep asking the password until it is given correctly");
        }

        String out = io.getSysOut();
        assertTrue("You did not ask anything", out.trim().length() > 0);
        assertTrue("You should prompt the user with question that contains \":\", now you printed " + out,
                out.contains(":"));

        assertTrue("You should promt the user with question \"Type the password:\", now you printed " + out,
                out.contains("assword"));

        assertTrue("Your output should not have text \"Right!\".  Make sure that you have not changed the password \"carrot\". You printed " + out,
                !out.contains("ight"));

        String[] rivit = out.split("\n");
        String vikarivi = rivit[rivit.length - 1];

        assertTrue("Program should keep asking the password until it is given correctly", vikarivi.contains(":"));
    }

    @Test
    @Points("22.2")
    public void ohjelmaKysyyKunnesRight() {
        io.setSysIn("boot\nfish\ndijkstra\ncarrot\n");
        Password.main(new String[0]);

        String out = io.getSysOut();
        assertTrue("You did not ask anything", out.trim().length() > 0);
        assertTrue("You should prompt the user with question that contains \":\", now you printed " + out,
                out.contains(":"));

        assertTrue("You should promt the user with question \"Type the password:\", now you printed " + out,
                out.contains("assword"));

        assertTrue("Programs output should contain text \"Right!\". Make sure that you have not changed the password \"carrot\". You printed " + out,
                out.contains("ight"));
    }

    @Test
    @Points("22.2")
    public void ohjelmaLopettaaKyselynKunRight() {
        io.setSysIn("boot\nfish\ncarrot\ndijkstra\n");
        Password.main(new String[0]);

        String out = io.getSysOut();
        assertTrue("You did not ask anything", out.trim().length() > 0);
        assertTrue("You should prompt the user with question that contains \":\", now you printed " + out,
                out.contains(":"));

        assertTrue("You should promt user the with question \"Type the password:\", now you printed " + out,
                out.contains("assword"));

        assertTrue("Programs output should contain text \"Right!\". Make sure that you have not changed the password \"carrot\". You printed " + out,
                out.contains("ight"));

        int oightVikaIndeksi = out.lastIndexOf("ight");
        int vaarinVikaIndeksi = out.lastIndexOf("rong");

        assertTrue("Program should keep asking the password until it is given correctly", oightVikaIndeksi > vaarinVikaIndeksi);
    }

    @Test
    @Points("22.3")
    public void lopussaSalaisuus() {
        io.setSysIn("boot\nfish\ncarrot\ndijkstra\n");
        Password.main(new String[0]);

        String out = io.getSysOut();
        assertTrue("You did not ask anything", out.trim().length() > 0);
        assertTrue("You should prompt the user with question that contains \":\", now you printed " + out,
                out.contains(":"));

        assertTrue("You should promt the user with question \"Type the password:\", now you printed " + out,
                out.contains("assword"));

        assertTrue("Programs output should contain text \"Right!\". Make sure that you have not changed the password \"carrot\". You printed " + out,
                out.contains("ight"));

        int oightVikaIndeksi = out.lastIndexOf("ight") + "ight".length();
        int vaarinVikaIndeksi = out.lastIndexOf("rong") + "rong".length();

        assertTrue("Program should keep asking the password until it is given correctly", oightVikaIndeksi > vaarinVikaIndeksi);

        assertTrue("Print the secret at the end", oightVikaIndeksi + 4 < out.length());
    }
}
