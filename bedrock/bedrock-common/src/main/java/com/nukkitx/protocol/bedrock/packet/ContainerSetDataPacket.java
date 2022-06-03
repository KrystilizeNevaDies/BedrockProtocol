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

public interface ContainerSetDataPacket extends BedrockPacket {

    public static final int FURNACE_TICK_COUNT = 0;
    public static final int FURNACE_LIT_TIME = 1;
    public static final int FURNACE_LIT_DURATION = 2;
    public static final int FURNACE_STORED_XP = 3;
    public static final int FURNACE_FUEL_AUX = 4;

    public static final int BREWING_STAND_BREW_TIME = 0;
    public static final int BREWING_STAND_FUEL_AMOUNT = 1;
    public static final int BREWING_STAND_FUEL_TOTAL = 2;

    private byte windowId;
    private int property;
    private int value;


    public class ContainerSetDataReader_v291 implements BedrockPacketReader<ContainerSetDataPacket> {
        public static final ContainerSetDataReader_v291 INSTANCE = new ContainerSetDataReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerSetDataPacket packet) {
            buffer.writeByte(packet.getWindowId());
            VarInts.writeInt(buffer, packet.getProperty());
            VarInts.writeInt(buffer, packet.getValue());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerSetDataPacket packet) {
            packet.setWindowId(buffer.readByte());
            packet.setProperty(VarInts.readInt(buffer));
            packet.setValue(VarInts.readInt(buffer));
        }
    }

}
