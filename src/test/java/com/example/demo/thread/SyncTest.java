package com.example.demo.thread;

public class SyncTest implements Runnable {
    private int num =100000;
    public SyncTest(){}

    @Override
    public synchronized void run() {
        while (true) {
            try {
                /* synchronized (this) {*/
                System.out.println(Thread.currentThread().getName() + "卖出"
                        + num + "号");
                num--;
                /* Thread.yield();	*/    //该行为了方便更容易出现模拟效果

                /*  }*/
                if (num <= 1) {
                        break;
                }
            }catch (Exception e){
                System.out.println("1");
            }

        }
    }

}
