package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface PlayStatusPacket extends BedrockPacket {
    private Status status;


    public enum Status {

        /**
         * Sent to confirm login success and move onto resource pack sequence
         */
        LOGIN_SUCCESS,

        /**
         * Displays outdated client disconnection screen
         */
        LOGIN_FAILED_CLIENT_OLD,

        /**
         * Displays outdated server disconnection screen
         */
        LOGIN_FAILED_SERVER_OLD,

        /**
         * Spawns player into the world
         */
        PLAYER_SPAWN,

        LOGIN_FAILED_INVALID_TENANT,

        /**
         * Sent when a Education Edition client joins an Bedrock server
         */
        LOGIN_FAILED_EDITION_MISMATCH_EDU_TO_VANILLA,

        /**
         * Sent when a Bedrock client joins an Education server
         */
        LOGIN_FAILED_EDITION_MISMATCH_VANILLA_TO_EDU,

        /**
         * Sent to a split screen player when the server is full
         */
        FAILED_SERVER_FULL_SUB_CLIENT
    }

    public class PlayStatusReader_v291 implements BedrockPacketReader<PlayStatusPacket> {
        public static final PlayStatusReader_v291 INSTANCE = new PlayStatusReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayStatusPacket packet) {
            buffer.writeInt(packet.getStatus().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayStatusPacket packet) {
            packet.setStatus(Status.values()[buffer.readInt()]);
        }
    }

}
