package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.util.TriConsumer;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

public interface MoveEntityDeltaPacket extends BedrockPacket {
    long runtimeEntityId;

    final Set<Flag> flags = EnumSet.noneOf(Flag.class);

    int deltaX;
    int deltaY;
    int deltaZ;

    float x;
    float y;
    float z;

    float pitch;
    float yaw;
    float headYaw;


    public String toString() {
        return "MoveEntityDeltaPacket(runtimeEntityId=" + runtimeEntityId +
                ", flags=" + flags + ", delta=(" + deltaX + ", " + deltaY + ", " + deltaZ +
                "), position=(" + x + ", " + y + ", " + z +
                "), rotation=(" + pitch + ", " + yaw + ", " + headYaw + "))";
    }

    public enum Flag {
        HAS_X,
        HAS_Y,
        HAS_Z,
        HAS_PITCH,
        HAS_YAW,
        HAS_HEAD_YAW,
        ON_GROUND,
        TELEPORTING,
        FORCE_MOVE_LOCAL_ENTITY
    }

    record v291 implements MoveEntityDeltaPacket {
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_DELTA_X =
                (buffer, helper, packet) -> packet.setDeltaX(VarInts.readInt(buffer));
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_DELTA_Y =
                (buffer, helper, packet) -> packet.setDeltaY(VarInts.readInt(buffer));
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_DELTA_Z =
                (buffer, helper, packet) -> packet.setDeltaZ(VarInts.readInt(buffer));

        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_PITCH =
                (buffer, helper, packet) -> packet.setPitch(helper.readByteAngle(buffer));
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_YAW =
                (buffer, helper, packet) -> packet.setYaw(helper.readByteAngle(buffer));
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_HEAD_YAW =
                (buffer, helper, packet) -> packet.setHeadYaw(helper.readByteAngle(buffer));

        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_DELTA_X =
                (buffer, helper, packet) -> VarInts.writeInt(buffer, packet.getDeltaX());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_DELTA_Y =
                (buffer, helper, packet) -> VarInts.writeInt(buffer, packet.getDeltaY());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_DELTA_Z =
                (buffer, helper, packet) -> VarInts.writeInt(buffer, packet.getDeltaZ());

        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_PITCH =
                (buffer, helper, packet) -> helper.writeByteAngle(buffer, packet.getPitch());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_YAW =
                (buffer, helper, packet) -> helper.writeByteAngle(buffer, packet.getYaw());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_HEAD_YAW =
                (buffer, helper, packet) -> helper.writeByteAngle(buffer, packet.getHeadYaw());

        protected static final MoveEntityDeltaPacket.Flag[] FLAGS = MoveEntityDeltaPacket.Flag.values();


        protected final EnumMap<Flag, TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket>> readers = new EnumMap<>(MoveEntityDeltaPacket.Flag.class);
        protected final EnumMap<MoveEntityDeltaPacket.Flag, TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket>> writers = new EnumMap<>(MoveEntityDeltaPacket.Flag.class);

        protected MoveEntityDeltaReader_v291() {
            this.readers.put(MoveEntityDeltaPacket.Flag.HAS_X, READER_DELTA_X);
            this.readers.put(MoveEntityDeltaPacket.Flag.HAS_Y, READER_DELTA_Y);
            this.readers.put(MoveEntityDeltaPacket.Flag.HAS_Z, READER_DELTA_Z);
            this.readers.put(MoveEntityDeltaPacket.Flag.HAS_PITCH, READER_PITCH);
            this.readers.put(MoveEntityDeltaPacket.Flag.HAS_YAW, READER_YAW);
            this.readers.put(MoveEntityDeltaPacket.Flag.HAS_HEAD_YAW, READER_HEAD_YAW);

            this.writers.put(MoveEntityDeltaPacket.Flag.HAS_X, WRITER_DELTA_X);
            this.writers.put(MoveEntityDeltaPacket.Flag.HAS_Y, WRITER_DELTA_Y);
            this.writers.put(MoveEntityDeltaPacket.Flag.HAS_Z, WRITER_DELTA_Z);
            this.writers.put(MoveEntityDeltaPacket.Flag.HAS_PITCH, WRITER_PITCH);
            this.writers.put(MoveEntityDeltaPacket.Flag.HAS_YAW, WRITER_YAW);
            this.writers.put(MoveEntityDeltaPacket.Flag.HAS_HEAD_YAW, WRITER_HEAD_YAW);
        }

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityDeltaPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

