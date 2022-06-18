package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Sent to the client when the server's movement prediction system does not match what the client is sending.
 */
public interface CorrectPlayerMovePredictionPacket extends BedrockPacket {

    /**
     * Client's reported position by the server
     *
     * @return reported position
     */
    Vector3f position();

    /**
     * Difference in client and server prediction
     *
     * @return position difference
     */
    Vector3f delta();

    /**
     * If the client is on the ground. (Not falling or jumping)
     *
     * @return is client on the ground
     */
    boolean onGround();

    /**
     * The tick which is being corrected by the server.
     *
     * @return to be corrected
     */
    long tick();


    record v419(Vector3f position, Vector3f delta, boolean onGround,
                long tick) implements CorrectPlayerMovePredictionPacket {

        public static final Interpreter<v419> INTERPRETER = new Interpreter<v419>() {
            @Override
            public @NotNull v419 interpret(@NotNull BitInput input) throws IOException {
                Vector3f position = readVector3f(input);
                Vector3f delta = readVector3f(input);
                boolean onGround = readBoolean(input);
                long tick = readUnsignedLong(input);
                return new v419(position, delta, onGround, tick);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeVector3f(output, position());
            writeVector3f(output, delta());
            writeBoolean(output, onGround());
            writeUnsignedLong(output, tick());
        }
    }
}
