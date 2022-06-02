package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PlayerArmorDamageFlag;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.EnumSet;
import java.util.Set;

interface PlayerArmorDamagePacket extends BedrockPacket {
    private final Set<PlayerArmorDamageFlag> flags = EnumSet.noneOf(PlayerArmorDamageFlag.class);
    private final int[] damage = new int[4];


    @Overrid

    public class PlayerArmorDamageReader_v407 implements BedrockPacketReader<PlayerArmorDamagePacket> {
        public static final PlayerArmorDamageReader_v407 INSTANCE = new PlayerArmorDamageReader_v407();

        private static final PlayerArmorDamageFlag[] FLAGS = PlayerArmorDamageFlag.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerArmorDamagePacket packet) {
            int flags = 0;
            for (PlayerArmorDamageFlag flag : packet.getFlags()) {
                flags |= 1 << flag.ordinal();
            }
            buffer.writeByte(flags);

            int[] damage = packet.getDamage();

            for (PlayerArmorDamageFlag flag : packet.getFlags()) {
                int value = damage[flag.ordinal()];
                VarInts.writeInt(buffer, value);
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerArmorDamagePacket packet) {
            int flagsVal = buffer.readUnsignedByte();
            Set<PlayerArmorDamageFlag> flags = packet.getFlags();
            int[] damage = packet.getDamage();
            for (int i = 0; i < 4; i++) {
                if ((flagsVal & (1 << i)) != 0) {
                    flags.add(FLAGS[i]);
                    damage[i] = VarInts.readInt(buffer);
                }
            }
        }
    }
}
