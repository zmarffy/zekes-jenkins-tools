@Grab(group='com.squareup.okhttp3', module='okhttp', version='4.10.0')

import okhttp3.OkHttpClient
import okhttp3.Credentials
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.MultipartBody
import okhttp3.MediaType
import okhttp3.FormBody
import java.net.URLConnection

@NonCPS
def call(Map map=[:], String url, OkHttpClient client = null) {
    def defaultParams = [
        'method': 'get',
        'queryParams': [:],
        'headers': [:],
        'json': null,
        'data': null,
        'files': [:],
        'basicAuth': null
    ]
    functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    functionParams['method'] = functionParams['method'].toLowerCase()
    client = client ?: new OkHttpClient()

    def body = null

    if (functionParams['json'] != null) {
        if (!(functionParams['json'] instanceof CharSequence)) {
            functionParams['json'] = writeJSON(json: functionParams['json'], returnText: true)
        }
        body = RequestBody.create(MediaType.parse('application/json; charset=utf-8'), functionParams['json'])
    }
    else if (functionParams['data']) {
        def bodyBuilder = new MultipartBody.Builder()
        bodyBuilder.setType(MultipartBody.FORM)
        functionParams['data'].each { k, v -> bodyBuilder.addFormDataPart(k, v) }
        functionParams['files'].each { k, v ->
            def f = env.WORKSPACE + System.getProperty("file.separator") + v
            bodyBuilder.addFormDataPart(k, v, RequestBody.create(MediaType.parse(URLConnection.getFileNameMap().getContentTypeFor(f) ?: 'application/octet-stream'), new File(f)))
        }
        body = bodyBuilder.build()
    }

    if (functionParams['method'] in ['post', 'put'] && body == null) {
        body = RequestBody.create(null, '')
    }

    def httpBuilder = HttpUrl.parse(url).newBuilder()

    defaultParams['queryParams'].each { k, v ->
        httpBuilder.addQueryParameter(k, v)
    }

    defaultParams['headers'].each { k, v ->
        httpBuilder.addHeader(k, v)
    }

    if (functionParams['basicAuth'] != null) {
        httpBuilder.addHeader("Authorization", Credentials.basic(functionParams['basicAuth'][0], functionParams['basicAuth'][1]))
    }

    def request = new Request.Builder().url(url)

    if (body != null) {
        request."${functionParams['method']}"(body)
    }
    else {
        request."${functionParams['method']}"()
    }

    def out = client.newCall(request.build()).execute()

    return [
        'code': out.code(),
        'body': body.body(),
        'headers': body.headers()
    ]
}
