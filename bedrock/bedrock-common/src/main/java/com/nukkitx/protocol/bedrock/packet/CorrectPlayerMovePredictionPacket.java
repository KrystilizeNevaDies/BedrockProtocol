package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sent to the client when the server's movement prediction system does not match what the client is sending.
 */
interface CorrectPlayerMovePredictionPacket extends BedrockPacket {

    /**
     * Client's reported position by the server
     *
     * @param position reported position
     * @return reported position
     */
    private Vector3f position;

    /**
     * Difference in client and server prediction
     *
     * @param delta position difference
     * @return position difference
     */
    private Vector3f delta;

    /**
     * If the client is on the ground. (Not falling or jumping)
     *
     * @param onGround is client on the ground
     * @return is client on the ground
     */
    private boolean onGround;

    /**
     * The tick which is being corrected by the server.
     *
     * @param tick to be corrected
     * @return to be corrected
     */
    private long tick;


    @Overrid

    public class CorrectPlayerMovePredictionReader_v419 implements BedrockPacketReader<CorrectPlayerMovePredictionPacket> {

        public static final CorrectPlayerMovePredictionReader_v419 INSTANCE = new CorrectPlayerMovePredictionReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CorrectPlayerMovePredictionPacket packet) {
            helper.writeVector3f(buffer, packet.getPosition());
            helper.writeVector3f(buffer, packet.getDelta());
            buffer.writeBoolean(packet.isOnGround());
            VarInts.writeUnsignedLong(buffer, packet.getTick());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CorrectPlayerMovePredictionPacket packet) {
            packet.setPosition(helper.readVector3f(buffer));
            packet.setDelta(helper.readVector3f(buffer));
            packet.setOnGround(buffer.readBoolean());
            packet.setTick(VarInts.readUnsignedInt(buffer));
        }
    }

}
