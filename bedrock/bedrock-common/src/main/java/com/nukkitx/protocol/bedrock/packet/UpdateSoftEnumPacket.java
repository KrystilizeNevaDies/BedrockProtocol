package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.SoftEnumUpdateType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface UpdateSoftEnumPacket extends BedrockPacket {
    CommandEnumData softEnum;
    SoftEnumUpdateType valueType;


    record v291 implements UpdateSoftEnumPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSoftEnumPacket packet) {
            helper.writeCommandEnum(buffer, packet.getSoftEnum());
            buffer.writeIntLE(packet.getType().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSoftEnumPacket packet) {
            packet.setSoftEnum(helper.readCommandEnum(buffer, true));
            packet.setType(SoftEnumUpdateType.values()[buffer.readIntLE()]);
        }
    }

}
