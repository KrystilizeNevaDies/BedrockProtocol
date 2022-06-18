package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface UpdateTradePacket extends BedrockPacket {
    int containerId;
    ContainerType containerType;
    int size; // Hardcoded to 0
    int tradeTier;
    long traderUniqueEntityId;
    long playerUniqueEntityId;
    String displayName;
    NbtMap offers;
    boolean newTradingUi;
    boolean recipeAddedOnUpdate;
    boolean usingEconomyTrade;


    record v291 implements UpdateTradePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
            buffer.writeByte(packet.getContainerId());
            buffer.writeByte(packet.getContainerType().getId());
            VarInts.writeInt(buffer, packet.getSize());
            VarInts.writeInt(buffer, packet.isUsingEconomyTrade() ? 40 : 0); // Merchant Timer
            buffer.writeBoolean(packet.isRecipeAddedOnUpdate());
            VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
            VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
            helper.writeString(buffer, packet.getDisplayName());
            helper.writeTag(buffer, packet.getOffers());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
            packet.setContainerId(buffer.readByte());
            packet.setContainerType(ContainerType.from(buffer.readByte()));
            packet.setSize(VarInts.readInt(buffer));
            packet.setUsingEconomyTrade(VarInts.readInt(buffer) >= 40);
            packet.setRecipeAddedOnUpdate(buffer.readBoolean());
            packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
            packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
            packet.setDisplayName(helper.readString(buffer));
            packet.setOffers(helper.readTag(buffer));
        }
    }

    record v313 implements UpdateTradePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
            buffer.writeByte(packet.getContainerId());
            buffer.writeByte(packet.getContainerType().getId());
            VarInts.writeInt(buffer, packet.getSize());
            VarInts.writeInt(buffer, packet.isNewTradingUi() ? 40 : 0);
            VarInts.writeInt(buffer, packet.getTradeTier());
            buffer.writeBoolean(packet.isRecipeAddedOnUpdate());
            VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
            VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
            helper.writeString(buffer, packet.getDisplayName());
            helper.writeTag(buffer, packet.getOffers());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
            packet.setContainerId(buffer.readByte());
            packet.setContainerType(ContainerType.from(buffer.readByte()));
            packet.setSize(VarInts.readInt(buffer));
            packet.setNewTradingUi(VarInts.readInt(buffer) >= 40);
            packet.setTradeTier(VarInts.readInt(buffer));
            packet.setRecipeAddedOnUpdate(buffer.readBoolean());
            packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
            packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
            packet.setDisplayName(helper.readString(buffer));
            packet.setOffers(helper.readTag(buffer));
        }
    }

    record v354 implements UpdateTradePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
            buffer.writeByte(packet.getContainerId());
            buffer.writeByte(packet.getContainerType().getId());
            VarInts.writeInt(buffer, packet.getSize());
            VarInts.writeInt(buffer, packet.getTradeTier());
            VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
            VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
            helper.writeString(buffer, packet.getDisplayName());
            buffer.writeBoolean(packet.isNewTradingUi());
            buffer.writeBoolean(packet.isUsingEconomyTrade());
            helper.writeTag(buffer, packet.getOffers());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
            packet.setContainerId(buffer.readByte());
            packet.setContainerType(ContainerType.from(buffer.readByte()));
            packet.setSize(VarInts.readInt(buffer));
            packet.setTradeTier(VarInts.readInt(buffer));
            packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
            packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
            packet.setDisplayName(helper.readString(buffer));
            packet.setNewTradingUi(buffer.readBoolean());
            packet.setUsingEconomyTrade(buffer.readBoolean());
            packet.setOffers(helper.readTag(buffer));
        }
    }

}
