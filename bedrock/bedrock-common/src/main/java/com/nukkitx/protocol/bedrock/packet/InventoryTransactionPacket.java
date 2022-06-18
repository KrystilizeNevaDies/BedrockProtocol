package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.LegacySetItemSlotData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public interface InventoryTransactionPacket extends BedrockPacket {
    int legacyRequestId;
    final List<LegacySetItemSlotData> legacySlots = new ObjectArrayList<>();
    final List<InventoryActionData> actions = new ObjectArrayList<>();
    TransactionType transactionType;
    int actionType;
    long runtimeEntityId;
    Vector3i blockPosition;
    int blockFace;
    int hotbarSlot;
    ItemData itemInHand;
    Vector3f playerPosition;
    Vector3f clickPosition;
    Vector3f headPosition;
    /**
     * @since v407
     * @deprecated v431
     */
    boolean usingNetIds;
    /**
     * Runtime ID of block being picked.
     * ItemUseInventoryTransaction only
     *
     * @param blockRuntimeId runtime ID of block
     * @return runtime ID of block
     */
    int blockRuntimeId;


    record v291 implements InventoryTransactionPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket
                packet, BedrockSession session) {
            TransactionType transactionType = packet.getTransactionType();
            VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

            helper.writeInventoryActions(buffer, session, packet.getActions(), false);

            switch (transactionType) {
                case ITEM_USE:
                    helper.writeItemUse(buffer, packet, session);
                    break;
                case ITEM_USE_ON_ENTITY:
                    this.writeItemUseOnEntity(buffer, helper, packet, session);
                    break;
                case ITEM_RELEASE:
                    this.writeItemRelease(buffer, helper, packet, session);
                    break;
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket
                packet, BedrockSession session) {
            TransactionType transactionType = TransactionType.values()[VarInts.readUnsignedInt(buffer)];
            packet.setTransactionType(transactionType);

            helper.readInventoryActions(buffer, session, packet.getActions());

            switch (transactionType) {
                case ITEM_USE:
                    helper.readItemUse(buffer, packet, session);
                    break;
                case ITEM_USE_ON_ENTITY:
                    this.readItemUseOnEntity(buffer, helper, packet, session);
                    break;
                case ITEM_RELEASE:
                    this.readItemRelease(buffer, helper, packet, session);
                    break;
            }
        }

        public void readItemUseOnEntity(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket
                packet, BedrockSession session) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setActionType(VarInts.readUnsignedInt(buffer));
            packet.setHotbarSlot(VarInts.readInt(buffer));
            packet.setItemInHand(helper.readItem(buffer, session));
            packet.setPlayerPosition(helper.readVector3f(buffer));
            packet.setClickPosition(helper.readVector3f(buffer));
        }

        public void writeItemUseOnEntity(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket
                packet, BedrockSession session) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeUnsignedInt(buffer, packet.getActionType());
            VarInts.writeInt(buffer, packet.getHotbarSlot());
            helper.writeItem(buffer, packet.getItemInHand(), session);
            helper.writeVector3f(buffer, packet.getPlayerPosition());
            helper.writeVector3f(buffer, packet.getClickPosition());
        }

        public void readItemRelease(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket
                packet, BedrockSession session) {
            packet.setActionType(VarInts.readUnsignedInt(buffer));
            packet.setHotbarSlot(VarInts.readInt(buffer));
            packet.setItemInHand(helper.readItem(buffer, session));
            packet.setHeadPosition(helper.readVector3f(buffer));
        }

        public void writeItemRelease(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket
                packet, BedrockSession session) {
            VarInts.writeUnsignedInt(buffer, packet.getActionType());
            VarInts.writeInt(buffer, packet.getHotbarSlot());
            helper.writeItem(buffer, packet.getItemInHand(), session);
            helper.writeVector3f(buffer, packet.getHeadPosition());
        }
    }

    record v407 extends InventoryTransactionReader_v291 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
            int legacyRequestId = packet.getLegacyRequestId();
            VarInts.writeInt(buffer, legacyRequestId);

            if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
                helper.writeArray(buffer, packet.getLegacySlots(), (buf, packetHelper, data) -> {
                    buf.writeByte(data.getContainerId());
                    packetHelper.writeByteArray(buf, data.getSlots());
                });
            }

            TransactionType transactionType = packet.getTransactionType();
            VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

            helper.writeInventoryActions(buffer, session, packet.getActions(), packet.isUsingNetIds());

            switch (transactionType) {
                case ITEM_USE:
                    helper.writeItemUse(buffer, packet, session);
                    break;
                case ITEM_USE_ON_ENTITY:
                    this.writeItemUseOnEntity(buffer, helper, packet, session);
                    break;
                case ITEM_RELEASE:
                    this.writeItemRelease(buffer, helper, packet, session);
                    break;
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
            int legacyRequestId = VarInts.readInt(buffer);
            packet.setLegacyRequestId(legacyRequestId);

            if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
                helper.readArray(buffer, packet.getLegacySlots(), (buf, packetHelper) -> {
                    byte containerId = buf.readByte();
                    byte[] slots = packetHelper.readByteArray(buf);
                    return new LegacySetItemSlotData(containerId, slots);
                });
            }

            TransactionType transactionType = TransactionType.values()[VarInts.readUnsignedInt(buffer)];
            packet.setTransactionType(transactionType);

            packet.setUsingNetIds(helper.readInventoryActions(buffer, session, packet.getActions()));

            switch (transactionType) {
                case ITEM_USE:
                    helper.readItemUse(buffer, packet, session);
                    break;
                case ITEM_USE_ON_ENTITY:
                    this.readItemUseOnEntity(buffer, helper, packet, session);
                    break;
                case ITEM_RELEASE:
                    this.readItemRelease(buffer, helper, packet, session);
                    break;
            }
        }
    }

}
