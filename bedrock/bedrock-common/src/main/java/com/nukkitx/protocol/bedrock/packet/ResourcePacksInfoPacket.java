package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

import static java.util.Objects.requireNonNull;

public interface ResourcePacksInfoPacket extends BedrockPacket {
    final List<Entry> behaviorPackInfos = new ObjectArrayList<>();
    final List<Entry> resourcePackInfos = new ObjectArrayList<>();
    boolean forcedToAccept;
    boolean scriptingEnabled;
    boolean forcingServerPacksEnabled;


    @Value
    public static class Entry {
        final String packId;
        final String packVersion;
        final long packSize;
        final String contentKey;
        final String subPackName;
        final String contentId;
        final boolean scripting;
        final boolean raytracingCapable;
    }

    record v291 implements ResourcePacksInfoPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            buffer.writeBoolean(packet.isForcedToAccept());
            helper.writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::writeEntry);
            helper.writeArrayShortLE(buffer, packet.getResourcePackInfos(), this::writeEntry);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            packet.setForcedToAccept(buffer.readBoolean());
            helper.readArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::readEntry);
            helper.readArrayShortLE(buffer, packet.getResourcePackInfos(), this::readEntry);
        }

        public ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            String packId = helper.readString(buffer);
            String packVersion = helper.readString(buffer);
            long packSize = buffer.readLongLE();
            String contentKey = helper.readString(buffer);
            String subPackName = helper.readString(buffer);
            String contentId = helper.readString(buffer);
            return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId, false, false);
        }

        public void writeEntry(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket.Entry entry) {
            requireNonNull(entry, "ResourcePacketInfoPacket entry was null");

            helper.writeString(buffer, entry.getPackId());
            helper.writeString(buffer, entry.getPackVersion());
            buffer.writeLongLE(entry.getPackSize());
            helper.writeString(buffer, entry.getContentKey());
            helper.writeString(buffer, entry.getSubPackName());
            helper.writeString(buffer, entry.getContentId());
        }
    }

    record v332 extends ResourcePacksInfoReader_v291 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            buffer.writeBoolean(packet.isForcedToAccept());
            buffer.writeBoolean(packet.isScriptingEnabled());
            helper.writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::writeEntry);
            helper.writeArrayShortLE(buffer, packet.getResourcePackInfos(), this::writeEntry);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            packet.setForcedToAccept(buffer.readBoolean());
            packet.setScriptingEnabled(buffer.readBoolean());
            helper.readArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::readEntry);
            helper.readArrayShortLE(buffer, packet.getResourcePackInfos(), this::readEntry);
        }

        @Override
        public void writeEntry(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket.Entry entry) {
            super.writeEntry(buffer, helper, entry);

            buffer.writeBoolean(entry.isScripting());
        }

        @Override
        public ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            String packId = helper.readString(buffer);
            String packVersion = helper.readString(buffer);
            long packSize = buffer.readLongLE();
            String contentKey = helper.readString(buffer);
            String subPackName = helper.readString(buffer);
            String contentId = helper.readString(buffer);
            boolean isScripting = buffer.readBoolean();
            return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                    isScripting, false);
        }
    }

    record v422 extends ResourcePacksInfoReader_v332 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            buffer.writeBoolean(packet.isForcedToAccept());
            buffer.writeBoolean(packet.isScriptingEnabled());
            helper.writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::writeEntry);
            helper.writeArrayShortLE(buffer, packet.getResourcePackInfos(), this::writeResourcePackEntry);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            packet.setForcedToAccept(buffer.readBoolean());
            packet.setScriptingEnabled(buffer.readBoolean());
            helper.readArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::readEntry);
            helper.readArrayShortLE(buffer, packet.getResourcePackInfos(), this::readResourcePackEntry);
        }

        public void writeResourcePackEntry(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket.Entry entry) {
            this.writeEntry(buffer, helper, entry);
            buffer.writeBoolean(entry.isRaytracingCapable());
        }

        @Override
        public ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            String packId = helper.readString(buffer);
            String packVersion = helper.readString(buffer);
            long packSize = buffer.readLongLE();
            String contentKey = helper.readString(buffer);
            String subPackName = helper.readString(buffer);
            String contentId = helper.readString(buffer);
            boolean isScripting = buffer.readBoolean();
            return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                    isScripting, false);
        }

        public ResourcePacksInfoPacket.Entry readResourcePackEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            String packId = helper.readString(buffer);
            String packVersion = helper.readString(buffer);
            long packSize = buffer.readLongLE();
            String contentKey = helper.readString(buffer);
            String subPackName = helper.readString(buffer);
            String contentId = helper.readString(buffer);
            boolean isScripting = buffer.readBoolean();
            boolean raytracingCapable = buffer.readBoolean();
            return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId,
                    isScripting, raytracingCapable);
        }

    }

    record v448 extends ResourcePacksInfoReader_v422 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            buffer.writeBoolean(packet.isForcedToAccept());
            buffer.writeBoolean(packet.isScriptingEnabled());
            buffer.writeBoolean(packet.isForcingServerPacksEnabled());
            helper.writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::writeEntry);
            helper.writeArrayShortLE(buffer, packet.getResourcePackInfos(), this::writeResourcePackEntry);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePacksInfoPacket packet) {
            packet.setForcedToAccept(buffer.readBoolean());
            packet.setScriptingEnabled(buffer.readBoolean());
            packet.setForcingServerPacksEnabled(buffer.readBoolean());
            helper.readArrayShortLE(buffer, packet.getBehaviorPackInfos(), this::readEntry);
            helper.readArrayShortLE(buffer, packet.getResourcePackInfos(), this::readResourcePackEntry);
        }
    }


}
