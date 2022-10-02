/**
 * Get the job name as a list.
 *
 * @return The job name.
 */
List call() {
    return env.JOB_NAME.split('/').toList()
}
