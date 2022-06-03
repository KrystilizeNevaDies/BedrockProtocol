package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface PlayerActionPacket extends BedrockPacket {
    private long runtimeEntityId;
    private PlayerActionType action;
    private Vector3i blockPosition;
    /**
     * @since v526
     */
    private Vector3i resultPosition;
    private int face;


    public class PlayerActionReader_v291 implements BedrockPacketReader<PlayerActionPacket> {
        public static final PlayerActionReader_v291 INSTANCE = new PlayerActionReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerActionPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getAction().ordinal());
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeInt(buffer, packet.getFace());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerActionPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setAction(PlayerActionType.values()[VarInts.readInt(buffer)]);
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setFace(VarInts.readInt(buffer));
        }
    }

    public class PlayerActionReaderBeta implements BedrockPacketReader<PlayerActionPacket> {
        public static final PlayerActionReaderBeta INSTANCE = new PlayerActionReaderBeta();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerActionPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getAction().ordinal());
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            helper.writeBlockPosition(buffer, packet.getResultPosition());
            VarInts.writeInt(buffer, packet.getFace());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerActionPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setAction(PlayerActionType.values()[VarInts.readInt(buffer)]);
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setResultPosition(helper.readBlockPosition(buffer));
            packet.setFace(VarInts.readInt(buffer));
        }
    }

}
