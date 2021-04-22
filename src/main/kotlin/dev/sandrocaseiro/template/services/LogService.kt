package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.properties.AppProperties
import io.vertx.core.http.HttpServerRequest
import org.jboss.logging.MDC
import javax.enterprise.context.RequestScoped

@RequestScoped
class LogService(
    private val appProperties: AppProperties
) {
    companion object {
        private const val PROJECT_ID = "project_id"
        private const val USER = "user"
        private const val IP = "src_ip"
        private const val ACTIVITY = "activity"
        private const val ACTION = "action"
        private const val SAVE_TO_FILE = "save_to_file"

        private val HEADERS_TO_TRY = arrayOf("X-Forwarded-For", "Client-Ip", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR")
    }

    fun build(request: HttpServerRequest) {
        MDC.put(PROJECT_ID, appProperties.id)
        MDC.put(USER, getUser(request))
        MDC.put(IP, getRemoteAddress(request))
        MDC.put(ACTIVITY, request.uri())
        MDC.put(ACTION, request.rawMethod())
        MDC.put(SAVE_TO_FILE, "true")
    }

    fun clear() {
        MDC.clear()
    }

    private fun getUser(request: HttpServerRequest): String? {
        for (header in HEADERS_TO_TRY) {
            val ip = request.getHeader(header)
            if (ip != null && ip.isNotEmpty() && !"unknown".equals(ip, ignoreCase = true)) {
                return ip
            }
        }
        return request.remoteAddress().toString()
    }

    private fun getRemoteAddress(request: HttpServerRequest): String? {
        for (header in HEADERS_TO_TRY) {
            val ip = request.getHeader(header)
            if (ip != null && ip.isNotEmpty() && !"unknown".equals(ip, ignoreCase = true)) {
                return ip
            }
        }
        return request.remoteAddress().toString()
    }
}
