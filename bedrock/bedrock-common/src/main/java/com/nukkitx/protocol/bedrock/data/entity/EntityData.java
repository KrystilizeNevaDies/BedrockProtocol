package com.nukkitx.protocol.bedrock.data.entity;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

public interface EntityData {

    interface Type<E extends EntityData> {
        @NotNull E read(@NotNull BitInput input) throws IOException;

        void write(@NotNull E value, @NotNull BitOutput output) throws IOException;
    }

    record Flags(boolean... flags) implements EntityData {
    }

    @NotNull EntityData.Type<Flags> FLAGS = create(ValueType.FLAGS, Flags::new, Flags::flags);

    record Health(int health) implements EntityData {
    }

    @NotNull EntityData.Type<Health> HEALTH = create(ValueType.INT, Health::new, Health::health);

    record Variant(int variant) implements EntityData {
    }

    @NotNull EntityData.Type<Variant> VARIANT = create(ValueType.INT, Variant::new, Variant::variant);

    record Color(byte color) implements EntityData {
    }

    @NotNull EntityData.Type<Color> COLOR = create(ValueType.BYTE, Color::new, Color::color);

    record Nametag(String nametag) implements EntityData {
    }

    @NotNull EntityData.Type<Nametag> NAMETAG = create(ValueType.STRING, Nametag::new, Nametag::nametag);

    record OwnerEid(long ownerEid) implements EntityData {
    }

    @NotNull EntityData.Type<OwnerEid> OWNER_EID = create(ValueType.LONG, OwnerEid::new, OwnerEid::ownerEid);

    record TargetEid(long targetEid) implements EntityData {
    }

    @NotNull EntityData.Type<TargetEid> TARGET_EID = create(ValueType.LONG, TargetEid::new, TargetEid::targetEid);

    record AirSupply(short airSupply) implements EntityData {
    }

    @NotNull EntityData.Type<AirSupply> AIR_SUPPLY = create(ValueType.SHORT, AirSupply::new, AirSupply::airSupply);

    record EffectColor(int effectColor) implements EntityData {
    }

    @NotNull EntityData.Type<EffectColor> EFFECT_COLOR = create(ValueType.INT, EffectColor::new, EffectColor::effectColor);

    record EffectAmbient(byte effectAmbient) implements EntityData {
    }

    @NotNull EntityData.Type<EffectAmbient> EFFECT_AMBIENT = create(ValueType.BYTE, EffectAmbient::new, EffectAmbient::effectAmbient);

    record JumpDuration(byte jumpDuration) implements EntityData {
    }

    @NotNull EntityData.Type<JumpDuration> JUMP_DURATION = create(ValueType.BYTE, JumpDuration::new, JumpDuration::jumpDuration);

    record HurtTime(int hurtTime) implements EntityData {
    }

    @NotNull EntityData.Type<HurtTime> HURT_TIME = create(ValueType.INT, HurtTime::new, HurtTime::hurtTime);

    record HurtDirection(int hurtDirection) implements EntityData {
    }

    @NotNull EntityData.Type<HurtDirection> HURT_DIRECTION = create(ValueType.INT, HurtDirection::new, HurtDirection::hurtDirection);

    record RowTimeLeft(float rowTimeLeft) implements EntityData {
    }

    @NotNull EntityData.Type<RowTimeLeft> ROW_TIME_LEFT = create(ValueType.FLOAT, RowTimeLeft::new, RowTimeLeft::rowTimeLeft);

    record RowTimeRight(float rowTimeRight) implements EntityData {
    }

    @NotNull EntityData.Type<RowTimeRight> ROW_TIME_RIGHT = create(ValueType.FLOAT, RowTimeRight::new, RowTimeRight::rowTimeRight);

    record ExperienceValue(int experienceValue) implements EntityData {
    }

    @NotNull EntityData.Type<ExperienceValue> EXPERIENCE_VALUE = create(ValueType.INT, ExperienceValue::new, ExperienceValue::experienceValue);

    record DisplayItem(int displayItem) implements EntityData {
    }

    @NotNull EntityData.Type<DisplayItem> DISPLAY_ITEM = create(ValueType.INT, DisplayItem::new, DisplayItem::displayItem);

    record CustomDisplay(byte customDisplay) implements EntityData {
    }

    // DISPLAY_OFFSET(null), // Can be long or int
    record DisplayOffset(long displayOffset) implements EntityData {
    }

    @NotNull EntityData.Type<DisplayOffset> DISPLAY_OFFSET_LONG = create(ValueType.LONG, DisplayOffset::new, DisplayOffset::displayOffset);
    @NotNull EntityData.Type<DisplayOffset> DISPLAY_OFFSET_INT = create(ValueType.INT, offset -> new DisplayOffset((long) offset), offset -> (int) offset.displayOffset());

