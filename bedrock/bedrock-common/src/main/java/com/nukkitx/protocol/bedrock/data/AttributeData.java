package com.nukkitx.protocol.bedrock.data;

import lombok.AllArgsConstructor;
import lombok.Value;

public record AttributeData(String name, float minimum, float maximum, float value, float defaultValue) {
    public AttributeData(String name, float minimum, float maximum, float value) {
        this(name, minimum, maximum, value, maximum);
    }
}
