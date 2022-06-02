package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sends a toast notification to the client.
 *
 * @since v526
 */
interface ToastRequestPacket extends BedrockPacket {

    private String title;
    private String content;


    public class ToastRequestReaderBeta implements BedrockPacketReader<ToastRequestPacket> {
        public static final ToastRequestReaderBeta INSTANCE = new ToastRequestReaderBeta();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ToastRequestPacket packet) {
            helper.writeString(buffer, packet.getTitle());
            helper.writeString(buffer, packet.getContent());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ToastRequestPacket packet) {
            packet.setTitle(helper.readString(buffer));
            packet.setContent(helper.readString(buffer));
        }
    }

}
