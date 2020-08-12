package lm.zeb.jmeter.config;

import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnvironmentalArguments extends ConfigTestElement {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentalArguments.class);

    public EnvironmentalArguments() {
        this.setProperty(new CollectionProperty("EnvironmentalArguments.arguments", new ArrayList()));
    }

    public CollectionProperty getArguments() {
        return (CollectionProperty)this.getProperty("EnvironmentalArguments.arguments");
    }

    public void addArgument(EnvironmentalArgument arg) {
        TestElementProperty newArg = new TestElementProperty(arg.getName(), arg);

        if (this.isRunningVersion()) {
            this.setTemporary(newArg);
        }
        this.getArguments().addItem(newArg);
    }

    public void clear() {
        super.clear();
        this.setProperty(new CollectionProperty("EnvironmentalArguments.arguments", new ArrayList()));
    }

    public PropertyIterator iterator() {
        return this.getArguments().iterator();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        PropertyIterator iter = this.getArguments().iterator();

        while(iter.hasNext()) {
            EnvironmentalArgument arg = (EnvironmentalArgument) iter.next().getObjectValue();
            String metaData = arg.getMetaData();
            str.append(arg.getName());
            if (metaData == null) {
                str.append("=");
            } else {
                str.append(metaData);
            }

            if (iter.hasNext()) {
                str.append("&");
            }
        }

        return str.toString();
    }

}
