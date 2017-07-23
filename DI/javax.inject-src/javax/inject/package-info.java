/*
 * Copyright (C) 2009 The JSR-330 Expert Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This package specifies a means for obtaining objects in such a way as to
 * maximize reusability, testability and maintainability compared to
 * traditional approaches such as constructors, factories, and service
 * locators (e.g., JNDI).&nbsp;This process, known as <i>dependency
 * injection</i>, is beneficial to most nontrivial applications.
 *
 * <p>Many types depend on other types. For example, a <tt>Stopwatch</tt> might
 * depend on a <tt>TimeSource</tt>. The types on which a type depends are
 * known as its <i>dependencies</i>. The process of finding an instance of a
 * dependency to use at run time is known as <i>resolving</i> the dependency.
 * If no such instance can be found, the dependency is said to be
 * <i>unsatisfied</i>, and the application is broken.
 *
 * <p>In the absence of dependency injection, an object can resolve its
 * dependencies in a few ways. It can invoke a constructor, hard-wiring an
 * object directly to its dependency's implementation and life cycle:
 *
 * <pre>   class Stopwatch {
 *     final TimeSource timeSource;
 *     Stopwatch () {
 *       timeSource = <b>new AtomicClock(...)</b>;
 *     }
 *     void start() { ... }
 *     long stop() { ... }
 *   }</pre>
 *
 * <p>If more flexibility is needed, the object can call out to a factory or
 * service locator:
 *
 * <pre>   class Stopwatch {
 *     final TimeSource timeSource;
 *     Stopwatch () {
 *       timeSource = <b>DefaultTimeSource.getInstance()</b>;
 *     }
 *     void start() { ... }
 *     long stop() { ... }
 *   }</pre>
 *
 * <p>In deciding between these traditional approaches to dependency
 * resolution, a programmer must make trade-offs. Constructors are more
 * concise but restrictive. Factories decouple the client and implementation
 * to some extent but require boilerplate code. Service locators decouple even
 * further but reduce compile time type safety. All three approaches inhibit
 * unit testing. For example, if the programmer uses a factory, each test
 * against code that depends on the factory will have to mock out the factory
 * and remember to clean up after itself or else risk side effects:
 *
 * <pre>   void testStopwatch() {
 *     <b>TimeSource original = DefaultTimeSource.getInstance();
 *     DefaultTimeSource.setInstance(new MockTimeSource());
 *     try {</b>
 *       // Now, we can actually test Stopwatch.
 *       Stopwatch sw = new Stopwatch();
 *       ...
 *     <b>} finally {
 *       DefaultTimeSource.setInstance(original);
 *     }</b>
 *   }</pre>
 *
 * <p>In practice, supporting this ability to mock out a factory results in
 * even more boilerplate code. Tests that mock out and clean up after multiple
 * dependencies quickly get out of hand. To make matters worse, a programmer
 * must predict accurately how much flexibility will be needed in the future
 * or else suffer the consequences. If a programmer initially elects to use a
 * constructor but later decides that more flexibility is required, the
 * programmer must replace every call to the constructor. If the programmer
 * errs on the side of caution and write factories up front, it may result in
 * a lot of unnecessary boilerplate code, adding noise, complexity, and
 * error-proneness.
 *
 * <p><i>Dependency injection</i> addresses all of these issues. Instead of
 * the programmer calling a constructor or factory, a tool called a
 * <i>dependency injector</i> passes dependencies to objects:
 *
 * <pre>   class Stopwatch {
 *     final TimeSource timeSource;
 *     <b>@Inject Stopwatch(TimeSource TimeSource)</b> {
 *       this.TimeSource = TimeSource;
 *     }
 *     void start() { ... }
 *     long stop() { ... }
 *   }</pre>
 *
 * <p>The injector further passes dependencies to other dependencies until it
 * constructs the entire object graph. For example, suppose the programmer
 * asked an injector to create a <tt>StopwatchWidget</tt> instance:
 *
 * <pre>   /** GUI for a Stopwatch &#42;/
 *   class StopwatchWidget {
 *     &#64;Inject StopwatchWidget(Stopwatch sw) { ... }
 *     ...
 *   }</pre>
 *
 * <p>The injector might:
 * <ol>
 *   <li>Find a <tt>TimeSource</tt>
 *   <li>Construct a <tt>Stopwatch</tt> with the <tt>TimeSource</tt>
 *   <li>Construct a <tt>StopwatchWidget</tt> with the <tt>Stopwatch</tt>
 * </ol>
 *
 * <p>This leaves the programmer's code clean, flexible, and relatively free
 * of dependency-related infrastructure.
 *
 * <p>In unit tests, the programmer can now construct objects directly
 * (without an injector) and pass in mock dependencies. The programmer no
 * longer needs to set up and tear down factories or service locators in each
 * test. This greatly simplifies our unit test:
 *
 * <pre>   void testStopwatch() {
 *     Stopwatch sw = new Stopwatch(new MockTimeSource());
 *     ...
 *   }</pre>
 *
 * <p>The total decrease in unit-test complexity is proportional to the
 * product of the number of unit tests and the number of dependencies.
 *
 * <p><b>This package provides dependency injection annotations that enable
 * portable classes</b>, but it leaves external dependency configuration up to
 * the injector implementation. Programmers annotate constructors, methods,
 * and fields to advertise their injectability (constructor injection is
 * demonstrated in the examples above). A dependency injector identifies a
 * class's dependencies by inspecting these annotations, and injects the
 * dependencies at run time. Moreover, the injector can verify that all
 * dependencies have been satisfied at <i>build time</i>. A service locator,
 * by contrast, cannot detect unsatisfied dependencies until run time.
 *
 * <p>Injector implementations can take many forms. An injector could
 * configure itself using XML, annotations, a DSL (domain-specific language),
 * or even plain Java code. An injector could rely on reflection or code
 * generation. An injector that uses compile-time code generation may not even
 * have its own run time representation. Other injectors may not be able to
 * generate code at all, neither at compile nor run time. A "container", for
 * some definition, can be an injector, but this package specification aims to
 * minimize restrictions on injector implementations.
 *
 * @see javax.inject.Inject @Inject
 */
package javax.inject;
