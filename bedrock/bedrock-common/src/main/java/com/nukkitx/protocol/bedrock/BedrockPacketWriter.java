package com.nukkitx.protocol.bedrock;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumConstraintData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumConstraintType;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.data.skin.AnimationData;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.serializer.PacketDataReader;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public interface BedrockPacketWriter extends PacketDataWriter {
    default void writeAttribute(@NotNull BitOutput output, @NotNull AttributeData value) throws IOException {
        writeString(output, value.name());
        writeFloatLE(output, value.minimum());
        writeFloatLE(output, value.maximum());
        writeFloatLE(output, value.value());
    }

    default void writeEntityData(@NotNull BitOutput output,  @NotNull Map<EntityData.Type<?>, EntityData> value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    default void writeEntityLink(@NotNull BitOutput output,  @NotNull EntityLinkData value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    default void writeItem(@NotNull BitOutput output, @NotNull ItemData value) throws IOException {
    }

    default void writeCommandEnum(@NotNull BitOutput output, CommandEnumData value) {
    }
}
