package com.nukkitx.protocol.bedrock.packet;

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
import java.util.UUID;

interface EmoteListPacket extends BedrockPacket {
    private long runtimeEntityId;
    private final List<UUID> pieceIds = new ObjectArrayList<>();


    @Overrid

    public class EmoteListReader_v407 implements BedrockPacketReader<EmoteListPacket> {
        public static final EmoteListReader_v407 INSTANCE = new EmoteListReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EmoteListPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeArray(buffer, packet.getPieceIds(), helper::writeUuid);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EmoteListPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            helper.readArray(buffer, packet.getPieceIds(), helper::readUuid);
        }
    }

}