    @NotNull EntityData.Type<CustomDisplay> CUSTOM_DISPLAY = create(ValueType.BYTE, CustomDisplay::new, CustomDisplay::customDisplay);

    // These values are stored server side
    @Deprecated
    record Swell(int swell) implements EntityData {
    }

    @NotNull EntityData.Type<Swell> SWELL = create(ValueType.INT, Swell::new, Swell::swell);

    @Deprecated
    record OldSwell(int oldSwell) implements EntityData {
    }

    @NotNull EntityData.Type<OldSwell> OLD_SWELL = create(ValueType.INT, OldSwell::new, OldSwell::oldSwell);

    @Deprecated
    record SwellDirection(int swellDirection) implements EntityData {
    }

    @NotNull EntityData.Type<SwellDirection> SWELL_DIRECTION = create(ValueType.INT, SwellDirection::new, SwellDirection::swellDirection);

    record ChargeAmount(byte chargeAmount) implements EntityData {
    }

    @NotNull EntityData.Type<ChargeAmount> CHARGE_AMOUNT = create(ValueType.BYTE, ChargeAmount::new, ChargeAmount::chargeAmount);

    record CarriedBlock(int carriedBlock) implements EntityData {
    }

    @NotNull EntityData.Type<CarriedBlock> CARRIED_BLOCK = create(ValueType.INT, CarriedBlock::new, CarriedBlock::carriedBlock);

    record ClientEvent(byte clientEvent) implements EntityData {
    }

    @NotNull EntityData.Type<ClientEvent> CLIENT_EVENT = create(ValueType.BYTE, ClientEvent::new, ClientEvent::clientEvent);

    record UsingItem(byte usingItem) implements EntityData {
    }

    @NotNull EntityData.Type<UsingItem> USING_ITEM = create(ValueType.BYTE, UsingItem::new, UsingItem::usingItem);

    record PlayerFlags(byte playerFlags) implements EntityData {
    }

    @NotNull EntityData.Type<PlayerFlags> PLAYER_FLAGS = create(ValueType.BYTE, PlayerFlags::new, PlayerFlags::playerFlags);

    record PlayerIndex(int playerIndex) implements EntityData {
    }

    @NotNull EntityData.Type<PlayerIndex> PLAYER_INDEX = create(ValueType.INT, PlayerIndex::new, PlayerIndex::playerIndex);

    record BedPosition(Vector3i bedPosition) implements EntityData {
    }

    @NotNull EntityData.Type<BedPosition> BED_POSITION = create(ValueType.VECTOR3I, BedPosition::new, BedPosition::bedPosition);

    record XPower(float xPower) implements EntityData {
    }

    @NotNull EntityData.Type<XPower> X_POWER = create(ValueType.FLOAT, XPower::new, XPower::xPower);

    record YPower(float yPower) implements EntityData {
    }

    @NotNull EntityData.Type<YPower> Y_POWER = create(ValueType.FLOAT, YPower::new, YPower::yPower);

    record ZPower(float zPower) implements EntityData {
    }

    @NotNull EntityData.Type<ZPower> Z_POWER = create(ValueType.FLOAT, ZPower::new, ZPower::zPower);

    record AUX_POWER() implements EntityData {
    }

    @NotNull EntityData.Type<AUX_POWER> AUX_POWER = create(AUX_POWER::new);

    record FISH_X() implements EntityData {
    }

    @NotNull EntityData.Type<FISH_X> FISH_X = create(FISH_X::new);

    record FISH_Z() implements EntityData {
    }

    @NotNull EntityData.Type<FISH_Z> FISH_Z = create(FISH_Z::new);

    record FISH_ANGLE() implements EntityData {
    }

    @NotNull EntityData.Type<FISH_ANGLE> FISH_ANGLE = create(FISH_ANGLE::new);

    record PotionAuxValue(short potionAuxValue) implements EntityData {
    }

    @NotNull EntityData.Type<PotionAuxValue> POTION_AUX_VALUE = create(ValueType.SHORT, PotionAuxValue::new, PotionAuxValue::potionAuxValue);

    record LeashHolderEid(long leashHolderEid) implements EntityData {
    }

    @NotNull EntityData.Type<LeashHolderEid> LEASH_HOLDER_EID = create(ValueType.LONG, LeashHolderEid::new, LeashHolderEid::leashHolderEid);

    record Scale(float scale) implements EntityData {
    }

    @NotNull EntityData.Type<Scale> SCALE = create(ValueType.FLOAT, Scale::new, Scale::scale);

