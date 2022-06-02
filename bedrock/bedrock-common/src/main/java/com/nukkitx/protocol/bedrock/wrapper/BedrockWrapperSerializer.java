package com.nukkitx.protocol.bedrock.wrapper;

import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.BedrockSession;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.Map;

public abstract class BedrockWrapperSerializer {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockWrapperSerializerV9_10.class);

    /**
     * Compress packets to a buffer for sending
     *
     * @param buffer buffer to write batched packets to
     * @param codec  packet codec
     * @param level  compression level
     */
    public abstract void serialize(ByteBuf buffer, BedrockPacketCodec codec, Map<BedrockPacket, PacketMetadata> packets, int level, BedrockSession session);

    /**
     * Decompress packets to handle
     *
     * @param buffer  buffer to write batched packets to
     * @param codec   packet codec
     * @param packets received packets
     */
    public abstract void deserialize(ByteBuf buffer, BedrockPacketCodec codec, Map<BedrockPacket, PacketMetadata> packets, BedrockSession session);
}
