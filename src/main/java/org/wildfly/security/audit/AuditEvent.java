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

import java.util.HashMap;
import java.util.Map;

/**
 *  Holder of audit information
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @version $Revision$
 *  @since  Aug 21, 2006
 */
public class AuditEvent {

    private String auditLevel = AuditLevel.INFO;

    private Map<String,Object> contextMap = new HashMap<String,Object>();

    private Exception underlyingException = null;

    public AuditEvent(String level) {
        this.auditLevel = level;
    }

    public AuditEvent(String level, Map<String,Object> map) {
        this(level);
        this.contextMap = map;
    }

    public AuditEvent(String level, Map<String,Object> map, Exception ex) {
        this(level, map);
        this.underlyingException = ex;
    }

    /**
     * Return the Audit Level
     * @return
     */
    public String getAuditLevel() {
        return auditLevel;
    }

    /**
     * Get the Contextual Map
     * @return Map that is final
     */
    public Map<String,Object> getContextMap() {
        return contextMap;
    }

    /**
     * Set a non-modifiable Context Map
     * @param contextMap Map that is final
     */
    public void setContextMap(final Map<String,Object> contextMap) {
        this.contextMap = contextMap;
    }

    /**
     * Get the Exception part of the audit
     * @return
     */
    public Exception getUnderlyingException() {
        return underlyingException;
    }

    /**
     * Set the exception on which an audit is happening
     * @param underlyingException
     */
    public void setUnderlyingException(Exception underlyingException) {
        this.underlyingException = underlyingException;
    }

}