package com.nukkitx.protocol.bedrock.v291;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.packet.*;

public class Bedrock_v291 extends BedrockPacketCodec {
    public static final Bedrock_v291 INSTANCE = new Bedrock_v291();

    protected Bedrock_v291(int protocolVersion, String minecraftVersion) {
        super(protocolVersion, minecraftVersion);
    }

    private Bedrock_v291() {
        super(291, "1.7.0");

        packet(1, LoginPacket.v291.INTERPRETER);
        packet(2, PlayStatusPacket.v291.INTERPRETER);
        packet(3, ServerToClientHandshakePacket.v291.INTERPRETER);
        packet(4, ClientToServerHandshakePacket.v291.INTERPRETER);
        packet(5, DisconnectPacket.v291.INTERPRETER);
        packet(6, ResourcePacksInfoPacket.v291.INTERPRETER);
        packet(7, ResourcePackStackPacket.v291.INTERPRETER);
        packet(8, ResourcePackClientResponsePacket.v291.INTERPRETER);
        packet(9, TextPacket.v291.INTERPRETER);
        packet(10, SetTimePacket.v291.INTERPRETER);
        packet(11, StartGamePacket.v291.INTERPRETER);
        packet(12, AddPlayerPacket.v291.INTERPRETER);
        packet(13, AddEntityPacket.v291.INTERPRETER);
        packet(14, RemoveEntityPacket.v291.INTERPRETER);
        packet(15, AddItemEntityPacket.v291.INTERPRETER);
        packet(16, AddHangingEntityPacket.v291.INTERPRETER);
        packet(17, TakeItemEntityPacket.v291.INTERPRETER);
        packet(18, MoveEntityAbsolutePacket.v291.INTERPRETER);
        packet(19, MovePlayerPacket.v291.INTERPRETER);
        packet(20, RiderJumpPacket.v291.INTERPRETER);
        packet(21, UpdateBlockPacket.v291.INTERPRETER);
        packet(22, AddPaintingPacket.v291.INTERPRETER);
        packet(23, ExplodePacket.v291.INTERPRETER);
        packet(24, LevelSoundEvent1Packet.v291.INTERPRETER);
        packet(25, LevelEventPacket.v291.INTERPRETER);
        packet(26, BlockEventPacket.v291.INTERPRETER);
        packet(27, EntityEventPacket.v291.INTERPRETER);
        packet(28, MobEffectPacket.v291.INTERPRETER);
        packet(29, UpdateAttributesPacket.v291.INTERPRETER);
        packet(30, InventoryTransactionPacket.v291.INTERPRETER);
        packet(31, MobEquipmentPacket.v291.INTERPRETER);
        packet(32, MobArmorEquipmentPacket.v291.INTERPRETER);
        packet(33, InteractPacket.v291.INTERPRETER);
        packet(34, BlockPickRequestPacket.v291.INTERPRETER);
        packet(35, EntityPickRequestPacket.v291.INTERPRETER);
        packet(36, PlayerActionPacket.v291.INTERPRETER);
        packet(37, EntityFallPacket.v291.INTERPRETER);
        packet(38, HurtArmorPacket.v291.INTERPRETER);
        packet(39, SetEntityDataPacket.v291.INTERPRETER);
        packet(40, SetEntityMotionPacket.v291.INTERPRETER);
        packet(41, SetEntityLinkPacket.v291.INTERPRETER);
        packet(42, SetHealthPacket.v291.INTERPRETER);
        packet(43, SetSpawnPositionPacket.v291.INTERPRETER);
        packet(44, AnimatePacket.v291.INTERPRETER);
        packet(45, RespawnPacket.v291.INTERPRETER);
        packet(46, ContainerOpenPacket.v291.INTERPRETER);
        packet(47, ContainerClosePacket.v291.INTERPRETER);
        packet(48, PlayerHotbarPacket.v291.INTERPRETER);
        packet(49, InventoryContentPacket.v291.INTERPRETER);
        packet(50, InventorySlotPacket.v291.INTERPRETER);
        packet(51, ContainerSetDataPacket.v291.INTERPRETER);
        packet(52, CraftingDataPacket.v291.INTERPRETER);
        packet(53, CraftingEventPacket.v291.INTERPRETER);
        packet(54, GuiDataPickItemPacket.v291.INTERPRETER);
        packet(55, AdventureSettingsPacket.v291.INTERPRETER);
        packet(56, BlockEntityDataPacket.v291.INTERPRETER);
        packet(57, PlayerInputPacket.v291.INTERPRETER);
        packet(58, FullChunkDataPacket.v291.INTERPRETER);
        packet(59, SetCommandsEnabledPacket.v291.INTERPRETER);
        packet(60, SetDifficultyPacket.v291.INTERPRETER);
        packet(61, ChangeDimensionPacket.v291.INTERPRETER);
        packet(62, SetPlayerGameTypePacket.v291.INTERPRETER);
        packet(63, PlayerListPacket.v291.INTERPRETER);
        packet(64, SimpleEventPacket.v291.INTERPRETER);
        packet(65, EventPacket.v291.INTERPRETER);
        packet(66, SpawnExperienceOrbPacket.v291.INTERPRETER);
        packet(67, ClientboundMapItemDataPacket.v291.INTERPRETER);
        packet(68, MapInfoRequestPacket.v291.INTERPRETER);
        packet(69, RequestChunkRadiusPacket.v291.INTERPRETER);
        packet(70, ChunkRadiusUpdatedPacket.v291.INTERPRETER);
        packet(71, ItemFrameDropItemPacket.v291.INTERPRETER);
        packet(72, GameRulesChangedPacket.v291.INTERPRETER);
        packet(73, CameraPacket.v291.INTERPRETER);
        packet(74, BossEventPacket.v291.INTERPRETER);
        packet(75, ShowCreditsPacket.v291.INTERPRETER);
        packet(76, AvailableCommandsPacket.v291.INTERPRETER);
        packet(77, CommandRequestPacket.v291.INTERPRETER);
        packet(78, CommandBlockUpdatePacket.v291.INTERPRETER);
        packet(79, CommandOutputPacket.v291.INTERPRETER);
        packet(80, UpdateTradePacket.v291.INTERPRETER);
        packet(81, UpdateEquipPacket.v291.INTERPRETER);
        packet(82, ResourcePackDataInfoPacket.v291.INTERPRETER);
        packet(83, ResourcePackChunkDataPacket.v291.INTERPRETER);
        packet(84, ResourcePackChunkRequestPacket.v291.INTERPRETER);
        packet(85, TransferPacket.v291.INTERPRETER);
        packet(86, PlaySoundPacket.v291.INTERPRETER);
        packet(87, StopSoundPacket.v291.INTERPRETER);
        packet(88, SetTitlePacket.v291.INTERPRETER);
        packet(89, AddBehaviorTreePacket.v291.INTERPRETER);
        packet(90, StructureBlockUpdatePacket.v291.INTERPRETER);
        packet(91, ShowStoreOfferPacket.v291.INTERPRETER);
        packet(92, PurchaseReceiptPacket.v291.INTERPRETER);
        packet(93, PlayerSkinPacket.v291.INTERPRETER);
        packet(94, SubClientLoginPacket.v291.INTERPRETER);
        packet(95, AutomationClientConnectPacket.v291.INTERPRETER);
        packet(96, SetLastHurtByPacket.v291.INTERPRETER);
        packet(97, BookEditPacket.v291.INTERPRETER);
        packet(98, NpcRequestPacket.v291.INTERPRETER);
        packet(99, PhotoTransferPacket.v291.INTERPRETER);
        packet(100, ModalFormRequestPacket.v291.INTERPRETER);
        packet(101, ModalFormResponsePacket.v291.INTERPRETER);
        packet(102, ServerSettingsRequestPacket.v291.INTERPRETER);
        packet(103, ServerSettingsResponsePacket.v291.INTERPRETER);
        packet(104, ShowProfilePacket.v291.INTERPRETER);
        packet(105, SetDefaultGameTypePacket.v291.INTERPRETER);
        packet(106, RemoveObjectivePacket.v291.INTERPRETER);
        packet(107, SetDisplayObjectivePacket.v291.INTERPRETER);
        packet(108, SetScorePacket.v291.INTERPRETER);
        packet(109, LabTablePacket.v291.INTERPRETER);
        packet(110, UpdateBlockSyncedPacket.v291.INTERPRETER);
        packet(111, MoveEntityDeltaPacket.v291.INTERPRETER);
        packet(112, SetScoreboardIdentityPacket.v291.INTERPRETER);
        packet(113, SetLocalPlayerAsInitializedPacket.v291.INTERPRETER);
        packet(114, UpdateSoftEnumPacket.v291.INTERPRETER);
        packet(115, NetworkStackLatencyPacket.v291.INTERPRETER);
        packet(117, ScriptCustomEventPacket.v291.INTERPRETER);
    }
}
