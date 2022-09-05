def call(String buildResult) {
    currentBuild.result = buildResult.toUpperCase()
}