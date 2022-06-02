package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;

interface LevelSoundEventPacket extends LevelSoundEvent2Packet {


    @Overrid

    public class LevelSoundEventReader_v332 implements BedrockPacketReader<LevelSoundEventPacket> {
        public static final LevelSoundEventReader_v332 INSTANCE = new LevelSoundEventReader_v332();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEventPacket packet) {
            VarInts.writeUnsignedInt(buffer, helper.getSoundEventId(packet.getSound()));
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, packet.getExtraData());
            helper.writeString(buffer, packet.getIdentifier());
            buffer.writeBoolean(packet.isBabySound());
            buffer.writeBoolean(packet.isRelativeVolumeDisabled());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEventPacket packet) {
            packet.setSound(helper.getSoundEvent(VarInts.readUnsignedInt(buffer)));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setExtraData(VarInts.readInt(buffer));
            packet.setIdentifier(helper.readString(buffer));
            packet.setBabySound(buffer.readBoolean());
            packet.setRelativeVolumeDisabled(buffer.readBoolean());
        }
    }

    public class LevelSoundEventReader_v407 implements BedrockPacketReader<LevelSoundEventPacket> {
        public static final LevelSoundEventReader_v407 INSTANCE = new LevelSoundEventReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEventPacket packet) {
            VarInts.writeUnsignedInt(buffer, helper.getSoundEventId(packet.getSound()));
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, packet.getExtraData());
            helper.writeString(buffer, packet.getIdentifier());
            buffer.writeBoolean(packet.isBabySound());
            buffer.writeBoolean(packet.isRelativeVolumeDisabled());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEventPacket packet) {
            packet.setSound(helper.getSoundEvent(VarInts.readUnsignedInt(buffer)));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setExtraData(VarInts.readInt(buffer));
            packet.setIdentifier(helper.readString(buffer));
            packet.setBabySound(buffer.readBoolean());
            packet.setRelativeVolumeDisabled(buffer.readBoolean());
        }
    }




}
