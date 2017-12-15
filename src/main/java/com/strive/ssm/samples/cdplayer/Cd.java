package com.strive.ssm.samples.cdplayer;

/**
 * @ClassName: Cd
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 14:49
 */
public class Cd {

    private final String name;
    private final Track[] tracks;

    public Cd(String name, Track[] tracks) {
        this.name = name;
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public Track[] getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return name;
    }

}
