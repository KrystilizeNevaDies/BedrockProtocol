package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ContainerSetDataPacket extends BedrockPacket {

    int FURNACE_TICK_COUNT = 0;
    int FURNACE_LIT_TIME = 1;
    int FURNACE_LIT_DURATION = 2;
    int FURNACE_STORED_XP = 3;
    int FURNACE_FUEL_AUX = 4;

    int BREWING_STAND_BREW_TIME = 0;
    int BREWING_STAND_FUEL_AMOUNT = 1;
    int BREWING_STAND_FUEL_TOTAL = 2;

    byte windowId();

    int property();

    int value();


    record v291(byte windowId, int property, int value) implements ContainerSetDataPacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                byte windowId = readByte(input);
                int property = readInt(input);
                int value = readInt(input);
                return new v291(windowId, property, value);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, windowId());
            writeInt(output, property());
            writeInt(output, value());
        }
    }
}
