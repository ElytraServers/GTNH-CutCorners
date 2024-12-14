package cn.elytra.gtnh.cutcorners.strate.impl.event;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.config.Config;
import cn.elytra.gtnh.cutcorners.strate.impl.event.listener.FixedReduceTickListener;
import cn.elytra.gtnh.cutcorners.strate.impl.event.listener.OneTickListener;
import cn.elytra.gtnh.cutcorners.strate.impl.event.listener.RatioReduceTickListener;
import cn.elytra.gtnh.cutcorners.strate.impl.event.listener.TierLVListener;

import java.util.Optional;

public class CutCornerBuiltinEventHandlers {

    public static final OneTickListener ONE_TICK = new OneTickListener();
    public static final TierLVListener GT_TIER_LV = new TierLVListener();

    public static Optional<Object> getHandler(String name) {
        return switch (name) {
            case "ONE_TICK" -> Optional.of(ONE_TICK);
            case "GT_TIER_LV" -> Optional.of(GT_TIER_LV);
            case "RATIO_TICK" -> makeRatioReduceTickListener();
            case "FIXED_TICK" -> makeFixedReduceTickListener();
            default -> Optional.empty();
        };
    }

    private static Optional<Object> makeRatioReduceTickListener() {
        float d = Config.getRatioTickValue();
        CutCorners.LOG.info("Initializing RatioTickListener with ratio {}", d);
        return Optional.of(new RatioReduceTickListener(d));
    }

    private static Optional<Object> makeFixedReduceTickListener() {
        int d = Config.getFixedReduceTickValue();
        CutCorners.LOG.info("Initializing FixedReduceTickListener with reducing {}", d);
        return Optional.of(new FixedReduceTickListener(d));
    }

}
