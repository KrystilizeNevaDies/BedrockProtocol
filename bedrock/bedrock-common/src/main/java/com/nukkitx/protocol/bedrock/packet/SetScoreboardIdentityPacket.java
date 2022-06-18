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
import lombok.Value;

import java.util.List;
import java.util.UUID;

public interface SetScoreboardIdentityPacket extends BedrockPacket {
    final List<Entry> entries = new ObjectArrayList<>();
    Action action;


    public enum Action {
        ADD,
        REMOVE
    }

    @Value
    public static class Entry {
        final long scoreboardId;
        final UUID uuid;
    }

    record v291 implements SetScoreboardIdentityPacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetScoreboardIdentityPacket packet) {
            SetScoreboardIdentityPacket.Action action = packet.getAction();
            buffer.writeByte(action.ordinal());
            helper.writeArray(buffer, packet.getEntries(), (buf, entry) -> {
                VarInts.writeLong(buffer, entry.getScoreboardId());
                if (action == Action.ADD) {
                    helper.writeUuid(buffer, entry.getUuid());
                }
            });
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetScoreboardIdentityPacket packet) {
            SetScoreboardIdentityPacket.Action action = Action.values()[buffer.readUnsignedByte()];
            packet.setAction(action);
            helper.readArray(buffer, packet.getEntries(), buf -> {
                long scoreboardId = VarInts.readLong(buffer);
                UUID uuid = null;
                if (action == Action.ADD) {
                    uuid = helper.readUuid(buffer);
                }
                return new Entry(scoreboardId, uuid);
            });
        }
    }

}
