package edu.nju.coursesystem.util;

public class LevelUtil {
    //用来设定用户的等级
    public static  int getLevel(Integer cost){
        if (cost<1000){
            return 0;
        }else if (cost<3000){
            return 1;
        }else if (cost<8000){
            return 2;
        }else if (cost<15000){
            return 3;
        }else {
            return 4;
        }
    }
    public static int getDis(Integer level){
        switch (level){
            case 0:
                return 10;
            case 1:
                return 8;
            case 2:
                return 7;
            case 3:
                return 6;
                default:
                    return 5;
        }
    }
}
