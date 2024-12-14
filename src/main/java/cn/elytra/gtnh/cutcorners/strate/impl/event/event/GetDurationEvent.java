package cn.elytra.gtnh.cutcorners.strate.impl.event.event;

import cpw.mods.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

public abstract class GetDurationEvent extends Event implements IHasDuration {

    private int duration;

    private final int min;
    private final int max;

    public GetDurationEvent(int duration) {
        this(duration, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public GetDurationEvent(int duration, int min, int max) {
        this.duration = duration;
        this.min = min;
        this.max = max;
    }

    public int getDuration() {
        return duration;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setDuration(int duration) {
        this.duration = clamp(duration, min, max);
    }

    public static class FurnaceSmeltingTime extends GetDurationEvent {
        public FurnaceSmeltingTime(int duration) {
            super(duration);
        }
    }

    public static class Botania_SpreadPingback extends GetDurationEvent {
        public Botania_SpreadPingback(int duration) {
            super(duration);
        }
    }

    public static class GT_MaxProgressTime extends GetDurationEvent {
        @NotNull
        public final Object target;

        public GT_MaxProgressTime(@NotNull Object target, int duration) {
            super(duration);
            this.target = target;
        }
    }

    public static class TC_FurnaceSmeltingTime extends GetDurationEvent {
        public TC_FurnaceSmeltingTime(int duration) {
            super(duration);
        }
    }

    public static class TC_NodeRegenerationTime extends GetDurationEvent {
        public TC_NodeRegenerationTime(int duration) {
            super(duration);
        }
    }

}
