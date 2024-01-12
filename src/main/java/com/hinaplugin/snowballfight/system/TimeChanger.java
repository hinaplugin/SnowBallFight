package com.hinaplugin.snowballfight.system;

public class TimeChanger {
    private final int time;

    public TimeChanger(final int time) {
        this.time = time;
    }

    public String Change() {
        int day;
        int hour;
        int min;
        int sec;
        if (this.time > 86400) {
            day = this.time / 86400;
            hour = this.time % 86400 / 3600;
            min = (this.time - this.time % 86400 % 3600) / 60;
            sec = this.time - (day * 86400 + hour * 3600 + min * 60);
            return day + "日" + hour + "時間" + min + "分" + sec + "秒";
        } else if (this.time > 3600) {
            hour = this.time / 3600;
            min = this.time % 3600 / 60;
            sec = this.time - (hour * 3600 + min * 60);
            return hour + "時間" + min + "分" + sec + "秒";
        } else if (this.time > 60) {
            min = this.time / 60;
            sec = this.time % 60;
            return min + "分" + sec + "秒";
        } else {
            return this.time + "秒";
        }
    }
}