    record HasNpcComponent(byte hasNpcComponent) implements EntityData {
    }

    @NotNull EntityData.Type<HasNpcComponent> HAS_NPC_COMPONENT = create(ValueType.BYTE, HasNpcComponent::new, HasNpcComponent::hasNpcComponent);

    record SkinId(String skinId) implements EntityData {
    }

    @NotNull EntityData.Type<SkinId> SKIN_ID = create(ValueType.STRING, SkinId::new, SkinId::skinId);

    record NpcSkinId(String npcSkinId) implements EntityData {
    }

    @NotNull EntityData.Type<NpcSkinId> NPC_SKIN_ID = create(ValueType.STRING, NpcSkinId::new, NpcSkinId::npcSkinId);

    record UrlTag(String urlTag) implements EntityData {
    }

    @NotNull EntityData.Type<UrlTag> URL_TAG = create(ValueType.STRING, UrlTag::new, UrlTag::urlTag);

    record MaxAirSupply(short maxAirSupply) implements EntityData {
    }

    @NotNull EntityData.Type<MaxAirSupply> MAX_AIR_SUPPLY = create(ValueType.SHORT, MaxAirSupply::new, MaxAirSupply::maxAirSupply);

    record MarkVariant(int markVariant) implements EntityData {
    }

    @NotNull EntityData.Type<MarkVariant> MARK_VARIANT = create(ValueType.INT, MarkVariant::new, MarkVariant::markVariant);

    record ContainerType(byte containerType) implements EntityData {
    }

    @NotNull EntityData.Type<ContainerType> CONTAINER_TYPE = create(ValueType.BYTE, ContainerType::new, ContainerType::containerType);

    record ContainerBaseSize(int containerBaseSize) implements EntityData {
    }

    @NotNull EntityData.Type<ContainerBaseSize> CONTAINER_BASE_SIZE = create(ValueType.INT, ContainerBaseSize::new, ContainerBaseSize::containerBaseSize);

    record ContainerStrengthModifier(int containerStrengthModifier) implements EntityData {
    }

    @NotNull EntityData.Type<ContainerStrengthModifier> CONTAINER_STRENGTH_MODIFIER = create(ValueType.INT, ContainerStrengthModifier::new, ContainerStrengthModifier::containerStrengthModifier);

    record BlockTarget(Vector3i blockTarget) implements EntityData {
    }

    @NotNull EntityData.Type<BlockTarget> BLOCK_TARGET = create(ValueType.VECTOR3I, BlockTarget::new, BlockTarget::blockTarget);

    record WitherInvulnerableTicks(int witherInvulnerableTicks) implements EntityData {
    }

    @NotNull EntityData.Type<WitherInvulnerableTicks> WITHER_INVULNERABLE_TICKS = create(ValueType.INT, WitherInvulnerableTicks::new, WitherInvulnerableTicks::witherInvulnerableTicks);

    record WitherTarget1(long witherTarget1) implements EntityData {
    }

    @NotNull EntityData.Type<WitherTarget1> WITHER_TARGET_1 = create(ValueType.LONG, WitherTarget1::new, WitherTarget1::witherTarget1);

    record WitherTarget2(long witherTarget2) implements EntityData {
    }

    @NotNull EntityData.Type<WitherTarget2> WITHER_TARGET_2 = create(ValueType.LONG, WitherTarget2::new, WitherTarget2::witherTarget2);

    record WitherTarget3(long witherTarget3) implements EntityData {
    }

    @NotNull EntityData.Type<WitherTarget3> WITHER_TARGET_3 = create(ValueType.LONG, WitherTarget3::new, WitherTarget3::witherTarget3);

    record WitherAerialAttack(short witherAerialAttack) implements EntityData {
    }

    @NotNull EntityData.Type<WitherAerialAttack> WITHER_AERIAL_ATTACK = create(ValueType.SHORT, WitherAerialAttack::new, WitherAerialAttack::witherAerialAttack);

    record BoundingBoxWidth(float boundingBoxWidth) implements EntityData {
    }

    @NotNull EntityData.Type<BoundingBoxWidth> BOUNDING_BOX_WIDTH = create(ValueType.FLOAT, BoundingBoxWidth::new, BoundingBoxWidth::boundingBoxWidth);

    record BoundingBoxHeight(float boundingBoxHeight) implements EntityData {
    }

    @NotNull EntityData.Type<BoundingBoxHeight> BOUNDING_BOX_HEIGHT = create(ValueType.FLOAT, BoundingBoxHeight::new, BoundingBoxHeight::boundingBoxHeight);

    record FuseLength(int fuseLength) implements EntityData {
    }

