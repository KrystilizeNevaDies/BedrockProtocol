package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface RiderJumpPacket extends BedrockPacket {
    private int jumpStrength;


    public class RiderJumpReader_v291 implements BedrockPacketReader<RiderJumpPacket> {
        public static final RiderJumpReader_v291 INSTANCE = new RiderJumpReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RiderJumpPacket packet) {
            VarInts.writeUnsignedInt(buffer, packet.getJumpStrength());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RiderJumpPacket packet) {
            packet.setJumpStrength(VarInts.readInt(buffer));
        }
    }

}
