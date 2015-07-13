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
 *  Formatter of AuditEvents - converts AuditEvent to String
 *  @author <a href="mailto:jkalina@redhat.com">Jan Kalina</a>
 *  @version $Revision$
 *  @since  July 13, 2015
 */
public interface AuditFormatter {
    /**
     * Converts AuditEvent to String
     * @param event audit event that should be converted
     * @return String representation of input audit event
     * @see AuditEvent
     */
    String format(AuditEvent event);
}
