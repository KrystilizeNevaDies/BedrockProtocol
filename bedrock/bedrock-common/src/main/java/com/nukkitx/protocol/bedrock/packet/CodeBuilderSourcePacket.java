package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.CodeBuilderCategoryType;
import com.nukkitx.protocol.bedrock.data.CodeBuilderOperationType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

interface CodeBuilderSourcePacket extends BedrockPacket {

    private CodeBuilderOperationType operation;
    private CodeBuilderCategoryType category;
    private String value;


    @Overrid

    public class CodeBuilderSourceReader_v486 implements BedrockPacketReader<CodeBuilderSourcePacket> {

        public static final CodeBuilderSourceReader_v486 INSTANCE = new CodeBuilderSourceReader_v486();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CodeBuilderSourcePacket packet) {
            buffer.writeByte(packet.getOperation().ordinal());
            buffer.writeByte(packet.getCategory().ordinal());
            helper.writeString(buffer, packet.getValue());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CodeBuilderSourcePacket packet) {
            packet.setOperation(CodeBuilderOperationType.values()[buffer.readByte()]);
            packet.setCategory(CodeBuilderCategoryType.values()[buffer.readByte()]);
            packet.setValue(helper.readString(buffer));
        }
    }

}
