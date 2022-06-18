package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ClientboundDebugRendererType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface ClientboundDebugRendererPacket extends BedrockPacket {

    @NotNull Action action();

    interface Action extends PacketDataWriter, BitDataWritable {
        int id();

        int INVALID = 0;

        record Invalid() implements Action {
            public static final Interpreter<Invalid> INTERPRETER = input -> new Invalid();

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                // no data
            }

            @Override
            public int id() {
                return INVALID;
            }
        }

        int CLEAR_DEBUG_MARKERS = 1;

        record ClearDebugMarkers() implements Action {
            public static final Interpreter<ClearDebugMarkers> INTERPRETER = input -> new ClearDebugMarkers();

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                // no data
            }

            @Override
            public int id() {
                return CLEAR_DEBUG_MARKERS;
            }
        }

        int ADD_DEBUG_MARKER_CUBE = 2;

        record AddDebugMarkerCube(@NotNull String markerText, @NotNull Vector3f markerPosition, float markerColorRed,
                                  float markerColorGreen, float markerColorBlue, float markerColorAlpha,
                                  long markerDuration) implements Action {
            public static final Interpreter<AddDebugMarkerCube> INTERPRETER = new Interpreter<AddDebugMarkerCube>() {
                @Override
                public @NotNull AddDebugMarkerCube interpret(@NotNull BitInput input) throws IOException {
                    String markerText = readString(input);
                    Vector3f markerPosition = readVector3f(input);
                    float markerColorRed = readFloat(input);
                    float markerColorGreen = readFloat(input);
                    float markerColorBlue = readFloat(input);
                    float markerColorAlpha = readFloat(input);
                    long markerDuration = readLongLE(input);
                    return new AddDebugMarkerCube(markerText, markerPosition, markerColorRed, markerColorGreen,
                            markerColorBlue, markerColorAlpha, markerDuration);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, markerText());
                writeVector3f(output, markerPosition());
                writeFloat(output, markerColorRed());
                writeFloat(output, markerColorGreen());
                writeFloat(output, markerColorBlue());
                writeFloat(output, markerColorAlpha());
                writeLongLE(output, markerDuration());
            }

            @Override
            public int id() {
                return ADD_DEBUG_MARKER_CUBE;
            }
        }
    }


    record v428(@NotNull Action action) implements ClientboundDebugRendererPacket {
        public static final Interpreter<v428> INTERPRETER = new Interpreter<v428>() {
            @Override
            public @NotNull v428 interpret(@NotNull BitInput input) throws IOException {
                int id = readUnsignedInt(input);
                Action action = switch (id) {
                    case Action.INVALID -> Action.Invalid.INTERPRETER.interpret(input);
                    case Action.CLEAR_DEBUG_MARKERS -> Action.ClearDebugMarkers.INTERPRETER.interpret(input);
                    case Action.ADD_DEBUG_MARKER_CUBE -> Action.AddDebugMarkerCube.INTERPRETER.interpret(input);
                    default -> throw new IOException("Unknown action id: " + id);
                };
                return new v428(action);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUnsignedInt(output, action.id());
            action.write(output);
        }
    }


}
