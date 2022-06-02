package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.MapDecoration;
import com.nukkitx.protocol.bedrock.data.MapTrackedObject;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

interface ClientboundMapItemDataPacket extends BedrockPacket {

    @NotNull MapComponents mapComponents();

    interface MapComponents extends BitDataWritable, PacketDataWriter {
        int flag();


        int COLORS_FLAG = 0x2;
        int DECORATIONS_AND_OBJECTS_FLAG = 0x4;
        int ENTITIES_FLAG = 0x8;
        int COLORS_AND_ENTITIES_FLAG = COLORS_FLAG | ENTITIES_FLAG;
        int COLORS_DECORATIONS_AND_ENTITIES_FLAG = COLORS_FLAG | DECORATIONS_AND_OBJECTS_FLAG | ENTITIES_FLAG;

        Int2ObjectMap<Interpreter<? extends MapComponents>> INTERPRETERS = new Int2ObjectOpenHashMap<>() {{
            put(DECORATIONS_AND_OBJECTS_FLAG, DecorationsAndObjects.INTERPRETER);
            put(COLORS_AND_ENTITIES_FLAG, TrackedEntitiesAndColorPatch.INTERPRETER);
            put(COLORS_DECORATIONS_AND_ENTITIES_FLAG, DecorationsObjectsEntitiesAndColorPatch.INTERPRETER);
        }};

        record DecorationsAndObjects(@NotNull MapDecoration @NotNull [] decorations,
                                     @NotNull MapTrackedObject @NotNull [] trackedObjects) implements MapComponents {
            public static final Interpreter<DecorationsAndObjects> INTERPRETER = new Interpreter<DecorationsAndObjects>() {
                @Override
                public @NotNull DecorationsAndObjects interpret(@NotNull BitInput input) throws IOException {
                    MapDecoration[] decorations = readArray(input, MapDecoration.INTERPRETER::interpret);
                    MapTrackedObject[] trackedObjects = readArray(input, MapTrackedObject.INTERPRETER::interpret);
                    return new DecorationsAndObjects(decorations, trackedObjects);
                }
            };

            @Override
            public int flag() {
                return DECORATIONS_AND_OBJECTS_FLAG;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeArray(output, trackedObjects());
                writeArray(output, decorations());
            }
        }

        record TrackedEntitiesAndColorPatch(@NotNull TrackedEntities trackedEntityIds,
                                            @NotNull ColorPatch colors) implements MapComponents {
            public static final Interpreter<TrackedEntitiesAndColorPatch> INTERPRETER = new Interpreter<>() {
                @Override
                public @NotNull TrackedEntitiesAndColorPatch interpret(@NotNull BitInput input) throws IOException {
                    TrackedEntities trackedEntityIds = TrackedEntities.INTERPRETER.interpret(input);
                    ColorPatch colors = ColorPatch.INTERPRETER.interpret(input);
                    return new TrackedEntitiesAndColorPatch(trackedEntityIds, colors);
                }
            };

            @Override
            public int flag() {
                return COLORS_AND_ENTITIES_FLAG;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                trackedEntityIds().write(output);
                colors().write(output);
            }
        }

        record DecorationsObjectsEntitiesAndColorPatch(@NotNull DecorationsAndObjects decorationsAndObjects, int scale,
                @NotNull TrackedEntities trackedEntities, @NotNull ColorPatch colorPatch) implements MapComponents {
            public static final Interpreter<DecorationsObjectsEntitiesAndColorPatch> INTERPRETER = new Interpreter<>() {
                @Override
                public @NotNull DecorationsObjectsEntitiesAndColorPatch interpret(@NotNull BitInput input) throws IOException {
                    var trackedEntities = TrackedEntities.INTERPRETER.interpret(input);
                    int scale = readByte(input);
                    var decorationsAndObjects = DecorationsAndObjects.INTERPRETER.interpret(input);
                    var colorPatch = ColorPatch.INTERPRETER.interpret(input);
                    return new DecorationsObjectsEntitiesAndColorPatch(decorationsAndObjects, scale, trackedEntities, colorPatch);
                }
            };

            @Override
            public int flag() {
                return decorationsAndObjects().flag() | ENTITIES_FLAG | COLORS_FLAG;
            }

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                trackedEntities().write(output);
                writeByte(output, (byte) scale());
                decorationsAndObjects().write(output);
                colorPatch().write(output);
            }
        }

