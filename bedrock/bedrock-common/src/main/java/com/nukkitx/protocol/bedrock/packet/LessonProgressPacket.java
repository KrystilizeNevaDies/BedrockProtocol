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
import java.io.IOException;

interface LessonProgressPacket extends BedrockPacket {
    LessonAction action();
    int score();
    String activityId();


    record Beta(@AsInt LessonAction action, int score, String activityId) implements LessonProgressPacket {
        public static final Interpreter<Beta> INTERPRETER = Interpreter.generate(Beta.class);
        private static final Deferer<Beta> DEFERER = Deferer.generate(Beta.class);

        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput output) throws IOException {
            DEFERER.defer(output, this);
        }
    }

}
