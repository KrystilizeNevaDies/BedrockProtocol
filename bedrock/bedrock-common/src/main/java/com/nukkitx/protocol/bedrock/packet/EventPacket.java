package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.BlockInteractionType;
import com.nukkitx.protocol.bedrock.data.event.*;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.util.TriConsumer;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;

import static com.nukkitx.protocol.bedrock.data.event.EventDataType.*;
import static com.nukkitx.protocol.bedrock.data.event.EventDataType.FISH_BUCKETED;

public interface EventPacket extends BedrockPacket {
    long uniqueEntityId;
    byte usePlayerId;
    EventData eventData;


    public enum Event {
        ACHIEVEMENT_AWARDED,
        ENTITY_INTERACT,
        PORTAL_BUILT,
        PORTAL_USED,
        MOB_KILLED,
        CAULDRON_USED,
        PLAYER_DEATH,
        BOSS_KILLED,
        AGENT_COMMAND,
        AGENT_CREATED,
        PATTERN_REMOVED,
        SLASH_COMMAND_EXECUTED,
        FISH_BUCKETED,
        MOB_BORN,
        PET_DIED,
        CAULDRON_BLOCK_USED,
        COMPOSTER_BLOCK_USED,
        BELL_BLOCK_USED
    }

    record v291 implements EventPacket {


        protected static final EventDataType[] VALUES = EventDataType.values();

        protected final EnumMap<EventDataType, BiFunction<ByteBuf, BedrockPacketHelper, EventData>> readers = new EnumMap<>(EventDataType.class);
        protected final EnumMap<EventDataType, TriConsumer<ByteBuf, BedrockPacketHelper, EventData>> writers = new EnumMap<>(EventDataType.class);

        protected EventReader_v291() {
            this.readers.put(ACHIEVEMENT_AWARDED, this::readAchievementAwarded);
            this.readers.put(ENTITY_INTERACT, this::readEntityInteract);
            this.readers.put(PORTAL_BUILT, this::readPortalBuilt);
            this.readers.put(PORTAL_USED, this::readPortalUsed);
            this.readers.put(MOB_KILLED, this::readMobKilled);
            this.readers.put(CAULDRON_USED, this::readCauldronUsed);
            this.readers.put(PLAYER_DIED, this::readPlayerDied);
            this.readers.put(BOSS_KILLED, this::readBossKilled);
            this.readers.put(AGENT_COMMAND, this::readAgentCommand);
            this.readers.put(AGENT_CREATED, (b, h) -> AgentCreatedEventData.INSTANCE);
            this.readers.put(PATTERN_REMOVED, this::readPatternRemoved);
            this.readers.put(SLASH_COMMAND_EXECUTED, this::readSlashCommandExecuted);
            this.readers.put(FISH_BUCKETED, this::readFishBucketed);

            this.writers.put(ACHIEVEMENT_AWARDED, this::writeAchievementAwarded);
            this.writers.put(ENTITY_INTERACT, this::writeEntityInteract);
            this.writers.put(PORTAL_BUILT, this::writePortalBuilt);
            this.writers.put(PORTAL_USED, this::writePortalUsed);
            this.writers.put(MOB_KILLED, this::writeMobKilled);
            this.writers.put(CAULDRON_USED, this::writeCauldronUsed);
            this.writers.put(PLAYER_DIED, this::writePlayerDied);
            this.writers.put(BOSS_KILLED, this::writeBossKilled);
            this.writers.put(AGENT_COMMAND, this::writeAgentCommand);
            this.writers.put(AGENT_CREATED, (b, h, e) -> {
            });
            this.writers.put(PATTERN_REMOVED, this::writePatternRemoved);
            this.writers.put(SLASH_COMMAND_EXECUTED, this::writeSlashCommandExecuted);
            this.writers.put(FISH_BUCKETED, this::writeFishBucketed);
        }

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EventPacket packet) {
            VarInts.writeLong(buffer, packet.getUniqueEntityId());
            EventData eventData = packet.getEventData();
            VarInts.writeInt(buffer, eventData.getType().ordinal());
            buffer.writeByte(packet.getUsePlayerId());

            TriConsumer<ByteBuf, BedrockPacketHelper, EventData> function = this.writers.get(eventData.getType());

            if (function == null) {
                throw new UnsupportedOperationException("Unknown event type " + eventData.getType());
            }

            function.accept(buffer, helper, eventData);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EventPacket packet) {
            packet.setUniqueEntityId(VarInts.readLong(buffer));

            int eventId = VarInts.readInt(buffer);
            Preconditions.checkElementIndex(eventId, VALUES.length, "EventDataType");
            EventDataType type = VALUES[eventId];

            packet.setUsePlayerId(buffer.readByte());

            BiFunction<ByteBuf, BedrockPacketHelper, EventData> function = this.readers.get(type);

            if (function == null) {
                throw new UnsupportedOperationException("Unknown event type " + type);
            }

            function.apply(buffer, helper);
        }

