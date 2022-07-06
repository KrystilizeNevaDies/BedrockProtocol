package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
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

public interface HurtArmorPacket extends BedrockPacket {
    int damage();
//    int cause();
    /**
     * @since v465
     */
//    long armorSlots;


    record v291(int damage) implements HurtArmorPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        private static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v407(int cause, int damage) implements HurtArmorPacket {
        public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
        private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v465(int cause, int damage, @Unsigned long armorSlots) implements HurtArmorPacket {
        public static final Interpreter<v465> INTERPRETER = Interpreter.generate(v465.class);
        private static final Deferer<v465> DEFERER = Deferer.generate(v465.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
