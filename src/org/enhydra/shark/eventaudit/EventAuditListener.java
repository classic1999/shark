package org.enhydra.shark.eventaudit;

/**
 * TODO: document
 *
 * @author <a href="daniel.frey@xmatrix.ch">Daniel Frey</a>
 * @version 0.2
 */
public interface EventAuditListener {

    void eventAuditChanged(EventAuditEvent e);
}
