package cn.elytra.gtnh.cutcorners.strate.impl.event.listener;

import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasDuration;
import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasLongDuration;

public class OneTickListener extends AbstractReducedTickListener {

    @Override
    protected void transform(IHasDuration i) {
        i.setDuration(1);
    }

    @Override
    protected void transform(IHasLongDuration i) {
        i.setDuration(1);
    }
}
