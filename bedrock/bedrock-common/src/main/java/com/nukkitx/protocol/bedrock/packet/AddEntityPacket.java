package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public interface AddEntityPacket extends BedrockPacket {

    long uniqueEntityId();

    long runtimeEntityId();

    @NotNull Vector3f position();

    @NotNull Vector3f motion();

    @NotNull Vector3f rotation();

    @NotNull AttributeData[] attributes();

    @NotNull Map<EntityData.Type<?>, EntityData> metadata();

    @NotNull EntityLinkData[] entityLinks();

    interface EntityTypeByInt {
        int entityType();
    }

    interface EntityTypeByString {
        @NotNull String entityType();
    }

    record v291(long uniqueEntityId, long runtimeEntityId, int entityType, Vector3f position, Vector3f motion,
                Vector3f rotation, AttributeData[] attributes, Map<EntityData.Type<?>, EntityData> metadata,
                EntityLinkData[] entityLinks
    ) implements AddEntityPacket, BedrockPacket.Codec291, EntityTypeByInt {

        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                int entityType = readUnsignedInt(input);
                Vector3f position = readVector3f(input);
                Vector3f motion = readVector3f(input);
                Vector3f rotation = readVector3f(input);
                AttributeData @NotNull [] attributeData = readArray(input, this::readAttribute);
                @NotNull Map<EntityData.Type<?>, EntityData> metaData = readEntityData(input);
                EntityLinkData @NotNull [] entityLinkData = readArray(input, this::readEntityLink);
                return new v291(uniqueEntityId, runtimeEntityId, entityType, position, motion, rotation, attributeData,
                        metaData, entityLinkData);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeUnsignedInt(output, entityType());
            writeVector3f(output, position());
            writeVector3f(output, motion());
            writeVector3f(output, rotation());
            writeArray(output, attributes(), this::writeAttribute);
            writeEntityData(output, metadata());
            writeArray(output, entityLinks(), this::writeEntityLink);
        }
    }

    record v313(long uniqueEntityId, long runtimeEntityId, String entityType, Vector3f position, Vector3f motion,
                Vector3f rotation, AttributeData[] attributes, Map<EntityData.Type<?>, EntityData> metadata,
                EntityLinkData[] entityLinks
    ) implements AddEntityPacket, BedrockPacket.Codec313, EntityTypeByString {
        public static final Interpreter<v313> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v313 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                long RuntimeEntityId = readUnsignedLong(input);
                String Identifier = readString(input);
                Vector3f Position = readVector3f(input);
                Vector3f Motion = readVector3f(input);
                Vector3f Rotation = readVector3f(input);
                AttributeData[] attributes = readArray(input, this::readAttribute);
                Map<EntityData.Type<?>, EntityData> metadata = readEntityData(input);
                EntityLinkData[] entityLinks = readArray(input, this::readEntityLink);
                return new v313(uniqueEntityId, RuntimeEntityId, Identifier, Position, Motion, Rotation, attributes,
                        metadata, entityLinks);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeString(output, entityType());
            writeVector3f(output, position());
            writeVector3f(output, motion());
            writeVector3f(output, rotation());
            writeArray(output, attributes(), this::writeAttribute);
            writeEntityData(output, metadata());
            writeArray(output, entityLinks(), this::writeEntityLink);
        }
    }
}
