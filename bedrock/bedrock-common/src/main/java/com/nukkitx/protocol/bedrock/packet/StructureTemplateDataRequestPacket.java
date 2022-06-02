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
    private String name;
    private Vector3i position;
    private StructureSettings settings;
    private StructureTemplateRequestOperation operation;


    public class StructureTemplateDataRequestReader_v361 implements BedrockPacketReader<StructureTemplateDataRequestPacket> {
        public static final StructureTemplateDataRequestReader_v361 INSTANCE = new StructureTemplateDataRequestReader_v361();

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
