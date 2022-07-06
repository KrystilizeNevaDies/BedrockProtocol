package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface LevelSoundEvent2Packet extends BedrockPacket {
    SoundEvent sound();
    Vector3f position();
    int data();
    String identifier();
    boolean isBabySound();
    boolean isRelativeVolumeDisabled();


    record v313(SoundEvent sound, Vector3f position, int data, String identifier, boolean isBabySound,
                boolean isRelativeVolumeDisabled) implements LevelSoundEvent2Packet {
        public static final Interpreter<v313> INTERPRETER = Interpreter.generate(v313.class);
        private static final Deferer<v313> DEFERER = Deferer.generate(v313.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v407(@AsInt SoundEvent sound, Vector3f position, int data, String identifier, boolean isBabySound,
                boolean isRelativeVolumeDisabled) implements LevelSoundEvent2Packet {
        public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
        private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
