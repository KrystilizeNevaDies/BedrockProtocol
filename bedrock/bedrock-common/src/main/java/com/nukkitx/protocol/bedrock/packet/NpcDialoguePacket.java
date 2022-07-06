package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

interface NpcDialoguePacket extends BedrockPacket {

    long uniqueEntityId();
    int action();
    String dialogue();
    String sceneName();
    String npcName();
    String actionJson();


    enum Action {
        OPEN,
        CLOSE
    }

    record v448(@LE long uniqueEntityId, int action, String dialogue, String sceneName, String npcName,
                String actionJson) implements NpcDialoguePacket {
        public static final Interpreter<v448> INTERPRETER = Interpreter.generate(v448.class);
        public static final Deferer<v448> DEFERER = Deferer.generate(v448.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull BitOutput writer) throws IOException {
            DEFERER.defer(writer, this);
        }
    }

}
