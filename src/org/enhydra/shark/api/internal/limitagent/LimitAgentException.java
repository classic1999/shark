package org.enhydra.shark.api.internal.limitagent;

import org.enhydra.shark.api.RootException;

/**
 *
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @since      Mar 24, 2004
 */
public class LimitAgentException extends RootException {

    public LimitAgentException() {
        super();
    }

    public LimitAgentException(String message) {
        super(message);
    }

    public LimitAgentException(Throwable t) {
        super(t);
    }

    public LimitAgentException(String message, Throwable t) {
        super(message, t);
    }
}

