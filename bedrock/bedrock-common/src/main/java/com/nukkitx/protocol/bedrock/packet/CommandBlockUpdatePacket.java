package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.CommandBlockMode;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.BitDataWriter;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface CommandBlockUpdatePacket extends BedrockPacket {
//     boolean block();
//     @NotNull Vector3i blockPosition();
//     @NotNull CommandBlockMode mode();
//     boolean redstoneMode();
//     boolean conditional();
//     long minecartRuntimeEntityId();
//     @NotNull String command();
//     @NotNull String lastOutput();
//     @NotNull String name();
//     boolean outputTracked();
//     long tickDelay();
//     boolean executingOnFirstTick();


    interface Target extends BitDataWritable, PacketDataWriter {

        boolean isBlock();

        Interpreter<Target> INTERPRETER = new Interpreter<Target>() {

            @Override
            public @NotNull Target interpret(@NotNull BitInput input) throws IOException {
                boolean isBlock = readBoolean(input);
                if (isBlock)
                    return Block.INTERPRETER.interpret(input);
                return Minecart.INTERPRETER.interpret(input);
            }
        };

        record Block(@NotNull Vector3i pos, CommandBlockMode mode, boolean isRedstoneMode,
                     boolean isConditional) implements Target {
            public static final Interpreter<Block> INTERPRETER = new Interpreter<Block>() {
                @Override
                public @NotNull Block interpret(@NotNull BitInput input) throws IOException {
                    Vector3i pos = readBlockPosition(input);
                    CommandBlockMode mode = CommandBlockMode.values()[readByte(input)];
                    boolean isRedstoneMode = readBoolean(input);
                    boolean isConditional = readBoolean(input);
                    return new Block(pos, mode, isRedstoneMode, isConditional);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeBlockPosition(output, pos());
                writeByte(output, (byte) mode().ordinal());
                writeBoolean(output, isRedstoneMode());
                writeBoolean(output, isConditional());
            }

            @Override
            public boolean isBlock() {
                return true;
            }
        }

        record Minecart(long minecartRuntimeEntityId) implements Target {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeUnsignedLong(output, minecartRuntimeEntityId());
            }

            @Override
            public boolean isBlock() {
                return false;
            }
        }
    }

    record v291(@NotNull Target target, String command, String lastOutput, String name,
                boolean isOutputTracked) implements CommandBlockUpdatePacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                Target target = Target.INTERPRETER.interpret(input);
                String command = readString(input);
                String lastOutput = readString(input);
                String name = readString(input);
                boolean isOutputTracked = readBoolean(input);
                return new v291(target, command, lastOutput, name, isOutputTracked);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeBoolean(output, target().isBlock());
            target().write(output);

            writeString(output, command());
            writeString(output, lastOutput());
            writeString(output, name());
            writeBoolean(output, isOutputTracked());
        }
    }

    record v361(@NotNull Target target, String command, String lastOutput, String name, boolean isOutputTracked,
                int tickDelay, boolean executeOnFirstTick) implements CommandBlockUpdatePacket {
        public static final Interpreter<v361> INTERPRETER = new Interpreter<v361>() {
            @Override
            public @NotNull v361 interpret(@NotNull BitInput input) throws IOException {
                Target target = Target.INTERPRETER.interpret(input);
                String command = readString(input);
                String lastOutput = readString(input);
                String name = readString(input);
                boolean isOutputTracked = readBoolean(input);
                int tickDelay = readUnsignedIntLE(input);
                boolean executeOnFirstTick = readBoolean(input);
                return new v361(target, command, lastOutput, name, isOutputTracked, tickDelay, executeOnFirstTick);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeBoolean(output, target().isBlock());
            target().write(output);

            writeString(output, command());
            writeString(output, lastOutput());
            writeString(output, name());
            writeBoolean(output, isOutputTracked());
            writeUnsignedIntLE(output, tickDelay());
            writeBoolean(output, executeOnFirstTick());
        }
    }

}
