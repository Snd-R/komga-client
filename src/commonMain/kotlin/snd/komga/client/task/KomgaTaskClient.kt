package snd.komga.client.task

interface KomgaTaskClient {
    suspend fun emptyTaskQueue(): Int
}