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

public interface TakeItemEntityPacket extends BedrockPacket {
    long itemRuntimeEntityId;
    long runtimeEntityId;


    record v291 implements TakeItemEntityPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TakeItemEntityPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getItemRuntimeEntityId());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TakeItemEntityPacket packet) {
            packet.setItemRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        }
    }

}
