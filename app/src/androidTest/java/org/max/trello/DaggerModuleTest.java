package org.max.trello;


import org.max.trello.base.AbstractTest;
import org.max.trello.injecting.DaggerInjectingModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dagger.Provides;

public class DaggerModuleTest extends AbstractTest {


    private DaggerInjectingModule injectingModule;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TrelloApplication application = (TrelloApplication) this.getSystemContext().getApplicationContext();
        injectingModule = application.getInjectingModule();
    }

    public void testNoNullIsProvided() throws InvocationTargetException, IllegalAccessException {
        for (Method method : DaggerInjectingModule.class.getDeclaredMethods()) {
            if(method.getAnnotation(Provides.class) != null) {
                assertNotNull(method.invoke(injectingModule, null));
            }
        }
    }
}
