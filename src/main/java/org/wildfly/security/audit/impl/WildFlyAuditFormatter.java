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

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.wildfly.security.audit.AuditEvent;
import org.wildfly.security.audit.AuditFormatter;

/**
 *  Standard formatter of AuditEvent messages. Based on toString()
 *  method of original AuditEvent from Picketbox.
 *  Formats events into format "[Level]key=value1;value2;key2=value3;"
 *
 *  @author <a href="mailto:jkalina@redhat.com">Jan Kalina</a>
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @version $Revision$
 *  @since  July 13, 2015
 */
public class WildFlyAuditFormatter implements AuditFormatter {

    public String format(AuditEvent event) {
        StringBuilder sbu  = new StringBuilder();
        sbu.append("[").append(event.getAuditLevel()).append("]");
        sbu.append(dissectContextMap(event.getContextMap()));
        return sbu.toString();
    }

    /**
     * Provide additional information about the entities in
     * the context map
     * @return
     */
    @SuppressWarnings("unchecked")
    private String dissectContextMap(Map<String,Object> contextMap) {
       StringBuilder sbu  = new StringBuilder();
       if(contextMap != null) {
          for(String key:contextMap.keySet()) {
             sbu.append(key).append("=");
             Object obj = contextMap.get(key);
             if(obj instanceof Object[]) {
                Object[] arr = (Object[])obj;
                obj = Arrays.asList(arr);
             }
             if(obj instanceof Collection) {
                Collection<Object> coll = (Collection<Object>)obj;
                for(Object o:coll) {
                   sbu.append(o).append(";");
                }
             } else {
                sbu.append(obj).append(";");
             }
          }
       }
       return sbu.toString();
    }

}
