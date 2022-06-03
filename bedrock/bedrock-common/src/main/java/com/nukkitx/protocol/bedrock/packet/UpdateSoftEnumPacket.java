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
    private CommandEnumData softEnum;
    private SoftEnumUpdateType valueType;


    public class UpdateSoftEnumReader_v291 implements BedrockPacketReader<UpdateSoftEnumPacket> {
        public static final UpdateSoftEnumReader_v291 INSTANCE = new UpdateSoftEnumReader_v291();

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
