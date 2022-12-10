def call(Map map=[:], String url) {
    def defaultParams = [
        'queryParams': [],
        'headers': []
    ]

    def functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    queryParams = functionParams.pop('queryParams')

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
    map['quiet'] = true
    map['customHeaders'] = functionParams.pop('headers').collect { ['name': it[0], 'value': it[1]] }

    return httpRequest(map)
}
