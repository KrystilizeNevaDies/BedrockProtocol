package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface BookEditPacket extends BedrockPacket {
    @NotNull Action action();

    interface Action extends BitDataWritable, PacketDataWriter {
        int actionId();

        int REPLACE_PAGE = 0;
        record ReplacePage(int pageNumber, @NotNull String text,
                           @NotNull String photoName) implements Action {

            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeByte(output, (byte) pageNumber);
                writeString(output, text);
                writeString(output, photoName);
            }

            @Override
            public int actionId() {
                return REPLACE_PAGE;
            }
        }

        int ADD_PAGE = 1;
        record AddPage(int pageNumber, @NotNull String text,
                       @NotNull String photoName) implements Action {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeByte(output, (byte) pageNumber);
                writeString(output, text);
                writeString(output, photoName);
            }

            @Override
            public int actionId() {
                return ADD_PAGE;
            }
        }

        int DELETE_PAGE = 2;
        record DeletePage(int pageNumber) implements Action {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeByte(output, (byte) pageNumber);
            }

            @Override
            public int actionId() {
                return DELETE_PAGE;
            }
        }

        int SWAP_PAGES = 3;
        record SwapPages(int pageA, int pageB) implements Action {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeByte(output, (byte) pageA);
                writeByte(output, (byte) pageB);
            }

            @Override
            public int actionId() {
                return SWAP_PAGES;
            }
        }

        int SIGN_BOOK = 4;
        record SignBook(@NotNull String title, @NotNull String author,
                        @NotNull String xuid) implements Action {
            @Override
            public void write(@NotNull BitOutput output) throws IOException {
                writeString(output, title);
                writeString(output, author);
                writeString(output, xuid);
            }

            @Override
            public int actionId() {
                return SIGN_BOOK;
            }
        }
    }

    record v291(@NotNull Action action, int inventorySlot) implements BookEditPacket, Codec291 {
        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            Action action = action();
            writeByte(output, (byte) action.actionId());
            writeByte(output, (byte) inventorySlot());
            action.write(output);
        }
    }

}
