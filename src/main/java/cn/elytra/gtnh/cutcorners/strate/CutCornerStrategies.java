package cn.elytra.gtnh.cutcorners.strate;

import cn.elytra.gtnh.cutcorners.strate.impl.event.CutCornersStrategyEvent;

import java.util.Optional;

/**
 * The strategies of {@link ICutCornerStrategy}.
 *
 * @see #getByName(String)
 */
public class CutCornerStrategies {

    public static Optional<ICutCornerStrategy> getByName(String name) {
        return switch (name) {
            case "NOOP" -> Optional.of(NOOP);
            case "EVENT" -> Optional.of(EVENT);
            default -> Optional.empty();
        };
    }

    public static final ICutCornerStrategy NOOP = new ICutCornerStrategy() {};
    public static final CutCornersStrategyEvent EVENT = new CutCornersStrategyEvent();

}