    @NotNull EntityData.Type<FuseLength> FUSE_LENGTH = create(ValueType.INT, FuseLength::new, FuseLength::fuseLength);

    record RiderSeatPosition(Vector3f riderSeatPosition) implements EntityData {
    }

    @NotNull EntityData.Type<RiderSeatPosition> RIDER_SEAT_POSITION = create(ValueType.VECTOR3F, RiderSeatPosition::new, RiderSeatPosition::riderSeatPosition);

    record RiderRotationLocked(byte riderRotationLocked) implements EntityData {
    }

    @NotNull EntityData.Type<RiderRotationLocked> RIDER_ROTATION_LOCKED = create(ValueType.BYTE, RiderRotationLocked::new, RiderRotationLocked::riderRotationLocked);

    record RiderMaxRotation(float riderMaxRotation) implements EntityData {
    }

    @NotNull EntityData.Type<RiderMaxRotation> RIDER_MAX_ROTATION = create(ValueType.FLOAT, RiderMaxRotation::new, RiderMaxRotation::riderMaxRotation);

    record RiderMinRotation(float riderMinRotation) implements EntityData {
    }

    @NotNull EntityData.Type<RiderMinRotation> RIDER_MIN_ROTATION = create(ValueType.FLOAT, RiderMinRotation::new, RiderMinRotation::riderMinRotation);

    record AreaEffectCloudRadius(float areaEffectCloudRadius) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudRadius> AREA_EFFECT_CLOUD_RADIUS = create(ValueType.FLOAT, AreaEffectCloudRadius::new, AreaEffectCloudRadius::areaEffectCloudRadius);

    record AreaEffectCloudWaiting(int areaEffectCloudWaiting) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudWaiting> AREA_EFFECT_CLOUD_WAITING = create(ValueType.INT, AreaEffectCloudWaiting::new, AreaEffectCloudWaiting::areaEffectCloudWaiting);

    record AreaEffectCloudParticleId(int areaEffectCloudParticleId) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudParticleId> AREA_EFFECT_CLOUD_PARTICLE_ID = create(ValueType.INT, AreaEffectCloudParticleId::new, AreaEffectCloudParticleId::areaEffectCloudParticleId);

    record ShulkerPeakHeight(int shulkerPeakHeight) implements EntityData {
    }

    @NotNull EntityData.Type<ShulkerPeakHeight> SHULKER_PEAK_HEIGHT = create(ValueType.INT, ShulkerPeakHeight::new, ShulkerPeakHeight::shulkerPeakHeight);

    record ShulkerAttachFace(byte shulkerAttachFace) implements EntityData {
    }

    @NotNull EntityData.Type<ShulkerAttachFace> SHULKER_ATTACH_FACE = create(ValueType.BYTE, ShulkerAttachFace::new, ShulkerAttachFace::shulkerAttachFace);

    record ShulkerAttachPos(Vector3i shulkerAttachPos) implements EntityData {
    }

    @NotNull EntityData.Type<ShulkerAttachPos> SHULKER_ATTACH_POS = create(ValueType.VECTOR3I, ShulkerAttachPos::new, ShulkerAttachPos::shulkerAttachPos);

    record TradeTargetEid(long tradeTargetEid) implements EntityData {
    }

    @NotNull EntityData.Type<TradeTargetEid> TRADE_TARGET_EID = create(ValueType.LONG, TradeTargetEid::new, TradeTargetEid::tradeTargetEid);

    record CommandBlockEnabled(byte commandBlockEnabled) implements EntityData {
    }

    @NotNull EntityData.Type<CommandBlockEnabled> COMMAND_BLOCK_ENABLED = create(ValueType.BYTE, CommandBlockEnabled::new, CommandBlockEnabled::commandBlockEnabled);

    record CommandBlockCommand(String commandBlockCommand) implements EntityData {
    }

    @NotNull EntityData.Type<CommandBlockCommand> COMMAND_BLOCK_COMMAND = create(ValueType.STRING, CommandBlockCommand::new, CommandBlockCommand::commandBlockCommand);

    record CommandBlockLastOutput(String commandBlockLastOutput) implements EntityData {
    }

    @NotNull EntityData.Type<CommandBlockLastOutput> COMMAND_BLOCK_LAST_OUTPUT = create(ValueType.STRING, CommandBlockLastOutput::new, CommandBlockLastOutput::commandBlockLastOutput);

    record CommandBlockTrackOutput(byte commandBlockTrackOutput) implements EntityData {
    }

    @NotNull EntityData.Type<CommandBlockTrackOutput> COMMAND_BLOCK_TRACK_OUTPUT = create(ValueType.BYTE, CommandBlockTrackOutput::new, CommandBlockTrackOutput::commandBlockTrackOutput);

