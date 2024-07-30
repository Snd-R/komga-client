package snd.komga.client.sse

import kotlinx.coroutines.flow.Flow

interface KomgaSSESession {
    val incoming: Flow<KomgaEvent>

    fun cancel()

}
