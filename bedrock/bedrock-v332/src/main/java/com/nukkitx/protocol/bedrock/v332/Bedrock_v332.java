package com.nukkitx.protocol.bedrock.v332;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.protocol.bedrock.v291.serializer.*;
import com.nukkitx.protocol.bedrock.v313.serializer.*;
import com.nukkitx.protocol.bedrock.v332.serializer.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Bedrock_v332 {
    public static BedrockPacketCodec V332_CODEC = BedrockPacketCodec.builder()
            .protocolVersion(332)
            .minecraftVersion("1.9.0")
            .helper(BedrockPacketReader_v332.INSTANCE)
            .registerPacket(LoginPacket.class, LoginReader_v291.INSTANCE, 1)
            .registerPacket(PlayStatusPacket.class, PlayStatusReader_v291.INSTANCE, 2)
            .registerPacket(ServerToClientHandshakePacket.class, ServerToClientHandshakeReader_v291.INSTANCE, 3)
            .registerPacket(ClientToServerHandshakePacket.class, ClientToServerHandshakeReader_v291.INSTANCE, 4)
            .registerPacket(DisconnectPacket.class, DisconnectReader_v291.INSTANCE, 5)
            .registerPacket(ResourcePacksInfoPacket.class, ResourcePacksInfoReader_v332.INSTANCE, 6)
            .registerPacket(ResourcePackStackPacket.class, ResourcePackStackReader_v313.INSTANCE, 7)
            .registerPacket(ResourcePackClientResponsePacket.class, ResourcePackClientResponseReader_v291.INSTANCE, 8)
            .registerPacket(TextPacket.class, TextReader_v332.INSTANCE, 9)
            .registerPacket(SetTimePacket.class, SetTimeReader_v291.INSTANCE, 10)
            .registerPacket(StartGamePacket.class, StartGameReader_v332.INSTANCE, 11)
            .registerPacket(AddPlayerPacket.class, AddPlayerReader_v291.INSTANCE, 12)
            .registerPacket(AddEntityPacket.class, AddEntityReader_v313.INSTANCE, 13)
            .registerPacket(RemoveEntityPacket.class, RemoveEntityReader_v291.INSTANCE, 14)
            .registerPacket(AddItemEntityPacket.class, AddItemEntityReader_v291.INSTANCE, 15)
            .registerPacket(AddHangingEntityPacket.class, AddHangingEntityReader_v291.INSTANCE, 16)
            .registerPacket(TakeItemEntityPacket.class, TakeItemEntityReader_v291.INSTANCE, 17)
            .registerPacket(MoveEntityAbsolutePacket.class, MoveEntityAbsoluteReader_v291.INSTANCE, 18)
            .registerPacket(MovePlayerPacket.class, MovePlayerReader_v291.INSTANCE, 19)
            .registerPacket(RiderJumpPacket.class, RiderJumpReader_v291.INSTANCE, 20)
            .registerPacket(UpdateBlockPacket.class, UpdateBlockReader_v291.INSTANCE, 21)
            .registerPacket(AddPaintingPacket.class, AddPaintingReader_v332.INSTANCE, 22)
            .registerPacket(ExplodePacket.class, ExplodeReader_v291.INSTANCE, 23)
            .registerPacket(LevelSoundEvent1Packet.class, LevelSoundEvent1Reader_v291.INSTANCE, 24)
            .registerPacket(LevelEventPacket.class, LevelEventReader_v291.INSTANCE, 25)
            .registerPacket(BlockEventPacket.class, BlockEventReader_v291.INSTANCE, 26)
            .registerPacket(EntityEventPacket.class, EntityEventReader_v291.INSTANCE, 27)
            .registerPacket(MobEffectPacket.class, MobEffectReader_v291.INSTANCE, 28)
            .registerPacket(UpdateAttributesPacket.class, UpdateAttributesReader_v291.INSTANCE, 29)
            .registerPacket(InventoryTransactionPacket.class, InventoryTransactionReader_v291.INSTANCE, 30)
            .registerPacket(MobEquipmentPacket.class, MobEquipmentReader_v291.INSTANCE, 31)
            .registerPacket(MobArmorEquipmentPacket.class, MobArmorEquipmentReader_v291.INSTANCE, 32)
            .registerPacket(InteractPacket.class, InteractReader_v291.INSTANCE, 33)
            .registerPacket(BlockPickRequestPacket.class, BlockPickRequestReader_v291.INSTANCE, 34)
            .registerPacket(EntityPickRequestPacket.class, EntityPickRequestReader_v291.INSTANCE, 35)
            .registerPacket(PlayerActionPacket.class, PlayerActionReader_v291.INSTANCE, 36)
            .registerPacket(EntityFallPacket.class, EntityFallReader_v291.INSTANCE, 37)
            .registerPacket(HurtArmorPacket.class, HurtArmorReader_v291.INSTANCE, 38)
            .registerPacket(SetEntityDataPacket.class, SetEntityDataReader_v291.INSTANCE, 39)
            .registerPacket(SetEntityMotionPacket.class, SetEntityMotionReader_v291.INSTANCE, 40)
            .registerPacket(SetEntityLinkPacket.class, SetEntityLinkReader_v291.INSTANCE, 41)
            .registerPacket(SetHealthPacket.class, SetHealthReader_v291.INSTANCE, 42)
            .registerPacket(SetSpawnPositionPacket.class, SetSpawnPositionReader_v291.INSTANCE, 43)
            .registerPacket(AnimatePacket.class, AnimateReader_v291.INSTANCE, 44)
            .registerPacket(RespawnPacket.class, RespawnReader_v291.INSTANCE, 45)
            .registerPacket(ContainerOpenPacket.class, ContainerOpenReader_v291.INSTANCE, 46)
            .registerPacket(ContainerClosePacket.class, ContainerCloseReader_v291.INSTANCE, 47)
            .registerPacket(PlayerHotbarPacket.class, PlayerHotbarReader_v291.INSTANCE, 48)
            .registerPacket(InventoryContentPacket.class, InventoryContentReader_v291.INSTANCE, 49)
            .registerPacket(InventorySlotPacket.class, InventorySlotReader_v291.INSTANCE, 50)
            .registerPacket(ContainerSetDataPacket.class, ContainerSetDataReader_v291.INSTANCE, 51)
            .registerPacket(CraftingDataPacket.class, CraftingDataReader_v291.INSTANCE, 52)
            .registerPacket(CraftingEventPacket.class, CraftingEventReader_v291.INSTANCE, 53)
            .registerPacket(GuiDataPickItemPacket.class, GuiDataPickItemReader_v291.INSTANCE, 54)
            .registerPacket(AdventureSettingsPacket.class, AdventureSettingsReader_v291.INSTANCE, 55)
            .registerPacket(BlockEntityDataPacket.class, BlockEntityDataReader_v291.INSTANCE, 56)
            .registerPacket(PlayerInputPacket.class, PlayerInputReader_v291.INSTANCE, 57)
            .registerPacket(LevelChunkPacket.class, FullChunkDataReader_v291.INSTANCE, 58)
            .registerPacket(SetCommandsEnabledPacket.class, SetCommandsEnabledReader_v291.INSTANCE, 59)
            .registerPacket(SetDifficultyPacket.class, SetDifficultyReader_v291.INSTANCE, 60)
            .registerPacket(ChangeDimensionPacket.class, ChangeDimensionReader_v291.INSTANCE, 61)
            .registerPacket(SetPlayerGameTypePacket.class, SetPlayerGameTypeReader_v291.INSTANCE, 62)
            .registerPacket(PlayerListPacket.class, PlayerListReader_v291.INSTANCE, 63)
            .registerPacket(SimpleEventPacket.class, SimpleEventReader_v291.INSTANCE, 64)
            .registerPacket(EventPacket.class, EventReader_v332.INSTANCE, 65)
            .registerPacket(SpawnExperienceOrbPacket.class, SpawnExperienceOrbReader_v291.INSTANCE, 66)
            .registerPacket(ClientboundMapItemDataPacket.class, ClientboundMapItemDataReader_v291.INSTANCE, 67)
            .registerPacket(MapInfoRequestPacket.class, MapInfoRequestReader_v291.INSTANCE, 68)
            .registerPacket(RequestChunkRadiusPacket.class, RequestChunkRadiusReader_v291.INSTANCE, 69)
            .registerPacket(ChunkRadiusUpdatedPacket.class, ChunkRadiusUpdatedReader_v291.INSTANCE, 70)
            .registerPacket(ItemFrameDropItemPacket.class, ItemFrameDropItemReader_v291.INSTANCE, 71)
            .registerPacket(GameRulesChangedPacket.class, GameRulesChangedReader_v291.INSTANCE, 72)
            .registerPacket(CameraPacket.class, CameraReader_v291.INSTANCE, 73)
            .registerPacket(BossEventPacket.class, BossEventReader_v291.INSTANCE, 74)
            .registerPacket(ShowCreditsPacket.class, ShowCreditsReader_v291.INSTANCE, 75)
            .registerPacket(AvailableCommandsPacket.class, AvailableCommandsReader_v291.INSTANCE, 76)
            .registerPacket(CommandRequestPacket.class, CommandRequestReader_v291.INSTANCE, 77)
            .registerPacket(CommandBlockUpdatePacket.class, CommandBlockUpdateReader_v291.INSTANCE, 78)
            .registerPacket(CommandOutputPacket.class, CommandOutputReader_v291.INSTANCE, 79)
            .registerPacket(UpdateTradePacket.class, UpdateTradeReader_v313.INSTANCE, 80)
            .registerPacket(UpdateEquipPacket.class, UpdateEquipReader_v291.INSTANCE, 81)
            .registerPacket(ResourcePackDataInfoPacket.class, ResourcePackDataInfoReader_v291.INSTANCE, 82)
            .registerPacket(ResourcePackChunkDataPacket.class, ResourcePackChunkDataReader_v291.INSTANCE, 83)
            .registerPacket(ResourcePackChunkRequestPacket.class, ResourcePackChunkRequestReader_v291.INSTANCE, 84)
            .registerPacket(TransferPacket.class, TransferReader_v291.INSTANCE, 85)
            .registerPacket(PlaySoundPacket.class, PlaySoundReader_v291.INSTANCE, 86)
            .registerPacket(StopSoundPacket.class, StopSoundReader_v291.INSTANCE, 87)
            .registerPacket(SetTitlePacket.class, SetTitleReader_v291.INSTANCE, 88)
            .registerPacket(AddBehaviorTreePacket.class, AddBehaviorTreeReader_v291.INSTANCE, 89)
            .registerPacket(StructureBlockUpdatePacket.class, StructureBlockUpdateReader_v291.INSTANCE, 90)
            .registerPacket(ShowStoreOfferPacket.class, ShowStoreOfferReader_v291.INSTANCE, 91)
            .registerPacket(PurchaseReceiptPacket.class, PurchaseReceiptReader_v291.INSTANCE, 92)
            .registerPacket(PlayerSkinPacket.class, PlayerSkinReader_v291.INSTANCE, 93)
            .registerPacket(SubClientLoginPacket.class, SubClientLoginReader_v291.INSTANCE, 94)
            .registerPacket(AutomationClientConnectPacket.class, AutomationClientConnectReader_v291.INSTANCE, 95)
            .registerPacket(SetLastHurtByPacket.class, SetLastHurtByReader_v291.INSTANCE, 96)
            .registerPacket(BookEditPacket.class, BookEditReader_v291.INSTANCE, 97)
            .registerPacket(NpcRequestPacket.class, NpcRequestReader_v291.INSTANCE, 98)
            .registerPacket(PhotoTransferPacket.class, PhotoTransferReader_v291.INSTANCE, 99)
            .registerPacket(ModalFormRequestPacket.class, ModalFormRequestReader_v291.INSTANCE, 100)
            .registerPacket(ModalFormResponsePacket.class, ModalFormResponseReader_v291.INSTANCE, 101)
            .registerPacket(ServerSettingsRequestPacket.class, ServerSettingsRequestReader_v291.INSTANCE, 102)
            .registerPacket(ServerSettingsResponsePacket.class, ServerSettingsResponseReader_v291.INSTANCE, 103)
            .registerPacket(ShowProfilePacket.class, ShowProfileReader_v291.INSTANCE, 104)
            .registerPacket(SetDefaultGameTypePacket.class, SetDefaultGameTypeReader_v291.INSTANCE, 105)
            .registerPacket(RemoveObjectivePacket.class, RemoveObjectiveReader_v291.INSTANCE, 106)
            .registerPacket(SetDisplayObjectivePacket.class, SetDisplayObjectiveReader_v291.INSTANCE, 107)
            .registerPacket(SetScorePacket.class, SetScoreReader_v291.INSTANCE, 108)
            .registerPacket(LabTablePacket.class, LabTableReader_v291.INSTANCE, 109)
            .registerPacket(UpdateBlockSyncedPacket.class, UpdateBlockSyncedReader_v291.INSTANCE, 110)
            .registerPacket(MoveEntityDeltaPacket.class, MoveEntityDeltaReader_v291.INSTANCE, 111)
            .registerPacket(SetScoreboardIdentityPacket.class, SetScoreboardIdentityReader_v291.INSTANCE, 112)
            .registerPacket(SetLocalPlayerAsInitializedPacket.class, SetLocalPlayerAsInitializedReader_v291.INSTANCE, 113)
            .registerPacket(UpdateSoftEnumPacket.class, UpdateSoftEnumReader_v291.INSTANCE, 114)
            .registerPacket(NetworkStackLatencyPacket.class, NetworkStackLatencyReader_v332.INSTANCE, 115)
            .registerPacket(ScriptCustomEventPacket.class, ScriptCustomEventReader_v291.INSTANCE, 117)
            .registerPacket(SpawnParticleEffectPacket.class, SpawnParticleEffectReader_v332.INSTANCE, 118)
            .registerPacket(AvailableEntityIdentifiersPacket.class, AvailableEntityIdentifiersReader_v313.INSTANCE, 119)
            .registerPacket(LevelSoundEvent2Packet.class, LevelSoundEvent2Reader_v313.INSTANCE, 120)
            .registerPacket(NetworkChunkPublisherUpdatePacket.class, NetworkChunkPublisherUpdateReader_v313.INSTANCE, 121)
            .registerPacket(BiomeDefinitionListPacket.class, BiomeDefinitionListReader_v313.INSTANCE, 122)
            .registerPacket(LevelSoundEventPacket.class, LevelSoundEventReader_v332.INSTANCE, 123)
            .build();
}
