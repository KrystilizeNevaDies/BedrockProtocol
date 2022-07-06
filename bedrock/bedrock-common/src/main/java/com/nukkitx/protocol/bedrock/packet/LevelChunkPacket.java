package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.serializer.BitData;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

interface LevelChunkPacket extends BedrockPacket {
//    int chunkX;
//    int chunkZ;
//    int subChunksLength;
//    boolean cachingEnabled;
//    /**
//     * @since v471
//     */
//    boolean requestSubChunks;
//    /**
//     * @since v485
//     */
//    int subChunkLimit;
//    final LongList blobIds = new LongArrayList();
//    byte[] data;


    record v291(int chunkX, int chunkZ, byte[] data) implements LevelChunkPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        private static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record Caching(@LE long[] blobId) implements BitDataWritable, PacketDataWriter {
        public static final Interpreter<Caching> INTERPRETER = Interpreter.generate(Caching.class);
        private static final Deferer<Caching> DEFERER = Deferer.generate(Caching.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v361(int chunkX, int chunkZ, @Unsigned int subChunksLength, @Nullable Caching caching,
                byte[] data) implements LevelChunkPacket {

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, chunkX());
            writeInt(output, chunkZ());
            writeUnsignedInt(output, subChunksLength());
            if (caching() != null) {
                writeBoolean(output, true);
                caching().write(output);
            } else {
                writeBoolean(output, false);
            }
            writeByteArray(output, data());
        }
    }

    record v486(int chunkX, int chunkZ, Action action, @Nullable Caching caching,
                byte[] data) implements LevelChunkPacket {

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, chunkX());
            writeInt(output, chunkZ());
            action().write(output);
            if (caching() != null) {
                writeBoolean(output, true);
                caching().write(output);
            } else {
                writeBoolean(output, false);
            }
            writeByteArray(output, data());
        }

        interface Action extends BitDataWritable, PacketDataWriter {
            record RequestSubChunks(@LE short subChunkLimit) implements Action {
                @Override
                public void write(@NotNull BitOutput output) throws IOException {
                    if (subChunkLimit < 0) {
                        writeUnsignedInt(output, -1);
                    } else {
                        writeUnsignedInt(output, -2);
                        writeShortLE(output, subChunkLimit);
                    }
                }
            }

            record SubChunkLength(int subChunkLength) implements Action {
                @Override
                public void write(@NotNull BitOutput output) throws IOException {
                    writeUnsignedInt(output, subChunkLength);
                }
            }
        }
    }


}
