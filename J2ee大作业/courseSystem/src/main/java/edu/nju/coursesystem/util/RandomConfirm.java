package edu.nju.coursesystem.util;

public class RandomConfirm {
    private static  String  pas = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static String pas2 = "0123456789";
    public static  String getConfirm(){
        String confirm = "";
        for (int i=0;i<8;i++){
            confirm = confirm+ pas.charAt((int)(Math.random()*26));
            confirm = confirm + pas2.charAt((int)(Math.random()*10));
        }
        return  confirm;
    }
}
