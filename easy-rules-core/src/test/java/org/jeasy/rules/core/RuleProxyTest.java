/**
 * The MIT License
 *
 *  Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.rules.core;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.AnnotatedRuleWithMetaRuleAnnotation;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.api.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RuleProxyTest {

    @Test
    public void proxyingHappensEvenWhenRuleIsAnnotatedWithMetaRuleAnnotation() {
        // Given
        AnnotatedRuleWithMetaRuleAnnotation rule = new AnnotatedRuleWithMetaRuleAnnotation();

        // When
        Rule proxy = RuleProxy.asRule(rule);

        // Then
        assertNotNull(proxy.getDescription());
        assertNotNull(proxy.getName());
    }

    @Test
    public void asRuleForObjectThatImplementsRule() {
        Object rule = new BasicRule();
        Rule proxy = RuleProxy.asRule(rule);

        assertNotNull(proxy.getDescription());
        assertNotNull(proxy.getName());
    }

    @Test
    public void asRuleForObjectThatExtendsBasicRule() {
        Object rule = new CompositeRule();
        Rule proxy = RuleProxy.asRule(rule);

        assertNotNull(proxy.getDescription());
        assertNotNull(proxy.getName());
    }

    @Test
    public void asRuleForObjectThatHasProxied() {
        Object rule = new DummyRule();
        Rule proxy1 = RuleProxy.asRule(rule);
        Rule proxy2 = RuleProxy.asRule(proxy1);

        assertEquals(proxy1.getDescription(), proxy2.getDescription());
        assertEquals(proxy1.getName(), proxy2.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void asRuleForPojo() {
        Object rule = new Object();
        Rule proxy = RuleProxy.asRule(rule);
    }

    @org.jeasy.rules.annotation.Rule
    class DummyRule {
        @Condition
        public boolean when() { return true; }

        @Action
        public void then() { }
    }
}

