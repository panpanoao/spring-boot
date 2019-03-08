package com.example.demo.Util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JedisUtis {

    private static Jedis jedis;

    /**
     * 获取redis连接
     * @return
     */
    public static synchronized Jedis getjedis() {
       JedisShardInfo shardInfo = new JedisShardInfo("redis://localhost:6379/0");
       /* JedisShardInfo shardInfo = new JedisShardInfo("127.0.0.1",6379,6000000);*/
        shardInfo.setPassword("123456");
        if(jedis==null) {
            jedis = new Jedis(shardInfo);

        }
        return jedis;
    }

    public  static  void close(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }

    /**
     * 序列化
     * @param object
     * @return
     */
    public static  byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
         //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
               //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 集合序列化
     * @param object
     * @return
     */
    public static byte[] serializeListObject(List<?> object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            //oos.writeObject(object);
            for(Object obj : object){
                oos.writeObject(obj);
            }
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 集合反序列化
     * @param in
     * @return
     */
    public static List<Object> deserialize(byte[] in) {
        List<Object> list = new ArrayList<>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if(in != null) {
                bis=new ByteArrayInputStream(in);
                is=new ObjectInputStream(bis);
                while (true) {
                    Object obj=(Object) is.readObject();
                    if(obj == null){
                        break;
                    }else{
                        list.add(obj);
                    }
                }
                is.close();
                bis.close();
            }
        } catch (IOException e) {

        }  catch (ClassNotFoundException e) {

            e.printStackTrace();
        } finally {

        }
        return list;
    }

}
