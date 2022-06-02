package com.nukkitx.protocol.bedrock;

import com.github.jinahya.bit.io.BitInput;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumConstraintData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumConstraintType;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.data.skin.AnimationData;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.serializer.PacketDataReader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public interface BedrockPacketReader extends PacketDataReader {

    static @NotNull BedrockPacketReader create() {
        return new BedrockPacketReader() {};
    }

    default void readItemUse(@NotNull BitInput input, InventoryTransactionPacket packet) throws IOException {
        packet.setActionType(readUnsignedInt(input));
        packet.setBlockPosition(readBlockPosition(input));
        packet.setBlockFace(readInt(input));
        packet.setHotbarSlot(readInt(input));
        packet.setItemInHand(readItem(input));
        packet.setPlayerPosition(readVector3f(input));
        packet.setClickPosition(readVector3f(input));
    }

    default InventoryActionData[] readInventoryActions(@NotNull BitInput input, List<InventoryActionData> actions) throws IOException {
        return readArray(input, () -> {
            InventorySource source = readSource(input);
            int slot = readUnsignedInt(input);
            ItemData from = readItem(input);
            ItemData to = readItem(input);
            
            return new InventoryActionData(source, slot, from, to);
        });
    }

    default InventorySource readSource(@NotNull BitInput input) throws IOException {
        InventorySource.Type type = InventorySource.Type.byId(readUnsignedInt(input));

        switch (type) {
            case CONTAINER:
                int containerId = readInt(input);
                return InventorySource.fromContainerWindowId(containerId);
            case GLOBAL:
                return InventorySource.fromGlobalInventory();
            case WORLD_INTERACTION:
                InventorySource.Flag flag = InventorySource.Flag.values()[readUnsignedInt(input)];
                return InventorySource.fromWorldInteraction(flag);
            case CREATIVE:
                return InventorySource.fromCreativeInventory();
            case NON_IMPLEMENTED_TODO:
                containerId = readInt(input);
                return InventorySource.fromNonImplementedTodo(containerId);
            default:
                return InventorySource.fromInvalid();
        }
    }

    default ItemData readRecipeIngredient(@NotNull BitInput input) throws IOException {
        requireNonNull(input, "input is null");

        int id = readInt(input);

        if (id == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }

        int meta = readInt(input);
        int count = readInt(input);

        return ItemData.builder()
                .id(id)
                .damage(meta)
                .count(count)
                .build();
    }

    default PotionMixData readPotionRecipe(@NotNull BitInput input) throws IOException {
        requireNonNull(input, "input is null");

        return new PotionMixData(
                readInt(input),
                readInt(input),
                readInt(input),
                readInt(input),
                readInt(input),
                readInt(input)
        );
    }

    default ContainerMixData readContainerChangeRecipe(@NotNull BitInput input) throws IOException {
        requireNonNull(input, "input is null");

        return new ContainerMixData(
                readInt(input),
                readInt(input),
                readInt(input)
        );
    }

    default CommandEnumConstraintData readCommandEnumConstraints(@NotNull BitInput input, List<CommandEnumData> enums, List<String> enumValues) throws IOException {
        int valueIndex = readIntLE(input);
        int enumIndex = readIntLE(input);

        CommandEnumConstraintType[] constraints = readArray(input, () -> CommandEnumConstraintType.byId(input.readByte8()));

        return new CommandEnumConstraintData(
                enumValues.get(valueIndex),
                enums.get(enumIndex),
                constraints
        );
    }

    default @NotNull AttributeData readAttribute(@NotNull BitInput input) throws IOException {
        String name = readString(input);
        float min = readFloatLE(input);
        float max = readFloatLE(input);
        float val = readFloatLE(input);

        return new AttributeData(name, min, max, val);
    }

    default @NotNull EntityLinkData readEntityLink(@NotNull BitInput input) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull ItemData readNetItem(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull ItemData readItem(@NotNull BitInput input) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull ItemData readItemInstance(@NotNull BitInput input) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull CommandOriginData readCommandOrigin(@NotNull BitInput input) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull GameRuleData<?> readGameRule(@NotNull BitInput input) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull Map<EntityData.Type<?>, EntityData> readEntityData(@NotNull BitInput input) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull CommandEnumData readCommandEnum(@NotNull BitInput input, boolean soft) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull StructureSettings readStructureSettings(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull SerializedSkin readSkin(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull AnimationData readAnimationData(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Not implemented");
    }
    default @NotNull ImageData readImage(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
}
