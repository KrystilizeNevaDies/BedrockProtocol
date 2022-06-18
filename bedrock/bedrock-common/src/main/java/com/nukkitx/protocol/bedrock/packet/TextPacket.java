package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public interface TextPacket extends BedrockPacket {
    Type valueType;
    boolean needsTranslation;
    String sourceName;
    String message;
    List<String> parameters = new ObjectArrayList<>();
    String xuid;
    String platformChatId = "";


    public enum Type {
        RAW,
        CHAT,
        TRANSLATION,
        POPUP,
        JUKEBOX_POPUP,
        TIP,
        SYSTEM,
        WHISPER,
        ANNOUNCEMENT,
        OBJECT,
        OBJECT_WHISPER
    }

    record v291 implements TextPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TextPacket packet) {
            TextPacket.Type type = packet.getType();
            buffer.writeByte(type.ordinal());
            buffer.writeBoolean(packet.isNeedsTranslation());

            switch (type) {
                case CHAT:
                case WHISPER:
                case ANNOUNCEMENT:
                    helper.writeString(buffer, packet.getSourceName());
                case RAW:
                case TIP:
                case SYSTEM:
                    helper.writeString(buffer, packet.getMessage());
                    break;
                case TRANSLATION:
                case POPUP:
                case JUKEBOX_POPUP:
                    helper.writeString(buffer, packet.getMessage());
                    helper.writeArray(buffer, packet.getParameters(), helper::writeString);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported TextType " + type);
            }

            helper.writeString(buffer, packet.getXuid());
            helper.writeString(buffer, packet.getPlatformChatId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TextPacket packet) {
            TextPacket.Type type = TextPacket.Type.values()[buffer.readUnsignedByte()];
            packet.setType(type);
            packet.setNeedsTranslation(buffer.readBoolean());

            switch (type) {
                case CHAT:
                case WHISPER:
                case ANNOUNCEMENT:
                    packet.setSourceName(helper.readString(buffer));
                case RAW:
                case TIP:
                case SYSTEM:
                    packet.setMessage(helper.readString(buffer));
                    break;
                case TRANSLATION:
                case POPUP:
                case JUKEBOX_POPUP:
                    packet.setMessage(helper.readString(buffer));
                    helper.readArray(buffer, packet.getParameters(), helper::readString);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported TextType " + type);
            }

            packet.setXuid(helper.readString(buffer));
            packet.setPlatformChatId(helper.readString(buffer));
        }
    }

    record v332 implements TextPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TextPacket packet) {
            TextPacket.Type type = packet.getType();
            buffer.writeByte(type.ordinal());
            buffer.writeBoolean(packet.isNeedsTranslation());

            switch (type) {
                case CHAT:
                case WHISPER:
                case ANNOUNCEMENT:
                    helper.writeString(buffer, packet.getSourceName());
                case RAW:
                case TIP:
                case SYSTEM:
                case OBJECT:
                case OBJECT_WHISPER:
                    helper.writeString(buffer, packet.getMessage());
                    break;
                case TRANSLATION:
                case POPUP:
                case JUKEBOX_POPUP:
                    helper.writeString(buffer, packet.getMessage());
                    helper.writeArray(buffer, packet.getParameters(), helper::writeString);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported TextType " + type);
            }

            helper.writeString(buffer, packet.getXuid());
            helper.writeString(buffer, packet.getPlatformChatId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TextPacket packet) {
            TextPacket.Type type = TextPacket.Type.values()[buffer.readUnsignedByte()];
            packet.setType(type);
            packet.setNeedsTranslation(buffer.readBoolean());

            switch (type) {
                case CHAT:
                case WHISPER:
                case ANNOUNCEMENT:
                    packet.setSourceName(helper.readString(buffer));
                case RAW:
                case TIP:
                case SYSTEM:
                case OBJECT:
                case OBJECT_WHISPER:
                    packet.setMessage(helper.readString(buffer));
                    break;
                case TRANSLATION:
                case POPUP:
                case JUKEBOX_POPUP:
                    packet.setMessage(helper.readString(buffer));
                    helper.readArray(buffer, packet.getParameters(), helper::readString);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported TextType " + type);
            }

            packet.setXuid(helper.readString(buffer));
            packet.setPlatformChatId(helper.readString(buffer));
        }
    }
}
