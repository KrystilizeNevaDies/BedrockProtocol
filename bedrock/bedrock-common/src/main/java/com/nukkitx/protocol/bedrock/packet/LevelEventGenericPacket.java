package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface LevelEventGenericPacket extends BedrockPacket {
    int eventId;
    NbtMap tag;


    record v361 implements LevelEventGenericPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventGenericPacket packet) {
            VarInts.writeInt(buffer, packet.getEventId());
            helper.writeTagValue(buffer, packet.getTag());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventGenericPacket packet) {
            packet.setEventId(VarInts.readInt(buffer));
            packet.setTag(helper.readTagValue(buffer, NbtType.COMPOUND));
        }
    }

}
