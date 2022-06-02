package com.nukkitx.protocol.bedrock.v291;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v291.serializer.*;

public class Bedrock_v291 extends BedrockPacketCodec {
    public static final Bedrock_v291 INSTANCE = new Bedrock_v291();

    protected Bedrock_v291(int protocolVersion, String minecraftVersion) {
        super(protocolVersion, minecraftVersion);
    }

    private Bedrock_v291() {
        super(291, "1.7.0");

        packet(1, LoginReader_v291.INSTANCE);
        packet(2, PlayStatusReader_v291.INSTANCE);
        packet(3, ServerToClientHandshakeReader_v291.INSTANCE);
        packet(4, ClientToServerHandshakeReader_v291.INSTANCE);
        packet(5, DisconnectReader_v291.INSTANCE);
        packet(6, ResourcePacksInfoReader_v291.INSTANCE);
        packet(7, ResourcePackStackReader_v291.INSTANCE);
        packet(8, ResourcePackClientResponseReader_v291.INSTANCE);
        packet(9, TextReader_v291.INSTANCE);
        packet(10, SetTimeReader_v291.INSTANCE);
        packet(11, StartGameReader_v291.INSTANCE);
        packet(12, AddPlayerReader_v291.INSTANCE);
        packet(13, AddEntityInterpreter_v291.INSTANCE);
        packet(14, RemoveEntityReader_v291.INSTANCE);
        packet(15, AddItemEntityReader_v291.INSTANCE);
        packet(16, AddHangingEntityReader_v291.INSTANCE);
        packet(17, TakeItemEntityReader_v291.INSTANCE);
        packet(18, MoveEntityAbsoluteReader_v291.INSTANCE);
        packet(19, MovePlayerReader_v291.INSTANCE);
        packet(20, RiderJumpReader_v291.INSTANCE);
        packet(21, UpdateBlockReader_v291.INSTANCE);
        packet(22, AddPaintingReader_v291.INSTANCE);
        packet(23, ExplodeReader_v291.INSTANCE);
        packet(24, LevelSoundEvent1Reader_v291.INSTANCE);
        packet(25, LevelEventReader_v291.INSTANCE);
        packet(26, BlockEventReader_v291.INSTANCE);
        packet(27, EntityEventReader_v291.INSTANCE);
        packet(28, MobEffectReader_v291.INSTANCE);
        packet(29, UpdateAttributesReader_v291.INSTANCE);
        packet(30, InventoryTransactionReader_v291.INSTANCE);
        packet(31, MobEquipmentReader_v291.INSTANCE);
        packet(32, MobArmorEquipmentReader_v291.INSTANCE);
        packet(33, InteractReader_v291.INSTANCE);
        packet(34, BlockPickRequestReader_v291.INSTANCE);
        packet(35, EntityPickRequestReader_v291.INSTANCE);
        packet(36, PlayerActionReader_v291.INSTANCE);
        packet(37, EntityFallReader_v291.INSTANCE);
        packet(38, HurtArmorReader_v291.INSTANCE);
        packet(39, SetEntityDataReader_v291.INSTANCE);
        packet(40, SetEntityMotionReader_v291.INSTANCE);
        packet(41, SetEntityLinkReader_v291.INSTANCE);
        packet(42, SetHealthReader_v291.INSTANCE);
        packet(43, SetSpawnPositionReader_v291.INSTANCE);
        packet(44, AnimateReader_v291.INSTANCE);
        packet(45, RespawnReader_v291.INSTANCE);
        packet(46, ContainerOpenReader_v291.INSTANCE);
        packet(47, ContainerCloseReader_v291.INSTANCE);
        packet(48, PlayerHotbarReader_v291.INSTANCE);
        packet(49, InventoryContentReader_v291.INSTANCE);
        packet(50, InventorySlotReader_v291.INSTANCE);
        packet(51, ContainerSetDataReader_v291.INSTANCE);
        packet(52, CraftingDataReader_v291.INSTANCE);
        packet(53, CraftingEventReader_v291.INSTANCE);
        packet(54, GuiDataPickItemReader_v291.INSTANCE);
        packet(55, AdventureSettingsReader_v291.INSTANCE);
        packet(56, BlockEntityDataReader_v291.INSTANCE);
        packet(57, PlayerInputReader_v291.INSTANCE);
        packet(58, FullChunkDataReader_v291.INSTANCE);
        packet(59, SetCommandsEnabledReader_v291.INSTANCE);
        packet(60, SetDifficultyReader_v291.INSTANCE);
        packet(61, ChangeDimensionReader_v291.INSTANCE);
        packet(62, SetPlayerGameTypeReader_v291.INSTANCE);
        packet(63, PlayerListReader_v291.INSTANCE);
        packet(64, SimpleEventReader_v291.INSTANCE);
        packet(65, EventReader_v291.INSTANCE);
        packet(66, SpawnExperienceOrbReader_v291.INSTANCE);
        packet(67, ClientboundMapItemDataReader_v291.INSTANCE);
        packet(68, MapInfoRequestReader_v291.INSTANCE);
        packet(69, RequestChunkRadiusReader_v291.INSTANCE);
        packet(70, ChunkRadiusUpdatedReader_v291.INSTANCE);
        packet(71, ItemFrameDropItemReader_v291.INSTANCE);
        packet(72, GameRulesChangedReader_v291.INSTANCE);
        packet(73, CameraReader_v291.INSTANCE);
        packet(74, BossEventReader_v291.INSTANCE);
        packet(75, ShowCreditsReader_v291.INSTANCE);
        packet(76, AvailableCommandsReader_v291.INSTANCE);
        packet(77, CommandRequestReader_v291.INSTANCE);
        packet(78, CommandBlockUpdateReader_v291.INSTANCE);
        packet(79, CommandOutputReader_v291.INSTANCE);
        packet(80, UpdateTradeReader_v291.INSTANCE);
        packet(81, UpdateEquipReader_v291.INSTANCE);
        packet(82, ResourcePackDataInfoReader_v291.INSTANCE);
        packet(83, ResourcePackChunkDataReader_v291.INSTANCE);
        packet(84, ResourcePackChunkRequestReader_v291.INSTANCE);
        packet(85, TransferReader_v291.INSTANCE);
        packet(86, PlaySoundReader_v291.INSTANCE);
        packet(87, StopSoundReader_v291.INSTANCE);
        packet(88, SetTitleReader_v291.INSTANCE);
        packet(89, AddBehaviorTreeReader_v291.INSTANCE);
        packet(90, StructureBlockUpdateReader_v291.INSTANCE);
        packet(91, ShowStoreOfferReader_v291.INSTANCE);
        packet(92, PurchaseReceiptReader_v291.INSTANCE);
        packet(93, PlayerSkinReader_v291.INSTANCE);
        packet(94, SubClientLoginReader_v291.INSTANCE);
        packet(95, AutomationClientConnectReader_v291.INSTANCE);
        packet(96, SetLastHurtByReader_v291.INSTANCE);
        packet(97, BookEditReader_v291.INSTANCE);
        packet(98, NpcRequestReader_v291.INSTANCE);
        packet(99, PhotoTransferReader_v291.INSTANCE);
        packet(100, ModalFormRequestReader_v291.INSTANCE);
        packet(101, ModalFormResponseReader_v291.INSTANCE);
        packet(102, ServerSettingsRequestReader_v291.INSTANCE);
        packet(103, ServerSettingsResponseReader_v291.INSTANCE);
        packet(104, ShowProfileReader_v291.INSTANCE);
        packet(105, SetDefaultGameTypeReader_v291.INSTANCE);
        packet(106, RemoveObjectiveReader_v291.INSTANCE);
        packet(107, SetDisplayObjectiveReader_v291.INSTANCE);
        packet(108, SetScoreReader_v291.INSTANCE);
        packet(109, LabTableReader_v291.INSTANCE);
        packet(110, UpdateBlockSyncedReader_v291.INSTANCE);
        packet(111, MoveEntityDeltaReader_v291.INSTANCE);
        packet(112, SetScoreboardIdentityReader_v291.INSTANCE);
        packet(113, SetLocalPlayerAsInitializedReader_v291.INSTANCE);
        packet(114, UpdateSoftEnumReader_v291.INSTANCE);
        packet(115, NetworkStackLatencyReader_v291.INSTANCE);
        packet(117, ScriptCustomEventReader_v291.INSTANCE);
    }
}
