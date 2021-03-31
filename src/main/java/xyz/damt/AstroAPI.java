package xyz.damt;

import lombok.Getter;
import xyz.damt.queue.Queue;

public class AstroAPI {

    @Getter private static AstroAPI instance;

    public AstroAPI() {
        instance = this;
    }

    public Queue getQueueByName(String name) {
        return Queue.getQueueByName(name);
    }

}
