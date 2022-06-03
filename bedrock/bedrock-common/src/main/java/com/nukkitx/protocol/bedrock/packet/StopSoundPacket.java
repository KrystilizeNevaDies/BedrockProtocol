package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface StopSoundPacket extends BedrockPacket {
    private String soundName;
    private boolean stoppingAllSound;


    public class StopSoundReader_v291 implements BedrockPacketReader<StopSoundPacket> {
        public static final StopSoundReader_v291 INSTANCE = new StopSoundReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StopSoundPacket packet) {
            helper.writeString(buffer, packet.getSoundName());
            buffer.writeBoolean(packet.isStoppingAllSound());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StopSoundPacket packet) {
            packet.setSoundName(helper.readString(buffer));
            packet.setStoppingAllSound(buffer.readBoolean());
        }
    }

}
