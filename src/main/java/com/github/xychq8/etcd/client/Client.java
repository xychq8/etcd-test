package com.github.xychq8.etcd.client;

import org.apache.commons.lang.StringUtils;
import org.boon.Str;
import org.boon.etcd.ClientBuilder;
import org.boon.etcd.EtcdClient;
import org.boon.etcd.Request;
import org.boon.etcd.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxu on 16/4/26.
 */
public class Client extends EtcdClient {

    protected Client(ClientBuilder builder) {
        super(builder);
    }

    public static Client getClient(){
        return new Client(new ClientBuilder().hosts(getEtcdHosts()).timeOutInMilliseconds(3000));
    }

    private static URI[] getEtcdHosts(){
        List<URI> uriList = new ArrayList<>();

        try {
            String uriStr = "http://192.168.10.235:2379";

            // uri为空,报错
            if(StringUtils.isBlank(uriStr)){
                return uriList.toArray(new URI[uriList.size()]);
            }

            // 有多个uri地址,中间用逗号隔开
            if(uriStr.contains(",")){
                for(String uriFragment : uriStr.split(",")){
                    uriList.add(new URI(uriFragment));
                }
                return uriList.toArray(new URI[uriList.size()]);
            }

            // 只有一个uri地址
            uriList.add(new URI(uriStr));
            return uriList.toArray(new URI[uriList.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            return uriList.toArray(new URI[uriList.size()]);
        }
    }


    public Response addToDirTemp(String dirName, String key, String value, int ttl) {
        return this.request(Request.request().methodPOST().key(Str.add(new String[]{dirName, "/", key})).value(value).ttl((long)ttl));
    }

}
