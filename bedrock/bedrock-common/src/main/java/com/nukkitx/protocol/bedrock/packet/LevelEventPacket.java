package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface LevelEventPacket extends BedrockPacket {
    private LevelEventType valueType;
    private Vector3f position;
    private int data;


    public class LevelEventReader_v291 implements BedrockPacketReader<LevelEventPacket> {
        public static final LevelEventReader_v291 INSTANCE = new LevelEventReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventPacket packet) {
            VarInts.writeInt(buffer, helper.getLevelEventId(packet.getType()));
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, packet.getData());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventPacket packet) {
            int eventId = VarInts.readInt(buffer);
            packet.setType(helper.getLevelEvent(eventId));
            packet.setPosition(helper.readVector3f(buffer));
            packet.setData(VarInts.readInt(buffer));
        }
    }

}
