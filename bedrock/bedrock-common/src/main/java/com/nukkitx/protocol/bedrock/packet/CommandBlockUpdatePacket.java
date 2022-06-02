package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.CommandBlockMode;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface CommandBlockUpdatePacket extends BedrockPacket {
    private boolean block;
    private Vector3i blockPosition;
    private CommandBlockMode mode;
    private boolean redstoneMode;
    private boolean conditional;
    private long minecartRuntimeEntityId;
    private String command;
    private String lastOutput;
    private String name;
    private boolean outputTracked;
    private long tickDelay;
    private boolean executingOnFirstTick;


    public class CommandBlockUpdateReader_v291 implements BedrockPacketReader<CommandBlockUpdatePacket> {
        public static final CommandBlockUpdateReader_v291 INSTANCE = new CommandBlockUpdateReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CommandBlockUpdatePacket packet) {
            buffer.writeBoolean(packet.isBlock());

            if (packet.isBlock()) {
                helper.writeBlockPosition(buffer, packet.getBlockPosition());
                VarInts.writeUnsignedInt(buffer, packet.getMode().ordinal());
                buffer.writeBoolean(packet.isRedstoneMode());
                buffer.writeBoolean(packet.isConditional());
            } else {
                VarInts.writeUnsignedLong(buffer, packet.getMinecartRuntimeEntityId());
            }

            helper.writeString(buffer, packet.getCommand());
            helper.writeString(buffer, packet.getLastOutput());
            helper.writeString(buffer, packet.getName());
            buffer.writeBoolean(packet.isOutputTracked());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CommandBlockUpdatePacket packet) {
            packet.setBlock(buffer.readBoolean());

            if (packet.isBlock()) {
                packet.setBlockPosition(helper.readBlockPosition(buffer));
                packet.setMode(CommandBlockMode.values()[VarInts.readUnsignedInt(buffer)]);
                packet.setRedstoneMode(buffer.readBoolean());
                packet.setConditional(buffer.readBoolean());
            } else {
                packet.setMinecartRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            }

            packet.setCommand(helper.readString(buffer));
            packet.setLastOutput(helper.readString(buffer));
            packet.setName(helper.readString(buffer));
            packet.setOutputTracked(buffer.readBoolean());
        }
    }

    public class CommandBlockUpdateReader_v361 implements BedrockPacketReader<CommandBlockUpdatePacket> {
        public static final CommandBlockUpdateReader_v361 INSTANCE = new CommandBlockUpdateReader_v361();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CommandBlockUpdatePacket packet) {
            buffer.writeBoolean(packet.isBlock());

            if (packet.isBlock()) {
                helper.writeBlockPosition(buffer, packet.getBlockPosition());
                VarInts.writeUnsignedInt(buffer, packet.getMode().ordinal());
                buffer.writeBoolean(packet.isRedstoneMode());
                buffer.writeBoolean(packet.isConditional());
            } else {
                VarInts.writeUnsignedLong(buffer, packet.getMinecartRuntimeEntityId());
            }

            helper.writeString(buffer, packet.getCommand());
            helper.writeString(buffer, packet.getLastOutput());
            helper.writeString(buffer, packet.getName());
            buffer.writeBoolean(packet.isOutputTracked());
            buffer.writeIntLE((int) packet.getTickDelay());
            buffer.writeBoolean(packet.isExecutingOnFirstTick());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CommandBlockUpdatePacket packet) {
            packet.setBlock(buffer.readBoolean());

            if (packet.isBlock()) {
                packet.setBlockPosition(helper.readBlockPosition(buffer));
                packet.setMode(CommandBlockMode.values()[VarInts.readUnsignedInt(buffer)]);
                packet.setRedstoneMode(buffer.readBoolean());
                packet.setConditional(buffer.readBoolean());
            } else {
                packet.setMinecartRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            }

            packet.setCommand(helper.readString(buffer));
            packet.setLastOutput(helper.readString(buffer));
            packet.setName(helper.readString(buffer));
            packet.setOutputTracked(buffer.readBoolean());
            packet.setTickDelay(buffer.readUnsignedIntLE());
            packet.setExecutingOnFirstTick(buffer.readBoolean());
        }
    }

}
