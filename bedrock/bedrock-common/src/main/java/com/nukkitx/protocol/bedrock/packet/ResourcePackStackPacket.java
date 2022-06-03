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
    private boolean forcedToAccept;
    private final List<Entry> behaviorPacks = new ObjectArrayList<>();
    private final List<Entry> resourcePacks = new ObjectArrayList<>();
    private String gameVersion;
    private final List<ExperimentData> experiments = new ObjectArrayList<>();
    private boolean experimentsPreviouslyToggled;



    @Value
    public static class Entry {
        private final String packId;
        private final String packVersion;
        private final String subPackName;
    }

    public class ResourcePackStackReader_v291 implements BedrockPacketReader<ResourcePackStackPacket> {
        public static final ResourcePackStackReader_v291 INSTANCE = new ResourcePackStackReader_v291();

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

    public class ResourcePackStackReader_v313 extends ResourcePackStackReader_v291 {
        public static final ResourcePackStackReader_v313 INSTANCE = new ResourcePackStackReader_v313();

        private static final ExperimentData LEGACY_EXPERIMENT_DATA = new ExperimentData("legacy_experiment", true);

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

    public class ResourcePackStackReader_v388 extends ResourcePackStackReader_v313 {
        public static final ResourcePackStackReader_v388 INSTANCE = new ResourcePackStackReader_v388();

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

    public class ResourcePackStackReader_v419 extends ResourcePackStackReader_v291 {

        public static final ResourcePackStackReader_v419 INSTANCE = new ResourcePackStackReader_v419();

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
