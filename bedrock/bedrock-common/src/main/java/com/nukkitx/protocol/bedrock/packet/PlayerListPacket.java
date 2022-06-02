package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

interface PlayerListPacket extends BedrockPacket {
    private final List<Entry> entries = new ObjectArrayList<>();
    private Action action;


    public enum Action {
        ADD,
        REMOVE
    }

    @ToString
    @Data
    @EqualsAndHashCode(doNotUseGetters = true)
    public final static class Entry {
        private final UUID uuid;
        private long entityId;
        private String name;
        private String xuid;
        private String platformChatId;
        private int buildPlatform;
        private SerializedSkin skin;
        private boolean teacher;
        private boolean host;
        private boolean trustedSkin;
    }

    public class PlayerListReader_v291 implements BedrockPacketReader<PlayerListPacket> {
        public static final PlayerListReader_v291 INSTANCE = new PlayerListReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

            for (Entry entry : packet.getEntries()) {
                helper.writeUuid(buffer, entry.getUuid());

                if (packet.getAction() == Action.ADD) {
                    VarInts.writeLong(buffer, entry.getEntityId());
                    helper.writeString(buffer, entry.getName());
                    SerializedSkin skin = entry.getSkin();
                    helper.writeString(buffer, skin.getSkinId());
                    skin.getSkinData().checkLegacySkinSize();
                    helper.writeByteArray(buffer, skin.getSkinData().getImage());
                    skin.getCapeData().checkLegacyCapeSize();
                    helper.writeByteArray(buffer, skin.getCapeData().getImage());
                    helper.writeString(buffer, skin.getGeometryName());
                    helper.writeString(buffer, skin.getGeometryData());
                    helper.writeString(buffer, entry.getXuid());
                    helper.writeString(buffer, entry.getPlatformChatId());
                }
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
            PlayerListPacket.Action action = PlayerListPacket.Action.values()[buffer.readUnsignedByte()];
            packet.setAction(action);
            int length = VarInts.readUnsignedInt(buffer);

            for (int i = 0; i < length; i++) {
                Entry entry = new Entry(helper.readUuid(buffer));

                if (action == Action.ADD) {
                    entry.setEntityId(VarInts.readLong(buffer));
                    entry.setName(helper.readString(buffer));
                    String skinId = helper.readString(buffer);
                    ImageData skinData = ImageData.of(helper.readByteArray(buffer));
                    ImageData capeData = ImageData.of(64, 32, helper.readByteArray(buffer));
                    String geometryName = helper.readString(buffer);
                    String geometryData = helper.readString(buffer);
                    entry.setSkin(SerializedSkin.of(skinId, "", skinData, capeData, geometryName, geometryData, false));
                    entry.setXuid(helper.readString(buffer));
                    entry.setPlatformChatId(helper.readString(buffer));
                }
                packet.getEntries().add(entry);
            }
        }
    }

    public class PlayerListReader_v388 implements BedrockPacketReader<PlayerListPacket> {
        public static final PlayerListReader_v388 INSTANCE = new PlayerListReader_v388();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

            for (Entry entry : packet.getEntries()) {
                helper.writeUuid(buffer, entry.getUuid());

                if (packet.getAction() == Action.ADD) {
                    VarInts.writeLong(buffer, entry.getEntityId());
                    helper.writeString(buffer, entry.getName());
                    helper.writeString(buffer, entry.getXuid());
                    helper.writeString(buffer, entry.getPlatformChatId());
                    buffer.writeIntLE(entry.getBuildPlatform());
                    helper.writeSkin(buffer, entry.getSkin());
                    buffer.writeBoolean(entry.isTeacher());
                    buffer.writeBoolean(entry.isHost());
                }
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
            Action action = Action.values()[buffer.readUnsignedByte()];
            packet.setAction(action);
            int length = VarInts.readUnsignedInt(buffer);

            for (int i = 0; i < length; i++) {
                Entry entry = new Entry(helper.readUuid(buffer));

                if (action == PlayerListPacket.Action.ADD) {
                    entry.setEntityId(VarInts.readLong(buffer));
                    entry.setName(helper.readString(buffer));
                    entry.setXuid(helper.readString(buffer));
                    entry.setPlatformChatId(helper.readString(buffer));
                    entry.setBuildPlatform(buffer.readIntLE());
                    entry.setSkin(helper.readSkin(buffer));
                    entry.setTeacher(buffer.readBoolean());
                    entry.setHost(buffer.readBoolean());
                }
                packet.getEntries().add(entry);
            }
        }
    }

    public class PlayerListReader_v390 implements BedrockPacketReader<PlayerListPacket> {
        public static final PlayerListReader_v390 INSTANCE = new PlayerListReader_v390();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

            if (packet.getAction() == Action.ADD) {
                for (Entry entry : packet.getEntries()) {
                    helper.writeUuid(buffer, entry.getUuid());

                    VarInts.writeLong(buffer, entry.getEntityId());
                    helper.writeString(buffer, entry.getName());
                    helper.writeString(buffer, entry.getXuid());
                    helper.writeString(buffer, entry.getPlatformChatId());
                    buffer.writeIntLE(entry.getBuildPlatform());
                    helper.writeSkin(buffer, entry.getSkin());
                    buffer.writeBoolean(entry.isTeacher());
                    buffer.writeBoolean(entry.isHost());
                }

                for (Entry entry : packet.getEntries()) {
                    buffer.writeBoolean(entry.isTrustedSkin());
                }
            } else {
                for (Entry entry : packet.getEntries()) {
                    helper.writeUuid(buffer, entry.getUuid());
                }
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerListPacket packet) {
            Action action = Action.values()[buffer.readUnsignedByte()];
            packet.setAction(action);
            int length = VarInts.readUnsignedInt(buffer);

            if (action == Action.ADD) {
                for (int i = 0; i < length; i++) {
                    Entry entry = new Entry(helper.readUuid(buffer));
                    entry.setEntityId(VarInts.readLong(buffer));
                    entry.setName(helper.readString(buffer));
                    entry.setXuid(helper.readString(buffer));
                    entry.setPlatformChatId(helper.readString(buffer));
                    entry.setBuildPlatform(buffer.readIntLE());
                    entry.setSkin(helper.readSkin(buffer));
                    entry.setTeacher(buffer.readBoolean());
                    entry.setHost(buffer.readBoolean());
                    packet.getEntries().add(entry);
                }

                for (int i = 0; i < length && buffer.isReadable(); i++) {
                    packet.getEntries().get(i).setTrustedSkin(buffer.readBoolean());
                }
            } else {
                for (int i = 0; i < length; i++) {
                    packet.getEntries().add(new Entry(helper.readUuid(buffer)));
                }
            }
        }
    }

}
