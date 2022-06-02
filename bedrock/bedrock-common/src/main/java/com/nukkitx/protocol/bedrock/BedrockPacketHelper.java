package com.nukkitx.protocol.bedrock;

import com.github.jinahya.bit.io.BitInput;
import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtType;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.*;
import com.nukkitx.protocol.bedrock.data.command.*;
import com.nukkitx.protocol.bedrock.data.entity.*;
import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import com.nukkitx.protocol.bedrock.data.skin.AnimationData;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import com.nukkitx.protocol.util.TriFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.util.AsciiString;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public abstract class BedrockPacketHelper {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPacketHelper.class);

    protected final Int2ObjectBiMap<EntityDataType> entityData = new Int2ObjectBiMap<>();
    protected final Int2ObjectBiMap<EntityFlag> entityFlags = new Int2ObjectBiMap<>();
    protected final Int2ObjectBiMap<EntityDataType.ValueType> entityDataTypes = new Int2ObjectBiMap<>();
    protected final Int2ObjectBiMap<EntityEventType> entityEvents = new Int2ObjectBiMap<>();
    protected final Object2IntMap<Class<?>> gameRuleTypes = new Object2IntOpenHashMap<>(3, 0.5f);
    protected final Int2ObjectBiMap<SoundEvent> soundEvents = new Int2ObjectBiMap<>();
    protected final Int2ObjectBiMap<LevelEventType> levelEvents = new Int2ObjectBiMap<>();
    protected final Int2ObjectBiMap<CommandParam> commandParams = new Int2ObjectBiMap<>();
    protected final Int2ObjectBiMap<ResourcePackType> resourcePackTypes = new Int2ObjectBiMap<>();

    protected BedrockPacketHelper() {
        gameRuleTypes.defaultReturnValue(-1);

        this.registerEntityDataTypes();
        this.registerEntityData();
        this.registerEntityFlags();
        this.registerEntityEvents();
        this.registerGameRuleTypes();
        this.registerSoundEvents();
        this.registerLevelEvents();
        this.registerCommandParams();
        this.registerResourcePackTypes();
    }

    protected final void addGameRuleType(int index, Class<?> clazz) {
        this.gameRuleTypes.put(clazz, index);
    }

    protected final void addEntityData(int index, EntityDataType entityDataType) {
        this.entityData.put(index, entityDataType);
    }

    protected final void addEntityFlag(int index, EntityFlag flag) {
        this.entityFlags.put(index, flag);
    }

    protected final void addEntityDataType(int index, EntityDataType.ValueType valueType) {
        this.entityDataTypes.put(index, valueType);
    }

    protected final void addEntityEvent(int index, EntityEventType type) {
        this.entityEvents.put(index, type);
    }

    protected final void addSoundEvent(int index, SoundEvent soundEvent) {
        this.soundEvents.put(index, soundEvent);
    }

    protected final void addLevelEvent(int index, LevelEventType levelEventType) {
        this.levelEvents.put(index, levelEventType);
    }

    public final int getEntityEventId(EntityEventType type) {
        // @TODO For speed we may want a flag that disables this check for production use
        if (!this.entityEvents.containsValue(type)) {
            log.debug("Unknown EntityEventType: {}", type);
            return this.entityEvents.get(EntityEventType.NONE);
        }
        return this.entityEvents.get(type);
    }

    public final EntityEventType getEntityEvent(int id) {
        // @TODO For speed we may want a flag that disables this check for production use
        if (!entityEvents.containsKey(id)) {
            log.debug("Unknown EntityEvent: {}", id);
            return EntityEventType.NONE;
        }
        return this.entityEvents.get(id);
    }

    public final int getSoundEventId(SoundEvent event) {
        if (!soundEvents.containsValue(event)) {
            log.debug("Unknown SoundEvent {} received", event);
            return soundEvents.get(SoundEvent.UNDEFINED);
        }
        return this.soundEvents.get(event);
    }

    public final SoundEvent getSoundEvent(int id) {
        SoundEvent soundEvent = this.soundEvents.get(id);
        if (soundEvent == null) {
            log.debug("Unknown SoundEvent {} received", Integer.toUnsignedLong(id));
            return SoundEvent.UNDEFINED;
        }
        return soundEvent;
    }

    public final int getLevelEventId(LevelEventType event) {
        // @TODO For speed we may want a flag that disables this check for production use
        if (!this.levelEvents.containsValue(event)) {
            log.debug("Unknown LevelEventType: {}", event);
            return this.levelEvents.get(LevelEventType.UNDEFINED);
        }
        return this.levelEvents.get(event);
    }

    public final LevelEventType getLevelEvent(int id) {
        LevelEventType levelEvent = this.levelEvents.get(id);
        if (levelEvent == null) {
            log.debug("Unknown LevelEventType {} received", id);
            return LevelEventType.UNDEFINED;
        }
        return levelEvent;

    }

    public final void addCommandParam(int index, CommandParam commandParam) {
        this.commandParams.put(index, commandParam);
    }

    public final CommandParam getCommandParam(int index) {
        CommandParam commandParam = this.commandParams.get(index);
        if (commandParam == null) {
            log.debug("Requested undefined CommandParam {}", index);
            return new CommandParam(index);
        }
        return commandParam;
    }

    public final int getCommandParamId(CommandParam commandParam) {
        return this.commandParams.get(commandParam);
    }

    public final void removeCommandParam(int index) {
        this.commandParams.remove(index);
    }

    public final void removeCommandParam(CommandParam type) {
        this.commandParams.remove(type);
    }
    
    public final void addResourcePackType(int index, ResourcePackType resourcePackType) {
        this.resourcePackTypes.put(index, resourcePackType);
    }

    public final ResourcePackType getResourcePackType(int index) {
        return this.resourcePackTypes.get(index);
    }

    public final int getResourcePackTypeId(ResourcePackType resourcePackType) {
        return this.resourcePackTypes.get(resourcePackType);
    }

    protected abstract void registerEntityData();

    protected abstract void registerEntityFlags();

    protected abstract void registerEntityDataTypes();

    protected abstract void registerEntityEvents();

    protected abstract void registerGameRuleTypes();

    protected abstract void registerSoundEvents();

    protected abstract void registerCommandParams();

    protected abstract void registerResourcePackTypes();

    protected abstract void registerLevelEvents();

    public abstract EntityLinkData readEntityLink(BitInput buffer);

    public abstract ItemData readNetItem(BitInput buffer, BedrockSession session);

    public abstract ItemData readItem(BitInput buffer, BedrockSession session);

    public abstract ItemData readItemInstance(BitInput buffer, BedrockSession session);

    public abstract CommandOriginData readCommandOrigin(BitInput buffer);

    public abstract GameRuleData<?> readGameRule(BitInput buffer);

    public abstract void readEntityData(BitInput buffer, EntityDataMap entityData);

    public abstract CommandEnumData readCommandEnum(BitInput buffer, boolean soft);

    public abstract StructureSettings readStructureSettings(BitInput buffer);

    public abstract SerializedSkin readSkin(BitInput buffer);

    public abstract AnimationData readAnimationData(BitInput buffer);

    public abstract ImageData readImage(BitInput buffer);

    public ByteBuf readBuffer(BitInput buffer) {
        int length = VarInts.readUnsignedInt(buffer);
        return buffer.readRetainedSlice(length);
    }

    public AsciiString readLEAsciiString(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        CharSequence string = buffer.readCharSequence(buffer.readIntLE(), StandardCharsets.US_ASCII);
        // Older Netty versions do not necessarily provide an AsciiString for this method
        if (string instanceof AsciiString) {
            return (AsciiString) string;
        } else {
            return new AsciiString(string);
        }
    }

    public UUID readUuid(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        return new UUID(buffer.readLongLE(), buffer.readLongLE());
    }

    public Vector3f readVector3f(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        float x = buffer.readFloatLE();
        float y = buffer.readFloatLE();
        float z = buffer.readFloatLE();
        return Vector3f.from(x, y, z);
    }

    public Vector2f readVector2f(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        float x = buffer.readFloatLE();
        float y = buffer.readFloatLE();
        return Vector2f.from(x, y);
    }

    public Vector3i readVector3i(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        int x = VarInts.readInt(buffer);
        int y = VarInts.readInt(buffer);
        int z = VarInts.readInt(buffer);

        return Vector3i.from(x, y, z);
    }

    public Vector3i readBlockPosition(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        int x = VarInts.readInt(buffer);
        int y = VarInts.readUnsignedInt(buffer);
        int z = VarInts.readInt(buffer);

        return Vector3i.from(x, y, z);
    }

    public Vector3f readByteRotation(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        float pitch = readByteAngle(buffer);
        float yaw = readByteAngle(buffer);
        float roll = readByteAngle(buffer);
        return Vector3f.from(pitch, yaw, roll);
    }

    public float readByteAngle(BitInput buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        return buffer.readByte() * (360f / 256f);
    }

    /*
        Helper array serialization
     */

    public <T> void readArray(BitInput buffer, Collection<T> array, BiFunction<ByteBuf, BedrockPacketHelper, T> function) {
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            array.add(function.apply(buffer, this));
        }
    }

    public <T> void readArray(BitInput buffer, Collection<T> array, BedrockSession session,
                              TriFunction<ByteBuf, BedrockPacketHelper, BedrockSession, T> function) {
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            array.add(function.apply(buffer, this, session));
        }
    }

    public <T> T[] readArray(BitInput buffer, T[] array, BiFunction<ByteBuf, BedrockPacketHelper, T> function) {
        ObjectArrayList<T> list = new ObjectArrayList<>();
        readArray(buffer, list, function);
        return list.toArray(array);
    }

    public <T> T[] readArray(BitInput buffer, T[] array, BedrockSession session,
                             TriFunction<ByteBuf, BedrockPacketHelper, BedrockSession, T> function) {
        ObjectArrayList<T> list = new ObjectArrayList<>();
        readArray(buffer, list, session, function);
        return list.toArray(array);
    }

    public <T> void readArrayShortLE(BitInput buffer, Collection<T> array, BiFunction<ByteBuf, BedrockPacketHelper, T> function) {
        int length = buffer.readUnsignedShortLE();
        for (int i = 0; i < length; i++) {
            array.add(function.apply(buffer, this));
        }
    }

    /*
        Non-helper array serialization
     */

    public <T> void readArray(BitInput buffer, Collection<T> array, Function<ByteBuf, T> function) {
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            array.add(function.apply(buffer));
        }
    }

    public <T> T[] readArray(BitInput buffer, T[] array, Function<ByteBuf, T> function) {
        ObjectArrayList<T> list = new ObjectArrayList<>();
        readArray(buffer, list, function);
        return list.toArray(array);
    }

    public <T> void readArrayShortLE(BitInput buffer, Collection<T> array, Function<ByteBuf, T> function) {
        int length = buffer.readUnsignedShortLE();
        for (int i = 0; i < length; i++) {
            array.add(function.apply(buffer));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T readTag(BitInput buffer) {
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            return (T) reader.readTag();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readTagValue(BitInput buffer, NbtType<T> type) {
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            return reader.readValue(type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readItemUse(BitInput buffer, InventoryTransactionPacket packet, BedrockSession session) {
        packet.setActionType(VarInts.readUnsignedInt(buffer));
        packet.setBlockPosition(this.readBlockPosition(buffer));
        packet.setBlockFace(VarInts.readInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setItemInHand(this.readItem(buffer, session));
        packet.setPlayerPosition(this.readVector3f(buffer));
        packet.setClickPosition(this.readVector3f(buffer));
    }

    public boolean readInventoryActions(BitInput buffer, BedrockSession session, List<InventoryActionData> actions) {
        this.readArray(buffer, actions, session, (buf, helper, aSession) -> {
            InventorySource source = helper.readSource(buf);
            int slot = VarInts.readUnsignedInt(buf);
            ItemData fromItem = helper.readItem(buf, aSession);
            ItemData toItem = helper.readItem(buf, aSession);

            return new InventoryActionData(source, slot, fromItem, toItem);
        });
        return false;
    }

    public InventorySource readSource(BitInput buffer) {
        InventorySource.Type type = InventorySource.Type.byId(VarInts.readUnsignedInt(buffer));

        switch (type) {
            case CONTAINER:
                int containerId = VarInts.readInt(buffer);
                return InventorySource.fromContainerWindowId(containerId);
            case GLOBAL:
                return InventorySource.fromGlobalInventory();
            case WORLD_INTERACTION:
                InventorySource.Flag flag = InventorySource.Flag.values()[VarInts.readUnsignedInt(buffer)];
                return InventorySource.fromWorldInteraction(flag);
            case CREATIVE:
                return InventorySource.fromCreativeInventory();
            case NON_IMPLEMENTED_TODO:
                containerId = VarInts.readInt(buffer);
                return InventorySource.fromNonImplementedTodo(containerId);
            default:
                return InventorySource.fromInvalid();
        }
    }

    public ItemData readRecipeIngredient(BitInput buffer) {
        requireNonNull(buffer, "buffer is null");

        int id = VarInts.readInt(buffer);

        if (id == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }

        int meta = VarInts.readInt(buffer);
        int count = VarInts.readInt(buffer);

        return ItemData.builder()
                .id(id)
                .damage(meta)
                .count(count)
                .build();
    }

    public PotionMixData readPotionRecipe(BitInput buffer) {
        requireNonNull(buffer, "buffer is null");

        return new PotionMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    public ContainerMixData readContainerChangeRecipe(BitInput buffer) {
        requireNonNull(buffer, "buffer is null");

        return new ContainerMixData(
                VarInts.readInt(buffer),
                VarInts.readInt(buffer),
                VarInts.readInt(buffer)
        );
    }

    public CommandEnumConstraintData readCommandEnumConstraints(BitInput buffer, List<CommandEnumData> enums, List<String> enumValues) {
        int valueIndex = buffer.readIntLE();
        int enumIndex = buffer.readIntLE();
        CommandEnumConstraintType[] constraints = readArray(buffer, new CommandEnumConstraintType[0],
                buf -> CommandEnumConstraintType.byId(buffer.readByte()));

        return new CommandEnumConstraintData(
                enumValues.get(valueIndex),
                enums.get(enumIndex),
                constraints
        );
    }

    /**
     * Return true if the item id has a blockingTicks attached.
     * Only a shield should return true
     *
     * @param id ID of item
     * @param session BedrockSession which holds correct blockingId
     * @return true if reading/writing blockingTicks
     */
    public boolean isBlockingItem(int id, BedrockSession session) {
        int blockingId = session.getHardcodedBlockingId().get();
        return id == blockingId;
    }

    /**
     * In case of identifier being different in any version,
     * helper can be used to return correct identifier.
     * @return item identifier of shield.
     */
    public String getBlockingItemIdentifier() {
        return "minecraft:shield";
    }

    public void readExperiments(BitInput buffer, List<ExperimentData> experiments) {
        throw new UnsupportedOperationException();
    }

    protected void registerStackActionRequestTypes() {
        throw new UnsupportedOperationException();
    }

    public StackRequestActionType getStackRequestActionTypeFromId(int id) {
        throw new UnsupportedOperationException();
    }

    public int getIdFromStackRequestActionType(StackRequestActionType type) {
        throw new UnsupportedOperationException();
    }

    public ItemStackRequest readItemStackRequest(BitInput buffer, BedrockSession session) {
        throw new UnsupportedOperationException();
    }

    public <O> O readOptional(BitInput buffer, O emptyValue, Function<ByteBuf, O> function) {
        if (buffer.readBoolean()) {
            return function.apply(buffer);
        }
        return emptyValue;
    }
}
