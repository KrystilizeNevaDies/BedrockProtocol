package com.nukkitx.protocol;

import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;

@ParametersAreNonnullByDefault
public interface MinecraftSession<T extends MinecraftPacket> {

    boolean isClosed();

    void disconnect();

    @NotNull InetSocketAddress getAddress();

    default @NotNull InetSocketAddress getRealAddress() {
        return getAddress();
    }

    void sendPacket(@NotNull T packet);

    void sendPacketImmediately(@NotNull T packet);

    long getLatency();
}
