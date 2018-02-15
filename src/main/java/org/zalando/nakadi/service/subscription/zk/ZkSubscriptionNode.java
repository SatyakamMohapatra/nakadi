package org.zalando.nakadi.service.subscription.zk;

import org.zalando.nakadi.service.subscription.model.Partition;
import org.zalando.nakadi.service.subscription.model.Session;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

public final class ZkSubscriptionNode {

    private final Collection<Partition> partitions;
    private final Collection<Session> sessions;

    public ZkSubscriptionNode(final Collection<Partition> partitions, final Collection<Session> sessions) {
        this.partitions = partitions;
        this.sessions = sessions;
    }

    public Collection<Partition> getPartitions() {
        return partitions;
    }

    public Partition.State guessState(final String eventType, final String partition) {
        return getPartitionWithActiveSession(eventType, partition)
                .map(Partition::getState)
                .orElse(Partition.State.UNASSIGNED);
    }

    private Optional<Partition> getPartitionWithActiveSession(final String eventType, final String partition) {
        return partitions.stream()
                .filter(p -> p.getPartition().equals(partition) && p.getEventType().equals(eventType))
                .filter(p -> sessions.stream().anyMatch(s -> s.getId().equalsIgnoreCase(p.getSession())))
                .findAny();
    }

    @Nullable
    public String guessStream(final String eventType, final String partition) {
        return getPartitionWithActiveSession(eventType, partition)
                .map(Partition::getSession)
                .orElse(null);
    }

}
