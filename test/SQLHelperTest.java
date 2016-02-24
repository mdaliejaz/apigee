import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SQLHelperTest {
    @Test
    public void shouldQueryTopNIPAddresses() throws Exception {
        SQLHelper sqlHelper = new SQLHelper();
        ArrayList<String> ipAddresses = sqlHelper.query(1);
        System.out.println(ipAddresses.toString());
    }

    @Test
    public void shouldInsertNewIPAddress() throws Exception {
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.insert("1.1.1.1");

        System.out.println(sqlHelper.query(2).toString());
    }

    @Test
    public void shouldDeleteAllRowsFromTable() throws Exception {
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.deleteTable();

//        System.out.println(sqlHelper.query(2).toString());
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