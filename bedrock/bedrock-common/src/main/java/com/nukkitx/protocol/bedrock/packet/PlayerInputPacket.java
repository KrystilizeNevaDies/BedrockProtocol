package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface PlayerInputPacket extends BedrockPacket {
    private Vector2f inputMotion;
    private boolean jumping;
    private boolean sneaking;


    public class PlayerInputReader_v291 implements BedrockPacketReader<PlayerInputPacket> {
        public static final PlayerInputReader_v291 INSTANCE = new PlayerInputReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerInputPacket packet) {
            helper.writeVector2f(buffer, packet.getInputMotion());
            buffer.writeBoolean(packet.isJumping());
            buffer.writeBoolean(packet.isSneaking());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerInputPacket packet) {
            packet.setInputMotion(helper.readVector2f(buffer));
            packet.setJumping(buffer.readBoolean());
            packet.setSneaking(buffer.readBoolean());
        }
    }

}
