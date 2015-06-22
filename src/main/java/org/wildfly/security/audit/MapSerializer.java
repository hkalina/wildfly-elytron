package org.wildfly.security.audit;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapSerializer {
    
    @SuppressWarnings("unchecked")
    public static String serializeMap(StringBuilder s, Map<String, Object> map) {
        s.append('{');
        Iterator<Entry<String, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, Object> entry = entries.next();
            appendEscapedString(s, entry.getKey());
            s.append(':');
            Object obj = entry.getValue();
            if (obj instanceof Collection) {
                serializeCollection(s, (Collection<Object>)obj);
            } else {
                appendEscapedString(s, String.valueOf(obj));
            }
            if (entries.hasNext()) {
                s.append(',');
            }
        }
        s.append('}');
        return null;
    }
    
    private static String serializeCollection(StringBuilder s, Collection<Object> coll) {
        s.append('[');
        Iterator<Object> entries = coll.iterator();
        while (entries.hasNext()) {
            appendEscapedString(s, String.valueOf(entries.next()));
        }
        s.append(']');
        return null;
    }
    
    private static void appendEscapedString(StringBuilder s, String input) {
        s.append('"');
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            // ABNF VCHAR values and spaces + JSON string escaping
            if (c < 32 || c > 126 || c == '\\' || c == '\"') {
                if(c == '\\' || c == '\"') {
                    s.append('\\').append(c);
                }else{
                    s.append('\\').append('u').append(String.format("%04d", (int)c));
                }
            } else {
                s.append(c);
            }
        }
        s.append('"');
    }
    
}
