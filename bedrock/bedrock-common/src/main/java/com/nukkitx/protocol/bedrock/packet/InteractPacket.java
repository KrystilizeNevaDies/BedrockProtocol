package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface InteractPacket extends BedrockPacket {
    Action action();
    long runtimeEntityId();

    interface Action extends BitDataWritable, PacketDataWriter {
        int id();

        @Override
        default void write(@NotNull BitOutput output) throws IOException {
        }

        int NONE = 0;
        record None() implements Action {
            @Override
            public int id() {
                return NONE;
            }
        }

        int INTERACT = 1;
        record Interact() implements Action {
            @Override
            public int id() {
                return INTERACT;
            }
        }
        int DAMAGE = 2;
        record Damage() implements Action {
            @Override
            public int id() {
                return DAMAGE;
            }
        }
        int LEAVE_VEHICLE = 3;
        record LeaveVehicle(Vector3f unknown) implements Action {
            @Override
            public int id() {
                return LEAVE_VEHICLE;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeVector3f(output, unknown());
            }
        }
        int MOUSEOVER = 4;
        record Mouseover(Vector3f mousePos) implements Action {
            @Override
            public int id() {
                return MOUSEOVER;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeVector3f(output, mousePos());
            }
        }
        int NPC_OPEN = 5;
        record NPCOpen() implements Action {

            @Override
            public int id() {
                return NPC_OPEN;
            }
        }
        int OPEN_INVENTORY = 6;
        record OpenInventory() implements Action {
            @Override
            public int id() {
                return OPEN_INVENTORY;
            }
        }
    }

    record v291(Action action, @Unsigned long runtimeEntityId) implements InteractPacket {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeByte(output, (byte) action.id());
            writeUnsignedLong(output, runtimeEntityId());
            action.write(output);
        }
    }
}
