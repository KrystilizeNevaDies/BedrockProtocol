package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface CreatePhotoPacket extends BedrockPacket {
    long id();

    String photoName();

    String photoItemName();


    record v465(long id, String photoName, String photoItemName) implements CreatePhotoPacket {
        public static final Interpreter<v465> INTERPRETER = new Interpreter<v465>() {
            @Override
            public @NotNull v465 interpret(@NotNull BitInput input) throws IOException {
                long id = readLongLE(input);
                String photoName = readString(input);
                String photoItemName = readString(input);
                return new v465(id, photoName, photoItemName);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLongLE(output, id());
            writeString(output, photoName());
            writeString(output, photoItemName());
        }
    }

}
