package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AbilityType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface RequestAbilityPacket extends BedrockPacket {
    private int ability;
    private AbilityType valueType;
    private boolean boolValue;
    private float floatValue;


    @Overrid

    public class RequestAbilityReaderBeta implements BedrockPacketReader<RequestAbilityPacket> {
        public static final RequestAbilityReaderBeta INSTANCE = new RequestAbilityReaderBeta();

        private static final AbilityType[] ABILITIES = AbilityType.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestAbilityPacket packet) {
            VarInts.writeInt(buffer, packet.getAbility());
            buffer.writeByte(packet.getType().ordinal());
            buffer.writeBoolean(packet.isBoolValue());
            buffer.writeFloatLE(packet.getFloatValue());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestAbilityPacket packet) {
            packet.setAbility(VarInts.readInt(buffer));
            packet.setType(ABILITIES[buffer.readUnsignedByte()]);
            packet.setBoolValue(buffer.readBoolean());
            packet.setFloatValue(buffer.readFloatLE());
        }
    }

}
