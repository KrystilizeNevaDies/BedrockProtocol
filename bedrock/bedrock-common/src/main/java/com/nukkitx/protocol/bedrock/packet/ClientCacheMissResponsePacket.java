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
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;

interface ClientCacheMissResponsePacket extends BedrockPacket {
    @NotNull Long2ObjectMap<byte[]> blobs();

    record v361(@NotNull Long2ObjectMap<byte[]> blobs) implements ClientCacheMissResponsePacket, Codec361 {
        public static final Interpreter<v361> INTERPRETER = new Interpreter<v361>() {
            @Override
            public @NotNull v361 interpret(@NotNull BitInput input) throws IOException {
                int size = readUnsignedInt(input);
                Long2ObjectMap<byte[]> blobs = new Long2ObjectOpenHashMap<>(size);
                for (int i = 0; i < size; i++) {
                    long key = readLongLE(input);
                    int blobLength = readUnsignedInt(input);
                    byte[] blob = new byte[blobLength];
                    for (int j = 0; j < blobLength; j++) {
                        blob[j] = readByte(input);
                    }
                    blobs.put(key, blob);
                }
                return new v361(blobs);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            Long2ObjectMap<byte[]> blobs = blobs();

            writeUnsignedInt(output, blobs.size());
            for (Long2ObjectMap.Entry<byte[]> entry : blobs.long2ObjectEntrySet()) {
                writeLongLE(output, entry.getLongKey());
                byte[] blob = entry.getValue();
                writeUnsignedInt(output, blob.length);
                for (byte b : blob) {
                    writeByte(output, b);
                }
            }
        }
    }
}
