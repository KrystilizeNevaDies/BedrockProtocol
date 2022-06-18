package com.nukkitx.protocol.bedrock.data.inventory;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface MaterialReducer extends BitDataWritable, PacketDataWriter {
    int inputId();
    Int2IntMap itemCounts();

    record v465(int inputId, Int2IntMap itemCounts) implements MaterialReducer, BedrockPacket.Codec465 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, inputId());
            for (Int2IntMap.Entry entry : itemCounts().int2IntEntrySet()) {
                writeInt(output, entry.getIntKey());
                writeInt(output, entry.getIntValue());
            }
        }
    }
}
