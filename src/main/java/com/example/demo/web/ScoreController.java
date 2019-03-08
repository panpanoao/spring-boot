package com.example.demo.web;


import com.example.demo.dao.ScoreRepository;
import com.example.demo.model.Score;
import com.example.demo.service.ScoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScoreController {
@Autowired
private ScoreRepository scoreRepository;



@Autowired
private ScoreServiceImpl scoreService;



    /**
     * 分页查询
     * @param pid
     * @param pname
     * @param pageSize
     * @param pageNumber
     * @return
     */
@RequestMapping("/scorelist")
public Map<String,Object>  findByPageAndSizeList(@RequestParam(value="pid",required=false)Integer pid,
                                                    @RequestParam(value="pname",required=false)String pname,
                                                    @RequestParam(value="pageSize",required=false)Integer pageSize,
                                                    @RequestParam(value="pageNumber",required=false)Integer pageNumber
                                               ){
    Map<String,Object> map=new HashMap<>();
    List<Score> scoreList=scoreService.findByPageSizeListRedis(pid,pname,pageSize,pageNumber);
     long total=scoreService.countss(pid,pname);
     System.out.println(total);
    map.put("total",total);
    map.put("rows",scoreList);
    System.out.println(map);
  /*  System.out.println(map);*/
    return map;
}


@RequestMapping("/")
public ModelAndView aa(){
    ModelAndView a=new ModelAndView();
    a.setViewName("/WEB-INF/jsp/hello.jsp");
    return a;
}

    /**
     * 批量删除
     * @param list
     * @return
     */
    @RequestMapping("/deletes")
    public boolean delete(Integer[] list){
    if(list!=null) {
        for (Integer integer : list) {
         scoreService.delete(integer);
        }
        return true;
        }else{
        return false;
    }
 }


    /**
     * addOrUpdate
     * @param score
     * @return
     */

     @RequestMapping("/add")
      public boolean save(Score score) {
     if(score!=null){
           if(score.getId()==null) {
              scoreService.add(score);
           }else{
           scoreService.update(score);
           }
         return true;
     }else{
         return false;
     }
    }


    @RequestMapping(value = "download/ScoreExcelFile", method = RequestMethod.GET)
    public void downloadScoreFile(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "pid", required = false) Integer pid,
                                  @RequestParam(value = "pname", required = false) String pname) throws Exception {
        scoreService.downloadScoreFile(request,response,pid,pname);
    }



}
