package com.uangel.simulator.utils.sound;

import com.sun.istack.NotNull;

import java.util.function.Consumer;

/**
 * @author kangmoo Heo
 */
public interface InOutSound {
    void onInputAction(@NotNull Consumer<byte[]> c);

    void play(byte[] data);

    void close();
}
