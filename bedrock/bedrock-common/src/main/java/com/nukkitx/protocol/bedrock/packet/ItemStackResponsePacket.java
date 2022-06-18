package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerSlotType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ItemStackResponse is sent by the server in response to an ItemStackRequest packet from the client. This
 * packet is used to either approve or reject ItemStackRequests from the client. If a request is approved, the
 * client will simply continue as normal. If rejected, the client will undo the actions so that the inventory
 * should be in sync with the server again.
 */
interface ItemStackResponsePacket extends BedrockPacket {
    final List<Response> entries = new ArrayList<>();


    @Overrid

    public enum ResponseStatus {
        OK,
        ERROR
    }

    /**
     * Response is a response to an individual ItemStackRequest.
     */
    @Value
    public static class Response {
        /**
         * success specifies if the request with the requestId below was successful. If this is the case, the
         * containers below will have information on what slots ended up changing. If not, the container info
         * will be empty.
         *
         * @deprecated as of v419
         */
        @ToString.Exclude
        @Deprecated
        boolean success;

        /**
         * Replaces the success boolean as of v419
         */
        ResponseStatus result;

        /**
         * requestId is the unique ID of the request that this response is in reaction to. If rejected, the client
         * will undo the actions from the request with this ID.
         */
        int requestId;

        /**
         * containers holds information on the containers that had their contents changed as a result of the
         * request.
         */
        List<ContainerEntry> containers;

        @Deprecated
        public Response(boolean success, int requestId, List<ContainerEntry> containers) {
            this.success = success;
            this.requestId = requestId;
            this.containers = containers;
            this.result = success ? ResponseStatus.OK : ResponseStatus.ERROR;
        }

        public Response(ResponseStatus result, int requestId, List<ContainerEntry> containers) {
            this.result = result;
            this.requestId = requestId;
            this.containers = containers;
            this.success = false;
        }
    }

    /**
     * ContainerEntry holds information on what slots in a container have what item stack in them.
     */
    @Value
    public static class ContainerEntry {
        /**
         * container that the slots that follow are in.
         */
        ContainerSlotType container;

        /**
         * items holds information on what item stack should be present in specific slots in the container.
         */
        List<ItemEntry> items;
    }

    /**
     * ItemEntry holds information on what item stack should be present in a specific slot.
     */
    @Value
    public static class ItemEntry {
        byte slot;
        byte hotbarSlot;
        byte count;

        /**
         * stackNetworkID is the network ID of the new stack at a specific slot.
         */
        int stackNetworkId;

        /**
         * Holds the final custom name of a renamed item, if relevant.
         *
         * @since v422
         */
        @NonNull String customName;

        /**
         * @since v428
         */
        int durabilityCorrection;
    }

    record v407 implements ItemStackResponsePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
            helper.writeArray(buffer, packet.getEntries(), (buf, response) -> {
                buf.writeBoolean(response.isSuccess());
                VarInts.writeInt(buffer, response.getRequestId());

                if (!response.isSuccess())
                    return;

                helper.writeArray(buf, response.getContainers(), (buf2, containerEntry) -> {
                    buf2.writeByte(containerEntry.getContainer().ordinal());

                    helper.writeArray(buf2, containerEntry.getItems(), this::writeItemEntry);
                });
            });
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
            List<ItemStackResponsePacket.Response> entries = packet.getEntries();
            helper.readArray(buffer, entries, buf -> {
                boolean success = buf.readBoolean();
                int requestId = VarInts.readInt(buf);

                if (!success)
                    return new ItemStackResponsePacket.Response(success, requestId, Collections.emptyList());

                List<ItemStackResponsePacket.ContainerEntry> containerEntries = new ArrayList<>();
                helper.readArray(buf, containerEntries, buf2 -> {
                    ContainerSlotType container = ContainerSlotType.values()[buf2.readByte()];

                    List<ItemStackResponsePacket.ItemEntry> itemEntries = new ArrayList<>();
                    helper.readArray(buf2, itemEntries, byteBuf -> this.readItemEntry(byteBuf, helper));
                    return new ItemStackResponsePacket.ContainerEntry(container, itemEntries);
                });
                return new ItemStackResponsePacket.Response(success, requestId, containerEntries);
            });
        }

        protected ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            return new ItemStackResponsePacket.ItemEntry(
                    buffer.readByte(),
                    buffer.readByte(),
                    buffer.readByte(),
                    VarInts.readInt(buffer),
                    "",
                    0);
        }

        protected void writeItemEntry(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket.ItemEntry
                itemEntry) {
            buffer.writeByte(itemEntry.getSlot());
            buffer.writeByte(itemEntry.getHotbarSlot());
            buffer.writeByte(itemEntry.getCount());
            VarInts.writeInt(buffer, itemEntry.getStackNetworkId());
        }
    }

    record v419 extends ItemStackResponseReader_v407 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
            helper.writeArray(buffer, packet.getEntries(), (buf, response) -> {
                buf.writeByte(response.getResult().ordinal());
                VarInts.writeInt(buffer, response.getRequestId());

                if (response.getResult() != ItemStackResponsePacket.ResponseStatus.OK)
                    return;

                helper.writeArray(buf, response.getContainers(), (buf2, containerEntry) -> {
                    buf2.writeByte(containerEntry.getContainer().ordinal());

                    helper.writeArray(buf2, containerEntry.getItems(), this::writeItemEntry);
                });
            });
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket packet) {
            List<ItemStackResponsePacket.Response> entries = packet.getEntries();
            helper.readArray(buffer, entries, buf -> {
                ItemStackResponsePacket.ResponseStatus result = ItemStackResponsePacket.ResponseStatus.values()[buf.readByte()];
                int requestId = VarInts.readInt(buf);

                if (result != ItemStackResponsePacket.ResponseStatus.OK)
                    return new ItemStackResponsePacket.Response(result, requestId, Collections.emptyList());

                List<ItemStackResponsePacket.ContainerEntry> containerEntries = new ArrayList<>();
                helper.readArray(buf, containerEntries, buf2 -> {
                    ContainerSlotType container = ContainerSlotType.values()[buf2.readByte()];

                    List<ItemStackResponsePacket.ItemEntry> itemEntries = new ArrayList<>();
                    helper.readArray(buf2, itemEntries, byteBuf -> this.readItemEntry(buf2, helper));
                    return new ItemStackResponsePacket.ContainerEntry(container, itemEntries);
                });
                return new ItemStackResponsePacket.Response(result, requestId, containerEntries);
            });
        }

    }

    record v422 extends ItemStackResponseReader_v419 {


        @Override
        protected ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            return new ItemStackResponsePacket.ItemEntry(
                    buffer.readByte(),
                    buffer.readByte(),
                    buffer.readByte(),
                    VarInts.readInt(buffer),
                    helper.readString(buffer),
                    0);
        }

        @Override
        protected void writeItemEntry(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket.ItemEntry itemEntry) {
            super.writeItemEntry(buffer, helper, itemEntry);
            helper.writeString(buffer, itemEntry.getCustomName());
        }
    }

    record v428 extends ItemStackResponseReader_v422 {


        @Override
        protected ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            return new ItemStackResponsePacket.ItemEntry(
                    buffer.readByte(),
                    buffer.readByte(),
                    buffer.readByte(),
                    VarInts.readInt(buffer),
                    helper.readString(buffer),
                    VarInts.readInt(buffer));
        }

        @Override
        protected void writeItemEntry(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket.ItemEntry itemEntry) {
            super.writeItemEntry(buffer, helper, itemEntry);
            VarInts.writeInt(buffer, itemEntry.getDurabilityCorrection());
        }
    }


}
