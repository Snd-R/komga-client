package snd.komga.client.filesystem

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class HttpFileSystemClient(private val ktor: HttpClient) : KomgaFileSystemClient {

    override suspend fun getDirectoryListing(request: DirectoryRequest): DirectoryListing {
        return ktor.post("api/v1/filesystem") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}