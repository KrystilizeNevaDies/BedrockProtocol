package com.nukkitx.protocol.bedrock.data.command;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public record CommandOriginData(CommandOriginType origin, UUID uuid, String requestId,
                                long event) implements BitDataWritable, PacketDataWriter {
    public static final BedrockPacket.Interpreter<CommandOriginData> INTERPRETER = new BedrockPacket.Interpreter<CommandOriginData>() {
        @Override
        public @NotNull CommandOriginData interpret(@NotNull BitInput input) throws IOException {
            // TODO: CommandOriginData.INTERPRETER
            return null;
        }
    };

    public void write(@NotNull BitOutput output) {
        // TODO: CommandOriginData#write
    }
}
