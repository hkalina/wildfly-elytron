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
package org.wildfly.security.audit;

/**
 *  An interface that defines the Security Audit Service
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @since  Jun 18, 2015
 *  @version $Revision$
 */
public interface AuditManager {
    /**
     * Audit the information available in the audit event
     * @param event the Audit Event
     * @see AuditEvent
     */
    void audit(AuditEvent event);

    /**
     * Get the security domain from which the audit manager is from. Every
     * audit manager belongs to security domain of WildFly. May be null in which
     * case the security manager belongs to the logical default domain.
     */
    String getSecurityDomain();
}