package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SetDifficultyPacket extends BedrockPacket {
    private int difficulty;


    public class SetDifficultyReader_v291 implements BedrockPacketReader<SetDifficultyPacket> {
        public static final SetDifficultyReader_v291 INSTANCE = new SetDifficultyReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetDifficultyPacket packet) {
            VarInts.writeUnsignedInt(buffer, packet.getDifficulty());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetDifficultyPacket packet) {
            packet.setDifficulty(VarInts.readUnsignedInt(buffer));
        }
    }

}
