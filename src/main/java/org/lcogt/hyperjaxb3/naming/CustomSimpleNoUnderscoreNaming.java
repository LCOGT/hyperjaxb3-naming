package org.lcogt.hyperjaxb3.naming;

import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.impl.DefaultNaming;
import org.apache.commons.lang3.Validate;
import java.util.Set;
import java.util.Map.Entry;

import java.util.Map;
import java.util.TreeMap;

/**
 * Customized hyperjaxb3 naming convention to restore behavior of 0.5.x version of plugin
 */
public class CustomSimpleNoUnderscoreNaming extends DefaultNaming
{
    private final Map<String, String> keyNameMap = new TreeMap<String, String>();
    private final Map<String, String> nameKeyMap = new TreeMap<String, String>();

    @Override
    public void afterPropertiesSet()
    {
        final Set<Entry<Object, Object>> entries = getReservedNames()
                .entrySet();
        for (final Entry<Object, Object> entry : entries) {
            final Object entryKey = entry.getKey();
            if (entryKey != null) {
                final String key = entryKey.toString().toUpperCase();
                final Object entryValue = entry.getValue();
                final String value = entryValue == null
                        || "".equals(entryValue.toString().trim()) ? key + "_"
                        : entryValue.toString();
                nameKeyMap.put(key, value);
                keyNameMap.put(value, key);
            }
        }
    }

    @Override
    public String getName(Mapping context, String draftName)
    {
        Validate.notNull(draftName, "Name must not be null.");
        String intermediateName = draftName.replace('$', '_');

        final String name = intermediateName.toUpperCase();

        if (nameKeyMap.containsKey(name))
        {
            return nameKeyMap.get(name);
        }
        else if (name.length() >= getMaxIdentifierLength())
        {
            for (int i = 0;; i++)
            {
                final String suffix = Integer.toString(i);
                final String prefix = name.substring(0,
                        getMaxIdentifierLength() - suffix.length() - 1);
                final String identifier = prefix + "_" + suffix;
                if (!keyNameMap.containsKey(identifier))
                {
                    nameKeyMap.put(name, identifier);
                    keyNameMap.put(identifier, name);
                    return identifier;
                }
            }
        }
        else if (getReservedNames().containsKey(name.toUpperCase()))
        {
            return name + "_";
        }
        else
        {
            return name;
        }
    }
}
