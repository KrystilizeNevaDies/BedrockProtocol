package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface LevelSoundEvent1Packet extends BedrockPacket {
    private SoundEvent sound;
    private Vector3f position;
    private int extraData;
    private int pitch;
    private boolean babySound;
    private boolean relativeVolumeDisabled;


    public class LevelSoundEvent1Reader_v291 implements BedrockPacketReader<LevelSoundEvent1Packet> {
        public static final LevelSoundEvent1Reader_v291 INSTANCE = new LevelSoundEvent1Reader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent1Packet packet) {
            buffer.writeByte(helper.getSoundEventId(packet.getSound()));
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, packet.getExtraData());
            VarInts.writeInt(buffer, packet.getPitch());
            buffer.writeBoolean(packet.isBabySound());
            buffer.writeBoolean(packet.isRelativeVolumeDisabled());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent1Packet packet) {
            packet.setSound(helper.getSoundEvent(buffer.readUnsignedByte()));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setExtraData(VarInts.readInt(buffer));
            packet.setPitch(VarInts.readInt(buffer));
            packet.setBabySound(buffer.readBoolean());
            packet.setRelativeVolumeDisabled(buffer.readBoolean());
        }
    }

}