        protected AchievementAwardedEventData readAchievementAwarded(ByteBuf buffer, BedrockPacketHelper helper) {
            int achievementId = VarInts.readInt(buffer);
            return new AchievementAwardedEventData(achievementId);
        }

        protected void writeAchievementAwarded(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            AchievementAwardedEventData event = (AchievementAwardedEventData) eventData;
            VarInts.writeInt(buffer, event.getAchievementId());
        }

        protected EntityInteractEventData readEntityInteract(ByteBuf buffer, BedrockPacketHelper helper) {
            int interactionType = VarInts.readInt(buffer);
            int interactionEntityType = VarInts.readInt(buffer);
            int entityVariant = VarInts.readInt(buffer);
            int entityColor = buffer.readUnsignedByte();
            return new EntityInteractEventData(interactionType, interactionEntityType, entityVariant, entityColor);
        }

        protected void writeEntityInteract(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            EntityInteractEventData event = (EntityInteractEventData) eventData;
            VarInts.writeInt(buffer, event.getInteractionType());
            VarInts.writeInt(buffer, event.getLegacyEntityTypeId());
            VarInts.writeInt(buffer, event.getVariant());
            buffer.writeByte(event.getPaletteColor());
        }

        protected PortalBuiltEventData readPortalBuilt(ByteBuf buffer, BedrockPacketHelper helper) {
            int dimensionId = VarInts.readInt(buffer);
            return new PortalBuiltEventData(dimensionId);
        }

        protected void writePortalBuilt(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            PortalBuiltEventData event = (PortalBuiltEventData) eventData;
            VarInts.writeInt(buffer, event.getDimensionId());
        }

        protected PortalUsedEventData readPortalUsed(ByteBuf buffer, BedrockPacketHelper helper) {
            int fromDimensionId = VarInts.readInt(buffer);
            int toDimensionId = VarInts.readInt(buffer);
            return new PortalUsedEventData(fromDimensionId, toDimensionId);
        }

        protected void writePortalUsed(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            PortalUsedEventData event = (PortalUsedEventData) eventData;
            VarInts.writeInt(buffer, event.getFromDimensionId());
            VarInts.writeInt(buffer, event.getToDimensionId());
        }

        protected MobKilledEventData readMobKilled(ByteBuf buffer, BedrockPacketHelper helper) {
            long killerUniqueEntityId = VarInts.readLong(buffer);
            long victimUniqueEntityId = VarInts.readLong(buffer);
            int entityDamageCause = VarInts.readInt(buffer);
            int villagerTradeTier = VarInts.readInt(buffer);
            String villagerDisplayName = helper.readString(buffer);
            return new MobKilledEventData(killerUniqueEntityId, victimUniqueEntityId, -1, entityDamageCause,
                    villagerTradeTier, villagerDisplayName);
        }

