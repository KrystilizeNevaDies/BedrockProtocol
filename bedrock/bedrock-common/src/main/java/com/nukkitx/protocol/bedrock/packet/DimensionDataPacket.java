package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.definition.DimensionDefinition;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Sends a list of the data-driven dimensions to the client.
 * This packet is sent before the {@link StartGamePacket} in the login sequence.
 *
 * <b>Note:</b> The client only supports sending the <code>minecraft:overworld</code> dimension as of 1.18.30
 *
 * @since v503
 */
interface DimensionDataPacket extends BedrockPacket {
    private final List<DimensionDefinition> definitions = new ObjectArrayList<>();


    @Overrid

    public class DimensionDataReader_v503 implements BedrockPacketReader<DimensionDataPacket> {
        public static final DimensionDataReader_v503 INSTANCE = new DimensionDataReader_v503();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DimensionDataPacket packet) {
            helper.writeArray(buffer, packet.getDefinitions(), this::writeDefinition);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DimensionDataPacket packet) {
            helper.readArray(buffer, packet.getDefinitions(), this::readDefinition);
        }

        protected void writeDefinition(ByteBuf buffer, BedrockPacketHelper helper, DimensionDefinition definition) {
            helper.writeString(buffer, definition.getId());
            VarInts.writeInt(buffer, definition.getMaximumHeight());
            VarInts.writeInt(buffer, definition.getMinimumHeight());
            VarInts.writeInt(buffer, definition.getGeneratorType());
        }

        protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockPacketHelper helper) {
            String id = helper.readString(buffer);
            int maximumHeight = VarInts.readInt(buffer);
            int minimumHeight = VarInts.readInt(buffer);
            int generatorType = VarInts.readInt(buffer);
            return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType);
        }
    }
}