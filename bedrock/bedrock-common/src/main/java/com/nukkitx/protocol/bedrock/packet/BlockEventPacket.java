package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Used to trigger Note blocks, Chests and End Gateways
 *
 * <h2>Examples</h2>
 *
 * <h3>Note Block</h3>
 * <blockquote>
 *     eventType: (Instrument)
 *     <ul>
 *         <li>0 (Piano)</li>
 *         <li>1 (Base Drum)</li>
 *         <li>2 (Sticks)</li>
 *         <li>3 (Drum)</li>
 *         <li>4 (Bass)</li>
 *     </ul>
 *     data: 0-15
 * </blockquote>
 *
 * <h3>Chest Block</h3>
 * <blockquote>
 *     eventType: 1 (Chest open/closed)<br>
 *     data: 0 or 1
 * </blockquote>
 *
 * <h3>End Gateway</h3>
 * <blockquote>
 *     eventType: 1 (Cool down)<br>
 *     data: n/a
 * </blockquote>
 *
 **/
public interface BlockEventPacket extends BedrockPacket {

    /**
     * Position to execute block event.
     * @return block event position
     */
    Vector3i blockPosition();

    /**
     * Block event valueType to execute
     * @return block event valueType
     */
    int eventType();

    /**
     * Data used by event (if applicable)
     * @return data for event
     */
    int eventData();

    record v291(@NotNull Vector3i blockPosition, int eventType, int eventData) implements BlockEventPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                return new v291(readBlockPosition(input), readInt(input), readInt(input));
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeBlockPosition(output, blockPosition());
            writeInt(output, eventType());
            writeInt(output, eventData());
        }
    }

}
