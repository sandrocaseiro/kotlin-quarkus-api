import io.cucumber.core.backend.ObjectFactory
import io.quarkus.arc.Unremovable
import io.quarkus.bootstrap.app.QuarkusBootstrap
import io.quarkus.bootstrap.app.RunningQuarkusApplication
import io.quarkus.bootstrap.model.PathsCollection
import io.quarkus.test.common.PathTestHelper
import java.nio.file.Path
import java.nio.file.Paths
import javax.enterprise.context.ApplicationScoped

class CdiObjectFactory : ObjectFactory {
    private var application: RunningQuarkusApplication? = null

    init {
        startApplication()
    }

    override fun start() {

    }

    override fun stop() {

    }

    override fun addClass(stepClass: Class<*>): Boolean {
        if (stepClass.getAnnotation(ApplicationScoped::class.java) == null)
            throw RuntimeException("You need to register your step definitions with @ApplicationScoped, otherwise Arc container does not register them as beans.")

        if (stepClass.getAnnotation(Unremovable::class.java) == null)
            throw RuntimeException("You need to register your step definitions with @Unremovable, otherwise Arc container may remove the beans at runtime")

        return true
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getInstance(type: Class<T>): T {
        if (application == null)
            throw RuntimeException("Application has not been successfully started")

        try {
            return application!!.instance(type) as T
        }
        catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun startApplication() {
        try {
            val rootBuilder: PathsCollection.Builder = PathsCollection.builder()

            val testClassLocation: Path = PathTestHelper.getTestClassesLocation(CdiObjectFactory::class.java)
            val appClassLocation: Path = PathTestHelper.getAppClassLocationForTestLocation(testClassLocation.toString())

            rootBuilder.add(testClassLocation)
            rootBuilder.add(appClassLocation)

            val appBuilder: QuarkusBootstrap.Builder = QuarkusBootstrap.builder()
                .setIsolateDeployment(false)
                .setMode(QuarkusBootstrap.Mode.TEST)
                .setProjectRoot(Paths.get("").normalize().toAbsolutePath())
                .setApplicationRoot(rootBuilder.build())

            application = appBuilder
                .build()
                .bootstrap()
                .createAugmentor()
                .createInitialRuntimeApplication()
                .run()
        }
        catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
