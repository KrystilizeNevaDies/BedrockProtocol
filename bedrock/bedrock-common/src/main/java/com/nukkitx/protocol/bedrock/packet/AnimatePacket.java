package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AnimatePacket extends BedrockPacket {
    @NotNull Action action();
    long runtimeEntityId();

    interface Action {

        int id();

        @NotNull Action NO_ACTION = () -> 0;
        @NotNull Action SWING_ARM = () -> 1;
        @NotNull Action WAKE_UP = () -> 3;
        @NotNull Action CRITICAL_HIT = () -> 4;
        @NotNull Action MAGIC_CRITICAL_HIT = () -> 5;

        @NotNull Action @NotNull [] VALUES = {
                NO_ACTION,
                SWING_ARM,
                WAKE_UP,
                CRITICAL_HIT,
                MAGIC_CRITICAL_HIT
        };

        interface RowingAction extends Action {
            float rowingTime();
        }

        static @NotNull RowingAction ROW_LEFT(float rowingTime) {
            return new RowingAction() {
                @Override
                public float rowingTime() {
                    return rowingTime;
                }

                @Override
                public int id() {
                    return 129;
                }
            };
        }
        static @NotNull RowingAction ROW_RIGHT(float rowingTime) {
            return new RowingAction() {
                @Override
                public float rowingTime() {
                    return rowingTime;
                }

                @Override
                public int id() {
                    return 128;
                }
            };
        }
    }

    record v291(Action action, long runtimeEntityId) implements AnimatePacket, Codec291 {
        private static final Int2ObjectMap<Action> ACTIONS;
        private static final Int2ObjectMap<Float2ObjectFunction<Action.RowingAction>> ROWING_ACTION_FACTORIES;

        static {
            var map = Stream.of(Action.VALUES).collect(Collectors.toMap(Action::id, a -> a));
            ACTIONS = new Int2ObjectOpenHashMap<>(map);
            Int2ObjectMap<Float2ObjectFunction<Action.RowingAction>> factories = new Int2ObjectOpenHashMap<>();
            factories.put(128, Action::ROW_RIGHT);
            factories.put(129, Action::ROW_LEFT);
            ROWING_ACTION_FACTORIES = factories;
        }

        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                int actionID = readInt(input);
                Action action = ACTIONS.get(actionID);
                long runtimeEntityId = readUnsignedLong(input);
                if (action == null) {
                    var factory = ROWING_ACTION_FACTORIES.get(actionID);
                    if (factory != null) {
                        float rowingTime = readFloat(input);
                        action = factory.get(rowingTime);
                    }
                }
                if (action == null) {
                    throw new IllegalArgumentException("Unknown action ID: " + actionID);
                }
                return new v291(action, runtimeEntityId);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, action().id());
            writeUnsignedLong(output, runtimeEntityId());
            if (action() instanceof Action.RowingAction rowingAction) {
                writeFloat(output, rowingAction.rowingTime());
            }
        }
    }

}
