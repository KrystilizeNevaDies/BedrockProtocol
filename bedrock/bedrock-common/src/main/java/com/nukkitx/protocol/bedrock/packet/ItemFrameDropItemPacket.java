package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface ItemFrameDropItemPacket extends BedrockPacket {
    Vector3i blockPosition;


    record v291 implements ItemFrameDropItemPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemFrameDropItemPacket packet) {
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemFrameDropItemPacket packet) {
            packet.setBlockPosition(helper.readBlockPosition(buffer));
        }
    }

}
