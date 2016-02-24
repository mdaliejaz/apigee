import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TopIPTest {
    @Test
    public void shouldPopulateTheMap() throws Exception {
        TopIP topIP = new TopIP();
        HashMap<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("250.250.30.4", 2);
        expectedMap.put("255.240.230.1", 2);
        expectedMap.put("255.240.230.5", 4);
        expectedMap.put("255.240.230.3", 3);
        expectedMap.put("255.240.230.8", 1);

        topIP.findTopIP(new File(this.getClass().getResource("testFile1").getFile()));

        assertThat(topIP.getHashMap(), is(expectedMap));
    }

    @Test
    public void shouldReturnIPInDescendingOrder() throws Exception {
        TopIP topIP = new TopIP();

        TreeMap<String, Integer> returnedMap = topIP.findTopIP(new File(this.getClass().getResource("testFile1").getFile()));

        assertThat(returnedMap.firstKey(), is("255.240.230.5"));
        assertThat(returnedMap.lastKey(), is("255.240.230.8"));
    }

    @Test
    public void shouldReturnIPInDescendingOrder_ForLargeFiles() throws Exception {
        TopIP topIP = new TopIP();

        TreeMap<String, Integer> returnedMap = topIP.findTopIP(new File(this.getClass().getResource("testFile2").getFile()));

        assertThat(returnedMap.firstKey(), is("255.240.230.3"));
        assertThat(returnedMap.lastKey(), is("255.220.230.3"));
    }

    @Test
    public void shouldValidateIPBeforeAddingToMap() throws Exception {
        TopIP topIP = new TopIP();

        assertThat(topIP.validateIP("a.b.c.d"), is(false));
        assertThat(topIP.validateIP("0.0.0.0"), is(true));
    }

    @Test
    public void shouldSkipLineAndContinueIfCannotParseIPInCurrentLine() throws Exception {
        TopIP topIP = new TopIP();

        TreeMap<String, Integer> returnedMap = topIP.findTopIP(new File(this.getClass().getResource("testFile3").getFile()));

        assertThat(returnedMap.firstKey(), is("255.240.230.3"));
        assertThat(returnedMap.lastKey(), is("250.250.30.4"));
    }
}