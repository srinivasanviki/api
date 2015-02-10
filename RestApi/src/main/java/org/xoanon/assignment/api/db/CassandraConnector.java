package org.xoanon.assignment.api.db;

/**
 * Created by viki on 10/2/15.
 */
import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CassandraConnector {
    private static Cluster cluster;
    private static Session session;
    final private Logger log = LoggerFactory.getLogger(this.getClass());
    public CassandraConnector(String cassandraIP,String keyspace){
        cluster= Cluster.builder().addContactPoint(cassandraIP).build();
        session=cluster.connect(keyspace);
    }

    public static void insertDetails(String column_family, Map<String, Object> details) {
        Insert insert = QueryBuilder.insertInto(column_family).values(Arrays.copyOf(details.keySet().toArray(),details.keySet().toArray().length, String[].class), details.values().toArray());
        session.execute(insert);
    }
    public static List<Row> getRowListWhere(String column_family, String indexed_column_name, String value) {
        ResultSet result = session.execute("select * from " + column_family + " where " + indexed_column_name + "=\'" + value + "\'" + " ALLOW FILTERING");
        return result.all();
    }
}
