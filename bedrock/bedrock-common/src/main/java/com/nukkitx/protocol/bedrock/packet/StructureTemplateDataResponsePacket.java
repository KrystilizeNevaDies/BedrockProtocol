package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.structure.StructureTemplateResponseType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface StructureTemplateDataResponsePacket extends BedrockPacket {
    private String name;
    private boolean save;
    private NbtMap tag;
    private StructureTemplateResponseType valueType;


    public class StructureTemplateDataResponseReader_v361 implements BedrockPacketReader<StructureTemplateDataResponsePacket> {
        public static final StructureTemplateDataResponseReader_v361 INSTANCE = new StructureTemplateDataResponseReader_v361();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
            helper.writeString(buffer, packet.getName());
            boolean save = packet.isSave();
            buffer.writeBoolean(save);

            if (save) {
                helper.writeTag(buffer, packet.getTag());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
            packet.setName(helper.readString(buffer));

            boolean save = buffer.readBoolean();
            packet.setSave(save);

            if (save) {
                packet.setTag(helper.readTag(buffer));
            }
        }
    }

    public class StructureTemplateDataResponseReader_v388 implements BedrockPacketReader<StructureTemplateDataResponsePacket> {
        public static final StructureTemplateDataResponseReader_v388 INSTANCE = new StructureTemplateDataResponseReader_v388();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
            helper.writeString(buffer, packet.getName());
            boolean save = packet.isSave();
            buffer.writeBoolean(save);

            if (save) {
                helper.writeTag(buffer, packet.getTag());
            }
            buffer.writeByte(packet.getType().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
            packet.setName(helper.readString(buffer));

            boolean save = buffer.readBoolean();
            packet.setSave(save);

            if (save) {
                packet.setTag(helper.readTag(buffer));
            }
            packet.setType(StructureTemplateResponseType.from(buffer.readByte()));
        }
    }


}
