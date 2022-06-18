package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface EntityPickRequestPacket extends BedrockPacket {
    long runtimeEntityId;
    int hotbarSlot;
    /**
     * @since v465
     */
    boolean withData;


    record v291 implements EntityPickRequestPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
            buffer.writeLongLE(packet.getRuntimeEntityId());
            buffer.writeByte(packet.getHotbarSlot());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
            packet.setRuntimeEntityId(buffer.readLongLE());
            packet.setHotbarSlot(buffer.readUnsignedByte());
        }
    }

    record v465 extends EntityPickRequestReader_v291 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
            super.serialize(buffer, helper, packet);
            buffer.writeBoolean(packet.isWithData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
            super.deserialize(buffer, helper, packet);
            packet.setWithData(buffer.readBoolean());
        }
    }


}
