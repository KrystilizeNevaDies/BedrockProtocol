package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PhotoType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

interface PhotoTransferPacket extends BedrockPacket {
    private String name;
    private byte[] data;
    private String bookId;
    /**
     * @since v465
     */
    private PhotoType photoType;
    /**
     * @since v465
     */
    private PhotoType sourceType;
    /**
     * @since v465
     */
    private long ownerId;
    /**
     * @since v465
     */
    private String newPhotoName;


    public class PhotoTransferReader_v291 implements BedrockPacketReader<PhotoTransferPacket> {
        public static final PhotoTransferReader_v291 INSTANCE = new PhotoTransferReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
            helper.writeString(buffer, packet.getName());
            byte[] data = packet.getData();
            VarInts.writeUnsignedInt(buffer, data.length);
            buffer.writeBytes(data);
            helper.writeString(buffer, packet.getBookId());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
            packet.setName(helper.readString(buffer));
            byte[] data = new byte[VarInts.readUnsignedInt(buffer)];
            buffer.readBytes(data);
            packet.setData(data);
            packet.setBookId(helper.readString(buffer));
        }
    }

    public class PhotoTransferReader_v465 extends PhotoTransferReader_v291 {

        public static final PhotoTransferReader_v465 INSTANCE = new PhotoTransferReader_v465();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
            super.serialize(buffer, helper, packet);
            buffer.writeByte(packet.getPhotoType().ordinal());
            buffer.writeByte(packet.getSourceType().ordinal());
            buffer.writeLongLE(packet.getOwnerId());
            helper.writeString(buffer, packet.getNewPhotoName());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PhotoTransferPacket packet) {
            super.deserialize(buffer, helper, packet);
            packet.setPhotoType(PhotoType.from(buffer.readByte()));
            packet.setSourceType(PhotoType.from(buffer.readByte()));
            packet.setOwnerId(buffer.readLongLE());
            packet.setNewPhotoName(helper.readString(buffer));
        }
    }


}
