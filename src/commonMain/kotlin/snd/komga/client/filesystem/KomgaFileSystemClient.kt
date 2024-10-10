package snd.komga.client.filesystem

interface KomgaFileSystemClient {
    suspend fun getDirectoryListing(request: DirectoryRequest): DirectoryListing
}