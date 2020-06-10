pipeline{
    agent any
    tools {
        jdk "JDK 8"
        gradle 'Gradle 5.0'
    }
    stages{
        stage("Build"){
            steps{
                configFileProvider([configFile(fileId: 'hibernate-cfg-twittercitymod', targetLocation: 'src/main/resources/assets/tc/hibernate.cfg.xml')]) {
                    // some block
                }  
                sh 'gradle clean build'
                
            }
        }
    }
    post{
        always{
            slackSend message: 'Twitter City Mod built'
        }
    }
}