        record ColorPatch(int @NotNull [] colors, int width, int height, int xOffset,
                          int yOffset) implements BitDataWritable, PacketDataWriter {
            public static final Interpreter<ColorPatch> INTERPRETER = new Interpreter<ColorPatch>() {
                @Override
                public @NotNull ColorPatch interpret(@NotNull BitInput input) throws IOException {
                    int width = readInt(input);
                    int height = readInt(input);
                    int xOffset = readInt(input);
                    int yOffset = readInt(input);
                    int length = readUnsignedInt(input);
                    int[] colors = new int[length];
                    for (int i = 0; i < length; i++) {
                        colors[i] = readUnsignedInt(input);
                    }
                    return new ColorPatch(colors, width, height, xOffset, yOffset);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeInt(output, width());
                writeInt(output, height());
                writeInt(output, xOffset());
                writeInt(output, yOffset());
                writeUnsignedInt(output, colors().length);
                for (int color : colors()) {
                    writeUnsignedInt(output, color);
                }
            }
        }

        record TrackedEntities(long @NotNull [] trackedEntityIds) implements BitDataWritable, PacketDataWriter {
            public static final Interpreter<TrackedEntities> INTERPRETER = new Interpreter<TrackedEntities>() {
                @Override
                public @NotNull TrackedEntities interpret(@NotNull BitInput input) throws IOException {
                    int length = readUnsignedInt(input);
                    long[] trackedEntityIds = new long[length];
                    for (int i = 0; i < length; i++) {
                        trackedEntityIds[i] = readUnsignedLong(input);
                    }
                    return new TrackedEntities(trackedEntityIds);
                }
            };

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeUnsignedInt(output, trackedEntityIds().length);
                for (long entityId : trackedEntityIds()) {
                    writeUnsignedLong(output, entityId);
                }
            }
        }
    }

    record v291(long uniqueMapId, @NotNull MapComponents mapComponents, int dimensionId) implements ClientboundMapItemDataPacket {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long uniqueMapId = readLong(input);
                int flags = readUnsignedInt(input);
                int dimensionId = readByte(input);
                Interpreter<? extends MapComponents> interpreter = MapComponents.INTERPRETERS.get(flags);
                if (interpreter == null) {
                    throw new IOException("Unknown map components flags: " + flags);
                }
                MapComponents mapComponents = interpreter.interpret(input);
                return new v291(uniqueMapId, mapComponents, dimensionId);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueMapId());
            writeUnsignedInt(output, mapComponents().flag());
            writeByte(output, (byte) dimensionId());
            mapComponents().write(output);
        }
    }

    record v354(long uniqueMapId, @NotNull MapComponents mapComponents, int dimensionId, boolean isLocked) implements ClientboundMapItemDataPacket {

        public static final Interpreter<v354> INTERPRETER = new Interpreter<v354>() {
            @Override
            public @NotNull v354 interpret(@NotNull BitInput input) throws IOException {
                long uniqueMapId = readLong(input);
                int flags = readUnsignedInt(input);
                int dimensionId = readByte(input);
                boolean isLocked = readBoolean(input);
                Interpreter<? extends MapComponents> interpreter = MapComponents.INTERPRETERS.get(flags);
                if (interpreter == null) {
                    throw new IOException("Unknown map components flags: " + flags);
                }
                MapComponents mapComponents = interpreter.interpret(input);
                return new v354(uniqueMapId, mapComponents, dimensionId, isLocked);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueMapId());
            writeUnsignedInt(output, mapComponents().flag());
            writeByte(output, (byte) dimensionId());
            writeBoolean(output, isLocked());
            mapComponents().write(output);
        }
    }
}
