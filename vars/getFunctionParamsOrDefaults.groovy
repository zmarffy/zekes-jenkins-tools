/**
 * Given a map of default key and values and a map of provided keys and values, return a map made of the provided keys and values for keys, else if the values for a key do not exist in the provided but do exist in the default, return the default value for that key in the returned map
 *
 * @param defaultFunctionParams The default key and value map.
 * @param givenParams The provided key and value map.
 * @return The resulting key and value pair.
 */
Map call(Map defaultFunctionParams, Map givenParams) {
    def retrievedParams = [:]
    givenParams.each { k, v -> retrievedParams[k] = v }
    defaultFunctionParams.each { k, v ->
        if (!givenParams.containsKey(k)) {
            retrievedParams[k] = defaultFunctionParams[k]
        }
    }
    return retrievedParams
}
