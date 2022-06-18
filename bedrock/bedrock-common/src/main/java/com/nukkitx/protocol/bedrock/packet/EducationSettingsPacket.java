package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.util.OptionalBoolean;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Optional;

interface EducationSettingsPacket extends BedrockPacket {
//    String codeBuilderUri();
//    String codeBuilderTitle();
//    boolean canResizeCodeBuilder();
//    /**
//     * @since v465
//     */
//    boolean disableLegacyTitle();
//    /**
//     * @since v465
//     */
//    String postProcessFilter();
//    /**
//     * @since v465
//     */
//    String screenshotBorderPath();
//    OptionalBoolean entityCapabilities();
//    Optional<String> overrideUri();
//    boolean quizAttached();
//    OptionalBoolean externalLinkSettings();


    record v388(String codeBuilderUri, boolean quizAttached) implements EducationSettingsPacket {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, codeBuilderUri());
            writeBoolean(output, quizAttached());
        }

        public static final Interpreter<v388> INTERPRETER = new Interpreter<v388>() {
            @Override
            public @NotNull v388 interpret(@NotNull BitInput input) throws IOException {
                String codeBuilderUri = readString(input);
                boolean quizAttached = readBoolean(input);
                return new v388(codeBuilderUri, quizAttached);
            }
        };
    }

    // TODO: Decide what to do with Nullable values
    record v407(String codeBuilderUri, String codeBuilderTitle, boolean canResizeCodeBuilder,
                @Nullable String overrideUri, boolean quizAttached) implements EducationSettingsPacket {

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, codeBuilderUri());
            writeString(output, codeBuilderTitle());
            writeBoolean(output, canResizeCodeBuilder());
            if (overrideUri() == null) {
                writeBoolean(output, false);
            } else {
                writeBoolean(output, true);
                writeString(output, overrideUri());
            }
            writeBoolean(output, quizAttached());
        }

        public static final Interpreter<v407> INTERPRETER = new Interpreter<v407>() {
            @Override
            public @NotNull v407 interpret(@NotNull BitInput input) throws IOException {
                String codeBuilderUri = readString(input);
                String codeBuilderTitle = readString(input);
                boolean canResizeCodeBuilder = readBoolean(input);
                String overrideUriValue = readBoolean(input) ? readString(input) : null;
                boolean quizAttached = readBoolean(input);
                return new v407(codeBuilderUri, codeBuilderTitle, canResizeCodeBuilder, overrideUriValue, quizAttached);
            }
        };
    }

    record v465(String codeBuilderUri, String codeBuilderTitle, boolean canResizeCodeBuilder,
                boolean isDisableLegacyTitle, String postProcessFilter, String screenshotBorderPath,
                OptionalBoolean entityCapabilities, @Nullable String overrideUri, boolean quizAttached,
                OptionalBoolean externalLinkSettings) implements EducationSettingsPacket {

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeString(output, codeBuilderUri());
            writeString(output, codeBuilderTitle());
            writeBoolean(output, canResizeCodeBuilder());
            writeBoolean(output, isDisableLegacyTitle());
            writeString(output, postProcessFilter());
            writeString(output, screenshotBorderPath());
            if (entityCapabilities().isPresent()) {
                writeBoolean(output, true);
                writeBoolean(output, entityCapabilities().getAsBoolean());
            } else {
                writeBoolean(output, false);
            }
            if (overrideUri() == null) {
                writeBoolean(output, false);
            } else {
                writeBoolean(output, true);
                writeString(output, overrideUri());
            }
            writeBoolean(output, quizAttached());
            if (externalLinkSettings().isPresent()) {
                writeBoolean(output, true);
                writeBoolean(output, externalLinkSettings().getAsBoolean());
            } else {
                writeBoolean(output, false);
            }
        }

        public static final Interpreter<v465> INTERPRETER = new Interpreter<v465>() {
            @Override
            public @NotNull v465 interpret(@NotNull BitInput input) throws IOException {
                String codeBuilderUri = readString(input);
                String codeBuilderTitle = readString(input);
                boolean canResizeCodeBuilder = readBoolean(input);
                boolean isDisableLegacyTitle = readBoolean(input);
                String postProcessFilter = readString(input);
                String screenshotBorderPath = readString(input);
                OptionalBoolean entityCapabilities = readBoolean(input) ? OptionalBoolean.of(readBoolean(input)) : OptionalBoolean.empty();
                String overrideUriValue = readBoolean(input) ? readString(input) : null;
                boolean quizAttached = readBoolean(input);
                OptionalBoolean externalLinkSettings = readBoolean(input) ? OptionalBoolean.of(readBoolean(input)) : OptionalBoolean.empty();
                return new v465(codeBuilderUri, codeBuilderTitle, canResizeCodeBuilder, isDisableLegacyTitle, postProcessFilter, screenshotBorderPath, entityCapabilities, overrideUriValue, quizAttached, externalLinkSettings);
            }
        };
    }
}
