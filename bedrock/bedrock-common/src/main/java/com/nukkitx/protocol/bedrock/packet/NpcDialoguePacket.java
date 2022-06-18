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

interface NpcDialoguePacket extends BedrockPacket {

    long uniqueEntityId;
    Action action;
    String dialogue;
    String sceneName;
    String npcName;
    String actionJson;


    @Overrid

    public enum Action {
        OPEN,
        CLOSE
    }

    record v448 implements NpcDialoguePacket {


        static final NpcDialoguePacket.Action[] VALUES = NpcDialoguePacket.Action.values();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NpcDialoguePacket packet) {
            buffer.writeLongLE(packet.getUniqueEntityId());
            VarInts.writeInt(buffer, packet.getAction().ordinal());
            helper.writeString(buffer, packet.getDialogue());
            helper.writeString(buffer, packet.getSceneName());
            helper.writeString(buffer, packet.getNpcName());
            helper.writeString(buffer, packet.getActionJson());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NpcDialoguePacket packet) {
            packet.setUniqueEntityId(buffer.readLongLE());
            packet.setAction(VALUES[VarInts.readInt(buffer)]);
            packet.setDialogue(helper.readString(buffer));
            packet.setSceneName(helper.readString(buffer));
            packet.setNpcName(helper.readString(buffer));
            packet.setActionJson(helper.readString(buffer));
        }
    }

}
