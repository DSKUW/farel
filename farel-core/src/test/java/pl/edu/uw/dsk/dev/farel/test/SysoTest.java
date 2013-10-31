package pl.edu.uw.dsk.dev.farel.test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

public class SysoTest {
    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void overrideProperty() {
        System.out.print("hello world");
        assertEquals("hello world", log.getLog());
    }
}
