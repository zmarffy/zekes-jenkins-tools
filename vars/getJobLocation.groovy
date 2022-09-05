def call() {
    return env.JOB_NAME.split('/').toList()
}