package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface BlockPickRequestPacket extends BedrockPacket {
    @NotNull Vector3i blockPosition();

    boolean addUserData();

    byte hotbarSlot();

    record v291(@NotNull Vector3i blockPosition, boolean addUserData,
                byte hotbarSlot) implements BlockPickRequestPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                return new v291(readBlockPosition(input), readBoolean(input), readByte(input));
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeBlockPosition(output, blockPosition());
            writeBoolean(output, addUserData());
            writeByte(output, hotbarSlot());
        }
    }

}
