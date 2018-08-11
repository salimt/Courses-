
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("21")
public class LeapYearTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void leapYears() {
        int[] vuodet = {1600, 1604, 1608, 1612, 1616, 1620, 1624, 1628, 1632, 1636, 1640, 1644, 1648, 1652, 1656, 1660, 1664, 1668, 1672, 1676, 1680, 1684, 1688, 1692, 1696, 1704, 1708, 1712, 1716, 1720, 1724, 1728, 1732, 1736, 1740, 1744, 1748, 1752, 1756, 1760, 1764, 1768, 1772, 1776, 1780, 1784, 1788, 1792, 1796, 1804, 1808, 1812, 1816, 1820, 1824, 1828, 1832, 1836, 1840, 1844, 1848, 1852, 1856, 1860, 1864, 1868, 1872, 1876, 1880, 1884, 1888, 1892, 1896, 1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936, 1940, 1944, 1948, 1952, 1956, 1960, 1964, 1968, 1972, 1976, 1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008, 2012, 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096, 2104, 2108};

        for (int vuosi : vuodet) {
            tunnistaaOikean(vuosi);
        }
    }

    @Test
    public void nonLeapYears() {
        for (int vuosi = 1600; vuosi < 2108; vuosi++) {
            if (karkausvuosi(vuosi)) {
                continue;
            }
            hylkaaVaaran(vuosi);
        }
    }

    private void tunnistaaOikean(int vuosi) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(vuosi + "\n");
        callMain(LeapYear.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("with input " + vuosi + " you did not print anything", out.length() > 0);
        assertTrue("with input " + vuosi + " you should print \"The year is a leap year\", but you printed \"" + out + "\" ", out.toLowerCase().contains("s a l"));
        assertTrue("with input " + vuosi + " you should print \"The year is a leap year\", but you printed \"" + out + "\" ", !out.toLowerCase().contains("s not"));
    }

    private void hylkaaVaaran(int vuosi) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(vuosi + "\n");
        callMain(LeapYear.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("with input " + vuosi + " you did not print anything", out.length() > 0);
        assertTrue("with input " + vuosi + " you should print \"The year is not a leap year\", but you printed \"" + out + "\" ", !out.toLowerCase().contains("s a l"));
        assertTrue("with input " + vuosi + " you should print \"The year is not a leap year\", but you printed \"" + out + "\" ", out.toLowerCase().contains("s not"));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something unexpected happened, more info " + e);
        }
    }

    private boolean karkausvuosi(int vuosi) {
        int[] oikea = {1600, 1604, 1608, 1612, 1616, 1620, 1624, 1628, 1632, 1636, 1640, 1644, 1648, 1652, 1656, 1660, 1664, 1668, 1672, 1676, 1680, 1684, 1688, 1692, 1696, 1704, 1708, 1712, 1716, 1720, 1724, 1728, 1732, 1736, 1740, 1744, 1748, 1752, 1756, 1760, 1764, 1768, 1772, 1776, 1780, 1784, 1788, 1792, 1796, 1804, 1808, 1812, 1816, 1820, 1824, 1828, 1832, 1836, 1840, 1844, 1848, 1852, 1856, 1860, 1864, 1868, 1872, 1876, 1880, 1884, 1888, 1892, 1896, 1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936, 1940, 1944, 1948, 1952, 1956, 1960, 1964, 1968, 1972, 1976, 1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008, 2012, 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096, 2104, 2108};

        for (int v : oikea) {
            if (vuosi == v) {
                return true;
            }
        }


        return false;
    }
}
