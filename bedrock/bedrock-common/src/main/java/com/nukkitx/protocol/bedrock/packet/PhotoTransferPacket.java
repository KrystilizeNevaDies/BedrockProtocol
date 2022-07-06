package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface PhotoTransferPacket extends BedrockPacket {
//    String name;
//    byte[] data;
//    String bookId;
//    /**
//     * @since v465
//     */
//    PhotoType photoType;
//    /**
//     * @since v465
//     */
//    PhotoType sourceType;
//    /**
//     * @since v465
//     */
//    long ownerId;
//    /**
//     * @since v465
//     */
//    String newPhotoName;


    record v291(String name, byte[] data, String bookId) implements PhotoTransferPacket {
        public static final Interpreter<v291> INTERPRETER = Interpreter.generate(v291.class);
        public static final Deferer<v291> DEFERER = Deferer.generate(v291.class);

        @Override
        public void write(@NotNull BitOutput writer) throws IOException {
            DEFERER.defer(writer, this);
        }
    }

    record v465(String name, byte[] data, String bookId, @AsByte int photoType, @AsByte int sourceType,
                @LE long ownerId, String newPhotoName) implements PhotoTransferPacket {
        public static final Interpreter<v465> INTERPRETER = Interpreter.generate(v465.class);
        public static final Deferer<v465> DEFERER = Deferer.generate(v465.class);

        @Override
        public void write(@NotNull BitOutput writer) throws IOException {
            DEFERER.defer(writer, this);
        }
    }
}
