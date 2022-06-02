package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface ServerSettingsResponsePacket extends BedrockPacket {
    private int formId;
    private String formData;


    public class ServerSettingsResponseReader_v291 implements BedrockPacketReader<ServerSettingsResponsePacket> {
        public static final ServerSettingsResponseReader_v291 INSTANCE = new ServerSettingsResponseReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsResponsePacket packet) {
            VarInts.writeUnsignedInt(buffer, packet.getFormId());
            helper.writeString(buffer, packet.getFormData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsResponsePacket packet) {
            packet.setFormId(VarInts.readUnsignedInt(buffer));
            packet.setFormData(helper.readString(buffer));
        }
    }

}
