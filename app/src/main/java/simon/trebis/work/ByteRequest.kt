package simon.trebis.work

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.HttpHeaderParser

class ByteRequest(
        method: Int,
        url: String,
        private val listener: Listener<ByteArray>,
        errorListener: Response.ErrorListener
) : Request<ByteArray>(method, url, errorListener) {

    override fun deliverResponse(response: ByteArray) {
        listener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray> {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response))
    }

}
