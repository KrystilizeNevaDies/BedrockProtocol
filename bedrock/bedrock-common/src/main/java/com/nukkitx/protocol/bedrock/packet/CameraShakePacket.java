package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.CameraShakeAction;
import com.nukkitx.protocol.bedrock.data.CameraShakeType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Causes the client's camera view to shake with a specified intensity and duration.
 * <p>
 * No known uses yet.
 */
interface CameraShakePacket extends BedrockPacket {

    /**
     * Intensity to shake the player's camera view.
     *
     * @return shake intensity
     */
    float intensity();

    /**
     * Amount of time to shake the player's camera.
     *
     * @return seconds to shake
     */
    float duration();

    @NotNull CameraShakeType shakeType();

    interface HasAction {
        @NotNull CameraShakeAction shakeAction();
    }

    record v419(float intensity, float duration, @NotNull CameraShakeType shakeType) implements CameraShakePacket {
        public static final Interpreter<v419> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v419 interpret(@NotNull BitInput input) throws IOException {
                float intensity = readFloatLE(input);
                float duration = readFloatLE(input);
                CameraShakeType shakeType = CameraShakeType.values()[readByte(input)];
                return new v419(intensity, duration, shakeType);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeFloatLE(output, intensity());
            writeFloatLE(output, duration());
            writeByte(output, (byte) shakeType().ordinal());
        }
    }

    record v428(float intensity, float duration, @NotNull CameraShakeType shakeType,
                @NotNull CameraShakeAction shakeAction) implements CameraShakePacket, HasAction {
        public static final Interpreter<v428> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v428 interpret(@NotNull BitInput input) throws IOException {
                float intensity = readFloatLE(input);
                float duration = readFloatLE(input);
                CameraShakeType shakeType = CameraShakeType.values()[readByte(input)];
                CameraShakeAction shakeAction = CameraShakeAction.values()[readByte(input)];
                return new v428(intensity, duration, shakeType, shakeAction);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeFloatLE(output, intensity());
            writeFloatLE(output, duration());
            writeByte(output, (byte) shakeType().ordinal());
            writeByte(output, (byte) shakeAction().ordinal());
        }
    }
}
