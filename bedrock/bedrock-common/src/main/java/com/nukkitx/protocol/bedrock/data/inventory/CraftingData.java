package com.nukkitx.protocol.bedrock.data.inventory;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public interface CraftingData extends BitDataWritable, PacketDataWriter, BedrockPacket.CodecAware {
    @NotNull CraftingDataType type();
//    int id();

    interface Codec291 extends CraftingData, BedrockPacket.Codec291 { }
    interface Codec354 extends CraftingData, BedrockPacket.Codec354 { }
    interface Codec361 extends CraftingData, BedrockPacket.Codec361 { }
    interface Codec388 extends CraftingData, BedrockPacket.Codec388 { }
    interface Codec390 extends CraftingData, BedrockPacket.Codec390 { }
    interface Codec407 extends CraftingData, BedrockPacket.Codec407 { }

    interface FurnaceData extends CraftingData {
        int inputId();
        int inputDamage();
        ItemData output();

        @Override
        default @NotNull CraftingDataType type() {
            return CraftingDataType.FURNACE_DATA;
        }

        record v291(int inputId, int inputDamage, ItemData output) implements FurnaceData, Codec291 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, inputId());
                writeInt(output, inputDamage());
                output().write(output);
            }
        }

        record v407(int inputId, int inputDamage, ItemData output, String craftingTag) implements FurnaceData, Codec407 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, inputId());
                writeInt(output, inputDamage());
                output().write(output);
                writeString(output, craftingTag());
            }
        }
    }

    interface Furnace extends CraftingData {

        int inputId();
        ItemData input();

        @Override
        default @NotNull CraftingDataType type() {
            return CraftingDataType.FURNACE;
        }

        record v291(int inputId, ItemData input) implements Furnace, Codec291 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, inputId());
                input().write(output);
            }
        }

        record v354(int inputId, ItemData input, String craftingTag) implements Furnace, Codec354 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, inputId());
                input().write(output);
                writeString(output, craftingTag());
            }
        }

        record v407(int inputId, ItemData input, String craftingTag) implements Furnace, Codec407 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, inputId());
                input().write(output);
                writeString(output, craftingTag());
            }
        }
    }

    interface Shapeless extends CraftingData {

        @Override
        default @NotNull CraftingDataType type() {
            return CraftingDataType.SHAPELESS;
        }

        record v291(ItemData[] inputs, ItemData[] outputs, UUID uuid) implements Shapeless, Codec291 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeArray(output, inputs());
                writeArray(output, outputs());
                writeUuid(output, uuid());
            }
        }
        record v361(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag,
                    int priority) implements Shapeless, Codec361 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, recipeId());
                writeArray(output, inputs());
                writeArray(output, outputs());
                writeUuid(output, uuid());
                writeString(output, craftingTag());
                writeInt(output, priority());
            }
        }

        record v407(String recipeId, ItemData[] inputs, ItemData[] outputs, UUID uuid, String craftingTag,
                    int priority, int networkId) implements Shapeless, BedrockPacket.Codec407 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, recipeId());
                writeArray(output, inputs());
                writeArray(output, outputs());
                writeUuid(output, uuid());
                writeString(output, craftingTag());
                writeInt(output, priority());
                writeUnsignedInt(output, networkId());
            }
        }
//        @Override
//        protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
//            super.writeShapelessRecipe(buffer, helper, data, session);
//
//            helper.writeString(buffer, data.getCraftingTag());
//        }
    }

    interface Shaped extends CraftingData {
        @Override
        default @NotNull CraftingDataType type() {
            return CraftingDataType.SHAPED;
        }

        record v291(int width, int height, ItemData[] inputs, ItemData[] outputs,
                    UUID uuid) implements Shaped, Codec291 {

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                // Taken from v291
                writeInt(output, width());
                writeInt(output, height());

                int count = width() * height();
                ItemData[] inputs = inputs();
                for (int i = 0; i < count; i++) {
                    inputs[i].write(output);
                }

                writeArray(output, outputs());
                writeUuid(output, uuid());
            }
        }

        record v361(String recipeId, int width, int height, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                    String craftingTag, int priority) implements Shaped, Codec361 {

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, recipeId());
                writeInt(output, width());
                writeInt(output, height());

                int count = width() * height();
                ItemData[] inputs = inputs();
                assert inputs.length == count;
                for (ItemData input : inputs) {
                    input.write(output);
                }
                writeArray(output, outputs());
                writeUuid(output, uuid());
                writeString(output, craftingTag());
                writeInt(output, priority());
            }
        }

        record v407(String recipeId, int width, int height, ItemData[] inputs, ItemData[] outputs, UUID uuid,
                    String craftingTag, int priority, int networkId) implements Shaped, Codec407 {

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, recipeId());
                writeInt(output, width());
                writeInt(output, height());

                int count = width() * height();
                ItemData[] inputs = inputs();
                assert inputs.length == count;
                for (ItemData input : inputs) {
                    input.write(output);
                }
                writeArray(output, outputs());
                writeUuid(output, uuid());
                writeString(output, craftingTag());
                writeInt(output, priority());
                writeUnsignedInt(output, networkId());
            }
        }
//        @Override
//        protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
//            super.writeShapedRecipe(buffer, helper, data, session);
//
//            helper.writeString(buffer, data.getCraftingTag());
//        }
    }

    interface ShapelessChemistry extends CraftingData {
        @Override
        default @NotNull CraftingDataType type() {
            return CraftingDataType.SHAPELESS_CHEMISTRY;
        }

        record v291(ItemData[] inputs, ItemData[] outputs, UUID uuid) implements ShapelessChemistry, Codec291 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                // Taken from v291
                writeArray(output, inputs());
                writeArray(output, outputs());
                writeUuid(output, uuid());
            }
        }
    }

    interface ShapedChemistry extends CraftingData {
        record v291(int width, int height, ItemData[] inputs, ItemData[] outputs,
                    UUID uuid) implements ShapedChemistry, Codec291 {

            @Override
            public @NotNull CraftingDataType type() {
                return CraftingDataType.SHAPED_CHEMISTRY;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                // Taken from v291
                writeInt(output, width());
                writeInt(output, height());

                int count = width() * height();
                ItemData[] inputs = inputs();
                for (int i = 0; i < count; i++) {
                    inputs[i].write(output);
                }

                writeArray(output, outputs());
                writeUuid(output, uuid());
            }
        }
    }

    interface ShulkerBox extends CraftingData {
        record v291(ItemData[] inputs, ItemData[] outputs, UUID uuid) implements ShulkerBox, Codec291 {

            @Override
            public @NotNull CraftingDataType type() {
                return CraftingDataType.SHULKER_BOX;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeArray(output, inputs());
                writeArray(output, outputs());
                writeUuid(output, uuid());
            }
        }
    }

    interface Multi extends CraftingData {
        @Override
        default @NotNull CraftingDataType type() {
            return CraftingDataType.MULTI;
        }

        record v291(UUID uuid) implements Multi, Codec291 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeUuid(output, uuid());
            }
        }

        record v407(UUID uuid, int networkId) implements Multi, Codec291 {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeUuid(output, uuid());
                writeUnsignedInt(output, networkId());
            }
        }
    }
}
