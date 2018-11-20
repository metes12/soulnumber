package com.soullotto.soulnumber;

public enum ColorBall {
    YELLOW("#EF6C00"),
    BLUE("#33B5E5"),
    RED("#F44336"),
    GRAY("#9E9E9E"),
    GREEN("#4CAF50");

    private String color;

    ColorBall(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public static ColorBall getColorByNumber(int number) {
        if (number <= 10) {
            return YELLOW;
        } else if (number <= 20) {
            return BLUE;
        } else if (number <= 30) {
            return RED;
        } else if (number <= 40) {
            return GRAY;
        } else {
            return GREEN;
        }
    }
}
