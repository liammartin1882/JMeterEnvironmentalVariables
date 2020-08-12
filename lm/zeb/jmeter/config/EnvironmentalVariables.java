package lm.zeb.jmeter.config;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentalVariables extends AbstractTestElement implements LoopIterationListener {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentalVariables.class);

    public EnvironmentalVariables() {

    }

    public EnvironmentalVariables(String name) { this.setName(name);}

    public EnvironmentalArguments getArguments() {
        return this.getEnvironmentalVariables();
    }


    public void setEnvironmentalVariables(EnvironmentalArguments vars) {
        this.setProperty(new TestElementProperty("EnvironmentalVariables.element", vars));
    }

    public JMeterProperty getEnvironmentalVariablesAsProperty() {
        return this.getProperty("EnvironmentalVariables.element");
    }

    private EnvironmentalArguments getEnvironmentalVariables() {
        EnvironmentalArguments args = (EnvironmentalArguments) this.getProperty("EnvironmentalVariables.element").getObjectValue();
        if (args == null) {
            args = new EnvironmentalArguments();
            this.setEnvironmentalVariables(args);
        }

        return args;
    }

    public void setName(String name) {
        this.setProperty("TestElement.name", name);
    }

    public String getName() {
        return this.getPropertyAsString("TestElement.name");
    }

    public void setComment(String comment) {
        this.setProperty(new StringProperty("EnvironmentalVariables.comments", comment));
    }

    public String getComment() {
        return this.getProperty("EnvironmentalVariables.comments").getStringValue();
    }

    @Override
    public void iterationStart(LoopIterationEvent iterEvent) {
        JMeterProperty jMeterProperty = getEnvironmentalVariablesAsProperty();
        PropertyIterator iterator = ((EnvironmentalArguments)jMeterProperty.getObjectValue()).iterator();
        while(iterator.hasNext()){
            JMeterProperty jMeterNextProperty = iterator.next();
            EnvironmentalArgument arg = (EnvironmentalArgument)jMeterNextProperty.getObjectValue();
            if(arg.getSelected()) {
                JMeterVariables variables = JMeterContextService.getContext().getVariables();
                variables.put("Protocol", arg.getProtocol());
                variables.put("Url", arg.getUrl());
                variables.put("Port", arg.getPort());
            }
        }
    }
}
