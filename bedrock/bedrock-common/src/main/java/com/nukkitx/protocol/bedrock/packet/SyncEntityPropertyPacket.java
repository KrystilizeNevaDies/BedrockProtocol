package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface SyncEntityPropertyPacket extends BedrockPacket {
    NbtMap data;


    record v440 implements SyncEntityPropertyPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SyncEntityPropertyPacket packet) {
            helper.writeTag(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SyncEntityPropertyPacket packet) {
            packet.setData(helper.readTag(buffer));
        }
    }

}
