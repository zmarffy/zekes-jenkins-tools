/**
 * Mark the Jenkins stage result.
 *
 * @param The string to mark the stage result as.
 * @param message (optional) The message to print when marking the stage result. Defaults to "Stage marked as $newStageResult".
 * @param includeDisclaimer (optional) If true, tack a disclaimer on to the message param.
 */
void call(Map map=[:], String stageResult) {
    newStageResult = stageResult.toUpperCase()

    def defaultParams = [
        'message': "Stage marked as $newStageResult",
        'includeDisclaimer': true
    ]
    functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    if (functionParams['includeDisclaimer']) {
        functionParams['message'] += '\nNote that this message itself does not necessarily denote an error; it is simply informative'
    }

    catchError(buildResult: currentBuild.result, stageResult: newStageResult) {
        error(functionParams['message'])
    }
}
