package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.util.OptionalBoolean;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;

interface EducationSettingsPacket extends BedrockPacket {
    private String codeBuilderUri;
    private String codeBuilderTitle;
    private boolean canResizeCodeBuilder;
    /**
     * @since v465
     */
    private boolean disableLegacyTitle;
    /**
     * @since v465
     */
    private String postProcessFilter;
    /**
     * @since v465
     */
    private String screenshotBorderPath;
    private OptionalBoolean entityCapabilities;
    private Optional<String> overrideUri;
    private boolean quizAttached;
    private OptionalBoolean externalLinkSettings;


    public class EducationSettingsReader_v388 implements BedrockPacketReader<EducationSettingsPacket> {

        public static final EducationSettingsReader_v388 INSTANCE = new EducationSettingsReader_v388();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
            helper.writeString(buffer, packet.getCodeBuilderUri());
            buffer.writeBoolean(packet.isQuizAttached());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
            packet.setCodeBuilderUri(helper.readString(buffer));
            packet.setQuizAttached(buffer.readBoolean());
        }
    }

    public class EducationSettingsReader_v407 extends EducationSettingsReader_v388 {

        public static final EducationSettingsReader_v407 INSTANCE = new EducationSettingsReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
            helper.writeString(buffer, packet.getCodeBuilderUri());
            helper.writeString(buffer, packet.getCodeBuilderTitle());
            buffer.writeBoolean(packet.isCanResizeCodeBuilder());
            helper.writeOptional(buffer, Optional::isPresent, packet.getOverrideUri(),
                    (byteBuf, optional) -> helper.writeString(byteBuf, optional.get()));
            buffer.writeBoolean(packet.isQuizAttached());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
            packet.setCodeBuilderUri(helper.readString(buffer));
            packet.setCodeBuilderTitle(helper.readString(buffer));
            packet.setCanResizeCodeBuilder(buffer.readBoolean());
            packet.setOverrideUri(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(helper.readString(byteBuf))));
            packet.setQuizAttached(buffer.readBoolean());
        }
    }

    public class EducationSettingsReader_v465 extends EducationSettingsReader_v407 {

        public static final EducationSettingsReader_v465 INSTANCE = new EducationSettingsReader_v465();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
            helper.writeString(buffer, packet.getCodeBuilderUri());
            helper.writeString(buffer, packet.getCodeBuilderTitle());
            buffer.writeBoolean(packet.isCanResizeCodeBuilder());
            buffer.writeBoolean(packet.isDisableLegacyTitle());
            helper.writeString(buffer, packet.getPostProcessFilter());
            helper.writeString(buffer, packet.getScreenshotBorderPath());
            helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getEntityCapabilities(),
                    (byteBuf, optional) -> byteBuf.writeBoolean(optional.getAsBoolean()));
            helper.writeOptional(buffer, Optional::isPresent, packet.getOverrideUri(),
                    (byteBuf, optional) -> helper.writeString(byteBuf, optional.get()));
            buffer.writeBoolean(packet.isQuizAttached());
            helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getExternalLinkSettings(),
                    (byteBuf, optional) -> byteBuf.writeBoolean(optional.getAsBoolean()));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
            packet.setCodeBuilderUri(helper.readString(buffer));
            packet.setCodeBuilderTitle(helper.readString(buffer));
            packet.setCanResizeCodeBuilder(buffer.readBoolean());
            packet.setDisableLegacyTitle(buffer.readBoolean());
            packet.setPostProcessFilter(helper.readString(buffer));
            packet.setScreenshotBorderPath(helper.readString(buffer));
            packet.setEntityCapabilities(helper.readOptional(buffer, OptionalBoolean.empty(),
                    byteBuf -> OptionalBoolean.of(buffer.readBoolean())));
            packet.setOverrideUri(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(helper.readString(byteBuf))));
            packet.setQuizAttached(buffer.readBoolean());
            packet.setExternalLinkSettings(helper.readOptional(buffer, OptionalBoolean.empty(),
                    byteBuf -> OptionalBoolean.of(buffer.readBoolean())));
        }
    }



}
