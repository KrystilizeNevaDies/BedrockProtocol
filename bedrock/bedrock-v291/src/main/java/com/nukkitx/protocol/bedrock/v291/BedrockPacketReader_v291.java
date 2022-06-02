package com.nukkitx.protocol.bedrock.v291;

import com.github.jinahya.bit.io.BitInput;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.data.command.CommandParam;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.skin.AnimationData;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public enum BedrockPacketReader_v291 implements BedrockPacketReader {
    INSTANCE;

    public final @NotNull Int2ObjectMap<EntityData.Type<?>> id2entityData = new Int2ObjectOpenHashMap<>() {{
        put(0, EntityData.FLAGS);
        put(1, EntityData.HEALTH);
        put(2, EntityData.VARIANT);
        put(3, EntityData.COLOR);
        put(4, EntityData.NAMETAG);
        put(5, EntityData.OWNER_EID);
        put(6, EntityData.TARGET_EID);
        put(7, EntityData.AIR_SUPPLY);
        put(8, EntityData.EFFECT_COLOR);
        put(9, EntityData.EFFECT_AMBIENT);
        put(10, EntityData.JUMP_DURATION);
        put(11, EntityData.HURT_TIME);
        put(12, EntityData.HURT_DIRECTION);
        put(13, EntityData.ROW_TIME_LEFT);
        put(14, EntityData.ROW_TIME_RIGHT);
        put(15, EntityData.EXPERIENCE_VALUE);
        put(16, EntityData.DISPLAY_ITEM);
        put(17, EntityData.DISPLAY_OFFSET_INT);
        put(17, EntityData.DISPLAY_OFFSET_LONG);
        put(18, EntityData.CUSTOM_DISPLAY);
        put(19, EntityData.SWELL);
        put(20, EntityData.OLD_SWELL);
        put(21, EntityData.SWELL_DIRECTION);
        put(22, EntityData.CHARGE_AMOUNT);
        put(23, EntityData.CARRIED_BLOCK);
        put(24, EntityData.CLIENT_EVENT);
        put(25, EntityData.USING_ITEM);
        put(26, EntityData.PLAYER_FLAGS);
        put(27, EntityData.PLAYER_INDEX);
        put(28, EntityData.BED_POSITION);
        put(29, EntityData.X_POWER);
        put(30, EntityData.Y_POWER);
        put(31, EntityData.Z_POWER);
        put(32, EntityData.AUX_POWER);
        put(33, EntityData.FISH_X);
        put(34, EntityData.FISH_Z);
        put(35, EntityData.FISH_ANGLE);
        put(36, EntityData.POTION_AUX_VALUE);
        put(37, EntityData.LEASH_HOLDER_EID);
        put(38, EntityData.SCALE);
        put(39, EntityData.INTERACTIVE_TAG);
        put(40, EntityData.NPC_SKIN_ID);
        put(41, EntityData.URL_TAG);
        put(42, EntityData.MAX_AIR_SUPPLY);
        put(43, EntityData.MARK_VARIANT);
        put(44, EntityData.CONTAINER_TYPE);
        put(45, EntityData.CONTAINER_BASE_SIZE);
        put(46, EntityData.CONTAINER_STRENGTH_MODIFIER);
        put(47, EntityData.BLOCK_TARGET);
        put(48, EntityData.WITHER_INVULNERABLE_TICKS);
        put(49, EntityData.WITHER_TARGET_1);
        put(50, EntityData.WITHER_TARGET_2);
        put(51, EntityData.WITHER_TARGET_3);
        put(52, EntityData.WITHER_AERIAL_ATTACK);
        put(53, EntityData.BOUNDING_BOX_WIDTH);
        put(54, EntityData.BOUNDING_BOX_HEIGHT);
        put(55, EntityData.FUSE_LENGTH);
        put(56, EntityData.RIDER_SEAT_POSITION);
        put(57, EntityData.RIDER_ROTATION_LOCKED);
        put(58, EntityData.RIDER_MAX_ROTATION);
        put(59, EntityData.RIDER_MIN_ROTATION);
        put(60, EntityData.AREA_EFFECT_CLOUD_RADIUS);
        put(61, EntityData.AREA_EFFECT_CLOUD_WAITING);
        put(62, EntityData.AREA_EFFECT_CLOUD_PARTICLE_ID);
        put(63, EntityData.SHULKER_PEAK_HEIGHT);
        put(64, EntityData.SHULKER_ATTACH_FACE);
        put(66, EntityData.SHULKER_ATTACH_POS);
        put(67, EntityData.TRADE_TARGET_EID);
        put(69, EntityData.COMMAND_BLOCK_ENABLED); // Unsure
        put(70, EntityData.COMMAND_BLOCK_COMMAND);
        put(71, EntityData.COMMAND_BLOCK_LAST_OUTPUT);
        put(72, EntityData.COMMAND_BLOCK_TRACK_OUTPUT);
        put(73, EntityData.CONTROLLING_RIDER_SEAT_INDEX);
        put(74, EntityData.STRENGTH);
        put(75, EntityData.MAX_STRENGTH);
        put(76, EntityData.EVOKER_SPELL_COLOR);
        put(77, EntityData.LIMITED_LIFE);
        put(78, EntityData.ARMOR_STAND_POSE_INDEX);
        put(79, EntityData.ENDER_CRYSTAL_TIME_OFFSET);
        put(80, EntityData.NAMETAG_ALWAYS_SHOW);
        put(81, EntityData.COLOR_2);
        put(83, EntityData.SCORE_TAG);
        put(84, EntityData.BALLOON_ATTACHED_ENTITY);
        put(85, EntityData.PUFFERFISH_SIZE);
        put(86, EntityData.BOAT_BUBBLE_TIME);
        put(87, EntityData.AGENT_ID);
    }};
    public final @NotNull Int2ObjectMap<EntityFlag> id2entityFlag = new Int2ObjectOpenHashMap<>() {{
        put(0, EntityFlag.ON_FIRE);
        put(1, EntityFlag.SNEAKING);
        put(2, EntityFlag.RIDING);
        put(3, EntityFlag.SPRINTING);
        put(4, EntityFlag.USING_ITEM);
        put(5, EntityFlag.INVISIBLE);
        put(6, EntityFlag.TEMPTED);
        put(7, EntityFlag.IN_LOVE);
        put(8, EntityFlag.SADDLED);
        put(9, EntityFlag.POWERED);
        put(10, EntityFlag.IGNITED);
        put(11, EntityFlag.BABY);
        put(12, EntityFlag.CONVERTING);
        put(13, EntityFlag.CRITICAL);
        put(14, EntityFlag.CAN_SHOW_NAME);
        put(15, EntityFlag.ALWAYS_SHOW_NAME);
        put(16, EntityFlag.NO_AI);
        put(17, EntityFlag.SILENT);
        put(18, EntityFlag.WALL_CLIMBING);
        put(19, EntityFlag.CAN_CLIMB);
        put(20, EntityFlag.CAN_SWIM);
        put(21, EntityFlag.CAN_FLY);
        put(22, EntityFlag.CAN_WALK);
        put(23, EntityFlag.RESTING);
        put(24, EntityFlag.SITTING);
        put(25, EntityFlag.ANGRY);
        put(26, EntityFlag.INTERESTED);
        put(27, EntityFlag.CHARGED);
        put(28, EntityFlag.TAMED);
        put(29, EntityFlag.ORPHANED);
        put(30, EntityFlag.LEASHED);
        put(31, EntityFlag.SHEARED);
        put(32, EntityFlag.GLIDING);
        put(33, EntityFlag.ELDER);
        put(34, EntityFlag.MOVING);
        put(35, EntityFlag.BREATHING);
        put(36, EntityFlag.CHESTED);
        put(37, EntityFlag.STACKABLE);
        put(38, EntityFlag.SHOW_BOTTOM);
        put(39, EntityFlag.STANDING);
        put(40, EntityFlag.SHAKING);
        put(41, EntityFlag.IDLING);
        put(42, EntityFlag.CASTING);
        put(43, EntityFlag.CHARGING);
        put(44, EntityFlag.WASD_CONTROLLED);
        put(45, EntityFlag.CAN_POWER_JUMP);
        put(46, EntityFlag.LINGERING);
        put(47, EntityFlag.HAS_COLLISION);
        put(48, EntityFlag.HAS_GRAVITY);
        put(49, EntityFlag.FIRE_IMMUNE);
        put(50, EntityFlag.DANCING);
        put(51, EntityFlag.ENCHANTED);
        put(52, EntityFlag.RETURN_TRIDENT);
        put(53, EntityFlag.CONTAINER_IS_PRIVATE);
        put(54, EntityFlag.IS_TRANSFORMING);
        put(55, EntityFlag.DAMAGE_NEARBY_MOBS);
        put(56, EntityFlag.SWIMMING);
        put(57, EntityFlag.BRIBED);
        put(58, EntityFlag.IS_PREGNANT);
        put(59, EntityFlag.LAYING_EGG);
        put(60, EntityFlag.RIDER_CAN_PICK);
    }};
    public final @NotNull Int2ObjectMap<EntityData.ValueType<?>> id2entityDataType = new Int2ObjectOpenHashMap<>() {{
        put(7, EntityData.ValueType.FLAGS);
        put(0, EntityData.ValueType.BYTE);
        put(1, EntityData.ValueType.SHORT);
        put(2, EntityData.ValueType.INT);
        put(3, EntityData.ValueType.FLOAT);
        put(4, EntityData.ValueType.STRING);
        put(5, EntityData.ValueType.NBT);
        put(6, EntityData.ValueType.VECTOR3I);
        put(7, EntityData.ValueType.LONG);
        put(8, EntityData.ValueType.VECTOR3F);
    }};
    public final @NotNull Int2ObjectMap<EntityEventType> id2entityEventType = new Int2ObjectOpenHashMap<>() {{
        put(0, EntityEventType.NONE);
        put(1, EntityEventType.JUMP);
        put(2, EntityEventType.HURT);
        put(3, EntityEventType.DEATH);
        put(4, EntityEventType.ATTACK_START);
        put(5, EntityEventType.ATTACK_STOP);
        put(6, EntityEventType.TAME_FAILED);
        put(7, EntityEventType.TAME_SUCCEEDED);
        put(8, EntityEventType.SHAKE_WETNESS);
        put(9, EntityEventType.USE_ITEM);
        put(10, EntityEventType.EAT_GRASS);
        put(11, EntityEventType.FISH_HOOK_BUBBLE);
        put(12, EntityEventType.FISH_HOOK_POSITION);
        put(13, EntityEventType.FISH_HOOK_TIME);
        put(14, EntityEventType.FISH_HOOK_TEASE);
        put(15, EntityEventType.SQUID_FLEEING);
        put(16, EntityEventType.ZOMBIE_VILLAGER_CURE);
        put(17, EntityEventType.PLAY_AMBIENT);
        put(18, EntityEventType.RESPAWN);
        put(19, EntityEventType.GOLEM_FLOWER_OFFER);
        put(20, EntityEventType.GOLEM_FLOWER_WITHDRAW);
        put(21, EntityEventType.LOVE_PARTICLES);
        put(22, EntityEventType.VILLAGER_ANGRY);
        put(23, EntityEventType.VILLAGER_HAPPY);
        put(24, EntityEventType.WITCH_HAT_MAGIC);
        put(25, EntityEventType.FIREWORK_EXPLODE);
        put(26, EntityEventType.IN_LOVE_HEARTS);
        put(27, EntityEventType.SILVERFISH_MERGE_WITH_STONE);
        put(28, EntityEventType.GUARDIAN_ATTACK_ANIMATION);
        put(29, EntityEventType.WITCH_DRINK_POTION);
        put(30, EntityEventType.WITCH_THROW_POTION);
        put(31, EntityEventType.PRIME_TNT_MINECART);
        put(32, EntityEventType.PRIME_CREEPER);
        put(33, EntityEventType.AIR_SUPPLY);
        put(34, EntityEventType.PLAYER_ADD_XP_LEVELS);
        put(35, EntityEventType.ELDER_GUARDIAN_CURSE);
        put(36, EntityEventType.AGENT_ARM_SWING);
        put(37, EntityEventType.ENDER_DRAGON_DEATH);
        put(38, EntityEventType.DUST_PARTICLES);
        put(39, EntityEventType.ARROW_SHAKE);

        put(57, EntityEventType.EATING_ITEM);
        put(60, EntityEventType.BABY_ANIMAL_FEED);
        put(61, EntityEventType.DEATH_SMOKE_CLOUD);
        put(62, EntityEventType.COMPLETE_TRADE);
        put(63, EntityEventType.REMOVE_LEASH);
        put(64, EntityEventType.CARAVAN);
        put(65, EntityEventType.CONSUME_TOTEM);
        put(66, EntityEventType.CHECK_TREASURE_HUNTER_ACHIEVEMENT);
        put(67, EntityEventType.ENTITY_SPAWN);
        put(68, EntityEventType.DRAGON_FLAMING);
        put(69, EntityEventType.UPDATE_ITEM_STACK_SIZE);
        put(70, EntityEventType.START_SWIMMING);
        put(71, EntityEventType.BALLOON_POP);
        put(72, EntityEventType.TREASURE_HUNT);
    }};
    public final @NotNull Int2ObjectMap<Class<?>> id2GameRuleType = new Int2ObjectOpenHashMap<>() {{
        put(1, Boolean.class);
        put(2, Integer.class);
        put(3, Float.class);
    }};
    public final @NotNull Int2ObjectMap<SoundEvent> id2SoundEvent = new Int2ObjectOpenHashMap<>() {{
        put(0, SoundEvent.ITEM_USE_ON);
        put(1, SoundEvent.HIT);
        put(2, SoundEvent.STEP);
        put(3, SoundEvent.FLY);
        put(4, SoundEvent.JUMP);
        put(5, SoundEvent.BREAK);
        put(6, SoundEvent.PLACE);
        put(7, SoundEvent.HEAVY_STEP);
        put(8, SoundEvent.GALLOP);
        put(9, SoundEvent.FALL);
        put(10, SoundEvent.AMBIENT);
        put(11, SoundEvent.AMBIENT_BABY);
        put(12, SoundEvent.AMBIENT_IN_WATER);
        put(13, SoundEvent.BREATHE);
        put(14, SoundEvent.DEATH);
        put(15, SoundEvent.DEATH_IN_WATER);
        put(16, SoundEvent.DEATH_TO_ZOMBIE);
        put(17, SoundEvent.HURT);
        put(18, SoundEvent.HURT_IN_WATER);
        put(19, SoundEvent.MAD);
        put(20, SoundEvent.BOOST);
        put(21, SoundEvent.BOW);
        put(22, SoundEvent.SQUISH_BIG);
        put(23, SoundEvent.SQUISH_SMALL);
        put(24, SoundEvent.FALL_BIG);
        put(25, SoundEvent.FALL_SMALL);
        put(26, SoundEvent.SPLASH);
        put(27, SoundEvent.FIZZ);
        put(28, SoundEvent.FLAP);
        put(29, SoundEvent.SWIM);
        put(30, SoundEvent.DRINK);
        put(31, SoundEvent.EAT);
        put(32, SoundEvent.TAKEOFF);
        put(33, SoundEvent.SHAKE);
        put(34, SoundEvent.PLOP);
        put(35, SoundEvent.LAND);
        put(36, SoundEvent.SADDLE);
        put(37, SoundEvent.ARMOR);
        put(38, SoundEvent.MOB_ARMOR_STAND_PLACE);
        put(39, SoundEvent.ADD_CHEST);
        put(40, SoundEvent.THROW);
        put(41, SoundEvent.ATTACK);
        put(42, SoundEvent.ATTACK_NODAMAGE);
        put(43, SoundEvent.ATTACK_STRONG);
        put(44, SoundEvent.WARN);
        put(45, SoundEvent.SHEAR);
        put(46, SoundEvent.MILK);
        put(47, SoundEvent.THUNDER);
        put(48, SoundEvent.EXPLODE);
        put(49, SoundEvent.FIRE);
        put(50, SoundEvent.IGNITE);
        put(51, SoundEvent.FUSE);
        put(52, SoundEvent.STARE);
        put(53, SoundEvent.SPAWN);
        put(54, SoundEvent.SHOOT);
        put(55, SoundEvent.BREAK_BLOCK);
        put(56, SoundEvent.LAUNCH);
        put(57, SoundEvent.BLAST);
        put(58, SoundEvent.LARGE_BLAST);
        put(59, SoundEvent.TWINKLE);
        put(60, SoundEvent.REMEDY);
        put(61, SoundEvent.UNFECT);
        put(62, SoundEvent.LEVELUP);
        put(63, SoundEvent.BOW_HIT);
        put(64, SoundEvent.BULLET_HIT);
        put(65, SoundEvent.EXTINGUISH_FIRE);
        put(66, SoundEvent.ITEM_FIZZ);
        put(67, SoundEvent.CHEST_OPEN);
        put(68, SoundEvent.CHEST_CLOSED);
        put(69, SoundEvent.SHULKERBOX_OPEN);
        put(70, SoundEvent.SHULKERBOX_CLOSED);
        put(71, SoundEvent.ENDERCHEST_OPEN);
        put(72, SoundEvent.ENDERCHEST_CLOSED);
        put(73, SoundEvent.POWER_ON);
        put(74, SoundEvent.POWER_OFF);
        put(75, SoundEvent.ATTACH);
        put(76, SoundEvent.DETACH);
        put(77, SoundEvent.DENY);
        put(78, SoundEvent.TRIPOD);
        put(79, SoundEvent.POP);
        put(80, SoundEvent.DROP_SLOT);
        put(81, SoundEvent.NOTE);
        put(82, SoundEvent.THORNS);
        put(83, SoundEvent.PISTON_IN);
        put(84, SoundEvent.PISTON_OUT);
        put(85, SoundEvent.PORTAL);
        put(86, SoundEvent.WATER);
        put(87, SoundEvent.LAVA_POP);
        put(88, SoundEvent.LAVA);
        put(89, SoundEvent.BURP);
        put(90, SoundEvent.BUCKET_FILL_WATER);
        put(91, SoundEvent.BUCKET_FILL_LAVA);
        put(92, SoundEvent.BUCKET_EMPTY_WATER);
        put(93, SoundEvent.BUCKET_EMPTY_LAVA);
        put(94, SoundEvent.ARMOR_EQUIP_CHAIN);
        put(95, SoundEvent.ARMOR_EQUIP_DIAMOND);
        put(96, SoundEvent.ARMOR_EQUIP_GENERIC);
        put(97, SoundEvent.ARMOR_EQUIP_GOLD);
        put(98, SoundEvent.ARMOR_EQUIP_IRON);
        put(99, SoundEvent.ARMOR_EQUIP_LEATHER);
        put(100, SoundEvent.ARMOR_EQUIP_ELYTRA);
        put(101, SoundEvent.RECORD_13);
        put(102, SoundEvent.RECORD_CAT);
        put(103, SoundEvent.RECORD_BLOCKS);
        put(104, SoundEvent.RECORD_CHIRP);
        put(105, SoundEvent.RECORD_FAR);
        put(106, SoundEvent.RECORD_MALL);
        put(107, SoundEvent.RECORD_MELLOHI);
        put(108, SoundEvent.RECORD_STAL);
        put(109, SoundEvent.RECORD_STRAD);
        put(110, SoundEvent.RECORD_WARD);
        put(111, SoundEvent.RECORD_11);
        put(112, SoundEvent.RECORD_WAIT);
        put(113, SoundEvent.STOP_RECORD);
        put(114, SoundEvent.FLOP);
        put(115, SoundEvent.ELDERGUARDIAN_CURSE);
        put(116, SoundEvent.MOB_WARNING);
        put(117, SoundEvent.MOB_WARNING_BABY);
        put(118, SoundEvent.TELEPORT);
        put(119, SoundEvent.SHULKER_OPEN);
        put(120, SoundEvent.SHULKER_CLOSE);
        put(121, SoundEvent.HAGGLE);
        put(122, SoundEvent.HAGGLE_YES);
        put(123, SoundEvent.HAGGLE_NO);
        put(124, SoundEvent.HAGGLE_IDLE);
        put(125, SoundEvent.CHORUS_GROW);
        put(126, SoundEvent.CHORUS_DEATH);
        put(127, SoundEvent.GLASS);
        put(128, SoundEvent.POTION_BREWED);
        put(129, SoundEvent.CAST_SPELL);
        put(130, SoundEvent.PREPARE_ATTACK);
        put(131, SoundEvent.PREPARE_SUMMON);
        put(132, SoundEvent.PREPARE_WOLOLO);
        put(133, SoundEvent.FANG);
        put(134, SoundEvent.CHARGE);
        put(135, SoundEvent.CAMERA_TAKE_PICTURE);
        put(136, SoundEvent.LEASHKNOT_PLACE);
        put(137, SoundEvent.LEASHKNOT_BREAK);
        put(138, SoundEvent.GROWL);
        put(139, SoundEvent.WHINE);
        put(140, SoundEvent.PANT);
        put(141, SoundEvent.PURR);
        put(142, SoundEvent.PURREOW);
        put(143, SoundEvent.DEATH_MIN_VOLUME);
        put(144, SoundEvent.DEATH_MID_VOLUME);
        put(145, SoundEvent.IMITATE_BLAZE);
        put(146, SoundEvent.IMITATE_CAVE_SPIDER);
        put(147, SoundEvent.IMITATE_CREEPER);
        put(148, SoundEvent.IMITATE_ELDER_GUARDIAN);
        put(149, SoundEvent.IMITATE_ENDER_DRAGON);
        put(150, SoundEvent.IMITATE_ENDERMAN);
        put(152, SoundEvent.IMITATE_EVOCATION_ILLAGER);
        put(153, SoundEvent.IMITATE_GHAST);
        put(154, SoundEvent.IMITATE_HUSK);
        put(155, SoundEvent.IMITATE_ILLUSION_ILLAGER);
        put(156, SoundEvent.IMITATE_MAGMA_CUBE);
        put(157, SoundEvent.IMITATE_POLAR_BEAR);
        put(158, SoundEvent.IMITATE_SHULKER);
        put(159, SoundEvent.IMITATE_SILVERFISH);
        put(160, SoundEvent.IMITATE_SKELETON);
        put(161, SoundEvent.IMITATE_SLIME);
        put(162, SoundEvent.IMITATE_SPIDER);
        put(163, SoundEvent.IMITATE_STRAY);
        put(164, SoundEvent.IMITATE_VEX);
        put(165, SoundEvent.IMITATE_VINDICATION_ILLAGER);
        put(166, SoundEvent.IMITATE_WITCH);
        put(167, SoundEvent.IMITATE_WITHER);
        put(168, SoundEvent.IMITATE_WITHER_SKELETON);
        put(169, SoundEvent.IMITATE_WOLF);
        put(170, SoundEvent.IMITATE_ZOMBIE);
        put(171, SoundEvent.IMITATE_ZOMBIE_PIGMAN);
        put(172, SoundEvent.IMITATE_ZOMBIE_VILLAGER);
        put(173, SoundEvent.BLOCK_END_PORTAL_FRAME_FILL);
        put(174, SoundEvent.BLOCK_END_PORTAL_SPAWN);
        put(175, SoundEvent.RANDOM_ANVIL_USE);
        put(176, SoundEvent.BOTTLE_DRAGONBREATH);
        put(177, SoundEvent.PORTAL_TRAVEL);
        put(178, SoundEvent.ITEM_TRIDENT_HIT);
        put(179, SoundEvent.ITEM_TRIDENT_RETURN);
        put(180, SoundEvent.ITEM_TRIDENT_RIPTIDE_1);
        put(181, SoundEvent.ITEM_TRIDENT_RIPTIDE_2);
        put(182, SoundEvent.ITEM_TRIDENT_RIPTIDE_3);
        put(183, SoundEvent.ITEM_TRIDENT_THROW);
        put(184, SoundEvent.ITEM_TRIDENT_THUNDER);
        put(185, SoundEvent.ITEM_TRIDENT_HIT_GROUND);
        put(186, SoundEvent.DEFAULT);
        put(188, SoundEvent.ELEMENT_CONSTRUCTOR_OPEN);
        put(189, SoundEvent.ICE_BOMB_HIT);
        put(190, SoundEvent.BALLOON_POP);
        put(191, SoundEvent.LT_REACTION_ICE_BOMB);
        put(192, SoundEvent.LT_REACTION_BLEACH);
        put(193, SoundEvent.LT_REACTION_E_PASTE);
        put(194, SoundEvent.LT_REACTION_E_PASTE2);
        put(199, SoundEvent.LT_REACTION_FERTILIZER);
        put(200, SoundEvent.LT_REACTION_FIREBALL);
        put(201, SoundEvent.LT_REACTION_MG_SALT);
        put(202, SoundEvent.LT_REACTION_MISC_FIRE);
        put(203, SoundEvent.LT_REACTION_FIRE);
        put(204, SoundEvent.LT_REACTION_MISC_EXPLOSION);
        put(205, SoundEvent.LT_REACTION_MISC_MYSTICAL);
        put(206, SoundEvent.LT_REACTION_MISC_MYSTICAL2);
        put(207, SoundEvent.LT_REACTION_PRODUCT);
        put(208, SoundEvent.SPARKLER_USE);
        put(209, SoundEvent.GLOWSTICK_USE);
        put(210, SoundEvent.SPARKLER_ACTIVE);
        put(211, SoundEvent.CONVERT_TO_DROWNED);
        put(212, SoundEvent.BUCKET_FILL_FISH);
        put(213, SoundEvent.BUCKET_EMPTY_FISH);
        put(214, SoundEvent.BUBBLE_UP);
        put(215, SoundEvent.BUBBLE_DOWN);
        put(216, SoundEvent.BUBBLE_POP);
        put(217, SoundEvent.BUBBLE_UP_INSIDE);
        put(218, SoundEvent.BUBBLE_DOWN_INSIDE);
        put(219, SoundEvent.BABY_HURT);
        put(220, SoundEvent.BABY_DEATH);
        put(221, SoundEvent.BABY_STEP);
        put(222, SoundEvent.BABY_SPAWN);
        put(223, SoundEvent.BORN);
        put(224, SoundEvent.BLOCK_TURTLE_EGG_BREAK);
        put(225, SoundEvent.BLOCK_TURTLE_EGG_CRACK);
        put(226, SoundEvent.BLOCK_TURTLE_EGG_HATCH);
        put(227, SoundEvent.TURTLE_LAY_EGG);
        put(228, SoundEvent.BLOCK_TURTLE_EGG_ATTACK);
        put(229, SoundEvent.BEACON_ACTIVATE);
        put(230, SoundEvent.BEACON_AMBIENT);
        put(231, SoundEvent.BEACON_DEACTIVATE);
        put(232, SoundEvent.BEACON_POWER);
        put(233, SoundEvent.CONDUIT_ACTIVATE);
        put(234, SoundEvent.CONDUIT_AMBIENT);
        put(235, SoundEvent.CONDUIT_ATTACK);
        put(236, SoundEvent.CONDUIT_DEACTIVATE);
        put(237, SoundEvent.CONDUIT_SHORT);
        put(238, SoundEvent.SWOOP);
        put(239, SoundEvent.UNDEFINED);
    }};
    public final @NotNull Int2ObjectMap<CommandParam> id2CommandParam = new Int2ObjectOpenHashMap<>() {{
        put(1, CommandParam.INT);
        put(2, CommandParam.FLOAT);
        put(3, CommandParam.VALUE);
        put(4, CommandParam.WILDCARD_INT);
        put(5, CommandParam.OPERATOR);
        put(6, CommandParam.TARGET);
        put(7, CommandParam.WILDCARD_TARGET);
        put(14, CommandParam.FILE_PATH);
        put(18, CommandParam.INT_RANGE);
        put(26, CommandParam.STRING);
        put(28, CommandParam.POSITION);
        put(31, CommandParam.MESSAGE);
        put(33, CommandParam.TEXT);
        put(36, CommandParam.JSON);
        put(43, CommandParam.COMMAND);
    }};
    public final @NotNull Int2ObjectMap<LevelEventType> id2LevelEventType = new Int2ObjectOpenHashMap<>() {{
        put(0, LevelEventType.UNDEFINED);

        // Sounds
        int sound = 1000;
        put(0 + sound, LevelEventType.SOUND_CLICK);
        put(1 + sound, LevelEventType.SOUND_CLICK_FAIL);
        put(2 + sound, LevelEventType.SOUND_LAUNCH);
        put(3 + sound, LevelEventType.SOUND_DOOR_OPEN);
        put(4 + sound, LevelEventType.SOUND_FIZZ);
        put(5 + sound, LevelEventType.SOUND_FUSE);
        put(6 + sound, LevelEventType.SOUND_PLAY_RECORDING);
        put(7 + sound, LevelEventType.SOUND_GHAST_WARNING);
        put(8 + sound, LevelEventType.SOUND_GHAST_FIREBALL);
        put(9 + sound, LevelEventType.SOUND_BLAZE_FIREBALL);
        put(10 + sound, LevelEventType.SOUND_ZOMBIE_DOOR_BUMP);

        put(12 + sound, LevelEventType.SOUND_ZOMBIE_DOOR_CRASH);

        put(16 + sound, LevelEventType.SOUND_ZOMBIE_INFECTED);
        put(17 + sound, LevelEventType.SOUND_ZOMBIE_CONVERTED);
        put(18 + sound, LevelEventType.SOUND_ENDERMAN_TELEPORT);

        put(20 + sound, LevelEventType.SOUND_ANVIL_BROKEN);
        put(21 + sound, LevelEventType.SOUND_ANVIL_USED);
        put(22 + sound, LevelEventType.SOUND_ANVIL_LAND);

        put(30 + sound, LevelEventType.SOUND_INFINITY_ARROW_PICKUP);

        put(32 + sound, LevelEventType.SOUND_TELEPORT_ENDERPEARL);

        put(40 + sound, LevelEventType.SOUND_ITEMFRAME_ITEM_ADD);
        put(41 + sound, LevelEventType.SOUND_ITEMFRAME_BREAK);
        put(42 + sound, LevelEventType.SOUND_ITEMFRAME_PLACE);
        put(43 + sound, LevelEventType.SOUND_ITEMFRAME_ITEM_REMOVE);
        put(44 + sound, LevelEventType.SOUND_ITEMFRAME_ITEM_ROTATE);

        put(51 + sound, LevelEventType.SOUND_EXPERIENCE_ORB_PICKUP);
        put(52 + sound, LevelEventType.SOUND_TOTEM_USED);

        put(60 + sound, LevelEventType.SOUND_ARMOR_STAND_BREAK);
        put(61 + sound, LevelEventType.SOUND_ARMOR_STAND_HIT);
        put(62 + sound, LevelEventType.SOUND_ARMOR_STAND_LAND);
        put(63 + sound, LevelEventType.SOUND_ARMOR_STAND_PLACE);

        // Particles
        int particle = 2000;
        put(0 + particle, LevelEventType.PARTICLE_SHOOT);
        put(1 + particle, LevelEventType.PARTICLE_DESTROY_BLOCK);
        put(2 + particle, LevelEventType.PARTICLE_POTION_SPLASH);
        put(3 + particle, LevelEventType.PARTICLE_EYE_OF_ENDER_DEATH);
        put(4 + particle, LevelEventType.PARTICLE_MOB_BLOCK_SPAWN);
        put(5 + particle, LevelEventType.PARTICLE_CROP_GROWTH);
        put(6 + particle, LevelEventType.PARTICLE_SOUND_GUARDIAN_GHOST);
        put(7 + particle, LevelEventType.PARTICLE_DEATH_SMOKE);
        put(8 + particle, LevelEventType.PARTICLE_DENY_BLOCK);
        put(9 + particle, LevelEventType.PARTICLE_GENERIC_SPAWN);
        put(10 + particle, LevelEventType.PARTICLE_DRAGON_EGG);
        put(11 + particle, LevelEventType.PARTICLE_CROP_EATEN);
        put(12 + particle, LevelEventType.PARTICLE_CRIT);
        put(13 + particle, LevelEventType.PARTICLE_TELEPORT);
        put(14 + particle, LevelEventType.PARTICLE_CRACK_BLOCK);
        put(15 + particle, LevelEventType.PARTICLE_BUBBLES);
        put(16 + particle, LevelEventType.PARTICLE_EVAPORATE);
        put(17 + particle, LevelEventType.PARTICLE_DESTROY_ARMOR_STAND);
        put(18 + particle, LevelEventType.PARTICLE_BREAKING_EGG);
        put(19 + particle, LevelEventType.PARTICLE_DESTROY_EGG);
        put(20 + particle, LevelEventType.PARTICLE_EVAPORATE_WATER);
        put(21 + particle, LevelEventType.PARTICLE_DESTROY_BLOCK_NO_SOUND);

        // World
        int world = 3000;
        put(1 + world, LevelEventType.START_RAINING);
        put(2 + world, LevelEventType.START_THUNDERSTORM);
        put(3 + world, LevelEventType.STOP_RAINING);
        put(4 + world, LevelEventType.STOP_THUNDERSTORM);
        put(5 + world, LevelEventType.GLOBAL_PAUSE);
        put(6 + world, LevelEventType.SIM_TIME_STEP);
        put(7 + world, LevelEventType.SIM_TIME_SCALE);

        // Blocks
        int block = 3500;
        put(0 + block, LevelEventType.ACTIVATE_BLOCK);
        put(1 + block, LevelEventType.CAULDRON_EXPLODE);
        put(2 + block, LevelEventType.CAULDRON_DYE_ARMOR);
        put(3 + block, LevelEventType.CAULDRON_CLEAN_ARMOR);
        put(4 + block, LevelEventType.CAULDRON_FILL_POTION);
        put(5 + block, LevelEventType.CAULDRON_TAKE_POTION);
        put(6 + block, LevelEventType.CAULDRON_FILL_WATER);
        put(7 + block, LevelEventType.CAULDRON_TAKE_WATER);
        put(8 + block, LevelEventType.CAULDRON_ADD_DYE);
        put(9 + block, LevelEventType.CAULDRON_CLEAN_BANNER);
        put(10 + block, LevelEventType.CAULDRON_FLUSH);

        // Legacy particles
        int legacy = 0x4000;
        put(1 + legacy, LevelEventType.PARTICLE_BUBBLE);
        put(2 + legacy, LevelEventType.PARTICLE_CRITICAL);
        put(3 + legacy, LevelEventType.PARTICLE_BLOCK_FORCE_FIELD);
        put(4 + legacy, LevelEventType.PARTICLE_SMOKE);
        put(5 + legacy, LevelEventType.PARTICLE_EXPLODE);
        put(6 + legacy, LevelEventType.PARTICLE_EVAPORATION);
        put(7 + legacy, LevelEventType.PARTICLE_FLAME);
        put(8 + legacy, LevelEventType.PARTICLE_LAVA);
        put(9 + legacy, LevelEventType.PARTICLE_LARGE_SMOKE);
        put(10 + legacy, LevelEventType.PARTICLE_REDSTONE);
        put(11 + legacy, LevelEventType.PARTICLE_RISING_RED_DUST);
        put(12 + legacy, LevelEventType.PARTICLE_ITEM_BREAK);
        put(13 + legacy, LevelEventType.PARTICLE_SNOWBALL_POOF);
        put(14 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE);
        put(15 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE_SEED);
        put(16 + legacy, LevelEventType.PARTICLE_MOB_FLAME);
        put(17 + legacy, LevelEventType.PARTICLE_HEART);
        put(18 + legacy, LevelEventType.PARTICLE_TERRAIN);
        put(19 + legacy, LevelEventType.PARTICLE_TOWN_AURA);
        put(20 + legacy, LevelEventType.PARTICLE_PORTAL);
        put(21 + legacy, LevelEventType.PARTICLE_SPLASH);
        put(22 + legacy, LevelEventType.PARTICLE_WATER_WAKE);
        put(23 + legacy, LevelEventType.PARTICLE_DRIP_WATER);
        put(24 + legacy, LevelEventType.PARTICLE_DRIP_LAVA);
        put(25 + legacy, LevelEventType.PARTICLE_FALLING_DUST);
        put(26 + legacy, LevelEventType.PARTICLE_MOB_SPELL);
        put(27 + legacy, LevelEventType.PARTICLE_MOB_SPELL_AMBIENT);
        put(28 + legacy, LevelEventType.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        put(29 + legacy, LevelEventType.PARTICLE_INK);
        put(30 + legacy, LevelEventType.PARTICLE_SLIME);
        put(31 + legacy, LevelEventType.PARTICLE_RAIN_SPLASH);
        put(32 + legacy, LevelEventType.PARTICLE_VILLAGER_ANGRY);
        put(33 + legacy, LevelEventType.PARTICLE_VILLAGER_HAPPY);
        put(34 + legacy, LevelEventType.PARTICLE_ENCHANTMENT_TABLE);
        put(35 + legacy, LevelEventType.PARTICLE_TRACKING_EMITTER);
        put(36 + legacy, LevelEventType.PARTICLE_NOTE);
        put(37 + legacy, LevelEventType.PARTICLE_WITCH_SPELL);
        put(38 + legacy, LevelEventType.PARTICLE_CARROT);
        put(39 + legacy, LevelEventType.PARTICLE_MOB_APPEARANCE);
        put(40 + legacy, LevelEventType.PARTICLE_END_ROD);
        put(41 + legacy, LevelEventType.PARTICLE_DRAGONS_BREATH);
        put(42 + legacy, LevelEventType.PARTICLE_SPIT);
        put(43 + legacy, LevelEventType.PARTICLE_TOTEM);
        put(44 + legacy, LevelEventType.PARTICLE_FOOD);
    }};

    @Override
    public @NotNull EntityLinkData readEntityLink(@NotNull BitInput input) throws IOException {
        long from = readLong(input);
        long to = readLong(input);
        int type = readUnsignedByte(input);
        boolean immediate = input.readBoolean();

        return new EntityLinkData(from, to, EntityLinkData.Type.values()[type], immediate);
    }

    @Override
    public @NotNull ItemData readItem(@NotNull BitInput input) throws IOException {
        Preconditions.checkNotNull(input, "input");

        int id = readInt(input);
        if (id == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        int aux = readInt(input);
        int damage = (short) (aux >> 8);
        if (damage == Short.MAX_VALUE) damage = -1;
        int count = aux & 0xff;
        short nbtSize = readShortLE(input);

        NbtMap compoundTag = null;
        if (nbtSize > 0) {
            compoundTag = readTag(input);
        }

        String[] canPlace = readArray(input, this::readString);
        String[] canBreak = readArray(input, this::readString);

        return ItemData.builder()
                .id(id)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .build();
    }

    @Override
    public @NotNull ItemData readItemInstance(@NotNull BitInput input) throws IOException {
        return readItem(input);
    }

    @Override
    public @NotNull CommandOriginData readCommandOrigin(@NotNull BitInput input) throws IOException {
        CommandOriginType origin = CommandOriginType.values()[readUnsignedInt(input)];
        UUID uuid = readUuid(input);
        String requestId = readString(input);
        long varLong = -1;
        if (origin == CommandOriginType.DEV_CONSOLE || origin == CommandOriginType.TEST) {
            varLong = readLong(input);
        }
        return new CommandOriginData(origin, uuid, requestId, varLong);
    }

    @Override
    public @NotNull GameRuleData<?> readGameRule(@NotNull BitInput input) throws IOException {
        Preconditions.checkNotNull(input, "input");

        String name = readString(input);
        int type = readUnsignedInt(input);

        return switch (type) {
            case 1 -> new GameRuleData<>(name, input.readBoolean());
            case 2 -> new GameRuleData<>(name, readUnsignedInt(input));
            case 3 -> new GameRuleData<>(name, readFloatLE(input));
            default -> throw new IllegalArgumentException("Unknown game rule type: " + type);
        };
    }

    @Override
    public @NotNull Map<EntityData.Type<?>, EntityData> readEntityData(@NotNull BitInput input) throws IOException {
        int length = readUnsignedInt(input);
        Map<EntityData.Type<?>, EntityData> type2Data = new HashMap<>();

        for (int i = 0; i < length; i++) {
            EntityData.Type<?> type = id2entityData.get(readUnsignedInt(input));
            EntityData.ValueType<?> valueType = id2entityDataType.get(readUnsignedInt(input));
            //TODO: Improve the entitydata type system to allow for different read methods depending on the value type.
            if (type == EntityData.DISPLAY_OFFSET_INT || type == EntityData.DISPLAY_OFFSET_LONG) {
                if (valueType == EntityData.ValueType.INT) {
                    type = EntityData.DISPLAY_OFFSET_INT;
                } else if (valueType == EntityData.ValueType.LONG) {
                    type = EntityData.DISPLAY_OFFSET_LONG;
                }
            }
            type2Data.put(type, type.read(input));
        }

        return Collections.unmodifiableMap(type2Data);
    }

    @Override
    public @NotNull CommandEnumData readCommandEnum(@NotNull BitInput input, boolean soft) throws IOException {
        String name = readString(input);
        String[] values = readArray(input, this::readString);
        return new CommandEnumData(name, values, soft);
    }

    @Override
    public @NotNull StructureSettings readStructureSettings(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Structures are not present in v291");
    }

    @Override
    public @NotNull SerializedSkin readSkin(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Skins are not present in v291");
    }

    @Override
    public @NotNull AnimationData readAnimationData(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Animations are not present in v291");
    }

    @Override
    public @NotNull ImageData readImage(@NotNull BitInput input) {
        throw new UnsupportedOperationException("Images are not present in v291");
    }
}
