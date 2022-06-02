package com.nukkitx.protocol;

import com.nukkitx.network.util.DisconnectReason;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public interface PlayerSession {

    boolean isClosed();

    void close();

    void onDisconnect(@NotNull DisconnectReason reason);

    void onDisconnect(@NotNull String reason);
}
