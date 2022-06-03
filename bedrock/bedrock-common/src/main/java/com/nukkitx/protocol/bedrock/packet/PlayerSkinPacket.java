package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

public interface PlayerSkinPacket extends BedrockPacket {
    private UUID uuid;
    private SerializedSkin skin;
    private String newSkinName;
    private String oldSkinName;
    private boolean trustedSkin;


    public class PlayerSkinReader_v291 implements BedrockPacketReader<PlayerSkinPacket> {
        public static final PlayerSkinReader_v291 INSTANCE = new PlayerSkinReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
            helper.writeUuid(buffer, packet.getUuid());
            SerializedSkin skin = packet.getSkin();
            helper.writeString(buffer, skin.getSkinId());
            helper.writeString(buffer, packet.getNewSkinName());
            helper.writeString(buffer, packet.getOldSkinName());
            skin.getSkinData().checkLegacySkinSize();
            helper.writeByteArray(buffer, skin.getSkinData().getImage());
            skin.getCapeData().checkLegacyCapeSize();
            helper.writeByteArray(buffer, skin.getCapeData().getImage());
            helper.writeString(buffer, skin.getGeometryName());
            helper.writeString(buffer, skin.getGeometryData());
            buffer.writeBoolean(skin.isPremium());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
            packet.setUuid(helper.readUuid(buffer));
            String skinId = helper.readString(buffer);
            packet.setNewSkinName(helper.readString(buffer));
            packet.setOldSkinName(helper.readString(buffer));
            ImageData skinData = ImageData.of(helper.readByteArray(buffer));
            ImageData capeData = ImageData.of(64, 32, helper.readByteArray(buffer));
            String geometryName = helper.readString(buffer);
            String geometryData = helper.readString(buffer);
            boolean premium = buffer.readBoolean();
            packet.setSkin(SerializedSkin.of(skinId, "", skinData, capeData, geometryName, geometryData, premium));
        }
    }


    public class PlayerSkinReader_v388 implements BedrockPacketReader<PlayerSkinPacket> {
        public static final PlayerSkinReader_v388 INSTANCE = new PlayerSkinReader_v388();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
            helper.writeUuid(buffer, packet.getUuid());
            helper.writeSkin(buffer, packet.getSkin());
            helper.writeString(buffer, packet.getNewSkinName());
            helper.writeString(buffer, packet.getOldSkinName());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
            packet.setUuid(helper.readUuid(buffer));
            packet.setSkin(helper.readSkin(buffer));
            packet.setNewSkinName(helper.readString(buffer));
            packet.setOldSkinName(helper.readString(buffer));
        }
    }

    public class PlayerSkinReader_v390 extends PlayerSkinReader_v388 {
        public static final PlayerSkinReader_v390 INSTANCE = new PlayerSkinReader_v390();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
            super.serialize(buffer, helper, packet);

            buffer.writeBoolean(packet.isTrustedSkin());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
            super.deserialize(buffer, helper, packet);

            if (buffer.isReadable()) packet.setTrustedSkin(buffer.readBoolean());
        }
    }


}
