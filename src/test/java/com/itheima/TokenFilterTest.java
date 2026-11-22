package com.itheima;

import com.itheima.filter.TokenFilter;
import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

class TokenFilterTest {
    private final TokenFilter filter = new TokenFilter();

    @AfterEach
    void clearContext() {
        CurrentHolder.remove();
    }

    @Test
    void rejectsMissingTokenWithoutCallingHandler() throws Exception {
        var request = new MockHttpServletRequest("GET", "/emps");
        var response = new MockHttpServletResponse();
        var called = new AtomicBoolean();
        filter.doFilter(request, response, (req, res) -> called.set(true));
        assertEquals(401, response.getStatus());
        assertFalse(called.get());
    }

    @Test
    void rejectsTokenWithoutEmployeeId() throws Exception {
        var request = new MockHttpServletRequest("GET", "/emps");
        request.addHeader("token", JwtUtils.generateToken(Map.of("username", "demo")));
        var response = new MockHttpServletResponse();
        filter.doFilter(request, response, (req, res) -> fail("Handler should not run"));
        assertEquals(401, response.getStatus());
        assertNull(CurrentHolder.getCurrentId());
    }

    @Test
    void exposesIdentityDuringRequestAndClearsAfterSuccess() throws Exception {
        var request = new MockHttpServletRequest("GET", "/emps");
        request.addHeader("token", JwtUtils.generateToken(Map.of("id", 42)));
        var called = new AtomicBoolean();
        filter.doFilter(request, new MockHttpServletResponse(), (req, res) -> {
            called.set(true);
            assertEquals(42, CurrentHolder.getCurrentId());
        });
        assertTrue(called.get());
        assertNull(CurrentHolder.getCurrentId());
    }

    @Test
    void clearsIdentityWhenDownstreamThrows() {
        var request = new MockHttpServletRequest("GET", "/emps");
        request.addHeader("token", JwtUtils.generateToken(Map.of("id", 42)));
        assertThrows(ServletException.class, () ->
            filter.doFilter(request, new MockHttpServletResponse(), (req, res) -> {
                assertEquals(42, CurrentHolder.getCurrentId());
                throw new ServletException("simulated downstream failure");
            }));
        assertNull(CurrentHolder.getCurrentId());
    }
}
