package com.example.demo.dao;

import com.example.demo.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ScoreRepository extends JpaSpecificationExecutor<Score>, JpaRepository<Score,Integer> {
   List<Score> findAll();

   Score findById(Integer Id);

    @Query(nativeQuery=true,value = "select pname from score order by id")
    public List<String> findByNames();

    @Query(nativeQuery=true,value = "select pname,testscore_id from score order by id")
    public List<Object[]> findByNamesAAndTestscoreId();

    @Query(nativeQuery=true,value = "select max(pid) from score")
    public int minid();

    @Modifying
    @Query(nativeQuery=true,value = "delete from score where id=?1")
   void deletes(Integer id);




}
