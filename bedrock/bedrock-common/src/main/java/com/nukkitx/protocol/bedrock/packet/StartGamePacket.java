package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.*;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.*;

import java.util.List;
import java.util.UUID;

public interface StartGamePacket extends BedrockPacket {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(StartGamePacket.class);

    private final List<GameRuleData<?>> gamerules = new ObjectArrayList<>();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private GameType playerGameType;
    private Vector3f playerPosition;
    private Vector2f rotation;
    // Level settings start
    private long seed;
    private SpawnBiomeType spawnBiomeType = SpawnBiomeType.DEFAULT;
    private String customBiomeName = "";
    private int dimensionId;
    private int generatorId;
    private GameType levelGameType;
    private int difficulty;
    private Vector3i defaultSpawn;
    private boolean achievementsDisabled;
    private int dayCycleStopTime;
    private int eduEditionOffers;
    private boolean eduFeaturesEnabled;
    private String educationProductionId = "";
    private float rainLevel;
    private float lightningLevel;
    private boolean platformLockedContentConfirmed;
    private boolean multiplayerGame;
    private boolean broadcastingToLan;
    private GamePublishSetting xblBroadcastMode;
    private GamePublishSetting platformBroadcastMode;
    private boolean commandsEnabled;
    private boolean texturePacksRequired;
    private final List<ExperimentData> experiments = new ObjectArrayList<>();
    private boolean experimentsPreviouslyToggled;
    private boolean bonusChestEnabled;
    private boolean startingWithMap;
    private boolean trustingPlayers;
    private PlayerPermission defaultPlayerPermission;
    private int serverChunkTickRange;
    private boolean behaviorPackLocked;
    private boolean resourcePackLocked;
    private boolean fromLockedWorldTemplate;
    private boolean usingMsaGamertagsOnly;
    private boolean fromWorldTemplate;
    private boolean worldTemplateOptionLocked;
    private boolean onlySpawningV1Villagers;
    private String vanillaVersion;
    private int limitedWorldWidth;
    private int limitedWorldHeight;
    private boolean netherType;
    /**
     * @since v465
     */
    private EduSharedUriResource eduSharedUriResource = EduSharedUriResource.EMPTY;
    private boolean forceExperimentalGameplay;
    // Level settings end
    private String levelId;
    private String levelName;
    private String premiumWorldTemplateId;
    private boolean trial;
    /**
     * @deprecated as of v428
     */
    private AuthoritativeMovementMode authoritativeMovementMode;
    /**
     * @since v428
     */
    private SyncedPlayerMovementSettings playerMovementSettings;
    private long currentTick;
    private int enchantmentSeed;
    private NbtList<NbtMap> blockPalette;
    private final List<BlockPropertyData> blockProperties = new ObjectArrayList<>();
    private List<ItemEntry> itemEntries = new ObjectArrayList<>();
    private String multiplayerCorrelationId;
    /**
     * @since v407
     */
    private boolean inventoriesServerAuthoritative;
    /**
     * The name of the server software.
     * Used for telemetry within the Bedrock client.
     *
     * @since v440
     */
    private String serverEngine;
    /**
     * @since v475
     */
    private long blockRegistryChecksum;

    /**
     * @since v526
     */
    private Object playerPropertyData;
    private UUID worldTemplateId;


    @Deprecated
    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Value
    @AllArgsConstructor
    public static class ItemEntry {
        private final String identifier;
        private final short id;
        private final boolean componentBased;

        public ItemEntry(String identifier, short id) {
            this.identifier = identifier;
            this.id = id;
            this.componentBased = false;
        }
    }

    public class StartGameReader_v291 implements BedrockPacketReader<StartGamePacket> {
        public static final StartGameReader_v291 INSTANCE = new StartGameReader_v291();

        protected static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
            helper.writeVector3f(buffer, packet.getPlayerPosition());
            helper.writeVector2f(buffer, packet.getRotation());

            this.writeLevelSettings(buffer, helper, packet);

            helper.writeString(buffer, packet.getLevelId());
            helper.writeString(buffer, packet.getLevelName());
            helper.writeString(buffer, packet.getPremiumWorldTemplateId());
            buffer.writeBoolean(packet.isTrial());
            buffer.writeLongLE(packet.getCurrentTick());
            VarInts.writeInt(buffer, packet.getEnchantmentSeed());

            NbtList<NbtMap> palette = packet.getBlockPalette();
            VarInts.writeUnsignedInt(buffer, palette.size());
            for (NbtMap entry : palette) {
                NbtMap blockTag = entry.getCompound("block");
                helper.writeString(buffer, blockTag.getString("name"));
                buffer.writeShortLE(entry.getShort("meta"));
            }

