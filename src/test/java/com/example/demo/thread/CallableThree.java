package com.example.demo.thread;

import java.util.concurrent.Callable;

public class CallableThree implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName()+" start");
        int i;
        for(i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName()+"  : "+i);
        }
        System.out.println(Thread.currentThread().getName()+" end");
        //为了体现Future的get()的等待效果，这里休息2秒
        Thread.sleep(2000);
        return Thread.currentThread().getName()+" 总共循环了"+i+"次";
    }
}
