package com.nukkitx.protocol.bedrock.data.inventory;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataReader;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Represents a potion mixing recipe which may be used in a brewing stand.
 */
public interface PotionMixData extends BitDataWritable, PacketDataWriter {
    // Potion to be put in
    int inputId();
//    int inputMeta();

    // Item to be added to the brewing stand to brew the output potion
    int reagentId();
//    int reagentMeta();

    // Output Potion
    int outputId();
//    int outputMeta();

    record v388(int inputId, int reagentId, int outputId) implements PotionMixData, BedrockPacket.Codec388 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, inputId());
            writeInt(output, reagentId());
            writeInt(output, outputId());
        }
    }
}
