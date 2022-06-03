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

public interface RequestChunkRadiusPacket extends BedrockPacket {
    private int radius;


    public class RequestChunkRadiusReader_v291 implements BedrockPacketReader<RequestChunkRadiusPacket> {
        public static final RequestChunkRadiusReader_v291 INSTANCE = new RequestChunkRadiusReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestChunkRadiusPacket packet) {
            VarInts.writeInt(buffer, packet.getRadius());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestChunkRadiusPacket packet) {
            packet.setRadius(VarInts.readInt(buffer));
        }
    }

}
