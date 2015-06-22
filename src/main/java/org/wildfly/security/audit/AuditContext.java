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

import java.util.ArrayList;
import java.util.List;

//$Id$

/**
 *  Context for Audit Purposes that manages a set of providers
 *  @see AuditProvider
 *  @author Anil.Saldhana@redhat.com
 *  @since  May 13, 2007
 *  @version $Revision$
 */
public abstract class AuditContext {

    protected String securityDomain = null;

    protected List<AuditProvider> providerList = new ArrayList<AuditProvider>();

    public void audit(AuditEvent ae) {
        int len = providerList.size();

        for(int i = 0; i < len; i++) {
            AuditProvider ap = (AuditProvider) providerList.get(i);
            ap.audit(ae);
        }
    }

    public void addProvider(AuditProvider ap) {
        providerList.add(ap);
    }

    public void addProviders(List<AuditProvider> list) {
        providerList.addAll(list);
    }

    public List<AuditProvider> getProviders() {
        return providerList;
    }

    public void replaceProviders(List<AuditProvider> list) {
        providerList.clear();
        providerList = list;
    }
}
