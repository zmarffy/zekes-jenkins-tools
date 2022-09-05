def call(Map map=[:], String message) {
    def defaultParams = [
        'ifttt': false,
        'iftttKeyCredsId': getConfigValue('ifttt_creds_id', defaultValue: 'ifttt-key'),
        'iftttKeyName': getConfigValue('ifttt_key_name', defaultValue: 'jenkins')
    ]
    functionParams = getFunctionParamsOrDefaults(defaultParams, map)

    if (functionParams['ifttt']) {
        withCredentials(
            [
                string(
                    credentialsId: functionParams['iftttKeyCredsId'],
                    variable: 'IFTTT_KEY'
                )
            ]
        ) {
            performHttpRequest("https://maker.ifttt.com/trigger/${functionParams['iftttKeyName']}/with/key/$IFTTT_KEY", method: 'POST', json: ['value1': message])
        }
    }
}