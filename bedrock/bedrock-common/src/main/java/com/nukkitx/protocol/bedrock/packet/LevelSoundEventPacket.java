package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface LevelSoundEventPacket extends BedrockPacket {

    SoundEvent sound();
    Vector3f position();
    int data();
    String identifier();
    boolean isBabySound();
    boolean isRelativeVolumeDisabled();

    record v332(SoundEvent sound, Vector3f position, int data, String identifier, boolean isBabySound,
                boolean isRelativeVolumeDisabled) implements LevelSoundEventPacket {
        public static final Interpreter<v332> INTERPRETER = Interpreter.generate(v332.class);
        private static final Deferer<v332> DEFERER = Deferer.generate(v332.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v407(SoundEvent sound, Vector3f position, int data, String identifier, boolean isBabySound,
                boolean isRelativeVolumeDisabled) implements LevelSoundEventPacket {
        public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
        private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
