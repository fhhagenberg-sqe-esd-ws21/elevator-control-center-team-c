package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SystemInfoTest {
    @Test
    public void testJavaVersion() {
        assertTrue(SystemInfo.javaVersion().startsWith("15"), "Actual java version: " + SystemInfo.javaVersion());
    }

    @Test
    public void testJavafxVersion() {
        assertEquals("15", SystemInfo.javafxVersion());
    }
}