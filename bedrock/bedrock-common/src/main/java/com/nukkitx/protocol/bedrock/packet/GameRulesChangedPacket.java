package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public interface GameRulesChangedPacket extends BedrockPacket {
    private final List<GameRuleData<?>> gameRules = new ObjectArrayList<>();


    public class GameRulesChangedReader_v291 implements BedrockPacketReader<GameRulesChangedPacket> {
        public static final GameRulesChangedReader_v291 INSTANCE = new GameRulesChangedReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GameRulesChangedPacket packet) {
            helper.writeArray(buffer, packet.getGameRules(), helper::writeGameRule);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GameRulesChangedPacket packet) {
            helper.readArray(buffer, packet.getGameRules(), helper::readGameRule);
        }
    }

}
