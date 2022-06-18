package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType.SHAPELESS;
import static com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType.SHAPELESS_CHEMISTRY;
import static java.util.Objects.requireNonNull;

// TODO: Reading for this packet is not implemented yet.
public interface CraftingDataPacket extends BedrockPacket {
//    @NotNull CraftingData[] craftingData();
//    boolean cleanRecipes();
//    @NotNull PotionMixData[] potionMixData();
//    @NotNull ContainerMixData[] containerMixData();
//    /**
//     * @since v465
//     */
//    @NotNull MaterialReducer[] materialReducers();


    record v291(@NotNull CraftingData.Codec291[] craftingData,
                boolean cleanRecipes) implements CraftingDataPacket, Codec291 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, craftingData());
            writeBoolean(output, cleanRecipes());
        }
    }

    record v354(@NotNull CraftingData.Codec354[] craftingData,
                boolean cleanRecipes) implements CraftingDataPacket, Codec354 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, craftingData());
            writeBoolean(output, cleanRecipes());
        }
    }

    record v361(@NotNull CraftingData.Codec361[] craftingData,
                boolean cleanRecipes) implements CraftingDataPacket, Codec361 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, craftingData());
            writeBoolean(output, cleanRecipes());
        }
    }

    record v388(@NotNull CraftingData.Codec388[] craftingData, PotionMixData[] potionMixData,
                ContainerMixData[] containerMixData, boolean cleanRecipes) implements CraftingDataPacket, Codec388 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, craftingData());
            writeArray(output, potionMixData());
            writeArray(output, containerMixData());
            writeBoolean(output, cleanRecipes());
        }
    }

    record v407(@NotNull CraftingData.Codec407[] craftingData, PotionMixData[] potionMixData,
                ContainerMixData[] containerMixData, boolean cleanRecipes) implements CraftingDataPacket, Codec407 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, craftingData());
            writeArray(output, potionMixData());
            writeArray(output, containerMixData());
            writeBoolean(output, cleanRecipes());
        }
    }

    record v465(@NotNull CraftingData.Codec407[] craftingData, PotionMixData[] potionMixData,
                ContainerMixData[] containerMixData, MaterialReducer[] materialReducers,
                boolean cleanRecipes) implements CraftingDataPacket, Codec465 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, craftingData());
            writeArray(output, potionMixData());
            writeArray(output, containerMixData());
            writeArray(output, materialReducers());
            writeBoolean(output, cleanRecipes());
        }
    }

}
