package com.example.demo.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Threadtest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
  /*   Xtherone a=new Xtherone("我是a");

        a.start();*/

  /*   Thread aa=new Thread(new XRunabled("pp"),"1");
     aa.start();
     *//*aa.join();*//*
        new Thread(new XRunabled("aa")).start();*/
       /*
        new Thread(new XRunabled("cc")).start();*/
   /*     SyncTest s=new SyncTest();
         Thread a=new Thread(s,"a");
        Thread b=new Thread(s,"b");
        Thread c=new Thread(s,"c");
        Thread d=new Thread(s,"d");
        Thread e=new Thread(s,"e");
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();*/







  /*      CallableThree a=new CallableThree();
        FutureTask futureTask = new FutureTask(a);
        new Thread(futureTask,"ThreadFuture1 ").start();
        System.out.println(futureTask.get());
        FutureTask futureTask2 = new FutureTask(a);
        new Thread(futureTask2,"ThreadFuture2 ").start();
        System.out.println(futureTask2.get());*/
String s="23213";
s=s+"1111";
String cc="232131111";
        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(cc));
        System.out.println(s==cc);
        System.out.println(s.equals(cc));

    }

}
