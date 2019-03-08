package com.example.demo.service;

import com.example.demo.Util.JedisUtis;
import com.example.demo.dao.ScoreRepository;
import com.example.demo.model.Score;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@Transactional
public class ScoreServiceImpl{

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ScoreRepository scoreRepository;



    private static Jedis jedis= JedisUtis.getjedis();




    public List<Score> findByPageSizeList(Integer pid, String pname, Integer size, Integer page){
        long startTime=System.currentTimeMillis();
        StringBuffer sql = new StringBuffer("select * from score a where 1=1 ");
        if (pid != null) {
            sql.append("and a.pid=" + pid);
        }
        if (pname!=null&&pname!="") {
            sql.append(" and a.pname='" + pname + "'");
        }
        Query query = entityManager.createNativeQuery(sql.toString(), Score.class);
        if(size!=null&&page!=null) {
            query.setFirstResult(page);
            query.setMaxResults(size);
        }
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return query.getResultList();
    }

   public long countss(Integer pid, String pname) {
        long total=0;
        if(!jedis.exists("scorezaddlist")) {
            StringBuffer sql = new StringBuffer("select count(*) from score a where 1=1");
            if (pid != null) {
                sql.append("and a.pid=" + pid);
            }
            if (pname != null && pname != "") {
                sql.append(" and a.pname='" + pname + "'");
            }
             Query query = entityManager.createNativeQuery(sql.toString());
             Integer s   = (Integer) query.getSingleResult();
              total=s.intValue();
         }else{
            System.out.println("redis中的缓存count");
           total=jedis.zcard("scorezaddlist");
        }
        return total;
    }
    /**
     * 导出
     * @param request
     * @param response
     * @param pid
     * @param pname
     * @throws Exception
     */
    public void downloadScoreFile(HttpServletRequest request, HttpServletResponse response, Integer pid, String pname) throws Exception {
        StringBuffer sql = new StringBuffer("select * from score a where 1=1");
        if (pid != null&&pid!=0) {
            sql.append(" and a.pid=" + pid);
        }
        if (pname!=null&&pname!="") {
            sql.append(" and a.pname='" + pname + "'");
    }
        List<Score> resultList = entityManager.createNativeQuery(sql.toString(), Score.class).getResultList();
        String path = request.getSession().getServletContext().getRealPath("/download");
        InputStream in = new FileInputStream(path + "/score.xlsx");
        XSSFWorkbook xs = new XSSFWorkbook(in);
        SXSSFWorkbook wb = new SXSSFWorkbook(xs);
        for (int i = 0; i < resultList.size(); i++) {
            SXSSFRow row = (SXSSFRow) wb.getSheetAt(0).createRow(i + 1);
            Score obj = resultList.get(i);
            row.createCell(0).setCellValue(toStringExcludeNull(obj.getId()));
            row.createCell(1).setCellValue(toStringExcludeNull(obj.getPid()));
            row.createCell(2).setCellValue(toStringExcludeNull(obj.getPname()));
            row.createCell(3).setCellValue(toStringExcludeNull(obj.getScoredate()));
            row.createCell(4).setCellValue(toStringExcludeNull(obj.getTestscoreId()));
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=scores.xlsx");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.close();
    }

    public static String toStringExcludeNull(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public List<Score> findByPageSizeListRedis(Integer pid, String pname, Integer size, Integer page) {
        long startTime=System.currentTimeMillis();
         List<Score> scoreList = new ArrayList<>();
          if(!jedis.exists("scorezaddlist")) {
            findAllByScorelist(pid, pname);
          }
         for (byte[] bytes : jedis.zrange("scorezaddlist".getBytes(),page,page+size)) {
            Score score1 = (Score) JedisUtis.unserialize(bytes);
            scoreList.add(score1);
        }

        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return scoreList;
    }

    public void findAllByScorelist(Integer pid, String pname){
            StringBuffer sql = new StringBuffer("select * from score a where 1=1 ");
            if (pid != null) {
                sql.append("and a.pid=" + pid);
            }
            if (pname != null && pname != "") {
                sql.append(" and a.pname='" + pname + "'");
            }
            Query query = entityManager.createNativeQuery(sql.toString(), Score.class);
            List<Score> scoreList = query.getResultList();
            Map<byte[], Double> mapscore = new HashMap<>();
               int i = 0;
                for (Score score1 : scoreList) {
                  mapscore.put(JedisUtis.serialize(score1), 1.0 + i);
                  i++;
                 if(i%200000==0){
                     jedis.zadd("scorezaddlist".toString().getBytes(), mapscore);
                     mapscore.clear();
                 }
            }
                 jedis.zadd("scorezaddlist".toString().getBytes(), mapscore);
                //设置缓存失效时间
                 jedis.expire("scorezaddlist", 600);
             /*   jedis.zadd("scorezaddlist".toString().getBytes(), mapscore);
                //设置缓存失效时间
                jedis.expire("scorezaddlist", 600);*/

    }

/*        public void cc(){
       List<String> scoreppnameList=scoreRepository.findByNames();
        int total=scoreRepository.minid();
       for (String s : scoreppnameList) {
        Score score1=new Score(total,"3",s,"2001-02-01");
        total++;
        entityManager.merge(score1);
    }
}
         public void b(){
        List<Object[]> scoreppnameList=scoreRepository.findByNamesAAndTestscoreId();
        int total=scoreRepository.minid()+1;
          for (Object[] s : scoreppnameList) {
            String  pname=s[0].toString();
            String testscoreId=s[1].toString();
            Score score1=new Score(total,testscoreId,pname,"2012-02-01");
            total++;
           entityManager.merge(score1);
        }


    }*/

    /**
     * 测试redis缓存
     * @return
     */
       /*     public List<Score> test(){
          long startTime=System.currentTimeMillis();
            List<Score> scoreList=new ArrayList<>();
             if(!jedis.exists("scorelist")){
                List<Score> scoreList2=scoreRepository.findAll();
                 for (Score score : scoreList2) {
                     //将查询出来的数据写入redis缓存
                     jedis.lpush("scorelist".getBytes(),JedisUtis.serialize(score));
                 }
                 //设置健位有效时间
                 jedis.expire("scorelist",120);
            }
            for (byte[] bytes : jedis.lrange("scorelist".getBytes(), 0, 1621)) {
                //反序列化取出数据
                    Score score1=(Score)JedisUtis.unserialize(bytes);
                    //保存在集合中
                    scoreList.add(score1);
                }
                //返回
                long endTime=System.currentTimeMillis();
                System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
            return scoreList;
    }

    public List<Score> finda(){
                return scoreRepository.findAll();
    }



           public List<Score> testscoe(int  start,int  end){
               List<Score> scoreList2=new ArrayList<>();
                if(!jedis.exists("scorelist")) {
                    List<Score> scoreList = scoreRepository.findAll();
                    Map<byte[], Double> map = new HashMap<>();
                    int i = 0;
                    for (Score score1 : scoreList) {
                        map.put(JedisUtis.serialize(score1), 1.0 + i);
                        i++;
                    }
                    jedis.zadd("scorelist".toString().getBytes(), map);
                }
               for (byte[] bytes : jedis.zrangeByScore("scorelist".getBytes(), start, end)) {
                   Score score=(Score)JedisUtis.unserialize(bytes);
                   scoreList2.add(score);
               }
               return scoreList2;
    }*/

    /**
     * 删除
     * @param id
     */
       public void delete(Integer id){
           //根据id找到该数据
           Score score1 = scoreRepository.findById(id);
           //开启事务
           Transaction transaction=null;
           try {
               //开启redis事务
               transaction = jedis.multi();
               //将该数据从redis缓存中删除
               transaction.zrem("scorezaddlist".getBytes(), JedisUtis.serialize(score1));
               //执行成功结束事务
               transaction.exec();
             }catch (Exception e){
               //执行失败抛出异常取消事务redis代码回滚
               transaction.discard();
               throw new RuntimeException("执行失败");
           }
           //将该数据从数据库删除
           scoreRepository.delete(id);
       }

    /**
     * 添加
     * @param score
     */
              public void add(Score score){
                  //添加到数据库
                 scoreRepository.save(score);
                  //得到当前redis集合中的总数（最大数）
                  long aac=jedis.zcard("scorezaddlist");
                  //定义一个集合用于保存最后一个数据
                  List<byte[]> sss=new ArrayList<>();
                  //根据当前最大数的索引找到当前最后一个数据
                  for (byte[] bytes : jedis.zrange("scorezaddlist".getBytes(), aac-1,aac-1)){
                      sss.add(bytes);
                  }
                  //最后一个数的分数值
                  Double sc=jedis.zscore("scorezaddlist".getBytes(),sss.get(0));
               //开启事务
               Transaction transaction=null;
               try {
                   transaction=jedis.multi();
                   //添加到缓存中
                   transaction.zadd("scorezaddlist".getBytes(),  sc + 1.0, JedisUtis.serialize(score));
                   //结束事务
                   transaction.exec();
               }catch (Exception e){
                   //执行失败抛出异常取消事务redis代码回滚
                    transaction.discard();
                   throw new RuntimeException("添加失败");
               }
     }

    /**
     * 修改
     * @param score
     */
    public void update(Score score){
            //根据id找到修改的语句
            Score score2 = scoreRepository.findById(score.getId());
            //查找原数据的有序集合中的数字
             double sc = jedis.zscore("scorezaddlist".getBytes(), JedisUtis.serialize(score2));
              Transaction transaction=null;
              try {
                  //开启事务
                  transaction=jedis.multi();
                  //将原数据删除
                  transaction.zrem("scorezaddlist".getBytes(), JedisUtis.serialize(score2));
                  //将新数据添加到缓存并将原数据的有序集合数字设置给新数据
                  transaction.zadd("scorezaddlist".getBytes(), sc, JedisUtis.serialize(score));
                  //执行成功结束事务
                  transaction.exec();
              }catch (Exception e){
                  //执行失败取消事务进行回滚
                  transaction.discard();
                  //抛出运行异常
                  throw new RuntimeException("修改失败");
              }
            //将新数据添加数据库
             scoreRepository.save(score);

    }
   //每5秒执行一次
/*    @Scheduled(fixedRate = 5000)*/
   @Scheduled(cron = "*/5 * * * * ?" )
    public void testding(){

    }

    /**
     * 以json格式保存
     * @param pid
     * @param pname
     */
    public void findAlllJson(Integer pid, String pname) {
        StringBuffer sql = new StringBuffer("select * from score a where 1=1 ");
        if (pid != null) {
            sql.append("and a.pid=" + pid);
        }
        if (pname != null && pname != "") {
            sql.append(" and a.pname='" + pname + "'");
        }
        Query query = entityManager.createNativeQuery(sql.toString(), Score.class);
        List<Score> scoreList = query.getResultList();
        Map<String,Double> stringMap=new HashMap<>();
        double i=0;
        for (Score score1 : scoreList) {
            stringMap.put(StringJson(score1),i);
                    i++;
         if(i%200000==0){
             jedis.zadd("scorezaddlist",stringMap);
             stringMap.clear();
         }

        }
        jedis.zadd("scorezaddlist",stringMap);
    }

    /**
     * 对象转换为json格式的字符串
     * @param object
     * @return
     */
    public String StringJson(Object object){
       return JSONObject.fromObject(object).toString();
    }

    public Score JsonZobj(Object object){
        //字符串转json
        JSONObject B=JSONObject.fromObject(object);
        //将json转对象
        Score score1= (Score) JSONObject.toBean(B,Score.class);
        return score1;
    }

    /**
     * json格式输出
     * @param pid
     * @param pname
     * @param size
     * @param page
     * @return
     */
    public List<Score> findByPageSizeListRedisAndJson(Integer pid, String pname, Integer size, Integer page) {
        long startTime=System.currentTimeMillis();
        List<Score> scoreList = new ArrayList<>();
        if(!jedis.exists("scorezaddlist")) {
            findAlllJson(pid, pname);
        }
        for (String obj : jedis.zrange("scorezaddlist",page,page+size)) {
            scoreList.add(JsonZobj(obj));
        }
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return scoreList;
    }


}
