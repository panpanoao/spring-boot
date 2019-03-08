package com.example.demo.model;

import com.example.demo.Util.JedisUtis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class Test {

    private static Jedis jedis = null;

    public static void main(String[] args) {
        jedis = JedisUtis.getjedis();
    /*    Score score = new Score();
        score.setPid(1);
        score.setScoredate("2012-02-01");
        score.setTestscoreId("2");
        score.setPname("张三");
        score.setId(186);
        //将对象存入redis
        jedis.set(score.getId().toString().getBytes(), JedisUtis.serialize(score));
        //取出对象
        byte[] value = jedis.get(score.getId().toString().getBytes());
        Score object = (Score) JedisUtis.unserialize(value);
        //输出
        System.out.println(object.getPname());
        System.out.println(object.getTestscoreId());
        *//*将对象序列化存储在redis中的list类型
        jedis.lpush("scorekey".toString().getBytes(),JedisUtis.serialize(score),JedisUtis.serialize(score1),JedisUtis.serialize(score2));
         *//*
        //通过集合反序列化取出对象

        List<byte[]> scorelist = jedis.lrange("scorekey".toString().getBytes(), 0, 6);
        //通过循环反序列化遍历
        for (Object scorekey : scorelist) {
            //反序列化取出对象
            Score scoreone = (Score) JedisUtis.unserialize((byte[]) scorekey);
            //输出测试
            System.out.println(scoreone.getId() + scoreone.getPname());
        }
        System.out.println(jedis.llen("scorekey"));

        //使用Hmset存储对象
        Map<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        jedis.hmset("maptest", map);
        System.out.println(jedis.hmget("maptest", "one"));
        Score score2=new Score();
        score2.setId(100);
        Map<byte[],byte[]> s=new HashMap<>();
        s.put("person".toString().getBytes(),JedisUtis.serialize(score));
        s.put("person1".toString().getBytes(),JedisUtis.serialize(score2));
        jedis.hmset("scoremap".toString().getBytes(),s);
      //List<byte[]> scorelist2= jedis.hmget("scoremap".toString().getBytes(),"person".toString().getBytes(),"person1".toString().getBytes());
        List<byte[]> scorelist2= jedis.hvals("scoremap".getBytes());
        //获取值
        for (byte[] bytes : scorelist2) {
            Score scoretwo=(Score) JedisUtis.unserialize(bytes);
            System.out.println(scoretwo.getId());
        }
        //获取健
    *//*   byte[] gg=jedis.hget("scoremap".toString().getBytes(),"person".getBytes());

        Score scoremap=(Score)JedisUtis.unserialize(gg);
        System.out.println(scoremap.getId());*//*
         *//*     List<byte>  scorelistkey=jedis.hkeys("scoremap".toString().getBytes());*//*
        jedis.set("te".toString().getBytes(),"gc".toString().getBytes());

    *//* byte[] a=jedis.get("te".toString().getBytes());
     String m=(String) JedisUtis.unserialize(a);*//*
         *//* System.out.println(jedis.get("te"));*//*
        for (String scoremap : jedis.hkeys("scoremap")) {
            System.out.println(scoremap);
        }
        for (String scoremap : jedis.hvals("scoremap")) {
            System.out.println(scoremap);
        }
        jedis.set("scoreto",score.toString());
        System.out.println(jedis.get("scoreto"));
        jedis.set("gf".getBytes(),JedisUtis.serialize("aaaa"));

        jedis.sadd("set".getBytes(),JedisUtis.serialize(score),JedisUtis.serialize(score2));
        Set<byte[]> setlist=jedis.smembers("set".toString().getBytes());
        for (byte[] bytes : setlist) {
            Score scorew=(Score) JedisUtis.unserialize(bytes);
            System.out.println("set集合下的"+scorew.getId());
        }*/

    /* Score score = new Score();
        score.setPid(1);
        score.setScoredate("2012-02-01");
        score.setTestscoreId("2");
        score.setPname("张三");
        score.setId(186);
        Score score2 = new Score();
        score2.setPid(1);
        score2.setScoredate("2012-02-01");
        score2.setTestscoreId("2");
        score2.setPname("张三");
        score2.setId(187);
        Map<byte[], Double> map = new HashMap<>();
        map.put(JedisUtis.serialize(score), 1.0);
        map.put(JedisUtis.serialize(score2), 2.0);
         jedis.zadd("scorezadd".toString().getBytes(),map);
       Set<byte[]> scorebyte=jedis.zrangeByScore("scorezadd".toString().getBytes(),1.0,2.0);

       List<byte[]> list=new ArrayList<>();
      for (byte[] bytes  : scorebyte) {
     *//*       Score score3=(Score)JedisUtis.unserialize(bytes);*//*
           list.add(bytes);
         *//*   System.out.println(score3.getId());*//*
        }
        for (Object o : JedisUtis.deserialize("scorezadd".toString().getBytes())) {
            Score score1=(Score) o;
            System.out.println(score1.getId());
        }

        System.out.println(jedis.exists("scorezadd"));
        System.out.println(jedis.zcard("scorezadd"));

        //将对象存入redis
        jedis.set(score.getId().toString().getBytes(), JedisUtis.serialize(score));
        //取出对象*/
    /*    for (byte[] bytes : jedis.zrangeByScore("scorezaddlist".toString().getBytes(), 0, 10)) {
            Score score1=(Score)JedisUtis.unserialize(bytes);
            System.out.println(score1);
        }
          jedis.zremrangeByScore("scorezaddlist",0,1);
        for (byte[] bytes : jedis.zrangeByScore("scorezaddlist".toString().getBytes(), 0, 10)) {
            Score score1=(Score)JedisUtis.unserialize(bytes);
            System.out.println(score1);
        }
*/

        List<String>  list1=new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("4");
        list1.add("3");
        List<String>  list2=new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("3");
        list2.add("4");
        list1.add("6");


/*            boolean sss=true;
            Set set=new HashSet();
        for (String s : list1) {
            int i=0;
            sss=true;
            for (String s1 : list1) {
                if(s.equals(s1)){
                   i++;
                }

            }
            if(i>1){
                System.out.println(s);
            }

        }*/

/*        int i=0;
        for (String s : list1) {
            i=0;
            for (String s1 : list2) {
                if(s.equals(s1)){
                    i++;
                }
            }
            if(i>1){
                System.out.println(s);
            }
        }*/

/*
Random rand=new Random();
Scanner input=new Scanner(System.in);
int a=rand.nextInt(10);
int i=1;
int j=0;
int c=0;
      while(true){
          if(i>1) {
              System.out.println("在猜猜");
          }else{
              System.out.println("输入一个数");
          }
          int s=  input.nextInt();
          if(s>a){
          System.out.println("输入大了");
          i++;
          c++;
          continue;
      }else if(s<a){
          System.out.println("输入小了");
              i++;
              c++;
          continue;
      }else {
          System.out.println("正确");
          System.out.println("你总共猜了"+i+"次");
          j++;
          c++;
          i=1;
          System.out.println("是否继续？Y/N");
           String test=input.next();
           if(test.equals("Y")||test.equals("y")){
               a=rand.nextInt(10);
               continue;
           }else{
               System.out.println("游戏结束");
               System.out.println("你总共猜了"+c+"次猜对了"+j+"次");
               if(c/j<=2){
               System.out.println("这是高手");
             }else if(c/j>2&&c/j<=4){
               System.out.println("一般般");
            }else{
            System.out.println("菜鸟");
        }
        break;
    }
}
 }

*/
     double cv=jedis.zrank("test","3");
     jedis.zrem("test","4");
        System.out.println("我存在哦"+cv);
/*      String[] strings={"黑桃","红桃","梅花","方块"};
      String[] puke={"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
      List<String> s=new ArrayList<>();
        for (String string : strings) {
            for (String s1 : puke) {
                    String cc=string+s1;
                    s.add(cc);
            }
        }
       s.add("大王");
       s.add("小王");
       //打乱顺序
        Collections.shuffle(s);
        //创建4个对象
        List<String> obj1=new ArrayList<>();
        List<String> obj2=new ArrayList<>();
        List<String> obj3=new ArrayList<>();
        List<String> obj4=new ArrayList<>();
       int gg=0;
       int ff=1;
       int ll=2;
        for (int i=0;i<s.size();i++) {
            //每人13张发牌最后两个14张
        *//*     if(i<=12){
                 obj1.add(s.get(i));
             }else if(i>12&&i<=25){
                 obj2.add(s.get(i));
             }else if(i>25&&i<=39){
                 obj3.add(s.get(i));
             }else{
                 obj4.add(s.get(i));
             }*//*
        //每人一张轮流发
     /* if(gg==i||i==52){
        obj1.add(s.get(i));
        gg+=4;
      }else if(ff==i||i==53){
          obj2.add(s.get(i));
          ff+=4;
      }
      else if(ll==i){
          obj3.add(s.get(i));
          ll+=4;
      }
      else {
          obj4.add(s.get(i));
      }

     }*/
        /*System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj3);
        System.out.println(obj4);*/

/*        for(int i=0;i<=5;i++){
            for (int j=0;j<=i;j++){
                System.out.print("*");
            }
            System.out.println("");
        }*/




        String[] strings={"黑桃","红桃","梅花","方块"};
        String[] puke={"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        List<String> s=new ArrayList<>();
        for (String string : strings) {
            for (String s1 : puke) {
                String cc=string+s1;
                s.add(cc);
            }
        }
        s.add("大王");
        s.add("小王");
        //打乱顺序
        Collections.shuffle(s);
        //创建4个对象
        List<String> obj1=new ArrayList<>();
        List<String> obj2=new ArrayList<>();
        List<String> obj3=new ArrayList<>();
        List<String> obj4=new ArrayList<>();
        for (int i=0;i<s.size();i++) {
           if(i%3==0&&i>2){
                obj1.add(s.get(i));
            }else if(i%3==1&&i>2){
               obj2.add(s.get(i));
           }else if(i%3==2&&i>2){
               obj3.add(s.get(i));
           }else{
               obj4.add(s.get(i));
           }
        }
       List<String> dizhu=new ArrayList<>();
        dizhu.add("obj1");
        dizhu.add("obj2");
        dizhu.add("obj3");
        Collections.shuffle(dizhu);
       if(dizhu.get(0).equals("obj1")){
           obj1.addAll(obj4);
           System.out.println("地主是玩家一");
       }else if(dizhu.get(0).equals("obe2")){
           obj2.addAll(obj4);
           System.out.println("地主是玩家二");
       }else {
           obj3.addAll(obj4);
           System.out.println("地主是玩家三");
       }
        System.out.println("地主牌为"+obj4);
        System.out.println("玩家一"+obj1);
        System.out.println("玩家二"+obj2);
        System.out.println("玩家三"+obj3);


        String ssss=jedis.get("aa");
        System.out.println(ssss);
       }


public void teee(){


}
@org.junit.Test
public void a(){
 /*   jedis = JedisUtis.getjedis();
    //    //得到当前redis集合中的总数（最大数）
    //    long aac=jedis.zcard("scorezaddlist");
    //    //定义一个集合用于保存最后一个数据
    //    List<byte[]> sss=new ArrayList<>();
    //    //根据当前最大数的索引找到当前最后一个数据
    //    for (byte[] bytes : jedis.zrange("scorezaddlist".getBytes(), aac-1,aac-1)){
    //        sss.add(bytes);
    //    }
    //    Double sc=jedis.zscore("scorezaddlist".getBytes(),sss.get(0));
    //
    //
    //    //根据值查找该值得分数*/

}
}