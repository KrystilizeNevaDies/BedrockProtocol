package com.nukkitx.protocol.bedrock.packet;

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

public interface ResourcePackClientResponsePacket extends BedrockPacket {
    final List<String> packIds = new ObjectArrayList<>();
    Status status;


    public enum Status {
        NONE,
        REFUSED,
        SEND_PACKS,
        HAVE_ALL_PACKS,
        COMPLETED
    }

    record v291 implements ResourcePackClientResponsePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackClientResponsePacket packet) {
            buffer.writeByte(packet.getStatus().ordinal());

            helper.writeArrayShortLE(buffer, packet.getPackIds(), helper::writeString);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackClientResponsePacket packet) {
            Status status = Status.values()[buffer.readUnsignedByte()];
            packet.setStatus(status);

            helper.readArrayShortLE(buffer, packet.getPackIds(), helper::readString);
        }
    }

}
