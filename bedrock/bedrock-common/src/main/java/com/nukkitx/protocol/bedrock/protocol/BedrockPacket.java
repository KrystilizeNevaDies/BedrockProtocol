package com.nukkitx.protocol.bedrock.protocol;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.MinecraftPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockPacketWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface BedrockPacket extends MinecraftPacket, BedrockPacketReader, BedrockPacketWriter {
    /**
     * Packet interpreters interpret input bits and compile output bits.
     */
    interface Interpreter<P> extends BedrockPacketReader {
        @NotNull P interpret(@NotNull BitInput input) throws IOException;
    }

    interface Codec291 { }
    interface Codec313 { }
    interface Codec332 { }
    interface Codec340 { }
    interface Codec354 { }
    interface Codec361 { }
    interface Codec388 { }
    interface Codec389 { }
    interface Codec390 { }
    interface Codec407 { }
    interface Codec408 { }
    interface Codec419 { }
    interface Codec422 { }
    interface Codec428 { }
    interface Codec431 { }
    interface Codec440 { }
    interface Codec448 { }
    interface Codec465 { }
    interface Codec471 { }
    interface Codec475 { }
    interface Codec486 { }
    interface Codec503 { }
}
