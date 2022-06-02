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

interface SetTitlePacket extends BedrockPacket {
    private Type valueType;
    private String text;
    private int fadeInTime;
    private int stayTime;
    private int fadeOutTime;
    private String xuid;
    private String platformOnlineId;


    public enum Type {
        CLEAR,
        RESET,
        TITLE,
        SUBTITLE,
        ACTIONBAR,
        TIMES,
        TITLE_JSON,
        SUBTITLE_JSON,
        ACTIONBAR_JSON
    }

    public class SetTitleReader_v291 implements BedrockPacketReader<SetTitlePacket> {
        public static final SetTitleReader_v291 INSTANCE = new SetTitleReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
            VarInts.writeInt(buffer, packet.getType().ordinal());
            helper.writeString(buffer, packet.getText());
            VarInts.writeInt(buffer, packet.getFadeInTime());
            VarInts.writeInt(buffer, packet.getStayTime());
            VarInts.writeInt(buffer, packet.getFadeOutTime());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
            packet.setType(SetTitlePacket.Type.values()[VarInts.readInt(buffer)]);
            packet.setText(helper.readString(buffer));
            packet.setFadeInTime(VarInts.readInt(buffer));
            packet.setStayTime(VarInts.readInt(buffer));
            packet.setFadeOutTime(VarInts.readInt(buffer));
        }
    }

    public class SetTitleReader_v448 extends SetTitleReader_v291 {

        public static final SetTitleReader_v448 INSTANCE = new SetTitleReader_v448();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
            super.serialize(buffer, helper, packet);

            helper.writeString(buffer, packet.getXuid());
            helper.writeString(buffer, packet.getPlatformOnlineId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetTitlePacket packet) {
            super.deserialize(buffer, helper, packet);

            packet.setXuid(helper.readString(buffer));
            packet.setPlatformOnlineId(helper.readString(buffer));
        }
    }


}
