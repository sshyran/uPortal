package org.jasig.portal.events.handlers;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import org.jasig.portal.events.PortalEvent;
import org.jasig.portal.security.IPerson;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class UserFilteringPortalEventHandlerTest {
    @Test
    public void testIgnoredUserPatterns() {
        final UserFilteringPortalEventHandler<TestPortalEvent> userFilteringPortalEventHandler = new UserFilteringPortalEventHandler<TestPortalEvent>();
        
        userFilteringPortalEventHandler.setIgnoredUserNamePatterns(ImmutableList.of(Pattern.compile("fo.*")));
        
        final IPerson userFoo = mock(IPerson.class);
        when(userFoo.getUserName()).thenReturn("foo");
        final TestPortalEvent fooEvent = new TestPortalEvent(new TestPortalEvent.TestPortalEventBuilder(this, "server", "sessionId", userFoo));
        
        boolean supports = userFilteringPortalEventHandler.supports(fooEvent);
        assertFalse(supports);
        
        final IPerson userBar = mock(IPerson.class);
        when(userBar.getUserName()).thenReturn("bar");
        final TestPortalEvent barEvent = new TestPortalEvent(new TestPortalEvent.TestPortalEventBuilder(this, "server", "sessionId", userBar));
        
        supports = userFilteringPortalEventHandler.supports(barEvent);
        assertTrue(supports);
    }
    
    @Test
    public void testIgnoredUsers() {
        final UserFilteringPortalEventHandler<TestPortalEvent> userFilteringPortalEventHandler = new UserFilteringPortalEventHandler<TestPortalEvent>();
        
        userFilteringPortalEventHandler.setIgnoredUserNames(ImmutableList.of("foo"));
        
        final IPerson userFoo = mock(IPerson.class);
        when(userFoo.getUserName()).thenReturn("foo");
        final TestPortalEvent fooEvent = new TestPortalEvent(new TestPortalEvent.TestPortalEventBuilder(this, "server", "sessionId", userFoo));
        
        boolean supports = userFilteringPortalEventHandler.supports(fooEvent);
        assertFalse(supports);
        
        final IPerson userBar = mock(IPerson.class);
        when(userBar.getUserName()).thenReturn("bar");
        final TestPortalEvent barEvent = new TestPortalEvent(new TestPortalEvent.TestPortalEventBuilder(this, "server", "sessionId", userBar));
        
        supports = userFilteringPortalEventHandler.supports(barEvent);
        assertTrue(supports);
    }
    
    @Test
    public void testSupportedUserNames() {
        final UserFilteringPortalEventHandler<TestPortalEvent> userFilteringPortalEventHandler = new UserFilteringPortalEventHandler<TestPortalEvent>();
        
        userFilteringPortalEventHandler.setSupportedUserNames(ImmutableList.of("foobar"));
        userFilteringPortalEventHandler.setIgnoredUserNamePatterns(ImmutableList.of(Pattern.compile("fo.*")));
        
        final IPerson userFoo = mock(IPerson.class);
        when(userFoo.getUserName()).thenReturn("foo");
        final TestPortalEvent fooEvent = new TestPortalEvent(new TestPortalEvent.TestPortalEventBuilder(this, "server", "sessionId", userFoo));
        
        boolean supports = userFilteringPortalEventHandler.supports(fooEvent);
        assertFalse(supports);
        
        final IPerson userBar = mock(IPerson.class);
        when(userBar.getUserName()).thenReturn("foobar");
        final TestPortalEvent barEvent = new TestPortalEvent(new TestPortalEvent.TestPortalEventBuilder(this, "server", "sessionId", userBar));
        
        supports = userFilteringPortalEventHandler.supports(barEvent);
        assertTrue(supports);
    }
    
    private static class TestPortalEvent extends PortalEvent {

        public TestPortalEvent(PortalEventBuilder eventBuilder) {
            super(eventBuilder);
            // TODO Auto-generated constructor stub
        }

        public static class TestPortalEventBuilder extends PortalEventBuilder {
            public TestPortalEventBuilder(Object source, String serverName, String eventSessionId, IPerson person) {
                super(source, serverName, eventSessionId, person);
            }
        }
    }
}