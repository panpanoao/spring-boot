package com.example.demo.thread;


    public class Digui {
        public static int digui(int n){
            if(n==1||n==0){
                return n;
            }else{
                System.out.println("执行第" + n + "次");
                return n*digui(n-1);
            }
        }
        public static void main (String[] args){
          int s=digui(1);
            System.out.println(s);
        }


}
