package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface PositionTrackingDBServerBroadcastPacket extends BedrockPacket {
    Action action;
    int trackingId;
    NbtMap tag;


    @Overrid

    public enum Action {
        UPDATE,
        DESTROY,
        NOT_FOUND
    }

    record v407 implements PositionTrackingDBServerBroadcastPacket {


        protected static final PositionTrackingDBServerBroadcastPacket.Action[] ACTIONS = PositionTrackingDBServerBroadcastPacket.Action.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBServerBroadcastPacket
                packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeInt(buffer, packet.getTrackingId());
            helper.writeTag(buffer, packet.getTag());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBServerBroadcastPacket
                packet) {
            packet.setAction(ACTIONS[buffer.readByte()]);
            packet.setTrackingId(VarInts.readInt(buffer));
            packet.setTag(helper.readTag(buffer));
        }
    }

}
