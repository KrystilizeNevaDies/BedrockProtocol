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

public interface SetLocalPlayerAsInitializedPacket extends BedrockPacket {
    private long runtimeEntityId;


    public class SetLocalPlayerAsInitializedReader_v291 implements BedrockPacketReader<SetLocalPlayerAsInitializedPacket> {
        public static final SetLocalPlayerAsInitializedReader_v291 INSTANCE = new SetLocalPlayerAsInitializedReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetLocalPlayerAsInitializedPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetLocalPlayerAsInitializedPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        }
    }

}
