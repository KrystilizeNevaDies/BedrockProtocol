package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public interface SetScorePacket extends BedrockPacket {
    Action action;
    List<ScoreInfo> infos = new ObjectArrayList<>();


    public enum Action {
        SET,
        REMOVE
    }

    record v291 implements SetScorePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetScorePacket packet) {
            Action action = packet.getAction();
            buffer.writeByte(action.ordinal());

            helper.writeArray(buffer, packet.getInfos(), (buf, scoreInfo) -> {
                VarInts.writeLong(buf, scoreInfo.getScoreboardId());
                helper.writeString(buf, scoreInfo.getObjectiveId());
                buf.writeIntLE(scoreInfo.getScore());
                if (action == Action.SET) {
                    buf.writeByte(scoreInfo.getType().ordinal());
                    switch (scoreInfo.getType()) {
                        case ENTITY:
                        case PLAYER:
                            VarInts.writeLong(buf, scoreInfo.getEntityId());
                            break;
                        case FAKE:
                            helper.writeString(buf, scoreInfo.getName());
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid score info received");
                    }
                }
            });
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetScorePacket packet) {
            Action action = Action.values()[buffer.readUnsignedByte()];
            packet.setAction(action);

            helper.readArray(buffer, packet.getInfos(), buf -> {
                long scoreboardId = VarInts.readLong(buf);
                String objectiveId = helper.readString(buf);
                int score = buf.readIntLE();
                if (action == Action.SET) {
                    ScoreInfo.ScorerType type = ScoreInfo.ScorerType.values()[buf.readUnsignedByte()];
                    switch (type) {
                        case ENTITY:
                        case PLAYER:
                            long entityId = VarInts.readLong(buf);
                            return new ScoreInfo(scoreboardId, objectiveId, score, type, entityId);
                        case FAKE:
                            String name = helper.readString(buf);
                            return new ScoreInfo(scoreboardId, objectiveId, score, name);
                        default:
                            throw new IllegalArgumentException("Invalid score info received");
                    }
                } else {
                    return new ScoreInfo(scoreboardId, objectiveId, score);
                }
            });
        }

    }
}
