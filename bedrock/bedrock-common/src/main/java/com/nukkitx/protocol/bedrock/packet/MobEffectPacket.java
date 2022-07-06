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

public interface MobEffectPacket extends BedrockPacket {
    long runtimeEntityId();
    Event event();
    int effectId();
    int amplifier();
    boolean isParticles();
    int duration();


    enum Event {
        NONE,
        ADD,
        MODIFY,
        REMOVE,
    }

    record v291(@Unsigned long runtimeEntityId, Event event, int effectId, int amplifier, boolean isParticles,
                int duration) implements MobEffectPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

}
