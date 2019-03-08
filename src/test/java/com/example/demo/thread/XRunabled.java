package com.example.demo.thread;

public class XRunabled implements Runnable {
    private int number=100;
    private String cname;

    public XRunabled(String cname){
        this.cname=cname;
    }

    public XRunabled(){}
    @Override
    public void run() {
        for (int i=0;i<=10;i++){
            try {
                /*System.out.println(i+cname);*/
                System.out.println(Thread.currentThread().getName()+i);
                        Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public String getCname() {
        return cname;
    }


    public void setCname(String name) {
        this.cname = cname;
    }
}
