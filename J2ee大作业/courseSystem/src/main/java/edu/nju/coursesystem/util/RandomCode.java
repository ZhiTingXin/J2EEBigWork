package edu.nju.coursesystem.util;

public class RandomCode {
    private static  String  pas = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static String pas2 = "0123456789";
    public static  String getCode(){
        String confirm = "";
        for (int i=0;i<3;i++){
            confirm = confirm+ pas.charAt((int)(Math.random()*26));
            confirm = confirm + pas2.charAt((int)(Math.random()*10));
        }
        confirm = confirm+pas.charAt((int)(Math.random()*26));
        return  confirm;
    }
}