    record ControllingRiderSeatIndex(byte controllingRiderSeatIndex) implements EntityData {
    }

    @NotNull EntityData.Type<ControllingRiderSeatIndex> CONTROLLING_RIDER_SEAT_INDEX = create(ValueType.BYTE, ControllingRiderSeatIndex::new, ControllingRiderSeatIndex::controllingRiderSeatIndex);

    record Strength(int strength) implements EntityData {
    }

    @NotNull EntityData.Type<Strength> STRENGTH = create(ValueType.INT, Strength::new, Strength::strength);

    record MaxStrength(int maxStrength) implements EntityData {
    }

    @NotNull EntityData.Type<MaxStrength> MAX_STRENGTH = create(ValueType.INT, MaxStrength::new, MaxStrength::maxStrength);

    record EvokerSpellColor(int evokerSpellColor) implements EntityData {
    }

    @NotNull EntityData.Type<EvokerSpellColor> EVOKER_SPELL_COLOR = create(ValueType.INT, EvokerSpellColor::new, EvokerSpellColor::evokerSpellColor);

    record LimitedLife(int limitedLife) implements EntityData {
    }

    @NotNull EntityData.Type<LimitedLife> LIMITED_LIFE = create(ValueType.INT, LimitedLife::new, LimitedLife::limitedLife);

    record ArmorStandPoseIndex(int armorStandPoseIndex) implements EntityData {
    }

    @NotNull EntityData.Type<ArmorStandPoseIndex> ARMOR_STAND_POSE_INDEX = create(ValueType.INT, ArmorStandPoseIndex::new, ArmorStandPoseIndex::armorStandPoseIndex);

    record EnderCrystalTimeOffset(int enderCrystalTimeOffset) implements EntityData {
    }

    @NotNull EntityData.Type<EnderCrystalTimeOffset> ENDER_CRYSTAL_TIME_OFFSET = create(ValueType.INT, EnderCrystalTimeOffset::new, EnderCrystalTimeOffset::enderCrystalTimeOffset);

    record NametagAlwaysShow(byte nametagAlwaysShow) implements EntityData {
    }

    @NotNull EntityData.Type<NametagAlwaysShow> NAMETAG_ALWAYS_SHOW = create(ValueType.BYTE, NametagAlwaysShow::new, NametagAlwaysShow::nametagAlwaysShow);

    record Color2(byte color2) implements EntityData {
    }

    @NotNull EntityData.Type<Color2> COLOR_2 = create(ValueType.BYTE, Color2::new, Color2::color2);

    record ScoreTag(String scoreTag) implements EntityData {
    }

    @NotNull EntityData.Type<ScoreTag> SCORE_TAG = create(ValueType.STRING, ScoreTag::new, ScoreTag::scoreTag);

    record BalloonAttachedEntity(long balloonAttachedEntity) implements EntityData {
    }

    @NotNull EntityData.Type<BalloonAttachedEntity> BALLOON_ATTACHED_ENTITY = create(ValueType.LONG, BalloonAttachedEntity::new, BalloonAttachedEntity::balloonAttachedEntity);

    record PufferfishSize(byte pufferfishSize) implements EntityData {
    }

    @NotNull EntityData.Type<PufferfishSize> PUFFERFISH_SIZE = create(ValueType.BYTE, PufferfishSize::new, PufferfishSize::pufferfishSize);

    record BoatBubbleTime(int boatBubbleTime) implements EntityData {
    }

    @NotNull EntityData.Type<BoatBubbleTime> BOAT_BUBBLE_TIME = create(ValueType.INT, BoatBubbleTime::new, BoatBubbleTime::boatBubbleTime);

    record AgentId(long agentId) implements EntityData {
    }

    @NotNull EntityData.Type<AgentId> AGENT_ID = create(ValueType.LONG, AgentId::new, AgentId::agentId);

    record EatingCounter(int eatingCounter) implements EntityData {
    }

    @NotNull EntityData.Type<EatingCounter> EATING_COUNTER = create(ValueType.INT, EatingCounter::new, EatingCounter::eatingCounter);

    record Flags2(boolean... flags) implements EntityData {
    }

    @NotNull EntityData.Type<Flags2> FLAGS_2 = create(ValueType.FLAGS, Flags2::new, Flags2::flags);

    record AreaEffectCloudDuration(int areaEffectCloudDuration) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudDuration> AREA_EFFECT_CLOUD_DURATION = create(ValueType.INT, AreaEffectCloudDuration::new, AreaEffectCloudDuration::areaEffectCloudDuration);

