package org.wildfly.security.audit;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.LinkedList;

import org.junit.Test;
import org.wildfly.security.audit.impl.SimpleIntegrityAuditFilter;
import org.wildfly.security.util.ByteIterator;

public class SimpleIntegrityAuditFilterTest {

    @Test
    public void testHashCorrectness() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        StringAuditProvider ap = new StringAuditProvider();
        AuditProvider filter = new SimpleIntegrityAuditFilter(md, ap);

        filter.audit("hello", null);
        filter.audit("world!", null);

        assertEquals(2, ap.list.size());

        String message1 = ap.list.get(0);
        assertEquals("hello", message1.substring(0, message1.length()-28));
        byte[] expectedDigestBytes = digest(md, "hello", null);
        String expectedDigestString = ByteIterator.ofBytes(expectedDigestBytes).base64Encode().drainToString();
        assertEquals(expectedDigestString, message1.substring(message1.length()-28));

        String message2 = ap.list.get(1);
        assertEquals("world!", message2.substring(0, message2.length()-28));
        expectedDigestBytes = digest(md, "world!", expectedDigestBytes);
        expectedDigestString = ByteIterator.ofBytes(expectedDigestBytes).base64Encode().drainToString();
        assertEquals(expectedDigestString, message2.substring(message2.length()-28));
    }

    private byte[] digest(MessageDigest digest, String message, byte[] previousDigest){
        digest.reset();
        digest.update(message.getBytes(StandardCharsets.UTF_8));
        if (previousDigest != null) {
            digest.update(previousDigest);
        }
        return digest.digest();
    }

    /* Simple testing AuditProvider */
    class StringAuditProvider implements AuditProvider {
        public LinkedList<String> list = new LinkedList<String>();

        public void audit(String message, Exception exception) {
            list.add(message);
        }
    }

}
