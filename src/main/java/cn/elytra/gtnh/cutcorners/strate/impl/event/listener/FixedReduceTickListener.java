package cn.elytra.gtnh.cutcorners.strate.impl.event.listener;

import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasDuration;
import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasLongDuration;

public class FixedReduceTickListener extends AbstractReducedTickListener implements SingleUseListener {

    private final int reduceTicks;

    public FixedReduceTickListener(int reduceTicks) {
        this.reduceTicks = reduceTicks;
    }

    @Override
    protected void transform(IHasDuration i) {
        i.setDuration(Math.max(1, i.getDuration() - reduceTicks));
    }

    @Override
    protected void transform(IHasLongDuration i) {
        i.setDuration(Math.max(1, i.getDuration() - reduceTicks));
    }
}
