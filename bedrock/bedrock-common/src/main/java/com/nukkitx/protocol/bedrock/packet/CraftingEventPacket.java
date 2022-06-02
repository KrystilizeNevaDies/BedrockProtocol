package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

interface CraftingEventPacket extends BedrockPacket {
    private final List<ItemData> inputs = new ObjectArrayList<>();
    private final List<ItemData> outputs = new ObjectArrayList<>();
    private byte containerId;
    private CraftingType valueType;
    private UUID uuid;


    public class CraftingEventReader_v291 implements BedrockPacketReader<CraftingEventPacket> {
        public static final CraftingEventReader_v291 INSTANCE = new CraftingEventReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingEventPacket packet, BedrockSession session) {
            buffer.writeByte(packet.getContainerId());
            VarInts.writeInt(buffer, packet.getType().ordinal());
            helper.writeUuid(buffer, packet.getUuid());

            helper.writeArray(buffer, packet.getInputs(), (buf, item) -> helper.writeItem(buf, item, session));
            helper.writeArray(buffer, packet.getOutputs(), (buf, item) -> helper.writeItem(buf, item, session));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CraftingEventPacket packet, BedrockSession session) {
            packet.setContainerId(buffer.readByte());
            packet.setType(CraftingType.values()[VarInts.readInt(buffer)]);
            packet.setUuid(helper.readUuid(buffer));

            helper.readArray(buffer, packet.getInputs(), buf -> helper.readItem(buf, session));
            helper.readArray(buffer, packet.getOutputs(), buf -> helper.readItem(buf, session));
        }
    }

}
