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
package org.wildfly.security.audit.integrity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.wildfly.security.audit.AuditEvent;
import org.wildfly.security.audit.AuditProvider;
import org.wildfly.security.util.ByteIterator;

public class IntegrityAuditFilter implements AuditProvider {

    private final AuditProvider delegating;
    private final MessageDigest digestor;

    public IntegrityAuditFilter(String algorithm, AuditProvider delegatingProvider) throws NoSuchAlgorithmException {
        delegating = delegatingProvider;
        digestor = MessageDigest.getInstance(algorithm);
    }

    public void audit(AuditEvent auditEvent) {
        String input = auditEvent.toString();
        byte[] digestBytes = digest(input.getBytes(StandardCharsets.UTF_8));

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("content", input);
        map.put("signature", ByteIterator.ofBytes(digestBytes).base64Encode().drainToString());

        delegating.audit(new AuditEvent(auditEvent.getAuditLevel(), map, auditEvent.getUnderlyingException()));
    }


    private byte[] previousDigest = null;

    byte[] digest(byte[] input) {
        digestor.reset();
        digestor.update(input);
        if (previousDigest != null) {
            digestor.update(previousDigest);
        }
        previousDigest = digestor.digest();
        return previousDigest;
    }

}
