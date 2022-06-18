package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.BlockSyncType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

public interface UpdateBlockSyncedPacket extends UpdateBlockPacket {
    long runtimeEntityId;
    BlockSyncType entityBlockSyncType;


    public String toString() {
        return "UpdateBlockSyncedPacket(runtimeEntityId=" + this.runtimeEntityId +
                ", entityBlockSyncType=" + this.entityBlockSyncType +
                ", flags=" + this.flags +
                ", blockPosition=" + this.blockPosition +
                ", runtimeId=" + this.runtimeId +
                ", dataLayer=" + this.dataLayer +
                ")";
    }

    record v291 implements UpdateBlockSyncedPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockSyncedPacket packet) {
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeUnsignedInt(buffer, packet.getRuntimeId());
            int flagValue = 0;
            for (Flag flag : packet.getFlags()) {
                flagValue |= (1 << flag.ordinal());
            }
            VarInts.writeUnsignedInt(buffer, flagValue);
            VarInts.writeUnsignedInt(buffer, packet.getDataLayer());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeUnsignedLong(buffer, packet.getEntityBlockSyncType().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockSyncedPacket packet) {
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setRuntimeId(VarInts.readUnsignedInt(buffer));
            int flagValue = VarInts.readUnsignedInt(buffer);
            Set<Flag> flags = packet.getFlags();
            for (Flag flag : Flag.values()) {
                if ((flagValue & (1 << flag.ordinal())) != 0) {
                    flags.add(flag);
                }
            }
            packet.setDataLayer(VarInts.readUnsignedInt(buffer));
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setEntityBlockSyncType(BlockSyncType.values()[(int) VarInts.readUnsignedLong(buffer)]);
        }
    }
}
