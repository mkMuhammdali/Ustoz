package e.ustoz.data.datasource.rest.exception

import e.ustoz.data.datasource.rest.retrofit.exception.RestErrorException
import e.ustoz.data.datasource.rest.retrofit.interceptor.HttpErrorResponse


class UnauthorizedException(override val response: HttpErrorResponse) : RestErrorException(response)