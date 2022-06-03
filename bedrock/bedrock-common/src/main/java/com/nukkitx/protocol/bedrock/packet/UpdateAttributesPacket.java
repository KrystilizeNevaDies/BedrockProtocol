package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public interface UpdateAttributesPacket extends BedrockPacket {
    private long runtimeEntityId;
    private List<AttributeData> attributes = new ObjectArrayList<>();
    private long tick;


    public class UpdateAttributesReader_v291 implements BedrockPacketReader<UpdateAttributesPacket> {
        public static final UpdateAttributesReader_v291 INSTANCE = new UpdateAttributesReader_v291();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeArray(buffer, packet.getAttributes(), this::writeAttribute);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            helper.readArray(buffer, packet.getAttributes(), this::readAttribute);
        }

        public AttributeData readAttribute(ByteBuf buffer, BedrockPacketHelper helper) {
            Preconditions.checkNotNull(buffer, "buffer");

            float min = buffer.readFloatLE();
            float max = buffer.readFloatLE();
            float val = buffer.readFloatLE();
            float def = buffer.readFloatLE();
            String name = helper.readString(buffer);

            return new AttributeData(name, min, max, val, def);
        }

        public void writeAttribute(ByteBuf buffer, BedrockPacketHelper helper, AttributeData attribute) {
            Preconditions.checkNotNull(buffer, "buffer");
            Preconditions.checkNotNull(attribute, "attribute");

            buffer.writeFloatLE(attribute.getMinimum());
            buffer.writeFloatLE(attribute.getMaximum());
            buffer.writeFloatLE(attribute.getValue());
            buffer.writeFloatLE(attribute.getDefaultValue());
            helper.writeString(buffer, attribute.getName());
        }
    }

    public class UpdateAttributesReader_v419 extends UpdateAttributesReader_v291 {

        public static final UpdateAttributesReader_v419 INSTANCE = new UpdateAttributesReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
            super.serialize(buffer, helper, packet);

            VarInts.writeUnsignedLong(buffer, packet.getTick());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setTick(VarInts.readUnsignedLong(buffer));
        }
    }

}
