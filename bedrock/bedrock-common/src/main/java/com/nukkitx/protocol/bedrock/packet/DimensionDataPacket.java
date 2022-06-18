package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.definition.DimensionDefinition;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * Sends a list of the data-driven dimensions to the client.
 * This packet is sent before the {@link StartGamePacket} in the login sequence.
 *
 * <b>Note:</b> The client only supports sending the <code>minecraft:overworld</code> dimension as of 1.18.30
 *
 * @since v503
 */
interface DimensionDataPacket extends BedrockPacket {
    DimensionDefinition[] definitions();


    record v503(DimensionDefinition[] definitions) implements DimensionDataPacket {
        public static final Interpreter<v503> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v503 interpret(@NotNull BitInput input) throws IOException {
                DimensionDefinition[] definitions = readArray(input, DimensionDefinition.INTERPRETER);
                return new v503(definitions);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, definitions());
        }
    }
}