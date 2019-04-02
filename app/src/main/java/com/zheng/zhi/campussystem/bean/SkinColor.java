package com.zheng.zhi.campussystem.bean;

import java.util.ArrayList;
import java.util.List;

public class SkinColor {

    public static final String TOP_COLOR = "#FFFFFF";
    public static final String LEFT_COLOR = "#66CDAA";
    public static final String RIGHT_COLOR = "#FFC125";
    private String topColor;
    private String leftColor;
    private String rightColor;
    public static List<SkinColor> skinColors;

    public SkinColor(String topColor,String leftColor,String rightColor){
        this.topColor = topColor;
        this.leftColor = leftColor;
        this.rightColor = rightColor;
    }

    public SkinColor(){
        skinColors = new ArrayList<>();
        skinColors.add(new SkinColor(TOP_COLOR,LEFT_COLOR,RIGHT_COLOR));
        skinColors.add(new SkinColor("#ffffff","#2b3e4f","#157abf"));
        skinColors.add(new SkinColor("#ffffff","#2b3e4f","#fa8329"));
        skinColors.add(new SkinColor("#ffffff","#f46999","#30c2f0"));
        skinColors.add(new SkinColor("#ffffff","#f46999","#ff0080"));
        skinColors.add(new SkinColor("#ffffff","#2896d8","#ff0080"));
        skinColors.add(new SkinColor("#ffffff","#00488c","#ff0080"));
        skinColors.add(new SkinColor("#ffffff","#57952b","#74cc3b"));
        skinColors.add(new SkinColor("#ffffff","#875cdb","#ffb53f"));
    }

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public String getLeftColor() {
        return leftColor;
    }

    public void setLeftColor(String leftColor) {
        this.leftColor = leftColor;
    }

    public String getRightColor() {
        return rightColor;
    }

    public void setRightColor(String rightColor) {
        this.rightColor = rightColor;
    }

    public static List<SkinColor> getSkinColors() {
        return skinColors;
    }

    public void setSkinColors(List<SkinColor> skinColors) {
        this.skinColors = skinColors;
    }
}
