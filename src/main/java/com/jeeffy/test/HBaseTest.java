package com.jeeffy.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * Created by jeeffy on 3/9/16.
 */
public class HBaseTest {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.security.auth.login.config","/Users/jeeffy/IdeaProjects/kafka-test/src/main/resources/hbase-jaas.conf");
        System.setProperty("java.security.krb5.conf","/Users/jeeffy/IdeaProjects/kafka-test/src/main/resources/hbase-krb5.conf");
        //System.setProperty("zookeeper.sasl.client","Client_new");

        Configuration conf = HBaseConfiguration.create();
        conf.addResource(new Path("/Users/jeeffy/IdeaProjects/kafka-test/src/main/resources/core-site.xml"));
        conf.addResource(new Path("/Users/jeeffy/IdeaProjects/kafka-test/src/main/resources/hbase-site.xml"));
        conf.addResource(new Path("/Users/jeeffy/IdeaProjects/kafka-test/src/main/resources/hdfs-site.xml"));
        UserGroupInformation.setConfiguration(conf);

        Connection conn = ConnectionFactory.createConnection(conf);
        System.out.println("-----------------"+conn);
        Table table = conn.getTable(TableName.valueOf("test"));

        Put put = new Put("key".getBytes());
        put.addColumn("c".getBytes(), "ql".getBytes(), "v1".getBytes());
        put.addColumn("c".getBytes(), "ql2".getBytes(), "v2".getBytes());
        table.put(put);

        Get get = new Get("key".getBytes());
        Result result = table.get(get);
        for (Cell cell : result.listCells()) {
            System.out.println(new String(CellUtil.cloneRow(cell))
                    + " " +new String(CellUtil.cloneFamily(cell))
                    +" "+new String(CellUtil.cloneQualifier(cell))
                    +" "+new String(CellUtil.cloneValue(cell)));
        }
    }
}
