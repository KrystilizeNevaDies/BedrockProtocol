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
 * Extension of the {@link SetEntityMotionPacket} which adds the {@link #isOnGround} field.
 */
interface MotionPredictionHintsPacket extends BedrockPacket {

    /**
     * The runtime ID of the entity to set motion.
     * @return runtime ID
     */
    long runtimeEntityId();

    /**
     * Motion to set onto the specified entity
     * @return motion of entity
     */
    Vector3f motion();

    /**
     * If the entity is on the ground. (Not falling or jumping)
     * @return is entity on the ground
     */
    boolean isOnGround();


    record v419(@Unsigned long runtimeEntityId, Vector3f motion, boolean isOnGround) implements MotionPredictionHintsPacket {
        public static final Interpreter<v419> INTERPRETER = Interpreter.generate(v419.class);
        public static final Deferer<v419> DEFERER = Deferer.generate(v419.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput output) throws java.io.IOException {
            DEFERER.defer(output, this);
        }
    }

}