        protected void writeMobKilled(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            MobKilledEventData event = (MobKilledEventData) eventData;
            VarInts.writeLong(buffer, event.getKillerUniqueEntityId());
            VarInts.writeLong(buffer, event.getVictimUniqueEntityId());
            VarInts.writeInt(buffer, event.getEntityDamageCause());
            VarInts.writeInt(buffer, event.getVillagerTradeTier());
            helper.writeString(buffer, event.getVillagerDisplayName());
        }

        protected CauldronUsedEventData readCauldronUsed(ByteBuf buffer, BedrockPacketHelper helper) {
            int potionId = VarInts.readInt(buffer);
            int color = VarInts.readInt(buffer);
            int fillLevel = VarInts.readInt(buffer);
            return new CauldronUsedEventData(potionId, color, fillLevel);
        }

        protected void writeCauldronUsed(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            CauldronUsedEventData event = (CauldronUsedEventData) eventData;
            VarInts.writeUnsignedInt(buffer, event.getPotionId());
            VarInts.writeInt(buffer, event.getColor());
            VarInts.writeInt(buffer, event.getFillLevel());
        }

        protected PlayerDiedEventData readPlayerDied(ByteBuf buffer, BedrockPacketHelper helper) {
            int attackerEntityId = VarInts.readInt(buffer);
            int entityDamageCause = VarInts.readInt(buffer);
            return new PlayerDiedEventData(attackerEntityId, -1, entityDamageCause, false);
        }

        protected void writePlayerDied(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            PlayerDiedEventData event = (PlayerDiedEventData) eventData;
            VarInts.writeInt(buffer, event.getAttackerEntityId());
            VarInts.writeInt(buffer, event.getEntityDamageCause());
        }

        protected BossKilledEventData readBossKilled(ByteBuf buffer, BedrockPacketHelper helper) {
            long bossUniqueEntityId = VarInts.readLong(buffer);
            int playerPartySize = VarInts.readInt(buffer);
            int interactionEntityType = VarInts.readInt(buffer);
            return new BossKilledEventData(bossUniqueEntityId, playerPartySize, interactionEntityType);
        }

        protected void writeBossKilled(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            BossKilledEventData event = (BossKilledEventData) eventData;
            VarInts.writeLong(buffer, event.getBossUniqueEntityId());
            VarInts.writeInt(buffer, event.getPlayerPartySize());
            VarInts.writeInt(buffer, event.getBossEntityType());
        }

        protected AgentCommandEventData readAgentCommand(ByteBuf buffer, BedrockPacketHelper helper) {
            int agentResult = VarInts.readInt(buffer);
            int dataValue = VarInts.readInt(buffer);
            String command = helper.readString(buffer);
            String dataKey = helper.readString(buffer);
            String output = helper.readString(buffer);
            return new AgentCommandEventData(agentResult, command, dataKey, dataValue, output);
        }

        protected void writeAgentCommand(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            AgentCommandEventData event = (AgentCommandEventData) eventData;
            VarInts.writeInt(buffer, event.getAgentResult());
            VarInts.writeInt(buffer, event.getDataValue());
            helper.writeString(buffer, event.getCommand());
            helper.writeString(buffer, event.getDataKey());
            helper.writeString(buffer, event.getOutput());
        }

        protected PatternRemovedEventData readPatternRemoved(ByteBuf buffer, BedrockPacketHelper helper) {
            int itemId = VarInts.readInt(buffer);
            int auxValue = VarInts.readInt(buffer);
            int patternsSize = VarInts.readInt(buffer);
            int patternIndex = VarInts.readInt(buffer);
            int patternColor = VarInts.readInt(buffer);
            return new PatternRemovedEventData(itemId, auxValue, patternsSize, patternIndex, patternColor);
        }

        protected void writePatternRemoved(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            PatternRemovedEventData event = (PatternRemovedEventData) eventData;
            VarInts.writeInt(buffer, event.getItemId());
            VarInts.writeInt(buffer, event.getAuxValue());
            VarInts.writeInt(buffer, event.getPatternsSize());
            VarInts.writeInt(buffer, event.getPatternIndex());
            VarInts.writeInt(buffer, event.getPatternColor());
        }

