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

public interface SetDisplayObjectivePacket extends BedrockPacket {
    private String displaySlot;
    private String objectiveId;
    private String displayName;
    private String criteria;
    private int sortOrder;


    public class SetDisplayObjectiveReader_v291 implements BedrockPacketReader<SetDisplayObjectivePacket> {
        public static final SetDisplayObjectiveReader_v291 INSTANCE = new SetDisplayObjectiveReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetDisplayObjectivePacket packet) {
            helper.writeString(buffer, packet.getDisplaySlot());
            helper.writeString(buffer, packet.getObjectiveId());
            helper.writeString(buffer, packet.getDisplayName());
            helper.writeString(buffer, packet.getCriteria());
            VarInts.writeInt(buffer, packet.getSortOrder());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetDisplayObjectivePacket packet) {
            packet.setDisplaySlot(helper.readString(buffer));
            packet.setObjectiveId(helper.readString(buffer));
            packet.setDisplayName(helper.readString(buffer));
            packet.setCriteria(helper.readString(buffer));
            packet.setSortOrder(VarInts.readInt(buffer));
        }
    }

}
