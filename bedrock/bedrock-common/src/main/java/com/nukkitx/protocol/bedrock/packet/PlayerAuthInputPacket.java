package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.*;
import com.nukkitx.protocol.bedrock.data.inventory.LegacySetItemSlotData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemStackRequest;
import com.nukkitx.protocol.bedrock.data.inventory.ItemUseTransaction;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

interface PlayerAuthInputPacket extends BedrockPacket {
    Vector3f rotation; // head rot after motion
    Vector3f position;
    Vector2f motion;
    final Set<PlayerAuthInputData> inputData = EnumSet.noneOf(PlayerAuthInputData.class);
    InputMode inputMode;
    ClientPlayMode playMode;
    Vector3f vrGazeDirection;
    long tick;
    Vector3f delta;
    /**
     * {@link #inputData} must contain {@link PlayerAuthInputData#PERFORM_ITEM_INTERACTION} in order for this to not be null.
     *
     * @since v428
     */
    ItemUseTransaction itemUseTransaction;
    /**
     * {@link #inputData} must contain {@link PlayerAuthInputData#PERFORM_ITEM_STACK_REQUEST} in order for this to not be null.
     *
     * @since v428
     */
    ItemStackRequest itemStackRequest;
    /**
     * {@link #inputData} must contain {@link PlayerAuthInputData#PERFORM_BLOCK_ACTIONS} in order for this to not be empty.
     *
     * @since v428
     */
    final List<PlayerBlockActionData> playerActions = new ObjectArrayList<>();


    record v388 implements PlayerAuthInputPacket {


        protected static final InputMode[] INPUT_MODES = InputMode.values();
        protected static final ClientPlayMode[] CLIENT_PLAY_MODES = ClientPlayMode.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
            Vector3f rotation = packet.getRotation();
            buffer.writeFloatLE(rotation.getX());
            buffer.writeFloatLE(rotation.getY());
            helper.writeVector3f(buffer, packet.getPosition());
            buffer.writeFloatLE(packet.getMotion().getX());
            buffer.writeFloatLE(packet.getMotion().getY());
            buffer.writeFloatLE(rotation.getZ());
            long flagValue = 0;
            for (PlayerAuthInputData data : packet.getInputData()) {
                flagValue |= (1L << data.ordinal());
            }
            VarInts.writeUnsignedLong(buffer, flagValue);
            VarInts.writeUnsignedInt(buffer, packet.getInputMode().ordinal());
            VarInts.writeUnsignedInt(buffer, packet.getPlayMode().ordinal());

            if (packet.getPlayMode() == ClientPlayMode.REALITY) {
                helper.writeVector3f(buffer, packet.getVrGazeDirection());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
            float x = buffer.readFloatLE();
            float y = buffer.readFloatLE();
            packet.setPosition(helper.readVector3f(buffer));
            packet.setMotion(Vector2f.from(buffer.readFloatLE(), buffer.readFloatLE()));
            float z = buffer.readFloatLE();
            packet.setRotation(Vector3f.from(x, y, z));
            long flagValue = VarInts.readUnsignedLong(buffer);
            Set<PlayerAuthInputData> flags = packet.getInputData();
            for (PlayerAuthInputData flag : PlayerAuthInputData.values()) {
                if ((flagValue & (1L << flag.ordinal())) != 0) {
                    flags.add(flag);
                }
            }
            packet.setInputMode(INPUT_MODES[VarInts.readUnsignedInt(buffer)]);
            packet.setPlayMode(CLIENT_PLAY_MODES[VarInts.readUnsignedInt(buffer)]);

            if (packet.getPlayMode() == ClientPlayMode.REALITY) {
                packet.setVrGazeDirection(helper.readVector3f(buffer));
            }
        }
    }

    record v419 extends PlayerAuthInputReader_v388 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
            super.serialize(buffer, helper, packet);

