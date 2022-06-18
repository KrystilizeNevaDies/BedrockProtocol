package com.nukkitx.protocol.bedrock.data.inventory;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataReader;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;

// TODO: Figure out how to structure this interface properly
public interface ItemData extends BitDataWritable, PacketDataWriter {

    BedrockPacket.Interpreter<ItemData> INTERPRETER = new BedrockPacket.Interpreter<>() {
        @Override
        public @NotNull ItemData interpret(@NotNull BitInput input) throws IOException {
            int id = readInt(input);
            if (id == 0) {
                return ItemData.from(id);
            }
            int damage = readInt(input);
            if (damage == 0x7fff) {
                damage = -1;
            }
            int count = readInt(input);
            return ItemData.from(id, damage, count);
        }
    };

    record Creative(int id, int damage, int stackSize, int networkId) implements ItemData, Damage, StackSize, NetworkId {
        public static final BedrockPacket.Interpreter<Creative> INTERPRETER = new BedrockPacket.Interpreter<Creative>() {
            @Override
            public @NotNull Creative interpret(@NotNull BitInput input) throws IOException {
                int networkId = readUnsignedInt(input);
                int id = readInt(input);
                int damage = readInt(input);
                int stackSize = readInt(input);
                return new Creative(id, damage, stackSize, networkId);
            }
        };
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUnsignedInt(output, networkId());
            writeInt(output, id());
        }
    }

    int id();

    interface ItemDataDS extends ItemData, Damage, StackSize {

        @Override
        default void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, id());
            if (id() != 0) {
                int damage = damage();
                if (damage == -1) damage = 0x7fff;
                writeInt(output, damage);
                writeInt(output, stackSize());
            }
        }
    }

    interface Damage extends ItemData {
        int damage();
    }
    interface StackSize extends ItemData {
        int stackSize();
    }
    interface NetworkId extends ItemData {
        int networkId();
    }

    static ItemDataDS from(int id, int damage, int stackSize) {
        return new ItemDataDS() {
            @Override
            public int damage() {
                return damage;
            }

            @Override
            public int stackSize() {
                return stackSize;
            }

            @Override
            public int id() {
                return id;
            }
        };
    }

    static ItemData from(int id) {
        return new ItemData() {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, id());
            }

            @Override
            public int id() {
                return id;
            }
        };
    }
}
