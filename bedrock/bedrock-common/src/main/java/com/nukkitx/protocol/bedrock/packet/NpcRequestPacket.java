package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.NpcRequestType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface NpcRequestPacket extends BedrockPacket {
    long runtimeEntityId;
    NpcRequestType requestType;
    String command;
    int actionType;
    String sceneName;


    record v291 implements NpcRequestPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            buffer.writeByte(packet.getRequestType().ordinal());
            helper.writeString(buffer, packet.getCommand());
            buffer.writeByte(packet.getActionType());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setRequestType(NpcRequestType.values()[buffer.readUnsignedByte()]);
            packet.setCommand(helper.readString(buffer));
            packet.setActionType(buffer.readUnsignedByte());
        }
    }

    record v448 extends NpcRequestReader_v291 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
            super.serialize(buffer, helper, packet);
            helper.writeString(buffer, packet.getSceneName());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NpcRequestPacket packet) {
            super.deserialize(buffer, helper, packet);
            packet.setSceneName(helper.readString(buffer));
        }
    }


}
