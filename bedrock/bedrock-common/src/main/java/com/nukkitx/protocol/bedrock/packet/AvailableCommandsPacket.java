package com.nukkitx.protocol.bedrock.packet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.protocol.bedrock.data.command.CommandData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumConstraintData;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface AvailableCommandsPacket extends BedrockPacket {
    @NotNull CommandData @NotNull [] commands();

    record v291(String[] enumValues, String[] postFixes, CommandEnumData[] enums, CommandData[] commands,
                CommandEnumData[] softEnums) implements AvailableCommandsPacket, Codec291 {
        public static final Interpreter<v291> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v291 interpret(@NotNull BitInput input) throws IOException {
                String[] enumValues = readArray(input, this::readString);
                String[] postFixes = readArray(input, this::readString);
                CommandEnumData[] enums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                CommandData[] commands = readArray(input, CommandData.INTERPRETER::interpret);
                CommandEnumData[] softEnums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                return new v291(enumValues, postFixes, enums, commands, softEnums);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, enumValues(), this::writeString);
            writeArray(output, postFixes(), this::writeString);
            writeArray(output, enums());
            writeArray(output, commands());
            writeArray(output, softEnums());
        }
    }

    record v340(String[] enumValues, String[] postFixes, CommandEnumData[] enums, CommandData[] commands,
                CommandEnumData[] softEnums) implements AvailableCommandsPacket, Codec340 {
        public static final Interpreter<v340> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v340 interpret(@NotNull BitInput input) throws IOException {
                String[] enumValues = readArray(input, this::readString);
                String[] postFixes = readArray(input, this::readString);
                CommandEnumData[] enums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                CommandData[] commands = readArray(input, CommandData.INTERPRETER::interpret);
                CommandEnumData[] softEnums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                return new v340(enumValues, postFixes, enums, commands, softEnums);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, enumValues(), this::writeString);
            writeArray(output, postFixes(), this::writeString);
            writeArray(output, enums());
            writeArray(output, commands());
            writeArray(output, softEnums());
        }
// TODO: Include these changes
//
//        @Override
//        protected void writeParameter(ByteBuf buffer, BedrockPacketHelper helper, CommandParamData param, List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
//            super.writeParameter(buffer, helper, param, enums, softEnums, postFixes);
//
//            byte options = 0;
//
//            if (param.getOptions() != null) {
//                for (CommandParamOption option : param.getOptions()) {
//                    options |= 1 << option.ordinal();
//                }
//            }
//            buffer.writeByte(options);
//        }
//
//        @Override
//        protected CommandParamData.Builder readParameter(ByteBuf buffer, BedrockPacketHelper helper) {
//            String parameterName = helper.readString(buffer);
//            CommandSymbolData type = CommandSymbolData.deserialize(buffer.readIntLE());
//            boolean optional = buffer.readBoolean();
//            byte options = buffer.readByte();
//
//            return new CommandParamData.Builder(parameterName, type, optional, options);
//        }
    }

    record v388(String[] enumValues, String[] postFixes, CommandEnumData[] enums, CommandData[] commands,
                CommandEnumData[] softEnums,
                CommandEnumConstraintData[] constraints) implements AvailableCommandsPacket, Codec388 {
        public static final Interpreter<v388> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v388 interpret(@NotNull BitInput input) throws IOException {
                String[] enumValues = readArray(input, this::readString);
                String[] postFixes = readArray(input, this::readString);
                CommandEnumData[] enums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                CommandData[] commands = readArray(input, CommandData.INTERPRETER::interpret);
                CommandEnumData[] softEnums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                CommandEnumConstraintData[] constraints = readArray(input, CommandEnumConstraintData.INTERPRETER::interpret);
                return new v388(enumValues, postFixes, enums, commands, softEnums, constraints);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, enumValues(), this::writeString);
            writeArray(output, postFixes(), this::writeString);
            writeArray(output, enums());
            writeArray(output, commands());
            writeArray(output, softEnums());
            writeArray(output, constraints());
        }

