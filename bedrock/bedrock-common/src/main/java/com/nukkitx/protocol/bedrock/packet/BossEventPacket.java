package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
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

interface BossEventPacket extends BedrockPacket {
    @NotNull Action action();

    interface Action extends BitDataWritable, PacketDataWriter {
        int id();

        interface Action291 extends Action, Codec291 {
        }

        interface Action486 extends Action, Codec486 {
        }

        /**
         * Creates the bossbar to the player.
         */
        int CREATE = 0;
        record Create(String title, float healthPercentage, short darkenSky, int color, int overlay) implements Action291, Action486 {
            public static final Interpreter<Create> INTERPRETER = new Interpreter<>() {
                @Override
                public @NotNull Create interpret(@NotNull BitInput input) throws IOException {
                    String title = readString(input);
                    float healthPercentage = readFloatLE(input);
                    short darkenSky = readShortLE(input);
                    int color = readUnsignedInt(input);
                    int overlay = readUnsignedInt(input);
                    return new Create(title, healthPercentage, darkenSky, color, overlay);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, title());
                writeFloatLE(output, healthPercentage());
                writeShortLE(output, darkenSky());
                writeUnsignedInt(output, color());
                writeUnsignedInt(output, overlay());
            }

            @Override
            public int id() {
                return CREATE;
            }
        }
        /**
         * Registers a player to a boss fight.
         */
        int REGISTER_PLAYER = 1;
        record RegisterPlayer(long playerUniqueEntityId) implements Action291, Action486 {
            public static final Interpreter<RegisterPlayer> INTERPRETER = new Interpreter<RegisterPlayer>() {
                @Override
                public @NotNull RegisterPlayer interpret(@NotNull BitInput input) throws IOException {
                    long playerUniqueEntityId = readLong(input);
                    return new RegisterPlayer(playerUniqueEntityId);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeLong(output, playerUniqueEntityId());
            }

            @Override
            public int id() {
                return REGISTER_PLAYER;
            }
        }
        /**
         * Removes the bossbar from the client.
         */
        int REMOVE = 2;
        record Remove() implements Action291, Action486 {
            public static final Interpreter<Remove> INTERPRETER = new Interpreter<Remove>() {
                @Override
                public @NotNull Remove interpret(@NotNull BitInput input) throws IOException {
                    return new Remove();
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                // no data
            }

            @Override
            public int id() {
                return REMOVE;
            }
        }
        /**
         * Unregisters a player from a boss fight.
         */
        int UNREGISTER_PLAYER = 3;
        record UnregisterPlayer(long playerUniqueEntityId) implements Action291, Action486 {
            public static final Interpreter<UnregisterPlayer> INTERPRETER = new Interpreter<UnregisterPlayer>() {
                @Override
                public @NotNull UnregisterPlayer interpret(@NotNull BitInput input) throws IOException {
                    long playerUniqueEntityId = readLong(input);
                    return new UnregisterPlayer(playerUniqueEntityId);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeLong(output, playerUniqueEntityId());
            }

            @Override
            public int id() {
                return UNREGISTER_PLAYER;
            }
        }
        /**
         * Appears not to be implemented. Currently bar percentage only appears to change in response to the target entity's health.
         */
        int UPDATE_PERCENTAGE = 4;
        record UpdatePercentage(float healthPercentage) implements Action291, Action486 {
            public static final Interpreter<UpdatePercentage> INTERPRETER = new Interpreter<UpdatePercentage>() {
                @Override
                public @NotNull UpdatePercentage interpret(@NotNull BitInput input) throws IOException {
                    float healthPercentage = readFloatLE(input);
                    return new UpdatePercentage(healthPercentage);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeFloatLE(output, healthPercentage());
            }

            @Override
            public int id() {
                return UPDATE_PERCENTAGE;
            }
        }
        /**
         * Also appears to not be implemented. Title clientside sticks as the target entity's nametag, or their entity transactionType name if not set.
         */
        int UPDATE_NAME = 5;
        record UpdateName(String title) implements Action291, Action486 {
            public static final Interpreter<UpdateName> INTERPRETER = new Interpreter<UpdateName>() {
                @Override
                public @NotNull UpdateName interpret(@NotNull BitInput input) throws IOException {
                    String title = readString(input);
                    return new UpdateName(title);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, title());
            }

