package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.data.structure.StructureTemplateRequestOperation;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface StructureTemplateDataRequestPacket extends BedrockPacket {
    String name;
    Vector3i position;
    StructureSettings settings;
    StructureTemplateRequestOperation operation;


    record v361 implements StructureTemplateDataRequestPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataRequestPacket packet) {
            helper.writeString(buffer, packet.getName());
            helper.writeBlockPosition(buffer, packet.getPosition());
            helper.writeStructureSettings(buffer, packet.getSettings());
            buffer.writeByte(packet.getOperation().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataRequestPacket packet) {
            packet.setName(helper.readString(buffer));
            packet.setPosition(helper.readBlockPosition(buffer));
            packet.setSettings(helper.readStructureSettings(buffer));
            packet.setOperation(StructureTemplateRequestOperation.from(buffer.readByte()));
        }
    }

}
