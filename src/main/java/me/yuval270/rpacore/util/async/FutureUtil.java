package me.yuval270.rpacore.util.async;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.CompletableFuture;

public class FutureUtil {
    private static final Plugin main = JavaPlugin.getProvidingPlugin(FutureUtil.class);

    public static <T> CompletableFuture<T> performFuture(AsyncAction<T> supplier) {
        CompletableFuture<T> future = new CompletableFuture<>();
        executeAsync(() -> {
            try {
                T value = supplier.get();
                executeMainThread(() -> future.complete(value));
            } catch (Throwable throwable) {
                future.completeExceptionally(throwable);
            }
        });
        return future;
    }

    public static void executeAsync(Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskAsynchronously(main);
    }

    public static void executeMainThread(Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(main);
    }

    public static void executeDelayedTask(long delay, Runnable runnable){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(main, delay);
    }
}
