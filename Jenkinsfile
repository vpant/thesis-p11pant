pipeline{
    agent any
    tools {
        gradle 'Gradle'
    }
    stages{
        stage("Build"){
            steps{
                configFileProvider([configFile(fileId: 'hibernate-cfg-twittercitymod', targetLocation: 'src/main/resources/assets/tc/hibernate.cfg.xml')]) {
                    // some block
                }  
                sh './gradlew build'
                
            }
        }
    }
    post{
        always{
            slackSend message: 'Twitter City Mod built'
        }
    }
}