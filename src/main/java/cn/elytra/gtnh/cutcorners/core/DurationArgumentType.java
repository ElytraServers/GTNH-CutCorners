package cn.elytra.gtnh.cutcorners.core;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class DurationArgumentType implements ArgumentType<Duration> {

    private static final String[] TIME_UNIT_LIST = new String[] { "ns", "us", "μs", "ms", "s", "m", "h", "d" };

    private static final DurationArgumentType INSTANCE = new DurationArgumentType();

    private static final SimpleCommandExceptionType NEGATIVE_DURATION = new SimpleCommandExceptionType(
        new LiteralMessage("Duration can't be negative"));
    private static final DynamicCommandExceptionType INVALID_TIME_UNIT = new DynamicCommandExceptionType(
        (found) -> new LiteralMessage(
            "Duration Unit can't be " + found + ", expected " + String.join(", ", TIME_UNIT_LIST)));

    private DurationArgumentType() {}

    public static DurationArgumentType duration() {
        return INSTANCE;
    }

    @Override
    public Duration parse(StringReader reader) throws CommandSyntaxException {
        int start = reader.getCursor();
        int value = reader.readInt();
        if (value < 0) {
            throw NEGATIVE_DURATION.create();
        }
        String timeUnitString = reader.readUnquotedString();
        TimeUnit timeUnit = switch (timeUnitString.toLowerCase(Locale.ENGLISH)) {
            case "ns" -> TimeUnit.NANOSECONDS;
            case "us", "μs" -> TimeUnit.MICROSECONDS;
            case "ms" -> TimeUnit.MILLISECONDS;
            case "s", "" /* the default value */ -> TimeUnit.SECONDS;
            case "m" -> TimeUnit.MINUTES;
            case "h" -> TimeUnit.HOURS;
            case "d" -> TimeUnit.DAYS;
            default -> {
                reader.setCursor(start);
                throw INVALID_TIME_UNIT.createWithContext(reader, timeUnitString);
            }
        };
        return Duration.of(value, timeUnit.toChronoUnit());
    }

    public static Duration getDuration(CommandContext<?> context, String name) {
        return context.getArgument(name, Duration.class);
    }

    @Override
    public Collection<String> getExamples() {
        return Arrays.asList(TIME_UNIT_LIST);
    }
}
