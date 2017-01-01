package net.hqdo.stock.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Qing
 * @since 2016/12/6.
 */
@Component
public class TaskManager {
    /**
     * second minute hour day week year
     */
    @Scheduled
    public void getPrice() {
        //todo
    }
}
