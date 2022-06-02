package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;

@FunctionalInterface
public interface BedrockPacketFactory<T extends BedrockPacket> {

    BedrockPacket newInstance();

    @SuppressWarnings("unchecked")
    default Class<BedrockPacket> getPacketClass() {
        return (Class<BedrockPacket>) newInstance().getClass();
    }
}
