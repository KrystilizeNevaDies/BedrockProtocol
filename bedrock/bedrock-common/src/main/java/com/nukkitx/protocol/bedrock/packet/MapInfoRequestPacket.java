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

public interface MapInfoRequestPacket extends BedrockPacket {
    private long uniqueMapId;


    public class MapInfoRequestReader_v291 implements BedrockPacketReader<MapInfoRequestPacket> {
        public static final MapInfoRequestReader_v291 INSTANCE = new MapInfoRequestReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MapInfoRequestPacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueMapId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MapInfoRequestPacket packet) {
            packet.setUniqueMapId(VarInts.readLong(buffer));
        }
    }

}
