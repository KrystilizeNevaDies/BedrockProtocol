package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface PlayerHotbarPacket extends BedrockPacket {
    private int selectedHotbarSlot;
    private int containerId;
    private boolean selectHotbarSlot;


    public class PlayerHotbarReader_v291 implements BedrockPacketReader<PlayerHotbarPacket> {
        public static final PlayerHotbarReader_v291 INSTANCE = new PlayerHotbarReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerHotbarPacket packet) {
            VarInts.writeUnsignedInt(buffer, packet.getSelectedHotbarSlot());
            buffer.writeByte(packet.getContainerId());
            buffer.writeBoolean(packet.isSelectHotbarSlot());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerHotbarPacket packet) {
            packet.setSelectedHotbarSlot(VarInts.readUnsignedInt(buffer));
            packet.setContainerId(buffer.readUnsignedByte());
            packet.setSelectHotbarSlot(buffer.readBoolean());
        }
    }

}
