//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lm.zeb.jmeter.config;

import java.io.Serializable;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.xpath.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentalArgument extends AbstractTestElement implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentalArgument.class);
    public static final String ARG_NAME = "EnvironmentArgument.name";
    public static final String PROTOCOL = "EnvironmentArgument.protocol";
    public static final String URL = "EnvironmentArgument.url";
    public static final String PORT = "EnvironmentArgument.port";
    public static final String SELECTED = "EnvironmentArgument.selected";
    public static final String METADATA = "EnvironmentArgument.metadata";

    public EnvironmentalArgument() {
        this((String)null, (String)null, (String)null, (String)null, (Boolean)null, (String)null);
    }

    public EnvironmentalArgument(String name, String value) {
        this(name, value, (String)null, (String)null, (Boolean)null, (String)null);
    }

    public EnvironmentalArgument(String name, String value, String metadata) {
        this(name, value, metadata, (String)null, (Boolean)null, (String)null);
    }

    public EnvironmentalArgument(String name, String protocol, String url, String port, Boolean selected, String metadata) {
        if (name != null) {
            this.setProperty(new StringProperty("EnvironmentArgument.name", name));
        }

        if (protocol != null) {
            this.setProperty(new StringProperty("EnvironmentArgument.protocol", protocol));
        }

        if (url != null) {
            this.setProperty(new StringProperty("EnvironmentArgument.url", url));
        }

        if (port != null) {
            this.setProperty(new StringProperty("EnvironmentArgument.port", port));
        }

        if (selected != null) {
            this.setProperty(new BooleanProperty("EnvironmentArgument.selected", selected));
        }

        if (metadata != null) {
            this.setProperty(new StringProperty("EnvironmentArgument.metadata", metadata));
        }

    }

    public void setName(String newName) {
        this.setProperty(new StringProperty("EnvironmentArgument.name", newName));
    }

    public String getName() {
        return this.getPropertyAsString("EnvironmentArgument.name");
    }

    public void setProtocol(String newProtocol) { this.setProperty(new StringProperty("EnvironmentArgument.protocol", newProtocol));}

    public String getProtocol() { return this.getPropertyAsString("EnvironmentArgument.protocol"); }

    public void setUrl(String newUrl) { this.setProperty(new StringProperty("EnvironmentArgument.url", newUrl));}

    public String getUrl() { return this.getPropertyAsString("EnvironmentArgument.url"); }

    public void setPort(String newPort) { this.setProperty(new StringProperty("EnvironmentArgument.port", newPort));}

    public String getPort() { return this.getPropertyAsString("EnvironmentArgument.port"); }

    public void setSelected(Boolean newSelcted) { this.setProperty(new StringProperty("EnvironmentArgument.selected", String.valueOf(newSelcted)));}

    public Boolean getSelected() { return Boolean.parseBoolean(this.getPropertyAsString("EnvironmentArgument.selected")); }

    public void setMetaData(String newMetaData) {
        this.setProperty(new StringProperty("EnvironmentArgument.metadata", newMetaData));
    }

    public String getMetaData() {
        return this.getPropertyAsString("EnvironmentArgument.metadata");
    }

    public String toString() {
        String desc = this.getUrl();
        return "".equals(desc) ? this.getName() + this.getMetaData() + this.getProtocol() : this.getName() + this.getMetaData() + this.getProtocol() + " //" + this.getUrl();
    }
}
