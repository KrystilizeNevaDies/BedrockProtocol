package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Parameter;

public class BedrockPacketGenerator {

    private final @NotNull Class<? extends Record> recordClass;
    private final Signature signature;

    public BedrockPacketGenerator(@NotNull Class<? extends Record> recordClass) {
        this.recordClass = recordClass;
        this.signature = signature();
    }

    private @NotNull Signature signature() {
        System.out.println("Reading signature of " + recordClass);
        var constructor = recordClass.getConstructors()[0];
        for (Parameter param : constructor.getParameters()) {
            System.out.println(param.getName() + ": " + param.getType());
        }
        return new Signature(recordClass);
    }

    public BedrockPacket.Interpreter<?> interpreter() {
        return null;
    }

    public BedrockPacket.Deferer<?> deferer() {
        return null;
    }


    private record Signature(Class<? extends Record> recordClass) {

    }

    private record Part(Type type, boolean nullable) {

    }

    private record Type() {
    }
}
