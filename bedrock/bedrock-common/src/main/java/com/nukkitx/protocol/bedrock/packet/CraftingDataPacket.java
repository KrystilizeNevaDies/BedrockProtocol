package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

interface CraftingDataPacket extends BedrockPacket {
    private final List<CraftingData> craftingData = new ObjectArrayList<>();
    private final List<PotionMixData> potionMixData = new ObjectArrayList<>();
    private final List<ContainerMixData> containerMixData = new ObjectArrayList<>();
    /**
     * @since v465
     */
    private final List<MaterialReducer> materialReducers = new ObjectArrayList<>();
    private boolean cleanRecipes;


    public class CraftingDataReader_v291 implements BedrockPacketReader<CraftingDataPacket> {
        public static final CraftingDataReader_v291 INSTANCE = new CraftingDataReader_v291();

        protected static final ItemData[] EMPTY_ARRAY = {};

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
                VarInts.writeInt(buf, craftingData.getType().ordinal());
                switch (craftingData.getType()) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        this.writeShapelessRecipe(buf, helper, craftingData, session);
                        break;
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        this.writeShapedRecipe(buf, helper, craftingData, session);
                        break;
                    case FURNACE:
                    case FURNACE_DATA:
                        this.writeFurnaceRecipe(buf, helper, craftingData, session);
                        break;
                    case MULTI:
                        this.writeMultiRecipe(buf, helper, craftingData);
                        break;
                }
            });
            buffer.writeBoolean(packet.isCleanRecipes());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.readArray(buffer, packet.getCraftingData(), buf -> {
                int typeInt = VarInts.readInt(buf);
                CraftingDataType type = CraftingDataType.byId(typeInt);

                switch (type) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        return this.readShapelessRecipe(buf, helper, type, session);
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        return this.readShapedRecipe(buf, helper, type, session);
                    case FURNACE:
                    case FURNACE_DATA:
                        return this.readFurnaceRecipe(buf, helper, type, session);
                    case MULTI:
                        return this.readMultiRecipe(buf, helper, type);
                    default:
                        throw new IllegalArgumentException("Unhandled crafting data type: " + type);
                }
            });
            packet.setCleanRecipes(buffer.readBoolean());
        }

        protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            List<ItemData> inputs = new ObjectArrayList<>();
            helper.readArray(buffer, inputs, buf -> helper.readItem(buf, session));

            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));

            UUID uuid = helper.readUuid(buffer);
            return new CraftingData(type, -1, -1, -1, -1, inputs, outputs, uuid, null);
        }

        protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            helper.writeArray(buffer, data.getInputs(), (buf, item) -> helper.writeItem(buf, item, session));
            helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
            helper.writeUuid(buffer, data.getUuid());
        }

        protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            int width = VarInts.readInt(buffer);
            int height = VarInts.readInt(buffer);
            int inputCount = width * height;
            List<ItemData> inputs = new ObjectArrayList<>(inputCount);
            for (int i = 0; i < inputCount; i++) {
                inputs.add(helper.readItem(buffer, session));
            }
            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));
            UUID uuid = helper.readUuid(buffer);
            return new CraftingData(type, width, height, -1, -1, inputs, outputs, uuid, null);
        }

        protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            VarInts.writeInt(buffer, data.getWidth());
            VarInts.writeInt(buffer, data.getHeight());
            int count = data.getWidth() * data.getHeight();
            List<ItemData> inputs = data.getInputs();
            for (int i = 0; i < count; i++) {
                helper.writeItem(buffer, inputs.get(i), session);
            }
            helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
            helper.writeUuid(buffer, data.getUuid());
        }

        protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            int inputId = VarInts.readInt(buffer);
            int inputDamage = type == CraftingDataType.FURNACE_DATA ? VarInts.readInt(buffer) : -1;
            List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItem(buffer, session)));
            return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                    null, null);
        }

        protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            VarInts.writeInt(buffer, data.getInputId());
            if (data.getType() == CraftingDataType.FURNACE_DATA) {
                VarInts.writeInt(buffer, data.getInputDamage());
            }
            helper.writeItem(buffer, data.getOutputs().get(0), session);
        }

        protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
            UUID uuid = helper.readUuid(buffer);
            return CraftingData.fromMulti(uuid);
        }

        protected void writeMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
            helper.writeUuid(buffer, data.getUuid());
        }
    }

    public class CraftingDataReader_v354 extends CraftingDataReader_v291 {
        public static final CraftingDataReader_v354 INSTANCE = new CraftingDataReader_v354();

        @Override
        protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            List<ItemData> inputs = new ObjectArrayList<>();
            helper.readArray(buffer, inputs, buf -> helper.readItem(buf, session));

            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));

            UUID uuid = helper.readUuid(buffer);
            String craftingTag = helper.readString(buffer);
            return new CraftingData(type, -1, -1, -1, -1, inputs, outputs, uuid, craftingTag);
        }

        @Override
        protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            super.writeShapelessRecipe(buffer, helper, data, session);

            helper.writeString(buffer, data.getCraftingTag());
        }

        @Override
        protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            int width = VarInts.readInt(buffer);
            int height = VarInts.readInt(buffer);
            int inputCount = width * height;
            List<ItemData> inputs = new ObjectArrayList<>(inputCount);
            for (int i = 0; i < inputCount; i++) {
                inputs.add(helper.readItem(buffer, session));
            }
            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));
            UUID uuid = helper.readUuid(buffer);
            String craftingTag = helper.readString(buffer);
            return new CraftingData(type, width, height, -1, -1, inputs, outputs, uuid, craftingTag);
        }

        @Override
        protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            super.writeShapedRecipe(buffer, helper, data, session);

            helper.writeString(buffer, data.getCraftingTag());
        }

        @Override
        protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            int inputId = VarInts.readInt(buffer);
            int inputDamage = type == CraftingDataType.FURNACE_DATA ? VarInts.readInt(buffer) : -1;
            List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItem(buffer, session)));
            String craftingTag = helper.readString(buffer);
            return new CraftingData(type, -1, -1, inputId, inputDamage, null, output, null, craftingTag);
        }

        @Override
        protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            super.writeFurnaceRecipe(buffer, helper, data, session);

            helper.writeString(buffer, data.getCraftingTag());
        }
    }

    public class CraftingDataReader_v361 extends CraftingDataReader_v354 {
        public static final CraftingDataReader_v361 INSTANCE = new CraftingDataReader_v361();

        @Override
        protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            String recipeId = helper.readString(buffer);
            List<ItemData> inputs = new ObjectArrayList<>();
            helper.readArray(buffer, inputs, this::readIngredient);

            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));

            UUID uuid = helper.readUuid(buffer);
            String craftingTag = helper.readString(buffer);
            int priority = VarInts.readInt(buffer);
            return new CraftingData(type, recipeId, -1, -1, -1, -1, inputs, outputs, uuid,
                    craftingTag, priority);
        }

        @Override
        protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            helper.writeString(buffer, data.getRecipeId());
            helper.writeArray(buffer, data.getInputs(), this::writeIngredient);
            helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
            helper.writeUuid(buffer, data.getUuid());
            helper.writeString(buffer, data.getCraftingTag());
            VarInts.writeInt(buffer, data.getPriority());
        }

        @Override
        protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            String recipeId = helper.readString(buffer);
            int width = VarInts.readInt(buffer);
            int height = VarInts.readInt(buffer);
            int inputCount = width * height;
            List<ItemData> inputs = new ObjectArrayList<>(inputCount);
            for (int i = 0; i < inputCount; i++) {
                inputs.add(this.readIngredient(buffer));
            }
            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItem(buf, session));
            UUID uuid = helper.readUuid(buffer);
            String craftingTag = helper.readString(buffer);
            int priority = VarInts.readInt(buffer);
            return new CraftingData(type, recipeId, width, height, -1, -1, inputs, outputs, uuid,
                    craftingTag, priority);
        }

        @Override
        protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            helper.writeString(buffer, data.getRecipeId());
            VarInts.writeInt(buffer, data.getWidth());
            VarInts.writeInt(buffer, data.getHeight());
            int count = data.getWidth() * data.getHeight();
            List<ItemData> inputs = data.getInputs();
            for (int i = 0; i < count; i++) {
                this.writeIngredient(buffer, inputs.get(i));
            }
            helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
            helper.writeUuid(buffer, data.getUuid());
            helper.writeString(buffer, data.getCraftingTag());
            VarInts.writeInt(buffer, data.getPriority());
        }

        protected ItemData readIngredient(ByteBuf buffer) {
            int id = VarInts.readInt(buffer);
            int auxValue = 0;
            int stackSize = 0;

            if (id != 0) {
                auxValue = VarInts.readInt(buffer);
                if (auxValue == 0x7fff) auxValue = -1;
                stackSize = VarInts.readInt(buffer);
            }

            return ItemData.builder()
                    .id(id)
                    .damage(auxValue)
                    .count(stackSize)
                    .build();
        }

        protected void writeIngredient(ByteBuf buffer, ItemData itemData) {
            requireNonNull(itemData, "ItemData is null");

            int id = itemData.getId();
            VarInts.writeInt(buffer, id);

            if (id != 0) {
                int damage = itemData.getDamage();
                if (damage == -1) damage = 0x7fff;
                VarInts.writeInt(buffer, damage);
                VarInts.writeInt(buffer, itemData.getCount());
            }
        }
    }

    public class CraftingDataReader_v388 extends CraftingDataReader_v361 {
        public static final CraftingDataReader_v388 INSTANCE = new CraftingDataReader_v388();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
                VarInts.writeInt(buf, craftingData.getType().ordinal());
                switch (craftingData.getType()) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        this.writeShapelessRecipe(buf, helper, craftingData, session);
                        break;
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        this.writeShapedRecipe(buf, helper, craftingData, session);
                        break;
                    case FURNACE:
                    case FURNACE_DATA:
                        this.writeFurnaceRecipe(buf, helper, craftingData, session);
                        break;
                    case MULTI:
                        this.writeMultiRecipe(buf, helper, craftingData);
                        break;
                }
            });

            helper.writeArray(buffer, packet.getPotionMixData(), (buf, potionMixData) -> {
                VarInts.writeInt(buf, potionMixData.getInputId());
                VarInts.writeInt(buf, potionMixData.getReagentId());
                VarInts.writeInt(buf, potionMixData.getOutputId());
            });
            helper.writeArray(buffer, packet.getContainerMixData(), (buf, containerMixData) -> {
                VarInts.writeInt(buf, containerMixData.getInputId());
                VarInts.writeInt(buf, containerMixData.getReagentId());
                VarInts.writeInt(buf, containerMixData.getOutputId());
            });

            buffer.writeBoolean(packet.isCleanRecipes());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.readArray(buffer, packet.getCraftingData(), buf -> {
                int typeInt = VarInts.readInt(buf);
                CraftingDataType type = CraftingDataType.byId(typeInt);

                switch (type) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        return this.readShapelessRecipe(buf, helper, type, session);
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        return this.readShapedRecipe(buf, helper, type, session);
                    case FURNACE:
                    case FURNACE_DATA:
                        return this.readFurnaceRecipe(buf, helper, type, session);
                    case MULTI:
                        return this.readMultiRecipe(buf, helper, type);
                    default:
                        throw new IllegalArgumentException("Unhandled crafting data type: " + type);
                }
            });
            helper.readArray(buffer, packet.getPotionMixData(), buf -> {
                int fromPotionId = VarInts.readInt(buf);
                int ingredient = VarInts.readInt(buf);
                int toPotionId = VarInts.readInt(buf);
                return new PotionMixData(fromPotionId, 0, ingredient, 0, toPotionId, 0);
            });
            helper.readArray(buffer, packet.getContainerMixData(), buf -> {
                int fromItemId = VarInts.readInt(buf);
                int ingredient = VarInts.readInt(buf);
                int toItemId = VarInts.readInt(buf);
                return new ContainerMixData(fromItemId, ingredient, toItemId);
            });
            packet.setCleanRecipes(buffer.readBoolean());
        }
    }

    public class CraftingDataReader_v407 implements BedrockPacketReader<CraftingDataPacket> {
        public static final CraftingDataReader_v407 INSTANCE = new CraftingDataReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
                VarInts.writeInt(buf, craftingData.getType().ordinal());
                switch (craftingData.getType()) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        this.writeShapelessRecipe(buf, helper, craftingData, session);
                        break;
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        this.writeShapedRecipe(buf, helper, craftingData, session);
                        break;
                    case FURNACE:
                        this.writeFurnaceRecipe(buf, helper, craftingData, session);
                        break;
                    case FURNACE_DATA:
                        this.writeFurnaceDataRecipe(buf, helper, craftingData, session);
                        break;
                    case MULTI:
                        this.writeMultiRecipe(buf, helper, craftingData);
                        break;
                }
            });

            // Potions
            helper.writeArray(buffer, packet.getPotionMixData(), (buf, data) -> helper.writePotionRecipe(buf, data));

            // Potion Container Change
            helper.writeArray(buffer, packet.getContainerMixData(), (buf, data) -> helper.writeContainerChangeRecipe(buf, data));

            buffer.writeBoolean(packet.isCleanRecipes());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.readArray(buffer, packet.getCraftingData(), buf -> {
                int typeInt = VarInts.readInt(buf);
                CraftingDataType type = CraftingDataType.byId(typeInt);

                switch (type) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        return this.readShapelessRecipe(buf, helper, type, session);
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        return this.readShapedRecipe(buf, helper, type, session);
                    case FURNACE:
                        return this.readFurnaceRecipe(buf, helper, type, session);
                    case FURNACE_DATA:
                        return this.readFurnaceDataRecipe(buf, helper, type, session);
                    case MULTI:
                        return this.readMultiRecipe(buf, helper, type);
                    default:
                        throw new IllegalArgumentException("Unhandled crafting data type: " + type);
                }
            });

            // Potions
            helper.readArray(buffer, packet.getPotionMixData(), helper::readPotionRecipe);

            // Potion Container Change
            helper.readArray(buffer, packet.getContainerMixData(), helper::readContainerChangeRecipe);

            packet.setCleanRecipes(buffer.readBoolean());
        }

        protected CraftingData readShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            String recipeId = helper.readString(buffer);
            List<ItemData> inputs = new ObjectArrayList<>();
            helper.readArray(buffer, inputs, helper::readRecipeIngredient);

            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItemInstance(buf, session));

            UUID uuid = helper.readUuid(buffer);
            String craftingTag = helper.readString(buffer);
            int priority = VarInts.readInt(buffer);
            int networkId = VarInts.readUnsignedInt(buffer);
            return new CraftingData(type, recipeId,-1, -1, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
        }

        protected void writeShapelessRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            helper.writeString(buffer, data.getRecipeId());
            helper.writeArray(buffer, data.getInputs(), helper::writeRecipeIngredient);
            helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItemInstance(buf, item, session));

            helper.writeUuid(buffer, data.getUuid());
            helper.writeString(buffer, data.getCraftingTag());
            VarInts.writeInt(buffer, data.getPriority());
            VarInts.writeUnsignedInt(buffer, data.getNetworkId());
        }

        protected CraftingData readShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            String recipeId = helper.readString(buffer);
            int width = VarInts.readInt(buffer);
            int height = VarInts.readInt(buffer);
            int inputCount = width * height;
            List<ItemData> inputs = new ObjectArrayList<>(inputCount);
            for (int i = 0; i < inputCount; i++) {
                inputs.add(helper.readRecipeIngredient(buffer));
            }
            List<ItemData> outputs = new ObjectArrayList<>();
            helper.readArray(buffer, outputs, buf -> helper.readItemInstance(buf, session));
            UUID uuid = helper.readUuid(buffer);
            String craftingTag = helper.readString(buffer);
            int priority = VarInts.readInt(buffer);
            int networkId = VarInts.readUnsignedInt(buffer);
            return new CraftingData(type, recipeId, width, height, -1, -1, inputs, outputs, uuid, craftingTag, priority, networkId);
        }

        protected void writeShapedRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            helper.writeString(buffer, data.getRecipeId());
            VarInts.writeInt(buffer, data.getWidth());
            VarInts.writeInt(buffer, data.getHeight());
            int count = data.getWidth() * data.getHeight();
            List<ItemData> inputs = data.getInputs();
            for (int i = 0; i < count; i++) {
                helper.writeRecipeIngredient(buffer, inputs.get(i));
            }
            helper.writeArray(buffer, data.getOutputs(), (buf, item) -> helper.writeItemInstance(buf, item, session));
            helper.writeUuid(buffer, data.getUuid());
            helper.writeString(buffer, data.getCraftingTag());
            VarInts.writeInt(buffer, data.getPriority());
            VarInts.writeUnsignedInt(buffer, data.getNetworkId());
        }

        protected CraftingData readFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            int inputId = VarInts.readInt(buffer);
            List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer, session)));
            String craftingTag = helper.readString(buffer);
            return new CraftingData(type, -1, -1, inputId, -1, null, output,
                    null, craftingTag);
        }

        protected void writeFurnaceRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            VarInts.writeInt(buffer, data.getInputId());
            helper.writeItemInstance(buffer, data.getOutputs().get(0), session);
            helper.writeString(buffer, data.getCraftingTag());
        }

        protected CraftingData readFurnaceDataRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type, BedrockSession session) {
            int inputId = VarInts.readInt(buffer);
            int inputDamage = VarInts.readInt(buffer);
            List<ItemData> output = new ObjectArrayList<>(Collections.singleton(helper.readItemInstance(buffer, session)));
            String craftingTag = helper.readString(buffer);
            return new CraftingData(type, -1, -1, inputId, inputDamage, null, output,
                    null, craftingTag);
        }

        protected void writeFurnaceDataRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data, BedrockSession session) {
            VarInts.writeInt(buffer, data.getInputId());
            VarInts.writeInt(buffer, data.getInputDamage());
            helper.writeItemInstance(buffer, data.getOutputs().get(0), session);
            helper.writeString(buffer, data.getCraftingTag());
        }

        protected CraftingData readMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataType type) {
            UUID uuid = helper.readUuid(buffer);
            int networkId = VarInts.readUnsignedInt(buffer);
            return CraftingData.fromMulti(uuid, networkId);
        }

        protected void writeMultiRecipe(ByteBuf buffer, BedrockPacketHelper helper, CraftingData data) {
            helper.writeUuid(buffer, data.getUuid());
            VarInts.writeUnsignedInt(buffer, data.getNetworkId());
        }
    }

    public class CraftingDataReader_v465 extends CraftingDataReader_v407 {

        public static final CraftingDataReader_v465 INSTANCE = new CraftingDataReader_v465();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.writeArray(buffer, packet.getCraftingData(), (buf, craftingData) -> {
                VarInts.writeInt(buf, craftingData.getType().ordinal());
                switch (craftingData.getType()) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        this.writeShapelessRecipe(buf, helper, craftingData, session);
                        break;
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        this.writeShapedRecipe(buf, helper, craftingData, session);
                        break;
                    case FURNACE:
                        this.writeFurnaceRecipe(buf, helper, craftingData, session);
                        break;
                    case FURNACE_DATA:
                        this.writeFurnaceDataRecipe(buf, helper, craftingData, session);
                        break;
                    case MULTI:
                        this.writeMultiRecipe(buf, helper, craftingData);
                        break;
                }
            });

            // Potions
            helper.writeArray(buffer, packet.getPotionMixData(), helper::writePotionRecipe);

            // Potion Container Change
            helper.writeArray(buffer, packet.getContainerMixData(), helper::writeContainerChangeRecipe);

            // Material Reducers
            helper.writeArray(buffer, packet.getMaterialReducers(), this::writeMaterialReducer);

            buffer.writeBoolean(packet.isCleanRecipes());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingDataPacket packet, BedrockSession session) {
            helper.readArray(buffer, packet.getCraftingData(), buf -> {
                int typeInt = VarInts.readInt(buf);
                CraftingDataType type = CraftingDataType.byId(typeInt);

                switch (type) {
                    case SHAPELESS:
                    case SHAPELESS_CHEMISTRY:
                    case SHULKER_BOX:
                        return this.readShapelessRecipe(buf, helper, type, session);
                    case SHAPED:
                    case SHAPED_CHEMISTRY:
                        return this.readShapedRecipe(buf, helper, type, session);
                    case FURNACE:
                        return this.readFurnaceRecipe(buf, helper, type, session);
                    case FURNACE_DATA:
                        return this.readFurnaceDataRecipe(buf, helper, type, session);
                    case MULTI:
                        return this.readMultiRecipe(buf, helper, type);
                    default:
                        throw new IllegalArgumentException("Unhandled crafting data type: " + type);
                }
            });

            // Potions
            helper.readArray(buffer, packet.getPotionMixData(), helper::readPotionRecipe);

            // Potion Container Change
            helper.readArray(buffer, packet.getContainerMixData(), helper::readContainerChangeRecipe);

            // Material Reducers
            helper.readArray(buffer, packet.getMaterialReducers(), this::readMaterialReducer);

            packet.setCleanRecipes(buffer.readBoolean());
        }

        protected void writeMaterialReducer(ByteBuf buffer, BedrockPacketHelper helper, MaterialReducer reducer) {
            VarInts.writeInt(buffer, reducer.getInputId());
            helper.writeArray(buffer, reducer.getItemCounts().int2IntEntrySet(), (buf, entry) -> {
                VarInts.writeInt(buffer, entry.getIntKey());
                VarInts.writeInt(buffer, entry.getIntValue());
            });
        }

        protected MaterialReducer readMaterialReducer(ByteBuf buffer, BedrockPacketHelper helper) {
            int inputId = VarInts.readInt(buffer);
            Int2IntMap itemCounts = new Int2IntOpenHashMap();
            int length = VarInts.readUnsignedInt(buffer);
            for (int i = 0; i < length; i++) {
                itemCounts.put(VarInts.readInt(buffer), VarInts.readInt(buffer));
            }
            return new MaterialReducer(inputId, itemCounts);
        }
    }

}
