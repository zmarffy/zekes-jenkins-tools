def call(obj) {
    return java.io.Serializable in obj.getClass().getInterfaces()
}