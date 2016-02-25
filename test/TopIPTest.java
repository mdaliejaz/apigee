import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TopIPTest {
    @Test
    public void shouldCreateTableFromFile() throws Exception {
        TopIP topIP = new TopIP();
        SQLHelper sqlHelper = new SQLHelper();

        topIP.findTopIP(new File(this.getClass().getResource("testFile1").getFile()), sqlHelper);

        assertThat(sqlHelper.query(1).toString(), is("[255.240.230.5]"));
    }

    @Test
    public void shouldReturnTopThreeIPInDescendingOrder() throws Exception {
        TopIP topIP = new TopIP();
        SQLHelper sqlHelper = new SQLHelper();

        topIP.findTopIP(new File(this.getClass().getResource("testFile1").getFile()), sqlHelper);

        ArrayList<String> returnedIPAddresses = sqlHelper.query(3);

        assertThat(returnedIPAddresses.get(0), is("255.240.230.5"));
        assertThat(returnedIPAddresses.get(1), is("255.240.230.3"));
        assertThat(returnedIPAddresses.get(2), is("250.250.30.4"));
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
        SQLHelper sqlHelper = new SQLHelper();

        topIP.findTopIP(new File(this.getClass().getResource("testFile3").getFile()), sqlHelper);
        ArrayList<String> returnedIPAddresses = sqlHelper.query(3);

        assertThat(returnedIPAddresses.get(0), is("255.240.230.3"));
        assertThat(returnedIPAddresses.get(1), is("255.240.230.1"));
        assertThat(returnedIPAddresses.get(2), is("255.240.230.5"));
    }
}