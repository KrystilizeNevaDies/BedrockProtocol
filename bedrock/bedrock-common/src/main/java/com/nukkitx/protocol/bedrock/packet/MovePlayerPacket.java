package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface MovePlayerPacket extends BedrockPacket {
    private long runtimeEntityId;
    private Vector3f position;
    private Vector3f rotation;
    private Mode mode;
    private boolean onGround;
    private long ridingRuntimeEntityId;
    private TeleportationCause teleportationCause;
    private int entityType;
    private long tick;


    public enum Mode {
        NORMAL,
        RESPAWN,
        TELEPORT,
        HEAD_ROTATION
    }

    public enum TeleportationCause {
        UNKNOWN,
        PROJECTILE,
        CHORUS_FRUIT,
        COMMAND,
        BEHAVIOR;

        private static final InternalLogger log = InternalLoggerFactory.getInstance(TeleportationCause.class);

        private static final TeleportationCause[] VALUES = values();

        public static TeleportationCause byId(int id) {
            if (id >= 0 && id < VALUES.length) {
                return VALUES[id];
            }
            log.debug("Unknown teleportation cause ID: {}", id);
            return UNKNOWN;
        }
    }

    public class MovePlayerReader_v291 implements BedrockPacketReader<MovePlayerPacket> {
        public static final MovePlayerReader_v291 INSTANCE = new MovePlayerReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MovePlayerPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeVector3f(buffer, packet.getPosition());
            helper.writeVector3f(buffer, packet.getRotation());
            buffer.writeByte(packet.getMode().ordinal());
            buffer.writeBoolean(packet.isOnGround());
            VarInts.writeUnsignedLong(buffer, packet.getRidingRuntimeEntityId());
            if (packet.getMode() == Mode.TELEPORT) {
                buffer.writeIntLE(packet.getTeleportationCause().ordinal());
                buffer.writeIntLE(packet.getEntityType());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MovePlayerPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setRotation(helper.readVector3f(buffer));
            packet.setMode(Mode.values()[buffer.readUnsignedByte()]);
            packet.setOnGround(buffer.readBoolean());
            packet.setRidingRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            if (packet.getMode() == Mode.TELEPORT) {
                packet.setTeleportationCause(TeleportationCause.byId(buffer.readIntLE()));
                packet.setEntityType(buffer.readIntLE());
            }
        }
    }

    public class MovePlayerReader_v419 extends MovePlayerReader_v291 {

        public static final MovePlayerReader_v419 INSTANCE = new MovePlayerReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MovePlayerPacket packet) {
            super.serialize(buffer, helper, packet);

            VarInts.writeUnsignedLong(buffer, packet.getTick());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MovePlayerPacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setTick(VarInts.readUnsignedLong(buffer));
        }
    }


}