            @Override
            public int id() {
                return UPDATE_NAME;
            }
        }
        /**
         * Darken the sky when the boss bar is shown.
         */
        int UPDATE_PROPERTIES = 6;
        record UpdateProperties(short darkenSky, int color, int overlay) implements Action291, Action486 {
            public static final Interpreter<UpdateProperties> INTERPRETER = new Interpreter<UpdateProperties>() {
                @Override
                public @NotNull UpdateProperties interpret(@NotNull BitInput input) throws IOException {
                    short darkenSky = readShortLE(input);
                    int color = readUnsignedInt(input);
                    int overlay = readUnsignedInt(input);
                    return new UpdateProperties(darkenSky, color, overlay);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeShortLE(output, darkenSky());
                writeUnsignedInt(output, color());
                writeUnsignedInt(output, overlay());
            }

            @Override
            public int id() {
                return UPDATE_PROPERTIES;
            }
        }
        /**
         * Not implemented :( Intended to alter bar appearance, but these currently produce no effect on clientside whatsoever.
         */
        int UPDATE_STYLE = 7;
        record UpdateStyle(int color, int overlay) implements Action291, Action486 {
            public static final Interpreter<UpdateStyle> INTERPRETER = new Interpreter<UpdateStyle>() {
                @Override
                public @NotNull UpdateStyle interpret(@NotNull BitInput input) throws IOException {
                    int color = readUnsignedInt(input);
                    int overlay = readUnsignedInt(input);
                    return new UpdateStyle(color, overlay);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeUnsignedInt(output, color());
                writeUnsignedInt(output, overlay());
            }

            @Override
            public int id() {
                return UPDATE_STYLE;
            }
        }

        int QUERY = 8;
        record Query(long playerUniqueEntityId) implements Action486 {
            public static final Interpreter<Query> INTERPRETER = new Interpreter<Query>() {
                @Override
                public @NotNull Query interpret(@NotNull BitInput input) throws IOException {
                    long playerUniqueEntityId = readLong(input);
                    return new Query(playerUniqueEntityId);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeLong(output, playerUniqueEntityId());
            }

            @Override
            public int id() {
                return QUERY;
            }
        }
    }

    record v291(long bossUniqueEntityId, @NotNull Action.Action291 action) implements BossEventPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long bossUniqueEntityId = readLong(input);
                int actionId = readUnsignedInt(input);
                Action.Action291 action = switch (actionId) {
                    case Action.CREATE -> Action.Create.INTERPRETER.interpret(input);
                    case Action.REGISTER_PLAYER -> Action.RegisterPlayer.INTERPRETER.interpret(input);
                    case Action.REMOVE -> Action.Remove.INTERPRETER.interpret(input);
                    case Action.UNREGISTER_PLAYER -> Action.UnregisterPlayer.INTERPRETER.interpret(input);
                    case Action.UPDATE_PERCENTAGE -> Action.UpdatePercentage.INTERPRETER.interpret(input);
                    case Action.UPDATE_NAME -> Action.UpdateName.INTERPRETER.interpret(input);
                    case Action.UPDATE_PROPERTIES -> Action.UpdateProperties.INTERPRETER.interpret(input);
                    case Action.UPDATE_STYLE -> Action.UpdateStyle.INTERPRETER.interpret(input);
                    default -> throw new IllegalStateException("Unexpected value: " + actionId);
                };
                return new v291(bossUniqueEntityId, action);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, bossUniqueEntityId());
            writeUnsignedInt(output, action.id());
            action.write(output);
        }
    }

    record v486(long bossUniqueEntityId, @NotNull Action.Action486 action) implements BossEventPacket, Codec486 {
        public static final Interpreter<v486> INTERPRETER = new Interpreter<v486>() {
            @Override
            public @NotNull v486 interpret(@NotNull BitInput input) throws IOException {
                long bossUniqueEntityId = readLong(input);
                int actionId = readUnsignedInt(input);
                Action.Action486 action = switch (actionId) {
                    case Action.CREATE -> Action.Create.INTERPRETER.interpret(input);
                    case Action.REGISTER_PLAYER -> Action.RegisterPlayer.INTERPRETER.interpret(input);
                    case Action.REMOVE -> Action.Remove.INTERPRETER.interpret(input);
                    case Action.UNREGISTER_PLAYER -> Action.UnregisterPlayer.INTERPRETER.interpret(input);
                    case Action.UPDATE_PERCENTAGE -> Action.UpdatePercentage.INTERPRETER.interpret(input);
                    case Action.UPDATE_NAME -> Action.UpdateName.INTERPRETER.interpret(input);
                    case Action.UPDATE_PROPERTIES -> Action.UpdateProperties.INTERPRETER.interpret(input);
                    case Action.UPDATE_STYLE -> Action.UpdateStyle.INTERPRETER.interpret(input);
                    case Action.QUERY -> Action.Query.INTERPRETER.interpret(input);
                    default -> throw new IllegalStateException("Unexpected value: " + actionId);
                };
                return new v486(bossUniqueEntityId, action);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, bossUniqueEntityId());
            writeUnsignedInt(output, action.id());
            action.write(output);
        }
    }
}
