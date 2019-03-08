package com.example.demo;

import com.example.demo.Util.JedisUtis;
import com.example.demo.Util.RedisUtils;
import com.example.demo.dao.ScoreRepository;
import com.example.demo.model.Score;
import com.example.demo.service.ScoreServiceImpl;
import net.sf.json.JSONObject;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Study5ApplicationTests {
	@Autowired
ScoreRepository scoreRepository;
	@Autowired
	ScoreServiceImpl scoreService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource
	private RedisUtils redisUtils;


	private static Jedis jedis=JedisUtis.getjedis();
	  @PersistenceContext
	  private EntityManager entityManager;

@Test
	  public void sa(){
	  List<Score> scoreList=scoreRepository.findAll();
	   Map<String,Object> map=new HashMap<>();
	   int i=0;
	for (Score score1 : scoreList) {
		map.put("score"+i,JSONObject.fromObject(score1).toString());
		i++;
	}
	redisUtils.hmset("sc",map);
    /*System.out.println(redisTemplate.opsForHash().get("sc","score1"));*/
  /*  for (String scorezaddlist : redisTemplate.opsForZSet().rangeByScore("scorezaddlist", 0, 3)) {
    *//*   Score score1=(Score)
        System.out.println(score1);*//*

    }*/

	  }
	@Test
	public void contextLoads() {
/*		List<String>  a1=new ArrayList<>();
		a1.add("1");
		a1.add("2");
		a1.add("3");
		a1.add("4");
		a1.add("3");
		a1.add("5");
   List<String>  a2=new ArrayList<>();
		a2.add("2");
		a2.add("4");
		a2.add("5");
		Set<String> ad=new TreeSet<>();
		boolean sss=true;
		for (String integer : a1) {
			sss=false;
			for (String integer1 : a2) {
			   if(integer1.equals(integer)){
				  sss=true;
				  break;
			   }
			}
			if(!sss){

				ad.add(integer);
			}

		}
		System.out.println(ad);
	}*/
/*
//使用字符串存储
Score score=scoreRepository.findById(177);
jedis= JedisUtis.getjedis();
jedis.set(score.getId().toString().getBytes(),JedisUtis.serialize(score));
Score score2=(Score) JedisUtis.unserialize(jedis.get(score.getId().toString().getBytes()));
System.out.println(score2.getId());
//使用list存储
List<Score>  scoreList=scoreRepository.findAll();
//使用hash存储
Map<byte[],byte[]> map=new HashMap<>();
		for (Score score1 : scoreList) {
			//往list存储元素
			jedis.lpush("scorelist".toString().getBytes(),JedisUtis.serialize(score1));
			//往set集合中添加元素
			jedis.sadd("scoreset".toString().getBytes(),JedisUtis.serialize(score1));
			//往map表中添加数据
			map.put(score1.getId().toString().getBytes(),JedisUtis.serialize(score1));
		}
		System.out.println("list下的");
		for(byte[] sc:jedis.lrange("scorelist".toString().getBytes(),0,100)){
			Score scores=(Score) JedisUtis.unserialize(sc);
			System.out.println(scores.getId());
		}
        //添加到redis中的hash集合中
		jedis.hmset("scoremap".toString().getBytes(),map);

		List<byte[]> scoreList1=jedis.hvals("scoremap".toString().getBytes());
		System.out.println("map下的");
       for(byte[] mapsc:scoreList1){
	    Score scores=(Score) JedisUtis.unserialize(mapsc);;
	   System.out.println(scores.getId());
   }
		System.out.println("set下的");
        Set<byte[]> scoreset=jedis.smembers("scoreset".toString().getBytes());
		for (byte[] bytes : scoreset) {
			Score f=(Score)JedisUtis.unserialize(bytes);
			System.out.println(f.getId());
		}

*/
/*
Map<byte[],Double> map=new HashMap<>();
List<Score> scoreList=scoreRepository.findAll();
		System.out.println(scoreList);
        int i=0;
	   	for (Score score1 : scoreList) {
			map.put(JedisUtis.serialize(score1),1.0+i);
			i++;
		}
       jedis.zadd("zaddlist".toString().getBytes(),map);
		for (byte[] bytes : jedis.zrangeByScore("zaddlist".toString().getBytes(), 0, 10)) {
			Score score1=(Score)JedisUtis.unserialize(bytes);
			System.out.println(score1);
		}
*/
        /*Score score=scoreRepository.findById(186);
		jedis.zrem("zaddlist".toString().getBytes(),JedisUtis.serialize(score));
		for (byte[] bytes : jedis.zrangeByScore("zaddlist".toString().getBytes(), 0, 10)) {
			Score score1=(Score)JedisUtis.unserialize(bytes);
			System.out.println(score1);
		}*/
		long startTime=System.currentTimeMillis();   //获取开始时间
		for (Score score1 : scoreService.findByPageSizeListRedis(null, null, 1600, 0)) {
			System.out.println(score1);
		}
		long endTime=System.currentTimeMillis(); //获取结束时间

		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

	}
@Test
public void c() throws JSONException {

//字符串保存为json

	List<Score> s=scoreRepository.findAll();
	//将对象转换为json格式的字符串
	String scorejson= JSONObject.fromObject(s.get(0)).toString();
	//保存
	jedis.set("test2",scorejson);
     String aa=jedis.get("test2");
    //字符串转json
    JSONObject B=JSONObject.fromObject(aa);
    //将json转对象
	Score score1= (Score) JSONObject.toBean(B,Score.class);
	//输出
	System.out.println(score1);
/*	System.out.println(B.getString("name"));*/




/*
	//判断hash是否存在(此处为不存在)
	if(!jedis.exists("scorelisthash")){
		//查询出所有数据
		List<Score> scoreList=scoreRepository.findAll();
      //定义一个map用于保存数据
		Map<byte[],byte[]> map=new HashMap<>();
		for (Score score1 : scoreList) {
			//根据名称为健
			map.put(score1.getPname().getBytes(),JedisUtis.serialize(score1));
		}
		//添加到hash中
		jedis.hmset("scorelisthash".getBytes(),map);
	}

	//以下是输出   可以一个也可以多个
	List<byte[]> scorelist=jedis.hmget("scorelisthash".getBytes(),"车牌号1".getBytes(),"车牌号2".getBytes());
	//反序列化取出
	for (byte[] bytes : scorelist) {
		//强转
		Score score1=(Score)JedisUtis.unserialize(bytes);
		//输出
		System.out.println(score1);
	}*/
}


}
