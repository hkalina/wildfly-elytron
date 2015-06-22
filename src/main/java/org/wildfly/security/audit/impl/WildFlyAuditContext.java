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

import org.wildfly.security.audit.AuditContext;


//$Id$

/**
 *  Default implementation of the audit context
 *  @author Anil.Saldhana@redhat.com
 *  @since  May 13, 2007
 *  @version $Revision$
 */
public class WildFlyAuditContext extends AuditContext
{
    public WildFlyAuditContext(String securityDomain)
    {
        this.securityDomain = securityDomain;
    }
}
