def call(Map map=[:], String url) {
    def defaultParams = [
        'queryParams': [],
    ]

    def functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    queryParams = functionParams['queryParams']

    def constructedUrl = url
    if (queryParams) {
        constructedUrl += '?'
    }
    queryParams.each {
        constructedUrl += "${URLEncoder.encode(it[0].toString())}=${URLEncoder.encode(it[1].toString())}&"
    }
    if (queryParams) {
        constructedUrl = constructedUrl[0..-2]
    }

    map['url'] = constructedUrl

    return httpRequest(map)
}
