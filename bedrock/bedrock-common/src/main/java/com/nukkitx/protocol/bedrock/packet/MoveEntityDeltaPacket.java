package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.util.TriConsumer;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public interface MoveEntityDeltaPacket extends BedrockPacket {

    long runtimeEntityId();
    Flag[] flags();

    interface Flag extends BitDataWritable, PacketDataWriter {

        interface Codec291 extends Flag { }
        interface Codec388 extends Flag { }
        interface Codec419 extends Flag { }

        int id();

        @Override
        default void write(@NotNull BitOutput output) throws IOException {
        }

        int DELTA_X = 0;
        record DeltaX(int x) implements Codec291, Codec388 {
            @Override
            public int id() {
                return DELTA_X;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, x());
            }
        }
        record DeltaX419(@LE float value) implements Codec419 {
            public int id() {
                return DELTA_X;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeFloatLE(output, value());
            }
        }
        int DELTA_Y = 1;
        record DeltaY(int y) implements Codec291, Codec388 {
            @Override
            public int id() {
                return DELTA_Y;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, y());
            }
        }

        record DeltaY419(@LE float y) implements Codec419 {
            @Override
            public int id() {
                return DELTA_Y;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeFloatLE(output, y());
            }
        }
        int DELTA_Z = 2;
        record DeltaZ(int z) implements Codec291, Codec388 {
            @Override
            public int id() {
                return DELTA_Z;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, z());
            }
        }
        record DeltaZ419(@LE float z) implements Codec419 {
            @Override
            public int id() {
                return DELTA_Z;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeFloatLE(output, z());
            }
        }
        
        int DELTA_PITCH = 3;
        record DeltaPitch(@ByteAngle float pitch) implements Codec291, Codec388 {
            @Override
            public int id() {
                return DELTA_PITCH;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeByteAngle(output, pitch());
            }
        }
        int DELTA_YAW = 4;
        record DeltaYaw(@ByteAngle float yaw) implements Codec291, Codec388 {
            @Override
            public int id() {
                return DELTA_YAW;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeByteAngle(output, yaw());
            }
        }
        int DELTA_HEAD_YAW = 5;
        record DeltaHeadYaw(float headYaw) implements Codec291, Codec388 {
            @Override
            public int id() {
                return DELTA_HEAD_YAW;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeByteAngle(output, headYaw());
            }
        }
        int ON_GROUND = 6;
        record OnGround() implements Codec291, Codec388, Codec419 {
            @Override
            public int id() {
                return ON_GROUND;
            }

            public static final OnGround INSTANCE = new OnGround();
        }
        int TELEPORTING = 7;
        record Teleporting() implements Codec291, Codec388, Codec419 {
            @Override
            public int id() {
                return TELEPORTING;
            }

            public static final Teleporting INSTANCE = new Teleporting();
        }
        int FORCE_MOVE_LOCAL_ENTITY = 8;
        record ForceMoveLocalEntity() implements Codec291, Codec388, Codec419 {
            @Override
            public int id() {
                return FORCE_MOVE_LOCAL_ENTITY;
            }

            public static final ForceMoveLocalEntity INSTANCE = new ForceMoveLocalEntity();
        }
    }

    record v291(@Unsigned long runtimeEntityId, @AsByte int flagMask, Flag.Codec291[] flags) implements MoveEntityDeltaPacket {
        public v291(long runtimeEntityId, Collection<Flag.Codec291> flags) {
            this(runtimeEntityId, flagMask(flags), uniqueOrderedFlags(flags));
        }

        private static int flagMask(Collection<? extends Flag> flags) {
            int mask = 0;
            for (Flag flag : flags) {
                mask |= 1 << flag.id();
            }
            return mask;
        }

        private static <F extends Flag> F[] uniqueOrderedFlags(Collection<F> flags) {
            //noinspection unchecked
            return (F[]) flags.stream()
                    .distinct()
                    .sorted(Comparator.comparingInt(Flag::id))
                    .toArray();
        }

        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v388(@Unsigned long runtimeEntityId, @AsShort @LE int flagmask, Flag.Codec388[] flags) implements MoveEntityDeltaPacket {
        public static final Interpreter<v388> INTERPRETER = Interpreter.generate(v388.class);
        public static final Deferer<v388> DEFERER = Deferer.generate(v388.class);

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v419(@Unsigned long runtimeEntityId, @AsShort @LE int flagmask, Flag.Codec419[] flags) implements MoveEntityDeltaPacket {
        public static final Interpreter<v419> INTERPRETER = Interpreter.generate(v419.class);
        public static final Deferer<v419> DEFERER = Deferer.generate(v419.class);

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