    record AreaEffectCloudSpawnTime(int areaEffectCloudSpawnTime) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudSpawnTime> AREA_EFFECT_CLOUD_SPAWN_TIME = create(ValueType.INT, AreaEffectCloudSpawnTime::new, AreaEffectCloudSpawnTime::areaEffectCloudSpawnTime);

    record AreaEffectCloudChangeRate(float areaEffectCloudChangeRate) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudChangeRate> AREA_EFFECT_CLOUD_CHANGE_RATE = create(ValueType.FLOAT, AreaEffectCloudChangeRate::new, AreaEffectCloudChangeRate::areaEffectCloudChangeRate);

    record AreaEffectCloudChangeOnPickup(float areaEffectCloudChangeOnPickup) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudChangeOnPickup> AREA_EFFECT_CLOUD_CHANGE_ON_PICKUP = create(ValueType.FLOAT, AreaEffectCloudChangeOnPickup::new, AreaEffectCloudChangeOnPickup::areaEffectCloudChangeOnPickup);

    record AreaEffectCloudCount(int areaEffectCloudCount) implements EntityData {
    }

    @NotNull EntityData.Type<AreaEffectCloudCount> AREA_EFFECT_CLOUD_COUNT = create(ValueType.INT, AreaEffectCloudCount::new, AreaEffectCloudCount::areaEffectCloudCount);

    record InteractiveTag(String interactiveTag) implements EntityData {
    }

    @NotNull EntityData.Type<InteractiveTag> INTERACTIVE_TAG = create(ValueType.STRING, InteractiveTag::new, InteractiveTag::interactiveTag);

    record TradeTier(int tradeTier) implements EntityData {
    }

    @NotNull EntityData.Type<TradeTier> TRADE_TIER = create(ValueType.INT, TradeTier::new, TradeTier::tradeTier);

    record MaxTradeTier(int maxTradeTier) implements EntityData {
    }

    @NotNull EntityData.Type<MaxTradeTier> MAX_TRADE_TIER = create(ValueType.INT, MaxTradeTier::new, MaxTradeTier::maxTradeTier);

    record TradeXp(int tradeXp) implements EntityData {
    }

    @NotNull EntityData.Type<TradeXp> TRADE_XP = create(ValueType.INT, TradeXp::new, TradeXp::tradeXp);

    record CommandBlockTickDelay(int commandBlockTickDelay) implements EntityData {
    }

    @NotNull EntityData.Type<CommandBlockTickDelay> COMMAND_BLOCK_TICK_DELAY = create(ValueType.INT, CommandBlockTickDelay::new, CommandBlockTickDelay::commandBlockTickDelay);

    record CommandBlockExecuteOnFirstTick(byte commandBlockExecuteOnFirstTick) implements EntityData {
    }

    @NotNull EntityData.Type<CommandBlockExecuteOnFirstTick> COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK = create(ValueType.BYTE, CommandBlockExecuteOnFirstTick::new, CommandBlockExecuteOnFirstTick::commandBlockExecuteOnFirstTick);

    record AmbientSoundInterval(float ambientSoundInterval) implements EntityData {
    }

    @NotNull EntityData.Type<AmbientSoundInterval> AMBIENT_SOUND_INTERVAL = create(ValueType.FLOAT, AmbientSoundInterval::new, AmbientSoundInterval::ambientSoundInterval);

    record AmbientSoundIntervalRange(float ambientSoundIntervalRange) implements EntityData {
    }

    @NotNull EntityData.Type<AmbientSoundIntervalRange> AMBIENT_SOUND_INTERVAL_RANGE = create(ValueType.FLOAT, AmbientSoundIntervalRange::new, AmbientSoundIntervalRange::ambientSoundIntervalRange);

    record AmbientSoundEventName(String ambientSoundEventName) implements EntityData {
    }

    @NotNull EntityData.Type<AmbientSoundEventName> AMBIENT_SOUND_EVENT_NAME = create(ValueType.STRING, AmbientSoundEventName::new, AmbientSoundEventName::ambientSoundEventName);

    record FallDamageMultiplier(float fallDamageMultiplier) implements EntityData {
    }

    @NotNull EntityData.Type<FallDamageMultiplier> FALL_DAMAGE_MULTIPLIER = create(ValueType.FLOAT, FallDamageMultiplier::new, FallDamageMultiplier::fallDamageMultiplier);

    record CanRideTarget(byte canRideTarget) implements EntityData {
    }

    @NotNull EntityData.Type<CanRideTarget> CAN_RIDE_TARGET = create(ValueType.BYTE, CanRideTarget::new, CanRideTarget::canRideTarget);

    record LowTierCuredTradeDiscount(int lowTierCuredTradeDiscount) implements EntityData {
    }

