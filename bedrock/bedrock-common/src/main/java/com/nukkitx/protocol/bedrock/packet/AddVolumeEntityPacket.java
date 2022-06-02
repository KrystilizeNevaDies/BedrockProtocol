package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface AddVolumeEntityPacket extends BedrockPacket {
    int id();
    NbtMap data();

    record v440(int id, NbtMap data) implements AddVolumeEntityPacket, Codec440 {
        public static final Interpreter<v440> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v440 interpret(@NotNull BitInput input) throws IOException {
                int id = readInt(input);
                NbtMap data = readNBTMap(input);
                return new v440(id, data);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, id());
            writeNBTMap(output, data());
        }
    }

    record v465(int id, NbtMap data, String engineVersion) implements AddVolumeEntityPacket, Codec465 {
        public static final Interpreter<v465> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v465 interpret(@NotNull BitInput input) throws IOException {
                int id = readInt(input);
                NbtMap data = readNBTMap(input);
                String engineVersion = readString(input);
                return new v465(id, data, engineVersion);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, id());
            writeNBTMap(output, data());
            writeString(output, engineVersion());
        }
    }

    record v486(int id, NbtMap data, String identifier, String instanceName, String engineVersion) implements AddVolumeEntityPacket, Codec486 {
        public static final Interpreter<v486> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v486 interpret(@NotNull BitInput input) throws IOException {
                int id = readInt(input);
                NbtMap data = readNBTMap(input);
                String identifier = readString(input);
                String instanceName = readString(input);
                String engineVersion = readString(input);
                return new v486(id, data, identifier, instanceName, engineVersion);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, id());
            writeNBTMap(output, data());
            writeString(output, identifier());
            writeString(output, instanceName());
            writeString(output, engineVersion());
        }
    }

    record v503(int id, NbtMap data, String identifier, String instanceName, Vector3i minBounds, Vector3i maxBounds,
                int dimension, String engineVersion) implements AddVolumeEntityPacket, Codec503 {
        public static final Interpreter<v503> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v503 interpret(@NotNull BitInput input) throws IOException {
                int id = readInt(input);
                NbtMap data = readNBTMap(input);
                String identifier = readString(input);
                String instanceName = readString(input);
                Vector3i minBounds = readVector3i(input);
                Vector3i maxBounds = readVector3i(input);
                int dimension = readInt(input);
                String engineVersion = readString(input);
                return new v503(id, data, identifier, instanceName, minBounds, maxBounds, dimension, engineVersion);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeInt(output, id());
            writeNBTMap(output, data());
            writeString(output, identifier());
            writeString(output, instanceName());
            writeVector3i(output, minBounds());
            writeVector3i(output, maxBounds());
            writeInt(output, dimension());
            writeString(output, engineVersion());
        }
    }

}
