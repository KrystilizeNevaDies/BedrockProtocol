package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface SetEntityDataPacket extends BedrockPacket {
    private final EntityDataMap metadata = new EntityDataMap();
    private long runtimeEntityId;
    private long tick;


    public class SetEntityDataReader_v291 implements BedrockPacketReader<SetEntityDataPacket> {
        public static final SetEntityDataReader_v291 INSTANCE = new SetEntityDataReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeEntityData(buffer, packet.getMetadata());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            helper.readEntityData(buffer, packet.getMetadata());
        }
    }

    public class SetEntityDataReader_v419 extends SetEntityDataReader_v291 {

        public static final SetEntityDataReader_v419 INSTANCE = new SetEntityDataReader_v419();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
            super.serialize(buffer, helper, packet);

            VarInts.writeUnsignedLong(buffer, packet.getTick());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setTick(VarInts.readUnsignedLong(buffer));
        }
    }


}
