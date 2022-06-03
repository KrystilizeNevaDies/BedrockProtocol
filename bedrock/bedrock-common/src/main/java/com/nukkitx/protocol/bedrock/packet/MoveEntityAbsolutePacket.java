package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface MoveEntityAbsolutePacket extends BedrockPacket {
    private long runtimeEntityId;
    private Vector3f position;
    private Vector3f rotation;
    private boolean onGround;
    private boolean teleported;


    public class MoveEntityAbsoluteReader_v291 implements BedrockPacketReader<MoveEntityAbsolutePacket> {
        public static final MoveEntityAbsoluteReader_v291 INSTANCE = new MoveEntityAbsoluteReader_v291();

        private static final int FLAG_ON_GROUND = 0x1;
        private static final int FLAG_TELEPORTED = 0x2;

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityAbsolutePacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            int flags = 0;
            if (packet.isOnGround()) {
                flags |= FLAG_ON_GROUND;
            }
            if (packet.isTeleported()) {
                flags |= FLAG_TELEPORTED;
            }
            buffer.writeByte(flags);
            helper.writeVector3f(buffer, packet.getPosition());
            helper.writeByteRotation(buffer, packet.getRotation());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityAbsolutePacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            int flags = buffer.readUnsignedByte();
            packet.setOnGround((flags & FLAG_ON_GROUND) != 0);
            packet.setTeleported((flags & FLAG_TELEPORTED) != 0);
            packet.setPosition(helper.readVector3f(buffer));
            packet.setRotation(helper.readByteRotation(buffer));
        }
    }
}
