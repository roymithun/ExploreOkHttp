import okhttp3.*
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.time.LocalDateTime

class SimpleEventListener : EventListener() {
    private var callStartNanos: Long = 0
    private fun printEvent(name: String, msg: String? = "") {
        val nowNanos = System.nanoTime()
        if (name == "callStart") {
            callStartNanos = nowNanos
        }
        val elapsedNanos = nowNanos - callStartNanos
        System.out.printf("%.3f %s [Params: %s]%n", elapsedNanos / 1000000000.0, name, msg)
    }

    override fun cacheConditionalHit(call: Call, cachedResponse: Response) {
        super.cacheConditionalHit(call, cachedResponse)
        printEvent("cacheConditionalHit", "cachedResponse $cachedResponse")
    }

    override fun cacheHit(call: Call, response: Response) {
        super.cacheHit(call, response)
        printEvent("cacheHit", "response $response")
    }

    override fun cacheMiss(call: Call) {
        super.cacheMiss(call)
        printEvent("cacheMiss")
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        printEvent("callEnd")
    }

    override fun callFailed(call: Call, ioe: IOException) {
        super.callFailed(call, ioe)
        println("callFailed at ${LocalDateTime.now()} ioException is ${ioe.message}")
    }

    override fun callStart(call: Call) {
        super.callStart(call)
        printEvent("callStart")
    }

    override fun canceled(call: Call) {
        super.canceled(call)
        printEvent("canceled")
    }

    override fun connectEnd(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        printEvent("connectEnd", "sockAddress ${inetSocketAddress.address} and proxy $proxy with protocol $protocol")
    }

    override fun connectFailed(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?,
        ioe: IOException
    ) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        printEvent("connectFailed", "sockAddress ${inetSocketAddress.address} and proxy $proxy with protocol $protocol")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        printEvent("connectStart", "sockAddress ${inetSocketAddress.address} and proxy $proxy")
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
        printEvent("connectionAcquired", "connection $connection")
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
        printEvent("connectionReleased", "connection $connection")
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        printEvent("dnsEnd", "domainName $domainName and inetAddressList $inetAddressList")
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        printEvent("dnsStart", "domainName $domainName")
    }

    override fun proxySelectEnd(call: Call, url: HttpUrl, proxies: List<Proxy>) {
        super.proxySelectEnd(call, url, proxies)
        printEvent("proxySelectEnd")
    }

    override fun proxySelectStart(call: Call, url: HttpUrl) {
        super.proxySelectStart(call, url)
        printEvent("proxySelectStart")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        printEvent("requestBodyEnd", "byteCount $byteCount")
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
        printEvent("requestBodyStart")
    }

    override fun requestFailed(call: Call, ioe: IOException) {
        super.requestFailed(call, ioe)
        printEvent("requestFailed", "ioe $ioe")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
        printEvent("requestHeadersEnd", "request $request")
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
        printEvent("requestHeadersStart")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        printEvent("responseHeadersEnd", "byteCount $byteCount")
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
        printEvent("responseBodyStart")
    }

    override fun responseFailed(call: Call, ioe: IOException) {
        super.responseFailed(call, ioe)
        printEvent("responseFailed")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
        printEvent("responseHeadersEnd", "response $response")
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
        printEvent("responseHeadersStart")
    }

    override fun satisfactionFailure(call: Call, response: Response) {
        super.satisfactionFailure(call, response)
        printEvent("satisfactionFailure", "response $response")
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
        printEvent("secureConnectEnd", "handshake $handshake")
    }

    override fun secureConnectStart(call: Call) {
        super.secureConnectStart(call)
        printEvent("secureConnectStart")    }

}