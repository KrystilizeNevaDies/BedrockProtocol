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

public interface InteractPacket extends BedrockPacket {
    Action action;
    long runtimeEntityId;
    Vector3f mousePosition;


    public enum Action {
        NONE,
        INTERACT,
        DAMAGE,
        LEAVE_VEHICLE,
        MOUSEOVER,
        NPC_OPEN,
        OPEN_INVENTORY
    }

    record v291 implements InteractPacket {


        static final InteractPacket.Action[] ACTIONS = InteractPacket.Action.values();

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

    record v388 implements InteractPacket {


        static final InteractPacket.Action[] ACTIONS = InteractPacket.Action.values();

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
