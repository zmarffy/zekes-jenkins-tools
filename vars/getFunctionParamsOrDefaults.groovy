def call(Map defaultFunctionParams, Map givenParams) {
    def retrievedParams = [:]
    givenParams.each { k, v -> retrievedParams[k] = v }
    defaultFunctionParams.each { k, v ->
        if (!givenParams.containsKey(k)) {
            retrievedParams[k] = defaultFunctionParams[k]
        }
    }
    return retrievedParams
}