        protected SlashCommandExecutedEventData readSlashCommandExecuted(ByteBuf buffer, BedrockPacketHelper helper) {
            int successCount = VarInts.readInt(buffer);
            VarInts.readInt(buffer);
            String commandName = helper.readString(buffer);
            List<String> outputMessages = Arrays.asList(helper.readString(buffer).split(";"));
            return new SlashCommandExecutedEventData(commandName, successCount, outputMessages);
        }

        protected void writeSlashCommandExecuted(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            SlashCommandExecutedEventData event = (SlashCommandExecutedEventData) eventData;
            VarInts.writeInt(buffer, event.getSuccessCount());
            List<String> outputMessages = event.getOutputMessages();
            VarInts.writeInt(buffer, outputMessages.size());
            helper.writeString(buffer, event.getCommandName());
            helper.writeString(buffer, String.join(";", outputMessages));
        }

        protected FishBucketedEventData readFishBucketed(ByteBuf buffer, BedrockPacketHelper helper) {
            int pattern = VarInts.readInt(buffer);
            int preset = VarInts.readInt(buffer);
            int bucketedEntityType = VarInts.readInt(buffer);
            boolean isRelease = buffer.readBoolean();
            return new FishBucketedEventData(pattern, preset, bucketedEntityType, isRelease);
        }

