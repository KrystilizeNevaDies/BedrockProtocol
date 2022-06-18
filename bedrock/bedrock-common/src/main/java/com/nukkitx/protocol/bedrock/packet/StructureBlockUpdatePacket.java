package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.data.structure.*;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface StructureBlockUpdatePacket extends BedrockPacket {
    Vector3i blockPosition;
    StructureEditorData editorData;
    boolean powered;


    public enum Type {
        NONE,
        SAVE,
        LOAD,
    }

    record v291 implements StructureBlockUpdatePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
            StructureEditorData editorData = packet.getEditorData();
            StructureSettings settings = editorData.getSettings();

            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeUnsignedInt(buffer, editorData.getType().ordinal());
            // Structure Editor Data start
            helper.writeString(buffer, editorData.getName());
            helper.writeString(buffer, editorData.getName());
            helper.writeBlockPosition(buffer, settings.getOffset());
            helper.writeBlockPosition(buffer, settings.getSize());
            buffer.writeBoolean(!settings.isIgnoringEntities());
            buffer.writeBoolean(settings.isIgnoringBlocks());
            buffer.writeBoolean(editorData.isIncludingPlayers());
            buffer.writeBoolean(false); // show air
            // Structure Settings start
            buffer.writeFloatLE(settings.getIntegrityValue());
            VarInts.writeUnsignedInt(buffer, settings.getIntegritySeed());
            VarInts.writeUnsignedInt(buffer, settings.getMirror().ordinal());
            VarInts.writeUnsignedInt(buffer, settings.getRotation().ordinal());
            buffer.writeBoolean(settings.isIgnoringEntities());
            buffer.writeBoolean(true); // ignore structure blocks
            Vector3i min = packet.getBlockPosition().add(settings.getOffset());
            helper.writeVector3i(buffer, min);
            Vector3i max = min.add(settings.getSize());
            helper.writeVector3i(buffer, max);
            // Structure Settings end
            // Structure Editor Data end
            buffer.writeBoolean(editorData.isBoundingBoxVisible());
            buffer.writeBoolean(packet.isPowered());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            StructureBlockType structureType = StructureBlockType.values()[VarInts.readUnsignedInt(buffer)];
            // Structure Editor Data start
            String name = helper.readString(buffer);
            String dataField = helper.readString(buffer);
            Vector3i offset = helper.readBlockPosition(buffer);
            Vector3i size = helper.readBlockPosition(buffer);
            buffer.readBoolean(); // include entities
            boolean ignoreBlocks = !buffer.readBoolean();
            boolean includePlayers = buffer.readBoolean();
            buffer.readBoolean(); // show air
            // Structure Settings start
            float structureIntegrity = buffer.readFloatLE();
            int integritySeed = VarInts.readUnsignedInt(buffer);
            StructureMirror mirror = StructureMirror.from(VarInts.readUnsignedInt(buffer));
            StructureRotation rotation = StructureRotation.from(VarInts.readUnsignedInt(buffer));
            boolean ignoreEntities = buffer.readBoolean();
            buffer.readBoolean(); // ignore structure bocks
            helper.readVector3i(buffer); // bounding box min
            helper.readVector3i(buffer); // bounding box max
            // Structure Settings end
            // Structure Editor Data end
            boolean boundingBoxVisible = buffer.readBoolean();

            StructureSettings settings = new StructureSettings("", ignoreEntities, ignoreBlocks, true, size, offset,
                    -1, rotation, mirror, StructureAnimationMode.NONE, 0,
                    structureIntegrity, integritySeed, Vector3f.ZERO);
            StructureEditorData editorData = new StructureEditorData(name, dataField, includePlayers, boundingBoxVisible,
                    structureType, settings, StructureRedstoneSaveMode.SAVES_TO_DISK);

            packet.setEditorData(editorData);
            packet.setPowered(buffer.readBoolean());
        }
    }

    record v340 implements StructureBlockUpdatePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
            StructureEditorData editorData = packet.getEditorData();
            StructureSettings settings = editorData.getSettings();

            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeUnsignedInt(buffer, editorData.getType().ordinal());
            // Structure Editor Data start
            helper.writeString(buffer, editorData.getName());
            helper.writeString(buffer, editorData.getName());
            helper.writeBlockPosition(buffer, settings.getOffset());
            helper.writeBlockPosition(buffer, settings.getSize());
            buffer.writeBoolean(!settings.isIgnoringEntities());
            buffer.writeBoolean(settings.isIgnoringBlocks());
            buffer.writeBoolean(editorData.isIncludingPlayers());
            buffer.writeBoolean(false); // show air
            // Structure Settings start
            buffer.writeFloatLE(settings.getIntegrityValue());
            VarInts.writeUnsignedInt(buffer, settings.getIntegritySeed());
            VarInts.writeUnsignedInt(buffer, settings.getMirror().ordinal());
            VarInts.writeUnsignedInt(buffer, settings.getRotation().ordinal());
            buffer.writeBoolean(settings.isIgnoringEntities());
            buffer.writeBoolean(true); // ignore structure blocks
            Vector3i min = packet.getBlockPosition().add(settings.getOffset());
            helper.writeVector3i(buffer, min);
            Vector3i max = min.add(settings.getSize());
            helper.writeVector3i(buffer, max);
            // Structure Settings end
            // Structure Editor Data end
            buffer.writeBoolean(editorData.isBoundingBoxVisible());
            buffer.writeBoolean(packet.isPowered());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            StructureBlockType structureType = StructureBlockType.values()[VarInts.readUnsignedInt(buffer)];
            // Structure Editor Data start
            String name = helper.readString(buffer);
            String dataField = helper.readString(buffer);
            Vector3i offset = helper.readBlockPosition(buffer);
            Vector3i size = helper.readBlockPosition(buffer);
            buffer.readBoolean(); // include entities
            boolean ignoreBlocks = !buffer.readBoolean();
            boolean includePlayers = buffer.readBoolean();
            buffer.readBoolean(); // show air
            // Structure Settings start
            float structureIntegrity = buffer.readFloatLE();
            int integritySeed = VarInts.readUnsignedInt(buffer);
            StructureMirror mirror = StructureMirror.from(VarInts.readUnsignedInt(buffer));
            StructureRotation rotation = StructureRotation.from(VarInts.readUnsignedInt(buffer));
            boolean ignoreEntities = buffer.readBoolean();
            buffer.readBoolean(); // ignore structure bocks
            helper.readVector3i(buffer); // bounding box min
            helper.readVector3i(buffer); // bounding box max
            // Structure Settings end
            // Structure Editor Data end
            boolean boundingBoxVisible = buffer.readBoolean();

            StructureSettings settings = new StructureSettings("", ignoreEntities, ignoreBlocks, true, size, offset,
                    -1, rotation, mirror, StructureAnimationMode.NONE, 0,
                    structureIntegrity, integritySeed, Vector3f.ZERO);
            StructureEditorData editorData = new StructureEditorData(name, dataField, includePlayers, boundingBoxVisible,
                    structureType, settings, StructureRedstoneSaveMode.SAVES_TO_DISK);

            packet.setEditorData(editorData);
            packet.setPowered(buffer.readBoolean());
        }
    }

    record v361 implements StructureBlockUpdatePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            this.writeEditorData(buffer, helper, packet.getEditorData());
            buffer.writeBoolean(packet.isPowered());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureBlockUpdatePacket packet) {
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setEditorData(this.readEditorData(buffer, helper));
            packet.setPowered(buffer.readBoolean());
        }

        protected StructureEditorData readEditorData(ByteBuf buffer, BedrockPacketHelper helper) {
            String name = helper.readString(buffer);
            String dataField = helper.readString(buffer);
            boolean includingPlayers = buffer.readBoolean();
            boolean boundingBoxVisible = buffer.readBoolean();
            StructureBlockType type = StructureBlockType.from(VarInts.readInt(buffer));
            StructureSettings settings = helper.readStructureSettings(buffer);
            return new StructureEditorData(name, dataField, includingPlayers, boundingBoxVisible, type, settings,
                    StructureRedstoneSaveMode.SAVES_TO_DISK);
        }

        protected void writeEditorData(ByteBuf buffer, BedrockPacketHelper helper, StructureEditorData data) {
            helper.writeString(buffer, data.getName());
            helper.writeString(buffer, data.getDataField());
            buffer.writeBoolean(data.isIncludingPlayers());
            buffer.writeBoolean(data.isBoundingBoxVisible());
            VarInts.writeInt(buffer, data.getType().ordinal());
            helper.writeStructureSettings(buffer, data.getSettings());
        }
    }

    record v388 extends StructureBlockUpdateReader_v361 {


        @Override
        protected StructureEditorData readEditorData(ByteBuf buffer, BedrockPacketHelper helper) {
            String name = helper.readString(buffer);
            String dataField = helper.readString(buffer);
            boolean includingPlayers = buffer.readBoolean();
            boolean boundingBoxVisible = buffer.readBoolean();
            StructureBlockType type = StructureBlockType.from(VarInts.readInt(buffer));
            StructureSettings settings = helper.readStructureSettings(buffer);
            StructureRedstoneSaveMode redstoneSaveMode = StructureRedstoneSaveMode.from(VarInts.readInt(buffer));
            return new StructureEditorData(name, dataField, includingPlayers, boundingBoxVisible, type, settings,
                    redstoneSaveMode);
        }

        @Override
        protected void writeEditorData(ByteBuf buffer, BedrockPacketHelper helper, StructureEditorData data) {
            super.writeEditorData(buffer, helper, data);

            VarInts.writeInt(buffer, data.getRedstoneSaveMode().ordinal());
        }
    }


}
