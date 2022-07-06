package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface PlayerActionPacket extends BedrockPacket {
//    long runtimeEntityId;
//    PlayerActionType action;
//    Vector3i blockPosition;
//    /**
//     * @since v526
//     */
//    Vector3i resultPosition;
//    int face;


    record v291(@Unsigned long runtimeEntityId, int actionId, @BlockPosition Vector3i blockPosition,
                int face) implements PlayerActionPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }

    record Beta(@Unsigned long runtimeEntityId, int actionId, @BlockPosition Vector3i blockPosition,
                @BlockPosition Vector3i resultPosition, int face) implements PlayerActionPacket {
        public static final Interpreter<Beta> INTERPRETER = Interpreter.generate(Beta.class);
        public static final Deferer<Beta> DEFERER = Deferer.generate(Beta.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput writer) throws java.io.IOException {
            DEFERER.defer(writer, this);
        }
    }

}
