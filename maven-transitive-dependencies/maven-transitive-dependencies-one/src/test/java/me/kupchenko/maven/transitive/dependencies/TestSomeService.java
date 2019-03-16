package me.kupchenko.maven.transitive.dependencies;

import org.junit.Assert;
import org.junit.Test;

public class TestSomeService {
    @Test
    public void test() {
        SomeService someService = new SomeService();
        boolean result = someService.isTrue();
        Assert.assertFalse(result);
    }
}
