package dev.sandrocaseiro.template.models

import io.quarkus.panache.common.Page
import org.eclipse.microprofile.openapi.annotations.media.Schema

import io.quarkus.panache.common.Sort as QuarkusSort

@Schema(hidden = true)
class DPageable (
    private var currentPage: Int = CURRENT_PAGE,
    private var pageSize: Int = PAGE_SIZE,
    private var pageSort: List<Sort>? = null
) {
    companion object {
        const val PAGE_SIZE = 10
        const val CURRENT_PAGE = 1
    }

    val page
        get(): Page = Page.of(currentPage - 1, pageSize)

    val sort
        get(): QuarkusSort? {
        var pageableSort: QuarkusSort? = null
        for (fieldSort in pageSort.orEmpty()) {
            val direction = if (fieldSort.isDescending) QuarkusSort.Direction.Descending
            else QuarkusSort.Direction.Ascending

            if (pageableSort == null)
                pageableSort = QuarkusSort.by(fieldSort.field, direction)
            else
                pageableSort.and(fieldSort.field, direction)
        }

        return pageableSort
    }

    data class Sort(
        val field: String,
        val isDescending: Boolean
    )
}