            helper.writeString(buffer, packet.getMultiplayerCorrelationId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setPlayerPosition(helper.readVector3f(buffer));
            packet.setRotation(helper.readVector2f(buffer));

            this.readLevelSettings(buffer, helper, packet);

            packet.setLevelId(helper.readString(buffer));
            packet.setLevelName(helper.readString(buffer));
            packet.setPremiumWorldTemplateId(helper.readString(buffer));
            packet.setTrial(buffer.readBoolean());
            packet.setCurrentTick(buffer.readLongLE());
            packet.setEnchantmentSeed(VarInts.readInt(buffer));

            int paletteLength = VarInts.readUnsignedInt(buffer);
            List<NbtMap> palette = new ObjectArrayList<>(paletteLength);
            for (int i = 0; i < paletteLength; i++) {
                palette.add(NbtMap.builder()
                        .putCompound("block", NbtMap.builder()
                                .putString("name", helper.readString(buffer))
                                .build())
                        .putShort("meta", buffer.readShortLE())
                        .build());
            }
            packet.setBlockPalette(new NbtList<>(NbtType.COMPOUND, palette));

            packet.setMultiplayerCorrelationId(helper.readString(buffer));
        }

        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeInt(buffer, (int) packet.getSeed());
            VarInts.writeInt(buffer, packet.getDimensionId());
            VarInts.writeInt(buffer, packet.getGeneratorId());
            VarInts.writeInt(buffer, packet.getLevelGameType().ordinal());
            VarInts.writeInt(buffer, packet.getDifficulty());
            helper.writeBlockPosition(buffer, packet.getDefaultSpawn());
            buffer.writeBoolean(packet.isAchievementsDisabled());
            VarInts.writeInt(buffer, packet.getDayCycleStopTime());
            buffer.writeBoolean(packet.getEduEditionOffers() != 0); // Is Education world
            buffer.writeBoolean(packet.isEduFeaturesEnabled());
            buffer.writeFloatLE(packet.getRainLevel());
            buffer.writeFloatLE(packet.getLightningLevel());
            buffer.writeBoolean(packet.isMultiplayerGame());
            buffer.writeBoolean(packet.isBroadcastingToLan());
            buffer.writeBoolean(packet.getXblBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
            buffer.writeBoolean(packet.isCommandsEnabled());
            buffer.writeBoolean(packet.isTexturePacksRequired());
            helper.writeArray(buffer, packet.getGamerules(), helper::writeGameRule);
            buffer.writeBoolean(packet.isBonusChestEnabled());
            buffer.writeBoolean(packet.isStartingWithMap());
            buffer.writeBoolean(packet.isTrustingPlayers());
            VarInts.writeInt(buffer, packet.getDefaultPlayerPermission().ordinal());
            VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
            buffer.writeIntLE(packet.getServerChunkTickRange());
            buffer.writeBoolean(packet.getPlatformBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
            VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
            buffer.writeBoolean(packet.getXblBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
            buffer.writeBoolean(packet.isBehaviorPackLocked());
            buffer.writeBoolean(packet.isResourcePackLocked());
            buffer.writeBoolean(packet.isFromLockedWorldTemplate());
            buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
        }

        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            packet.setSeed(VarInts.readInt(buffer));
            packet.setDimensionId(VarInts.readInt(buffer));
            packet.setGeneratorId(VarInts.readInt(buffer));
            packet.setLevelGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setDifficulty(VarInts.readInt(buffer));
            packet.setDefaultSpawn(helper.readBlockPosition(buffer));
            packet.setAchievementsDisabled(buffer.readBoolean());
            packet.setDayCycleStopTime(VarInts.readInt(buffer));
            packet.setEduEditionOffers(buffer.readBoolean() ? 1 : 0); // Is Education world
            packet.setEduFeaturesEnabled(buffer.readBoolean());
            packet.setRainLevel(buffer.readFloatLE());
            packet.setLightningLevel(buffer.readFloatLE());
            packet.setMultiplayerGame(buffer.readBoolean());
            packet.setBroadcastingToLan(buffer.readBoolean());
            buffer.readBoolean(); // broadcasting to XBL
            packet.setCommandsEnabled(buffer.readBoolean());
            packet.setTexturePacksRequired(buffer.readBoolean());
            helper.readArray(buffer, packet.getGamerules(), helper::readGameRule);
            packet.setBonusChestEnabled(buffer.readBoolean());
            packet.setStartingWithMap(buffer.readBoolean());
            packet.setTrustingPlayers(buffer.readBoolean());
            packet.setDefaultPlayerPermission(PLAYER_PERMISSIONS[VarInts.readInt(buffer)]);
            packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setServerChunkTickRange(buffer.readIntLE());
            buffer.readBoolean(); // Broadcasting to Platform
            packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            buffer.readBoolean(); // Intent on XBL broadcast
            packet.setBehaviorPackLocked(buffer.readBoolean());
            packet.setResourcePackLocked(buffer.readBoolean());
            packet.setFromLockedWorldTemplate(buffer.readBoolean());
            packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
        }
    }

    public class StartGameReader_v313 extends StartGameReader_v291 {
        public static final StartGameReader_v313 INSTANCE = new StartGameReader_v313();

        @Override
        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.writeLevelSettings(buffer, helper, packet);
            buffer.writeBoolean(packet.isFromWorldTemplate());
            buffer.writeBoolean(packet.isFromLockedWorldTemplate());
        }

        @Override
        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.readLevelSettings(buffer, helper, packet);

