package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

interface PlayerArmorDamagePacket extends BedrockPacket {
//    final Set<PlayerArmorDamageFlag> flags = EnumSet.noneOf(PlayerArmorDamageFlag.class);
//    final int[] damage = new int[4];


    record v407(@AsByte int flags, int[] damages) implements PlayerArmorDamagePacket {
        public v407(Map<PlayerArmorDamageFlag, Integer> damages) {
            this(flags(damages.keySet()), damages(damages));
        }

        private static int flags(Collection<PlayerArmorDamageFlag> flags) {
            int flagBits = 0;
            for (PlayerArmorDamageFlag flag : flags) {
                flagBits |= flag.ordinal();
            }
            return flagBits;
        }

        private static int[] damages(Map<PlayerArmorDamageFlag, Integer> damages) {
            int[] damage = new int[4];
            for (PlayerArmorDamageFlag flag : PlayerArmorDamageFlag.values()) {
                damage[flag.ordinal()] = damages.getOrDefault(flag, 0);
            }
            return damage;
        }


        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, (byte) flags());
            for (int i = 0; i < 4; i++) {
                writeInt(output, damages()[i]);
            }
        }
    }
}
