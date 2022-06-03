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

public interface ModalFormRequestPacket extends BedrockPacket {
    private int formId;
    private String formData;


    public class ModalFormRequestReader_v291 implements BedrockPacketReader<ModalFormRequestPacket> {
        public static final ModalFormRequestReader_v291 INSTANCE = new ModalFormRequestReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ModalFormRequestPacket packet) {
            VarInts.writeUnsignedInt(buffer, packet.getFormId());
            helper.writeString(buffer, packet.getFormData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ModalFormRequestPacket packet) {
            packet.setFormId(VarInts.readUnsignedInt(buffer));
            packet.setFormData(helper.readString(buffer));
        }
    }

}
