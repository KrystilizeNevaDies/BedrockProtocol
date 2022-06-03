package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SetSpawnPositionPacket extends BedrockPacket {
    // spawnType is the valueType of spawn to set. It is either PLAYER_SPAWN or WORLD_SPAWN, and specifies
    // the behaviour of the spawn set. If WORLD_SPAWN is set, the position to which compasses will point is
    // also changed.
    private Type spawnType;

    // blockPosition is the new position of the spawn that was set. If spawnType is WORLD_SPAWN, compasses will
    // point to this position. As of 1.16, blockPosition is always the position of the player.
    private Vector3i blockPosition;

    // dimensionId is the ID of the dimension that had its spawn updated. This is specifically relevant for
    // behaviour added in 1.16 such as the respawn anchor, which allows setting the spawn in a specific
    // dimension.
    private int dimensionId;

    // SpawnPosition is a new field added in 1.16. It holds the spawn position of the world. This spawn
    // position is {-2147483648, -2147483648, -2147483648} for a default spawn position.
    private Vector3i spawnPosition = Vector3i.from(-2147483648, -2147483648, -2147483648);

    @Deprecated
    private boolean spawnForced;


    public enum Type {
        PLAYER_SPAWN,
        WORLD_SPAWN
    }

    public class SetSpawnPositionReader_v291 implements BedrockPacketReader<SetSpawnPositionPacket> {
        public static final SetSpawnPositionReader_v291 INSTANCE = new SetSpawnPositionReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetSpawnPositionPacket packet) {
            VarInts.writeInt(buffer, packet.getSpawnType().ordinal());
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            buffer.writeBoolean(packet.isSpawnForced());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetSpawnPositionPacket packet) {
            packet.setSpawnType(SetSpawnPositionPacket.Type.values()[VarInts.readInt(buffer)]);
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setSpawnForced(buffer.readBoolean());
        }
    }

    public class SetSpawnPositionReader_v407 implements BedrockPacketReader<SetSpawnPositionPacket> {
        public static final SetSpawnPositionReader_v407 INSTANCE = new SetSpawnPositionReader_v407();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetSpawnPositionPacket packet) {
            VarInts.writeInt(buffer, packet.getSpawnType().ordinal());
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeInt(buffer, packet.getDimensionId());
            helper.writeBlockPosition(buffer, packet.getSpawnPosition());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetSpawnPositionPacket packet) {
            packet.setSpawnType(SetSpawnPositionPacket.Type.values()[VarInts.readInt(buffer)]);
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setDimensionId(VarInts.readInt(buffer));
            packet.setSpawnPosition(helper.readBlockPosition(buffer));
        }
    }


}
