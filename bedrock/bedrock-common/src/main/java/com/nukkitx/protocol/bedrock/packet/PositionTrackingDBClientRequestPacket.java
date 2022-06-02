package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface PositionTrackingDBClientRequestPacket extends BedrockPacket {
    private Action action;
    private int trackingId;


    @Overrid

    public enum Action {
        QUERY
    }

    public class PositionTrackingDBClientRequestReader_v407 implements BedrockPacketReader<PositionTrackingDBClientRequestPacket> {
        public static final PositionTrackingDBClientRequestReader_v407 INSTANCE = new PositionTrackingDBClientRequestReader_v407();

        protected static final PositionTrackingDBClientRequestPacket.Action[] ACTIONS = PositionTrackingDBClientRequestPacket.Action.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBClientRequestPacket packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeInt(buffer, packet.getTrackingId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBClientRequestPacket packet) {
            packet.setAction(ACTIONS[buffer.readByte()]);
            packet.setTrackingId(VarInts.readInt(buffer));
        }
    }

}