    @NotNull EntityData.Type<LowTierCuredTradeDiscount> LOW_TIER_CURED_TRADE_DISCOUNT = create(ValueType.INT, LowTierCuredTradeDiscount::new, LowTierCuredTradeDiscount::lowTierCuredTradeDiscount);

    record HighTierCuredTradeDiscount(int highTierCuredTradeDiscount) implements EntityData {
    }

    @NotNull EntityData.Type<HighTierCuredTradeDiscount> HIGH_TIER_CURED_TRADE_DISCOUNT = create(ValueType.INT, HighTierCuredTradeDiscount::new, HighTierCuredTradeDiscount::highTierCuredTradeDiscount);

    record NearbyCuredTradeDiscount(int nearbyCuredTradeDiscount) implements EntityData {
    }

    @NotNull EntityData.Type<NearbyCuredTradeDiscount> NEARBY_CURED_TRADE_DISCOUNT = create(ValueType.INT, NearbyCuredTradeDiscount::new, NearbyCuredTradeDiscount::nearbyCuredTradeDiscount);

    record NearbyCuredDiscountTimeStamp(int nearbyCuredDiscountTimeStamp) implements EntityData {
    }

    @NotNull EntityData.Type<NearbyCuredDiscountTimeStamp> NEARBY_CURED_DISCOUNT_TIME_STAMP = create(ValueType.INT, NearbyCuredDiscountTimeStamp::new, NearbyCuredDiscountTimeStamp::nearbyCuredDiscountTimeStamp);

    record Hitbox(NbtMap hitbox) implements EntityData {
    }

    @NotNull EntityData.Type<Hitbox> HITBOX = create(ValueType.NBT, Hitbox::new, Hitbox::hitbox);

    record IsBuoyant(byte isBuoyant) implements EntityData {
    }

    @NotNull EntityData.Type<IsBuoyant> IS_BUOYANT = create(ValueType.BYTE, IsBuoyant::new, IsBuoyant::isBuoyant);

    record BuoyancyData(String buoyancyData) implements EntityData {
    }

    @NotNull EntityData.Type<BuoyancyData> BUOYANCY_DATA = create(ValueType.STRING, BuoyancyData::new, BuoyancyData::buoyancyData);

    /**
     * @since v428
     */
    record FreezingEffectStrength() implements EntityData {
    }

    @NotNull EntityData.Type<FreezingEffectStrength> FREEZING_EFFECT_STRENGTH = create(FreezingEffectStrength::new);

    /**
     * @since v428
     */
    record GoatHornCount() implements EntityData {
    }

    @NotNull EntityData.Type<GoatHornCount> GOAT_HORN_COUNT = create(GoatHornCount::new);

    /**
     * @since v428
     */
    record BaseRuntimeId() implements EntityData {
    }

    @NotNull EntityData.Type<BaseRuntimeId> BASE_RUNTIME_ID = create(BaseRuntimeId::new);

    /**
     * @since v428
     * @deprecated v440
     */
    record DefineProperties() implements EntityData {
    }

    @NotNull EntityData.Type<DefineProperties> DEFINE_PROPERTIES = create(DefineProperties::new);

    /**
     * @since v428
     */
    record UpdateProperties() implements EntityData {
    }

    @NotNull EntityData.Type<UpdateProperties> UPDATE_PROPERTIES = create(UpdateProperties::new);

    /**
     * @since v503
     */
    record MovementSoundDistanceOffset() implements EntityData {
    }

    @NotNull EntityData.Type<MovementSoundDistanceOffset> MOVEMENT_SOUND_DISTANCE_OFFSET = create(MovementSoundDistanceOffset::new);

    /**
     * @since v503
     */
    record HeartbeatIntervalTicks(int heartbeatIntervalTicks) implements EntityData {
    }

    @NotNull EntityData.Type<HeartbeatIntervalTicks> HEARTBEAT_INTERVAL_TICKS = create(ValueType.INT, HeartbeatIntervalTicks::new, HeartbeatIntervalTicks::heartbeatIntervalTicks);

    /**
     * @since v503
     */
    record HeartbeatSoundEffect(int heartbeatSoundEffect) implements EntityData {
    }

    @NotNull EntityData.Type<HeartbeatSoundEffect> HEARTBEAT_SOUND_EVENT = create(ValueType.INT, HeartbeatSoundEffect::new, HeartbeatSoundEffect::heartbeatSoundEffect);

    /**
     * @since v526
     */
    record PlayerLastDeathPos(Vector3i playerLastDeathPos) implements EntityData {
    }

    @NotNull EntityData.Type<PlayerLastDeathPos> PLAYER_LAST_DEATH_POS = create(ValueType.VECTOR3I, PlayerLastDeathPos::new, PlayerLastDeathPos::playerLastDeathPos);

