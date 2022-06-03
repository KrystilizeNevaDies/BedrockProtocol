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
    private final List<Vector3i> records = new ObjectArrayList<>();
    private Vector3f position;
    private float radius;


    public class ExplodeReader_v291 implements BedrockPacketReader<ExplodePacket> {
        public static final ExplodeReader_v291 INSTANCE = new ExplodeReader_v291();


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
