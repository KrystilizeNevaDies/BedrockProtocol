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
    private Action action;
    private int trackingId;
    private NbtMap tag;


    @Overrid

    public enum Action {
        UPDATE,
        DESTROY,
        NOT_FOUND
    }

    public class PositionTrackingDBServerBroadcastReader_v407 implements BedrockPacketReader<PositionTrackingDBServerBroadcastPacket> {
        public static final PositionTrackingDBServerBroadcastReader_v407 INSTANCE = new PositionTrackingDBServerBroadcastReader_v407();

        protected static final PositionTrackingDBServerBroadcastPacket.Action[] ACTIONS = PositionTrackingDBServerBroadcastPacket.Action.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBServerBroadcastPacket packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeInt(buffer, packet.getTrackingId());
            helper.writeTag(buffer, packet.getTag());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBServerBroadcastPacket packet) {
            packet.setAction(ACTIONS[buffer.readByte()]);
            packet.setTrackingId(VarInts.readInt(buffer));
            packet.setTag(helper.readTag(buffer));
        }
    }

}