    /**
     * @since v526
     */
    record PlayerLastDeathDimension(int playerLastDeathDimension) implements EntityData {
    }

    @NotNull EntityData.Type<PlayerLastDeathDimension> PLAYER_LAST_DEATH_DIMENSION = create(ValueType.INT, PlayerLastDeathDimension::new, PlayerLastDeathDimension::playerLastDeathDimension);

    /**
     * @since v526
     */
    record PlayerHasDied(byte playerHasDied) implements EntityData {
    }

    @NotNull EntityData.Type<PlayerHasDied> PLAYER_HAS_DIED = create(ValueType.BYTE, PlayerHasDied::new, PlayerHasDied::playerHasDied);

    static <E extends EntityData> EntityData.@NotNull Type<E> create(@NotNull Supplier<E> supplier) {
        return new EntityData.Type<>() {
            @Override
            public @NotNull E read(@NotNull BitInput input) {
                return supplier.get();
            }

            @Override
            public void write(@NotNull E value, @NotNull BitOutput output) {
            }
        };
    }

    private static <E extends EntityData, T> EntityData.@NotNull Type<E> create(@NotNull EntityData.ValueType<T> valueType, Function<T, E> factory, Function<E, T> getter) {
        BedrockPacketReader reader = BedrockPacketReader.create();
        PacketDataWriter writer = PacketDataWriter.create();
        return new EntityData.Type<>() {
            @Override
            public @NotNull E read(@NotNull BitInput input) throws IOException {
                T value = valueType.safeRead.create(reader, input);
                return factory.apply(value);
            }

            @Override
            public void write(@NotNull E value, @NotNull BitOutput output) throws IOException {
                T t = getter.apply(value);
                valueType.safeWrite.write(writer, output, t);
            }
        };
    }

    interface SafeReadFactory<E> {
        @NotNull E create(@NotNull BedrockPacketReader reader, @NotNull BitInput input) throws IOException;
    }

    interface SafeWrite<E> {
        void write(@NotNull PacketDataWriter writer, @NotNull BitOutput output, @NotNull E value) throws IOException;
    }

    record ValueType<B>(@NotNull SafeReadFactory<B> safeRead, @NotNull SafeWrite<B> safeWrite) {

        public static final ValueType<boolean[]> FLAGS = new ValueType<>(BedrockPacketReader::readLongFlags, PacketDataWriter::writeLongFlags);
        public static final ValueType<Byte> BYTE = new ValueType<>(BedrockPacketReader::readByte, PacketDataWriter::writeByte);
        public static final ValueType<Short> SHORT = new ValueType<>(BedrockPacketReader::readShortLE, PacketDataWriter::writeShortLE);
        public static final ValueType<Integer> INT = new ValueType<>(BedrockPacketReader::readInt, PacketDataWriter::writeInt);
        public static final ValueType<Float> FLOAT = new ValueType<>(BedrockPacketReader::readFloatLE, PacketDataWriter::writeFloatLE);
        public static final ValueType<String> STRING = new ValueType<>(BedrockPacketReader::readString, PacketDataWriter::writeString);
        public static final ValueType<NbtMap> NBT = new ValueType<>(BedrockPacketReader::readNBTMap, PacketDataWriter::writeNBTMap);
        public static final ValueType<Long> LONG = new ValueType<>(BedrockPacketReader::readLong, PacketDataWriter::writeLong);
        public static final ValueType<Vector3i> VECTOR3I = new ValueType<>(BedrockPacketReader::readVector3i, PacketDataWriter::writeVector3i);
        public static final ValueType<Vector3f> VECTOR3F = new ValueType<>(BedrockPacketReader::readVector3f, PacketDataWriter::writeVector3f);

        @Nonnull
        public static ValueType<?> from(Object o) {
            if (o instanceof boolean[]) {
                return ValueType.FLAGS;
            } else if (o instanceof Byte) {
                return ValueType.BYTE;
            } else if (o instanceof Short) {
                return ValueType.SHORT;
            } else if (o instanceof Integer) {
                return ValueType.INT;
            } else if (o instanceof Float) {
                return ValueType.FLOAT;
            } else if (o instanceof String) {
                return ValueType.STRING;
            } else if (o instanceof ItemData || o instanceof NbtMap) {
                return ValueType.NBT;
            } else if (o instanceof Vector3i) {
                return ValueType.VECTOR3I;
            } else if (o instanceof Long) {
                return ValueType.LONG;
            } else if (o instanceof Vector3f) {
                return ValueType.VECTOR3F;
            }
            throw new IllegalArgumentException("Invalid type");
        }
    }
}