            packet.setFromWorldTemplate(buffer.readBoolean());
            packet.setFromLockedWorldTemplate(buffer.readBoolean());
        }
    }

    public class StartGameReader_v332 extends StartGameReader_v291 { // No need to extend last version
        public static final StartGameReader_v332 INSTANCE = new StartGameReader_v332();

        @Override
        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeInt(buffer, (int) packet.getSeed());
            VarInts.writeInt(buffer, packet.getDimensionId());
            VarInts.writeInt(buffer, packet.getGeneratorId());
            VarInts.writeInt(buffer, packet.getLevelGameType().ordinal());
            VarInts.writeInt(buffer, packet.getDifficulty());
            helper.writeBlockPosition(buffer, packet.getDefaultSpawn());
            buffer.writeBoolean(packet.isAchievementsDisabled());
            VarInts.writeInt(buffer, packet.getDayCycleStopTime());
            buffer.writeBoolean(packet.getEduEditionOffers() != 0);
            buffer.writeBoolean(packet.isEduFeaturesEnabled());
            buffer.writeFloatLE(packet.getRainLevel());
            buffer.writeFloatLE(packet.getLightningLevel());
            buffer.writeBoolean(packet.isPlatformLockedContentConfirmed());
            buffer.writeBoolean(packet.isMultiplayerGame());
            buffer.writeBoolean(packet.isBroadcastingToLan());
            VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
            VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
            buffer.writeBoolean(packet.isCommandsEnabled());
            buffer.writeBoolean(packet.isTexturePacksRequired());
            helper.writeArray(buffer, packet.getGamerules(), helper::writeGameRule);
            buffer.writeBoolean(packet.isBonusChestEnabled());
            buffer.writeBoolean(packet.isStartingWithMap());
            VarInts.writeInt(buffer, packet.getDefaultPlayerPermission().ordinal());
            buffer.writeIntLE(packet.getServerChunkTickRange());
            buffer.writeBoolean(packet.isBehaviorPackLocked());
            buffer.writeBoolean(packet.isResourcePackLocked());
            buffer.writeBoolean(packet.isFromLockedWorldTemplate());
            buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
            buffer.writeBoolean(packet.isFromWorldTemplate());
            buffer.writeBoolean(packet.isWorldTemplateOptionLocked());
        }

        @Override
        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            packet.setSeed(VarInts.readInt(buffer));
            packet.setDimensionId(VarInts.readInt(buffer));
            packet.setGeneratorId(VarInts.readInt(buffer));
            packet.setLevelGameType(GameType.values()[VarInts.readInt(buffer)]);
            packet.setDifficulty(VarInts.readInt(buffer));
            packet.setDefaultSpawn(helper.readBlockPosition(buffer));
            packet.setAchievementsDisabled(buffer.readBoolean());
            packet.setDayCycleStopTime(VarInts.readInt(buffer));
            packet.setEduEditionOffers(buffer.readBoolean() ? 1 : 0);
            packet.setEduFeaturesEnabled(buffer.readBoolean());
            packet.setRainLevel(buffer.readFloatLE());
            packet.setLightningLevel(buffer.readFloatLE());
            packet.setPlatformLockedContentConfirmed(buffer.readBoolean());
            packet.setMultiplayerGame(buffer.readBoolean());
            packet.setBroadcastingToLan(buffer.readBoolean());
            packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setCommandsEnabled(buffer.readBoolean());
            packet.setTexturePacksRequired(buffer.readBoolean());
            helper.readArray(buffer, packet.getGamerules(), helper::readGameRule);
            packet.setBonusChestEnabled(buffer.readBoolean());
            packet.setStartingWithMap(buffer.readBoolean());
            packet.setDefaultPlayerPermission(PLAYER_PERMISSIONS[VarInts.readInt(buffer)]);
            packet.setServerChunkTickRange(buffer.readIntLE());
            packet.setBehaviorPackLocked(buffer.readBoolean());
            packet.setResourcePackLocked(buffer.readBoolean());
            packet.setFromLockedWorldTemplate(buffer.readBoolean());
            packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
            packet.setFromWorldTemplate(buffer.readBoolean());
            packet.setWorldTemplateOptionLocked(buffer.readBoolean());
        }
    }

    public class StartGameReader_v361 extends StartGameReader_v332 {
        public static final StartGameReader_v361 INSTANCE = new StartGameReader_v361();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
            helper.writeVector3f(buffer, packet.getPlayerPosition());
            helper.writeVector2f(buffer, packet.getRotation());

            this.writeLevelSettings(buffer, helper, packet);

            helper.writeString(buffer, packet.getLevelId());
            helper.writeString(buffer, packet.getLevelName());
            helper.writeString(buffer, packet.getPremiumWorldTemplateId());
            buffer.writeBoolean(packet.isTrial());
            buffer.writeLongLE(packet.getCurrentTick());
            VarInts.writeInt(buffer, packet.getEnchantmentSeed());

            List<NbtMap> palette = packet.getBlockPalette();
            VarInts.writeUnsignedInt(buffer, palette.size());
            for (NbtMap entry : palette) {
                NbtMap blockTag = entry.getCompound("block");
                helper.writeString(buffer, blockTag.getString("name"));
                buffer.writeShortLE(entry.getShort("meta"));
                buffer.writeShortLE(entry.getShort("id"));
            }

            helper.writeArray(buffer, packet.getItemEntries(), (buf, entry) -> {
                helper.writeString(buf, entry.getIdentifier());
                buf.writeShortLE(entry.getId());
            });

            helper.writeString(buffer, packet.getMultiplayerCorrelationId());

        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setPlayerPosition(helper.readVector3f(buffer));
            packet.setRotation(helper.readVector2f(buffer));

            this.readLevelSettings(buffer, helper, packet);

            packet.setLevelId(helper.readString(buffer));
            packet.setLevelName(helper.readString(buffer));
            packet.setPremiumWorldTemplateId(helper.readString(buffer));
            packet.setTrial(buffer.readBoolean());
            packet.setCurrentTick(buffer.readLongLE());
            packet.setEnchantmentSeed(VarInts.readInt(buffer));

            int paletteLength = VarInts.readUnsignedInt(buffer);
            List<NbtMap> palette = new ObjectArrayList<>(paletteLength);
            for (int i = 0; i < paletteLength; i++) {
                palette.add(NbtMap.builder()
                        .putCompound("block", NbtMap.builder()
                                .putString("name", helper.readString(buffer))
                                .build())
                        .putShort("meta", buffer.readShortLE())
                        .putShort("id", buffer.readShortLE())
                        .build());
            }
            packet.setBlockPalette(new NbtList<>(NbtType.COMPOUND, palette));

            helper.readArray(buffer, packet.getItemEntries(), session, (buf, packetHelper, aSession) -> {
                String identifier = packetHelper.readString(buf);
                short id = buf.readShortLE();
                if (identifier.equals(packetHelper.getBlockingItemIdentifier())) {
                    aSession.getHardcodedBlockingId().set(id);
                }
                return new StartGamePacket.ItemEntry(identifier, id);
            });

            packet.setMultiplayerCorrelationId(helper.readString(buffer));
        }

        @Override
        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.readLevelSettings(buffer, helper, packet);

            packet.setOnlySpawningV1Villagers(buffer.readBoolean());
        }

        @Override
        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.writeLevelSettings(buffer, helper, packet);

            buffer.writeBoolean(packet.isOnlySpawningV1Villagers());
        }
    }

    public class StartGameReader_v388 extends StartGameReader_v361 {
        public static final StartGameReader_v388 INSTANCE = new StartGameReader_v388();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
            helper.writeVector3f(buffer, packet.getPlayerPosition());
            helper.writeVector2f(buffer, packet.getRotation());

            this.writeLevelSettings(buffer, helper, packet);

            helper.writeString(buffer, packet.getLevelId());
            helper.writeString(buffer, packet.getLevelName());
            helper.writeString(buffer, packet.getPremiumWorldTemplateId());
            buffer.writeBoolean(packet.isTrial());
            buffer.writeBoolean(packet.getAuthoritativeMovementMode() != AuthoritativeMovementMode.CLIENT);
            buffer.writeLongLE(packet.getCurrentTick());
            VarInts.writeInt(buffer, packet.getEnchantmentSeed());

            // cache palette for fast writing
            helper.writeTag(buffer, packet.getBlockPalette());

            helper.writeArray(buffer, packet.getItemEntries(), (buf, h, entry) -> {
                h.writeString(buf, entry.getIdentifier());
                buf.writeShortLE(entry.getId());
            });

            helper.writeString(buffer, packet.getMultiplayerCorrelationId());

        }

        @SuppressWarnings("unchecked")
        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setPlayerPosition(helper.readVector3f(buffer));
            packet.setRotation(helper.readVector2f(buffer));

            this.readLevelSettings(buffer, helper, packet);

            packet.setLevelId(helper.readString(buffer));
            packet.setLevelName(helper.readString(buffer));
            packet.setPremiumWorldTemplateId(helper.readString(buffer));
            packet.setTrial(buffer.readBoolean());
            packet.setAuthoritativeMovementMode(buffer.readBoolean() ? AuthoritativeMovementMode.SERVER : AuthoritativeMovementMode.CLIENT);
            packet.setCurrentTick(buffer.readLongLE());
            packet.setEnchantmentSeed(VarInts.readInt(buffer));

            packet.setBlockPalette(helper.readTag(buffer));

            helper.readArray(buffer, packet.getItemEntries(), session, (buf, packetHelper, aSession) -> {
                String identifier = packetHelper.readString(buf);
                short id = buf.readShortLE();
                if (identifier.equals(packetHelper.getBlockingItemIdentifier())) {
                    aSession.getHardcodedBlockingId().set(id);
                }
                return new StartGamePacket.ItemEntry(identifier, id);
            });

            packet.setMultiplayerCorrelationId(helper.readString(buffer));
        }

        @Override
        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.readLevelSettings(buffer, helper, packet);

            packet.setVanillaVersion(helper.readString(buffer));
        }

        @Override
        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.writeLevelSettings(buffer, helper, packet);

            helper.writeString(buffer, packet.getVanillaVersion());
        }
    }

    public class StartGameReader_v407 extends StartGameReader_v388 {

        public static final StartGameReader_v407 INSTANCE = new StartGameReader_v407();

        private static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.serialize(buffer, helper, packet);

            buffer.writeBoolean(packet.isInventoriesServerAuthoritative());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setInventoriesServerAuthoritative(buffer.readBoolean());
        }

        @Override
        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeInt(buffer, (int) packet.getSeed());
            buffer.writeShortLE(packet.getSpawnBiomeType().ordinal());
            helper.writeString(buffer, packet.getCustomBiomeName());
            VarInts.writeInt(buffer, packet.getDimensionId());
            VarInts.writeInt(buffer, packet.getGeneratorId());
            VarInts.writeInt(buffer, packet.getLevelGameType().ordinal());
            VarInts.writeInt(buffer, packet.getDifficulty());
            helper.writeBlockPosition(buffer, packet.getDefaultSpawn());
            buffer.writeBoolean(packet.isAchievementsDisabled());
            VarInts.writeInt(buffer, packet.getDayCycleStopTime());
            VarInts.writeInt(buffer, packet.getEduEditionOffers());
            buffer.writeBoolean(packet.isEduFeaturesEnabled());
            helper.writeString(buffer, packet.getEducationProductionId());
            buffer.writeFloatLE(packet.getRainLevel());
            buffer.writeFloatLE(packet.getLightningLevel());
            buffer.writeBoolean(packet.isPlatformLockedContentConfirmed());
            buffer.writeBoolean(packet.isMultiplayerGame());
            buffer.writeBoolean(packet.isBroadcastingToLan());
            VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
            VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
            buffer.writeBoolean(packet.isCommandsEnabled());
            buffer.writeBoolean(packet.isTexturePacksRequired());
            helper.writeArray(buffer, packet.getGamerules(), helper::writeGameRule);
            buffer.writeBoolean(packet.isBonusChestEnabled());
            buffer.writeBoolean(packet.isStartingWithMap());
            VarInts.writeInt(buffer, packet.getDefaultPlayerPermission().ordinal());
            buffer.writeIntLE(packet.getServerChunkTickRange());
            buffer.writeBoolean(packet.isBehaviorPackLocked());
            buffer.writeBoolean(packet.isResourcePackLocked());
            buffer.writeBoolean(packet.isFromLockedWorldTemplate());
            buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
            buffer.writeBoolean(packet.isFromWorldTemplate());
            buffer.writeBoolean(packet.isWorldTemplateOptionLocked());
            buffer.writeBoolean(packet.isOnlySpawningV1Villagers());
            helper.writeString(buffer, packet.getVanillaVersion());
            buffer.writeIntLE(packet.getLimitedWorldWidth());
            buffer.writeIntLE(packet.getLimitedWorldHeight());
            buffer.writeBoolean(packet.isNetherType());
            buffer.writeBoolean(packet.isForceExperimentalGameplay());
        }

        @Override
        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            packet.setSeed(VarInts.readInt(buffer));
            packet.setSpawnBiomeType(SpawnBiomeType.byId(buffer.readShortLE()));
            packet.setCustomBiomeName(helper.readString(buffer));
            packet.setDimensionId(VarInts.readInt(buffer));
            packet.setGeneratorId(VarInts.readInt(buffer));
            packet.setLevelGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setDifficulty(VarInts.readInt(buffer));
            packet.setDefaultSpawn(helper.readBlockPosition(buffer));
            packet.setAchievementsDisabled(buffer.readBoolean());
            packet.setDayCycleStopTime(VarInts.readInt(buffer));
            packet.setEduEditionOffers(VarInts.readInt(buffer));
            packet.setEduFeaturesEnabled(buffer.readBoolean());
            packet.setEducationProductionId(helper.readString(buffer));
            packet.setRainLevel(buffer.readFloatLE());
            packet.setLightningLevel(buffer.readFloatLE());
            packet.setPlatformLockedContentConfirmed(buffer.readBoolean());
            packet.setMultiplayerGame(buffer.readBoolean());
            packet.setBroadcastingToLan(buffer.readBoolean());
            packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setCommandsEnabled(buffer.readBoolean());
            packet.setTexturePacksRequired(buffer.readBoolean());
            helper.readArray(buffer, packet.getGamerules(), helper::readGameRule);
            packet.setBonusChestEnabled(buffer.readBoolean());
            packet.setStartingWithMap(buffer.readBoolean());
            packet.setDefaultPlayerPermission(PLAYER_PERMISSIONS[VarInts.readInt(buffer)]);
            packet.setServerChunkTickRange(buffer.readIntLE());
            packet.setBehaviorPackLocked(buffer.readBoolean());
            packet.setResourcePackLocked(buffer.readBoolean());
            packet.setFromLockedWorldTemplate(buffer.readBoolean());
            packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
            packet.setFromWorldTemplate(buffer.readBoolean());
            packet.setWorldTemplateOptionLocked(buffer.readBoolean());
            packet.setOnlySpawningV1Villagers(buffer.readBoolean());
            packet.setVanillaVersion(helper.readString(buffer));
            packet.setLimitedWorldWidth(buffer.readIntLE());
            packet.setLimitedWorldHeight(buffer.readIntLE());
            packet.setNetherType(buffer.readBoolean());
            packet.setForceExperimentalGameplay(buffer.readBoolean());
        }
    }

    public class StartGameReader_v419 implements BedrockPacketReader<StartGamePacket> {

        public static final StartGameReader_v419 INSTANCE = new StartGameReader_v419();

        protected static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();
        protected static final AuthoritativeMovementMode[] MOVEMENT_MODES = AuthoritativeMovementMode.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
            helper.writeVector3f(buffer, packet.getPlayerPosition());
            helper.writeVector2f(buffer, packet.getRotation());

            this.writeLevelSettings(buffer, helper, packet);

            helper.writeString(buffer, packet.getLevelId());
            helper.writeString(buffer, packet.getLevelName());
            helper.writeString(buffer, packet.getPremiumWorldTemplateId());
            buffer.writeBoolean(packet.isTrial());
            VarInts.writeUnsignedInt(buffer, packet.getAuthoritativeMovementMode().ordinal());
            buffer.writeLongLE(packet.getCurrentTick());
            VarInts.writeInt(buffer, packet.getEnchantmentSeed());

            helper.writeArray(buffer, packet.getBlockProperties(), (buf, packetHelper, block) -> {
                packetHelper.writeString(buf, block.getName());
                packetHelper.writeTag(buf, block.getProperties());
            });

            helper.writeArray(buffer, packet.getItemEntries(), (buf, packetHelper, entry) -> {
                packetHelper.writeString(buf, entry.getIdentifier());
                buf.writeShortLE(entry.getId());
                buf.writeBoolean(entry.isComponentBased());
            });

            helper.writeString(buffer, packet.getMultiplayerCorrelationId());
            buffer.writeBoolean(packet.isInventoriesServerAuthoritative());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setPlayerPosition(helper.readVector3f(buffer));
            packet.setRotation(helper.readVector2f(buffer));

            this.readLevelSettings(buffer, helper, packet);

            packet.setLevelId(helper.readString(buffer));
            packet.setLevelName(helper.readString(buffer));
            packet.setPremiumWorldTemplateId(helper.readString(buffer));
            packet.setTrial(buffer.readBoolean());
            packet.setAuthoritativeMovementMode(MOVEMENT_MODES[VarInts.readUnsignedInt(buffer)]);
            packet.setCurrentTick(buffer.readLongLE());
            packet.setEnchantmentSeed(VarInts.readInt(buffer));

            helper.readArray(buffer, packet.getBlockProperties(), (buf, packetHelper) -> {
                String name = packetHelper.readString(buf);
                NbtMap properties = packetHelper.readTag(buf);
                return new BlockPropertyData(name, properties);
            });

            helper.readArray(buffer, packet.getItemEntries(), session, (buf, packetHelper, aSession) -> {
                String identifier = packetHelper.readString(buf);
                short id = buf.readShortLE();
                boolean componentBased = buf.readBoolean();
                if (identifier.equals(packetHelper.getBlockingItemIdentifier())) {
                    aSession.getHardcodedBlockingId().set(id);
                }
                return new StartGamePacket.ItemEntry(identifier, id, componentBased);
            });

            packet.setMultiplayerCorrelationId(helper.readString(buffer));
            packet.setInventoriesServerAuthoritative(buffer.readBoolean());
        }

        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            writeSeed(buffer, packet.getSeed());
            buffer.writeShortLE(packet.getSpawnBiomeType().ordinal());
            helper.writeString(buffer, packet.getCustomBiomeName());
            VarInts.writeInt(buffer, packet.getDimensionId());
            VarInts.writeInt(buffer, packet.getGeneratorId());
            VarInts.writeInt(buffer, packet.getLevelGameType().ordinal());
            VarInts.writeInt(buffer, packet.getDifficulty());
            helper.writeBlockPosition(buffer, packet.getDefaultSpawn());
            buffer.writeBoolean(packet.isAchievementsDisabled());
            VarInts.writeInt(buffer, packet.getDayCycleStopTime());
            VarInts.writeInt(buffer, packet.getEduEditionOffers());
            buffer.writeBoolean(packet.isEduFeaturesEnabled());
            helper.writeString(buffer, packet.getEducationProductionId());
            buffer.writeFloatLE(packet.getRainLevel());
            buffer.writeFloatLE(packet.getLightningLevel());
            buffer.writeBoolean(packet.isPlatformLockedContentConfirmed());
            buffer.writeBoolean(packet.isMultiplayerGame());
            buffer.writeBoolean(packet.isBroadcastingToLan());
            VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
            VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
            buffer.writeBoolean(packet.isCommandsEnabled());
            buffer.writeBoolean(packet.isTexturePacksRequired());
            helper.writeArray(buffer, packet.getGamerules(), helper::writeGameRule);
            helper.writeExperiments(buffer, packet.getExperiments());
            buffer.writeBoolean(packet.isExperimentsPreviouslyToggled());
            buffer.writeBoolean(packet.isBonusChestEnabled());
            buffer.writeBoolean(packet.isStartingWithMap());
            VarInts.writeInt(buffer, packet.getDefaultPlayerPermission().ordinal());
            buffer.writeIntLE(packet.getServerChunkTickRange());
            buffer.writeBoolean(packet.isBehaviorPackLocked());
            buffer.writeBoolean(packet.isResourcePackLocked());
            buffer.writeBoolean(packet.isFromLockedWorldTemplate());
            buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
            buffer.writeBoolean(packet.isFromWorldTemplate());
            buffer.writeBoolean(packet.isWorldTemplateOptionLocked());
            buffer.writeBoolean(packet.isOnlySpawningV1Villagers());
            helper.writeString(buffer, packet.getVanillaVersion());
            buffer.writeIntLE(packet.getLimitedWorldWidth());
            buffer.writeIntLE(packet.getLimitedWorldHeight());
            buffer.writeBoolean(packet.isNetherType());
            buffer.writeBoolean(packet.isForceExperimentalGameplay());
            if (packet.isForceExperimentalGameplay()) {
                buffer.writeBoolean(true); // optional boolean
            }
        }

        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            packet.setSeed(readSeed(buffer));
            packet.setSpawnBiomeType(SpawnBiomeType.byId(buffer.readShortLE()));
            packet.setCustomBiomeName(helper.readString(buffer));
            packet.setDimensionId(VarInts.readInt(buffer));
            packet.setGeneratorId(VarInts.readInt(buffer));
            packet.setLevelGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setDifficulty(VarInts.readInt(buffer));
            packet.setDefaultSpawn(helper.readBlockPosition(buffer));
            packet.setAchievementsDisabled(buffer.readBoolean());
            packet.setDayCycleStopTime(VarInts.readInt(buffer));
            packet.setEduEditionOffers(VarInts.readInt(buffer));
            packet.setEduFeaturesEnabled(buffer.readBoolean());
            packet.setEducationProductionId(helper.readString(buffer));
            packet.setRainLevel(buffer.readFloatLE());
            packet.setLightningLevel(buffer.readFloatLE());
            packet.setPlatformLockedContentConfirmed(buffer.readBoolean());
            packet.setMultiplayerGame(buffer.readBoolean());
            packet.setBroadcastingToLan(buffer.readBoolean());
            packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setCommandsEnabled(buffer.readBoolean());
            packet.setTexturePacksRequired(buffer.readBoolean());
            helper.readArray(buffer, packet.getGamerules(), helper::readGameRule);
            helper.readExperiments(buffer, packet.getExperiments());
            packet.setExperimentsPreviouslyToggled(buffer.readBoolean());
            packet.setBonusChestEnabled(buffer.readBoolean());
            packet.setStartingWithMap(buffer.readBoolean());
            packet.setDefaultPlayerPermission(PLAYER_PERMISSIONS[VarInts.readInt(buffer)]);
            packet.setServerChunkTickRange(buffer.readIntLE());
            packet.setBehaviorPackLocked(buffer.readBoolean());
            packet.setResourcePackLocked(buffer.readBoolean());
            packet.setFromLockedWorldTemplate(buffer.readBoolean());
            packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
            packet.setFromWorldTemplate(buffer.readBoolean());
            packet.setWorldTemplateOptionLocked(buffer.readBoolean());
            packet.setOnlySpawningV1Villagers(buffer.readBoolean());
            packet.setVanillaVersion(helper.readString(buffer));
            packet.setLimitedWorldWidth(buffer.readIntLE());
            packet.setLimitedWorldHeight(buffer.readIntLE());
            packet.setNetherType(buffer.readBoolean());
            if (buffer.readBoolean()) { // optional boolean
                packet.setForceExperimentalGameplay(buffer.readBoolean());
            }
        }

        protected long readSeed(ByteBuf buffer) {
            return VarInts.readInt(buffer);
        }

        protected void writeSeed(ByteBuf buffer, long seed) {
            VarInts.writeInt(buffer, (int) seed);
        }
    }

    public class StartGameReader_v428 extends StartGameReader_v419 {

        public static final StartGameReader_v428 INSTANCE = new StartGameReader_v428();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
            helper.writeVector3f(buffer, packet.getPlayerPosition());
            helper.writeVector2f(buffer, packet.getRotation());

            this.writeLevelSettings(buffer, helper, packet);

            helper.writeString(buffer, packet.getLevelId());
            helper.writeString(buffer, packet.getLevelName());
            helper.writeString(buffer, packet.getPremiumWorldTemplateId());
            buffer.writeBoolean(packet.isTrial());
            writeSyncedPlayerMovementSettings(buffer, helper, packet.getPlayerMovementSettings()); // new for v428
            buffer.writeLongLE(packet.getCurrentTick());
            VarInts.writeInt(buffer, packet.getEnchantmentSeed());

            helper.writeArray(buffer, packet.getBlockProperties(), (buf, packetHelper, block) -> {
                packetHelper.writeString(buf, block.getName());
                packetHelper.writeTag(buf, block.getProperties());
            });

            helper.writeArray(buffer, packet.getItemEntries(), (buf, packetHelper, entry) -> {
                packetHelper.writeString(buf, entry.getIdentifier());
                buf.writeShortLE(entry.getId());
                buf.writeBoolean(entry.isComponentBased());
            });

            helper.writeString(buffer, packet.getMultiplayerCorrelationId());
            buffer.writeBoolean(packet.isInventoriesServerAuthoritative());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setPlayerPosition(helper.readVector3f(buffer));
            packet.setRotation(helper.readVector2f(buffer));

            this.readLevelSettings(buffer, helper, packet);

            packet.setLevelId(helper.readString(buffer));
            packet.setLevelName(helper.readString(buffer));
            packet.setPremiumWorldTemplateId(helper.readString(buffer));
            packet.setTrial(buffer.readBoolean());
            packet.setPlayerMovementSettings(readSyncedPlayerMovementSettings(buffer, helper)); // new for v428
            packet.setCurrentTick(buffer.readLongLE());
            packet.setEnchantmentSeed(VarInts.readInt(buffer));

            helper.readArray(buffer, packet.getBlockProperties(), (buf, packetHelper) -> {
                String name = packetHelper.readString(buf);
                NbtMap properties = packetHelper.readTag(buf);
                return new BlockPropertyData(name, properties);
            });

            helper.readArray(buffer, packet.getItemEntries(), session, (buf, packetHelper, aSession) -> {
                String identifier = packetHelper.readString(buf);
                short id = buf.readShortLE();
                boolean componentBased = buf.readBoolean();
                if (identifier.equals(packetHelper.getBlockingItemIdentifier())) {
                    aSession.getHardcodedBlockingId().set(id);
                }
                return new StartGamePacket.ItemEntry(identifier, id, componentBased);
            });

            packet.setMultiplayerCorrelationId(helper.readString(buffer));
            packet.setInventoriesServerAuthoritative(buffer.readBoolean());
        }

        protected void writeSyncedPlayerMovementSettings(ByteBuf buffer, BedrockPacketHelper helper, SyncedPlayerMovementSettings playerMovementSettings) {
            VarInts.writeInt(buffer, playerMovementSettings.getMovementMode().ordinal());
            VarInts.writeInt(buffer, playerMovementSettings.getRewindHistorySize());
            buffer.writeBoolean(playerMovementSettings.isServerAuthoritativeBlockBreaking());
        }

        protected SyncedPlayerMovementSettings readSyncedPlayerMovementSettings(ByteBuf buffer, BedrockPacketHelper helper) {
            SyncedPlayerMovementSettings playerMovementSettings = new SyncedPlayerMovementSettings();
            playerMovementSettings.setMovementMode(MOVEMENT_MODES[VarInts.readInt(buffer)]);
            playerMovementSettings.setRewindHistorySize(VarInts.readInt(buffer));
            playerMovementSettings.setServerAuthoritativeBlockBreaking(buffer.readBoolean());
            return playerMovementSettings;
        }

    }

    public class StartGameReader_v440 extends StartGameReader_v428 {

        public static final StartGameReader_v440 INSTANCE = new StartGameReader_v440();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.serialize(buffer, helper, packet);
            helper.writeString(buffer, packet.getServerEngine());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            super.deserialize(buffer, helper, packet, session);
            packet.setServerEngine(helper.readString(buffer));
        }
    }

    public class StartGameReader_v465 extends StartGameReader_v440 {

        public static final StartGameReader_v465 INSTANCE = new StartGameReader_v465();

        @Override
        protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            writeSeed(buffer, packet.getSeed());
            buffer.writeShortLE(packet.getSpawnBiomeType().ordinal());
            helper.writeString(buffer, packet.getCustomBiomeName());
            VarInts.writeInt(buffer, packet.getDimensionId());
            VarInts.writeInt(buffer, packet.getGeneratorId());
            VarInts.writeInt(buffer, packet.getLevelGameType().ordinal());
            VarInts.writeInt(buffer, packet.getDifficulty());
            helper.writeBlockPosition(buffer, packet.getDefaultSpawn());
            buffer.writeBoolean(packet.isAchievementsDisabled());
            VarInts.writeInt(buffer, packet.getDayCycleStopTime());
            VarInts.writeInt(buffer, packet.getEduEditionOffers());
            buffer.writeBoolean(packet.isEduFeaturesEnabled());
            helper.writeString(buffer, packet.getEducationProductionId());
            buffer.writeFloatLE(packet.getRainLevel());
            buffer.writeFloatLE(packet.getLightningLevel());
            buffer.writeBoolean(packet.isPlatformLockedContentConfirmed());
            buffer.writeBoolean(packet.isMultiplayerGame());
            buffer.writeBoolean(packet.isBroadcastingToLan());
            VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
            VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
            buffer.writeBoolean(packet.isCommandsEnabled());
            buffer.writeBoolean(packet.isTexturePacksRequired());
            helper.writeArray(buffer, packet.getGamerules(), helper::writeGameRule);
            helper.writeExperiments(buffer, packet.getExperiments());
            buffer.writeBoolean(packet.isExperimentsPreviouslyToggled());
            buffer.writeBoolean(packet.isBonusChestEnabled());
            buffer.writeBoolean(packet.isStartingWithMap());
            VarInts.writeInt(buffer, packet.getDefaultPlayerPermission().ordinal());
            buffer.writeIntLE(packet.getServerChunkTickRange());
            buffer.writeBoolean(packet.isBehaviorPackLocked());
            buffer.writeBoolean(packet.isResourcePackLocked());
            buffer.writeBoolean(packet.isFromLockedWorldTemplate());
            buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
            buffer.writeBoolean(packet.isFromWorldTemplate());
            buffer.writeBoolean(packet.isWorldTemplateOptionLocked());
            buffer.writeBoolean(packet.isOnlySpawningV1Villagers());
            helper.writeString(buffer, packet.getVanillaVersion());
            buffer.writeIntLE(packet.getLimitedWorldWidth());
            buffer.writeIntLE(packet.getLimitedWorldHeight());
            buffer.writeBoolean(packet.isNetherType());
            helper.writeString(buffer, packet.getEduSharedUriResource().getButtonName());
            helper.writeString(buffer, packet.getEduSharedUriResource().getLinkUri());
            buffer.writeBoolean(packet.isForceExperimentalGameplay());
            if (packet.isForceExperimentalGameplay()) {
                buffer.writeBoolean(true); // optional boolean
            }
        }

        @Override
        protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            packet.setSeed(readSeed(buffer));
            packet.setSpawnBiomeType(SpawnBiomeType.byId(buffer.readShortLE()));
            packet.setCustomBiomeName(helper.readString(buffer));
            packet.setDimensionId(VarInts.readInt(buffer));
            packet.setGeneratorId(VarInts.readInt(buffer));
            packet.setLevelGameType(GameType.from(VarInts.readInt(buffer)));
            packet.setDifficulty(VarInts.readInt(buffer));
            packet.setDefaultSpawn(helper.readBlockPosition(buffer));
            packet.setAchievementsDisabled(buffer.readBoolean());
            packet.setDayCycleStopTime(VarInts.readInt(buffer));
            packet.setEduEditionOffers(VarInts.readInt(buffer));
            packet.setEduFeaturesEnabled(buffer.readBoolean());
            packet.setEducationProductionId(helper.readString(buffer));
            packet.setRainLevel(buffer.readFloatLE());
            packet.setLightningLevel(buffer.readFloatLE());
            packet.setPlatformLockedContentConfirmed(buffer.readBoolean());
            packet.setMultiplayerGame(buffer.readBoolean());
            packet.setBroadcastingToLan(buffer.readBoolean());
            packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
            packet.setCommandsEnabled(buffer.readBoolean());
            packet.setTexturePacksRequired(buffer.readBoolean());
            helper.readArray(buffer, packet.getGamerules(), helper::readGameRule);
            helper.readExperiments(buffer, packet.getExperiments());
            packet.setExperimentsPreviouslyToggled(buffer.readBoolean());
            packet.setBonusChestEnabled(buffer.readBoolean());
            packet.setStartingWithMap(buffer.readBoolean());
            packet.setDefaultPlayerPermission(PLAYER_PERMISSIONS[VarInts.readInt(buffer)]);
            packet.setServerChunkTickRange(buffer.readIntLE());
            packet.setBehaviorPackLocked(buffer.readBoolean());
            packet.setResourcePackLocked(buffer.readBoolean());
            packet.setFromLockedWorldTemplate(buffer.readBoolean());
            packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
            packet.setFromWorldTemplate(buffer.readBoolean());
            packet.setWorldTemplateOptionLocked(buffer.readBoolean());
            packet.setOnlySpawningV1Villagers(buffer.readBoolean());
            packet.setVanillaVersion(helper.readString(buffer));
            packet.setLimitedWorldWidth(buffer.readIntLE());
            packet.setLimitedWorldHeight(buffer.readIntLE());
            packet.setNetherType(buffer.readBoolean());
            packet.setEduSharedUriResource(new EduSharedUriResource(helper.readString(buffer), helper.readString(buffer)));
            if (buffer.readBoolean()) { // optional boolean
                packet.setForceExperimentalGameplay(buffer.readBoolean());
            }
        }
    }

    public class StartGameReader_v475 extends StartGameReader_v465 {

        public static final StartGameReader_v475 INSTANCE = new StartGameReader_v475();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
            super.serialize(buffer, helper, packet);

            buffer.writeLongLE(packet.getBlockRegistryChecksum());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            super.deserialize(buffer, helper, packet, session);

            packet.setBlockRegistryChecksum(buffer.readLongLE());
        }
    }


    public class StartGameReader_v503 extends StartGameReader_v475 {
        public static final StartGameReader_v503 INSTANCE = new StartGameReader_v503();

        @Override
        protected long readSeed(ByteBuf buf) {
            return buf.readLongLE();
        }

        @Override
        protected void writeSeed(ByteBuf buf, long seed) {
            buf.writeLongLE(seed);
        }
    }

    public class StartGameReaderBeta extends StartGameReader_v465 {
        public static final StartGameReaderBeta INSTANCE = new StartGameReaderBeta();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            super.serialize(buffer, helper, packet, session);

            helper.writeTag(buffer, packet.getPlayerPropertyData());
            buffer.writeLongLE(packet.getBlockRegistryChecksum());
            helper.writeUuid(buffer, packet.getWorldTemplateId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
            super.deserialize(buffer, helper, packet, session);

            packet.setPlayerPropertyData(helper.readTag(buffer));
            packet.setBlockRegistryChecksum(buffer.readLongLE());
            packet.setWorldTemplateId(helper.readUuid(buffer));
        }

        @Override
        protected long readSeed(ByteBuf buf) {
            return buf.readLongLE();
        }

        @Override
        protected void writeSeed(ByteBuf buf, long seed) {
            buf.writeLongLE(seed);
        }
    }

}
