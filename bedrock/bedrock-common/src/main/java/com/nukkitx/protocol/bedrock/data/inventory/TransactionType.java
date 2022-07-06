package com.nukkitx.protocol.bedrock.data.inventory;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface TransactionType extends BitDataWritable, PacketDataWriter {

    int id();

    interface Codec291 extends TransactionType, BedrockPacket.Codec291 { }
    interface Codec407 extends TransactionType, BedrockPacket.Codec407 { }

    @Override
    default void write(@NotNull BitOutput output) throws IOException {
    }

    int NORMAL = 0;
    interface Normal extends TransactionType {
        @Override
        default int id() {
            return NORMAL;
        }
    }
    int INVENTORY_MISMATCH = 1;
    interface InventoryMismatch extends TransactionType {
        @Override
        default int id() {
            return INVENTORY_MISMATCH;
        }
    }
    int ITEM_USE = 2;
    interface ItemUse extends TransactionType {

        @Override
        default int id() {
            return ITEM_USE;
        }

        record v291(@Unsigned int actionType, @BlockPosition Vector3i blockPosition, int blockFace, int hotbarSlot,
                    ItemData item, Vector3f playerPosition, Vector3f clickPosition) implements ItemUse, Codec291 {
            public static final BedrockPacket.Interpreter<v291> INTERPRETER = BedrockPacket.Interpreter.generate(v291.class);
            private static final BedrockPacket.Deferer<v291> DEFERER = BedrockPacket.Deferer.generate(v291.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }
    }

    int ITEM_USE_ON_ENTITY = 3;
    interface ItemUseOnEntity extends TransactionType {
        @Override
        default int id() {
            return ITEM_USE_ON_ENTITY;
        }

        record v291(@Unsigned long runtimeEntityId, @Unsigned int actionType, int hotbar, ItemData[] item,
                    Vector3f playerPosition, Vector3f clickPosition) implements ItemUseOnEntity, Codec291 {
            public static final BedrockPacket.Interpreter<v291> INTERPRETER = BedrockPacket.Interpreter.generate(v291.class);
            private static final BedrockPacket.Deferer<v291> DEFERER = BedrockPacket.Deferer.generate(v291.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }
    }
    int ITEM_RELEASE = 4;
    interface ItemRelease extends TransactionType {
        @Override
        default int id() {
            return ITEM_RELEASE;
        }

        record v291(@Unsigned int actionType, int hotbarSlot, ItemData item,
                    Vector3f headPosition) implements ItemRelease, Codec291 {
            public static final BedrockPacket.Interpreter<v291> INTERPRETER = BedrockPacket.Interpreter.generate(v291.class);
            private static final BedrockPacket.Deferer<v291> DEFERER = BedrockPacket.Deferer.generate(v291.class);

            public void write(@NotNull BitOutput output) throws IOException {
                DEFERER.defer(output, this);
            }
        }
    }
}
