package org.wildfly.security.audit;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

import org.jboss.logmanager.ExtLogRecord;
import org.junit.Test;
import org.wildfly.security.audit.integrity.TcpSyslogHandler;
import org.wildfly.security.audit.integrity.TcpSyslogHandler.Facility;
import org.wildfly.security.audit.integrity.TcpSyslogHandler.SyslogType;

public class AuditTest {
    
    @Test
    public void testEventToString(){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("key", "value");
        HashSet<String> set = new HashSet<String>(); 
        set.add("aa");
        set.add("bb");
        set.add("cc");
        map.put("arr", set);
        AuditEvent event = new AuditEvent(AuditLevel.INFO, map);
        System.out.println(event.toString());
    }
    
    @Test
    public void test() throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("key", "value");
        HashSet<String> set = new HashSet<String>(); 
        set.add("aa");
        set.add("bb");
        set.add("cc");
        map.put("arr", set);
        AuditEvent event = new AuditEvent(AuditLevel.INFO, map);
        
        TcpSyslogHandler syslogHandler = new TcpSyslogHandler(InetAddress.getByName("localhost"), 10514, Facility.LOG_AUDIT, SyslogType.RFC3164, "ElytronTest");
        syslogHandler.doPublish(new ExtLogRecord(Level.INFO, String.valueOf(event), this.getClass().getName()));
        syslogHandler.close();
    }
    
}
