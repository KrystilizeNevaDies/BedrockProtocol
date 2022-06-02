package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.inventory.EnchantData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.EnchantOptionData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

interface PlayerEnchantOptionsPacket extends BedrockPacket {
    private final List<EnchantOptionData> options = new ArrayList<>();


    @Overrid

    public class PlayerEnchantOptionsReader_v407 implements BedrockPacketReader<PlayerEnchantOptionsPacket> {

        public static final PlayerEnchantOptionsReader_v407 INSTANCE = new PlayerEnchantOptionsReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerEnchantOptionsPacket packet) {
            helper.writeArray(buffer, packet.getOptions(), this::writeOption);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerEnchantOptionsPacket packet) {
            helper.readArray(buffer, packet.getOptions(), this::readOption);
        }

        protected void writeOption(ByteBuf buffer, BedrockPacketHelper helper, EnchantOptionData option) {
            VarInts.writeUnsignedInt(buffer, option.getCost());
            buffer.writeIntLE(option.getPrimarySlot());
            helper.writeArray(buffer, option.getEnchants0(), this::serializeEnchant);
            helper.writeArray(buffer, option.getEnchants1(), this::serializeEnchant);
            helper.writeArray(buffer, option.getEnchants2(), this::serializeEnchant);
            helper.writeString(buffer, option.getEnchantName());
            VarInts.writeUnsignedInt(buffer, option.getEnchantNetId());
        }

        protected EnchantOptionData readOption(ByteBuf buffer, BedrockPacketHelper helper) {
            int cost = VarInts.readUnsignedInt(buffer);
            int primarySlot = buffer.readIntLE();
            List<EnchantData> enchants1 = new ObjectArrayList<>();
            helper.readArray(buffer, enchants1, this::deserializeEnchant);
            List<EnchantData> enchants2 = new ObjectArrayList<>();
            helper.readArray(buffer, enchants2, this::deserializeEnchant);
            List<EnchantData> enchants3 = new ObjectArrayList<>();
            helper.readArray(buffer, enchants3, this::deserializeEnchant);
            String enchantName = helper.readString(buffer);
            int enchantNetId = VarInts.readUnsignedInt(buffer);
            return new EnchantOptionData(cost, primarySlot, enchants1, enchants2, enchants3, enchantName, enchantNetId);
        }

        protected void serializeEnchant(ByteBuf buffer, EnchantData enchant) {
            buffer.writeByte(enchant.getType());
            buffer.writeByte(enchant.getLevel());
        }

        protected EnchantData deserializeEnchant(ByteBuf buffer) {
            int type = buffer.readUnsignedByte();
            int level = buffer.readUnsignedByte();
            return new EnchantData(type, level);
        }
    }

}
