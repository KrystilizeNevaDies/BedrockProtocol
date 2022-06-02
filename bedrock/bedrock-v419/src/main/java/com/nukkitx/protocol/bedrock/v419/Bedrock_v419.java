package com.nukkitx.protocol.bedrock.v419;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.protocol.bedrock.v407.serializer.*;
import com.nukkitx.protocol.bedrock.v419.serializer.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Bedrock_v419 {
    public static BedrockPacketCodec V419_CODEC = BedrockPacketCodec.builder()
            .protocolVersion(419)
            .minecraftVersion("1.16.100.60")
            .helper(BedrockPacketReader_v419.INSTANCE)
            .registerPacket(LoginPacket.class, LoginReader_v291.INSTANCE, 1)
            .registerPacket(PlayStatusPacket.class, PlayStatusReader_v291.INSTANCE, 2)
            .registerPacket(ServerToClientHandshakePacket.class, ServerToClientHandshakeReader_v291.INSTANCE, 3)
            .registerPacket(ClientToServerHandshakePacket.class, ClientToServerHandshakeReader_v291.INSTANCE, 4)
            .registerPacket(DisconnectPacket.class, DisconnectReader_v291.INSTANCE, 5)
            .registerPacket(ResourcePacksInfoPacket.class, ResourcePacksInfoReader_v332.INSTANCE, 6)
            .registerPacket(ResourcePackStackPacket.class, ResourcePackStackReader_v419.INSTANCE, 7)
            .registerPacket(ResourcePackClientResponsePacket.class, ResourcePackClientResponseReader_v291.INSTANCE, 8)
            .registerPacket(TextPacket.class, TextReader_v332.INSTANCE, 9)
            .registerPacket(SetTimePacket.class, SetTimeReader_v291.INSTANCE, 10)
            .registerPacket(StartGamePacket.class, StartGameReader_v419.INSTANCE, 11)
            .registerPacket(AddPlayerPacket.class, AddPlayerReader_v388.INSTANCE, 12)
            .registerPacket(AddEntityPacket.class, AddEntityReader_v313.INSTANCE, 13)
            .registerPacket(RemoveEntityPacket.class, RemoveEntityReader_v291.INSTANCE, 14)
            .registerPacket(AddItemEntityPacket.class, AddItemEntityReader_v291.INSTANCE, 15)
            .registerPacket(TakeItemEntityPacket.class, TakeItemEntityReader_v291.INSTANCE, 17)
            .registerPacket(MoveEntityAbsolutePacket.class, MoveEntityAbsoluteReader_v291.INSTANCE, 18)
            .registerPacket(MovePlayerPacket.class, MovePlayerReader_v419.INSTANCE, 19)
            .registerPacket(RiderJumpPacket.class, RiderJumpReader_v291.INSTANCE, 20)
            .registerPacket(UpdateBlockPacket.class, UpdateBlockReader_v291.INSTANCE, 21)
            .registerPacket(AddPaintingPacket.class, AddPaintingReader_v361.INSTANCE, 22)
            .registerPacket(TickSyncPacket.class, TickSyncReader_v388.INSTANCE, 23)
            .registerPacket(LevelSoundEvent1Packet.class, LevelSoundEvent1Reader_v291.INSTANCE, 24)
            .registerPacket(LevelEventPacket.class, LevelEventReader_v291.INSTANCE, 25)
            .registerPacket(BlockEventPacket.class, BlockEventReader_v291.INSTANCE, 26)
            .registerPacket(EntityEventPacket.class, EntityEventReader_v291.INSTANCE, 27)
            .registerPacket(MobEffectPacket.class, MobEffectReader_v291.INSTANCE, 28)
            .registerPacket(UpdateAttributesPacket.class, UpdateAttributesReader_v419.INSTANCE, 29)
            .registerPacket(InventoryTransactionPacket.class, InventoryTransactionReader_v407.INSTANCE, 30)
            .registerPacket(MobEquipmentPacket.class, MobEquipmentReader_v291.INSTANCE, 31)
            .registerPacket(MobArmorEquipmentPacket.class, MobArmorEquipmentReader_v291.INSTANCE, 32)
            .registerPacket(InteractPacket.class, InteractReader_v388.INSTANCE, 33)
            .registerPacket(BlockPickRequestPacket.class, BlockPickRequestReader_v291.INSTANCE, 34)
            .registerPacket(EntityPickRequestPacket.class, EntityPickRequestReader_v291.INSTANCE, 35)
            .registerPacket(PlayerActionPacket.class, PlayerActionReader_v291.INSTANCE, 36)
            .registerPacket(EntityFallPacket.class, EntityFallReader_v291.INSTANCE, 37)
            .registerPacket(HurtArmorPacket.class, HurtArmorReader_v407.INSTANCE, 38)
            .registerPacket(SetEntityDataPacket.class, SetEntityDataReader_v419.INSTANCE, 39)
            .registerPacket(SetEntityMotionPacket.class, SetEntityMotionReader_v291.INSTANCE, 40)
            .registerPacket(SetEntityLinkPacket.class, SetEntityLinkReader_v291.INSTANCE, 41)
            .registerPacket(SetHealthPacket.class, SetHealthReader_v291.INSTANCE, 42)
            .registerPacket(SetSpawnPositionPacket.class, SetSpawnPositionReader_v407.INSTANCE, 43)
            .registerPacket(AnimatePacket.class, AnimateReader_v291.INSTANCE, 44)
            .registerPacket(RespawnPacket.class, RespawnReader_v388.INSTANCE, 45)
            .registerPacket(ContainerOpenPacket.class, ContainerOpenReader_v291.INSTANCE, 46)
            .registerPacket(ContainerClosePacket.class, ContainerCloseReader_v419.INSTANCE, 47)
            .registerPacket(PlayerHotbarPacket.class, PlayerHotbarReader_v291.INSTANCE, 48)
            .registerPacket(InventoryContentPacket.class, InventoryContentReader_v407.INSTANCE, 49)
            .registerPacket(InventorySlotPacket.class, InventorySlotReader_v407.INSTANCE, 50)
            .registerPacket(ContainerSetDataPacket.class, ContainerSetDataReader_v291.INSTANCE, 51)
            .registerPacket(CraftingDataPacket.class, CraftingDataReader_v407.INSTANCE, 52)
            .registerPacket(CraftingEventPacket.class, CraftingEventReader_v291.INSTANCE, 53)
            .registerPacket(GuiDataPickItemPacket.class, GuiDataPickItemReader_v291.INSTANCE, 54)
            .registerPacket(AdventureSettingsPacket.class, AdventureSettingsReader_v291.INSTANCE, 55)
            .registerPacket(BlockEntityDataPacket.class, BlockEntityDataReader_v291.INSTANCE, 56)
            .registerPacket(PlayerInputPacket.class, PlayerInputReader_v291.INSTANCE, 57)
            .registerPacket(LevelChunkPacket.class, LevelChunkReader_v361.INSTANCE, 58)
            .registerPacket(SetCommandsEnabledPacket.class, SetCommandsEnabledReader_v291.INSTANCE, 59)
            .registerPacket(SetDifficultyPacket.class, SetDifficultyReader_v291.INSTANCE, 60)
            .registerPacket(ChangeDimensionPacket.class, ChangeDimensionReader_v291.INSTANCE, 61)
            .registerPacket(SetPlayerGameTypePacket.class, SetPlayerGameTypeReader_v291.INSTANCE, 62)
            .registerPacket(PlayerListPacket.class, PlayerListReader_v390.INSTANCE, 63)
            .registerPacket(SimpleEventPacket.class, SimpleEventReader_v291.INSTANCE, 64)
            .registerPacket(EventPacket.class, EventReader_v389.INSTANCE, 65)
            .registerPacket(SpawnExperienceOrbPacket.class, SpawnExperienceOrbReader_v291.INSTANCE, 66)
            .registerPacket(ClientboundMapItemDataPacket.class, ClientboundMapItemDataReader_v354.INSTANCE, 67)
            .registerPacket(MapInfoRequestPacket.class, MapInfoRequestReader_v291.INSTANCE, 68)
            .registerPacket(RequestChunkRadiusPacket.class, RequestChunkRadiusReader_v291.INSTANCE, 69)
            .registerPacket(ChunkRadiusUpdatedPacket.class, ChunkRadiusUpdatedReader_v291.INSTANCE, 70)
            .registerPacket(ItemFrameDropItemPacket.class, ItemFrameDropItemReader_v291.INSTANCE, 71)
            .registerPacket(GameRulesChangedPacket.class, GameRulesChangedReader_v291.INSTANCE, 72)
            .registerPacket(CameraPacket.class, CameraReader_v291.INSTANCE, 73)
            .registerPacket(BossEventPacket.class, BossEventReader_v291.INSTANCE, 74)
            .registerPacket(ShowCreditsPacket.class, ShowCreditsReader_v291.INSTANCE, 75)
            .registerPacket(AvailableCommandsPacket.class, AvailableCommandsReader_v388.INSTANCE, 76)
            .registerPacket(CommandRequestPacket.class, CommandRequestReader_v291.INSTANCE, 77)
            .registerPacket(CommandBlockUpdatePacket.class, CommandBlockUpdateReader_v361.INSTANCE, 78)
            .registerPacket(CommandOutputPacket.class, CommandOutputReader_v291.INSTANCE, 79)
            .registerPacket(UpdateTradePacket.class, UpdateTradeReader_v354.INSTANCE, 80)
            .registerPacket(UpdateEquipPacket.class, UpdateEquipReader_v291.INSTANCE, 81)
            .registerPacket(ResourcePackDataInfoPacket.class, ResourcePackDataInfoReader_v361.INSTANCE, 82)
            .registerPacket(ResourcePackChunkDataPacket.class, ResourcePackChunkDataReader_v388.INSTANCE, 83)
            .registerPacket(ResourcePackChunkRequestPacket.class, ResourcePackChunkRequestReader_v291.INSTANCE, 84)
            .registerPacket(TransferPacket.class, TransferReader_v291.INSTANCE, 85)
            .registerPacket(PlaySoundPacket.class, PlaySoundReader_v291.INSTANCE, 86)
            .registerPacket(StopSoundPacket.class, StopSoundReader_v291.INSTANCE, 87)
            .registerPacket(SetTitlePacket.class, SetTitleReader_v291.INSTANCE, 88)
            .registerPacket(AddBehaviorTreePacket.class, AddBehaviorTreeReader_v291.INSTANCE, 89)
            .registerPacket(StructureBlockUpdatePacket.class, StructureBlockUpdateReader_v388.INSTANCE, 90)
            .registerPacket(ShowStoreOfferPacket.class, ShowStoreOfferReader_v291.INSTANCE, 91)
            .registerPacket(PurchaseReceiptPacket.class, PurchaseReceiptReader_v291.INSTANCE, 92)
            .registerPacket(PlayerSkinPacket.class, PlayerSkinReader_v390.INSTANCE, 93)
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
            .registerPacket(MoveEntityDeltaPacket.class, MoveEntityDeltaReader_v419.INSTANCE, 111)
            .registerPacket(SetScoreboardIdentityPacket.class, SetScoreboardIdentityReader_v291.INSTANCE, 112)
            .registerPacket(SetLocalPlayerAsInitializedPacket.class, SetLocalPlayerAsInitializedReader_v291.INSTANCE, 113)
            .registerPacket(UpdateSoftEnumPacket.class, UpdateSoftEnumReader_v291.INSTANCE, 114)
            .registerPacket(NetworkStackLatencyPacket.class, NetworkStackLatencyReader_v332.INSTANCE, 115)
            .registerPacket(ScriptCustomEventPacket.class, ScriptCustomEventReader_v291.INSTANCE, 117)
            .registerPacket(SpawnParticleEffectPacket.class, SpawnParticleEffectReader_v332.INSTANCE, 118)
            .registerPacket(AvailableEntityIdentifiersPacket.class, AvailableEntityIdentifiersReader_v313.INSTANCE, 119)
            .registerPacket(LevelSoundEvent2Packet.class, LevelSoundEvent2Reader_v407.INSTANCE, 120)
            .registerPacket(NetworkChunkPublisherUpdatePacket.class, NetworkChunkPublisherUpdateReader_v313.INSTANCE, 121)
            .registerPacket(BiomeDefinitionListPacket.class, BiomeDefinitionListReader_v313.INSTANCE, 122)
            .registerPacket(LevelSoundEventPacket.class, LevelSoundEventReader_v407.INSTANCE, 123)
            .registerPacket(LevelEventGenericPacket.class, LevelEventGenericReader_v361.INSTANCE, 124)
            .registerPacket(LecternUpdatePacket.class, LecternUpdateReader_v354.INSTANCE, 125)
            .registerPacket(VideoStreamConnectPacket.class, VideoStreamConnectReader_v361.INSTANCE, 126)
            // AddEntityPacket 127
            // RemoveEntityPacket 128
            .registerPacket(ClientCacheStatusPacket.class, ClientCacheStatusReader_v361.INSTANCE, 129)
            .registerPacket(OnScreenTextureAnimationPacket.class, OnScreenTextureAnimationReader_v354.INSTANCE, 130)
            .registerPacket(MapCreateLockedCopyPacket.class, MapCreateLockedCopyReader_v354.INSTANCE, 131)
            .registerPacket(StructureTemplateDataRequestPacket.class, StructureTemplateDataRequestReader_v361.INSTANCE, 132)
            .registerPacket(StructureTemplateDataResponsePacket.class, StructureTemplateDataResponseReader_v388.INSTANCE, 133)
            .registerPacket(ClientCacheBlobStatusPacket.class, ClientCacheBlobStatusReader_v361.INSTANCE, 135)
            .registerPacket(ClientCacheMissResponsePacket.class, ClientCacheMissResponseReader_v361.INSTANCE, 136)
            .registerPacket(EducationSettingsPacket.class, EducationSettingsReader_v407.INSTANCE, 137)
            .registerPacket(EmotePacket.class, EmoteReader_v388.INSTANCE, 138)
            .registerPacket(MultiplayerSettingsPacket.class, MultiplayerSettingsReader_v388.INSTANCE, 139)
            .registerPacket(SettingsCommandPacket.class, SettingsCommandReader_v388.INSTANCE, 140)
            .registerPacket(AnvilDamagePacket.class, AnvilDamageReader_v388.INSTANCE, 141)
            .registerPacket(CompletedUsingItemPacket.class, CompletedUsingItemReader_v388.INSTANCE, 142)
            .registerPacket(NetworkSettingsPacket.class, NetworkSettingsReader_v388.INSTANCE, 143)
            .registerPacket(PlayerAuthInputPacket.class, PlayerAuthInputReader_v419.INSTANCE, 144)
            .registerPacket(CreativeContentPacket.class, CreativeContentReader_v407.INSTANCE, 145)
            .registerPacket(PlayerEnchantOptionsPacket.class, PlayerEnchantOptionsReader_v407.INSTANCE, 146)
            .registerPacket(ItemStackRequestPacket.class, ItemStackRequestReader_v407.INSTANCE, 147)
            .registerPacket(ItemStackResponsePacket.class, ItemStackResponseReader_v419.INSTANCE, 148)
            .registerPacket(PlayerArmorDamagePacket.class, PlayerArmorDamageReader_v407.INSTANCE, 149)
            .registerPacket(CodeBuilderPacket.class, CodeBuilderReader_v407.INSTANCE, 150)
            .registerPacket(UpdatePlayerGameTypePacket.class, UpdatePlayerGameTypeReader_v407.INSTANCE, 151)
            .registerPacket(EmoteListPacket.class, EmoteListReader_v407.INSTANCE, 152)
            .registerPacket(PositionTrackingDBServerBroadcastPacket.class, PositionTrackingDBServerBroadcastReader_v407.INSTANCE, 153)
            .registerPacket(PositionTrackingDBClientRequestPacket.class, PositionTrackingDBClientRequestReader_v407.INSTANCE, 154)
            .registerPacket(DebugInfoPacket.class, DebugInfoReader_v407.INSTANCE, 155)
            .registerPacket(PacketViolationWarningPacket.class, PacketViolationWarningReader_v407.INSTANCE, 156)
            .registerPacket(MotionPredictionHintsPacket.class, MotionPredictionHintsReader_v419.INSTANCE, 157)
            .registerPacket(AnimateEntityPacket.class, AnimateEntityReader_v419.INSTANCE, 158)
            .registerPacket(CameraShakePacket.class, CameraShakeReader_v419.INSTANCE, 159)
            .registerPacket(PlayerFogPacket.class, PlayerFogReader_v419.INSTANCE, 160)
            .registerPacket(CorrectPlayerMovePredictionPacket.class, CorrectPlayerMovePredictionReader_v419.INSTANCE, 161)
            .registerPacket(ItemComponentPacket.class, ItemComponentReader_v419.INSTANCE, 162)
            .build();
}
