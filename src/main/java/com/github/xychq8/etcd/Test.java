package com.github.xychq8.etcd;

import com.github.xychq8.etcd.client.Client;
import org.boon.core.Handler;
import org.boon.etcd.Response;

/**
 * Created by zhangxu on 16/4/27.
 */
public class Test {
    private static final Client client = Client.getClient();

    public static void main(String[] args) throws Exception{
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