        protected void writeFishBucketed(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            FishBucketedEventData event = (FishBucketedEventData) eventData;
            VarInts.writeInt(buffer, event.getPattern());
            VarInts.writeInt(buffer, event.getPreset());
            VarInts.writeInt(buffer, event.getBucketedEntityType());
            buffer.writeBoolean(event.isReleaseEvent());
        }
    }

    record v332 extends EventReader_v291 {


        protected EventReader_v332() {
            super();
            this.readers.put(MOB_BORN, this::readMobBorn);
            this.readers.put(PET_DIED, this::readPetDied);
            this.writers.put(MOB_BORN, this::writeMobBorn);
            this.writers.put(PET_DIED, this::writePetDied);
        }

        protected MobBornEventData readMobBorn(ByteBuf buffer, BedrockPacketHelper helper) {
            int entityType = VarInts.readInt(buffer);
            int variant = VarInts.readInt(buffer);
            int color = buffer.readUnsignedByte();
            return new MobBornEventData(entityType, variant, color);
        }

        protected void writeMobBorn(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            MobBornEventData event = (MobBornEventData) eventData;
            VarInts.writeInt(buffer, event.getEntityType());
            VarInts.writeInt(buffer, event.getVariant());
            buffer.writeByte(event.getColor());
        }

        protected PetDiedEventData readPetDied(ByteBuf buffer, BedrockPacketHelper helper) {
            boolean killedByOwner = buffer.readBoolean();
            long killerUniqueEntityId = VarInts.readLong(buffer);
            long petUniqueEntityId = VarInts.readLong(buffer);
            int entityDamageCause = VarInts.readInt(buffer);
            return new PetDiedEventData(killedByOwner, killerUniqueEntityId, petUniqueEntityId, entityDamageCause, -1);
        }

        protected void writePetDied(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            PetDiedEventData event = (PetDiedEventData) eventData;
            buffer.writeBoolean(event.isOwnerKilled());
            VarInts.writeLong(buffer, event.getKillerUniqueEntityId());
            VarInts.writeLong(buffer, event.getPetUniqueEntityId());
            VarInts.writeInt(buffer, event.getEntityDamageCause());
        }
    }

    record v340 extends EventReader_v332 {


        @Override
        protected PetDiedEventData readPetDied(ByteBuf buffer, BedrockPacketHelper helper) {
            boolean killedByOwner = buffer.readBoolean();
            long killerUniqueEntityId = VarInts.readLong(buffer);
            long petUniqueEntityId = VarInts.readLong(buffer);
            int entityDamageCause = VarInts.readInt(buffer);
            int petEntityType = VarInts.readInt(buffer);

            return new PetDiedEventData(killedByOwner, killerUniqueEntityId, petUniqueEntityId, entityDamageCause, petEntityType);
        }

        @Override
        protected void writePetDied(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            super.writePetDied(buffer, helper, eventData);

            VarInts.writeInt(buffer, ((PetDiedEventData) eventData).getPetEntityType());
        }
    }

    record v354 extends EventReader_v340 {


        protected EventReader_v354() {
            super();
            this.readers.put(CAULDRON_INTERACT, this::readCauldronInteract);
            this.readers.put(COMPOSTER_INTERACT, this::readComposterInteract);
            this.readers.put(BELL_USED, this::readBellUsed);
            this.writers.put(CAULDRON_INTERACT, this::writeCauldronInteract);
            this.writers.put(COMPOSTER_INTERACT, this::writeComposterInteract);
            this.writers.put(BELL_USED, this::writeBellUsed);
        }

        protected CauldronInteractEventData readCauldronInteract(ByteBuf buffer, BedrockPacketHelper helper) {
            BlockInteractionType type = BlockInteractionType.values()[VarInts.readInt(buffer)];
            int itemId = VarInts.readInt(buffer);
            return new CauldronInteractEventData(type, itemId);
        }

        protected void writeCauldronInteract(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            CauldronInteractEventData event = (CauldronInteractEventData) eventData;
            VarInts.writeInt(buffer, event.getBlockInteractionType().ordinal());
            VarInts.writeInt(buffer, event.getItemId());
        }

        protected ComposterInteractEventData readComposterInteract(ByteBuf buffer, BedrockPacketHelper helper) {
            BlockInteractionType type = BlockInteractionType.values()[VarInts.readInt(buffer)];
            int itemId = VarInts.readInt(buffer);
            return new ComposterInteractEventData(type, itemId);
        }

        protected void writeComposterInteract(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            ComposterInteractEventData event = (ComposterInteractEventData) eventData;
            VarInts.writeInt(buffer, event.getBlockInteractionType().ordinal());
            VarInts.writeInt(buffer, event.getItemId());
        }

        protected BellUsedEventData readBellUsed(ByteBuf buffer, BedrockPacketHelper helper) {
            int itemId = VarInts.readInt(buffer);
            return new BellUsedEventData(itemId);
        }

        protected void writeBellUsed(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            BellUsedEventData event = (BellUsedEventData) eventData;
            VarInts.writeInt(buffer, event.getItemId());
        }
    }

    record v388 extends EventReader_v354 {


        protected EventReader_v388() {
            super();
            this.readers.put(ENTITY_DEFINITION_TRIGGER, this::readEntityDefinitionTrigger);
            this.readers.put(RAID_UPDATE, this::readRaidUpdate);
            this.readers.put(MOVEMENT_ANOMALY, this::readMovementAnomaly);
            this.readers.put(MOVEMENT_CORRECTED, this::readMovementCorrected);
            this.writers.put(ENTITY_DEFINITION_TRIGGER, this::writeEntityDefinitionTrigger);
            this.writers.put(RAID_UPDATE, this::writeRaidUpdate);
            this.writers.put(MOVEMENT_ANOMALY, this::writeMovementAnomaly);
            this.writers.put(MOVEMENT_CORRECTED, this::writeMovementCorrected);
        }

        @Override
        protected MobKilledEventData readMobKilled(ByteBuf buffer, BedrockPacketHelper helper) {
            long killerUniqueEntityId = VarInts.readLong(buffer);
            long victimUniqueEntityId = VarInts.readLong(buffer);
            int killerEntityType = VarInts.readInt(buffer);
            int entityDamageCause = VarInts.readInt(buffer);
            int villagerTradeTier = VarInts.readInt(buffer);
            String villagerDisplayName = helper.readString(buffer);
            return new MobKilledEventData(killerUniqueEntityId, victimUniqueEntityId, killerEntityType, entityDamageCause,
                    villagerTradeTier, villagerDisplayName);
        }

        @Override
        protected void writeMobKilled(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            MobKilledEventData event = (MobKilledEventData) eventData;
            VarInts.writeLong(buffer, event.getKillerUniqueEntityId());
            VarInts.writeLong(buffer, event.getVictimUniqueEntityId());
            VarInts.writeInt(buffer, event.getKillerEntityType());
            VarInts.writeInt(buffer, event.getEntityDamageCause());
            VarInts.writeInt(buffer, event.getVillagerTradeTier());
            helper.writeString(buffer, event.getVillagerDisplayName());
        }

        protected EntityDefinitionTriggerEventData readEntityDefinitionTrigger(ByteBuf buffer, BedrockPacketHelper helper) {
            String eventName = helper.readString(buffer);
            return new EntityDefinitionTriggerEventData(eventName);
        }

        protected void writeEntityDefinitionTrigger(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            EntityDefinitionTriggerEventData event = (EntityDefinitionTriggerEventData) eventData;
            helper.writeString(buffer, event.getEventName());
        }

        protected RaidUpdateEventData readRaidUpdate(ByteBuf buffer, BedrockPacketHelper helper) {
            int currentRaidWave = VarInts.readInt(buffer);
            int totalRaidWaves = VarInts.readInt(buffer);
            boolean wonRaid = buffer.readBoolean();
            return new RaidUpdateEventData(currentRaidWave, totalRaidWaves, wonRaid);
        }

        protected void writeRaidUpdate(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            RaidUpdateEventData event = (RaidUpdateEventData) eventData;
            VarInts.writeInt(buffer, event.getCurrentWave());
            VarInts.writeInt(buffer, event.getTotalWaves());
            buffer.writeBoolean(event.isWinner());
        }

        protected MovementAnomalyEventData readMovementAnomaly(ByteBuf buffer, BedrockPacketHelper helper) {
            byte eventType = buffer.readByte();
            float cheatingScore = buffer.readFloatLE();
            float averagePositionDelta = buffer.readFloatLE();
            float totalPositionDelta = buffer.readFloatLE();
            float minPositionDelta = buffer.readFloatLE();
            float maxPositionDelta = buffer.readFloatLE();
            return new MovementAnomalyEventData(eventType, cheatingScore, averagePositionDelta, totalPositionDelta,
                    minPositionDelta, maxPositionDelta);
        }

        protected void writeMovementAnomaly(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            MovementAnomalyEventData event = (MovementAnomalyEventData) eventData;
            buffer.writeByte(event.getEventType());
            buffer.writeFloatLE(event.getCheatingScore());
            buffer.writeFloatLE(event.getAveragePositionDelta());
            buffer.writeFloatLE(event.getTotalPositionDelta());
            buffer.writeFloatLE(event.getMinPositionDelta());
            buffer.writeFloatLE(event.getMaxPositionDelta());
        }

        protected MovementCorrectedEventData readMovementCorrected(ByteBuf buffer, BedrockPacketHelper helper) {
            float positionDelta = buffer.readFloatLE();
            float cheatingScore = buffer.readFloatLE();
            float scoreThreshold = buffer.readFloatLE();
            float distanceThreshold = buffer.readFloatLE();
            int durationThreshold = VarInts.readInt(buffer);
            return new MovementCorrectedEventData(positionDelta, cheatingScore, scoreThreshold, distanceThreshold,
                    durationThreshold);
        }

        protected void writeMovementCorrected(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            MovementCorrectedEventData event = (MovementCorrectedEventData) eventData;
            buffer.writeFloatLE(event.getPositionDelta());
            buffer.writeFloatLE(event.getCheatingScore());
            buffer.writeFloatLE(event.getScoreThreshold());
            buffer.writeFloatLE(event.getDistanceThreshold());
            VarInts.writeInt(buffer, event.getDurationThreshold());
        }
    }

    record v389 extends EventReader_v388 {


        protected EventReader_v389() {
            super();
            this.readers.put(EXTRACT_HONEY, (b, h) -> ExtractHoneyEventData.INSTANCE);
            this.writers.put(EXTRACT_HONEY, (b, h, e) -> {
            });
        }
    }

    record v471 extends EventReader_v389 {


        protected EventReader_v471() {
            super();
            this.readers.put(EventDataType.TARGET_BLOCK_HIT, this::readBlockHit);
            this.writers.put(EventDataType.TARGET_BLOCK_HIT, this::writeBlockHit);
            this.readers.put(EventDataType.PIGLIN_BARTER, this::readPiglinBarter);
            this.writers.put(EventDataType.PIGLIN_BARTER, this::writePiglinBarter);
            this.readers.put(EventDataType.COPPER_WAXED_OR_UNWAXED, this::readCopperWaxedUnwaxed);
            this.writers.put(EventDataType.COPPER_WAXED_OR_UNWAXED, this::writeCopperWaxedUnwaxed);
            this.readers.put(EventDataType.CODE_BUILDER_ACTION, this::readCodeBuilderAction);
            this.writers.put(EventDataType.CODE_BUILDER_ACTION, this::writeCodeBuilderAction);
            this.readers.put(EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD, (b, h) -> StriderRiddenInLavaInOverworldEventData.INSTANCE);
            this.writers.put(EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD, (b, h, e) -> {
            });
            this.readers.put(EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR, (b, h) -> SneakCloseToSculkSensorEventData.INSTANCE);
            this.writers.put(EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR, (b, h, e) -> {
            });
        }

        protected TargetBlockHitEventData readBlockHit(ByteBuf buffer, BedrockPacketHelper helper) {
            int redstoneLevel = VarInts.readInt(buffer);
            return new TargetBlockHitEventData(redstoneLevel);
        }

        protected void writeBlockHit(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            TargetBlockHitEventData event = (TargetBlockHitEventData) eventData;
            VarInts.writeInt(buffer, event.getRedstoneLevel());
        }

        protected PiglinBarterEventData readPiglinBarter(ByteBuf buffer, BedrockPacketHelper helper) {
            int runtimeId = VarInts.readInt(buffer);
            boolean targetingPlayer = buffer.readBoolean();
            return new PiglinBarterEventData(runtimeId, targetingPlayer);
        }

        protected void writePiglinBarter(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            PiglinBarterEventData event = (PiglinBarterEventData) eventData;
            VarInts.writeInt(buffer, event.getItemId());
            buffer.writeBoolean(event.isTargetingPlayer());
        }

        protected CopperWaxedOrUnwaxedEventData readCopperWaxedUnwaxed(ByteBuf buffer, BedrockPacketHelper helper) {
            int runtimeId = VarInts.readInt(buffer);
            return new CopperWaxedOrUnwaxedEventData(runtimeId);
        }

        protected void writeCopperWaxedUnwaxed(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            CopperWaxedOrUnwaxedEventData event = (CopperWaxedOrUnwaxedEventData) eventData;
            VarInts.writeInt(buffer, event.getBlockRuntimeId());
        }

        protected CodeBuilderActionEventData readCodeBuilderAction(ByteBuf buffer, BedrockPacketHelper helper) {
            String action = helper.readString(buffer);
            return new CodeBuilderActionEventData(action);
        }

        protected void writeCodeBuilderAction(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            CodeBuilderActionEventData event = (CodeBuilderActionEventData) eventData;
            helper.writeString(buffer, event.getAction());
        }

        protected CodeBuilderScoreboardEventData readCodeBuilderScoreboard(ByteBuf buffer, BedrockPacketHelper helper) {
            String objectiveName = helper.readString(buffer);
            int score = VarInts.readInt(buffer);
            return new CodeBuilderScoreboardEventData(objectiveName, score);
        }

        protected void writeCodeBuilderScoreboard(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
            CodeBuilderScoreboardEventData event = (CodeBuilderScoreboardEventData) eventData;
            helper.writeString(buffer, event.getObjectiveName());
            VarInts.writeInt(buffer, event.getScore());
        }
    }


}
