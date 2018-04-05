/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017 Red Hat, Inc., and individual contributors
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

package org.wildfly.security.credential.source;

import static org.wildfly.security._private.ElytronMessages.log;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;

import org.ietf.jgss.Oid;
import org.wildfly.security.auth.SupportLevel;
import org.wildfly.security.auth.util.GSSCredentialSecurityFactory;
import org.wildfly.security.credential.Credential;
import org.wildfly.security.credential.GSSKerberosCredential;

/**
 * A credential source which acquires a credential from local kerberos ticket cache (ccache).
 * Provides {@link org.ietf.jgss.GSSCredential} visible in {@code klist} command output etc.
 *
 * Successful obtaining from cache requires set system property {@code javax.security.auth.useSubjectCredsOnly} to {@code false}.
 *
 * @author <a href="mailto:jkalina@redhat.com">Jan Kalina</a>
 */
public class LocalKerberosCredentialSource implements CredentialSource {

    private final Oid[] mechanismOids;
    private final String ccache;
    private final boolean debug;

    LocalKerberosCredentialSource(Builder builder) {
        this.mechanismOids = builder.mechanismOids;
        this.ccache = builder.ccache;
        this.debug = builder.debug;
    }

    @Override
    public SupportLevel getCredentialAcquireSupport(Class<? extends Credential> credentialType, String algorithmName, AlgorithmParameterSpec parameterSpec) throws IOException {
        return credentialType == GSSKerberosCredential.class ? SupportLevel.SUPPORTED : SupportLevel.UNSUPPORTED;
    }

    @Override
    public <C extends Credential> C getCredential(Class<C> credentialType, String algorithmName, AlgorithmParameterSpec parameterSpec) throws IOException {
        if (credentialType != GSSKerberosCredential.class) {
            log.tracef("Unable to obtain credential of type %s from LocalKerberosCredentialSource", credentialType);
            return null;
        }

        GSSCredentialSecurityFactory.Builder factory = GSSCredentialSecurityFactory.builder();

        for (Oid oid : mechanismOids) {
            factory.addMechanismOid(oid);
        }

        factory.setIsServer(false);
        factory.setDebug(debug);
        if (ccache != null) {
            factory.setDefaultCcache();
        } else {
            factory.setCcache(ccache);
        }

        GSSKerberosCredential credential;
        try {
            credential = factory.build().create();
            if (log.isTraceEnabled()) {
                log.tracef("Obtained local kerberos credential: %s", credential.getGssCredential());
            }
            return credentialType.cast(credential);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    /**
     * Construct a new builder instance.
     *
     * @return the new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for a local kerberos credential source.
     */
    public static final class Builder {

        private Oid[] mechanismOids = null;
        private String ccache = null;
        private boolean debug = false;

        /**
         * Set array of oid's indicating the mechanisms over which the credential is to be acquired.
         * Use {@code null} to request system specific default.
         *
         * @param mechanismOids array of mechanism oid's
         * @return this builder
         */
        public Builder setMechanismOids(Oid[] mechanismOids) {
            this.mechanismOids = mechanismOids;
            return this;
        }

        /**
         * Set the ccache file URL to obtain the identity.
         * If not set, default ccache of JDK will be used.
         *
         * @param url the URL to ccache file to obtain the identity.
         * @return {@code this} to allow chaining.
         */
        public Builder setCcache(String url) {
            this.ccache = url;

            return this;
        }

        /**
         * Set debug output for GSSCredential obtaining.
         *
         * @param debug whether should be debug output enabled.
         * @return {@code this} to allow chaining.
         */
        public Builder setDebug(boolean debug) {
            this.debug = debug;

            return this;
        }

        /**
         * Construct the credential source instance.
         *
         * @return the credential source
         */
        public LocalKerberosCredentialSource build() {
            return new LocalKerberosCredentialSource(this);
        }
    }
}
