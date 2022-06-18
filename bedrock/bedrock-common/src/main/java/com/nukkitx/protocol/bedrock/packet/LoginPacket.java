package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public interface LoginPacket extends BedrockPacket {
    int protocolVersion;
    AsciiString chainData;
    AsciiString skinData;


    record v291 implements LoginPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LoginPacket packet) {
            buffer.writeInt(packet.getProtocolVersion());

            AsciiString chainData = packet.getChainData();
            AsciiString skinData = packet.getSkinData();

            VarInts.writeUnsignedInt(buffer, chainData.length() + skinData.length() + 8);

            helper.writeLEAsciiString(buffer, chainData);
            helper.writeLEAsciiString(buffer, skinData);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LoginPacket packet) {
            packet.setProtocolVersion(buffer.readInt());

            ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.
            packet.setChainData(helper.readLEAsciiString(jwt));
            packet.setSkinData(helper.readLEAsciiString(jwt));
        }
    }

}
