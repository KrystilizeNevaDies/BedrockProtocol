package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface EntityEventPacket extends BedrockPacket {
    private long runtimeEntityId;
    private EntityEventType valueType;
    private int data;


    public class EntityEventReader_v291 implements BedrockPacketReader<EntityEventPacket> {
        public static final EntityEventReader_v291 INSTANCE = new EntityEventReader_v291();
        private static final InternalLogger log = InternalLoggerFactory.getInstance(EntityEventReader_v291.class);

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityEventPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            buffer.writeByte(helper.getEntityEventId(packet.getType()));
            VarInts.writeInt(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityEventPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            int event = buffer.readUnsignedByte();
            packet.setType(helper.getEntityEvent(event));
            packet.setData(VarInts.readInt(buffer));
            if (packet.getType() == null) {
                log.debug("Unknown EntityEvent {} in packet {}", event, packet);
            }
        }
    }

}
