package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.EnumSet;

public interface MoveEntityAbsolutePacket extends BedrockPacket {
//    long runtimeEntityId;
//    Vector3f position;
//    Vector3f rotation;
//    boolean onGround;
//    boolean teleported;


    record v291(@Unsigned long runtimeEntityId, byte flags, Vector3f position,
                @ByteRotation Vector3f rotation) implements MoveEntityAbsolutePacket {

        static final int FLAG_ON_GROUND = 0x1;
        static final int FLAG_TELEPORTED = 0x2;

        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@org.jetbrains.annotations.NotNull com.github.jinahya.bit.io.BitOutput output) throws java.io.IOException {
            DEFERER.defer(output, this);
        }
    }
}
