package cn.elytra.gtnh.cutcorners.internal;

import gtPlusPlus.core.item.base.BaseItemTickable;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

public class GTPPTickableItemHook {

    /**
     * Override the return value of {@link BaseItemTickable#getMaxTicks(ItemStack)} to 1 when {@code true}.
     */
    @SuppressWarnings("JavadocReference")
    public static final ThreadLocal<Boolean> ONE_MAX_TICK = ThreadLocal.withInitial(() -> false);

    @ApiStatus.Internal
    public static void setShouldOverrideMaxTickTime(boolean value) {
        ONE_MAX_TICK.set(value);
    }

    @ApiStatus.Internal
    public static boolean shouldOverrideMaxTickTime() {
        return ONE_MAX_TICK.get();
    }

}
