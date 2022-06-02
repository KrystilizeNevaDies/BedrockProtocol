package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ee.LessonAction;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface LessonProgressPacket extends BedrockPacket {
    private LessonAction action;
    private int score;
    private String activityId;


    @Overrid

    public class LessonProgressReaderBeta implements BedrockPacketReader<LessonProgressPacket> {
        public static final LessonProgressReaderBeta INSTANCE = new LessonProgressReaderBeta();

        private static final LessonAction[] ACTIONS = LessonAction.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LessonProgressPacket packet) {
            VarInts.writeInt(buffer, packet.getAction().ordinal());
            VarInts.writeInt(buffer, packet.getScore());
            helper.writeString(buffer, packet.getActivityId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LessonProgressPacket packet) {
            packet.setAction(ACTIONS[VarInts.readInt(buffer)]);
            packet.setScore(VarInts.readInt(buffer));
            packet.setActivityId(helper.readString(buffer));
        }
    }

}
