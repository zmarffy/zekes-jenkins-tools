def call(Map variables, Closure steps) {
    def withEnvParam = []
    variables.each { k, v ->
        if ('=' in k) {
            error('Variable name cannot have \'=\' in it')
        }
        withEnvParam.add("${k.toUpperCase()}=$v")
    }
    withEnv(withEnvParam) {
        steps()
    }
}