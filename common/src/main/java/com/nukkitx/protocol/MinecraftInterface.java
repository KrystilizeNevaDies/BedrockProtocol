package com.nukkitx.protocol;

import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public interface MinecraftInterface {

    @NotNull CompletableFuture<Void> bind();

    void close();

    @NotNull InetSocketAddress getBindAddress();
}
