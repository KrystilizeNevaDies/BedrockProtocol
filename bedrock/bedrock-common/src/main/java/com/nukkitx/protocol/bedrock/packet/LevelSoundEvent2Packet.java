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

interface LevelSoundEvent2Packet extends BedrockPacket {
    SoundEvent sound;
    Vector3f position;
    int extraData;
    String identifier;
    boolean babySound;
    boolean relativeVolumeDisabled;


    public class LevelSoundEvent2Reader_v313 implements LevelSoundEvent2Packet {
        public static final LevelSoundEvent2Reader_v313 INSTANCE = new LevelSoundEvent2Reader_v313();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent2Packet packet) {
            buffer.writeByte(helper.getSoundEventId(packet.getSound()));
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, packet.getExtraData());
            helper.writeString(buffer, packet.getIdentifier());
            buffer.writeBoolean(packet.isBabySound());
            buffer.writeBoolean(packet.isRelativeVolumeDisabled());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent2Packet packet) {
            packet.setSound(helper.getSoundEvent(buffer.readUnsignedByte()));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setExtraData(VarInts.readInt(buffer));
            packet.setIdentifier(helper.readString(buffer));
            packet.setBabySound(buffer.readBoolean());
            packet.setRelativeVolumeDisabled(buffer.readBoolean());
        }
    }

    public class LevelSoundEvent2Reader_v407 implements LevelSoundEvent2Packet {
        public static final LevelSoundEvent2Reader_v407 INSTANCE = new LevelSoundEvent2Reader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent2Packet packet) {
            VarInts.writeUnsignedInt(buffer, helper.getSoundEventId(packet.getSound()));
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, packet.getExtraData());
            helper.writeString(buffer, packet.getIdentifier());
            buffer.writeBoolean(packet.isBabySound());
            buffer.writeBoolean(packet.isRelativeVolumeDisabled());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent2Packet packet) {
            packet.setSound(helper.getSoundEvent(VarInts.readUnsignedInt(buffer)));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setExtraData(VarInts.readInt(buffer));
            packet.setIdentifier(helper.readString(buffer));
            packet.setBabySound(buffer.readBoolean());
            packet.setRelativeVolumeDisabled(buffer.readBoolean());
        }
    }


}
