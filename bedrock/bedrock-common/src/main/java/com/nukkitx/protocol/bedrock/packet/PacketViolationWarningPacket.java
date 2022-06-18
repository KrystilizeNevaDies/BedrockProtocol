package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PacketViolationSeverity;
import com.nukkitx.protocol.bedrock.data.PacketViolationType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface PacketViolationWarningPacket extends BedrockPacket {
    PacketViolationType valueType;
    PacketViolationSeverity severity;
    int packetCauseId;
    String context;


    record v407 implements PacketViolationWarningPacket {


        protected static final PacketViolationType[] TYPES = PacketViolationType.values();
        protected static final PacketViolationSeverity[] SEVERITIES = PacketViolationSeverity.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PacketViolationWarningPacket packet) {
            VarInts.writeInt(buffer, packet.getType().ordinal() - 1);
            VarInts.writeInt(buffer, packet.getSeverity().ordinal() - 1);
            VarInts.writeInt(buffer, packet.getPacketCauseId());
            helper.writeString(buffer, packet.getContext());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PacketViolationWarningPacket packet) {
            packet.setType(TYPES[VarInts.readInt(buffer) + 1]);
            packet.setSeverity(SEVERITIES[VarInts.readInt(buffer) + 1]);
            packet.setPacketCauseId(VarInts.readInt(buffer));
            packet.setContext(helper.readString(buffer));
        }
    }

}
