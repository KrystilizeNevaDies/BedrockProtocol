package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerSlotType;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
    Response[] entries();

    enum ResponseStatus {
        OK,
        ERROR
    }

    /**
     * Response is a response to an individual ItemStackRequest.
     */
    interface Response extends BitDataWritable, PacketDataWriter {
        /**
         * @param success success specifies if the request with the requestId below was successful. If this is the case,
         *                the containers below will have information on what slots ended up changing. If not, the
         *                container info will be empty.
         * @param requestId requestId is the unique ID of the request that this response is in reaction to. If
         *                  rejected, the client will undo the actions from the request with this ID.
         * @param containers containers holds information on the containers that had their contents changed as a
         *                   result of the request.
         */
        record v407(boolean success, int requestId, ContainerEntry.v407[] containers) implements Response {

            public void write(@NotNull BitOutput output) throws IOException {
                writeBoolean(output, success());
                writeInt(output, requestId());

                if (!success())
                    return;

                writeArray(output, containers());
            }
        }

        /**
         * @param status Replaces the success boolean as of v419
         * @param requestId requestId is the unique ID of the request that this response is in reaction to. If
         *                  rejected, the client will undo the actions from the request with this ID.
         * @param containers containers holds information on the containers that had their contents changed as a
         *                   result of the request.
         */
        record v419(ResponseStatus status, int requestId, ContainerEntry.v407[] containers) implements Response {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeEnum(output, status());
                writeInt(output, requestId());

                if (status() == ResponseStatus.ERROR)
                    return;

                writeArray(output, containers());
            }
        }

        /**
         * @param status Replaces the success boolean as of v419
         * @param requestId requestId is the unique ID of the request that this response is in reaction to. If
         *                  rejected, the client will undo the actions from the request with this ID.
         * @param containers containers holds information on the containers that had their contents changed as a
         *                   result of the request.
         */
        record v422(ResponseStatus status, int requestId, ContainerEntry.v422[] containers) implements Response {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeEnum(output, status());
                writeInt(output, requestId());

                if (status() == ResponseStatus.ERROR)
                    return;

                writeArray(output, containers());
            }
        }

        /**
         * @param status Replaces the success boolean as of v419
         * @param requestId requestId is the unique ID of the request that this response is in reaction to. If
         *                  rejected, the client will undo the actions from the request with this ID.
         * @param containers containers holds information on the containers that had their contents changed as a
         *                   result of the request.
         */
        record v428(ResponseStatus status, int requestId, ContainerEntry.v428[] containers) implements Response {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeEnum(output, status());
                writeInt(output, requestId());

                if (status() == ResponseStatus.ERROR)
                    return;

                writeArray(output, containers());
            }
        }
    }

    /**
     * ContainerEntry holds information on what slots in a container have what item stack in them.
     */
    interface ContainerEntry extends BitDataWritable, PacketDataWriter {

        /**
         * @return container that the slots that follow are in.
         */
        ContainerSlotType container();

        /**
         * @return items holds information on what item stack should be present in specific slots in the container.
         */
        ItemEntry[] items();

        record v407(ContainerSlotType container, ItemEntry.v407[] items) implements ContainerEntry {
            public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
            private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }

        record v422(ContainerSlotType container, ItemEntry.v422[] items) implements ContainerEntry {
            public static final Interpreter<v422> INTERPRETER = Interpreter.generate(v422.class);
            private static final Deferer<v422> DEFERER = Deferer.generate(v422.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }

        record v428(ContainerSlotType container, ItemEntry.v428[] items) implements ContainerEntry {
            public static final Interpreter<v428> INTERPRETER = Interpreter.generate(v428.class);
            private static final Deferer<v428> DEFERER = Deferer.generate(v428.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }
    }

    /**
     * ItemEntry holds information on what item stack should be present in a specific slot.
     */
    interface ItemEntry extends BitDataWritable, PacketDataWriter {
        byte slot();
        byte hotbarSlot();
        byte count();

        /**
         * stackNetworkID is the network ID of the new stack at a specific slot.
         */
        int stackNetworkId();

        record v407(byte slot, byte hotbarSlot, byte count, int stackNetworkId) implements ItemEntry {
            public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
            private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }

        record v422(byte slot, byte hotbarSlot, byte count, int stackNetworkId, String customName) implements ItemEntry {
            public static final Interpreter<v422> INTERPRETER = Interpreter.generate(v422.class);
            private static final Deferer<v422> DEFERER = Deferer.generate(v422.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }

        record v428(byte slot, byte hotbarSlot, byte count, int stackNetworkId, String customName,
                    int durabilityCorrection) implements ItemEntry {
            public static final Interpreter<v428> INTERPRETER = Interpreter.generate(v428.class);
            private static final Deferer<v428> DEFERER = Deferer.generate(v428.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }
    }

    record v407(Response.v407[] entries) implements ItemStackResponsePacket {
        public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
        private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v419(Response.v419[] entries) implements ItemStackResponsePacket {
        public static final Interpreter<v419> INTERPRETER = Interpreter.generate(v419.class);
        private static final Deferer<v419> DEFERER = Deferer.generate(v419.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v422(Response.v422[] entries) implements ItemStackResponsePacket {
        public static final Interpreter<v422> INTERPRETER = Interpreter.generate(v422.class);
        private static final Deferer<v422> DEFERER = Deferer.generate(v422.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v428(Response.v428[] entries) implements ItemStackResponsePacket {
        public static final Interpreter<v428> INTERPRETER = Interpreter.generate(v428.class);
        private static final Deferer<v428> DEFERER = Deferer.generate(v428.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
