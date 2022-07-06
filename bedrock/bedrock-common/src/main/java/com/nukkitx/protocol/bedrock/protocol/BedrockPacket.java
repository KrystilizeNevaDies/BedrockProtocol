package com.nukkitx.protocol.bedrock.protocol;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.MinecraftPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketGenerator;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.BedrockPacketWriter;
import com.nukkitx.protocol.bedrock.packet.EntityFallPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataReader;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface BedrockPacket extends MinecraftPacket, BedrockPacketReader, BedrockPacketWriter {
    /**
     * Packet interpreters interpret input bits and compile output bits.
     */
    interface Interpreter<P> extends BedrockPacketReader, PacketDataReader.IOExceptionFunction<P> {
        static <R extends Record> Interpreter<R> generate(Class<R> recordClass) {
            //noinspection unchecked
            return (Interpreter<R>) new BedrockPacketGenerator(recordClass).interpreter();
        }

        @NotNull P interpret(@NotNull BitInput input) throws IOException;

        default P apply(@NotNull BitInput input) throws IOException {
            return interpret(input);
        }
    }

    interface Deferer<P> extends BedrockPacketWriter, PacketDataWriter.IOExceptionWriter<P> {

        static <R extends Record> Deferer<R> generate(Class<R> recordClass) {
            //noinspection unchecked
            return (Deferer<R>) new BedrockPacketGenerator(recordClass).deferer();
        }

        void defer(@NotNull BitOutput output, @NotNull P value) throws IOException;

        @Override
        default void apply(@NotNull BitOutput output, @NotNull P value) throws IOException {
            defer(output, value);
        }
    }

    enum Codec {
        Codec291,
        Codec313,
        Codec332,
        Codec340,
        Codec354,
        Codec361,
        Codec388,
        Codec389,
        Codec390,
        Codec407,
        Codec408,
        Codec419,
        Codec422,
        Codec428,
        Codec431,
        Codec440,
        Codec448,
        Codec465,
        Codec471,
        Codec475,
        Codec486,
        Codec503;
    }

    interface CodecAware {
        @NotNull Codec codec();
    }

    interface Codec291 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec291; } }
    interface Codec313 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec313; } }
    interface Codec332 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec332; } }
    interface Codec340 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec340; } }
    interface Codec354 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec354; } }
    interface Codec361 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec361; } }
    interface Codec388 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec388; } }
    interface Codec389 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec389; } }
    interface Codec390 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec390; } }
    interface Codec407 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec407; } }
    interface Codec408 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec408; } }
    interface Codec419 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec419; } }
    interface Codec422 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec422; } }
    interface Codec428 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec428; } }
    interface Codec431 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec431; } }
    interface Codec440 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec440; } }
    interface Codec448 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec448; } }
    interface Codec465 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec465; } }
    interface Codec471 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec471; } }
    interface Codec475 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec475; } }
    interface Codec486 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec486; } }
    interface Codec503 extends CodecAware { @Override default @NotNull Codec codec() { return Codec.Codec503; } }
}
