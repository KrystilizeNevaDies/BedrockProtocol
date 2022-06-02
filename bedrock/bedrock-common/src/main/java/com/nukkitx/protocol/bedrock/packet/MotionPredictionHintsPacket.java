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
 * Extension of the {@link SetEntityMotionPacket} which adds the {@link #onGround} field.
 */
interface MotionPredictionHintsPacket extends BedrockPacket {

    /**
     * The runtime ID of the entity to set motion.
     *
     * @param runtimeEntityId runtime ID
     * @return runtime ID
     */
    private long runtimeEntityId;

    /**
     * Motion to set onto the specified entity
     *
     * @param motion motion of entity
     * @return motion of entity
     */
    private Vector3f motion;

    /**
     * If the entity is on the ground. (Not falling or jumping)
     *
     * @param onGround is entity on the ground
     * @return is entity on the ground
     */
    private boolean onGround;


    @Overrid

    public class MotionPredictionHintsReader_v419 implements BedrockPacketReader<MotionPredictionHintsPacket> {

        public static final MotionPredictionHintsReader_v419 INSTANCE = new MotionPredictionHintsReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MotionPredictionHintsPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeVector3f(buffer, packet.getMotion());
            buffer.writeBoolean(packet.isOnGround());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MotionPredictionHintsPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setMotion(helper.readVector3f(buffer));
            packet.setOnGround(buffer.readBoolean());
        }
    }

}