            VarInts.writeUnsignedLong(buffer, packet.getTick());
            helper.writeVector3f(buffer, packet.getDelta());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setTick(VarInts.readUnsignedLong(buffer));
            packet.setDelta(helper.readVector3f(buffer));
        }
    }

    record v428 extends PlayerAuthInputReader_v419 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet, BedrockSession session) {
            super.serialize(buffer, helper, packet);

            if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {
                //TODO use inventory transaction packet serialization?
                ItemUseTransaction transaction = packet.getItemUseTransaction();
                int legacyRequestId = transaction.getLegacyRequestId();
                VarInts.writeInt(buffer, legacyRequestId);

                if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
                    helper.writeArray(buffer, transaction.getLegacySlots(), (buf, packetHelper, data) -> {
                        buf.writeByte(data.getContainerId());
                        packetHelper.writeByteArray(buf, data.getSlots());
                    });
                }

                helper.writeInventoryActions(buffer, session, transaction.getActions(), transaction.isUsingNetIds());
                VarInts.writeUnsignedInt(buffer, transaction.getActionType());
                helper.writeBlockPosition(buffer, transaction.getBlockPosition());
                VarInts.writeInt(buffer, transaction.getBlockFace());
                VarInts.writeInt(buffer, transaction.getHotbarSlot());
                helper.writeItem(buffer, transaction.getItemInHand(), session);
                helper.writeVector3f(buffer, transaction.getPlayerPosition());
                helper.writeVector3f(buffer, transaction.getClickPosition());
                VarInts.writeUnsignedInt(buffer, transaction.getBlockRuntimeId());
            }

            if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {
                helper.writeItemStackRequest(buffer, session, packet.getItemStackRequest());
            }

            if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
                VarInts.writeInt(buffer, packet.getPlayerActions().size());
                for (PlayerBlockActionData actionData : packet.getPlayerActions()) {
                    writePlayerBlockActionData(buffer, helper, actionData);
                }
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet, BedrockSession session) {
            super.deserialize(buffer, helper, packet);

            if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {
                ItemUseTransaction itemTransaction = new ItemUseTransaction();

                int legacyRequestId = VarInts.readInt(buffer);
                itemTransaction.setLegacyRequestId(legacyRequestId);

                if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
                    helper.readArray(buffer, itemTransaction.getLegacySlots(), (buf, packetHelper) -> {
                        byte containerId = buf.readByte();
                        byte[] slots = packetHelper.readByteArray(buf);
                        return new LegacySetItemSlotData(containerId, slots);
                    });
                }

                boolean hasNetIds = helper.readInventoryActions(buffer, session, itemTransaction.getActions());
                itemTransaction.setActionType(VarInts.readUnsignedInt(buffer));
                itemTransaction.setBlockPosition(helper.readBlockPosition(buffer));
                itemTransaction.setBlockFace(VarInts.readInt(buffer));
                itemTransaction.setHotbarSlot(VarInts.readInt(buffer));
                itemTransaction.setItemInHand(helper.readItem(buffer, session));
                itemTransaction.setPlayerPosition(helper.readVector3f(buffer));
                itemTransaction.setClickPosition(helper.readVector3f(buffer));
                itemTransaction.setBlockRuntimeId(VarInts.readUnsignedInt(buffer));
                packet.setItemUseTransaction(itemTransaction);
            }

            if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {
                packet.setItemStackRequest(helper.readItemStackRequest(buffer, session));
            }

            if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
                int arraySize = VarInts.readInt(buffer);
                for (int i = 0; i < arraySize; i++) {
                    packet.getPlayerActions().add(readPlayerBlockActionData(buffer, helper));
                }
            }
        }

        protected void writePlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper, PlayerBlockActionData actionData) {
            VarInts.writeInt(buffer, actionData.getAction().ordinal());
            switch (actionData.getAction()) {
                case START_BREAK:
                case ABORT_BREAK:
                case CONTINUE_BREAK:
                case BLOCK_PREDICT_DESTROY:
                case BLOCK_CONTINUE_DESTROY:
                    helper.writeVector3i(buffer, actionData.getBlockPosition());
                    VarInts.writeInt(buffer, actionData.getFace());
            }
        }

        protected PlayerBlockActionData readPlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper) {
            PlayerBlockActionData actionData = new PlayerBlockActionData();
            actionData.setAction(PlayerActionType.values()[VarInts.readInt(buffer)]);
            switch (actionData.getAction()) {
                case START_BREAK:
                case ABORT_BREAK:
                case CONTINUE_BREAK:
                case BLOCK_PREDICT_DESTROY:
                case BLOCK_CONTINUE_DESTROY:
                    actionData.setBlockPosition(helper.readVector3i(buffer));
                    actionData.setFace(VarInts.readInt(buffer));
            }
            return actionData;
        }
    }

    record v431 extends PlayerAuthInputReader_v428 {


        @Override
        protected void writePlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper, PlayerBlockActionData actionData) {
            super.writePlayerBlockActionData(buffer, helper, actionData);
            // PlayerActionType.STOP_BREAK - NOOP
        }

        @Override
        protected PlayerBlockActionData readPlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper) {
            // PlayerActionType.STOP_BREAK - NOOP
            return super.readPlayerBlockActionData(buffer, helper);
        }
    }


}
