package com.example.teithe.uv_thessaloniki;

/**
 * Created by christos on 27/6/2016.
 */
public class UV {

    private String risk;
    private int color;
    private String tips;

    public UV(String risk, int color, String tips) {
        this.risk = risk;
        this.color = color;
        this.tips = tips;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
