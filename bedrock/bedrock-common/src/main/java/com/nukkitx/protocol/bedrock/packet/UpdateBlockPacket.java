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

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public interface UpdateBlockPacket extends BedrockPacket {
    public static final Set<Flag> FLAG_ALL = Collections.unmodifiableSet(EnumSet.of(Flag.NEIGHBORS, Flag.NETWORK));
    public static final Set<Flag> FLAG_ALL_PRIORITY = Collections.unmodifiableSet(
            EnumSet.of(Flag.NEIGHBORS, Flag.NETWORK, Flag.PRIORITY));

    final Set<Flag> flags = EnumSet.noneOf(Flag.class);
    Vector3i blockPosition;
    int runtimeId;
    int dataLayer;


    public enum Flag {
        NEIGHBORS,
        NETWORK,
        NO_GRAPHIC,
        UNUSED,
        PRIORITY
    }

    record v291 implements UpdateBlockPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPacket packet) {
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeUnsignedInt(buffer, packet.getRuntimeId());
            int flagValue = 0;
            for (Flag flag : packet.getFlags()) {
                flagValue |= (1 << flag.ordinal());
            }
            VarInts.writeUnsignedInt(buffer, flagValue);
            VarInts.writeUnsignedInt(buffer, packet.getDataLayer());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPacket packet) {
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
        }
    }

}
