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
    String name;
    boolean save;
    NbtMap tag;
    StructureTemplateResponseType valueType;


    record v361 implements StructureTemplateDataResponsePacket {


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

    record v388 implements StructureTemplateDataResponsePacket {


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
