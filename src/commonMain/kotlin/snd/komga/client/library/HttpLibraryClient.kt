package snd.komga.client.library

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class HttpLibraryClient(private val ktor: HttpClient) : KomgaLibraryClient {

    override suspend fun getLibraries(): List<KomgaLibrary> {
        return ktor.get("api/v1/libraries").body()
    }

    override suspend fun getLibrary(libraryId: KomgaLibraryId): KomgaLibrary {
        return ktor.get("api/v1/libraries/$libraryId").body()
    }

    override suspend fun addOne(request: KomgaLibraryCreateRequest): KomgaLibrary {
        return ktor.post("api/v1/libraries") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun patchOne(libraryId: KomgaLibraryId, request: KomgaLibraryUpdateRequest) {
        ktor.patch("api/v1/libraries/$libraryId") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun deleteOne(libraryId: KomgaLibraryId) {
        ktor.delete("api/v1/libraries/$libraryId")
    }

    override suspend fun scan(libraryId: KomgaLibraryId, deep: Boolean) {
        ktor.post("api/v1/libraries/$libraryId/scan") {
            parameter("deep", deep)
        }
    }

    override suspend fun analyze(libraryId: KomgaLibraryId) {
        ktor.post("api/v1/libraries/$libraryId/analyze")
    }

    override suspend fun refreshMetadata(libraryId: KomgaLibraryId) {
        ktor.post("api/v1/libraries/$libraryId/metadata/refresh")
    }

    override suspend fun emptyTrash(libraryId: KomgaLibraryId) {
        ktor.post("api/v1/libraries/$libraryId/empty-trash")
    }
}