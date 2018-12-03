package it.almawave.gatawey.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class GatewayApplication extends Application{
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> sets = new HashSet<Class<?>>();
        sets.add(it.almawave.gatawey.textanalytics.Gateway.class);
//        sets.add(it.almawave.iride.es.Tester.class);
//        sets.add(it.almawave.iride.km.KMWrapperAPI.class);
//        sets.add(it.almawave.iride.es.ESWrapperAPI.class);
//        sets.add(it.almawave.iit.utility.WebAppService.class);
        return sets;
    }
}
