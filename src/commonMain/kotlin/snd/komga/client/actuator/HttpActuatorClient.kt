package snd.komga.client.actuator

import io.ktor.client.*
import io.ktor.client.request.*

class HttpActuatorClient internal constructor(private val ktor: HttpClient) : KomgaActuatorClient {

    override suspend fun shutdown() {
        ktor.post("actuator/shutdown")
    }
}