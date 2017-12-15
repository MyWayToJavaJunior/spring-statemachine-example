package com.strive.ssm.samples.cdplayer;

/**
 * @ClassName: Track
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 14:49
 */
public class Track {

    private final String name;
    private final long length;

    public Track(String name, long length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return name;
    }

}
