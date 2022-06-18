package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface PlaySoundPacket extends BedrockPacket {
    String sound;
    Vector3f position;
    float volume;
    float pitch;


    record v291 implements PlaySoundPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlaySoundPacket packet) {
            helper.writeString(buffer, packet.getSound());
            helper.writeBlockPosition(buffer, packet.getPosition().mul(8).toInt());
            buffer.writeFloatLE(packet.getVolume());
            buffer.writeFloatLE(packet.getPitch());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlaySoundPacket packet) {
            packet.setSound(helper.readString(buffer));
            packet.setPosition(helper.readBlockPosition(buffer).toFloat().div(8));
            packet.setVolume(buffer.readFloatLE());
            packet.setPitch(buffer.readFloatLE());
        }
    }

}
