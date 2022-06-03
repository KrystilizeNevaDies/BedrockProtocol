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

public interface HurtArmorPacket extends BedrockPacket {
    private int cause;
    private int damage;
    /**
     * @since v465
     */
    private long armorSlots;


    public class HurtArmorReader_v291 implements BedrockPacketReader<HurtArmorPacket> {
        public static final HurtArmorReader_v291 INSTANCE = new HurtArmorReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
            VarInts.writeInt(buffer, packet.getDamage());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
            packet.setDamage(VarInts.readInt(buffer));
        }
    }

    public class HurtArmorReader_v407 implements BedrockPacketReader<HurtArmorPacket> {
        public static final HurtArmorReader_v407 INSTANCE = new HurtArmorReader_v407();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
            VarInts.writeInt(buffer, packet.getCause());
            VarInts.writeInt(buffer, packet.getDamage());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
            packet.setCause(VarInts.readInt(buffer));
            packet.setDamage(VarInts.readInt(buffer));
        }
    }

    public class HurtArmorReader_v465 extends HurtArmorReader_v407 {

        public static final HurtArmorReader_v465 INSTANCE = new HurtArmorReader_v465();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
            super.serialize(buffer, helper, packet);
            VarInts.writeUnsignedLong(buffer, packet.getArmorSlots());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
            super.deserialize(buffer, helper, packet);
            packet.setArmorSlots(VarInts.readUnsignedLong(buffer));
        }
    }



}