// TODO: incorporate these new changes
//
//
//
//
//        @Override
//        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableCommandsPacket packet) {
//            Set<String> enumValuesSet = new ObjectOpenHashSet<>();
//            Set<String> postfixSet = new ObjectOpenHashSet<>();
//            Set<CommandEnumData> enumsSet = new ObjectOpenHashSet<>();
//            Set<CommandEnumData> softEnumsSet = new ObjectOpenHashSet<>();
//
//            // Get all enum values
//            for (CommandData data : packet.getCommands()) {
//                if (data.getAliases() != null) {
//                    Collections.addAll(enumValuesSet, data.getAliases().getValues());
//                    enumsSet.add(data.getAliases());
//                }
//
//                for (CommandParamData[] overload : data.getOverloads()) {
//                    for (CommandParamData parameter : overload) {
//                        CommandEnumData commandEnumData = parameter.getEnumData();
//                        if (commandEnumData != null) {
//                            if (commandEnumData.isSoft()) {
//                                softEnumsSet.add(commandEnumData);
//                            } else {
//                                Collections.addAll(enumValuesSet, commandEnumData.getValues());
//                                enumsSet.add(commandEnumData);
//                            }
//                        }
//
//                        String postfix = parameter.getPostfix();
//                        if (postfix != null) {
//                            postfixSet.add(postfix);
//                        }
//                    }
//                }
//            }
//
//            // Add Constraint Enums
//            for(CommandEnumData enumData : packet.getConstraints().stream().map(CommandEnumConstraintData::getEnumData).collect(Collectors.toList())) {
//                if (enumData.isSoft()) {
//                    softEnumsSet.add(enumData);
//                } else {
//                    enumsSet.add(enumData);
//                }
//                enumValuesSet.addAll(Arrays.asList(enumData.getValues()));
//            }
//
//            List<String> enumValues = new ObjectArrayList<>(enumValuesSet);
//            List<String> postFixes = new ObjectArrayList<>(postfixSet);
//            List<CommandEnumData> enums = new ObjectArrayList<>(enumsSet);
//            List<CommandEnumData> softEnums = new ObjectArrayList<>(softEnumsSet);
//
//            helper.writeArray(buffer, enumValues, helper::writeString);
//            helper.writeArray(buffer, postFixes, helper::writeString);
//
//            this.writeEnums(buffer, helper, enumValues, enums);
//
//            helper.writeArray(buffer, packet.getCommands(), (buf, command) -> {
//                this.writeCommand(buffer, helper, command, enums, softEnums, postFixes);
//            });
//
//            helper.writeArray(buffer, softEnums, helper::writeCommandEnum);
//
//            // Constraints
//            helper.writeArray(buffer, packet.getConstraints(), (buf, constraint) -> {
//                helper.writeCommandEnumConstraints(buf, constraint, enums, enumValues);
//            });
//        }
//
//        @Override
//        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableCommandsPacket packet) {
//            List<String> enumValues = new ObjectArrayList<>();
//            List<String> postFixes = new ObjectArrayList<>();
//            List<CommandEnumData> enums = new ObjectArrayList<>();
//            List<CommandData.Builder> commands = new ObjectArrayList<>();
//            List<CommandEnumData> softEnums = new ObjectArrayList<>();
//
//            helper.readArray(buffer, enumValues, helper::readString);
//            helper.readArray(buffer, postFixes, helper::readString);
//
//            this.readEnums(buffer, helper, enumValues, enums);
//
//            helper.readArray(buffer, commands, this::readCommand);
//
//            helper.readArray(buffer, softEnums, buf -> helper.readCommandEnum(buffer, true));
//
//
//            // Generate command data
//            for (CommandData.Builder command : commands) {
//                int flags = command.getFlags();
//                List<CommandData.Flag> flagList = new ObjectArrayList<>();
//                for (int i = 0; i < 6; i++) {
//                    if ((flags & (1 << i)) != 0) {
//                        flagList.add(FLAGS[i]);
//                    }
//                }
//                int aliasesIndex = command.getAliases();
//                CommandEnumData aliases = aliasesIndex == -1 ? null : enums.get(aliasesIndex);
//
//                CommandParamData.Builder[][] overloadBuilders = command.getOverloads();
//                CommandParamData[][] overloads = new CommandParamData[overloadBuilders.length][];
//                for (int i = 0; i < overloadBuilders.length; i++) {
//                    overloads[i] = new CommandParamData[overloadBuilders[i].length];
//                    for (int i2 = 0; i2 < overloadBuilders[i].length; i2++) {
//                        CommandParamData.Builder param = overloadBuilders[i][i2];
//                        String name = param.getName();
//                        CommandSymbolData valueType = param.getType();
//                        boolean optional = param.isOptional();
//                        byte optionsByte = param.getOptions();
//
//                        String postfix = null;
//                        CommandEnumData enumData = null;
//                        CommandParam commandParam = null;
//                        if (valueType.isPostfix()) {
//                            postfix = postFixes.get(valueType.getValue());
//                        } else {
//                            if (valueType.isCommandEnum()) {
//                                enumData = enums.get(valueType.getValue());
//                            } else if (valueType.isSoftEnum()) {
//                                enumData = softEnums.get(valueType.getValue());
//                            } else {
//                                commandParam = helper.getCommandParam(valueType.getValue());
//                            }
//                        }
//
//                        List<CommandParamOption> options = new ObjectArrayList<>();
//                        for (int idx = 0; idx < 8; idx++) {
//                            if ((optionsByte & (1 << idx)) != 0) {
//                                options.add(OPTIONS[idx]);
//                            }
//                        }
//
//                        overloads[i][i2] = new CommandParamData(name, optional, enumData, commandParam, postfix, options);
//                    }
//                }
//
//                packet.getCommands().add(new CommandData(command.getName(), command.getDescription(),
//                        flagList, command.getPermission(), aliases, overloads));
//            }
//
//            // Constraints
//            helper.readArray(buffer, packet.getConstraints(), buf -> helper.readCommandEnumConstraints(buf, enums, enumValues));
//        }
    }

    record v448(String[] enumValues, String[] postFixes, CommandEnumData[] enums, CommandData[] commands,
                CommandEnumData[] softEnums,
                CommandEnumConstraintData[] constraints) implements AvailableCommandsPacket, Codec448 {
        public static final Interpreter<v448> INTERPRETER = new Interpreter<>() {
            @Override
            public @NotNull v448 interpret(@NotNull BitInput input) throws IOException {
                String[] enumValues = readArray(input, this::readString);
                String[] postFixes = readArray(input, this::readString);
                CommandEnumData[] enums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                CommandData[] commands = readArray(input, CommandData.INTERPRETER::interpret);
                CommandEnumData[] softEnums = readArray(input, CommandEnumData.INTERPRETER::interpret);
                CommandEnumConstraintData[] constraints = readArray(input, CommandEnumConstraintData.INTERPRETER::interpret);
                return new v448(enumValues, postFixes, enums, commands, softEnums, constraints);
            }
        };

        @Override
        public void write(@NotNull BitOutput output) throws IOException {
            writeArray(output, enumValues(), this::writeString);
            writeArray(output, postFixes(), this::writeString);
            writeArray(output, enums());
            writeArray(output, commands());
            writeArray(output, softEnums());
            writeArray(output, constraints());
        }

// TODO incorporate these changes:
//        @Override
//        protected void writeCommand(ByteBuf buffer, BedrockPacketHelper helper, CommandData commandData,
//                                    List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
//            helper.writeString(buffer, commandData.getName());
//            helper.writeString(buffer, commandData.getDescription());
//            int flags = 0;
//            for (CommandData.Flag flag : commandData.getFlags()) {
//                flags |= 1 << flag.ordinal();
//            }
//            buffer.writeShortLE(flags);
//            buffer.writeByte(commandData.getPermission());
//
//            CommandEnumData aliases = commandData.getAliases();
//            buffer.writeIntLE(enums.indexOf(aliases));
//
//            CommandParamData[][] overloads = commandData.getOverloads();
//            VarInts.writeUnsignedInt(buffer, overloads.length);
//            for (CommandParamData[] overload : overloads) {
//                VarInts.writeUnsignedInt(buffer, overload.length);
//                for (CommandParamData param : overload) {
//                    this.writeParameter(buffer, helper, param, enums, softEnums, postFixes);
//                }
//            }
//        }
//
//        @Override
//        protected CommandData.Builder readCommand(ByteBuf buffer, BedrockPacketHelper helper) {
//            String name = helper.readString(buffer);
//            String description = helper.readString(buffer);
//            int flags = buffer.readUnsignedShortLE();
//            byte permissions = buffer.readByte();
//            int aliasesIndex = buffer.readIntLE();
//
//            CommandParamData.Builder[][] overloads = new CommandParamData.Builder[VarInts.readUnsignedInt(buffer)][];
//            for (int i = 0; i < overloads.length; i++) {
//                overloads[i] = new CommandParamData.Builder[VarInts.readUnsignedInt(buffer)];
//                for (int i2 = 0; i2 < overloads[i].length; i2++) {
//                    overloads[i][i2] = readParameter(buffer, helper);
//                }
//            }
//            return new CommandData.Builder(name, description, flags, permissions, aliasesIndex, overloads);
//        }
    }
}
