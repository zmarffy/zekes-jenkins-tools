def call(Map map=[:], String stageResult) {
    stageResult = stageResult.toUpperCase()

    def defaultParams = [
        'message': "Stage marked as $stageResult",
        'includeDisclaimer': true
    ]
    functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    if (functionParams['includeDisclaimer']) {
        functionParams['message'] += '\nNote that this message itself does not necessarily denote an error; it is simply informative'
    }

    catchError(buildResult: currentBuild.result, stageResult: stageResult) {
        error(functionParams['message'])
    }
}