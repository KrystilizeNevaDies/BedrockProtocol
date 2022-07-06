package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
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
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.io.IOException;
import java.util.List;

public interface InventoryTransactionPacket extends BedrockPacket {

    record v291(@Unsigned int typeId, InventoryActionData[] actions,
                TransactionType type) implements InventoryTransactionPacket {

        public v291(InventoryActionData[] actions, TransactionType.Codec291 type) {
            this(type.id(), actions, type);
        }

        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        private static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record LegacySlot(byte containerId, byte[] slots) implements BitDataWritable, PacketDataWriter {
        public static final Interpreter<LegacySlot> INTERPRETER = Interpreter.generate(LegacySlot.class);
        private static final Deferer<LegacySlot> DEFERER = Deferer.generate(LegacySlot.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

    record v407(int legacyRequestId, @NotNull LegacySlot @UnknownNullability [] legacySlots, @Unsigned int typeId, InventoryActionData[] actions,
                TransactionType.Codec407 type) implements InventoryTransactionPacket {
        public static final Interpreter<v407> INTERPRETER = Interpreter.generate(v407.class);
        private static final Deferer<v407> DEFERER = Deferer.generate(v407.class);

        public void write(@NotNull BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }
}
