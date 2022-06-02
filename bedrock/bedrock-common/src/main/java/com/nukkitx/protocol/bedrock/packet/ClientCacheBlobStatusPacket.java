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
import java.util.function.LongConsumer;

interface ClientCacheBlobStatusPacket extends BedrockPacket {

    long[] naks();
    long[] arks();

    record v361(long[] arks, long[] naks) implements ClientCacheBlobStatusPacket {
        public static final Interpreter<v361> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v361 interpret(@NotNull BitInput input) throws IOException {
                int naksLength = readUnsignedInt(input);
                int acksLength = readUnsignedInt(input);

                long[] naks = new long[naksLength];
                long[] acks = new long[acksLength];

                for (int i = 0; i < naksLength; i++) naks[i] = readLong(input);
                for (int i = 0; i < acksLength; i++) acks[i] = readLong(input);

                return new v361(acks, naks);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            long[] naks = naks();
            long[] acks = arks();

            writeUnsignedInt(output, naks.length);
            writeUnsignedInt(output, acks.length);

            for (long nak : naks) writeLongLE(output, nak);
            for (long ack : acks) writeLongLE(output, ack);
        }
    }

}
