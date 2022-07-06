package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.PacketDataWriter;

public interface ClientPlayMode implements BitDataWritable, PacketDataWriter {
    int NORMAL = 0;
    int TEASER = 1;
    int SCREEN = 2;
    int VIEWER = 3;
    int REALITY = 4;
    int PLACEMENT = 5;
    int LIVING_ROOM = 6;
    int EXIT_LEVEL = 7;
    int EXIT_LEVEL_LIVING_ROOM = 8;

    int id();

    interface Normal extends ClientPlayMode {
        @Override
        default int id() {
            return NORMAL;
        }
    }
    interface Teaser extends ClientPlayMode {
        @Override
        default int id() {
            return TEASER;
        }
    }
    interface Screen extends ClientPlayMode {
        @Override
        default int id() {
            return SCREEN;
        }
    }
    interface Viewer extends ClientPlayMode {
        @Override
        default int id() {
            return VIEWER;
        }
    }
    interface Reality extends ClientPlayMode {
        @Override
        default int id() {
            return REALITY;
        }
    }
    interface Placement extends ClientPlayMode {
        @Override
        default int id() {
            return PLACEMENT;
        }
    }
    interface LivingRoom extends ClientPlayMode {
        @Override
        default int id() {
            return LIVING_ROOM;
        }
    }
    interface ExitLevel extends ClientPlayMode {
        @Override
        default int id() {
            return EXIT_LEVEL;
        }
    }
    interface ExitLevelLivingRoom extends ClientPlayMode {
        @Override
        default int id() {
            return EXIT_LEVEL_LIVING_ROOM;
        }
    }
}
