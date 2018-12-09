package it.almawave.gatawey.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class GatewayApplication extends Application{
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> sets = new HashSet<Class<?>>();
        sets.add(it.almawave.gatawey.textanalytics.Gateway.class);
        return sets;
    }
}
