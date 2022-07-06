package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface MovePlayerPacket extends BedrockPacket {
    long runtimeEntityId();
    Vector3f position();
    Vector3f rotation();
    int modeId();
    boolean isOnGround();
    long ridingRuntimeEntityId();
    Mode mode();

    interface Mode extends BitDataWritable, PacketDataWriter {
        int id();

        @Override
        default void write(@NotNull BitOutput output) throws IOException {
        }

        int NORMAL = 0;
        record Normal() implements Mode {
            @Override
            public int id() {
                return NORMAL;
            }
            public static final Normal INSTANCE = new Normal();
        }
        int RESPAWN = 1;
        record Respawn() implements Mode {
            @Override
            public int id() {
                return RESPAWN;
            }
            public static final Respawn INSTANCE = new Respawn();
        }
        int TELEPORT = 2;
        record Teleport(@LE int cause, @LE int entityType) implements Mode {

            public Teleport(Cause cause, int entityType) {
                this(cause.id(), entityType);
            }
            @Override
            public int id() {
                return TELEPORT;
            }

            public static final Interpreter<Teleport> INTERPRETER = Interpreter.generate(Teleport.class);
            public static final Deferer<Teleport> DEFERER = Deferer.generate(Teleport.class);

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }

            enum Cause {
                UNKNOWN,
                PROJECTILE,
                CHORUS_FRUIT,
                COMMAND,
                BEHAVIOR;
                static final Cause[] VALUES = values();

                public int id() {
                    return ordinal();
                }

                public static Cause byId(int id) {
                    if (id >= 0 && id < VALUES.length) {
                        return VALUES[id];
                    }
                    return UNKNOWN;
                }
            }
        }
        int HEAD_ROTATION = 3;
        record HeadRotation() implements Mode {
            @Override
            public int id() {
                return HEAD_ROTATION;
            }
            public static final HeadRotation INSTANCE = new HeadRotation();
        }
    }

    record v291(@Unsigned long runtimeEntityId, Vector3f position, Vector3f rotation, @AsByte int modeId,
                boolean isOnGround, @Unsigned long ridingRuntimeEntityId, Mode mode) implements MovePlayerPacket {
        public v291(@Unsigned long runtimeEntityId, Vector3f position, Vector3f rotation, boolean isOnGround,
                    @Unsigned long ridingRuntimeEntityId, Mode mode) {
            this(runtimeEntityId, position, rotation, mode.id(), isOnGround, ridingRuntimeEntityId, mode);
        }

        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long runtimeEntityId = readUnsignedLong(input);
                Vector3f position = readVector3f(input);
                Vector3f rotation = readVector3f(input);
                int modeId = readUnsignedByte(input);
                boolean isOnGround = readBoolean(input);
                long ridingRuntimeEntityId = readUnsignedLong(input);
                Mode mode = switch (modeId) {
                    case Mode.NORMAL -> Mode.Normal.INSTANCE;
                    case Mode.RESPAWN -> Mode.Respawn.INSTANCE;
                    case Mode.TELEPORT -> Mode.Teleport.INTERPRETER.interpret(input);
                    case Mode.HEAD_ROTATION -> Mode.HeadRotation.INSTANCE;
                    default -> throw new IllegalArgumentException("Unknown mode: " + modeId);
                };
                return new v291(runtimeEntityId, position, rotation, isOnGround, ridingRuntimeEntityId, mode);
            }
        };

        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
    record v419(@Unsigned long runtimeEntityId, Vector3f position, Vector3f rotation, @AsByte int modeId,
                boolean isOnGround, @Unsigned long ridingRuntimeEntityId, Mode mode, @Unsigned long tick) implements MovePlayerPacket {
        public v419(@Unsigned long runtimeEntityId, Vector3f position, Vector3f rotation, boolean isOnGround,
                    @Unsigned long ridingRuntimeEntityId, Mode mode, @Unsigned long tick) {
            this(runtimeEntityId, position, rotation, mode.id(), isOnGround, ridingRuntimeEntityId, mode, tick);
        }

        public static final Interpreter<v419> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v419 interpret(@NotNull BitInput input) throws IOException {
                long runtimeEntityId = readUnsignedLong(input);
                Vector3f position = readVector3f(input);
                Vector3f rotation = readVector3f(input);
                int modeId = readUnsignedByte(input);
                boolean isOnGround = readBoolean(input);
                long ridingRuntimeEntityId = readUnsignedLong(input);
                Mode mode = switch (modeId) {
                    case Mode.NORMAL -> Mode.Normal.INSTANCE;
                    case Mode.RESPAWN -> Mode.Respawn.INSTANCE;
                    case Mode.TELEPORT -> Mode.Teleport.INTERPRETER.interpret(input);
                    case Mode.HEAD_ROTATION -> Mode.HeadRotation.INSTANCE;
                    default -> throw new IllegalArgumentException("Unknown mode: " + modeId);
                };
                long tick = readUnsignedLong(input);
                return new v419(runtimeEntityId, position, rotation, isOnGround, ridingRuntimeEntityId, mode, tick);
            }
        };

        public static final Deferer<v419> DEFERER = Deferer.generate(v419.class);

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }


}
