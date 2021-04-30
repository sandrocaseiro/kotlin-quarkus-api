package dev.sandrocaseiro.template.filters

import dev.sandrocaseiro.template.exceptions.PageableBadRequestException
import dev.sandrocaseiro.template.models.DPageable
import io.vertx.core.http.HttpServerRequest
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Context
import javax.ws.rs.ext.Provider

import dev.sandrocaseiro.template.utils.toInt as toIntDefault

@Provider
class PageRequestFilter: ContainerRequestFilter {
    companion object {
        const val PAGEABLE_CONTEXT = "pageable"
        private const val SORT_PATTERN = "^(\\-?)(.*)"
        private const val OFFSET_PARAM = "\$pageoffset"
        private const val LIMIT_PARAM = "\$pagelimit"
        private const val SORT_PARAM = "\$sort"
    }

    private val pattern: Pattern = Pattern.compile(SORT_PATTERN)

    @Context
    lateinit var request: HttpServerRequest

    override fun filter(requestContext: ContainerRequestContext) {
        var pageOffset: Int = DPageable.CURRENT_PAGE
        if (request.params().contains(OFFSET_PARAM))
            pageOffset = request.params().get(OFFSET_PARAM).toIntDefault(DPageable.CURRENT_PAGE)

        var pageLimit: Int = DPageable.PAGE_SIZE
        if (request.params().contains(LIMIT_PARAM))
            pageLimit = request.params().get(LIMIT_PARAM).toIntDefault(DPageable.PAGE_SIZE)

        if (pageOffset <= 0)
            throw PageableBadRequestException(OFFSET_PARAM)

        var sort = ""
        if (request.params().contains(SORT_PARAM))
            sort = request.params().get(SORT_PARAM)

        val requestSortFields = sort.split(",").filter { it.isNotBlank() }
        val sortFields = mutableListOf<DPageable.Sort>()
        for (sortField: String in requestSortFields) {
            val matcher: Matcher = pattern.matcher(sortField.trim())
            if (!matcher.matches())
                throw PageableBadRequestException(SORT_PARAM)

            val signal: String = matcher.group(1)
            val field: String = matcher.group(2)

            if (signal.isNotEmpty() && "-" != signal)
                throw PageableBadRequestException(SORT_PARAM)

            sortFields.add(DPageable.Sort(field, "-" == signal))
        }

        requestContext.setProperty(PAGEABLE_CONTEXT, DPageable(pageOffset, pageLimit, sortFields))
    }
}
