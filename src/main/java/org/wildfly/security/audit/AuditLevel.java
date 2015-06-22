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
 *  Define the Audit Levels of Severity
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @version $Revision$
 *  @since  Aug 21, 2006
 */
public interface AuditLevel {
    /** Denotes situations where there has been a server exception */
    String ERROR = "Error";

    /** Denotes situations when there has been a failed attempt */
    String FAILURE = "Failure";

    /** Denotes situations when there has been a successful attempt */
    String SUCCESS = "Success";

    /** Just some info being passed into the audit logs */
    String INFO = "Info";
}
