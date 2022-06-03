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
    private long runtimeEntityId;
    private int hotbarSlot;
    /**
     * @since v465
     */
    private boolean withData;


    public class EntityPickRequestReader_v291 implements BedrockPacketReader<EntityPickRequestPacket> {
        public static final EntityPickRequestReader_v291 INSTANCE = new EntityPickRequestReader_v291();


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

    public class EntityPickRequestReader_v465 extends EntityPickRequestReader_v291 {

        public static final EntityPickRequestReader_v465 INSTANCE = new EntityPickRequestReader_v465();

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
