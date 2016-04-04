package concurrent.net.jcip.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import concurrent.net.jcip.annotations.GuardedBy;
import concurrent.net.jcip.annotations.ThreadSafe;


/**
 * AttributeStore
 * <p/>
 * Holding a lock longer than necessary
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class AttributeStore {
    @GuardedBy("this") private final Map<String, String>
            attributes = new HashMap<String, String>();

    public synchronized boolean userLocationMatches(String name,
                                                    String regexp) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}
