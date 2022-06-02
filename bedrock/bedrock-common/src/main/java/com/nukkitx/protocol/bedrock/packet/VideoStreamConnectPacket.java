package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface VideoStreamConnectPacket extends BedrockPacket {
    private String address;
    private float screenshotFrequency;
    private Action action;
    private int width;
    private int height;


    public enum Action {
        OPEN,
        CLOSE
    }

    public class VideoStreamConnectReader_v340 implements BedrockPacketReader<VideoStreamConnectPacket> {
        public static final VideoStreamConnectReader_v340 INSTANCE = new VideoStreamConnectReader_v340();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, VideoStreamConnectPacket packet) {
            helper.writeString(buffer, packet.getAddress());
            buffer.writeFloatLE(packet.getScreenshotFrequency());
            buffer.writeByte(packet.getAction().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, VideoStreamConnectPacket packet) {
            packet.setAddress(helper.readString(buffer));
            packet.setScreenshotFrequency(buffer.readFloatLE());
            packet.setAction(VideoStreamConnectPacket.Action.values()[buffer.readUnsignedByte()]);
        }
    }

    public class VideoStreamConnectReader_v361 implements BedrockPacketReader<VideoStreamConnectPacket> {
        public static final VideoStreamConnectReader_v361 INSTANCE = new VideoStreamConnectReader_v361();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, VideoStreamConnectPacket packet) {
            helper.writeString(buffer, packet.getAddress());
            buffer.writeFloatLE(packet.getScreenshotFrequency());
            buffer.writeByte(packet.getAction().ordinal());
            buffer.writeIntLE(packet.getWidth());
            buffer.writeIntLE(packet.getHeight());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, VideoStreamConnectPacket packet) {
            packet.setAddress(helper.readString(buffer));
            packet.setScreenshotFrequency(buffer.readFloatLE());
            packet.setAction(VideoStreamConnectPacket.Action.values()[buffer.readUnsignedByte()]);
            packet.setWidth(buffer.readIntLE());
            packet.setHeight(buffer.readIntLE());
        }
    }


}
