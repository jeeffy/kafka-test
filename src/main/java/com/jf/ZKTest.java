package com.jf;

import org.apache.zookeeper.*;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.apache.zookeeper.data.*;
import java.util.*;

public class ZKTest {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //new一个acl
        List<ACL> acls = new ArrayList<ACL>();
        //添加第一个id，采用用户名密码形式
        Id id1 = new Id("digest",
                DigestAuthenticationProvider.generateDigest("cwadmin:cwadmin123"));
        ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);
        acls.add(acl1);
        //添加第二个id，所有用户可读权限
        Id id2 = new Id("world", "anyone");
        ACL acl2 = new ACL(ZooDefs.Perms.READ, id2);
        acls.add(acl2);

        // zk用admin认证，创建/test ZNode。

        ZooKeeper zk = new ZooKeeper(
                "kfdev11:2181,kfdev12:2181,kfdev13:2181",2000, null);
        zk.addAuthInfo("digest", "cwadmin:cwadmin123".getBytes());
        //zk.create("/test", "data".getBytes(), acls, CreateMode.PERSISTENT);
        //zk.delete("/consumers/test",0);
        List<String> list = zk.getChildren("/consumers/test",false);
        System.out.println(list);
        //zk.delete("/consumers/test/ids", 0);
        //zk.delete("/consumers/test/owners",0);
        zk.delete("/consumers/test",0);
    }
}