package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public interface AddItemEntityPacket extends BedrockPacket {
    Map<EntityData.Type<?>, EntityData> metadata();

    long uniqueEntityId();

    long runtimeEntityId();

    ItemData itemInHand();

    Vector3f position();

    Vector3f motion();

    boolean fromFishing();

    record v291(
            long uniqueEntityId, long runtimeEntityId, ItemData itemInHand, Vector3f position, Vector3f motion,
            Map<EntityData.Type<?>, EntityData> metadata, boolean fromFishing
    ) implements AddItemEntityPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<v291>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                long uniqueEntityId = readLong(input);
                long runtimeEntityId = readUnsignedLong(input);
                ItemData itemInHand = readItem(input);
                Vector3f position = readVector3f(input);
                Vector3f motion = readVector3f(input);
                Map<EntityData.Type<?>, EntityData> metadata = readEntityData(input);
                boolean fromFishing = input.readBoolean();
                return new v291(uniqueEntityId, runtimeEntityId, itemInHand, position, motion, metadata, fromFishing);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeLong(output, uniqueEntityId());
            writeUnsignedLong(output, runtimeEntityId());
            writeItem(output, itemInHand());
            writeVector3f(output, position());
            writeVector3f(output, motion());
            writeEntityData(output, metadata());
            writeBoolean(output, fromFishing());
        }
    }
}
