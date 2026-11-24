package cn.elytra.gtnh.cutcorners.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizon.gtnhlib.util.CoordinatePacker;

import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import net.minecraft.tileentity.TileEntity;

public final class WorldTickingManager {

    // packed pos -> extra tick count per regular tick
    private static final Long2IntMap ACCELERATED_POS = new Long2IntOpenHashMap();
    private static final Timer THE_TIMER = new Timer("WorldTickingManager-Timer");

    public static void register(int x, int y, int z, int extraTickCount) {
        long pos = CoordinatePacker.pack(x, y, z);
        ACCELERATED_POS.put(pos, extraTickCount);
    }

    public static void unregister(int x, int y, int z) {
        long pos = CoordinatePacker.pack(x, y, z);
        ACCELERATED_POS.remove(pos);
    }

    public static void registerDuration(int x, int y, int z, int extraTickCount, long duration, TimeUnit timeUnit) {
        long pos = CoordinatePacker.pack(x, y, z);
        ACCELERATED_POS.put(pos, extraTickCount);
        long delay = timeUnit.toMillis(duration);
        THE_TIMER.schedule(new CancelTask(pos), delay);
    }

    @ApiStatus.Internal
    public static void onBlockPosTick(int x, int y, int z, TileEntity tile) {
        long pos = CoordinatePacker.pack(x, y, z);
        int extraTickCount = ACCELERATED_POS.get(pos);
        for (int n = 0; n < extraTickCount; n++) {
            if (
                tile.isInvalid() || !tile.hasWorldObj()
                    || !tile.getWorldObj().blockExists(tile.xCoord, tile.yCoord, tile.zCoord)
            ) {
                // something has gone wrong; stop ticking.
                break;
            }
            tile.updateEntity();
        }
    }

    private static class CancelTask extends TimerTask {

        private final long pos;

        private CancelTask(long pos) {
            this.pos = pos;
        }

        @Override
        public void run() {
            ACCELERATED_POS.remove(pos);
        }
    }

}
