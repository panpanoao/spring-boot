package com.example.demo.thread;

public class Xtherone extends Thread {
private String cname;

    public Xtherone(String cname){
        this.cname=cname;
    }

    public  synchronized  void run(){
        System.out.println("线程开始");
        for (int i=0;i<=10;i++){
            try {
                System.out.println(i+cname);
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
