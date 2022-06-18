package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public interface ExplodePacket extends BedrockPacket {
    final List<Vector3i> records = new ObjectArrayList<>();
    Vector3f position;
    float radius;


    record v291 implements ExplodePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ExplodePacket packet) {
            helper.writeVector3f(buffer, packet.getPosition());
            VarInts.writeInt(buffer, (int) (packet.getRadius() * 32));

            helper.writeArray(buffer, packet.getRecords(), helper::writeVector3i);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ExplodePacket packet) {
            packet.setPosition(helper.readVector3f(buffer));
            packet.setRadius(VarInts.readInt(buffer) / 32f);

            helper.readArray(buffer, packet.getRecords(), helper::readVector3i);
        }
    }
}
