package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static com.nukkitx.protocol.bedrock.data.AdventureSetting.*;
import static com.nukkitx.protocol.bedrock.data.AdventureSetting.DEFAULT_LEVEL_PERMISSIONS;

public interface AdventureSettingsPacket extends BedrockPacket {
    CommandPermission commandPermission();

    PlayerPermission playerPermission();

    Set<AdventureSetting> settings();

    long uniqueEntityId();

    record v291(CommandPermission commandPermission, PlayerPermission playerPermission, Set<AdventureSetting> settings,
                long uniqueEntityId) implements AdventureSettingsPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                int flags1 = readUnsignedInt(input);
                CommandPermission commandPermission = COMMAND_PERMISSIONS[readUnsignedInt(input)];
                int flags2 = readUnsignedInt(input);
                PlayerPermission playerPermission = PLAYER_PERMISSIONS[readUnsignedInt(input)];
                int flags3 = readUnsignedInt(input); // nobody likes flags3, lets just pretend it doesn't exist
                long uniqueEntityId = readLongLE(input);

                Set<AdventureSetting> settings = new HashSet<>();
                readFlags(flags1, FLAGS_1, settings::add);
                readFlags(flags2, FLAGS_2, settings::add);

                return new v291(commandPermission, playerPermission, settings, uniqueEntityId);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            int flags1 = 0;
            int flags2 = 0;
            for (AdventureSetting setting : settings) {
                if (FLAGS_TO_BIT_1.containsKey(setting)) {
                    flags1 |= FLAGS_TO_BIT_1.getInt(setting);
                } else if (FLAGS_TO_BIT_2.containsKey(setting)) {
                    flags2 |= FLAGS_TO_BIT_2.getInt(setting);
                }
            }
            writeUnsignedInt(output, flags1);
            writeUnsignedInt(output, commandPermission.ordinal());
            writeUnsignedInt(output, flags2);
            writeUnsignedInt(output, playerPermission.ordinal());
            writeUnsignedInt(output, 0); // Useless
            writeLongLE(output, uniqueEntityId);
        }

        static final CommandPermission[] COMMAND_PERMISSIONS = CommandPermission.values();

        static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();
        static final AdventureSetting[] FLAGS_1 = {WORLD_IMMUTABLE, NO_PVM, NO_MVP, null, SHOW_NAME_TAGS, AUTO_JUMP, MAY_FLY, NO_CLIP, WORLD_BUILDER, FLYING, MUTED};

        static final AdventureSetting[] FLAGS_2 = {MINE, DOORS_AND_SWITCHES, OPEN_CONTAINERS, ATTACK_PLAYERS, ATTACK_MOBS, OPERATOR, null, TELEPORT, BUILD, DEFAULT_LEVEL_PERMISSIONS};
        static final Object2IntMap<AdventureSetting> FLAGS_TO_BIT_1 = new Object2IntOpenHashMap<>();

        static final Object2IntMap<AdventureSetting> FLAGS_TO_BIT_2 = new Object2IntOpenHashMap<>();

        static void readFlags(int flags, AdventureSetting[] mappings, Consumer<AdventureSetting> applyTo) {
            for (int i = 0; i < mappings.length; i++) {
                AdventureSetting setting = mappings[i];
                if (setting != null && (flags & (1 << i)) != 0) {
                    applyTo.accept(setting);
                }
            }
        }

        static {
            FLAGS_TO_BIT_1.put(WORLD_IMMUTABLE, (1 << 0));
            FLAGS_TO_BIT_1.put(NO_PVM, (1 << 1));
            FLAGS_TO_BIT_1.put(NO_MVP, (1 << 2));
            // ?????
            FLAGS_TO_BIT_1.put(SHOW_NAME_TAGS, (1 << 4));
            FLAGS_TO_BIT_1.put(AUTO_JUMP, (1 << 5));
            FLAGS_TO_BIT_1.put(MAY_FLY, (1 << 6));
            FLAGS_TO_BIT_1.put(NO_CLIP, (1 << 7));
            FLAGS_TO_BIT_1.put(WORLD_BUILDER, (1 << 8));
            FLAGS_TO_BIT_1.put(FLYING, (1 << 9));
            FLAGS_TO_BIT_1.put(MUTED, (1 << 10));

            // Permissions flags
            FLAGS_TO_BIT_2.put(MINE, (1 << 0));
            FLAGS_TO_BIT_2.put(DOORS_AND_SWITCHES, (1 << 1));
            FLAGS_TO_BIT_2.put(OPEN_CONTAINERS, (1 << 2));
            FLAGS_TO_BIT_2.put(ATTACK_PLAYERS, (1 << 3));
            FLAGS_TO_BIT_2.put(ATTACK_MOBS, (1 << 4));
            FLAGS_TO_BIT_2.put(OPERATOR, (1 << 5));
            // ?????
            FLAGS_TO_BIT_2.put(TELEPORT, (1 << 7));
            FLAGS_TO_BIT_2.put(BUILD, (1 << 8));
            FLAGS_TO_BIT_2.put(DEFAULT_LEVEL_PERMISSIONS, (1 << 9));
        }
    }
}
