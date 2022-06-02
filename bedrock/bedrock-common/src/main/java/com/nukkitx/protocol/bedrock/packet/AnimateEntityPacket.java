package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Used to trigger an entity animation on the specified runtime IDs to the client that receives it.
 */
interface AnimateEntityPacket extends BedrockPacket {

    /**
     * Name of the to play on the entities specified in {@link #runtimeEntityIds}
     */
    @NotNull String animation();

    /**
     * The entity state to move to when the animation has finished playing.
     */
    @NotNull String nextState();

    /**
     * Expression to check if the animation needs to stop.
     */
    @NotNull String stopExpression();

    /**
     * Name of the animation controller to use.
     */
    @NotNull String controller();

    /**
     * Time taken to blend out of the specified animation.
     */
    float blendOutTime();

    /**
     * Entity runtime IDs to run the animation on when sent to the client.
     */
    long @NotNull [] runtimeEntityIds();

    interface Versioned {
        int stopExpressionVersion();
    }

    record v419(String animation, String nextState, String stopExpression, String controller, float blendOutTime,
                long[] runtimeEntityIds) implements AnimateEntityPacket, Codec419 {

        public static final Interpreter<v419> INTERPRETER = new Interpreter<v419>() {
            @Override
            public @NotNull v419 interpret(@NotNull BitInput input) throws IOException {
                String animation = readString(input);
                String nextState = readString(input);
                String stopExpression = readString(input);
                String controller = readString(input);
                float blendOutTime = readFloatLE(input);
                long[] runtimeEntityIds = readLongArray(input);
                return new v419(animation, nextState, stopExpression, controller, blendOutTime, runtimeEntityIds);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, animation);
            writeString(output, nextState);
            writeString(output, stopExpression);
            writeString(output, controller);
            writeFloatLE(output, blendOutTime);
            writeLongArray(output, runtimeEntityIds);
        }
    }

    record v465(String animation, String nextState, String stopExpression, int stopExpressionVersion, String controller,
                float blendOutTime, long[] runtimeEntityIds) implements AnimateEntityPacket, Codec465, Versioned {
        public static final Interpreter<v465> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v465 interpret(@NotNull BitInput input) throws IOException {
                String animation = readString(input);
                String nextState = readString(input);
                String stopExpression = readString(input);
                int stopExpressionVersion = readInt(input);
                String controller = readString(input);
                float blendOutTime = readFloatLE(input);
                long[] runtimeEntityIds = readLongArray(input);
                return new v465(animation, nextState, stopExpression, stopExpressionVersion, controller, blendOutTime,
                        runtimeEntityIds);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, animation);
            writeString(output, nextState);
            writeString(output, stopExpression);
            writeInt(output, stopExpressionVersion);
            writeString(output, controller);
            writeFloatLE(output, blendOutTime);
            writeLongArray(output, runtimeEntityIds);
        }
    }
}
