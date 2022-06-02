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

interface SubClientLoginPacket extends BedrockPacket {
    private AsciiString chainData;
    private AsciiString skinData;


    public class SubClientLoginReader_v291 implements BedrockPacketReader<SubClientLoginPacket> {
        public static final SubClientLoginReader_v291 INSTANCE = new SubClientLoginReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubClientLoginPacket packet) {
            AsciiString chainData = packet.getChainData();
            AsciiString skinData = packet.getSkinData();
            VarInts.writeUnsignedInt(buffer, chainData.length() + skinData.length() + 8);
            helper.writeLEAsciiString(buffer, chainData);
            helper.writeLEAsciiString(buffer, skinData);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubClientLoginPacket packet) {
            ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer));
            packet.setChainData(helper.readLEAsciiString(jwt));
            packet.setSkinData(helper.readLEAsciiString(jwt));
        }
    }

}