            int flagsIndex = buffer.writerIndex();
            buffer.writeByte(0); // flags

            int flags = 0;
            for (MoveEntityDeltaPacket.Flag flag : packet.getFlags()) {
                flags |= 1 << flag.ordinal();

                TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> writer = this.writers.get(flag);
                if (writer != null) {
                    writer.accept(buffer, helper, packet);
                }
            }

            // Go back to flags and set them
            int currentIndex = buffer.writerIndex();
            buffer.writerIndex(flagsIndex);
            buffer.writeByte(flags);
            buffer.writerIndex(currentIndex);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityDeltaPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            int flags = buffer.readUnsignedByte();
            Set<MoveEntityDeltaPacket.Flag> flagSet = packet.getFlags();

            for (MoveEntityDeltaPacket.Flag flag : FLAGS) {
                if ((flags & (1 << flag.ordinal())) != 0) {
                    flagSet.add(flag);
                    TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> reader = this.readers.get(flag);
                    if (reader != null) {
                        reader.accept(buffer, helper, packet);
                    }
                }
            }
        }
    }

    record v388 extends MoveEntityDeltaReader_v291 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityDeltaPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

            int flagsIndex = buffer.writerIndex();
            buffer.writeShortLE(0); // flags

            int flags = 0;
            for (Flag flag : packet.getFlags()) {
                flags |= 1 << flag.ordinal();

                TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> writer = this.writers.get(flag);
                if (writer != null) {
                    writer.accept(buffer, helper, packet);
                }
            }

            // Go back to flags and set them
            int currentIndex = buffer.writerIndex();
            buffer.writerIndex(flagsIndex);
            buffer.writeShortLE(flags);
            buffer.writerIndex(currentIndex);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityDeltaPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            int flags = buffer.readUnsignedShortLE();
            Set<Flag> flagSet = packet.getFlags();

            for (Flag flag : FLAGS) {
                if ((flags & (1 << flag.ordinal())) != 0) {
                    flagSet.add(flag);
                    TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> reader = this.readers.get(flag);
                    if (reader != null) {
                        reader.accept(buffer, helper, packet);
                    }
                }
            }
        }
    }

    record v419 extends MoveEntityDeltaReader_v388 {

        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_X =
                (buffer, helper, packet) -> packet.setX(buffer.readFloatLE());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_Y =
                (buffer, helper, packet) -> packet.setY(buffer.readFloatLE());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> READER_Z =
                (buffer, helper, packet) -> packet.setZ(buffer.readFloatLE());

        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_X =
                (buffer, helper, packet) -> buffer.writeFloatLE(packet.getX());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_Y =
                (buffer, helper, packet) -> buffer.writeFloatLE(packet.getY());
        protected static final TriConsumer<ByteBuf, BedrockPacketHelper, MoveEntityDeltaPacket> WRITER_Z =
                (buffer, helper, packet) -> buffer.writeFloatLE(packet.getZ());


        protected MoveEntityDeltaReader_v419() {
            super();

            this.readers.put(Flag.HAS_X, READER_X);
            this.readers.put(Flag.HAS_Y, READER_Y);
            this.readers.put(Flag.HAS_Z, READER_Z);

            this.writers.put(Flag.HAS_X, WRITER_X);
            this.writers.put(Flag.HAS_Y, WRITER_Y);
            this.writers.put(Flag.HAS_Z, WRITER_Z);
        }
    }

}
