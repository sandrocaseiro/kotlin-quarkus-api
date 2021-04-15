//package dev.sandrocaseiro.template
//
//import com.github.tomakehurst.wiremock.WireMockServer
//import com.github.tomakehurst.wiremock.client.WireMock
//import com.github.tomakehurst.wiremock.core.WireMockConfiguration
//import javax.annotation.PostConstruct
//import javax.annotation.PreDestroy
//import javax.enterprise.context.ApplicationScoped
//
//@ApplicationScoped
//class MockServer {
////    @Autowired
////    lateinit var env: Environment
//
//    val mockServer = WireMockServer(
//        WireMockConfiguration.options()
//            .port(8089)
//            .usingFilesUnderClasspath("mocks")
//    ).apply {
//        start()
//        WireMock.configureFor(this.port())
//    }
//
//    fun reset() = mockServer.resetMappings()
//
//    @PostConstruct
//    fun init() {
////        if ("true" != env.getProperty("isTest")) {
////            ExternalApiSteps.stubIsWorking()
////        }
//    }
//
//    @PreDestroy
//    fun dispose() {
//        mockServer.shutdownServer()
//        while (mockServer.isRunning) {
//            try {
//                Thread.sleep(100L)
//            }
//            catch (e: InterruptedException) {}
//        }
//    }
//}
