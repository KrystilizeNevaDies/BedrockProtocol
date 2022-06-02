package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

interface PurchaseReceiptPacket extends BedrockPacket {
    private final List<String> receipts = new ObjectArrayList<>();


    public class PurchaseReceiptReader_v291 implements BedrockPacketReader<PurchaseReceiptPacket> {
        public static final PurchaseReceiptReader_v291 INSTANCE = new PurchaseReceiptReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PurchaseReceiptPacket packet) {
            helper.writeArray(buffer, packet.getReceipts(), helper::writeString);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PurchaseReceiptPacket packet) {
            helper.readArray(buffer, packet.getReceipts(), helper::readString);
        }
    }

}
