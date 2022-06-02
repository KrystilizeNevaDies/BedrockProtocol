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

interface InteractPacket extends BedrockPacket {
    private Action action;
    private long runtimeEntityId;
    private Vector3f mousePosition;


    public enum Action {
        NONE,
        INTERACT,
        DAMAGE,
        LEAVE_VEHICLE,
        MOUSEOVER,
        NPC_OPEN,
        OPEN_INVENTORY
    }

    public class InteractReader_v291 implements BedrockPacketReader<InteractPacket> {
        public static final InteractReader_v291 INSTANCE = new InteractReader_v291();

        private static final InteractPacket.Action[] ACTIONS = InteractPacket.Action.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InteractPacket packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

            if (packet.getAction() == InteractPacket.Action.MOUSEOVER) {
                helper.writeVector3f(buffer, packet.getMousePosition());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InteractPacket packet) {
            packet.setAction(ACTIONS[buffer.readUnsignedByte()]);
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));

            if (packet.getAction() == InteractPacket.Action.MOUSEOVER) {
                packet.setMousePosition(helper.readVector3f(buffer));
            }
        }
    }

    public class InteractReader_v388 implements BedrockPacketReader<InteractPacket> {
        public static final InteractReader_v388 INSTANCE = new InteractReader_v388();

        private static final InteractPacket.Action[] ACTIONS = InteractPacket.Action.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InteractPacket packet) {
            buffer.writeByte(packet.getAction().ordinal());
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());

            if (packet.getAction() == InteractPacket.Action.MOUSEOVER || packet.getAction() == InteractPacket.Action.LEAVE_VEHICLE) {
                helper.writeVector3f(buffer, packet.getMousePosition());
            }
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InteractPacket packet) {
            packet.setAction(ACTIONS[buffer.readUnsignedByte()]);
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));

            if (packet.getAction() == InteractPacket.Action.MOUSEOVER || packet.getAction() == InteractPacket.Action.LEAVE_VEHICLE) {
                packet.setMousePosition(helper.readVector3f(buffer));
            }
        }
    }

}
