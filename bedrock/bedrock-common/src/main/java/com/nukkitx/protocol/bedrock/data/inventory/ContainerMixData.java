package com.nukkitx.protocol.bedrock.data.inventory;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ContainerMixData extends BitDataWritable, PacketDataWriter {
    int inputId();
    int reagentId();
    int outputId();


    record v388(int inputId, int reagentId, int outputId) implements ContainerMixData, BedrockPacket.Codec388 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, inputId());
            writeInt(output, reagentId());
            writeInt(output, outputId());
        }
    }
}
