/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.security.audit.impl;

import org.jboss.logging.Logger;
import org.wildfly.security.audit.AuditProvider;

/**
 *  Audit Provider that just logs the audit event using a Logger.
 *  The flexibility of passing the audit log entries to a different
 *  sink (database, jms queue, file etc) can be controlled in the
 *  logging configuration (Eg: log4j.xml in log4j)
 *  <p>
 *  Ensure that the appender is configured properly in the
 *  global log4j.xml for log entries to go to a log, separate
 *  from the regular server logs.
 *  </p>
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @version $Revision$
 *  @since  Aug 21, 2006
 */
public class LogAuditProvider implements AuditProvider {

    private static final Logger log = Logger.getLogger("org.wildfly.security.audit");

    public void audit(String message, Exception underlyingException) {
        if ( ! log.isTraceEnabled() ) {
            return;
        }
        if (underlyingException != null) {
            log.trace(message, underlyingException);
        } else {
            log.trace(message);
        }
    }
}