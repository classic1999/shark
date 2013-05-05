package org.enhydra.shark.eventaudit;

import java.util.EventListener;
import org.enhydra.shark.api.internal.eventaudit.EventAuditPersistenceInterface;

/**
 * TODO: document
 *
 * @author <a href="daniel.frey@xmatrix.ch">Daniel Frey</a>
 * @version 0.2
 */
public class EventAuditEvent implements EventListener {

    private Object source;
    private EventAuditPersistenceInterface persister;

    public EventAuditEvent(Object source) {
        this.source = source;
    }

    public EventAuditPersistenceInterface getPersister() {
        return persister;
    }

    public void setPersister(EventAuditPersistenceInterface persister) {
        this.persister = persister;
    }
}
