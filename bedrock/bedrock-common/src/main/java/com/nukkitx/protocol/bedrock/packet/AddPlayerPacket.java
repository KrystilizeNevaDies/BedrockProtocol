package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface AddPlayerPacket extends BedrockPacket {
    @NotNull UUID uuid();

    @NotNull String username();

    long uniqueEntityId();

    long runtimeEntityId();

    @NotNull String platformChatId();

    @NotNull Vector3f position();

    @NotNull Vector3f motion();

    @NotNull Vector3f rotation();

    @NotNull ItemData hand();

    @NotNull Map<EntityData.Type<?>, EntityData> metadata();

    @NotNull AdventureSettingsPacket adventureSettings();

    record v291(
            UUID uuid, String username, long uniqueEntityId, long runtimeEntityId, String platformChatId,
            Vector3f position, Vector3f motion, Vector3f rotation, ItemData hand,
            Map<EntityData.Type<?>, EntityData> metadata, AdventureSettingsPacket adventureSettings
    ) implements AddPlayerPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                UUID uuid = readUuid(input);
                String username = readString(input);
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                String platformChatId = readString(input);
                Vector3f position = readVector3f(input);
                Vector3f motion = readVector3f(input);
                Vector3f rotation = readVector3f(input);
                ItemData hand = readItem(input);
                Map<EntityData.Type<?>, EntityData> metadata = readEntityData(input);
                AdventureSettingsPacket adventureSettings = AdventureSettingsPacket.v291.INTERPRETER.interpret(input);
                return new v291(uuid, username, uniqueEntityId, runtimeEntityId, platformChatId, position, motion,
                        rotation, hand, metadata, adventureSettings);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUuid(output, uuid());
            writeString(output, username());
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeString(output, platformChatId());
            writeVector3f(output, position());
            writeVector3f(output, motion());
            writeVector3f(output, rotation());
            writeItem(output, hand());
            writeEntityData(output, metadata());
            adventureSettings().write(output);
        }
    }

    record v388(
            UUID uuid, String username, long uniqueEntityId, long runtimeEntityId, String platformChatId,
            Vector3f position, Vector3f motion, Vector3f rotation, ItemData hand,
            Map<EntityData.Type<?>, EntityData> metadata, AdventureSettingsPacket adventureSettings, int buildPlatform
    ) implements AddPlayerPacket, Codec388 {
        public static final Interpreter<v388> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v388 interpret(@NotNull BitInput input) throws IOException {
                UUID uuid = readUuid(input);
                String username = readString(input);
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                String platformChatId = readString(input);
                Vector3f position = readVector3f(input);
                Vector3f motion = readVector3f(input);
                Vector3f rotation = readVector3f(input);
                ItemData hand = readItem(input);
                Map<EntityData.Type<?>, EntityData> metadata = readEntityData(input);
                AdventureSettingsPacket adventureSettings = AdventureSettingsPacket.v291.INTERPRETER.interpret(input);
                int buildPlatform = readIntLE(input);
                return new v388(uuid, username, uniqueEntityId, runtimeEntityId, platformChatId, position, motion,
                        rotation, hand, metadata, adventureSettings, buildPlatform);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUuid(output, uuid());
            writeString(output, username());
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeString(output, platformChatId());
            writeVector3f(output, position());
            writeVector3f(output, motion());
            writeVector3f(output, rotation());
            writeItem(output, hand());
            writeEntityData(output, metadata());
            adventureSettings().write(output);
            writeIntLE(output, buildPlatform());
        }
    }

    record v503(
            UUID uuid, String username, long uniqueEntityId, long runtimeEntityId, String platformChatId,
            Vector3f position, Vector3f motion, Vector3f rotation, ItemData hand, GameType gameType,
            Map<EntityData.Type<?>, EntityData> metadata, AdventureSettingsPacket adventureSettings,
            EntityLinkData[] entityLinks, String deviceId, int buildPlatform
    ) implements AddPlayerPacket, Codec503 {

        public static final Interpreter<v503> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v503 interpret(@NotNull BitInput input) throws IOException {
                UUID uuid = readUuid(input);
                String username = readString(input);
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                String platformChatId = readString(input);
                Vector3f position = readVector3f(input);
                Vector3f motion = readVector3f(input);
                Vector3f rotation = readVector3f(input);
                ItemData hand = readItem(input);
                GameType gameType = GameType.values()[readInt(input)];
                Map<EntityData.Type<?>, EntityData> metadata = readEntityData(input);
                AdventureSettingsPacket adventureSettings = AdventureSettingsPacket.v291.INTERPRETER.interpret(input);
                EntityLinkData[] entityLinks = readArray(input, this::readEntityLink);
                String deviceId = readString(input);
                int buildPlatform = readInt(input);
                return new v503(uuid, username, uniqueEntityId, runtimeEntityId, platformChatId, position, motion,
                        rotation, hand, gameType, metadata, adventureSettings, entityLinks, deviceId, buildPlatform);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeUuid(output, uuid());
            writeString(output, username());
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeString(output, platformChatId());
            writeVector3f(output, position());
            writeVector3f(output, motion());
            writeVector3f(output, rotation());
            writeItem(output, hand());
            writeInt(output, gameType().ordinal());
            writeEntityData(output, metadata());
            adventureSettings().write(output);
            writeArray(output, entityLinks(), this::writeEntityLink);
            writeString(output, deviceId());
            writeInt(output, buildPlatform());
        }
    }


}
