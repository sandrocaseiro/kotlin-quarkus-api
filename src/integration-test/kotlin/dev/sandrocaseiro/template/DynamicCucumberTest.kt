package dev.sandrocaseiro.template


//import io.quarkus.test.junit.QuarkusTest;
//import java.lang.reflect.Field;
//import java.net.URI;
//import java.time.Clock;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicReference;
//
//import javax.enterprise.inject.Instance;
//import javax.enterprise.inject.spi.CDI;
//
//import io.cucumber.core.backend.ObjectFactory;
//import io.cucumber.core.eventbus.EventBus;
//import io.cucumber.core.feature.FeatureParser;
//import io.cucumber.core.options.CommandlineOptionsParser;
//import io.cucumber.core.options.RuntimeOptions;
//import io.cucumber.core.options.RuntimeOptionsBuilder;
//import io.cucumber.core.plugin.PluginFactory;
//import io.cucumber.core.plugin.Plugins;
//import io.cucumber.core.plugin.PrettyFormatter;
//import io.cucumber.core.runner.Runner;
//import io.cucumber.core.runtime.CucumberExecutionContext;
//import io.cucumber.core.runtime.ExitStatus;
//import io.cucumber.core.runtime.FeaturePathFeatureSupplier;
//import io.cucumber.core.runtime.FeatureSupplier;
//import io.cucumber.core.runtime.ObjectFactorySupplier;
//import io.cucumber.core.runtime.ScanningTypeRegistryConfigurerSupplier;
//import io.cucumber.core.runtime.TimeServiceEventBus;
//import io.cucumber.core.runtime.TypeRegistryConfigurerSupplier;
////import io.cucumber.java.JavaBackendProviderService;
//import io.cucumber.plugin.event.EventHandler;
//import io.cucumber.plugin.event.PickleStepTestStep;
//import io.cucumber.plugin.event.Status;
//import io.cucumber.plugin.event.TestStepFinished;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DynamicContainer;
//import org.junit.jupiter.api.DynamicNode;
//import org.junit.jupiter.api.DynamicTest;
//import org.junit.jupiter.api.TestFactory;
//
//@QuarkusTest
//class DynamicCucumberTest {
//    private val mainArgs = arrayOf<String>()
//
//    @TestFactory
//    fun getTests(): List<DynamicNode>? {
//        try {
//            // We run in a different ClassLoader then "main", so we need to grab any cli arguments from the SystemClassLoader
//            val aClass = ClassLoader.getSystemClassLoader().loadClass(this.javaClass.name)
//            val aClassDeclaredField: Field = aClass.getDeclaredField("mainArgs")
//            aClassDeclaredField.setAccessible(true)
//            DynamicCucumberTest.mainArgs = aClassDeclaredField.get(aClass) as Array<String?>
//        } catch (e: NoSuchFieldException) {
//            e.printStackTrace()
//        } catch (e: ClassNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IllegalAccessException) {
//            e.printStackTrace()
//        }
//        val eventBus: EventBus = TimeServiceEventBus(Clock.systemUTC(), UUID::randomUUID)
//        val parser = FeatureParser(eventBus::generateId)
//        val commandlineOptionsParser = CommandlineOptionsParser(System.out).parse(*mainArgs)
//        val runtimeOptionsBuilder = RuntimeOptionsBuilder()
//        runtimeOptionsBuilder.addDefaultFeaturePathIfAbsent()
//        runtimeOptionsBuilder.addDefaultGlueIfAbsent()
//        runtimeOptionsBuilder.addDefaultFormatterIfAbsent()
//        runtimeOptionsBuilder.addDefaultSummaryPrinterIfAbsent()
//        runtimeOptionsBuilder.addGlue(
//            URI.create(
//                "classpath:/" + Steps::class.java.getPackage().getName().replace(".", "/")
//            )
//        )
//        val runtimeOptions: RuntimeOptions = runtimeOptionsBuilder.build(commandlineOptionsParser.build())
//        val featureSupplier: FeatureSupplier = FeaturePathFeatureSupplier(
//            {
//                Thread.currentThread().contextClassLoader
//            }, runtimeOptions,
//            parser
//        )
//        val plugins = Plugins(PluginFactory(), runtimeOptions)
//        plugins.addPlugin(PrettyFormatter(System.out))
//        val exitStatus = ExitStatus(runtimeOptions)
//        plugins.addPlugin(exitStatus)
//        if (runtimeOptions.isMultiThreaded()) {
//            plugins.setSerialEventBusOnEventListenerPlugins(eventBus)
//        } else {
//            plugins.setEventBusOnEventListenerPlugins(eventBus)
//        }
//        val objectFactory: ObjectFactory = CdiObjectFactory()
//        val objectFactorySupplier = ObjectFactorySupplier { objectFactory }
//        val typeRegistryConfigurerSupplier: TypeRegistryConfigurerSupplier = ScanningTypeRegistryConfigurerSupplier(
//            {
//                Thread.currentThread()
//                    .contextClassLoader
//            },
//            runtimeOptions
//        )
//        val runner = Runner(
//            eventBus,
//            Collections.singleton(JavaBackendProviderService().create(
//                objectFactorySupplier.get(),
//                objectFactorySupplier.get()
//            ) {
//                Thread.currentThread()
//                    .contextClassLoader
//            }),
//            objectFactorySupplier.get(),
//            typeRegistryConfigurerSupplier.get(),
//            runtimeOptions
//        )
//        val context = CucumberExecutionContext(eventBus, exitStatus) { runner }
//        val features: MutableList<DynamicNode> = LinkedList()
//        features.add(DynamicTest.dynamicTest("Start Cucumber") { context.startTestRun() })
//        featureSupplier.get().forEach(Consumer { f: Feature ->
//            val tests: MutableList<DynamicTest> = LinkedList()
//            tests.add(DynamicTest.dynamicTest("Start Feature") {
//                context.beforeFeature(
//                    f
//                )
//            })
//            f.pickles
//                .forEach(Consumer { p: Pickle ->
//                    tests.add(
//                        DynamicTest.dynamicTest(
//                            p.name
//                        ) {
//                            val resultAtomicReference: AtomicReference<TestStepFinished?> =
//                                AtomicReference<TestStepFinished?>()
//                            val handler: EventHandler<TestStepFinished> = EventHandler<TestStepFinished> { event ->
//                                if (event.getResult().getStatus() !== Status.PASSED) {
//                                    // save the first failed test step, so that we can get the line number of the cucumber file
//                                    resultAtomicReference.compareAndSet(null, event)
//                                }
//                            }
//                            eventBus.registerHandlerFor(TestStepFinished::class.java, handler)
//                            context.runTestCase { r: Runner ->
//                                r.runPickle(
//                                    p
//                                )
//                            }
//                            eventBus.removeHandlerFor(TestStepFinished::class.java, handler)
//                            if (mainArgs.size == 0) {
//                                // if we have no main arguments, we are running as part of a junit test suite, we need to fail the junit test explicitly
//                                if (resultAtomicReference.get() != null) {
//                                    Assertions.fail(
//                                        "failed in " + f.uri + " at line " + (resultAtomicReference.get()
//                                            .getTestStep() as PickleStepTestStep).step
//                                            .location.line,
//                                        resultAtomicReference.get().getResult().getError()
//                                    )
//                                }
//                            }
//                        }
//                    )
//                })
//            features.add(DynamicContainer.dynamicContainer(f.name.orElse(f.source), tests.stream()))
//        })
//        features.add(DynamicTest.dynamicTest("Finish Cucumber") { context.finishTestRun() })
//        return features
//    }
//
//    class CdiObjectFactory : ObjectFactory {
//        override fun start() {}
//        override fun stop() {}
//        override fun addClass(clazz: Class<*>?): Boolean {
//            return true
//        }
//
//        override fun <T> getInstance(type: Class<T>): T {
//            val selected: Instance<T> = CDI.current().select(type)
//            return if (selected.isUnsatisfied) {
//                throw IllegalArgumentException(type.name + " is no CDI bean.")
//            } else {
//                selected.get()
//            }
//        }
//    }
//}
