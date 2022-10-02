/**
 * Return if an object is serializable or not.
 *
 * @param The object.
 * @return Whether an object is serializable or not.
 */
Boolean call(Object obj) {
    /* groovylint-disable-next-line UnnecessaryGetter */
    return Serializable in obj.getClass().getInterfaces()
}
