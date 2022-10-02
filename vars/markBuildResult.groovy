/**
 * Mark the Jenkins build result.
 *
 * @param The string to mark the build result as.
 */
void call(String buildResult) {
    currentBuild.result = buildResult.toUpperCase()
}
