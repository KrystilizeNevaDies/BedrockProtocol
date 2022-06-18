package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.LabTableReactionType;
import com.nukkitx.protocol.bedrock.data.inventory.LabTableType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface LabTablePacket extends BedrockPacket {
    LabTableType valueType;
    Vector3i position;
    LabTableReactionType reactionType;


    record v291 implements LabTablePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LabTablePacket packet) {
            buffer.writeByte(packet.getType().ordinal());
            helper.writeVector3i(buffer, packet.getPosition());
            buffer.writeByte(packet.getReactionType().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LabTablePacket packet) {
            packet.setType(LabTableType.values()[buffer.readUnsignedByte()]);
            packet.setPosition(helper.readVector3i(buffer));
            packet.setReactionType(LabTableReactionType.values()[buffer.readUnsignedByte()]);
        }
    }

}
