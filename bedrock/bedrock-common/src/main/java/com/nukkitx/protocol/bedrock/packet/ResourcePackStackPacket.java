package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ExperimentData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

import static java.util.Objects.requireNonNull;

public interface ResourcePackStackPacket extends BedrockPacket {
    boolean forcedToAccept;
    final List<Entry> behaviorPacks = new ObjectArrayList<>();
    final List<Entry> resourcePacks = new ObjectArrayList<>();
    String gameVersion;
    final List<ExperimentData> experiments = new ObjectArrayList<>();
    boolean experimentsPreviouslyToggled;


    @Value
    public static class Entry {
        final String packId;
        final String packVersion;
        final String subPackName;
    }

    record v292 implements ResourcePackStackPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            buffer.writeBoolean(packet.isForcedToAccept());
            helper.writeArray(buffer, packet.getBehaviorPacks(), this::writeEntry);
            helper.writeArray(buffer, packet.getResourcePacks(), this::writeEntry);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            packet.setForcedToAccept(buffer.readBoolean());
            helper.readArray(buffer, packet.getBehaviorPacks(), this::readEntry);
            helper.readArray(buffer, packet.getResourcePacks(), this::readEntry);
        }

        public ResourcePackStackPacket.Entry readEntry(ByteBuf buffer, BedrockPacketHelper helper) {
            String packId = helper.readString(buffer);
            String packVersion = helper.readString(buffer);
            String subPackName = helper.readString(buffer);
            return new ResourcePackStackPacket.Entry(packId, packVersion, subPackName);
        }

        public void writeEntry(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket.Entry entry) {
            requireNonNull(entry, "ResourcePackStackPacket entry is null");

            helper.writeString(buffer, entry.getPackId());
            helper.writeString(buffer, entry.getPackVersion());
            helper.writeString(buffer, entry.getSubPackName());
        }
    }

    record v313 extends ResourcePackStackReader_v291 {


        static final ExperimentData LEGACY_EXPERIMENT_DATA = new ExperimentData("legacy_experiment", true);

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            super.serialize(buffer, helper, packet);

            buffer.writeBoolean(packet.getExperiments().contains(LEGACY_EXPERIMENT_DATA));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            super.deserialize(buffer, helper, packet);

            if (buffer.readBoolean()) {
                packet.getExperiments().add(LEGACY_EXPERIMENT_DATA);
            }
        }
    }

    record v388 extends ResourcePackStackReader_v313 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            super.serialize(buffer, helper, packet);

            helper.writeString(buffer, packet.getGameVersion());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setGameVersion(helper.readString(buffer));
        }
    }

    record v419 extends ResourcePackStackReader_v291 {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            super.serialize(buffer, helper, packet);

            helper.writeString(buffer, packet.getGameVersion());

            helper.writeExperiments(buffer, packet.getExperiments());
            buffer.writeBoolean(packet.isExperimentsPreviouslyToggled());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setGameVersion(helper.readString(buffer));

            helper.readExperiments(buffer, packet.getExperiments());
            packet.setExperimentsPreviouslyToggled(buffer.readBoolean());
        }
    }


}
