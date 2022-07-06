package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.NpcRequestType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface NpcRequestPacket extends BedrockPacket {
//    long runtimeEntityId;
//    NpcRequestType requestType;
//    String command;
//    int actionType;
//    String sceneName;


    record v291(@Unsigned long runtimeEntityId, @AsByte int requestType, String command,
                @AsByte int actionType) implements NpcRequestPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }

    record v448(@Unsigned long runtimeEntityId, @AsByte int requestType, String command,
                @AsByte int actionType, String sceneName) implements NpcRequestPacket {
        public static final Interpreter<v448> INTERPRETER = Interpreter.generate(v448.class);
        public static final Deferer<v448> DEFERER = Deferer.generate(v448.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }


}
