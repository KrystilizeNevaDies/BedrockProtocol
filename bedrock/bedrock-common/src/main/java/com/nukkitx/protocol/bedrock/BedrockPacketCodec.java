package com.nukkitx.protocol.bedrock;

import com.github.jinahya.bit.io.*;
import com.nukkitx.protocol.bedrock.exception.PacketSerializeException;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class BedrockPacketCodec {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPacketCodec.class);

    private final int protocolVersion;
    private final String minecraftVersion;
    private final Int2ObjectMap<BedrockPacket.Interpreter<?>> packets;
    private final int raknetProtocolVersion;

    public BedrockPacketCodec(int protocolVersion, @NotNull String minecraftVersion, int raknetProtocolVersion) {
        this.protocolVersion = protocolVersion;
        this.minecraftVersion = minecraftVersion;
        this.raknetProtocolVersion = raknetProtocolVersion;
        this.packets = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
    }

    public BedrockPacketCodec(int protocolVersion, String minecraftVersion) {
        this(protocolVersion, minecraftVersion, 10);
    }

    protected void packet(int id, @NotNull BedrockPacket.Interpreter<? extends BedrockPacket> reader) {
        packets.put(id, reader);
    }

    public @NotNull BedrockPacket tryDecode(@NotNull ByteBuf buf, int id) throws PacketSerializeException {
        BedrockPacketReader<?> serializer = getPacketSerializer(id);

        BitInput input = BitInputAdapter.from(StreamByteInput.from(new ByteBufInputStream(buf)));
        BedrockPacket packet = serializer.read(input);

        if (log.isDebugEnabled() && buf.isReadable()) {
            log.debug(packet.getClass().getSimpleName() + " still has " + buf.readableBytes() + " bytes to read!");
        }
        return packet;
    }

    public void tryEncode(@NotNull ByteBuf buf, @NotNull BedrockPacket packet) throws PacketSerializeException {
        BitOutput output = BitOutputAdapter.from(StreamByteOutput.from(new ByteBufOutputStream(buf)));
        packet.write(output);
        try {
            output.align();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to align output stream", e);
        }
    }

    public @NotNull BedrockPacketReader<?> getPacketSerializer(int id) {
        return packets.get(id);
    }

    public int getId(@NotNull BedrockPacket packet) {
        return packet.packetId();
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getMinecraftVersion() {
        return minecraftVersion;
    }

    public int getRaknetProtocolVersion() {
        return raknetProtocolVersion;
    }
}
