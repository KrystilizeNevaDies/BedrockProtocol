package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface OnScreenTextureAnimationPacket extends BedrockPacket {
    private long effectId;


    public class OnScreenTextureAnimationReader_v354 implements BedrockPacketReader<OnScreenTextureAnimationPacket> {
        public static final OnScreenTextureAnimationReader_v354 INSTANCE = new OnScreenTextureAnimationReader_v354();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, OnScreenTextureAnimationPacket packet) {
            buffer.writeIntLE((int) packet.getEffectId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, OnScreenTextureAnimationPacket packet) {
            packet.setEffectId(buffer.readUnsignedIntLE());
        }
    }

}
