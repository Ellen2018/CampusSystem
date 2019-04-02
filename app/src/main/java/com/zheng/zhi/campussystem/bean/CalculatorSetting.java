package com.zheng.zhi.campussystem.bean;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class CalculatorSetting {

    public static final String TAG = "CalculatorSetting";
    public static final String SKIN_COLOR_TOP = "color_top";
    public static final String SKIN_COLOR_LEFT = "color_left";
    public static final String SKIN_COLOR_RIGHT = "color_right";

    public static class ColorData{

        private List<String> colorDataList;

        public List<String> getColorDataList() {
            return colorDataList;
        }

        public void setColorDataList(List<String> colorDataList) {
            this.colorDataList = colorDataList;
        }

        public ColorData(){
            colorDataList = new ArrayList<>();
            colorDataList.add("#FF0000");
            colorDataList.add("#00FF00");
            colorDataList.add("#0000FF");
            colorDataList.add("#FFFF00");

            colorDataList.add("#DAA520");
            colorDataList.add("#D6D6D6");
            colorDataList.add("#D2691E");
            colorDataList.add("#D02090");

            colorDataList.add("#CDCD00");
            colorDataList.add("#CD3700");
            colorDataList.add("#97FFFF");
            colorDataList.add("#828282");
        }

    }

}
