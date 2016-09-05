package com.jeeffy.test;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created by jeeffy on 3/30/16.
 */
public class ZKClientTest {
    public static void main(String[] args) {
        final ZkClient zkClient = new ZkClient("localhost:2181",5000,5000,new ZkSerializerImpl());
        System.out.println(zkClient.getChildren("/brokers/topics/test2/partitions").size());
        //System.out.println(zkClient.readData("/brokers/topics/test2/partitions/0/state"));

        new Thread(new Runnable() {

            public void run() {

                zkClient.subscribeDataChanges("/test", new IZkDataListener() {

                    public void handleDataDeleted(String dataPath) throws Exception {
                        System.out.println("the node 'dataPath'===>");
                    }

                    public void handleDataChange(String dataPath, Object data) throws Exception {
                        System.out.println("the node 'dataPath'===>"+dataPath+", data has changed.it's data is "+data);

                    }
                });

            }

        }).start();

        try {
            Thread.sleep(1000);
            //zkClient.createPersistent("/test","abc");
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
