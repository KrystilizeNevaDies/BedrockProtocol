package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public interface InventoryContentPacket extends BedrockPacket {
    private List<ItemData> contents = new ObjectArrayList<>();
    private int containerId;


    public class InventoryContentReader_v291 implements BedrockPacketReader<InventoryContentPacket> {
        public static final InventoryContentReader_v291 INSTANCE = new InventoryContentReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet, BedrockSession session) {
            VarInts.writeUnsignedInt(buffer, packet.getContainerId());
            helper.writeArray(buffer, packet.getContents(), (buf, item) -> helper.writeItem(buf, item, session));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet, BedrockSession session) {
            packet.setContainerId(VarInts.readUnsignedInt(buffer));
            helper.readArray(buffer, packet.getContents(), buf -> helper.readItem(buf, session));
        }
    }

    public class InventoryContentReader_v407 implements BedrockPacketReader<InventoryContentPacket> {
        public static final InventoryContentReader_v407 INSTANCE = new InventoryContentReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet, BedrockSession session) {
            VarInts.writeUnsignedInt(buffer, packet.getContainerId());
            helper.writeArray(buffer, packet.getContents(), (buf, item) -> helper.writeNetItem(buf, item, session));
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet, BedrockSession session) {
            packet.setContainerId(VarInts.readUnsignedInt(buffer));
            helper.readArray(buffer, packet.getContents(), buf -> helper.readNetItem(buf, session));
        }
    }

}
