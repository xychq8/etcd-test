package com.github.xychq8.etcd.base;

import com.github.xychq8.etcd.client.Client;
import org.boon.etcd.Response;

/**
 * Created by zhangxu on 16/4/26.
 */
public class Test {

    private static Client client = Client.getClient();

    public static void main(String[] args) throws Exception{
        System.out.println("create dir (test):" + client.createDir("test").responseCode());
        System.out.println("delete dir (test):" + client.deleteDir("test").responseCode());

        System.out.println("create temp dir(test):" + client.createTempDir("test", 1).responseCode());
        Thread.sleep(1000);

        System.out.println("set key (myKey:123):" + client.set("myKey", "123").responseCode());
        System.out.println("get key (myKey):" + client.get("myKey").node().getValue());
        System.out.println("delete key (myKey):" + client.delete("myKey").responseCode());

        System.out.println("set temp key (myTempKey):" + client.setTemp("myTempKey", "234", 1).responseCode());
        Thread.sleep(1000);

        System.out.println("create dir (queue):" + client.createDir("queue").responseCode());
        System.out.println("create dir (queue/job1):" + client.createDir("queue/job1").responseCode());
        System.out.println("create dir (queue/job2):" + client.createDir("queue/job2").responseCode());
        System.out.println("delete recursively dir (queue):" + client.deleteDirRecursively("queue").responseCode());

        System.out.println("create sequence key (value:job1):" + client.addToDirTemp("queue", "", "job1", 1).node().key());
        Thread.sleep(1000);

        System.out.println("create sequence key (value:job2):" + client.addToDirTemp("queue", "", "job2", 1).node().key());
        Thread.sleep(1000);

        // 如果用异步wait,client.list会报超时错误.换成同步wait就没问题
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Response response = client.waitRecursive("queue");
                    System.out.println(response);
                    System.out.println("sub:" + client.list("queue"));
                }
            }
        }).start();

//        Handler<Response> responseHandler = new Handler<Response>() {
//            @Override
//            public void handle(Response response) {
//                System.out.println("sub:" + client.list("queue"));
//            }
//        };
//        client.waitRecursive(responseHandler, "queue");

        System.out.println("main:" + client.list("queue"));
        Thread.sleep(Long.MAX_VALUE);
    }


}
