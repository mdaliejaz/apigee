import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SQLHelperTest {
    @Test
    public void shouldQueryTopNIPAddresses() throws Exception {
        SQLHelper sqlHelper = new SQLHelper();
        String ip1 = "1.1.1.1";
        String ip2 = "2.2.2.2";
        String ip3 = "3.3.3.3";
        String ip4 = "4.4.4.4";

        sqlHelper.deleteTable();
        sqlHelper.insert(ip1);
        sqlHelper.insert(ip1);
        sqlHelper.insert(ip2);
        sqlHelper.insert(ip2);
        sqlHelper.insert(ip2);
        sqlHelper.insert(ip2);
        sqlHelper.insert(ip3);
        sqlHelper.insert(ip4);
        sqlHelper.insert(ip4);
        sqlHelper.insert(ip4);

        ArrayList<String> queryResult = sqlHelper.query(2);

        assertThat(queryResult.get(0), is(ip2));
        assertThat(queryResult.get(1), is(ip4));

        sqlHelper.deleteTable();

        assertThat(sqlHelper.query(1), is(new ArrayList<String>()));
    }

    @Test
    public void shouldInsertAndDeleteIPAddress() throws Exception {
        SQLHelper sqlHelper = new SQLHelper();
        String ip = "1.1.1.1";

        sqlHelper.deleteTable();
        sqlHelper.insert(ip);

        assertThat(sqlHelper.query(1).get(0), is(ip));

        sqlHelper.deleteTable();

        assertThat(sqlHelper.query(1), is(new ArrayList<String>()));
    }

    @Test
    public void shouldDeleteAllRowsFromTable() throws Exception {
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.deleteTable();

        assertThat(sqlHelper.query(1), is(new ArrayList<String>()));
    }

//    @Test
//    public void shouldCreateAndDropTable() throws Exception {
//        SQLHelper sqlHelper = new SQLHelper();
//        sqlHelper.createTable();
//        String ip = "0.0.0.0";
//        sqlHelper.insert(ip);
//        ArrayList<String> ipAddress = sqlHelper.query(1);
//
//        assertThat(ipAddress.get(0), is(ip));
//
//        sqlHelper.dropTable();
//    }
}