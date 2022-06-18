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
    String displaySlot;
    String objectiveId;
    String displayName;
    String criteria;
    int sortOrder;


    record v291 implements SetDisplayObjectivePacket {


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
