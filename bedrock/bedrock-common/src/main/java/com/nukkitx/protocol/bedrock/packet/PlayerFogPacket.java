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

/**
 * Tracks the current fog effects applied to a client
 */
interface PlayerFogPacket extends BedrockPacket {

    /**
     * Fog stack containing fog effects from the /fog command
     *
     * @param fogStack list of fog effects
     * @return list of fog effects
     */
    private final List<String> fogStack = new ObjectArrayList<>();


    @Overrid

    public class PlayerFogReader_v419 implements BedrockPacketReader<PlayerFogPacket> {

        public static final PlayerFogReader_v419 INSTANCE = new PlayerFogReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerFogPacket packet) {
            helper.writeArray(buffer, packet.getFogStack(), (buf, hlp, fogEffect) -> hlp.writeString(buf, fogEffect));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerFogPacket packet) {
            helper.readArray(buffer, packet.getFogStack(), (buf, hlp) -> hlp.readString(buf));
        }
    }

}
