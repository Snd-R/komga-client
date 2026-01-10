package snd.komga.client.sse

import io.ktor.client.plugins.sse.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class KtorKomgaSSESession(
    val ktorSession: ClientSSESession,
    val json: Json
) : KomgaSSESession {
    override val incoming = ktorSession.incoming
        .map { json.toKomgaEvent(it.event, it.data) }

    override fun cancel() {
        ktorSession.cancel()
    }
}