package com.nukkitx.protocol.bedrock.wrapper;

public record PacketMetadata(int senderId, int clientId) {
    public static final PacketMetadata NONE = new PacketMetadata(-1, -1);
}
