package cn.elytra.gtnh.cutcorners.strate.impl.event.listener;

import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasDuration;
import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasLongDuration;

public class RatioReduceTickListener extends AbstractReducedTickListener implements SingleUseListener {

    private final float ratio;

    public RatioReduceTickListener(float ratio) {
        this.ratio = ratio;
    }

    @Override
    protected void transform(IHasDuration i) {
        i.setDuration(Math.max(1, (int) (i.getDuration() * ratio)));
    }

    @Override
    protected void transform(IHasLongDuration i) {
        i.setDuration(Math.max(1, (long) (i.getDuration() * ratio)));
    }
}
