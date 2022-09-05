def call(Map map=[:], String key) {
    def defaultParams = [
        'useSpecificIfGlobalNotFound': true,
        'defaultValue': null,
    ]
    functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    def val = env[key]

    if (val == null && functionParams['useSpecificIfGlobalNotFound']) {
        def found = false
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