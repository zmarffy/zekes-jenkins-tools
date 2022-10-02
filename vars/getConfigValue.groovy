/**
 * Get a value from the env map, optionally tacking on the job name and two underscores before the key name, then removing the rightmost job, and so on.
 *
 * @param key The key to look up in the env map.
 * @param useSpecificIfGlobalNotFound (optional) Tack on the job name and two underscores before the key name, then emoving the rightmost job, and so on.
 * @param defaultValue (optional) If value for the given key is not found, return this.
 * @return The value from the env map.
 */
String call(Map map=[:], String key) {
    def defaultParams = [
        'useSpecificIfGlobalNotFound': true,
        'defaultValue': null,
    ]
    functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    def val = env[key]

    if (val == null && functionParams['useSpecificIfGlobalNotFound']) {
        def found = false
        /* groovylint-disable-next-line UnnecessaryGetter */
        def jobLocation = getJobLocation()
        for (d in jobLocation.indexed()) {
            val = env."${jobLocation[0..-1 - d.key].join('/')}__${key}"
            if (val != null) {
                found = true
                break
            }
        }
        if (!found) {
            val = functionParams['defaultValue']
        }
    }

    return val
}
