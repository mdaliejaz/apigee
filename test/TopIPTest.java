import org.junit.Test;

import java.io.File;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ali on 2/24/16.
 */
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


}