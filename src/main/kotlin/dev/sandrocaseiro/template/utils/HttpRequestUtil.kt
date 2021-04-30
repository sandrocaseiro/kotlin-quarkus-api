package dev.sandrocaseiro.template.utils

import dev.sandrocaseiro.template.filters.PageRequestFilter
import dev.sandrocaseiro.template.models.DPageable
import org.jboss.resteasy.spi.HttpRequest

fun HttpRequest.getPageable(): DPageable = this.getAttribute(PageRequestFilter.PAGEABLE_CONTEXT) as DPageable
